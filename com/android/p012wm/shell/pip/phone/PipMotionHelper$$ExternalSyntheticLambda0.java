package com.android.p012wm.shell.pip.phone;

import android.graphics.Rect;
import com.android.p012wm.shell.ShellTaskOrganizer$$ExternalSyntheticLambda1;
import com.android.p012wm.shell.animation.PhysicsAnimator;
import com.android.p012wm.shell.pip.PipBoundsState;
import com.android.p012wm.shell.pip.PipTaskOrganizer;
import java.util.Objects;

/* renamed from: com.android.wm.shell.pip.phone.PipMotionHelper$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PipMotionHelper$$ExternalSyntheticLambda0 implements PhysicsAnimator.UpdateListener {
    public final /* synthetic */ PipMotionHelper f$0;

    public /* synthetic */ PipMotionHelper$$ExternalSyntheticLambda0(PipMotionHelper pipMotionHelper) {
        this.f$0 = pipMotionHelper;
    }

    public final void onAnimationUpdateForProperty(Object obj) {
        PipMotionHelper pipMotionHelper = this.f$0;
        Rect rect = (Rect) obj;
        Objects.requireNonNull(pipMotionHelper);
        PipBoundsState pipBoundsState = pipMotionHelper.mPipBoundsState;
        Objects.requireNonNull(pipBoundsState);
        PipBoundsState.MotionBoundsState motionBoundsState = pipBoundsState.mMotionBoundsState;
        Objects.requireNonNull(motionBoundsState);
        if (!motionBoundsState.mBoundsInMotion.isEmpty()) {
            PipTaskOrganizer pipTaskOrganizer = pipMotionHelper.mPipTaskOrganizer;
            Rect bounds = pipMotionHelper.getBounds();
            PipBoundsState pipBoundsState2 = pipMotionHelper.mPipBoundsState;
            Objects.requireNonNull(pipBoundsState2);
            PipBoundsState.MotionBoundsState motionBoundsState2 = pipBoundsState2.mMotionBoundsState;
            Objects.requireNonNull(motionBoundsState2);
            Rect rect2 = motionBoundsState2.mBoundsInMotion;
            Objects.requireNonNull(pipTaskOrganizer);
            pipTaskOrganizer.scheduleUserResizePip(bounds, rect2, 0.0f, (ShellTaskOrganizer$$ExternalSyntheticLambda1) null);
        }
    }
}
