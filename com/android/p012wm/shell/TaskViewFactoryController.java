package com.android.p012wm.shell;

import android.content.Context;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import com.android.systemui.controls.p004ui.ControlActionCoordinatorImpl$showDetail$1;
import com.android.systemui.util.concurrency.DelayableExecutor;

/* renamed from: com.android.wm.shell.TaskViewFactoryController */
public class TaskViewFactoryController {
    public final TaskViewFactory mImpl = new TaskViewFactoryImpl();
    public final ShellExecutor mShellExecutor;
    public final SyncTransactionQueue mSyncQueue;
    public final ShellTaskOrganizer mTaskOrganizer;
    public final TaskViewTransitions mTaskViewTransitions;

    /* renamed from: com.android.wm.shell.TaskViewFactoryController$TaskViewFactoryImpl */
    public class TaskViewFactoryImpl implements TaskViewFactory {
        public TaskViewFactoryImpl() {
        }

        public final void create(Context context, DelayableExecutor delayableExecutor, ControlActionCoordinatorImpl$showDetail$1.C07451.C07461 r5) {
            TaskViewFactoryController.this.mShellExecutor.execute(new C1783xccf6ffa7(this, context, delayableExecutor, r5));
        }
    }

    public TaskViewFactoryController(ShellTaskOrganizer shellTaskOrganizer, ShellExecutor shellExecutor, SyncTransactionQueue syncTransactionQueue, TaskViewTransitions taskViewTransitions) {
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mShellExecutor = shellExecutor;
        this.mSyncQueue = syncTransactionQueue;
        this.mTaskViewTransitions = taskViewTransitions;
    }
}
