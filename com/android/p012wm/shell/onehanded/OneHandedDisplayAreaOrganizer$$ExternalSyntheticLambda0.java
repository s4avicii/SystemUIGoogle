package com.android.p012wm.shell.onehanded;

import androidx.dynamicanimation.animation.DynamicAnimation;
import com.android.p012wm.shell.bubbles.animation.PhysicsAnimationLayout;

/* renamed from: com.android.wm.shell.onehanded.OneHandedDisplayAreaOrganizer$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class OneHandedDisplayAreaOrganizer$$ExternalSyntheticLambda0 implements PhysicsAnimationLayout.PhysicsAnimationController.ChildAnimationConfigurator {
    public static final /* synthetic */ OneHandedDisplayAreaOrganizer$$ExternalSyntheticLambda0 INSTANCE = new OneHandedDisplayAreaOrganizer$$ExternalSyntheticLambda0();
    public static final /* synthetic */ OneHandedDisplayAreaOrganizer$$ExternalSyntheticLambda0 INSTANCE$1 = new OneHandedDisplayAreaOrganizer$$ExternalSyntheticLambda0();

    public final void configureAnimationForChildAtIndex(int i, PhysicsAnimationLayout.PhysicsPropertyAnimator physicsPropertyAnimator) {
        physicsPropertyAnimator.property(DynamicAnimation.SCALE_X, 1.0f, new Runnable[0]);
        physicsPropertyAnimator.property(DynamicAnimation.SCALE_Y, 1.0f, new Runnable[0]);
        physicsPropertyAnimator.property(DynamicAnimation.ALPHA, 1.0f, new Runnable[0]);
    }
}
