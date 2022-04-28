package com.android.p012wm.shell.pip;

import android.window.WindowContainerTransaction;
import com.android.p012wm.shell.splitscreen.SplitScreenController;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.pip.PipTaskOrganizer$$ExternalSyntheticLambda8 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PipTaskOrganizer$$ExternalSyntheticLambda8 implements Consumer {
    public final /* synthetic */ PipTaskOrganizer f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ WindowContainerTransaction f$2;

    public /* synthetic */ PipTaskOrganizer$$ExternalSyntheticLambda8(PipTaskOrganizer pipTaskOrganizer, boolean z, WindowContainerTransaction windowContainerTransaction) {
        this.f$0 = pipTaskOrganizer;
        this.f$1 = z;
        this.f$2 = windowContainerTransaction;
    }

    public final void accept(Object obj) {
        int i;
        PipTaskOrganizer pipTaskOrganizer = this.f$0;
        boolean z = this.f$1;
        WindowContainerTransaction windowContainerTransaction = this.f$2;
        SplitScreenController splitScreenController = (SplitScreenController) obj;
        Objects.requireNonNull(pipTaskOrganizer);
        int i2 = pipTaskOrganizer.mTaskInfo.taskId;
        Objects.requireNonNull(splitScreenController);
        if (splitScreenController.isSplitScreenVisible()) {
            i = -1;
        } else {
            i = 1;
        }
        splitScreenController.moveToStage(i2, i, z ^ true ? 1 : 0, windowContainerTransaction);
    }
}
