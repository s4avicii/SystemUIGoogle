package com.android.p012wm.shell.pip.phone;

import com.android.p012wm.shell.pip.phone.PipController;
import java.util.Objects;

/* renamed from: com.android.wm.shell.pip.phone.PipController$PipImpl$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PipController$PipImpl$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ PipController.PipImpl f$0;
    public final /* synthetic */ Runnable f$1 = null;
    public final /* synthetic */ Runnable f$2 = null;

    public /* synthetic */ PipController$PipImpl$$ExternalSyntheticLambda2(PipController.PipImpl pipImpl) {
        this.f$0 = pipImpl;
    }

    public final void run() {
        PipController.PipImpl pipImpl = this.f$0;
        Runnable runnable = this.f$1;
        Runnable runnable2 = this.f$2;
        Objects.requireNonNull(pipImpl);
        PipController pipController = PipController.this;
        Objects.requireNonNull(pipController);
        PhonePipMenuController phonePipMenuController = pipController.mMenuController;
        Objects.requireNonNull(phonePipMenuController);
        if (phonePipMenuController.isMenuVisible()) {
            if (runnable != null) {
                runnable.run();
            }
            PipMenuView pipMenuView = phonePipMenuController.mPipMenuView;
            Objects.requireNonNull(pipMenuView);
            pipMenuView.hideMenu(runnable2, true, pipMenuView.mDidLastShowMenuResize, 1);
        }
    }
}
