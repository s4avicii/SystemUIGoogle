package com.android.p012wm.shell.animation;

import androidx.dynamicanimation.animation.SpringAnimation;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* renamed from: com.android.wm.shell.animation.PhysicsAnimator$startInternal$2 */
/* compiled from: PhysicsAnimator.kt */
final /* synthetic */ class PhysicsAnimator$startInternal$2 extends FunctionReferenceImpl implements Function0<Unit> {
    public PhysicsAnimator$startInternal$2(SpringAnimation springAnimation) {
        super(0, springAnimation, SpringAnimation.class, "start", "start()V", 0);
    }

    public final Object invoke() {
        ((SpringAnimation) this.receiver).start();
        return Unit.INSTANCE;
    }
}
