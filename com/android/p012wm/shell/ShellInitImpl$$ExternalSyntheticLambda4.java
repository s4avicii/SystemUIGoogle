package com.android.p012wm.shell;

import android.os.CancellationSignal;
import com.android.p012wm.shell.recents.RecentTasksController;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.ShellInitImpl$$ExternalSyntheticLambda4 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ShellInitImpl$$ExternalSyntheticLambda4 implements Consumer {
    public static final /* synthetic */ ShellInitImpl$$ExternalSyntheticLambda4 INSTANCE = new ShellInitImpl$$ExternalSyntheticLambda4(0);
    public static final /* synthetic */ ShellInitImpl$$ExternalSyntheticLambda4 INSTANCE$1 = new ShellInitImpl$$ExternalSyntheticLambda4(1);
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ ShellInitImpl$$ExternalSyntheticLambda4(int i) {
        this.$r8$classId = i;
    }

    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                RecentTasksController recentTasksController = (RecentTasksController) obj;
                Objects.requireNonNull(recentTasksController);
                recentTasksController.mTaskStackListener.addListener(recentTasksController);
                return;
            default:
                ((CancellationSignal) obj).cancel();
                return;
        }
    }
}
