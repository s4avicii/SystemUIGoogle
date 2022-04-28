package com.android.p012wm.shell;

import android.window.WindowContainerTransaction;
import com.android.p012wm.shell.splitscreen.SplitScreenController;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda6 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ShellCommandHandlerImpl$$ExternalSyntheticLambda6 implements Consumer {
    public final /* synthetic */ int f$0;

    public /* synthetic */ ShellCommandHandlerImpl$$ExternalSyntheticLambda6(int i) {
        this.f$0 = i;
    }

    public final void accept(Object obj) {
        int i = this.f$0;
        SplitScreenController splitScreenController = (SplitScreenController) obj;
        Objects.requireNonNull(splitScreenController);
        splitScreenController.mStageCoordinator.setSideStagePosition(i, (WindowContainerTransaction) null);
    }
}
