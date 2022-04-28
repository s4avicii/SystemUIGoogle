package com.android.p012wm.shell;

import android.window.WindowContainerTransaction;
import com.android.p012wm.shell.splitscreen.SplitScreenController;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda8 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ShellCommandHandlerImpl$$ExternalSyntheticLambda8 implements Consumer {
    public final /* synthetic */ int f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ ShellCommandHandlerImpl$$ExternalSyntheticLambda8(int i, int i2) {
        this.f$0 = i;
        this.f$1 = i2;
    }

    public final void accept(Object obj) {
        int i = this.f$0;
        int i2 = this.f$1;
        SplitScreenController splitScreenController = (SplitScreenController) obj;
        Objects.requireNonNull(splitScreenController);
        splitScreenController.moveToStage(i, 1, i2, new WindowContainerTransaction());
    }
}
