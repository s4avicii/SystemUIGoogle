package com.android.p012wm.shell.pip.phone;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.IAccessibilityInteractionConnectionCallback;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.common.DisplayLayout;
import com.android.p012wm.shell.pip.PipBoundsState;
import com.android.p012wm.shell.pip.PipSnapAlgorithm;
import com.android.p012wm.shell.pip.PipTaskOrganizer;
import com.android.p012wm.shell.pip.phone.PipAccessibilityInteractionConnection;
import com.android.systemui.p006qs.QSTileHost$$ExternalSyntheticLambda1;
import com.android.systemui.wmshell.WMShell$$ExternalSyntheticLambda6;
import com.android.systemui.wmshell.WMShell$$ExternalSyntheticLambda7;
import java.util.Objects;

/* renamed from: com.android.wm.shell.pip.phone.PipAccessibilityInteractionConnection$PipAccessibilityInteractionConnectionImpl$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1894xdda2519d implements Runnable {
    public final /* synthetic */ PipAccessibilityInteractionConnection.PipAccessibilityInteractionConnectionImpl f$0;
    public final /* synthetic */ long f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ Bundle f$3;
    public final /* synthetic */ int f$4;
    public final /* synthetic */ IAccessibilityInteractionConnectionCallback f$5;

    public /* synthetic */ C1894xdda2519d(PipAccessibilityInteractionConnection.PipAccessibilityInteractionConnectionImpl pipAccessibilityInteractionConnectionImpl, long j, int i, Bundle bundle, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2) {
        this.f$0 = pipAccessibilityInteractionConnectionImpl;
        this.f$1 = j;
        this.f$2 = i;
        this.f$3 = bundle;
        this.f$4 = i2;
        this.f$5 = iAccessibilityInteractionConnectionCallback;
    }

    public final void run() {
        int i;
        int i2;
        PipAccessibilityInteractionConnection.PipAccessibilityInteractionConnectionImpl pipAccessibilityInteractionConnectionImpl = this.f$0;
        long j = this.f$1;
        int i3 = this.f$2;
        Bundle bundle = this.f$3;
        int i4 = this.f$4;
        IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback = this.f$5;
        Objects.requireNonNull(pipAccessibilityInteractionConnectionImpl);
        PipAccessibilityInteractionConnection pipAccessibilityInteractionConnection = PipAccessibilityInteractionConnection.this;
        Objects.requireNonNull(pipAccessibilityInteractionConnection);
        boolean z = true;
        if (j == AccessibilityNodeInfo.ROOT_NODE_ID) {
            if (i3 == C1777R.C1779id.action_pip_resize) {
                if (pipAccessibilityInteractionConnection.mPipBoundsState.getBounds().width() == pipAccessibilityInteractionConnection.mNormalBounds.width() && pipAccessibilityInteractionConnection.mPipBoundsState.getBounds().height() == pipAccessibilityInteractionConnection.mNormalBounds.height()) {
                    PipSnapAlgorithm pipSnapAlgorithm = pipAccessibilityInteractionConnection.mSnapAlgorithm;
                    Rect bounds = pipAccessibilityInteractionConnection.mPipBoundsState.getBounds();
                    Rect rect = pipAccessibilityInteractionConnection.mNormalMovementBounds;
                    Objects.requireNonNull(pipSnapAlgorithm);
                    float snapFraction = pipSnapAlgorithm.getSnapFraction(bounds, rect, 0);
                    PipSnapAlgorithm pipSnapAlgorithm2 = pipAccessibilityInteractionConnection.mSnapAlgorithm;
                    Rect rect2 = pipAccessibilityInteractionConnection.mExpandedBounds;
                    Rect rect3 = pipAccessibilityInteractionConnection.mExpandedMovementBounds;
                    Objects.requireNonNull(pipSnapAlgorithm2);
                    PipSnapAlgorithm.applySnapFraction(rect2, rect3, snapFraction);
                    PipTaskOrganizer pipTaskOrganizer = pipAccessibilityInteractionConnection.mTaskOrganizer;
                    Rect rect4 = pipAccessibilityInteractionConnection.mExpandedBounds;
                    WMShell$$ExternalSyntheticLambda6 wMShell$$ExternalSyntheticLambda6 = new WMShell$$ExternalSyntheticLambda6(pipAccessibilityInteractionConnection, 3);
                    Objects.requireNonNull(pipTaskOrganizer);
                    pipTaskOrganizer.scheduleFinishResizePip(rect4, 0, wMShell$$ExternalSyntheticLambda6);
                } else {
                    PipSnapAlgorithm pipSnapAlgorithm3 = pipAccessibilityInteractionConnection.mSnapAlgorithm;
                    Rect bounds2 = pipAccessibilityInteractionConnection.mPipBoundsState.getBounds();
                    Rect rect5 = pipAccessibilityInteractionConnection.mExpandedMovementBounds;
                    Objects.requireNonNull(pipSnapAlgorithm3);
                    float snapFraction2 = pipSnapAlgorithm3.getSnapFraction(bounds2, rect5, 0);
                    PipSnapAlgorithm pipSnapAlgorithm4 = pipAccessibilityInteractionConnection.mSnapAlgorithm;
                    Rect rect6 = pipAccessibilityInteractionConnection.mNormalBounds;
                    Rect rect7 = pipAccessibilityInteractionConnection.mNormalMovementBounds;
                    Objects.requireNonNull(pipSnapAlgorithm4);
                    PipSnapAlgorithm.applySnapFraction(rect6, rect7, snapFraction2);
                    PipTaskOrganizer pipTaskOrganizer2 = pipAccessibilityInteractionConnection.mTaskOrganizer;
                    Rect rect8 = pipAccessibilityInteractionConnection.mNormalBounds;
                    WMShell$$ExternalSyntheticLambda7 wMShell$$ExternalSyntheticLambda7 = new WMShell$$ExternalSyntheticLambda7(pipAccessibilityInteractionConnection, 3);
                    Objects.requireNonNull(pipTaskOrganizer2);
                    pipTaskOrganizer2.scheduleFinishResizePip(rect8, 0, wMShell$$ExternalSyntheticLambda7);
                }
            } else if (i3 == C1777R.C1779id.action_pip_stash) {
                PipMotionHelper pipMotionHelper = pipAccessibilityInteractionConnection.mMotionHelper;
                Objects.requireNonNull(pipMotionHelper);
                Rect rect9 = new Rect();
                PipBoundsState pipBoundsState = pipMotionHelper.mPipBoundsState;
                Objects.requireNonNull(pipBoundsState);
                DisplayLayout displayLayout = pipBoundsState.mDisplayLayout;
                Objects.requireNonNull(displayLayout);
                Rect rect10 = displayLayout.mStableInsets;
                int i5 = pipMotionHelper.mPipBoundsState.getBounds().left;
                PipBoundsState pipBoundsState2 = pipMotionHelper.mPipBoundsState;
                Objects.requireNonNull(pipBoundsState2);
                if (i5 == pipBoundsState2.mMovementBounds.left) {
                    i = 1;
                } else {
                    i = 2;
                }
                if (i == 1) {
                    PipBoundsState pipBoundsState3 = pipMotionHelper.mPipBoundsState;
                    Objects.requireNonNull(pipBoundsState3);
                    i2 = (pipBoundsState3.mStashOffset - pipMotionHelper.mPipBoundsState.getBounds().width()) + rect10.left;
                } else {
                    int i6 = pipMotionHelper.mPipBoundsState.getDisplayBounds().right;
                    PipBoundsState pipBoundsState4 = pipMotionHelper.mPipBoundsState;
                    Objects.requireNonNull(pipBoundsState4);
                    i2 = (i6 - pipBoundsState4.mStashOffset) - rect10.right;
                }
                float f = (float) i2;
                rect9.set((int) f, pipMotionHelper.mPipBoundsState.getBounds().top, (int) (f + ((float) pipMotionHelper.mPipBoundsState.getBounds().width())), pipMotionHelper.mPipBoundsState.getBounds().bottom);
                pipMotionHelper.mPipTaskOrganizer.scheduleAnimateResizePip(rect9, 250, 8, (QSTileHost$$ExternalSyntheticLambda1) null);
                PipBoundsState pipBoundsState5 = pipMotionHelper.mPipBoundsState;
                Objects.requireNonNull(pipBoundsState5);
                PipBoundsState.MotionBoundsState motionBoundsState = pipBoundsState5.mMotionBoundsState;
                Objects.requireNonNull(motionBoundsState);
                motionBoundsState.mAnimatingToBounds.set(rect9);
                pipMotionHelper.mFloatingContentCoordinator.onContentMoved(pipMotionHelper);
                pipMotionHelper.mPipBoundsState.setStashed(i);
            } else if (i3 == C1777R.C1779id.action_pip_unstash) {
                pipAccessibilityInteractionConnection.mUnstashCallback.run();
                pipAccessibilityInteractionConnection.mPipBoundsState.setStashed(0);
            } else if (i3 == 16) {
                PipTouchHandler$$ExternalSyntheticLambda2 pipTouchHandler$$ExternalSyntheticLambda2 = (PipTouchHandler$$ExternalSyntheticLambda2) pipAccessibilityInteractionConnection.mCallbacks;
                Objects.requireNonNull(pipTouchHandler$$ExternalSyntheticLambda2);
                PipTouchHandler pipTouchHandler = (PipTouchHandler) pipTouchHandler$$ExternalSyntheticLambda2.f$0;
                Objects.requireNonNull(pipTouchHandler);
                pipTouchHandler.mMenuController.showMenu(1, pipTouchHandler.mPipBoundsState.getBounds(), true, pipTouchHandler.willResizeMenu());
            } else if (i3 == 262144) {
                PipMotionHelper pipMotionHelper2 = pipAccessibilityInteractionConnection.mMotionHelper;
                Objects.requireNonNull(pipMotionHelper2);
                pipMotionHelper2.expandLeavePip(false, false);
            } else if (i3 == 1048576) {
                pipAccessibilityInteractionConnection.mMotionHelper.dismissPip();
            } else if (i3 == 16908354) {
                int i7 = bundle.getInt("ACTION_ARGUMENT_MOVE_WINDOW_X");
                int i8 = bundle.getInt("ACTION_ARGUMENT_MOVE_WINDOW_Y");
                new Rect().set(pipAccessibilityInteractionConnection.mPipBoundsState.getBounds());
                pipAccessibilityInteractionConnection.mTmpBounds.offsetTo(i7, i8);
                PipMotionHelper pipMotionHelper3 = pipAccessibilityInteractionConnection.mMotionHelper;
                Rect rect11 = pipAccessibilityInteractionConnection.mTmpBounds;
                Objects.requireNonNull(pipMotionHelper3);
                pipMotionHelper3.movePip(rect11, false);
            }
            iAccessibilityInteractionConnectionCallback.setPerformAccessibilityActionResult(z, i4);
        }
        z = false;
        try {
            iAccessibilityInteractionConnectionCallback.setPerformAccessibilityActionResult(z, i4);
        } catch (RemoteException unused) {
        }
    }
}
