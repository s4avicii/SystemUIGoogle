package com.android.p012wm.shell;

import com.android.p012wm.shell.apppairs.AppPairsController;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda5 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ShellCommandHandlerImpl$$ExternalSyntheticLambda5 implements Consumer {
    public final /* synthetic */ int f$0;

    public /* synthetic */ ShellCommandHandlerImpl$$ExternalSyntheticLambda5(int i) {
        this.f$0 = i;
    }

    public final void accept(Object obj) {
        int i = this.f$0;
        AppPairsController appPairsController = (AppPairsController) obj;
        Objects.requireNonNull(appPairsController);
        appPairsController.unpair(i, true);
    }
}
