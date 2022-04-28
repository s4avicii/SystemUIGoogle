package androidx.mediarouter.app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatDialog;
import androidx.mediarouter.media.MediaRouteSelector;
import androidx.mediarouter.media.MediaRouter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.p012wm.shell.C1777R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class MediaRouteDynamicChooserDialog extends AppCompatDialog {
    public RecyclerAdapter mAdapter;
    public boolean mAttachedToWindow;
    public final MediaRouterCallback mCallback;
    public Context mContext;
    public final C02671 mHandler = new Handler() {
        public final void handleMessage(Message message) {
            if (message.what == 1) {
                MediaRouteDynamicChooserDialog mediaRouteDynamicChooserDialog = MediaRouteDynamicChooserDialog.this;
                Objects.requireNonNull(mediaRouteDynamicChooserDialog);
                mediaRouteDynamicChooserDialog.mLastUpdateTime = SystemClock.uptimeMillis();
                mediaRouteDynamicChooserDialog.mRoutes.clear();
                mediaRouteDynamicChooserDialog.mRoutes.addAll((List) message.obj);
                mediaRouteDynamicChooserDialog.mAdapter.rebuildItems();
            }
        }
    };
    public long mLastUpdateTime;
    public RecyclerView mRecyclerView;
    public final MediaRouter mRouter;
    public ArrayList mRoutes;
    public MediaRouter.RouteInfo mSelectingRoute;
    public MediaRouteSelector mSelector = MediaRouteSelector.EMPTY;
    public long mUpdateRoutesDelayMs;

    public final class MediaRouterCallback extends MediaRouter.Callback {
        public MediaRouterCallback() {
        }

        public final void onRouteAdded() {
            MediaRouteDynamicChooserDialog.this.refreshRoutes();
        }

        public final void onRouteChanged(MediaRouter.RouteInfo routeInfo) {
            MediaRouteDynamicChooserDialog.this.refreshRoutes();
        }

        public final void onRouteRemoved() {
            MediaRouteDynamicChooserDialog.this.refreshRoutes();
        }

        public final void onRouteSelected(MediaRouter.RouteInfo routeInfo) {
            MediaRouteDynamicChooserDialog.this.dismiss();
        }
    }

    public final class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        public final Drawable mDefaultIcon;
        public final LayoutInflater mInflater;
        public final ArrayList<Item> mItems = new ArrayList<>();
        public final Drawable mSpeakerGroupIcon;
        public final Drawable mSpeakerIcon;
        public final Drawable mTvIcon;

        public class RouteViewHolder extends RecyclerView.ViewHolder {
            public final ImageView mImageView;
            public final View mItemView;
            public final ProgressBar mProgressBar;
            public final TextView mTextView;

            public RouteViewHolder(View view) {
                super(view);
                this.mItemView = view;
                this.mImageView = (ImageView) view.findViewById(C1777R.C1779id.mr_picker_route_icon);
                ProgressBar progressBar = (ProgressBar) view.findViewById(C1777R.C1779id.mr_picker_route_progress_bar);
                this.mProgressBar = progressBar;
                this.mTextView = (TextView) view.findViewById(C1777R.C1779id.mr_picker_route_name);
                MediaRouterThemeHelper.setIndeterminateProgressBarColor(MediaRouteDynamicChooserDialog.this.mContext, progressBar);
            }
        }

        public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
            if (i == 1) {
                return new HeaderViewHolder(this.mInflater.inflate(C1777R.layout.mr_picker_header_item, recyclerView, false));
            }
            if (i == 2) {
                return new RouteViewHolder(this.mInflater.inflate(C1777R.layout.mr_picker_route_item, recyclerView, false));
            }
            Log.w("RecyclerAdapter", "Cannot create ViewHolder because of wrong view type");
            return null;
        }

        public class HeaderViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;

            public HeaderViewHolder(View view) {
                super(view);
                this.mTextView = (TextView) view.findViewById(C1777R.C1779id.mr_picker_header_name);
            }
        }

        public class Item {
            public final Object mData;
            public final int mType;

            public Item(Object obj) {
                this.mData = obj;
                if (obj instanceof String) {
                    this.mType = 1;
                } else if (obj instanceof MediaRouter.RouteInfo) {
                    this.mType = 2;
                } else {
                    this.mType = 0;
                    Log.w("RecyclerAdapter", "Wrong type of data passed to Item constructor");
                }
            }
        }

        public RecyclerAdapter() {
            this.mInflater = LayoutInflater.from(MediaRouteDynamicChooserDialog.this.mContext);
            this.mDefaultIcon = MediaRouterThemeHelper.getIconByAttrId(MediaRouteDynamicChooserDialog.this.mContext, C1777R.attr.mediaRouteDefaultIconDrawable);
            this.mTvIcon = MediaRouterThemeHelper.getIconByAttrId(MediaRouteDynamicChooserDialog.this.mContext, C1777R.attr.mediaRouteTvIconDrawable);
            this.mSpeakerIcon = MediaRouterThemeHelper.getIconByAttrId(MediaRouteDynamicChooserDialog.this.mContext, C1777R.attr.mediaRouteSpeakerIconDrawable);
            this.mSpeakerGroupIcon = MediaRouterThemeHelper.getIconByAttrId(MediaRouteDynamicChooserDialog.this.mContext, C1777R.attr.mediaRouteSpeakerGroupIconDrawable);
            rebuildItems();
        }

        public final int getItemCount() {
            return this.mItems.size();
        }

        public final int getItemViewType(int i) {
            Item item = this.mItems.get(i);
            Objects.requireNonNull(item);
            return item.mType;
        }

        public final void rebuildItems() {
            this.mItems.clear();
            this.mItems.add(new Item(MediaRouteDynamicChooserDialog.this.mContext.getString(C1777R.string.mr_chooser_title)));
            Iterator it = MediaRouteDynamicChooserDialog.this.mRoutes.iterator();
            while (it.hasNext()) {
                this.mItems.add(new Item((MediaRouter.RouteInfo) it.next()));
            }
            notifyDataSetChanged();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0060, code lost:
            if (r1 != null) goto L_0x0090;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onBindViewHolder(androidx.recyclerview.widget.RecyclerView.ViewHolder r8, int r9) {
            /*
                r7 = this;
                int r0 = r7.getItemViewType(r9)
                java.util.ArrayList<androidx.mediarouter.app.MediaRouteDynamicChooserDialog$RecyclerAdapter$Item> r7 = r7.mItems
                java.lang.Object r7 = r7.get(r9)
                androidx.mediarouter.app.MediaRouteDynamicChooserDialog$RecyclerAdapter$Item r7 = (androidx.mediarouter.app.MediaRouteDynamicChooserDialog.RecyclerAdapter.Item) r7
                r9 = 1
                if (r0 == r9) goto L_0x0094
                java.lang.String r1 = "RecyclerAdapter"
                r2 = 2
                if (r0 == r2) goto L_0x001b
                java.lang.String r7 = "Cannot bind item to ViewHolder because of wrong view type"
                android.util.Log.w(r1, r7)
                goto L_0x00a4
            L_0x001b:
                androidx.mediarouter.app.MediaRouteDynamicChooserDialog$RecyclerAdapter$RouteViewHolder r8 = (androidx.mediarouter.app.MediaRouteDynamicChooserDialog.RecyclerAdapter.RouteViewHolder) r8
                java.util.Objects.requireNonNull(r7)
                java.lang.Object r7 = r7.mData
                androidx.mediarouter.media.MediaRouter$RouteInfo r7 = (androidx.mediarouter.media.MediaRouter.RouteInfo) r7
                android.view.View r0 = r8.mItemView
                r3 = 0
                r0.setVisibility(r3)
                android.widget.ProgressBar r0 = r8.mProgressBar
                r3 = 4
                r0.setVisibility(r3)
                android.view.View r0 = r8.mItemView
                androidx.mediarouter.app.MediaRouteDynamicChooserDialog$RecyclerAdapter$RouteViewHolder$1 r3 = new androidx.mediarouter.app.MediaRouteDynamicChooserDialog$RecyclerAdapter$RouteViewHolder$1
                r3.<init>(r7)
                r0.setOnClickListener(r3)
                android.widget.TextView r0 = r8.mTextView
                java.util.Objects.requireNonNull(r7)
                java.lang.String r3 = r7.mName
                r0.setText(r3)
                android.widget.ImageView r0 = r8.mImageView
                androidx.mediarouter.app.MediaRouteDynamicChooserDialog$RecyclerAdapter r8 = androidx.mediarouter.app.MediaRouteDynamicChooserDialog.RecyclerAdapter.this
                java.util.Objects.requireNonNull(r8)
                android.net.Uri r3 = r7.mIconUri
                if (r3 == 0) goto L_0x0078
                androidx.mediarouter.app.MediaRouteDynamicChooserDialog r4 = androidx.mediarouter.app.MediaRouteDynamicChooserDialog.this     // Catch:{ IOException -> 0x0063 }
                android.content.Context r4 = r4.mContext     // Catch:{ IOException -> 0x0063 }
                android.content.ContentResolver r4 = r4.getContentResolver()     // Catch:{ IOException -> 0x0063 }
                java.io.InputStream r4 = r4.openInputStream(r3)     // Catch:{ IOException -> 0x0063 }
                r5 = 0
                android.graphics.drawable.Drawable r1 = android.graphics.drawable.Drawable.createFromStream(r4, r5)     // Catch:{ IOException -> 0x0063 }
                if (r1 == 0) goto L_0x0078
                goto L_0x0090
            L_0x0063:
                r4 = move-exception
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                java.lang.String r6 = "Failed to load "
                r5.append(r6)
                r5.append(r3)
                java.lang.String r3 = r5.toString()
                android.util.Log.w(r1, r3, r4)
            L_0x0078:
                int r1 = r7.mDeviceType
                if (r1 == r9) goto L_0x008d
                if (r1 == r2) goto L_0x008a
                boolean r7 = r7.isGroup()
                if (r7 == 0) goto L_0x0087
                android.graphics.drawable.Drawable r7 = r8.mSpeakerGroupIcon
                goto L_0x008f
            L_0x0087:
                android.graphics.drawable.Drawable r7 = r8.mDefaultIcon
                goto L_0x008f
            L_0x008a:
                android.graphics.drawable.Drawable r7 = r8.mSpeakerIcon
                goto L_0x008f
            L_0x008d:
                android.graphics.drawable.Drawable r7 = r8.mTvIcon
            L_0x008f:
                r1 = r7
            L_0x0090:
                r0.setImageDrawable(r1)
                goto L_0x00a4
            L_0x0094:
                androidx.mediarouter.app.MediaRouteDynamicChooserDialog$RecyclerAdapter$HeaderViewHolder r8 = (androidx.mediarouter.app.MediaRouteDynamicChooserDialog.RecyclerAdapter.HeaderViewHolder) r8
                java.util.Objects.requireNonNull(r7)
                java.lang.Object r7 = r7.mData
                java.lang.String r7 = r7.toString()
                android.widget.TextView r8 = r8.mTextView
                r8.setText(r7)
            L_0x00a4:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.mediarouter.app.MediaRouteDynamicChooserDialog.RecyclerAdapter.onBindViewHolder(androidx.recyclerview.widget.RecyclerView$ViewHolder, int):void");
        }
    }

    public static final class RouteComparator implements Comparator<MediaRouter.RouteInfo> {
        public static final RouteComparator sInstance = new RouteComparator();

        public final int compare(Object obj, Object obj2) {
            MediaRouter.RouteInfo routeInfo = (MediaRouter.RouteInfo) obj;
            MediaRouter.RouteInfo routeInfo2 = (MediaRouter.RouteInfo) obj2;
            Objects.requireNonNull(routeInfo);
            String str = routeInfo.mName;
            Objects.requireNonNull(routeInfo2);
            return str.compareToIgnoreCase(routeInfo2.mName);
        }
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MediaRouteDynamicChooserDialog(android.content.Context r3) {
        /*
            r2 = this;
            r0 = 0
            android.view.ContextThemeWrapper r3 = androidx.mediarouter.app.MediaRouterThemeHelper.createThemedDialogContext(r3, r0)
            int r0 = androidx.mediarouter.app.MediaRouterThemeHelper.createThemedDialogStyle(r3)
            r2.<init>(r3, r0)
            androidx.mediarouter.media.MediaRouteSelector r3 = androidx.mediarouter.media.MediaRouteSelector.EMPTY
            r2.mSelector = r3
            androidx.mediarouter.app.MediaRouteDynamicChooserDialog$1 r3 = new androidx.mediarouter.app.MediaRouteDynamicChooserDialog$1
            r3.<init>()
            r2.mHandler = r3
            android.content.Context r3 = r2.getContext()
            androidx.mediarouter.media.MediaRouter r0 = androidx.mediarouter.media.MediaRouter.getInstance(r3)
            r2.mRouter = r0
            androidx.mediarouter.app.MediaRouteDynamicChooserDialog$MediaRouterCallback r0 = new androidx.mediarouter.app.MediaRouteDynamicChooserDialog$MediaRouterCallback
            r0.<init>()
            r2.mCallback = r0
            r2.mContext = r3
            android.content.res.Resources r3 = r3.getResources()
            r0 = 2131493005(0x7f0c008d, float:1.8609478E38)
            int r3 = r3.getInteger(r0)
            long r0 = (long) r3
            r2.mUpdateRoutesDelayMs = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.mediarouter.app.MediaRouteDynamicChooserDialog.<init>(android.content.Context):void");
    }

    public final void refreshRoutes() {
        Collection collection;
        if (this.mSelectingRoute == null && this.mAttachedToWindow) {
            Objects.requireNonNull(this.mRouter);
            MediaRouter.checkCallingThread();
            MediaRouter.GlobalMediaRouter globalRouter = MediaRouter.getGlobalRouter();
            if (globalRouter == null) {
                collection = Collections.emptyList();
            } else {
                collection = globalRouter.mRoutes;
            }
            ArrayList arrayList = new ArrayList(collection);
            int size = arrayList.size();
            while (true) {
                int i = size - 1;
                boolean z = true;
                if (size <= 0) {
                    break;
                }
                MediaRouter.RouteInfo routeInfo = (MediaRouter.RouteInfo) arrayList.get(i);
                if (routeInfo.isDefaultOrBluetooth() || !routeInfo.mEnabled || !routeInfo.matchesSelector(this.mSelector)) {
                    z = false;
                }
                if (!z) {
                    arrayList.remove(i);
                }
                size = i;
            }
            Collections.sort(arrayList, RouteComparator.sInstance);
            if (SystemClock.uptimeMillis() - this.mLastUpdateTime >= this.mUpdateRoutesDelayMs) {
                this.mLastUpdateTime = SystemClock.uptimeMillis();
                this.mRoutes.clear();
                this.mRoutes.addAll(arrayList);
                this.mAdapter.rebuildItems();
                return;
            }
            this.mHandler.removeMessages(1);
            C02671 r1 = this.mHandler;
            r1.sendMessageAtTime(r1.obtainMessage(1, arrayList), this.mLastUpdateTime + this.mUpdateRoutesDelayMs);
        }
    }

    public final void setRouteSelector(MediaRouteSelector mediaRouteSelector) {
        if (mediaRouteSelector == null) {
            throw new IllegalArgumentException("selector must not be null");
        } else if (!this.mSelector.equals(mediaRouteSelector)) {
            this.mSelector = mediaRouteSelector;
            if (this.mAttachedToWindow) {
                this.mRouter.removeCallback(this.mCallback);
                this.mRouter.addCallback(mediaRouteSelector, this.mCallback, 1);
            }
            refreshRoutes();
        }
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAttachedToWindow = true;
        this.mRouter.addCallback(this.mSelector, this.mCallback, 1);
        refreshRoutes();
    }

    public final void onCreate(Bundle bundle) {
        int i;
        super.onCreate(bundle);
        setContentView((int) C1777R.layout.mr_picker_dialog);
        MediaRouterThemeHelper.setDialogBackgroundColor(this.mContext, this);
        this.mRoutes = new ArrayList();
        ((ImageButton) findViewById(C1777R.C1779id.mr_picker_close_button)).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                MediaRouteDynamicChooserDialog.this.dismiss();
            }
        });
        this.mAdapter = new RecyclerAdapter();
        RecyclerView recyclerView = (RecyclerView) findViewById(C1777R.C1779id.mr_picker_list);
        this.mRecyclerView = recyclerView;
        recyclerView.setAdapter(this.mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(1));
        Context context = this.mContext;
        int i2 = -1;
        if (!context.getResources().getBoolean(C1777R.bool.is_tablet)) {
            i = -1;
        } else {
            i = MediaRouteDialogHelper.getDialogWidth(context);
        }
        if (this.mContext.getResources().getBoolean(C1777R.bool.is_tablet)) {
            i2 = -2;
        }
        getWindow().setLayout(i, i2);
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mAttachedToWindow = false;
        this.mRouter.removeCallback(this.mCallback);
        this.mHandler.removeMessages(1);
    }
}
