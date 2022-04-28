package com.android.p012wm.shell.recents;

import com.android.p012wm.shell.recents.RecentTasksController;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.recents.RecentTasksController$IRecentTasksImpl$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class RecentTasksController$IRecentTasksImpl$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ RecentTasksController.IRecentTasksImpl f$0;
    public final /* synthetic */ IRecentTasksListener f$1;

    public /* synthetic */ RecentTasksController$IRecentTasksImpl$$ExternalSyntheticLambda0(RecentTasksController.IRecentTasksImpl iRecentTasksImpl, IRecentTasksListener iRecentTasksListener) {
        this.f$0 = iRecentTasksImpl;
        this.f$1 = iRecentTasksListener;
    }

    public final void accept(Object obj) {
        RecentTasksController.IRecentTasksImpl iRecentTasksImpl = this.f$0;
        IRecentTasksListener iRecentTasksListener = this.f$1;
        RecentTasksController recentTasksController = (RecentTasksController) obj;
        Objects.requireNonNull(iRecentTasksImpl);
        iRecentTasksImpl.mListener.register(iRecentTasksListener);
    }
}
