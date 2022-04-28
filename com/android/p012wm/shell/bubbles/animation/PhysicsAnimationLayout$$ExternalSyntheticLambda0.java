package com.android.p012wm.shell.bubbles.animation;

import android.view.View;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PhysicsAnimationLayout$$ExternalSyntheticLambda0 implements DynamicAnimation.OnAnimationUpdateListener {
    public final /* synthetic */ PhysicsAnimationLayout f$0;
    public final /* synthetic */ View f$1;
    public final /* synthetic */ DynamicAnimation.ViewProperty f$2;

    public /* synthetic */ PhysicsAnimationLayout$$ExternalSyntheticLambda0(PhysicsAnimationLayout physicsAnimationLayout, View view, DynamicAnimation.ViewProperty viewProperty) {
        this.f$0 = physicsAnimationLayout;
        this.f$1 = view;
        this.f$2 = viewProperty;
    }

    public final void onAnimationUpdate(float f, float f2) {
        SpringAnimation springAnimationFromView;
        PhysicsAnimationLayout physicsAnimationLayout = this.f$0;
        View view = this.f$1;
        DynamicAnimation.ViewProperty viewProperty = this.f$2;
        Objects.requireNonNull(physicsAnimationLayout);
        int indexOfChild = physicsAnimationLayout.indexOfChild(view);
        int nextAnimationInChain = physicsAnimationLayout.mController.getNextAnimationInChain(viewProperty, indexOfChild);
        if (nextAnimationInChain != -1 && indexOfChild >= 0) {
            float offsetForChainedPropertyAnimation = physicsAnimationLayout.mController.getOffsetForChainedPropertyAnimation(viewProperty, nextAnimationInChain);
            if (nextAnimationInChain < physicsAnimationLayout.getChildCount() && (springAnimationFromView = PhysicsAnimationLayout.getSpringAnimationFromView(viewProperty, physicsAnimationLayout.getChildAt(nextAnimationInChain))) != null) {
                springAnimationFromView.animateToFinalPosition(f + offsetForChainedPropertyAnimation);
            }
        }
    }
}
