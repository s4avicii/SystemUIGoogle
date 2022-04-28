package androidx.mediarouter.app;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.p000v4.media.MediaDescriptionCompat;
import android.support.p000v4.media.MediaMetadataCompat;
import android.support.p000v4.media.session.MediaControllerCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline1;
import androidx.mediarouter.media.MediaRouteProvider;
import androidx.mediarouter.media.MediaRouteSelector;
import androidx.mediarouter.media.MediaRouter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.p012wm.shell.C1777R;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class MediaRouteDynamicControllerDialog extends AppCompatDialog {
    public static final boolean DEBUG = Log.isLoggable("MediaRouteCtrlDialog", 3);
    public RecyclerAdapter mAdapter;
    public int mArtIconBackgroundColor;
    public Bitmap mArtIconBitmap;
    public boolean mArtIconIsLoaded;
    public Bitmap mArtIconLoadedBitmap;
    public Uri mArtIconUri;
    public ImageView mArtView;
    public boolean mAttachedToWindow;
    public final MediaRouterCallback mCallback;
    public ImageButton mCloseButton;
    public Context mContext;
    public MediaControllerCallback mControllerCallback;
    public boolean mCreated;
    public MediaDescriptionCompat mDescription;
    public final boolean mEnableGroupVolumeUX;
    public FetchArtTask mFetchArtTask;
    public final ArrayList mGroupableRoutes = new ArrayList();
    public final C02701 mHandler = new Handler() {
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                MediaRouteDynamicControllerDialog.this.updateRoutesView();
            } else if (i == 2) {
                MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog = MediaRouteDynamicControllerDialog.this;
                if (mediaRouteDynamicControllerDialog.mRouteForVolumeUpdatingByUser != null) {
                    mediaRouteDynamicControllerDialog.mRouteForVolumeUpdatingByUser = null;
                    mediaRouteDynamicControllerDialog.updateViewsIfNeeded();
                }
            }
        }
    };
    public boolean mIsAnimatingVolumeSliderLayout;
    public long mLastUpdateTime;
    public MediaControllerCompat mMediaController;
    public final ArrayList mMemberRoutes = new ArrayList();
    public ImageView mMetadataBackground;
    public View mMetadataBlackScrim;
    public RecyclerView mRecyclerView;
    public MediaRouter.RouteInfo mRouteForVolumeUpdatingByUser;
    public final MediaRouter mRouter;
    public MediaRouter.RouteInfo mSelectedRoute;
    public MediaRouteSelector mSelector = MediaRouteSelector.EMPTY;
    public Button mStopCastingButton;
    public TextView mSubtitleView;
    public String mTitlePlaceholder;
    public TextView mTitleView;
    public final ArrayList mTransferableRoutes = new ArrayList();
    public final ArrayList mUngroupableRoutes = new ArrayList();
    public HashMap mUnmutedVolumeMap;
    public boolean mUpdateMetadataViewsDeferred;
    public boolean mUpdateRoutesViewDeferred;
    public VolumeChangeListener mVolumeChangeListener;
    public HashMap mVolumeSliderHolderMap;

    public class FetchArtTask extends AsyncTask<Void, Void, Bitmap> {
        public int mBackgroundColor;
        public final Bitmap mIconBitmap;
        public final Uri mIconUri;

        public FetchArtTask() {
            Bitmap bitmap;
            boolean z;
            MediaDescriptionCompat mediaDescriptionCompat = MediaRouteDynamicControllerDialog.this.mDescription;
            Uri uri = null;
            if (mediaDescriptionCompat == null) {
                bitmap = null;
            } else {
                bitmap = mediaDescriptionCompat.mIcon;
            }
            if (bitmap == null || !bitmap.isRecycled()) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                Log.w("MediaRouteCtrlDialog", "Can't fetch the given art bitmap because it's already recycled.");
                bitmap = null;
            }
            this.mIconBitmap = bitmap;
            MediaDescriptionCompat mediaDescriptionCompat2 = MediaRouteDynamicControllerDialog.this.mDescription;
            this.mIconUri = mediaDescriptionCompat2 != null ? mediaDescriptionCompat2.mIconUri : uri;
        }

        /* JADX WARNING: Can't wrap try/catch for region: R(7:15|16|17|18|(2:20|(1:22)(1:70))|23|(1:25)(4:26|27|28|29)) */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0047 */
        /* JADX WARNING: Removed duplicated region for block: B:20:0x0052 A[Catch:{ IOException -> 0x009f }] */
        /* JADX WARNING: Removed duplicated region for block: B:42:0x00bb A[SYNTHETIC, Splitter:B:42:0x00bb] */
        /* JADX WARNING: Removed duplicated region for block: B:45:0x00c1 A[SYNTHETIC, Splitter:B:45:0x00c1] */
        /* JADX WARNING: Removed duplicated region for block: B:54:0x00ce  */
        /* JADX WARNING: Removed duplicated region for block: B:55:0x00d0  */
        /* JADX WARNING: Removed duplicated region for block: B:57:0x00d3  */
        /* JADX WARNING: Removed duplicated region for block: B:58:0x00e8 A[ADDED_TO_REGION] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object doInBackground(java.lang.Object[] r9) {
            /*
                r8 = this;
                java.lang.Void[] r9 = (java.lang.Void[]) r9
                java.lang.String r9 = "Unable to open: "
                android.graphics.Bitmap r0 = r8.mIconBitmap
                r1 = 1
                r2 = 0
                java.lang.String r3 = "MediaRouteCtrlDialog"
                r4 = 0
                if (r0 == 0) goto L_0x000f
                goto L_0x00c6
            L_0x000f:
                android.net.Uri r0 = r8.mIconUri
                if (r0 == 0) goto L_0x00c5
                java.io.BufferedInputStream r0 = r8.openInputStreamByScheme(r0)     // Catch:{ IOException -> 0x00a3, all -> 0x00a1 }
                if (r0 != 0) goto L_0x0030
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x009f }
                r5.<init>()     // Catch:{ IOException -> 0x009f }
                r5.append(r9)     // Catch:{ IOException -> 0x009f }
                android.net.Uri r6 = r8.mIconUri     // Catch:{ IOException -> 0x009f }
                r5.append(r6)     // Catch:{ IOException -> 0x009f }
                java.lang.String r5 = r5.toString()     // Catch:{ IOException -> 0x009f }
                android.util.Log.w(r3, r5)     // Catch:{ IOException -> 0x009f }
                if (r0 == 0) goto L_0x0120
                goto L_0x0097
            L_0x0030:
                android.graphics.BitmapFactory$Options r5 = new android.graphics.BitmapFactory$Options     // Catch:{ IOException -> 0x009f }
                r5.<init>()     // Catch:{ IOException -> 0x009f }
                r5.inJustDecodeBounds = r1     // Catch:{ IOException -> 0x009f }
                android.graphics.BitmapFactory.decodeStream(r0, r4, r5)     // Catch:{ IOException -> 0x009f }
                int r6 = r5.outWidth     // Catch:{ IOException -> 0x009f }
                if (r6 == 0) goto L_0x0097
                int r6 = r5.outHeight     // Catch:{ IOException -> 0x009f }
                if (r6 != 0) goto L_0x0043
                goto L_0x0097
            L_0x0043:
                r0.reset()     // Catch:{ IOException -> 0x0047 }
                goto L_0x0069
            L_0x0047:
                r0.close()     // Catch:{ IOException -> 0x009f }
                android.net.Uri r6 = r8.mIconUri     // Catch:{ IOException -> 0x009f }
                java.io.BufferedInputStream r0 = r8.openInputStreamByScheme(r6)     // Catch:{ IOException -> 0x009f }
                if (r0 != 0) goto L_0x0069
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x009f }
                r5.<init>()     // Catch:{ IOException -> 0x009f }
                r5.append(r9)     // Catch:{ IOException -> 0x009f }
                android.net.Uri r6 = r8.mIconUri     // Catch:{ IOException -> 0x009f }
                r5.append(r6)     // Catch:{ IOException -> 0x009f }
                java.lang.String r5 = r5.toString()     // Catch:{ IOException -> 0x009f }
                android.util.Log.w(r3, r5)     // Catch:{ IOException -> 0x009f }
                if (r0 == 0) goto L_0x0120
                goto L_0x0097
            L_0x0069:
                r5.inJustDecodeBounds = r2     // Catch:{ IOException -> 0x009f }
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog r6 = androidx.mediarouter.app.MediaRouteDynamicControllerDialog.this     // Catch:{ IOException -> 0x009f }
                android.content.Context r6 = r6.mContext     // Catch:{ IOException -> 0x009f }
                android.content.res.Resources r6 = r6.getResources()     // Catch:{ IOException -> 0x009f }
                r7 = 2131166371(0x7f0704a3, float:1.7946985E38)
                int r6 = r6.getDimensionPixelSize(r7)     // Catch:{ IOException -> 0x009f }
                int r7 = r5.outHeight     // Catch:{ IOException -> 0x009f }
                int r7 = r7 / r6
                int r6 = java.lang.Integer.highestOneBit(r7)     // Catch:{ IOException -> 0x009f }
                int r6 = java.lang.Math.max(r1, r6)     // Catch:{ IOException -> 0x009f }
                r5.inSampleSize = r6     // Catch:{ IOException -> 0x009f }
                boolean r6 = r8.isCancelled()     // Catch:{ IOException -> 0x009f }
                if (r6 == 0) goto L_0x008e
                goto L_0x0097
            L_0x008e:
                android.graphics.Bitmap r9 = android.graphics.BitmapFactory.decodeStream(r0, r4, r5)     // Catch:{ IOException -> 0x009f }
                r0.close()     // Catch:{ IOException -> 0x0095 }
            L_0x0095:
                r0 = r9
                goto L_0x00c6
            L_0x0097:
                r0.close()     // Catch:{ IOException -> 0x0120 }
                goto L_0x0120
            L_0x009c:
                r8 = move-exception
                r4 = r0
                goto L_0x00bf
            L_0x009f:
                r5 = move-exception
                goto L_0x00a5
            L_0x00a1:
                r8 = move-exception
                goto L_0x00bf
            L_0x00a3:
                r5 = move-exception
                r0 = r4
            L_0x00a5:
                java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x009c }
                r6.<init>()     // Catch:{ all -> 0x009c }
                r6.append(r9)     // Catch:{ all -> 0x009c }
                android.net.Uri r9 = r8.mIconUri     // Catch:{ all -> 0x009c }
                r6.append(r9)     // Catch:{ all -> 0x009c }
                java.lang.String r9 = r6.toString()     // Catch:{ all -> 0x009c }
                android.util.Log.w(r3, r9, r5)     // Catch:{ all -> 0x009c }
                if (r0 == 0) goto L_0x00c5
                r0.close()     // Catch:{ IOException -> 0x00c5 }
                goto L_0x00c5
            L_0x00bf:
                if (r4 == 0) goto L_0x00c4
                r4.close()     // Catch:{ IOException -> 0x00c4 }
            L_0x00c4:
                throw r8
            L_0x00c5:
                r0 = r4
            L_0x00c6:
                if (r0 == 0) goto L_0x00d0
                boolean r9 = r0.isRecycled()
                if (r9 == 0) goto L_0x00d0
                r9 = r1
                goto L_0x00d1
            L_0x00d0:
                r9 = r2
            L_0x00d1:
                if (r9 == 0) goto L_0x00e8
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>()
                java.lang.String r9 = "Can't use recycled bitmap: "
                r8.append(r9)
                r8.append(r0)
                java.lang.String r8 = r8.toString()
                android.util.Log.w(r3, r8)
                goto L_0x0120
            L_0x00e8:
                if (r0 == 0) goto L_0x011f
                int r9 = r0.getWidth()
                int r3 = r0.getHeight()
                if (r9 >= r3) goto L_0x011f
                androidx.palette.graphics.Palette$Builder r9 = new androidx.palette.graphics.Palette$Builder
                r9.<init>(r0)
                r9.mMaxColors = r1
                androidx.palette.graphics.Palette r9 = r9.generate()
                java.util.List<androidx.palette.graphics.Palette$Swatch> r1 = r9.mSwatches
                java.util.List r1 = java.util.Collections.unmodifiableList(r1)
                boolean r1 = r1.isEmpty()
                if (r1 == 0) goto L_0x010c
                goto L_0x011d
            L_0x010c:
                java.util.List<androidx.palette.graphics.Palette$Swatch> r9 = r9.mSwatches
                java.util.List r9 = java.util.Collections.unmodifiableList(r9)
                java.lang.Object r9 = r9.get(r2)
                androidx.palette.graphics.Palette$Swatch r9 = (androidx.palette.graphics.Palette.Swatch) r9
                java.util.Objects.requireNonNull(r9)
                int r2 = r9.mRgb
            L_0x011d:
                r8.mBackgroundColor = r2
            L_0x011f:
                r4 = r0
            L_0x0120:
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.mediarouter.app.MediaRouteDynamicControllerDialog.FetchArtTask.doInBackground(java.lang.Object[]):java.lang.Object");
        }

        public final void onPostExecute(Object obj) {
            Bitmap bitmap = (Bitmap) obj;
            MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog = MediaRouteDynamicControllerDialog.this;
            mediaRouteDynamicControllerDialog.mFetchArtTask = null;
            if (!Objects.equals(mediaRouteDynamicControllerDialog.mArtIconBitmap, this.mIconBitmap) || !Objects.equals(MediaRouteDynamicControllerDialog.this.mArtIconUri, this.mIconUri)) {
                MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog2 = MediaRouteDynamicControllerDialog.this;
                mediaRouteDynamicControllerDialog2.mArtIconBitmap = this.mIconBitmap;
                mediaRouteDynamicControllerDialog2.mArtIconLoadedBitmap = bitmap;
                mediaRouteDynamicControllerDialog2.mArtIconUri = this.mIconUri;
                mediaRouteDynamicControllerDialog2.mArtIconBackgroundColor = this.mBackgroundColor;
                mediaRouteDynamicControllerDialog2.mArtIconIsLoaded = true;
                mediaRouteDynamicControllerDialog2.updateMetadataViews();
            }
        }

        public final void onPreExecute() {
            MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog = MediaRouteDynamicControllerDialog.this;
            Objects.requireNonNull(mediaRouteDynamicControllerDialog);
            mediaRouteDynamicControllerDialog.mArtIconIsLoaded = false;
            mediaRouteDynamicControllerDialog.mArtIconLoadedBitmap = null;
            mediaRouteDynamicControllerDialog.mArtIconBackgroundColor = 0;
        }

        public final BufferedInputStream openInputStreamByScheme(Uri uri) throws IOException {
            InputStream inputStream;
            String lowerCase = uri.getScheme().toLowerCase();
            if ("android.resource".equals(lowerCase) || "content".equals(lowerCase) || "file".equals(lowerCase)) {
                inputStream = MediaRouteDynamicControllerDialog.this.mContext.getContentResolver().openInputStream(uri);
            } else {
                URLConnection openConnection = new URL(uri.toString()).openConnection();
                openConnection.setConnectTimeout(30000);
                openConnection.setReadTimeout(30000);
                inputStream = openConnection.getInputStream();
            }
            if (inputStream == null) {
                return null;
            }
            return new BufferedInputStream(inputStream);
        }
    }

    public final class MediaControllerCallback extends MediaControllerCompat.Callback {
        public MediaControllerCallback() {
        }

        public final void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) {
            MediaDescriptionCompat mediaDescriptionCompat;
            MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog = MediaRouteDynamicControllerDialog.this;
            if (mediaMetadataCompat == null) {
                mediaDescriptionCompat = null;
            } else {
                mediaDescriptionCompat = mediaMetadataCompat.getDescription();
            }
            mediaRouteDynamicControllerDialog.mDescription = mediaDescriptionCompat;
            MediaRouteDynamicControllerDialog.this.reloadIconIfNeeded();
            MediaRouteDynamicControllerDialog.this.updateMetadataViews();
        }

        public final void onSessionDestroyed() {
            MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog = MediaRouteDynamicControllerDialog.this;
            MediaControllerCompat mediaControllerCompat = mediaRouteDynamicControllerDialog.mMediaController;
            if (mediaControllerCompat != null) {
                mediaControllerCompat.unregisterCallback(mediaRouteDynamicControllerDialog.mControllerCallback);
                MediaRouteDynamicControllerDialog.this.mMediaController = null;
            }
        }
    }

    public abstract class MediaRouteVolumeSliderHolder extends RecyclerView.ViewHolder {
        public final ImageButton mMuteButton;
        public MediaRouter.RouteInfo mRoute;
        public final MediaRouteVolumeSlider mVolumeSlider;

        public MediaRouteVolumeSliderHolder(View view, ImageButton imageButton, MediaRouteVolumeSlider mediaRouteVolumeSlider) {
            super(view);
            int i;
            int i2;
            this.mMuteButton = imageButton;
            this.mVolumeSlider = mediaRouteVolumeSlider;
            Context context = MediaRouteDynamicControllerDialog.this.mContext;
            Drawable drawable = AppCompatResources.getDrawable(context, C1777R.C1778drawable.mr_cast_mute_button);
            if (MediaRouterThemeHelper.isLightTheme(context)) {
                Object obj = ContextCompat.sLock;
                drawable.setTint(context.getColor(C1777R.color.mr_dynamic_dialog_icon_light));
            }
            imageButton.setImageDrawable(drawable);
            Context context2 = MediaRouteDynamicControllerDialog.this.mContext;
            if (MediaRouterThemeHelper.isLightTheme(context2)) {
                Object obj2 = ContextCompat.sLock;
                i = context2.getColor(C1777R.color.mr_cast_progressbar_progress_and_thumb_light);
                i2 = context2.getColor(C1777R.color.mr_cast_progressbar_background_light);
            } else {
                Object obj3 = ContextCompat.sLock;
                i = context2.getColor(C1777R.color.mr_cast_progressbar_progress_and_thumb_dark);
                i2 = context2.getColor(C1777R.color.mr_cast_progressbar_background_dark);
            }
            mediaRouteVolumeSlider.setColor(i, i2);
        }

        public final void bindRouteVolumeSliderHolder(MediaRouter.RouteInfo routeInfo) {
            boolean z;
            this.mRoute = routeInfo;
            Objects.requireNonNull(routeInfo);
            int i = routeInfo.mVolume;
            if (i == 0) {
                z = true;
            } else {
                z = false;
            }
            this.mMuteButton.setActivated(z);
            this.mMuteButton.setOnClickListener(new View.OnClickListener() {
                public final void onClick(View view) {
                    MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog = MediaRouteDynamicControllerDialog.this;
                    if (mediaRouteDynamicControllerDialog.mRouteForVolumeUpdatingByUser != null) {
                        mediaRouteDynamicControllerDialog.mHandler.removeMessages(2);
                    }
                    MediaRouteVolumeSliderHolder mediaRouteVolumeSliderHolder = MediaRouteVolumeSliderHolder.this;
                    MediaRouteDynamicControllerDialog.this.mRouteForVolumeUpdatingByUser = mediaRouteVolumeSliderHolder.mRoute;
                    int i = 1;
                    boolean z = !view.isActivated();
                    if (z) {
                        i = 0;
                    } else {
                        MediaRouteVolumeSliderHolder mediaRouteVolumeSliderHolder2 = MediaRouteVolumeSliderHolder.this;
                        Objects.requireNonNull(mediaRouteVolumeSliderHolder2);
                        HashMap hashMap = MediaRouteDynamicControllerDialog.this.mUnmutedVolumeMap;
                        MediaRouter.RouteInfo routeInfo = mediaRouteVolumeSliderHolder2.mRoute;
                        Objects.requireNonNull(routeInfo);
                        Integer num = (Integer) hashMap.get(routeInfo.mUniqueId);
                        if (num != null) {
                            i = Math.max(1, num.intValue());
                        }
                    }
                    MediaRouteVolumeSliderHolder.this.setMute(z);
                    MediaRouteVolumeSliderHolder.this.mVolumeSlider.setProgress(i);
                    MediaRouteVolumeSliderHolder.this.mRoute.requestSetVolume(i);
                    MediaRouteDynamicControllerDialog.this.mHandler.sendEmptyMessageDelayed(2, 500);
                }
            });
            this.mVolumeSlider.setTag(this.mRoute);
            MediaRouteVolumeSlider mediaRouteVolumeSlider = this.mVolumeSlider;
            Objects.requireNonNull(routeInfo);
            mediaRouteVolumeSlider.setMax(routeInfo.mVolumeMax);
            this.mVolumeSlider.setProgress(i);
            this.mVolumeSlider.setOnSeekBarChangeListener(MediaRouteDynamicControllerDialog.this.mVolumeChangeListener);
        }

        public final void setMute(boolean z) {
            if (this.mMuteButton.isActivated() != z) {
                this.mMuteButton.setActivated(z);
                if (z) {
                    HashMap hashMap = MediaRouteDynamicControllerDialog.this.mUnmutedVolumeMap;
                    MediaRouter.RouteInfo routeInfo = this.mRoute;
                    Objects.requireNonNull(routeInfo);
                    hashMap.put(routeInfo.mUniqueId, Integer.valueOf(this.mVolumeSlider.getProgress()));
                    return;
                }
                HashMap hashMap2 = MediaRouteDynamicControllerDialog.this.mUnmutedVolumeMap;
                MediaRouter.RouteInfo routeInfo2 = this.mRoute;
                Objects.requireNonNull(routeInfo2);
                hashMap2.remove(routeInfo2.mUniqueId);
            }
        }
    }

    public final class MediaRouterCallback extends MediaRouter.Callback {
        public MediaRouterCallback() {
        }

        public final void onRouteAdded() {
            MediaRouteDynamicControllerDialog.this.updateRoutesView();
        }

        /* JADX WARNING: Removed duplicated region for block: B:18:0x0058  */
        /* JADX WARNING: Removed duplicated region for block: B:19:0x0063  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onRouteChanged(androidx.mediarouter.media.MediaRouter.RouteInfo r3) {
            /*
                r2 = this;
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog r0 = androidx.mediarouter.app.MediaRouteDynamicControllerDialog.this
                androidx.mediarouter.media.MediaRouter$RouteInfo r0 = r0.mSelectedRoute
                if (r3 != r0) goto L_0x0055
                androidx.mediarouter.media.MediaRouteProvider$DynamicGroupRouteController r0 = androidx.mediarouter.media.MediaRouter.RouteInfo.getDynamicGroupController()
                if (r0 == 0) goto L_0x0055
                androidx.mediarouter.media.MediaRouter$ProviderInfo r3 = r3.mProvider
                java.util.Objects.requireNonNull(r3)
                androidx.mediarouter.media.MediaRouter.checkCallingThread()
                java.util.ArrayList r3 = r3.mRoutes
                java.util.List r3 = java.util.Collections.unmodifiableList(r3)
                java.util.Iterator r3 = r3.iterator()
            L_0x001e:
                boolean r0 = r3.hasNext()
                if (r0 == 0) goto L_0x0055
                java.lang.Object r0 = r3.next()
                androidx.mediarouter.media.MediaRouter$RouteInfo r0 = (androidx.mediarouter.media.MediaRouter.RouteInfo) r0
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog r1 = androidx.mediarouter.app.MediaRouteDynamicControllerDialog.this
                androidx.mediarouter.media.MediaRouter$RouteInfo r1 = r1.mSelectedRoute
                java.util.List r1 = r1.getMemberRoutes()
                boolean r1 = r1.contains(r0)
                if (r1 == 0) goto L_0x0039
                goto L_0x001e
            L_0x0039:
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog r1 = androidx.mediarouter.app.MediaRouteDynamicControllerDialog.this
                androidx.mediarouter.media.MediaRouter$RouteInfo r1 = r1.mSelectedRoute
                androidx.mediarouter.media.MediaRouter$RouteInfo$DynamicGroupState r1 = r1.getDynamicGroupState(r0)
                if (r1 == 0) goto L_0x001e
                boolean r1 = r1.isGroupable()
                if (r1 == 0) goto L_0x001e
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog r1 = androidx.mediarouter.app.MediaRouteDynamicControllerDialog.this
                java.util.ArrayList r1 = r1.mGroupableRoutes
                boolean r0 = r1.contains(r0)
                if (r0 != 0) goto L_0x001e
                r3 = 1
                goto L_0x0056
            L_0x0055:
                r3 = 0
            L_0x0056:
                if (r3 == 0) goto L_0x0063
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog r3 = androidx.mediarouter.app.MediaRouteDynamicControllerDialog.this
                r3.updateViewsIfNeeded()
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog r2 = androidx.mediarouter.app.MediaRouteDynamicControllerDialog.this
                r2.updateRoutes()
                goto L_0x0068
            L_0x0063:
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog r2 = androidx.mediarouter.app.MediaRouteDynamicControllerDialog.this
                r2.updateRoutesView()
            L_0x0068:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.mediarouter.app.MediaRouteDynamicControllerDialog.MediaRouterCallback.onRouteChanged(androidx.mediarouter.media.MediaRouter$RouteInfo):void");
        }

        public final void onRouteRemoved() {
            MediaRouteDynamicControllerDialog.this.updateRoutesView();
        }

        public final void onRouteSelected(MediaRouter.RouteInfo routeInfo) {
            MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog = MediaRouteDynamicControllerDialog.this;
            mediaRouteDynamicControllerDialog.mSelectedRoute = routeInfo;
            mediaRouteDynamicControllerDialog.updateViewsIfNeeded();
            MediaRouteDynamicControllerDialog.this.updateRoutes();
        }

        public final void onRouteUnselected() {
            MediaRouteDynamicControllerDialog.this.updateRoutesView();
        }

        public final void onRouteVolumeChanged(MediaRouter.RouteInfo routeInfo) {
            MediaRouteVolumeSliderHolder mediaRouteVolumeSliderHolder;
            boolean z;
            int i = routeInfo.mVolume;
            if (MediaRouteDynamicControllerDialog.DEBUG) {
                ExifInterface$$ExternalSyntheticOutline1.m14m("onRouteVolumeChanged(), route.getVolume:", i, "MediaRouteCtrlDialog");
            }
            MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog = MediaRouteDynamicControllerDialog.this;
            if (mediaRouteDynamicControllerDialog.mRouteForVolumeUpdatingByUser != routeInfo && (mediaRouteVolumeSliderHolder = (MediaRouteVolumeSliderHolder) mediaRouteDynamicControllerDialog.mVolumeSliderHolderMap.get(routeInfo.mUniqueId)) != null) {
                MediaRouter.RouteInfo routeInfo2 = mediaRouteVolumeSliderHolder.mRoute;
                Objects.requireNonNull(routeInfo2);
                int i2 = routeInfo2.mVolume;
                if (i2 == 0) {
                    z = true;
                } else {
                    z = false;
                }
                mediaRouteVolumeSliderHolder.setMute(z);
                mediaRouteVolumeSliderHolder.mVolumeSlider.setProgress(i2);
            }
        }
    }

    public final class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        public final AccelerateDecelerateInterpolator mAccelerateDecelerateInterpolator;
        public final Drawable mDefaultIcon;
        public Item mGroupVolumeItem;
        public final LayoutInflater mInflater;
        public final ArrayList<Item> mItems = new ArrayList<>();
        public final int mLayoutAnimationDurationMs;
        public final Drawable mSpeakerGroupIcon;
        public final Drawable mSpeakerIcon;
        public final Drawable mTvIcon;

        public class GroupViewHolder extends RecyclerView.ViewHolder {
            public final float mDisabledAlpha;
            public final ImageView mImageView;
            public final View mItemView;
            public final ProgressBar mProgressBar;
            public MediaRouter.RouteInfo mRoute;
            public final TextView mTextView;

            public GroupViewHolder(View view) {
                super(view);
                this.mItemView = view;
                this.mImageView = (ImageView) view.findViewById(C1777R.C1779id.mr_cast_group_icon);
                ProgressBar progressBar = (ProgressBar) view.findViewById(C1777R.C1779id.mr_cast_group_progress_bar);
                this.mProgressBar = progressBar;
                this.mTextView = (TextView) view.findViewById(C1777R.C1779id.mr_cast_group_name);
                this.mDisabledAlpha = MediaRouterThemeHelper.getDisabledAlpha(MediaRouteDynamicControllerDialog.this.mContext);
                MediaRouterThemeHelper.setIndeterminateProgressBarColor(MediaRouteDynamicControllerDialog.this.mContext, progressBar);
            }
        }

        public class GroupVolumeViewHolder extends MediaRouteVolumeSliderHolder {
            public final int mExpandedHeight;
            public final TextView mTextView;

            public GroupVolumeViewHolder(View view) {
                super(view, (ImageButton) view.findViewById(C1777R.C1779id.mr_cast_mute_button), (MediaRouteVolumeSlider) view.findViewById(C1777R.C1779id.mr_cast_volume_slider));
                this.mTextView = (TextView) view.findViewById(C1777R.C1779id.mr_group_volume_route_name);
                Resources resources = MediaRouteDynamicControllerDialog.this.mContext.getResources();
                DisplayMetrics displayMetrics = resources.getDisplayMetrics();
                TypedValue typedValue = new TypedValue();
                resources.getValue(C1777R.dimen.mr_dynamic_volume_group_list_item_height, typedValue, true);
                this.mExpandedHeight = (int) typedValue.getDimension(displayMetrics);
            }
        }

        public class RouteViewHolder extends MediaRouteVolumeSliderHolder {
            public final CheckBox mCheckBox;
            public final float mDisabledAlpha;
            public final int mExpandedLayoutHeight;
            public final ImageView mImageView;
            public final View mItemView;
            public final ProgressBar mProgressBar;
            public final TextView mTextView;
            public final C02771 mViewClickListener = new View.OnClickListener() {
                public final void onClick(View view) {
                    boolean z;
                    int i;
                    boolean z2;
                    RouteViewHolder routeViewHolder = RouteViewHolder.this;
                    boolean z3 = true;
                    boolean z4 = !routeViewHolder.isSelected(routeViewHolder.mRoute);
                    boolean isGroup = RouteViewHolder.this.mRoute.isGroup();
                    int i2 = 0;
                    if (z4) {
                        RouteViewHolder routeViewHolder2 = RouteViewHolder.this;
                        MediaRouter mediaRouter = MediaRouteDynamicControllerDialog.this.mRouter;
                        MediaRouter.RouteInfo routeInfo = routeViewHolder2.mRoute;
                        Objects.requireNonNull(mediaRouter);
                        Objects.requireNonNull(routeInfo, "route must not be null");
                        MediaRouter.checkCallingThread();
                        MediaRouter.GlobalMediaRouter globalRouter = MediaRouter.getGlobalRouter();
                        Objects.requireNonNull(globalRouter);
                        if (globalRouter.mSelectedRouteController instanceof MediaRouteProvider.DynamicGroupRouteController) {
                            MediaRouter.RouteInfo.DynamicGroupState dynamicGroupState = globalRouter.mSelectedRoute.getDynamicGroupState(routeInfo);
                            if (globalRouter.mSelectedRoute.getMemberRoutes().contains(routeInfo) || dynamicGroupState == null || !dynamicGroupState.isGroupable()) {
                                Log.w("MediaRouter", "Ignoring attempt to add a non-groupable route to dynamic group : " + routeInfo);
                            } else {
                                ((MediaRouteProvider.DynamicGroupRouteController) globalRouter.mSelectedRouteController).onAddMemberRoute(routeInfo.mDescriptorId);
                            }
                        } else {
                            throw new IllegalStateException("There is no currently selected dynamic group route.");
                        }
                    } else {
                        RouteViewHolder routeViewHolder3 = RouteViewHolder.this;
                        MediaRouter mediaRouter2 = MediaRouteDynamicControllerDialog.this.mRouter;
                        MediaRouter.RouteInfo routeInfo2 = routeViewHolder3.mRoute;
                        Objects.requireNonNull(mediaRouter2);
                        Objects.requireNonNull(routeInfo2, "route must not be null");
                        MediaRouter.checkCallingThread();
                        MediaRouter.GlobalMediaRouter globalRouter2 = MediaRouter.getGlobalRouter();
                        Objects.requireNonNull(globalRouter2);
                        if (globalRouter2.mSelectedRouteController instanceof MediaRouteProvider.DynamicGroupRouteController) {
                            MediaRouter.RouteInfo.DynamicGroupState dynamicGroupState2 = globalRouter2.mSelectedRoute.getDynamicGroupState(routeInfo2);
                            if (globalRouter2.mSelectedRoute.getMemberRoutes().contains(routeInfo2) && dynamicGroupState2 != null) {
                                MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor dynamicRouteDescriptor = dynamicGroupState2.mDynamicDescriptor;
                                if (dynamicRouteDescriptor == null || dynamicRouteDescriptor.mIsUnselectable) {
                                    z2 = true;
                                } else {
                                    z2 = false;
                                }
                                if (z2) {
                                    if (globalRouter2.mSelectedRoute.getMemberRoutes().size() <= 1) {
                                        Log.w("MediaRouter", "Ignoring attempt to remove the last member route.");
                                    } else {
                                        ((MediaRouteProvider.DynamicGroupRouteController) globalRouter2.mSelectedRouteController).onRemoveMemberRoute(routeInfo2.mDescriptorId);
                                    }
                                }
                            }
                            Log.w("MediaRouter", "Ignoring attempt to remove a non-unselectable member route : " + routeInfo2);
                        } else {
                            throw new IllegalStateException("There is no currently selected dynamic group route.");
                        }
                    }
                    RouteViewHolder.this.showSelectingProgress(z4, !isGroup);
                    if (isGroup) {
                        List<MediaRouter.RouteInfo> memberRoutes = MediaRouteDynamicControllerDialog.this.mSelectedRoute.getMemberRoutes();
                        for (MediaRouter.RouteInfo next : RouteViewHolder.this.mRoute.getMemberRoutes()) {
                            if (memberRoutes.contains(next) != z4) {
                                HashMap hashMap = MediaRouteDynamicControllerDialog.this.mVolumeSliderHolderMap;
                                Objects.requireNonNull(next);
                                MediaRouteVolumeSliderHolder mediaRouteVolumeSliderHolder = (MediaRouteVolumeSliderHolder) hashMap.get(next.mUniqueId);
                                if (mediaRouteVolumeSliderHolder instanceof RouteViewHolder) {
                                    ((RouteViewHolder) mediaRouteVolumeSliderHolder).showSelectingProgress(z4, true);
                                }
                            }
                        }
                    }
                    RouteViewHolder routeViewHolder4 = RouteViewHolder.this;
                    RecyclerAdapter recyclerAdapter = RecyclerAdapter.this;
                    MediaRouter.RouteInfo routeInfo3 = routeViewHolder4.mRoute;
                    Objects.requireNonNull(recyclerAdapter);
                    List<MediaRouter.RouteInfo> memberRoutes2 = MediaRouteDynamicControllerDialog.this.mSelectedRoute.getMemberRoutes();
                    int max = Math.max(1, memberRoutes2.size());
                    int i3 = -1;
                    if (routeInfo3.isGroup()) {
                        for (MediaRouter.RouteInfo contains : routeInfo3.getMemberRoutes()) {
                            if (memberRoutes2.contains(contains) != z4) {
                                if (z4) {
                                    i = 1;
                                } else {
                                    i = -1;
                                }
                                max += i;
                            }
                        }
                    } else {
                        if (z4) {
                            i3 = 1;
                        }
                        max += i3;
                    }
                    MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog = MediaRouteDynamicControllerDialog.this;
                    if (!mediaRouteDynamicControllerDialog.mEnableGroupVolumeUX || mediaRouteDynamicControllerDialog.mSelectedRoute.getMemberRoutes().size() <= 1) {
                        z = false;
                    } else {
                        z = true;
                    }
                    MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog2 = MediaRouteDynamicControllerDialog.this;
                    if (!mediaRouteDynamicControllerDialog2.mEnableGroupVolumeUX || max < 2) {
                        z3 = false;
                    }
                    if (z != z3) {
                        RecyclerView.ViewHolder findViewHolderForAdapterPosition = mediaRouteDynamicControllerDialog2.mRecyclerView.findViewHolderForAdapterPosition(0);
                        if (findViewHolderForAdapterPosition instanceof GroupVolumeViewHolder) {
                            GroupVolumeViewHolder groupVolumeViewHolder = (GroupVolumeViewHolder) findViewHolderForAdapterPosition;
                            View view2 = groupVolumeViewHolder.itemView;
                            if (z3) {
                                i2 = groupVolumeViewHolder.mExpandedHeight;
                            }
                            recyclerAdapter.animateLayoutHeight(view2, i2);
                        }
                    }
                }
            };
            public final RelativeLayout mVolumeSliderLayout;

            public RouteViewHolder(View view) {
                super(view, (ImageButton) view.findViewById(C1777R.C1779id.mr_cast_mute_button), (MediaRouteVolumeSlider) view.findViewById(C1777R.C1779id.mr_cast_volume_slider));
                this.mItemView = view;
                this.mImageView = (ImageView) view.findViewById(C1777R.C1779id.mr_cast_route_icon);
                ProgressBar progressBar = (ProgressBar) view.findViewById(C1777R.C1779id.mr_cast_route_progress_bar);
                this.mProgressBar = progressBar;
                this.mTextView = (TextView) view.findViewById(C1777R.C1779id.mr_cast_route_name);
                this.mVolumeSliderLayout = (RelativeLayout) view.findViewById(C1777R.C1779id.mr_cast_volume_layout);
                CheckBox checkBox = (CheckBox) view.findViewById(C1777R.C1779id.mr_cast_checkbox);
                this.mCheckBox = checkBox;
                Context context = MediaRouteDynamicControllerDialog.this.mContext;
                Drawable drawable = AppCompatResources.getDrawable(context, C1777R.C1778drawable.mr_cast_checkbox);
                if (MediaRouterThemeHelper.isLightTheme(context)) {
                    Object obj = ContextCompat.sLock;
                    drawable.setTint(context.getColor(C1777R.color.mr_dynamic_dialog_icon_light));
                }
                checkBox.setButtonDrawable(drawable);
                MediaRouterThemeHelper.setIndeterminateProgressBarColor(MediaRouteDynamicControllerDialog.this.mContext, progressBar);
                this.mDisabledAlpha = MediaRouterThemeHelper.getDisabledAlpha(MediaRouteDynamicControllerDialog.this.mContext);
                Resources resources = MediaRouteDynamicControllerDialog.this.mContext.getResources();
                DisplayMetrics displayMetrics = resources.getDisplayMetrics();
                TypedValue typedValue = new TypedValue();
                resources.getValue(C1777R.dimen.mr_dynamic_dialog_row_height, typedValue, true);
                this.mExpandedLayoutHeight = (int) typedValue.getDimension(displayMetrics);
            }

            public final void showSelectingProgress(boolean z, boolean z2) {
                int i = 0;
                this.mCheckBox.setEnabled(false);
                this.mItemView.setEnabled(false);
                this.mCheckBox.setChecked(z);
                if (z) {
                    this.mImageView.setVisibility(4);
                    this.mProgressBar.setVisibility(0);
                }
                if (z2) {
                    RecyclerAdapter recyclerAdapter = RecyclerAdapter.this;
                    RelativeLayout relativeLayout = this.mVolumeSliderLayout;
                    if (z) {
                        i = this.mExpandedLayoutHeight;
                    }
                    recyclerAdapter.animateLayoutHeight(relativeLayout, i);
                }
            }

            public final boolean isSelected(MediaRouter.RouteInfo routeInfo) {
                int i;
                if (routeInfo.isSelected()) {
                    return true;
                }
                MediaRouter.RouteInfo.DynamicGroupState dynamicGroupState = MediaRouteDynamicControllerDialog.this.mSelectedRoute.getDynamicGroupState(routeInfo);
                if (dynamicGroupState != null) {
                    MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor dynamicRouteDescriptor = dynamicGroupState.mDynamicDescriptor;
                    if (dynamicRouteDescriptor != null) {
                        i = dynamicRouteDescriptor.mSelectionState;
                    } else {
                        i = 1;
                    }
                    if (i == 3) {
                        return true;
                    }
                }
                return false;
            }
        }

        public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
            if (i == 1) {
                return new GroupVolumeViewHolder(this.mInflater.inflate(C1777R.layout.mr_cast_group_volume_item, recyclerView, false));
            }
            if (i == 2) {
                return new HeaderViewHolder(this.mInflater.inflate(C1777R.layout.mr_cast_header_item, recyclerView, false));
            }
            if (i == 3) {
                return new RouteViewHolder(this.mInflater.inflate(C1777R.layout.mr_cast_route_item, recyclerView, false));
            }
            if (i == 4) {
                return new GroupViewHolder(this.mInflater.inflate(C1777R.layout.mr_cast_group_item, recyclerView, false));
            }
            Log.w("MediaRouteCtrlDialog", "Cannot create ViewHolder because of wrong view type");
            return null;
        }

        public class HeaderViewHolder extends RecyclerView.ViewHolder {
            public final TextView mTextView;

            public HeaderViewHolder(View view) {
                super(view);
                this.mTextView = (TextView) view.findViewById(C1777R.C1779id.mr_cast_header_name);
            }
        }

        public class Item {
            public final Object mData;
            public final int mType;

            public Item(Object obj, int i) {
                this.mData = obj;
                this.mType = i;
            }
        }

        public RecyclerAdapter() {
            this.mInflater = LayoutInflater.from(MediaRouteDynamicControllerDialog.this.mContext);
            this.mDefaultIcon = MediaRouterThemeHelper.getIconByAttrId(MediaRouteDynamicControllerDialog.this.mContext, C1777R.attr.mediaRouteDefaultIconDrawable);
            this.mTvIcon = MediaRouterThemeHelper.getIconByAttrId(MediaRouteDynamicControllerDialog.this.mContext, C1777R.attr.mediaRouteTvIconDrawable);
            this.mSpeakerIcon = MediaRouterThemeHelper.getIconByAttrId(MediaRouteDynamicControllerDialog.this.mContext, C1777R.attr.mediaRouteSpeakerIconDrawable);
            this.mSpeakerGroupIcon = MediaRouterThemeHelper.getIconByAttrId(MediaRouteDynamicControllerDialog.this.mContext, C1777R.attr.mediaRouteSpeakerGroupIconDrawable);
            this.mLayoutAnimationDurationMs = MediaRouteDynamicControllerDialog.this.mContext.getResources().getInteger(C1777R.integer.mr_cast_volume_slider_layout_animation_duration_ms);
            this.mAccelerateDecelerateInterpolator = new AccelerateDecelerateInterpolator();
            updateItems();
        }

        public final int getItemCount() {
            return this.mItems.size() + 1;
        }

        public final int getItemViewType(int i) {
            Item item;
            if (i == 0) {
                item = this.mGroupVolumeItem;
            } else {
                item = this.mItems.get(i - 1);
            }
            Objects.requireNonNull(item);
            return item.mType;
        }

        public final void notifyAdapterDataSetChanged() {
            MediaRouteDynamicControllerDialog.this.mUngroupableRoutes.clear();
            MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog = MediaRouteDynamicControllerDialog.this;
            ArrayList arrayList = mediaRouteDynamicControllerDialog.mUngroupableRoutes;
            ArrayList arrayList2 = mediaRouteDynamicControllerDialog.mGroupableRoutes;
            ArrayList arrayList3 = new ArrayList();
            MediaRouter.RouteInfo routeInfo = mediaRouteDynamicControllerDialog.mSelectedRoute;
            Objects.requireNonNull(routeInfo);
            MediaRouter.ProviderInfo providerInfo = routeInfo.mProvider;
            Objects.requireNonNull(providerInfo);
            MediaRouter.checkCallingThread();
            for (MediaRouter.RouteInfo routeInfo2 : Collections.unmodifiableList(providerInfo.mRoutes)) {
                MediaRouter.RouteInfo.DynamicGroupState dynamicGroupState = mediaRouteDynamicControllerDialog.mSelectedRoute.getDynamicGroupState(routeInfo2);
                if (dynamicGroupState != null && dynamicGroupState.isGroupable()) {
                    arrayList3.add(routeInfo2);
                }
            }
            HashSet hashSet = new HashSet(arrayList2);
            hashSet.removeAll(arrayList3);
            arrayList.addAll(hashSet);
            notifyDataSetChanged();
        }

        public final void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
            MediaRouteDynamicControllerDialog.this.mVolumeSliderHolderMap.values().remove(viewHolder);
        }

        public final void updateItems() {
            String str;
            String str2;
            this.mItems.clear();
            MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog = MediaRouteDynamicControllerDialog.this;
            this.mGroupVolumeItem = new Item(mediaRouteDynamicControllerDialog.mSelectedRoute, 1);
            if (!mediaRouteDynamicControllerDialog.mMemberRoutes.isEmpty()) {
                Iterator it = MediaRouteDynamicControllerDialog.this.mMemberRoutes.iterator();
                while (it.hasNext()) {
                    this.mItems.add(new Item((MediaRouter.RouteInfo) it.next(), 3));
                }
            } else {
                this.mItems.add(new Item(MediaRouteDynamicControllerDialog.this.mSelectedRoute, 3));
            }
            boolean z = false;
            if (!MediaRouteDynamicControllerDialog.this.mGroupableRoutes.isEmpty()) {
                Iterator it2 = MediaRouteDynamicControllerDialog.this.mGroupableRoutes.iterator();
                boolean z2 = false;
                while (it2.hasNext()) {
                    MediaRouter.RouteInfo routeInfo = (MediaRouter.RouteInfo) it2.next();
                    if (!MediaRouteDynamicControllerDialog.this.mMemberRoutes.contains(routeInfo)) {
                        if (!z2) {
                            Objects.requireNonNull(MediaRouteDynamicControllerDialog.this.mSelectedRoute);
                            MediaRouteProvider.DynamicGroupRouteController dynamicGroupController = MediaRouter.RouteInfo.getDynamicGroupController();
                            if (dynamicGroupController != null) {
                                str2 = dynamicGroupController.getGroupableSelectionTitle();
                            } else {
                                str2 = null;
                            }
                            if (TextUtils.isEmpty(str2)) {
                                str2 = MediaRouteDynamicControllerDialog.this.mContext.getString(C1777R.string.mr_dialog_groupable_header);
                            }
                            this.mItems.add(new Item(str2, 2));
                            z2 = true;
                        }
                        this.mItems.add(new Item(routeInfo, 3));
                    }
                }
            }
            if (!MediaRouteDynamicControllerDialog.this.mTransferableRoutes.isEmpty()) {
                Iterator it3 = MediaRouteDynamicControllerDialog.this.mTransferableRoutes.iterator();
                while (it3.hasNext()) {
                    MediaRouter.RouteInfo routeInfo2 = (MediaRouter.RouteInfo) it3.next();
                    MediaRouter.RouteInfo routeInfo3 = MediaRouteDynamicControllerDialog.this.mSelectedRoute;
                    if (routeInfo3 != routeInfo2) {
                        if (!z) {
                            Objects.requireNonNull(routeInfo3);
                            MediaRouteProvider.DynamicGroupRouteController dynamicGroupController2 = MediaRouter.RouteInfo.getDynamicGroupController();
                            if (dynamicGroupController2 != null) {
                                str = dynamicGroupController2.getTransferableSectionTitle();
                            } else {
                                str = null;
                            }
                            if (TextUtils.isEmpty(str)) {
                                str = MediaRouteDynamicControllerDialog.this.mContext.getString(C1777R.string.mr_dialog_transferable_header);
                            }
                            this.mItems.add(new Item(str, 2));
                            z = true;
                        }
                        this.mItems.add(new Item(routeInfo2, 4));
                    }
                }
            }
            notifyAdapterDataSetChanged();
        }

        public final void animateLayoutHeight(final View view, final int i) {
            final int i2 = view.getLayoutParams().height;
            C02741 r1 = new Animation() {
                public final void applyTransformation(float f, Transformation transformation) {
                    int i = i;
                    int i2 = i2;
                    View view = view;
                    int i3 = i2 + ((int) (((float) (i - i2)) * f));
                    boolean z = MediaRouteDynamicControllerDialog.DEBUG;
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    layoutParams.height = i3;
                    view.setLayoutParams(layoutParams);
                }
            };
            r1.setAnimationListener(new Animation.AnimationListener() {
                public final void onAnimationRepeat(Animation animation) {
                }

                public final void onAnimationEnd(Animation animation) {
                    MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog = MediaRouteDynamicControllerDialog.this;
                    mediaRouteDynamicControllerDialog.mIsAnimatingVolumeSliderLayout = false;
                    mediaRouteDynamicControllerDialog.updateViewsIfNeeded();
                }

                public final void onAnimationStart(Animation animation) {
                    MediaRouteDynamicControllerDialog.this.mIsAnimatingVolumeSliderLayout = true;
                }
            });
            r1.setDuration((long) this.mLayoutAnimationDurationMs);
            r1.setInterpolator(this.mAccelerateDecelerateInterpolator);
            view.startAnimation(r1);
        }

        public final Drawable getIconDrawable(MediaRouter.RouteInfo routeInfo) {
            Objects.requireNonNull(routeInfo);
            Uri uri = routeInfo.mIconUri;
            if (uri != null) {
                try {
                    Drawable createFromStream = Drawable.createFromStream(MediaRouteDynamicControllerDialog.this.mContext.getContentResolver().openInputStream(uri), (String) null);
                    if (createFromStream != null) {
                        return createFromStream;
                    }
                } catch (IOException e) {
                    Log.w("MediaRouteCtrlDialog", "Failed to load " + uri, e);
                }
            }
            int i = routeInfo.mDeviceType;
            if (i == 1) {
                return this.mTvIcon;
            }
            if (i == 2) {
                return this.mSpeakerIcon;
            }
            if (routeInfo.isGroup()) {
                return this.mSpeakerGroupIcon;
            }
            return this.mDefaultIcon;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:47:0x012e, code lost:
            if (r7 != false) goto L_0x0133;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onBindViewHolder(androidx.recyclerview.widget.RecyclerView.ViewHolder r8, int r9) {
            /*
                r7 = this;
                int r0 = r7.getItemViewType(r9)
                if (r9 != 0) goto L_0x0009
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog$RecyclerAdapter$Item r9 = r7.mGroupVolumeItem
                goto L_0x0013
            L_0x0009:
                java.util.ArrayList<androidx.mediarouter.app.MediaRouteDynamicControllerDialog$RecyclerAdapter$Item> r1 = r7.mItems
                int r9 = r9 + -1
                java.lang.Object r9 = r1.get(r9)
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog$RecyclerAdapter$Item r9 = (androidx.mediarouter.app.MediaRouteDynamicControllerDialog.RecyclerAdapter.Item) r9
            L_0x0013:
                r1 = 1
                r2 = 0
                if (r0 == r1) goto L_0x01b6
                r3 = 2
                if (r0 == r3) goto L_0x01a5
                r4 = 3
                r5 = 1065353216(0x3f800000, float:1.0)
                r6 = 4
                if (r0 == r4) goto L_0x007f
                if (r0 == r6) goto L_0x002b
                java.lang.String r7 = "MediaRouteCtrlDialog"
                java.lang.String r8 = "Cannot bind item to ViewHolder because of wrong view type"
                android.util.Log.w(r7, r8)
                goto L_0x0204
            L_0x002b:
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog$RecyclerAdapter$GroupViewHolder r8 = (androidx.mediarouter.app.MediaRouteDynamicControllerDialog.RecyclerAdapter.GroupViewHolder) r8
                java.util.Objects.requireNonNull(r9)
                java.lang.Object r7 = r9.mData
                androidx.mediarouter.media.MediaRouter$RouteInfo r7 = (androidx.mediarouter.media.MediaRouter.RouteInfo) r7
                r8.mRoute = r7
                android.widget.ImageView r9 = r8.mImageView
                r9.setVisibility(r2)
                android.widget.ProgressBar r9 = r8.mProgressBar
                r9.setVisibility(r6)
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog$RecyclerAdapter r9 = androidx.mediarouter.app.MediaRouteDynamicControllerDialog.RecyclerAdapter.this
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog r9 = androidx.mediarouter.app.MediaRouteDynamicControllerDialog.this
                androidx.mediarouter.media.MediaRouter$RouteInfo r9 = r9.mSelectedRoute
                java.util.List r9 = r9.getMemberRoutes()
                int r0 = r9.size()
                if (r0 != r1) goto L_0x0057
                java.lang.Object r9 = r9.get(r2)
                if (r9 != r7) goto L_0x0057
                r1 = r2
            L_0x0057:
                android.view.View r9 = r8.mItemView
                if (r1 == 0) goto L_0x005c
                goto L_0x005e
            L_0x005c:
                float r5 = r8.mDisabledAlpha
            L_0x005e:
                r9.setAlpha(r5)
                android.view.View r9 = r8.mItemView
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog$RecyclerAdapter$GroupViewHolder$1 r0 = new androidx.mediarouter.app.MediaRouteDynamicControllerDialog$RecyclerAdapter$GroupViewHolder$1
                r0.<init>()
                r9.setOnClickListener(r0)
                android.widget.ImageView r9 = r8.mImageView
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog$RecyclerAdapter r0 = androidx.mediarouter.app.MediaRouteDynamicControllerDialog.RecyclerAdapter.this
                android.graphics.drawable.Drawable r0 = r0.getIconDrawable(r7)
                r9.setImageDrawable(r0)
                android.widget.TextView r8 = r8.mTextView
                java.lang.String r7 = r7.mName
                r8.setText(r7)
                goto L_0x0204
            L_0x007f:
                java.util.Objects.requireNonNull(r9)
                java.lang.Object r0 = r9.mData
                androidx.mediarouter.media.MediaRouter$RouteInfo r0 = (androidx.mediarouter.media.MediaRouter.RouteInfo) r0
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog r7 = androidx.mediarouter.app.MediaRouteDynamicControllerDialog.this
                java.util.HashMap r7 = r7.mVolumeSliderHolderMap
                java.util.Objects.requireNonNull(r0)
                java.lang.String r0 = r0.mUniqueId
                r4 = r8
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog$MediaRouteVolumeSliderHolder r4 = (androidx.mediarouter.app.MediaRouteDynamicControllerDialog.MediaRouteVolumeSliderHolder) r4
                r7.put(r0, r4)
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog$RecyclerAdapter$RouteViewHolder r8 = (androidx.mediarouter.app.MediaRouteDynamicControllerDialog.RecyclerAdapter.RouteViewHolder) r8
                java.lang.Object r7 = r9.mData
                androidx.mediarouter.media.MediaRouter$RouteInfo r7 = (androidx.mediarouter.media.MediaRouter.RouteInfo) r7
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog$RecyclerAdapter r9 = androidx.mediarouter.app.MediaRouteDynamicControllerDialog.RecyclerAdapter.this
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog r9 = androidx.mediarouter.app.MediaRouteDynamicControllerDialog.this
                androidx.mediarouter.media.MediaRouter$RouteInfo r9 = r9.mSelectedRoute
                if (r7 != r9) goto L_0x00ce
                java.util.List r9 = r7.getMemberRoutes()
                int r9 = r9.size()
                if (r9 <= 0) goto L_0x00ce
                java.util.List r9 = r7.getMemberRoutes()
                java.util.Iterator r9 = r9.iterator()
            L_0x00b5:
                boolean r0 = r9.hasNext()
                if (r0 == 0) goto L_0x00ce
                java.lang.Object r0 = r9.next()
                androidx.mediarouter.media.MediaRouter$RouteInfo r0 = (androidx.mediarouter.media.MediaRouter.RouteInfo) r0
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog$RecyclerAdapter r4 = androidx.mediarouter.app.MediaRouteDynamicControllerDialog.RecyclerAdapter.this
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog r4 = androidx.mediarouter.app.MediaRouteDynamicControllerDialog.this
                java.util.ArrayList r4 = r4.mGroupableRoutes
                boolean r4 = r4.contains(r0)
                if (r4 != 0) goto L_0x00b5
                r7 = r0
            L_0x00ce:
                r8.bindRouteVolumeSliderHolder(r7)
                android.widget.ImageView r9 = r8.mImageView
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog$RecyclerAdapter r0 = androidx.mediarouter.app.MediaRouteDynamicControllerDialog.RecyclerAdapter.this
                android.graphics.drawable.Drawable r0 = r0.getIconDrawable(r7)
                r9.setImageDrawable(r0)
                android.widget.TextView r9 = r8.mTextView
                java.lang.String r0 = r7.mName
                r9.setText(r0)
                android.widget.CheckBox r9 = r8.mCheckBox
                r9.setVisibility(r2)
                boolean r9 = r8.isSelected(r7)
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog$RecyclerAdapter r0 = androidx.mediarouter.app.MediaRouteDynamicControllerDialog.RecyclerAdapter.this
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog r0 = androidx.mediarouter.app.MediaRouteDynamicControllerDialog.this
                java.util.ArrayList r0 = r0.mUngroupableRoutes
                boolean r0 = r0.contains(r7)
                if (r0 == 0) goto L_0x00f9
                goto L_0x0131
            L_0x00f9:
                boolean r0 = r8.isSelected(r7)
                if (r0 == 0) goto L_0x0110
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog$RecyclerAdapter r0 = androidx.mediarouter.app.MediaRouteDynamicControllerDialog.RecyclerAdapter.this
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog r0 = androidx.mediarouter.app.MediaRouteDynamicControllerDialog.this
                androidx.mediarouter.media.MediaRouter$RouteInfo r0 = r0.mSelectedRoute
                java.util.List r0 = r0.getMemberRoutes()
                int r0 = r0.size()
                if (r0 >= r3) goto L_0x0110
                goto L_0x0131
            L_0x0110:
                boolean r0 = r8.isSelected(r7)
                if (r0 == 0) goto L_0x0133
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog$RecyclerAdapter r0 = androidx.mediarouter.app.MediaRouteDynamicControllerDialog.RecyclerAdapter.this
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog r0 = androidx.mediarouter.app.MediaRouteDynamicControllerDialog.this
                androidx.mediarouter.media.MediaRouter$RouteInfo r0 = r0.mSelectedRoute
                androidx.mediarouter.media.MediaRouter$RouteInfo$DynamicGroupState r7 = r0.getDynamicGroupState(r7)
                if (r7 == 0) goto L_0x0131
                androidx.mediarouter.media.MediaRouteProvider$DynamicGroupRouteController$DynamicRouteDescriptor r7 = r7.mDynamicDescriptor
                if (r7 == 0) goto L_0x012d
                boolean r7 = r7.mIsUnselectable
                if (r7 == 0) goto L_0x012b
                goto L_0x012d
            L_0x012b:
                r7 = r2
                goto L_0x012e
            L_0x012d:
                r7 = r1
            L_0x012e:
                if (r7 == 0) goto L_0x0131
                goto L_0x0133
            L_0x0131:
                r7 = r2
                goto L_0x0134
            L_0x0133:
                r7 = r1
            L_0x0134:
                android.widget.CheckBox r0 = r8.mCheckBox
                r0.setChecked(r9)
                android.widget.ProgressBar r0 = r8.mProgressBar
                r0.setVisibility(r6)
                android.widget.ImageView r0 = r8.mImageView
                r0.setVisibility(r2)
                android.view.View r0 = r8.mItemView
                r0.setEnabled(r7)
                android.widget.CheckBox r0 = r8.mCheckBox
                r0.setEnabled(r7)
                android.widget.ImageButton r0 = r8.mMuteButton
                if (r7 != 0) goto L_0x0156
                if (r9 == 0) goto L_0x0154
                goto L_0x0156
            L_0x0154:
                r3 = r2
                goto L_0x0157
            L_0x0156:
                r3 = r1
            L_0x0157:
                r0.setEnabled(r3)
                androidx.mediarouter.app.MediaRouteVolumeSlider r0 = r8.mVolumeSlider
                if (r7 != 0) goto L_0x0162
                if (r9 == 0) goto L_0x0161
                goto L_0x0162
            L_0x0161:
                r1 = r2
            L_0x0162:
                r0.setEnabled(r1)
                android.view.View r0 = r8.mItemView
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog$RecyclerAdapter$RouteViewHolder$1 r1 = r8.mViewClickListener
                r0.setOnClickListener(r1)
                android.widget.CheckBox r0 = r8.mCheckBox
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog$RecyclerAdapter$RouteViewHolder$1 r1 = r8.mViewClickListener
                r0.setOnClickListener(r1)
                android.widget.RelativeLayout r0 = r8.mVolumeSliderLayout
                if (r9 == 0) goto L_0x0181
                androidx.mediarouter.media.MediaRouter$RouteInfo r1 = r8.mRoute
                boolean r1 = r1.isGroup()
                if (r1 != 0) goto L_0x0181
                int r2 = r8.mExpandedLayoutHeight
            L_0x0181:
                android.view.ViewGroup$LayoutParams r1 = r0.getLayoutParams()
                r1.height = r2
                r0.setLayoutParams(r1)
                android.view.View r0 = r8.mItemView
                if (r7 != 0) goto L_0x0194
                if (r9 == 0) goto L_0x0191
                goto L_0x0194
            L_0x0191:
                float r1 = r8.mDisabledAlpha
                goto L_0x0195
            L_0x0194:
                r1 = r5
            L_0x0195:
                r0.setAlpha(r1)
                android.widget.CheckBox r0 = r8.mCheckBox
                if (r7 != 0) goto L_0x01a1
                if (r9 != 0) goto L_0x019f
                goto L_0x01a1
            L_0x019f:
                float r5 = r8.mDisabledAlpha
            L_0x01a1:
                r0.setAlpha(r5)
                goto L_0x0204
            L_0x01a5:
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog$RecyclerAdapter$HeaderViewHolder r8 = (androidx.mediarouter.app.MediaRouteDynamicControllerDialog.RecyclerAdapter.HeaderViewHolder) r8
                java.util.Objects.requireNonNull(r9)
                java.lang.Object r7 = r9.mData
                java.lang.String r7 = r7.toString()
                android.widget.TextView r8 = r8.mTextView
                r8.setText(r7)
                goto L_0x0204
            L_0x01b6:
                java.util.Objects.requireNonNull(r9)
                java.lang.Object r0 = r9.mData
                androidx.mediarouter.media.MediaRouter$RouteInfo r0 = (androidx.mediarouter.media.MediaRouter.RouteInfo) r0
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog r7 = androidx.mediarouter.app.MediaRouteDynamicControllerDialog.this
                java.util.HashMap r7 = r7.mVolumeSliderHolderMap
                java.util.Objects.requireNonNull(r0)
                java.lang.String r0 = r0.mUniqueId
                r3 = r8
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog$MediaRouteVolumeSliderHolder r3 = (androidx.mediarouter.app.MediaRouteDynamicControllerDialog.MediaRouteVolumeSliderHolder) r3
                r7.put(r0, r3)
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog$RecyclerAdapter$GroupVolumeViewHolder r8 = (androidx.mediarouter.app.MediaRouteDynamicControllerDialog.RecyclerAdapter.GroupVolumeViewHolder) r8
                android.view.View r7 = r8.itemView
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog$RecyclerAdapter r0 = androidx.mediarouter.app.MediaRouteDynamicControllerDialog.RecyclerAdapter.this
                java.util.Objects.requireNonNull(r0)
                androidx.mediarouter.app.MediaRouteDynamicControllerDialog r0 = androidx.mediarouter.app.MediaRouteDynamicControllerDialog.this
                boolean r3 = r0.mEnableGroupVolumeUX
                if (r3 == 0) goto L_0x01e8
                androidx.mediarouter.media.MediaRouter$RouteInfo r0 = r0.mSelectedRoute
                java.util.List r0 = r0.getMemberRoutes()
                int r0 = r0.size()
                if (r0 <= r1) goto L_0x01e8
                goto L_0x01e9
            L_0x01e8:
                r1 = r2
            L_0x01e9:
                if (r1 == 0) goto L_0x01ed
                int r2 = r8.mExpandedHeight
            L_0x01ed:
                android.view.ViewGroup$LayoutParams r0 = r7.getLayoutParams()
                r0.height = r2
                r7.setLayoutParams(r0)
                java.lang.Object r7 = r9.mData
                androidx.mediarouter.media.MediaRouter$RouteInfo r7 = (androidx.mediarouter.media.MediaRouter.RouteInfo) r7
                r8.bindRouteVolumeSliderHolder(r7)
                android.widget.TextView r8 = r8.mTextView
                java.lang.String r7 = r7.mName
                r8.setText(r7)
            L_0x0204:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.mediarouter.app.MediaRouteDynamicControllerDialog.RecyclerAdapter.onBindViewHolder(androidx.recyclerview.widget.RecyclerView$ViewHolder, int):void");
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

    public class VolumeChangeListener implements SeekBar.OnSeekBarChangeListener {
        public VolumeChangeListener() {
        }

        public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            boolean z2;
            if (z) {
                MediaRouter.RouteInfo routeInfo = (MediaRouter.RouteInfo) seekBar.getTag();
                HashMap hashMap = MediaRouteDynamicControllerDialog.this.mVolumeSliderHolderMap;
                Objects.requireNonNull(routeInfo);
                MediaRouteVolumeSliderHolder mediaRouteVolumeSliderHolder = (MediaRouteVolumeSliderHolder) hashMap.get(routeInfo.mUniqueId);
                if (mediaRouteVolumeSliderHolder != null) {
                    if (i == 0) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    mediaRouteVolumeSliderHolder.setMute(z2);
                }
                routeInfo.requestSetVolume(i);
            }
        }

        public final void onStartTrackingTouch(SeekBar seekBar) {
            MediaRouteDynamicControllerDialog mediaRouteDynamicControllerDialog = MediaRouteDynamicControllerDialog.this;
            if (mediaRouteDynamicControllerDialog.mRouteForVolumeUpdatingByUser != null) {
                mediaRouteDynamicControllerDialog.mHandler.removeMessages(2);
            }
            MediaRouteDynamicControllerDialog.this.mRouteForVolumeUpdatingByUser = (MediaRouter.RouteInfo) seekBar.getTag();
        }

        public final void onStopTrackingTouch(SeekBar seekBar) {
            MediaRouteDynamicControllerDialog.this.mHandler.sendEmptyMessageDelayed(2, 500);
        }
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MediaRouteDynamicControllerDialog(android.content.Context r3) {
        /*
            r2 = this;
            r0 = 0
            android.view.ContextThemeWrapper r3 = androidx.mediarouter.app.MediaRouterThemeHelper.createThemedDialogContext(r3, r0)
            int r1 = androidx.mediarouter.app.MediaRouterThemeHelper.createThemedDialogStyle(r3)
            r2.<init>(r3, r1)
            androidx.mediarouter.media.MediaRouteSelector r3 = androidx.mediarouter.media.MediaRouteSelector.EMPTY
            r2.mSelector = r3
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r2.mMemberRoutes = r3
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r2.mGroupableRoutes = r3
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r2.mTransferableRoutes = r3
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r2.mUngroupableRoutes = r3
            androidx.mediarouter.app.MediaRouteDynamicControllerDialog$1 r3 = new androidx.mediarouter.app.MediaRouteDynamicControllerDialog$1
            r3.<init>()
            r2.mHandler = r3
            android.content.Context r3 = r2.getContext()
            r2.mContext = r3
            androidx.mediarouter.media.MediaRouter r3 = androidx.mediarouter.media.MediaRouter.getInstance(r3)
            r2.mRouter = r3
            androidx.mediarouter.media.MediaRouter$GlobalMediaRouter r3 = androidx.mediarouter.media.MediaRouter.sGlobal
            if (r3 != 0) goto L_0x0044
            goto L_0x004c
        L_0x0044:
            androidx.mediarouter.media.MediaRouter$GlobalMediaRouter r3 = androidx.mediarouter.media.MediaRouter.getGlobalRouter()
            java.util.Objects.requireNonNull(r3)
            r0 = 1
        L_0x004c:
            r2.mEnableGroupVolumeUX = r0
            androidx.mediarouter.app.MediaRouteDynamicControllerDialog$MediaRouterCallback r3 = new androidx.mediarouter.app.MediaRouteDynamicControllerDialog$MediaRouterCallback
            r3.<init>()
            r2.mCallback = r3
            androidx.mediarouter.media.MediaRouter$RouteInfo r3 = androidx.mediarouter.media.MediaRouter.getSelectedRoute()
            r2.mSelectedRoute = r3
            androidx.mediarouter.app.MediaRouteDynamicControllerDialog$MediaControllerCallback r3 = new androidx.mediarouter.app.MediaRouteDynamicControllerDialog$MediaControllerCallback
            r3.<init>()
            r2.mControllerCallback = r3
            r2.setMediaSession()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.mediarouter.app.MediaRouteDynamicControllerDialog.<init>(android.content.Context):void");
    }

    public final void reloadIconIfNeeded() {
        Bitmap bitmap;
        Bitmap bitmap2;
        Uri uri;
        MediaDescriptionCompat mediaDescriptionCompat = this.mDescription;
        Uri uri2 = null;
        if (mediaDescriptionCompat == null) {
            bitmap = null;
        } else {
            Objects.requireNonNull(mediaDescriptionCompat);
            bitmap = mediaDescriptionCompat.mIcon;
        }
        MediaDescriptionCompat mediaDescriptionCompat2 = this.mDescription;
        if (mediaDescriptionCompat2 != null) {
            Objects.requireNonNull(mediaDescriptionCompat2);
            uri2 = mediaDescriptionCompat2.mIconUri;
        }
        FetchArtTask fetchArtTask = this.mFetchArtTask;
        if (fetchArtTask == null) {
            bitmap2 = this.mArtIconBitmap;
        } else {
            Objects.requireNonNull(fetchArtTask);
            bitmap2 = fetchArtTask.mIconBitmap;
        }
        FetchArtTask fetchArtTask2 = this.mFetchArtTask;
        if (fetchArtTask2 == null) {
            uri = this.mArtIconUri;
        } else {
            Objects.requireNonNull(fetchArtTask2);
            uri = fetchArtTask2.mIconUri;
        }
        if (bitmap2 != bitmap || (bitmap2 == null && !Objects.equals(uri, uri2))) {
            FetchArtTask fetchArtTask3 = this.mFetchArtTask;
            if (fetchArtTask3 != null) {
                fetchArtTask3.cancel(true);
            }
            FetchArtTask fetchArtTask4 = new FetchArtTask();
            this.mFetchArtTask = fetchArtTask4;
            fetchArtTask4.execute(new Void[0]);
        }
    }

    public final void setMediaSession() {
        MediaControllerCompat mediaControllerCompat = this.mMediaController;
        if (mediaControllerCompat != null) {
            mediaControllerCompat.unregisterCallback(this.mControllerCallback);
            this.mMediaController = null;
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
                updateRoutes();
            }
        }
    }

    public final void updateLayout() {
        int i;
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
        this.mArtIconBitmap = null;
        this.mArtIconUri = null;
        reloadIconIfNeeded();
        updateMetadataViews();
        updateRoutesView();
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x00dc  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00de  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00ea  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00f3  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00f9  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0102  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x010d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateMetadataViews() {
        /*
            r10 = this;
            androidx.mediarouter.media.MediaRouter$RouteInfo r0 = r10.mRouteForVolumeUpdatingByUser
            r1 = 1
            if (r0 != 0) goto L_0x000e
            boolean r0 = r10.mIsAnimatingVolumeSliderLayout
            if (r0 == 0) goto L_0x000a
            goto L_0x000e
        L_0x000a:
            boolean r0 = r10.mCreated
            r0 = r0 ^ r1
            goto L_0x000f
        L_0x000e:
            r0 = r1
        L_0x000f:
            if (r0 == 0) goto L_0x0014
            r10.mUpdateMetadataViewsDeferred = r1
            return
        L_0x0014:
            r0 = 0
            r10.mUpdateMetadataViewsDeferred = r0
            androidx.mediarouter.media.MediaRouter$RouteInfo r2 = r10.mSelectedRoute
            boolean r2 = r2.isSelected()
            if (r2 == 0) goto L_0x0027
            androidx.mediarouter.media.MediaRouter$RouteInfo r2 = r10.mSelectedRoute
            boolean r2 = r2.isDefaultOrBluetooth()
            if (r2 == 0) goto L_0x002a
        L_0x0027:
            r10.dismiss()
        L_0x002a:
            boolean r2 = r10.mArtIconIsLoaded
            r3 = 8
            r4 = 0
            if (r2 == 0) goto L_0x00a0
            android.graphics.Bitmap r2 = r10.mArtIconLoadedBitmap
            if (r2 == 0) goto L_0x003d
            boolean r2 = r2.isRecycled()
            if (r2 == 0) goto L_0x003d
            r2 = r1
            goto L_0x003e
        L_0x003d:
            r2 = r0
        L_0x003e:
            if (r2 != 0) goto L_0x00a0
            android.graphics.Bitmap r2 = r10.mArtIconLoadedBitmap
            if (r2 == 0) goto L_0x00a0
            android.widget.ImageView r2 = r10.mArtView
            r2.setVisibility(r0)
            android.widget.ImageView r2 = r10.mArtView
            android.graphics.Bitmap r5 = r10.mArtIconLoadedBitmap
            r2.setImageBitmap(r5)
            android.widget.ImageView r2 = r10.mArtView
            int r5 = r10.mArtIconBackgroundColor
            r2.setBackgroundColor(r5)
            android.view.View r2 = r10.mMetadataBlackScrim
            r2.setVisibility(r0)
            android.graphics.Bitmap r2 = r10.mArtIconLoadedBitmap
            r5 = 1092616192(0x41200000, float:10.0)
            android.content.Context r6 = r10.mContext
            android.renderscript.RenderScript r6 = android.renderscript.RenderScript.create(r6)
            android.renderscript.Allocation r7 = android.renderscript.Allocation.createFromBitmap(r6, r2)
            android.renderscript.Type r8 = r7.getType()
            android.renderscript.Allocation r8 = android.renderscript.Allocation.createTyped(r6, r8)
            android.renderscript.Element r9 = android.renderscript.Element.U8_4(r6)
            android.renderscript.ScriptIntrinsicBlur r9 = android.renderscript.ScriptIntrinsicBlur.create(r6, r9)
            r9.setRadius(r5)
            r9.setInput(r7)
            r9.forEach(r8)
            android.graphics.Bitmap$Config r5 = r2.getConfig()
            android.graphics.Bitmap r2 = r2.copy(r5, r1)
            r8.copyTo(r2)
            r7.destroy()
            r8.destroy()
            r9.destroy()
            r6.destroy()
            android.widget.ImageView r5 = r10.mMetadataBackground
            r5.setImageBitmap(r2)
            goto L_0x00d2
        L_0x00a0:
            android.graphics.Bitmap r2 = r10.mArtIconLoadedBitmap
            if (r2 == 0) goto L_0x00ac
            boolean r2 = r2.isRecycled()
            if (r2 == 0) goto L_0x00ac
            r2 = r1
            goto L_0x00ad
        L_0x00ac:
            r2 = r0
        L_0x00ad:
            if (r2 == 0) goto L_0x00c3
            java.lang.String r2 = "Can't set artwork image with recycled bitmap: "
            java.lang.StringBuilder r2 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r2)
            android.graphics.Bitmap r5 = r10.mArtIconLoadedBitmap
            r2.append(r5)
            java.lang.String r2 = r2.toString()
            java.lang.String r5 = "MediaRouteCtrlDialog"
            android.util.Log.w(r5, r2)
        L_0x00c3:
            android.widget.ImageView r2 = r10.mArtView
            r2.setVisibility(r3)
            android.view.View r2 = r10.mMetadataBlackScrim
            r2.setVisibility(r3)
            android.widget.ImageView r2 = r10.mMetadataBackground
            r2.setImageBitmap(r4)
        L_0x00d2:
            r10.mArtIconIsLoaded = r0
            r10.mArtIconLoadedBitmap = r4
            r10.mArtIconBackgroundColor = r0
            android.support.v4.media.MediaDescriptionCompat r2 = r10.mDescription
            if (r2 != 0) goto L_0x00de
            r2 = r4
            goto L_0x00e0
        L_0x00de:
            java.lang.CharSequence r2 = r2.mTitle
        L_0x00e0:
            boolean r5 = android.text.TextUtils.isEmpty(r2)
            r5 = r5 ^ r1
            android.support.v4.media.MediaDescriptionCompat r6 = r10.mDescription
            if (r6 != 0) goto L_0x00ea
            goto L_0x00ec
        L_0x00ea:
            java.lang.CharSequence r4 = r6.mSubtitle
        L_0x00ec:
            boolean r6 = android.text.TextUtils.isEmpty(r4)
            r1 = r1 ^ r6
            if (r5 == 0) goto L_0x00f9
            android.widget.TextView r5 = r10.mTitleView
            r5.setText(r2)
            goto L_0x0100
        L_0x00f9:
            android.widget.TextView r2 = r10.mTitleView
            java.lang.String r5 = r10.mTitlePlaceholder
            r2.setText(r5)
        L_0x0100:
            if (r1 == 0) goto L_0x010d
            android.widget.TextView r1 = r10.mSubtitleView
            r1.setText(r4)
            android.widget.TextView r10 = r10.mSubtitleView
            r10.setVisibility(r0)
            goto L_0x0112
        L_0x010d:
            android.widget.TextView r10 = r10.mSubtitleView
            r10.setVisibility(r3)
        L_0x0112:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.mediarouter.app.MediaRouteDynamicControllerDialog.updateMetadataViews():void");
    }

    public final void updateRoutes() {
        boolean z;
        this.mMemberRoutes.clear();
        this.mGroupableRoutes.clear();
        this.mTransferableRoutes.clear();
        this.mMemberRoutes.addAll(this.mSelectedRoute.getMemberRoutes());
        MediaRouter.RouteInfo routeInfo = this.mSelectedRoute;
        Objects.requireNonNull(routeInfo);
        MediaRouter.ProviderInfo providerInfo = routeInfo.mProvider;
        Objects.requireNonNull(providerInfo);
        MediaRouter.checkCallingThread();
        for (MediaRouter.RouteInfo routeInfo2 : Collections.unmodifiableList(providerInfo.mRoutes)) {
            MediaRouter.RouteInfo.DynamicGroupState dynamicGroupState = this.mSelectedRoute.getDynamicGroupState(routeInfo2);
            if (dynamicGroupState != null) {
                if (dynamicGroupState.isGroupable()) {
                    this.mGroupableRoutes.add(routeInfo2);
                }
                MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor dynamicRouteDescriptor = dynamicGroupState.mDynamicDescriptor;
                if (dynamicRouteDescriptor == null || !dynamicRouteDescriptor.mIsTransferable) {
                    z = false;
                } else {
                    z = true;
                }
                if (z) {
                    this.mTransferableRoutes.add(routeInfo2);
                }
            }
        }
        onFilterRoutes(this.mGroupableRoutes);
        onFilterRoutes(this.mTransferableRoutes);
        ArrayList arrayList = this.mMemberRoutes;
        RouteComparator routeComparator = RouteComparator.sInstance;
        Collections.sort(arrayList, routeComparator);
        Collections.sort(this.mGroupableRoutes, routeComparator);
        Collections.sort(this.mTransferableRoutes, routeComparator);
        this.mAdapter.updateItems();
    }

    public final void updateRoutesView() {
        boolean z;
        if (!this.mAttachedToWindow) {
            return;
        }
        if (SystemClock.uptimeMillis() - this.mLastUpdateTime >= 300) {
            if (this.mRouteForVolumeUpdatingByUser != null || this.mIsAnimatingVolumeSliderLayout) {
                z = true;
            } else {
                z = !this.mCreated;
            }
            if (z) {
                this.mUpdateRoutesViewDeferred = true;
                return;
            }
            this.mUpdateRoutesViewDeferred = false;
            if (!this.mSelectedRoute.isSelected() || this.mSelectedRoute.isDefaultOrBluetooth()) {
                dismiss();
            }
            this.mLastUpdateTime = SystemClock.uptimeMillis();
            this.mAdapter.notifyAdapterDataSetChanged();
            return;
        }
        this.mHandler.removeMessages(1);
        this.mHandler.sendEmptyMessageAtTime(1, this.mLastUpdateTime + 300);
    }

    public final void updateViewsIfNeeded() {
        if (this.mUpdateRoutesViewDeferred) {
            updateRoutesView();
        }
        if (this.mUpdateMetadataViewsDeferred) {
            updateMetadataViews();
        }
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAttachedToWindow = true;
        this.mRouter.addCallback(this.mSelector, this.mCallback, 1);
        updateRoutes();
        Objects.requireNonNull(this.mRouter);
        boolean z = MediaRouter.DEBUG;
        setMediaSession();
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C1777R.layout.mr_cast_dialog);
        MediaRouterThemeHelper.setDialogBackgroundColor(this.mContext, this);
        ImageButton imageButton = (ImageButton) findViewById(C1777R.C1779id.mr_cast_close_button);
        this.mCloseButton = imageButton;
        imageButton.setColorFilter(-1);
        this.mCloseButton.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                MediaRouteDynamicControllerDialog.this.dismiss();
            }
        });
        Button button = (Button) findViewById(C1777R.C1779id.mr_cast_stop_button);
        this.mStopCastingButton = button;
        button.setTextColor(-1);
        this.mStopCastingButton.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                if (MediaRouteDynamicControllerDialog.this.mSelectedRoute.isSelected()) {
                    Objects.requireNonNull(MediaRouteDynamicControllerDialog.this.mRouter);
                    MediaRouter.unselect(2);
                }
                MediaRouteDynamicControllerDialog.this.dismiss();
            }
        });
        this.mAdapter = new RecyclerAdapter();
        RecyclerView recyclerView = (RecyclerView) findViewById(C1777R.C1779id.mr_cast_list);
        this.mRecyclerView = recyclerView;
        recyclerView.setAdapter(this.mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(1));
        this.mVolumeChangeListener = new VolumeChangeListener();
        this.mVolumeSliderHolderMap = new HashMap();
        this.mUnmutedVolumeMap = new HashMap();
        this.mMetadataBackground = (ImageView) findViewById(C1777R.C1779id.mr_cast_meta_background);
        this.mMetadataBlackScrim = findViewById(C1777R.C1779id.mr_cast_meta_black_scrim);
        this.mArtView = (ImageView) findViewById(C1777R.C1779id.mr_cast_meta_art);
        TextView textView = (TextView) findViewById(C1777R.C1779id.mr_cast_meta_title);
        this.mTitleView = textView;
        textView.setTextColor(-1);
        TextView textView2 = (TextView) findViewById(C1777R.C1779id.mr_cast_meta_subtitle);
        this.mSubtitleView = textView2;
        textView2.setTextColor(-1);
        this.mTitlePlaceholder = this.mContext.getResources().getString(C1777R.string.mr_cast_dialog_title_view_placeholder);
        this.mCreated = true;
        updateLayout();
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mAttachedToWindow = false;
        this.mRouter.removeCallback(this.mCallback);
        this.mHandler.removeCallbacksAndMessages((Object) null);
        setMediaSession();
    }

    public final void onFilterRoutes(List<MediaRouter.RouteInfo> list) {
        boolean z;
        for (int size = list.size() - 1; size >= 0; size--) {
            MediaRouter.RouteInfo routeInfo = list.get(size);
            if (routeInfo.isDefaultOrBluetooth() || !routeInfo.mEnabled || !routeInfo.matchesSelector(this.mSelector) || this.mSelectedRoute == routeInfo) {
                z = false;
            } else {
                z = true;
            }
            if (!z) {
                list.remove(size);
            }
        }
    }
}
