package com.android.p012wm.shell.back;

import com.android.p012wm.shell.back.BackAnimationController;
import java.util.Objects;

/* renamed from: com.android.wm.shell.back.BackAnimationController$BackAnimationImpl$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1788x542d5222 implements Runnable {
    public final /* synthetic */ BackAnimationController.BackAnimationImpl f$0;
    public final /* synthetic */ float f$2;

    public /* synthetic */ C1788x542d5222(BackAnimationController.BackAnimationImpl backAnimationImpl, float f, float f2) {
        this.f$0 = backAnimationImpl;
        this.f$2 = f2;
    }

    public final void run() {
        BackAnimationController.BackAnimationImpl backAnimationImpl = this.f$0;
        float f = this.f$2;
        Objects.requireNonNull(backAnimationImpl);
        BackAnimationController backAnimationController = BackAnimationController.this;
        Objects.requireNonNull(backAnimationController);
        backAnimationController.mProgressThreshold = f;
    }
}
