package com.android.p012wm.shell.animation;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* renamed from: com.android.wm.shell.animation.PhysicsAnimator$withEndActions$1$1 */
/* compiled from: PhysicsAnimator.kt */
public /* synthetic */ class PhysicsAnimator$withEndActions$1$1 extends FunctionReferenceImpl implements Function0<Unit> {
    public PhysicsAnimator$withEndActions$1$1(Runnable runnable) {
        super(0, runnable, Runnable.class, "run", "run()V", 0);
    }

    public final Object invoke() {
        ((Runnable) this.receiver).run();
        return Unit.INSTANCE;
    }
}
