package com.android.p012wm.shell.pip.phone;

import com.android.p012wm.shell.pip.phone.PipController;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.pip.phone.PipController$IPipImpl$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PipController$IPipImpl$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ PipController.IPipImpl f$0;

    public /* synthetic */ PipController$IPipImpl$$ExternalSyntheticLambda0(PipController.IPipImpl iPipImpl) {
        this.f$0 = iPipImpl;
    }

    public final void accept(Object obj) {
        PipController.IPipImpl iPipImpl = this.f$0;
        PipController pipController = (PipController) obj;
        Objects.requireNonNull(iPipImpl);
        PipController.IPipImpl.C19041 r0 = iPipImpl.mPipAnimationListener;
        Objects.requireNonNull(pipController);
        pipController.mPinnedStackAnimationRecentsCallback = r0;
        pipController.onPipCornerRadiusChanged();
    }
}
