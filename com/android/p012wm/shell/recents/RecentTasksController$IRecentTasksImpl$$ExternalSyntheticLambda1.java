package com.android.p012wm.shell.recents;

import com.android.p012wm.shell.util.GroupedRecentTaskInfo;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.recents.RecentTasksController$IRecentTasksImpl$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class RecentTasksController$IRecentTasksImpl$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ GroupedRecentTaskInfo[][] f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ RecentTasksController$IRecentTasksImpl$$ExternalSyntheticLambda1(GroupedRecentTaskInfo[][] groupedRecentTaskInfoArr, int i, int i2, int i3) {
        this.f$0 = groupedRecentTaskInfoArr;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = i3;
    }

    public final void accept(Object obj) {
        this.f$0[0] = (GroupedRecentTaskInfo[]) ((RecentTasksController) obj).getRecentTasks(this.f$1, this.f$2, this.f$3).toArray(new GroupedRecentTaskInfo[0]);
    }
}
