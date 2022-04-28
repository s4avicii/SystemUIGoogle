package com.android.p012wm.shell;

import android.app.ActivityManager;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.p012wm.shell.recents.RecentTasksController;
import com.android.p012wm.shell.util.GroupedRecentTaskInfo;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda9 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ShellCommandHandlerImpl$$ExternalSyntheticLambda9 implements Consumer {
    public final /* synthetic */ PrintWriter f$0;

    public /* synthetic */ ShellCommandHandlerImpl$$ExternalSyntheticLambda9(PrintWriter printWriter) {
        this.f$0 = printWriter;
    }

    public final void accept(Object obj) {
        PrintWriter printWriter = this.f$0;
        RecentTasksController recentTasksController = (RecentTasksController) obj;
        Objects.requireNonNull(recentTasksController);
        printWriter.println("RecentTasksController");
        ArrayList<GroupedRecentTaskInfo> recentTasks = recentTasksController.getRecentTasks(Integer.MAX_VALUE, 2, ActivityManager.getCurrentUser());
        for (int i = 0; i < recentTasks.size(); i++) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("  ");
            m.append(recentTasks.get(i));
            printWriter.println(m.toString());
        }
    }
}
