package com.android.systemui.wmshell;

import android.content.Context;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.telephony.PinResult;
import android.util.Log;
import android.view.Choreographer;
import android.view.InputChannel;
import android.window.StartingWindowRemovalInfo;
import androidx.core.graphics.drawable.IconCompat;
import androidx.recyclerview.R$dimen;
import com.android.keyguard.KeyguardSimPukViewController;
import com.android.p012wm.shell.common.RemoteCallable;
import com.android.p012wm.shell.common.SingleInstanceRemoteListener;
import com.android.p012wm.shell.pip.phone.PipInputConsumer;
import com.android.p012wm.shell.pip.phone.PipTouchHandler;
import com.android.p012wm.shell.protolog.ShellProtoLogCache;
import com.android.p012wm.shell.protolog.ShellProtoLogGroup;
import com.android.p012wm.shell.protolog.ShellProtoLogImpl;
import com.android.p012wm.shell.startingsurface.StartingSurfaceDrawer;
import com.android.p012wm.shell.startingsurface.StartingWindowController;
import com.android.settingslib.media.LocalMediaManager;
import com.android.settingslib.media.MediaDevice;
import com.android.systemui.media.dialog.C0901x9ea0901;
import com.android.systemui.media.dialog.MediaOutputBaseAdapter;
import com.android.systemui.media.dialog.MediaOutputController;
import com.android.systemui.plugins.OverlayPlugin;
import com.android.systemui.screenshot.ImageLoader$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.wmshell.BubblesManager;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubblesManager$5$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ BubblesManager$5$$ExternalSyntheticLambda0(Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    public final void run() {
        Bitmap bitmap;
        boolean z;
        boolean z2;
        int i;
        int i2 = 1;
        switch (this.$r8$classId) {
            case 0:
                BubblesManager.C17525 r0 = (BubblesManager.C17525) this.f$0;
                Objects.requireNonNull(r0);
                NotificationEntry entry = BubblesManager.this.mCommonNotifCollection.getEntry((String) this.f$1);
                if (entry != null) {
                    BubblesManager.this.mNotificationGroupManager.onEntryRemoved(entry);
                    return;
                }
                return;
            case 1:
                KeyguardSimPukViewController.CheckSimPuk checkSimPuk = (KeyguardSimPukViewController.CheckSimPuk) this.f$0;
                int i3 = KeyguardSimPukViewController.CheckSimPuk.$r8$clinit;
                Objects.requireNonNull(checkSimPuk);
                checkSimPuk.onSimLockChangedResponse((PinResult) this.f$1);
                return;
            case 2:
                MediaOutputBaseAdapter.MediaDeviceBaseViewHolder mediaDeviceBaseViewHolder = (MediaOutputBaseAdapter.MediaDeviceBaseViewHolder) this.f$0;
                MediaDevice mediaDevice = (MediaDevice) this.f$1;
                int i4 = MediaOutputBaseAdapter.MediaDeviceBaseViewHolder.$r8$clinit;
                Objects.requireNonNull(mediaDeviceBaseViewHolder);
                MediaOutputController mediaOutputController = MediaOutputBaseAdapter.this.mController;
                Objects.requireNonNull(mediaOutputController);
                Drawable icon = mediaDevice.getIcon();
                if (icon == null) {
                    if (MediaOutputController.DEBUG) {
                        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("getDeviceIconCompat() device : ");
                        m.append(mediaDevice.getName());
                        m.append(", drawable is null");
                        Log.d("MediaOutputController", m.toString());
                    }
                    icon = mediaOutputController.mContext.getDrawable(17302329);
                }
                boolean z3 = icon instanceof BitmapDrawable;
                if (!z3) {
                    LocalMediaManager localMediaManager = mediaOutputController.mLocalMediaManager;
                    Objects.requireNonNull(localMediaManager);
                    boolean equals = localMediaManager.mCurrentConnectedDevice.getId().equals(mediaDevice.getId());
                    if (mediaOutputController.getSelectedMediaDevice().size() <= 1 || !mediaOutputController.getSelectedMediaDevice().contains(mediaDevice)) {
                        z = false;
                    } else {
                        z = true;
                    }
                    if ((mediaOutputController.hasAdjustVolumeUserRestriction() || !equals || mediaOutputController.isTransferring()) && !z) {
                        z2 = false;
                    } else {
                        z2 = true;
                    }
                    if (z2) {
                        i = mediaOutputController.mColorActiveItem;
                    } else {
                        i = mediaOutputController.mColorInactiveItem;
                    }
                    icon.setColorFilter(new PorterDuffColorFilter(i, PorterDuff.Mode.SRC_IN));
                }
                if (z3) {
                    bitmap = ((BitmapDrawable) icon).getBitmap();
                } else {
                    int intrinsicWidth = icon.getIntrinsicWidth();
                    int intrinsicHeight = icon.getIntrinsicHeight();
                    if (intrinsicWidth <= 0) {
                        intrinsicWidth = 1;
                    }
                    if (intrinsicHeight > 0) {
                        i2 = intrinsicHeight;
                    }
                    bitmap = Bitmap.createBitmap(intrinsicWidth, i2, Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    icon.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                    icon.draw(canvas);
                }
                IconCompat createWithBitmap = IconCompat.createWithBitmap(bitmap);
                Context context = MediaOutputBaseAdapter.this.mContext;
                R$dimen.postOnMainThread(new C0901x9ea0901(mediaDeviceBaseViewHolder, mediaDevice, createWithBitmap.toIcon$1()));
                return;
            case 3:
                StatusBar.C15432 r02 = (StatusBar.C15432) this.f$0;
                OverlayPlugin overlayPlugin = (OverlayPlugin) this.f$1;
                Objects.requireNonNull(r02);
                StatusBar statusBar = StatusBar.this;
                Objects.requireNonNull(statusBar);
                overlayPlugin.setup(statusBar.mNotificationShadeWindowView, StatusBar.this.getNavigationBarView(), new StatusBar.C15432.Callback(overlayPlugin), StatusBar.this.mDozeParameters);
                return;
            case 4:
                SingleInstanceRemoteListener.C18481 r03 = (SingleInstanceRemoteListener.C18481) this.f$0;
                Objects.requireNonNull(r03);
                SingleInstanceRemoteListener singleInstanceRemoteListener = SingleInstanceRemoteListener.this;
                singleInstanceRemoteListener.mListener = null;
                singleInstanceRemoteListener.mOnUnregisterCallback.accept((RemoteCallable) this.f$1);
                return;
            case 5:
                PipInputConsumer pipInputConsumer = (PipInputConsumer) this.f$0;
                Objects.requireNonNull(pipInputConsumer);
                pipInputConsumer.mInputEventReceiver = new PipInputConsumer.InputEventReceiver((InputChannel) this.f$1, Looper.myLooper(), Choreographer.getSfInstance());
                PipInputConsumer.RegistrationListener registrationListener = pipInputConsumer.mRegistrationListener;
                if (registrationListener != null) {
                    ((PipTouchHandler) ((ImageLoader$$ExternalSyntheticLambda0) registrationListener).f$0).onRegistrationChanged(true);
                    return;
                }
                return;
            default:
                StartingWindowController startingWindowController = (StartingWindowController) this.f$0;
                StartingWindowRemovalInfo startingWindowRemovalInfo = (StartingWindowRemovalInfo) this.f$1;
                Objects.requireNonNull(startingWindowController);
                StartingSurfaceDrawer startingSurfaceDrawer = startingWindowController.mStartingSurfaceDrawer;
                Objects.requireNonNull(startingSurfaceDrawer);
                if (ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled) {
                    ShellProtoLogImpl.m80v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, -958966913, 1, (String) null, Long.valueOf((long) startingWindowRemovalInfo.taskId));
                }
                startingSurfaceDrawer.removeWindowSynced(startingWindowRemovalInfo, false);
                return;
        }
    }
}
