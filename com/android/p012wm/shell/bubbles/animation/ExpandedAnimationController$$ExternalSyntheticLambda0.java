package com.android.p012wm.shell.bubbles.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.graphics.PointF;
import android.view.View;
import android.view.animation.LinearInterpolator;
import androidx.dynamicanimation.animation.DynamicAnimation;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda7;
import com.android.p012wm.shell.animation.Interpolators;
import com.android.p012wm.shell.bubbles.animation.PhysicsAnimationLayout;
import java.util.HashMap;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.animation.ExpandedAnimationController$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ExpandedAnimationController$$ExternalSyntheticLambda0 implements PhysicsAnimationLayout.PhysicsAnimationController.ChildAnimationConfigurator {
    public final /* synthetic */ ExpandedAnimationController f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ ExpandedAnimationController$$ExternalSyntheticLambda0(ExpandedAnimationController expandedAnimationController, boolean z) {
        this.f$0 = expandedAnimationController;
        this.f$1 = z;
    }

    public final void configureAnimationForChildAtIndex(int i, PhysicsAnimationLayout.PhysicsPropertyAnimator physicsPropertyAnimator) {
        boolean z;
        int i2;
        boolean z2;
        Runnable runnable;
        ExpandedAnimationController expandedAnimationController = this.f$0;
        boolean z3 = this.f$1;
        Objects.requireNonNull(expandedAnimationController);
        View childAt = expandedAnimationController.mLayout.getChildAt(i);
        Path path = new Path();
        path.moveTo(childAt.getTranslationX(), childAt.getTranslationY());
        PointF expandedBubbleXY = expandedAnimationController.mPositioner.getExpandedBubbleXY(i, expandedAnimationController.mBubbleStackView.getState());
        if (z3) {
            path.lineTo(childAt.getTranslationX(), expandedBubbleXY.y);
            path.lineTo(expandedBubbleXY.x, expandedBubbleXY.y);
        } else {
            float f = expandedAnimationController.mCollapsePoint.x;
            path.lineTo(f, expandedBubbleXY.y);
            path.lineTo(f, (((float) Math.min(i, 1)) * expandedAnimationController.mStackOffsetPx) + expandedAnimationController.mCollapsePoint.y);
        }
        if ((!z3 || expandedAnimationController.mLayout.isFirstChildXLeftOfCenter(childAt.getTranslationX())) && (z3 || !expandedAnimationController.mLayout.isFirstChildXLeftOfCenter(expandedAnimationController.mCollapsePoint.x))) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            i2 = i * 10;
        } else {
            i2 = (expandedAnimationController.mLayout.getChildCount() - i) * 10;
        }
        if ((!z || i != 0) && (z || i != expandedAnimationController.mLayout.getChildCount() - 1)) {
            z2 = false;
        } else {
            z2 = true;
        }
        LinearInterpolator linearInterpolator = Interpolators.LINEAR;
        Runnable[] runnableArr = new Runnable[2];
        if (z2) {
            runnable = expandedAnimationController.mLeadBubbleEndAction;
        } else {
            runnable = null;
        }
        runnableArr[0] = runnable;
        runnableArr[1] = new KeyguardUpdateMonitor$$ExternalSyntheticLambda7(expandedAnimationController, 5);
        ObjectAnimator objectAnimator = physicsPropertyAnimator.mPathAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(physicsPropertyAnimator, physicsPropertyAnimator.mCurrentPointOnPathXProperty, physicsPropertyAnimator.mCurrentPointOnPathYProperty, path);
        physicsPropertyAnimator.mPathAnimator = ofFloat;
        ofFloat.addListener(new AnimatorListenerAdapter(runnableArr) {
            public final /* synthetic */ Runnable[] val$pathAnimEndActions;

            {
                this.val$pathAnimEndActions = r1;
            }

            public final void onAnimationEnd(Animator animator) {
                for (Runnable runnable : this.val$pathAnimEndActions) {
                    if (runnable != null) {
                        runnable.run();
                    }
                }
            }
        });
        physicsPropertyAnimator.mPathAnimator.setDuration((long) 175);
        physicsPropertyAnimator.mPathAnimator.setInterpolator(linearInterpolator);
        HashMap hashMap = physicsPropertyAnimator.mAnimatedProperties;
        DynamicAnimation.C01371 r9 = DynamicAnimation.TRANSLATION_X;
        hashMap.remove(r9);
        HashMap hashMap2 = physicsPropertyAnimator.mAnimatedProperties;
        DynamicAnimation.C01422 r0 = DynamicAnimation.TRANSLATION_Y;
        hashMap2.remove(r0);
        physicsPropertyAnimator.mInitialPropertyValues.remove(r9);
        physicsPropertyAnimator.mInitialPropertyValues.remove(r0);
        PhysicsAnimationLayout.this.mEndActionForProperty.remove(r9);
        PhysicsAnimationLayout.this.mEndActionForProperty.remove(r0);
        physicsPropertyAnimator.mStartDelay = (long) i2;
        physicsPropertyAnimator.mStiffness = 1000.0f;
    }
}
