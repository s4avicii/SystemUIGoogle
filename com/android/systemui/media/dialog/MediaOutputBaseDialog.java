package com.android.systemui.media.dialog;

import android.content.ComponentName;
import android.content.Context;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.drawable.Drawable;
import android.media.session.MediaController;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.graphics.drawable.IconCompat;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline2;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda2;
import com.android.p012wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda3;
import com.android.p012wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda4;
import com.android.settingslib.media.LocalMediaManager;
import com.android.systemui.media.dialog.MediaOutputController;
import com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda9;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;
import java.util.Iterator;
import java.util.Objects;

public abstract class MediaOutputBaseDialog extends SystemUIDialog implements MediaOutputController.Callback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public MediaOutputAdapter mAdapter;
    public Button mAppButton;
    public ImageView mAppResourceIcon;
    public LinearLayout mCastAppLayout;
    public final Context mContext = getContext();
    public LinearLayout mDeviceListLayout;
    public final MediaOutputBaseDialog$$ExternalSyntheticLambda0 mDeviceListLayoutListener = new MediaOutputBaseDialog$$ExternalSyntheticLambda0(this);
    public RecyclerView mDevicesRecyclerView;
    public View mDialogView;
    public Button mDoneButton;
    public ImageView mHeaderIcon;
    public TextView mHeaderSubtitle;
    public TextView mHeaderTitle;
    public final LinearLayoutManager mLayoutManager;
    public int mListMaxHeight;
    public final Handler mMainThreadHandler = new Handler(Looper.getMainLooper());
    public final MediaOutputController mMediaOutputController;
    public Button mStopButton;

    public abstract Drawable getAppSourceIcon();

    public abstract IconCompat getHeaderIcon();

    public abstract void getHeaderIconRes();

    public abstract int getHeaderIconSize();

    public abstract CharSequence getHeaderSubtitle();

    public abstract CharSequence getHeaderText();

    public abstract int getStopButtonVisibility();

    public void refresh() {
        refresh(false);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: android.content.pm.ApplicationInfo} */
    /* JADX WARNING: type inference failed for: r2v1 */
    /* JADX WARNING: type inference failed for: r2v2, types: [java.lang.CharSequence] */
    /* JADX WARNING: type inference failed for: r1v7 */
    /* JADX WARNING: type inference failed for: r1v9, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r1v10, types: [java.lang.CharSequence] */
    /* JADX WARNING: type inference failed for: r2v8 */
    /* JADX WARNING: type inference failed for: r2v9 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void refresh(boolean r13) {
        /*
            r12 = this;
            r12.getHeaderIconRes()
            androidx.core.graphics.drawable.IconCompat r0 = r12.getHeaderIcon()
            android.graphics.drawable.Drawable r1 = r12.getAppSourceIcon()
            android.widget.LinearLayout r2 = r12.mCastAppLayout
            com.android.systemui.media.dialog.MediaOutputController r3 = r12.mMediaOutputController
            java.util.Objects.requireNonNull(r3)
            android.content.Context r3 = r3.mContext
            androidx.mediarouter.media.MediaRouter.getInstance(r3)
            androidx.mediarouter.media.MediaRouter.checkCallingThread()
            androidx.mediarouter.media.MediaRouter.getGlobalRouter()
            java.lang.String r3 = "MediaOutputController"
            java.lang.String r4 = "try to get routerParams: null"
            android.util.Log.d(r3, r4)
            r3 = 8
            r2.setVisibility(r3)
            r2 = 0
            r4 = 0
            if (r1 == 0) goto L_0x0078
            android.widget.ImageView r5 = r12.mAppResourceIcon
            r5.setImageDrawable(r1)
            android.widget.Button r5 = r12.mAppButton
            android.content.Context r6 = r12.mContext
            android.content.res.Resources r6 = r6.getResources()
            r7 = 2131166340(0x7f070484, float:1.7946923E38)
            int r6 = r6.getDimensionPixelSize(r7)
            int r7 = r1.getIntrinsicWidth()
            int r8 = r1.getIntrinsicHeight()
            int r9 = r1.getOpacity()
            r10 = -1
            if (r9 == r10) goto L_0x0054
            android.graphics.Bitmap$Config r9 = android.graphics.Bitmap.Config.ARGB_8888
            goto L_0x0056
        L_0x0054:
            android.graphics.Bitmap$Config r9 = android.graphics.Bitmap.Config.RGB_565
        L_0x0056:
            android.graphics.Bitmap r9 = android.graphics.Bitmap.createBitmap(r7, r8, r9)
            android.graphics.Canvas r10 = new android.graphics.Canvas
            r10.<init>(r9)
            r1.setBounds(r4, r4, r7, r8)
            r1.draw(r10)
            android.graphics.drawable.BitmapDrawable r1 = new android.graphics.drawable.BitmapDrawable
            android.content.Context r7 = r12.mContext
            android.content.res.Resources r7 = r7.getResources()
            android.graphics.Bitmap r6 = android.graphics.Bitmap.createScaledBitmap(r9, r6, r6, r4)
            r1.<init>(r7, r6)
            r5.setCompoundDrawablesWithIntrinsicBounds(r1, r2, r2, r2)
            goto L_0x007d
        L_0x0078:
            android.widget.ImageView r1 = r12.mAppResourceIcon
            r1.setVisibility(r3)
        L_0x007d:
            if (r0 == 0) goto L_0x01ec
            android.graphics.drawable.Icon r0 = r0.toIcon$1()
            android.content.Context r1 = r12.mContext
            android.content.res.Resources r1 = r1.getResources()
            android.content.res.Configuration r1 = r1.getConfiguration()
            int r1 = r1.uiMode
            r1 = r1 & 48
            r5 = 32
            r6 = 1
            if (r1 != r5) goto L_0x0098
            r1 = r6
            goto L_0x0099
        L_0x0098:
            r1 = r4
        L_0x0099:
            android.graphics.Bitmap r5 = r0.getBitmap()
            android.app.WallpaperColors r5 = android.app.WallpaperColors.fromBitmap(r5)
            boolean r7 = r5.equals(r2)
            r6 = r6 ^ r7
            if (r6 == 0) goto L_0x01e1
            com.android.systemui.media.dialog.MediaOutputAdapter r7 = r12.mAdapter
            java.util.Objects.requireNonNull(r7)
            com.android.systemui.media.dialog.MediaOutputController r7 = r7.mController
            java.util.Objects.requireNonNull(r7)
            java.util.List r5 = com.android.systemui.monet.ColorScheme.Companion.getSeedColors(r5)
            boolean r8 = r5.isEmpty()
            if (r8 != 0) goto L_0x01d9
            java.lang.Object r5 = r5.get(r4)
            java.lang.Number r5 = (java.lang.Number) r5
            int r5 = r5.intValue()
            com.android.systemui.monet.Style r8 = com.android.systemui.monet.Style.TONAL_SPOT
            com.android.internal.graphics.cam.Cam r9 = com.android.internal.graphics.cam.Cam.fromInt(r5)
            r10 = -14979341(0xffffffffff1b6ef3, float:-2.0660642E38)
            if (r5 != 0) goto L_0x00d2
            goto L_0x00dc
        L_0x00d2:
            float r9 = r9.getChroma()
            r11 = 1084227584(0x40a00000, float:5.0)
            int r9 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r9 >= 0) goto L_0x00dd
        L_0x00dc:
            r5 = r10
        L_0x00dd:
            com.android.internal.graphics.cam.Cam r5 = com.android.internal.graphics.cam.Cam.fromInt(r5)
            com.android.systemui.monet.CoreSpec r9 = r8.mo9171x334ec08f()
            java.util.Objects.requireNonNull(r9)
            com.android.systemui.monet.TonalSpec r9 = r9.f62a1
            java.util.ArrayList r9 = r9.shades(r5)
            com.android.systemui.monet.CoreSpec r10 = r8.mo9171x334ec08f()
            java.util.Objects.requireNonNull(r10)
            com.android.systemui.monet.TonalSpec r10 = r10.f63a2
            java.util.ArrayList r10 = r10.shades(r5)
            com.android.systemui.monet.CoreSpec r11 = r8.mo9171x334ec08f()
            java.util.Objects.requireNonNull(r11)
            com.android.systemui.monet.TonalSpec r11 = r11.f64a3
            r11.shades(r5)
            com.android.systemui.monet.CoreSpec r11 = r8.mo9171x334ec08f()
            java.util.Objects.requireNonNull(r11)
            com.android.systemui.monet.TonalSpec r11 = r11.f65n1
            java.util.ArrayList r11 = r11.shades(r5)
            com.android.systemui.monet.CoreSpec r8 = r8.mo9171x334ec08f()
            java.util.Objects.requireNonNull(r8)
            com.android.systemui.monet.TonalSpec r8 = r8.f66n2
            r8.shades(r5)
            r5 = 10
            if (r1 == 0) goto L_0x0162
            java.lang.Object r1 = r11.get(r5)
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            r7.mColorActiveItem = r1
            java.lang.Object r1 = r11.get(r5)
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            r7.mColorInactiveItem = r1
            r1 = 2
            java.lang.Object r5 = r9.get(r1)
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r5 = r5.intValue()
            r7.mColorSeekbarProgress = r5
            java.lang.Object r1 = r9.get(r1)
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            r7.mColorButtonBackground = r1
            java.lang.Object r1 = r10.get(r4)
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            r7.mColorItemBackground = r1
            goto L_0x01a0
        L_0x0162:
            java.lang.Object r1 = r11.get(r5)
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            r7.mColorActiveItem = r1
            r1 = 7
            java.lang.Object r1 = r9.get(r1)
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            r7.mColorInactiveItem = r1
            r1 = 3
            java.lang.Object r5 = r9.get(r1)
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r5 = r5.intValue()
            r7.mColorSeekbarProgress = r5
            java.lang.Object r1 = r9.get(r1)
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            r7.mColorButtonBackground = r1
            java.lang.Object r1 = r10.get(r4)
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            r7.mColorItemBackground = r1
        L_0x01a0:
            android.graphics.PorterDuffColorFilter r1 = new android.graphics.PorterDuffColorFilter
            com.android.systemui.media.dialog.MediaOutputAdapter r5 = r12.mAdapter
            java.util.Objects.requireNonNull(r5)
            com.android.systemui.media.dialog.MediaOutputController r5 = r5.mController
            java.util.Objects.requireNonNull(r5)
            int r5 = r5.mColorButtonBackground
            android.graphics.PorterDuff$Mode r7 = android.graphics.PorterDuff.Mode.SRC_IN
            r1.<init>(r5, r7)
            android.graphics.PorterDuffColorFilter r5 = new android.graphics.PorterDuffColorFilter
            com.android.systemui.media.dialog.MediaOutputAdapter r7 = r12.mAdapter
            java.util.Objects.requireNonNull(r7)
            com.android.systemui.media.dialog.MediaOutputController r7 = r7.mController
            java.util.Objects.requireNonNull(r7)
            int r7 = r7.mColorInactiveItem
            android.graphics.PorterDuff$Mode r8 = android.graphics.PorterDuff.Mode.SRC_IN
            r5.<init>(r7, r8)
            android.widget.Button r7 = r12.mDoneButton
            android.graphics.drawable.Drawable r7 = r7.getBackground()
            r7.setColorFilter(r1)
            android.widget.Button r1 = r12.mStopButton
            android.graphics.drawable.Drawable r1 = r1.getBackground()
            r1.setColorFilter(r5)
            goto L_0x01e1
        L_0x01d9:
            java.util.NoSuchElementException r12 = new java.util.NoSuchElementException
            java.lang.String r13 = "List is empty."
            r12.<init>(r13)
            throw r12
        L_0x01e1:
            android.widget.ImageView r1 = r12.mHeaderIcon
            r1.setVisibility(r4)
            android.widget.ImageView r1 = r12.mHeaderIcon
            r1.setImageIcon(r0)
            goto L_0x01f2
        L_0x01ec:
            android.widget.ImageView r0 = r12.mHeaderIcon
            r0.setVisibility(r3)
            r6 = r4
        L_0x01f2:
            android.widget.ImageView r0 = r12.mHeaderIcon
            int r0 = r0.getVisibility()
            if (r0 != 0) goto L_0x0216
            int r0 = r12.getHeaderIconSize()
            android.content.Context r1 = r12.mContext
            android.content.res.Resources r1 = r1.getResources()
            r5 = 2131166343(0x7f070487, float:1.7946929E38)
            int r1 = r1.getDimensionPixelSize(r5)
            android.widget.ImageView r5 = r12.mHeaderIcon
            android.widget.LinearLayout$LayoutParams r7 = new android.widget.LinearLayout$LayoutParams
            int r1 = r1 + r0
            r7.<init>(r1, r0)
            r5.setLayoutParams(r7)
        L_0x0216:
            android.widget.Button r0 = r12.mAppButton
            com.android.systemui.media.dialog.MediaOutputController r1 = r12.mMediaOutputController
            java.util.Objects.requireNonNull(r1)
            java.lang.String r5 = r1.mPackageName
            boolean r5 = r5.isEmpty()
            if (r5 == 0) goto L_0x0226
            goto L_0x024b
        L_0x0226:
            android.content.Context r5 = r1.mContext
            android.content.pm.PackageManager r5 = r5.getPackageManager()
            java.lang.String r7 = r1.mPackageName     // Catch:{ NameNotFoundException -> 0x0238 }
            r8 = 0
            android.content.pm.PackageManager$ApplicationInfoFlags r8 = android.content.pm.PackageManager.ApplicationInfoFlags.of(r8)     // Catch:{ NameNotFoundException -> 0x0238 }
            android.content.pm.ApplicationInfo r2 = r5.getApplicationInfo(r7, r8)     // Catch:{ NameNotFoundException -> 0x0238 }
        L_0x0238:
            if (r2 == 0) goto L_0x023f
            java.lang.CharSequence r1 = r5.getApplicationLabel(r2)
            goto L_0x0248
        L_0x023f:
            android.content.Context r1 = r1.mContext
            r2 = 2131952742(0x7f130466, float:1.9541935E38)
            java.lang.String r1 = r1.getString(r2)
        L_0x0248:
            r2 = r1
            java.lang.String r2 = (java.lang.String) r2
        L_0x024b:
            r0.setText(r2)
            android.widget.TextView r0 = r12.mHeaderTitle
            java.lang.CharSequence r1 = r12.getHeaderText()
            r0.setText(r1)
            java.lang.CharSequence r0 = r12.getHeaderSubtitle()
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 == 0) goto L_0x026f
            android.widget.TextView r0 = r12.mHeaderSubtitle
            r0.setVisibility(r3)
            android.widget.TextView r0 = r12.mHeaderTitle
            r1 = 8388627(0x800013, float:1.175497E-38)
            r0.setGravity(r1)
            goto L_0x027e
        L_0x026f:
            android.widget.TextView r1 = r12.mHeaderSubtitle
            r1.setVisibility(r4)
            android.widget.TextView r1 = r12.mHeaderSubtitle
            r1.setText(r0)
            android.widget.TextView r0 = r12.mHeaderTitle
            r0.setGravity(r4)
        L_0x027e:
            com.android.systemui.media.dialog.MediaOutputAdapter r0 = r12.mAdapter
            java.util.Objects.requireNonNull(r0)
            boolean r0 = r0.mIsDragging
            if (r0 != 0) goto L_0x02ac
            com.android.systemui.media.dialog.MediaOutputAdapter r0 = r12.mAdapter
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.media.dialog.MediaOutputAdapter r0 = r12.mAdapter
            java.util.Objects.requireNonNull(r0)
            int r0 = r0.mCurrentActivePosition
            if (r6 != 0) goto L_0x02a7
            if (r13 != 0) goto L_0x02a7
            if (r0 < 0) goto L_0x02a7
            com.android.systemui.media.dialog.MediaOutputAdapter r13 = r12.mAdapter
            int r13 = r13.getItemCount()
            if (r0 >= r13) goto L_0x02a7
            com.android.systemui.media.dialog.MediaOutputAdapter r13 = r12.mAdapter
            r13.notifyItemChanged(r0)
            goto L_0x02ac
        L_0x02a7:
            com.android.systemui.media.dialog.MediaOutputAdapter r13 = r12.mAdapter
            r13.notifyDataSetChanged()
        L_0x02ac:
            android.widget.Button r13 = r12.mStopButton
            int r12 = r12.getStopButtonVisibility()
            r13.setVisibility(r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.dialog.MediaOutputBaseDialog.refresh(boolean):void");
    }

    public MediaOutputBaseDialog(Context context, MediaOutputController mediaOutputController, SystemUIDialogManager systemUIDialogManager) {
        super(context, 2132018188, true, systemUIDialogManager);
        this.mMediaOutputController = mediaOutputController;
        this.mLayoutManager = new LinearLayoutManager(1);
        this.mListMaxHeight = context.getResources().getDimensionPixelSize(C1777R.dimen.media_output_dialog_list_max_height);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mDialogView = LayoutInflater.from(this.mContext).inflate(C1777R.layout.media_output_dialog, (ViewGroup) null);
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = 17;
        attributes.setFitInsetsTypes(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
        attributes.setFitInsetsSides(WindowInsets.Side.all());
        attributes.setFitInsetsIgnoringVisibility(true);
        window.setAttributes(attributes);
        window.setContentView(this.mDialogView);
        window.setTitle(" ");
        this.mHeaderTitle = (TextView) this.mDialogView.requireViewById(C1777R.C1779id.header_title);
        this.mHeaderSubtitle = (TextView) this.mDialogView.requireViewById(C1777R.C1779id.header_subtitle);
        this.mHeaderIcon = (ImageView) this.mDialogView.requireViewById(C1777R.C1779id.header_icon);
        this.mDevicesRecyclerView = (RecyclerView) this.mDialogView.requireViewById(C1777R.C1779id.list_result);
        this.mDeviceListLayout = (LinearLayout) this.mDialogView.requireViewById(C1777R.C1779id.device_list);
        this.mDoneButton = (Button) this.mDialogView.requireViewById(C1777R.C1779id.done);
        this.mStopButton = (Button) this.mDialogView.requireViewById(C1777R.C1779id.stop);
        this.mAppButton = (Button) this.mDialogView.requireViewById(C1777R.C1779id.launch_app_button);
        this.mAppResourceIcon = (ImageView) this.mDialogView.requireViewById(C1777R.C1779id.app_source_icon);
        this.mCastAppLayout = (LinearLayout) this.mDialogView.requireViewById(C1777R.C1779id.cast_app_section);
        this.mDeviceListLayout.getViewTreeObserver().addOnGlobalLayoutListener(this.mDeviceListLayoutListener);
        this.mDevicesRecyclerView.setLayoutManager(this.mLayoutManager);
        this.mDevicesRecyclerView.setAdapter(this.mAdapter);
        this.mHeaderIcon.setOnClickListener(new ScreenshotView$$ExternalSyntheticLambda9(this, 1));
        this.mDoneButton.setOnClickListener(new PipMenuView$$ExternalSyntheticLambda2(this, 1));
        this.mStopButton.setOnClickListener(new PipMenuView$$ExternalSyntheticLambda3(this, 1));
        this.mAppButton.setOnClickListener(new PipMenuView$$ExternalSyntheticLambda4(this, 1));
    }

    public final void onStart() {
        super.onStart();
        MediaOutputController mediaOutputController = this.mMediaOutputController;
        Objects.requireNonNull(mediaOutputController);
        mediaOutputController.mMediaDevices.clear();
        if (!TextUtils.isEmpty(mediaOutputController.mPackageName)) {
            Iterator<MediaController> it = mediaOutputController.mMediaSessionManager.getActiveSessions((ComponentName) null).iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                MediaController next = it.next();
                if (TextUtils.equals(next.getPackageName(), mediaOutputController.mPackageName)) {
                    mediaOutputController.mMediaController = next;
                    next.unregisterCallback(mediaOutputController.mCb);
                    mediaOutputController.mMediaController.registerCallback(mediaOutputController.mCb);
                    break;
                }
            }
        }
        if (mediaOutputController.mMediaController == null && MediaOutputController.DEBUG) {
            ExifInterface$$ExternalSyntheticOutline2.m15m(VendorAtomValue$$ExternalSyntheticOutline1.m1m("No media controller for "), mediaOutputController.mPackageName, "MediaOutputController");
        }
        LocalMediaManager localMediaManager = mediaOutputController.mLocalMediaManager;
        if (localMediaManager != null) {
            mediaOutputController.mCallback = this;
            localMediaManager.mCallbacks.remove(mediaOutputController);
            mediaOutputController.mLocalMediaManager.stopScan();
            LocalMediaManager localMediaManager2 = mediaOutputController.mLocalMediaManager;
            Objects.requireNonNull(localMediaManager2);
            localMediaManager2.mCallbacks.add(mediaOutputController);
            mediaOutputController.mLocalMediaManager.startScan();
        } else if (MediaOutputController.DEBUG) {
            ExifInterface$$ExternalSyntheticOutline2.m15m(VendorAtomValue$$ExternalSyntheticOutline1.m1m("No local media manager "), mediaOutputController.mPackageName, "MediaOutputController");
        }
    }

    public final void onStop() {
        super.onStop();
        MediaOutputController mediaOutputController = this.mMediaOutputController;
        Objects.requireNonNull(mediaOutputController);
        MediaController mediaController = mediaOutputController.mMediaController;
        if (mediaController != null) {
            mediaController.unregisterCallback(mediaOutputController.mCb);
        }
        LocalMediaManager localMediaManager = mediaOutputController.mLocalMediaManager;
        if (localMediaManager != null) {
            localMediaManager.mCallbacks.remove(mediaOutputController);
            mediaOutputController.mLocalMediaManager.stopScan();
        }
        mediaOutputController.mMediaDevices.clear();
    }
}
