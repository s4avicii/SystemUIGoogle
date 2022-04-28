package com.android.p012wm.shell;

import android.app.ActivityManager;
import com.android.p012wm.shell.apppairs.AppPairsController;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda7 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ShellCommandHandlerImpl$$ExternalSyntheticLambda7 implements Consumer {
    public final /* synthetic */ int f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ ShellCommandHandlerImpl$$ExternalSyntheticLambda7(int i, int i2) {
        this.f$0 = i;
        this.f$1 = i2;
    }

    public final void accept(Object obj) {
        int i = this.f$0;
        int i2 = this.f$1;
        AppPairsController appPairsController = (AppPairsController) obj;
        Objects.requireNonNull(appPairsController);
        ActivityManager.RunningTaskInfo runningTaskInfo = appPairsController.mTaskOrganizer.getRunningTaskInfo(i);
        ActivityManager.RunningTaskInfo runningTaskInfo2 = appPairsController.mTaskOrganizer.getRunningTaskInfo(i2);
        if (runningTaskInfo != null && runningTaskInfo2 != null) {
            appPairsController.pairInner(runningTaskInfo, runningTaskInfo2);
        }
    }
}
