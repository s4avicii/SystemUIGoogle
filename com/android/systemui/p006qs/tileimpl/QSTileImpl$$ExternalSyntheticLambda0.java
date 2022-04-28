package com.android.systemui.p006qs.tileimpl;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.window.WindowContainerTransaction;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;
import com.android.keyguard.KeyguardPasswordViewController;
import com.android.p012wm.shell.onehanded.OneHandedController;
import com.android.p012wm.shell.pip.PipAnimationController;
import com.android.p012wm.shell.pip.phone.PipController;
import com.android.p012wm.shell.pip.phone.PipMotionHelper;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.screenshot.ScreenshotEvent;
import com.android.systemui.screenshot.ScrollCaptureClient;
import com.android.systemui.screenshot.ScrollCaptureController;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.wmshell.WMShell;
import com.android.systemui.wmshell.WMShell$8$$ExternalSyntheticLambda0;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tileimpl.QSTileImpl$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class QSTileImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ QSTileImpl$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                QSTileImpl qSTileImpl = (QSTileImpl) this.f$0;
                Objects.requireNonNull(qSTileImpl);
                LifecycleRegistry lifecycleRegistry = qSTileImpl.mLifecycle;
                Objects.requireNonNull(lifecycleRegistry);
                if (!lifecycleRegistry.mState.equals(Lifecycle.State.DESTROYED)) {
                    qSTileImpl.mLifecycle.setCurrentState(Lifecycle.State.RESUMED);
                    if (qSTileImpl.mReadyState == 0) {
                        qSTileImpl.mReadyState = 1;
                    }
                    qSTileImpl.refreshState((Object) null);
                    return;
                }
                return;
            case 1:
                KeyguardPasswordViewController.m157$r8$lambda$jjE1OxCwl_IFkmzkDD3tZQdL_8((KeyguardPasswordViewController) this.f$0);
                return;
            case 2:
                KeyguardViewMediator keyguardViewMediator = (KeyguardViewMediator) this.f$0;
                boolean z = KeyguardViewMediator.DEBUG;
                Objects.requireNonNull(keyguardViewMediator);
                Log.e("KeyguardViewMediator", "mHideAnimationFinishedRunnable#run");
                keyguardViewMediator.mHideAnimationRunning = false;
                keyguardViewMediator.tryKeyguardDone();
                return;
            case 3:
                OverviewProxyService.C10571 r6 = (OverviewProxyService.C10571) this.f$0;
                int i = OverviewProxyService.C10571.$r8$clinit;
                Objects.requireNonNull(r6);
                OverviewProxyService.this.mCommandQueue.handleSystemKey(281);
                return;
            case 4:
                ScrollCaptureController scrollCaptureController = (ScrollCaptureController) this.f$0;
                Objects.requireNonNull(scrollCaptureController);
                scrollCaptureController.mCancelled = true;
                CallbackToFutureAdapter.SafeFuture safeFuture = scrollCaptureController.mSessionFuture;
                if (safeFuture != null) {
                    safeFuture.cancel(true);
                }
                ListenableFuture<ScrollCaptureClient.CaptureResult> listenableFuture = scrollCaptureController.mTileFuture;
                if (listenableFuture != null) {
                    listenableFuture.cancel(true);
                }
                ScrollCaptureClient.Session session = scrollCaptureController.mSession;
                if (session != null) {
                    session.end();
                }
                scrollCaptureController.mEventLogger.log(ScreenshotEvent.SCREENSHOT_LONG_SCREENSHOT_FAILURE, 0, scrollCaptureController.mWindowOwner);
                return;
            case 5:
                StatusBar statusBar = (StatusBar) this.f$0;
                long[] jArr = StatusBar.CAMERA_LAUNCH_GESTURE_VIBRATION_TIMINGS;
                Objects.requireNonNull(statusBar);
                try {
                    statusBar.mBarService.onPanelHidden();
                    return;
                } catch (RemoteException unused) {
                    return;
                }
            case FalsingManager.VERSION:
                ((View) this.f$0).callOnClick();
                return;
            case 7:
                OneHandedController oneHandedController = (OneHandedController) this.f$0;
                Objects.requireNonNull(oneHandedController);
                WMShell.C17718 r62 = (WMShell.C17718) oneHandedController.mEventCallback;
                Objects.requireNonNull(r62);
                WMShell.this.mSysUiMainExecutor.execute(new WMShell$8$$ExternalSyntheticLambda0(r62, 0));
                return;
            case 8:
                PipAnimationController.PipTransitionAnimator pipTransitionAnimator = (PipAnimationController.PipTransitionAnimator) this.f$0;
                Objects.requireNonNull(pipTransitionAnimator);
                pipTransitionAnimator.mContentOverlay = null;
                return;
            case 9:
                PipController pipController = (PipController) this.f$0;
                Objects.requireNonNull(pipController);
                pipController.updateMovementBounds((Rect) null, false, false, false, (WindowContainerTransaction) null);
                return;
            case 10:
                ((PipMotionHelper) this.f$0).dismissPip();
                return;
            default:
                ((ValueAnimator) this.f$0).start();
                return;
        }
    }
}
