package com.android.p012wm.shell;

import com.android.p012wm.shell.pip.phone.PhonePipMenuController;
import com.android.p012wm.shell.splitscreen.SplitScreenController;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ShellCommandHandlerImpl$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ ShellCommandHandlerImpl$$ExternalSyntheticLambda0(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((SplitScreenController) obj).removeFromSideStage(this.f$0);
                return;
            case 1:
                ((PhonePipMenuController.Listener) obj).onPipMenuStateChangeFinish(this.f$0);
                return;
            default:
                int i = this.f$0;
                int i2 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                ((SplitScreenController) obj).removeFromSideStage(i);
                return;
        }
    }
}
