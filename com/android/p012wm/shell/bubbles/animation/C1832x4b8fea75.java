package com.android.p012wm.shell.bubbles.animation;

import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout$PhysicsPropertyAnimator$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1832x4b8fea75 implements Runnable {
    public final /* synthetic */ SpringForce f$0;
    public final /* synthetic */ float f$1;
    public final /* synthetic */ float f$2;
    public final /* synthetic */ float f$3;
    public final /* synthetic */ SpringAnimation f$4;
    public final /* synthetic */ float f$5;

    public /* synthetic */ C1832x4b8fea75(SpringForce springForce, float f, float f2, float f3, SpringAnimation springAnimation, float f4) {
        this.f$0 = springForce;
        this.f$1 = f;
        this.f$2 = f2;
        this.f$3 = f3;
        this.f$4 = springAnimation;
        this.f$5 = f4;
    }

    public final void run() {
        SpringForce springForce = this.f$0;
        float f = this.f$1;
        float f2 = this.f$2;
        float f3 = this.f$3;
        SpringAnimation springAnimation = this.f$4;
        float f4 = this.f$5;
        springForce.setStiffness(f);
        springForce.setDampingRatio(f2);
        if (f3 > -3.4028235E38f) {
            Objects.requireNonNull(springAnimation);
            springAnimation.mVelocity = f3;
        }
        springForce.mFinalPosition = (double) f4;
        springAnimation.start();
    }
}
