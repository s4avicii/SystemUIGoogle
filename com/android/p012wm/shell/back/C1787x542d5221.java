package com.android.p012wm.shell.back;

import com.android.p012wm.shell.back.BackAnimationController;
import java.util.Objects;

/* renamed from: com.android.wm.shell.back.BackAnimationController$BackAnimationImpl$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1787x542d5221 implements Runnable {
    public final /* synthetic */ BackAnimationController.BackAnimationImpl f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ C1787x542d5221(BackAnimationController.BackAnimationImpl backAnimationImpl, boolean z) {
        this.f$0 = backAnimationImpl;
        this.f$1 = z;
    }

    public final void run() {
        BackAnimationController.BackAnimationImpl backAnimationImpl = this.f$0;
        boolean z = this.f$1;
        Objects.requireNonNull(backAnimationImpl);
        BackAnimationController backAnimationController = BackAnimationController.this;
        Objects.requireNonNull(backAnimationController);
        backAnimationController.mTriggerBack = z;
    }
}
