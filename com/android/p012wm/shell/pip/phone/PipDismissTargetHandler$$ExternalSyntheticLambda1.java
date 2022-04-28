package com.android.p012wm.shell.pip.phone;

import android.graphics.PointF;
import android.graphics.Rect;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.animation.FloatProperties;
import com.android.p012wm.shell.animation.PhysicsAnimator;
import com.android.p012wm.shell.common.magnetictarget.MagnetizedObject;
import com.android.p012wm.shell.pip.PipBoundsState;
import java.util.Objects;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function5;

/* renamed from: com.android.wm.shell.pip.phone.PipDismissTargetHandler$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PipDismissTargetHandler$$ExternalSyntheticLambda1 implements Function5 {
    public final /* synthetic */ PipDismissTargetHandler f$0;

    public /* synthetic */ PipDismissTargetHandler$$ExternalSyntheticLambda1(PipDismissTargetHandler pipDismissTargetHandler) {
        this.f$0 = pipDismissTargetHandler;
    }

    public final void invoke(MagnetizedObject.MagneticTarget magneticTarget, Float f, Float f2, Boolean bool, Object obj) {
        PipDismissTargetHandler pipDismissTargetHandler = this.f$0;
        Function0 function0 = (Function0) obj;
        Objects.requireNonNull(pipDismissTargetHandler);
        if (pipDismissTargetHandler.mEnableDismissDragToEdge) {
            PipMotionHelper pipMotionHelper = pipDismissTargetHandler.mMotionHelper;
            float floatValue = f.floatValue();
            float floatValue2 = f2.floatValue();
            bool.booleanValue();
            Objects.requireNonNull(pipMotionHelper);
            PointF pointF = magneticTarget.centerOnScreen;
            float dimensionPixelSize = ((float) pipMotionHelper.mContext.getResources().getDimensionPixelSize(C1777R.dimen.dismiss_circle_size)) * 0.85f;
            float width = dimensionPixelSize / (((float) pipMotionHelper.getBounds().width()) / ((float) pipMotionHelper.getBounds().height()));
            float f3 = pointF.x - (dimensionPixelSize / 2.0f);
            float f4 = pointF.y - (width / 2.0f);
            PipBoundsState pipBoundsState = pipMotionHelper.mPipBoundsState;
            Objects.requireNonNull(pipBoundsState);
            PipBoundsState.MotionBoundsState motionBoundsState = pipBoundsState.mMotionBoundsState;
            Objects.requireNonNull(motionBoundsState);
            if (!(!motionBoundsState.mBoundsInMotion.isEmpty())) {
                PipBoundsState pipBoundsState2 = pipMotionHelper.mPipBoundsState;
                Objects.requireNonNull(pipBoundsState2);
                PipBoundsState.MotionBoundsState motionBoundsState2 = pipBoundsState2.mMotionBoundsState;
                Rect bounds = pipMotionHelper.getBounds();
                Objects.requireNonNull(motionBoundsState2);
                motionBoundsState2.mBoundsInMotion.set(bounds);
            }
            PhysicsAnimator<Rect> physicsAnimator = pipMotionHelper.mTemporaryBoundsPhysicsAnimator;
            physicsAnimator.spring(FloatProperties.RECT_X, f3, floatValue, pipMotionHelper.mAnimateToDismissSpringConfig);
            physicsAnimator.spring(FloatProperties.RECT_Y, f4, floatValue2, pipMotionHelper.mAnimateToDismissSpringConfig);
            physicsAnimator.spring(FloatProperties.RECT_WIDTH, dimensionPixelSize, 0.0f, pipMotionHelper.mAnimateToDismissSpringConfig);
            physicsAnimator.spring(FloatProperties.RECT_HEIGHT, width, 0.0f, pipMotionHelper.mAnimateToDismissSpringConfig);
            physicsAnimator.endActions.addAll(ArraysKt___ArraysKt.filterNotNull(new Function0[]{function0}));
            pipMotionHelper.startBoundsAnimator(f3, f4, (Runnable) null);
        }
    }
}
