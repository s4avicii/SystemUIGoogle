package com.android.p012wm.shell.bubbles;

import android.content.BroadcastReceiver;
import android.view.animation.PathInterpolator;
import androidx.mediarouter.media.MediaRouter;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.p012wm.shell.TaskView;
import com.android.p012wm.shell.pip.PipAnimationController;
import com.android.p012wm.shell.splitscreen.MainStage;
import com.android.p012wm.shell.splitscreen.StageCoordinator;
import com.android.systemui.navigationbar.buttons.KeyButtonRipple;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.p006qs.tiles.dialog.InternetDialog;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.phone.PhoneStatusBarPolicy;
import com.android.systemui.statusbar.phone.StatusBar;
import com.google.android.systemui.assist.uihints.NgaUiController;
import com.google.android.systemui.elmyra.gates.CameraVisibility;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda15 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda15 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda15(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BubbleStackView bubbleStackView = (BubbleStackView) this.f$0;
                Objects.requireNonNull(bubbleStackView);
                bubbleStackView.mManageMenu.setVisibility(4);
                BubbleViewProvider bubbleViewProvider = bubbleStackView.mExpandedBubble;
                if (bubbleViewProvider != null && bubbleViewProvider.getExpandedView() != null) {
                    BubbleExpandedView expandedView = bubbleStackView.mExpandedBubble.getExpandedView();
                    Objects.requireNonNull(expandedView);
                    TaskView taskView = expandedView.mTaskView;
                    if (taskView != null) {
                        taskView.onLocationChanged();
                        return;
                    }
                    return;
                }
                return;
            case 1:
                ((MediaRouter.PrepareTransferNotifier) this.f$0).finishTransfer();
                return;
            case 2:
                KeyguardUpdateMonitor.C054115 r3 = (KeyguardUpdateMonitor.C054115) this.f$0;
                Objects.requireNonNull(r3);
                KeyguardUpdateMonitor.this.updateBiometricListeningState(2);
                return;
            case 3:
                PathInterpolator pathInterpolator = KeyButtonRipple.ALPHA_OUT_INTERPOLATOR;
                ((KeyButtonRipple) this.f$0).enterSoftware();
                return;
            case 4:
                InternetDialog internetDialog = (InternetDialog) this.f$0;
                boolean z = InternetDialog.DEBUG;
                Objects.requireNonNull(internetDialog);
                internetDialog.updateDialog(true);
                return;
            case 5:
                PhoneStatusBarPolicy phoneStatusBarPolicy = (PhoneStatusBarPolicy) this.f$0;
                boolean z2 = PhoneStatusBarPolicy.DEBUG;
                Objects.requireNonNull(phoneStatusBarPolicy);
                phoneStatusBarPolicy.mIconController.setIconAccessibilityLiveRegion(phoneStatusBarPolicy.mSlotScreenRecord, 0);
                return;
            case FalsingManager.VERSION:
                StatusBar.C153310 r32 = (StatusBar.C153310) this.f$0;
                Objects.requireNonNull(r32);
                StatusBar statusBar = StatusBar.this;
                statusBar.mCommandQueueCallbacks.onCameraLaunchGestureDetected(statusBar.mLastCameraLaunchSource);
                return;
            case 7:
                for (Runnable run : (Runnable[]) this.f$0) {
                    run.run();
                }
                return;
            case 8:
                PipAnimationController.PipTransitionAnimator pipTransitionAnimator = (PipAnimationController.PipTransitionAnimator) this.f$0;
                Objects.requireNonNull(pipTransitionAnimator);
                pipTransitionAnimator.mContentOverlay = null;
                return;
            case 9:
                StageCoordinator stageCoordinator = (StageCoordinator) this.f$0;
                Objects.requireNonNull(stageCoordinator);
                MainStage mainStage = stageCoordinator.mMainStage;
                Objects.requireNonNull(mainStage);
                if (!mainStage.mIsActive) {
                    stageCoordinator.mSplitLayout.release();
                    stageCoordinator.mSplitLayout.resetDividerPosition();
                    stageCoordinator.mTopStageAfterFoldDismiss = -1;
                    return;
                }
                return;
            case 10:
                ((BroadcastReceiver.PendingResult) this.f$0).finish();
                return;
            case QSTileImpl.C1034H.STALE:
                boolean z3 = NgaUiController.VERBOSE;
                ((NgaUiController) this.f$0).closeNgaUi();
                return;
            default:
                CameraVisibility cameraVisibility = (CameraVisibility) this.f$0;
                Objects.requireNonNull(cameraVisibility);
                boolean isCameraShowing = cameraVisibility.isCameraShowing();
                if (cameraVisibility.mCameraShowing != isCameraShowing) {
                    cameraVisibility.mCameraShowing = isCameraShowing;
                    cameraVisibility.notifyListener();
                    return;
                }
                return;
        }
    }
}
