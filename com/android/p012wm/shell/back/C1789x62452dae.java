package com.android.p012wm.shell.back;

import android.window.IOnBackInvokedCallback;
import com.android.p012wm.shell.back.BackAnimationController;
import java.lang.Thread;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.back.BackAnimationController$IBackAnimationImpl$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1789x62452dae implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ C1789x62452dae(Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                BackAnimationController.IBackAnimationImpl iBackAnimationImpl = (BackAnimationController.IBackAnimationImpl) this.f$0;
                BackAnimationController backAnimationController = (BackAnimationController) obj;
                Objects.requireNonNull(iBackAnimationImpl);
                iBackAnimationImpl.mController.setBackToLauncherCallback((IOnBackInvokedCallback) this.f$1);
                return;
            default:
                ((Thread.UncaughtExceptionHandler) obj).uncaughtException((Thread) this.f$0, (Throwable) this.f$1);
                return;
        }
    }
}
