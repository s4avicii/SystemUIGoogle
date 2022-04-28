package com.android.p012wm.shell;

import android.content.Context;
import com.android.p012wm.shell.TaskViewFactoryController;
import com.android.systemui.controls.p004ui.ControlActionCoordinatorImpl$showDetail$1;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda1;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.TaskViewFactoryController$TaskViewFactoryImpl$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1783xccf6ffa7 implements Runnable {
    public final /* synthetic */ TaskViewFactoryController.TaskViewFactoryImpl f$0;
    public final /* synthetic */ Context f$1;
    public final /* synthetic */ Executor f$2;
    public final /* synthetic */ Consumer f$3;

    public /* synthetic */ C1783xccf6ffa7(TaskViewFactoryController.TaskViewFactoryImpl taskViewFactoryImpl, Context context, DelayableExecutor delayableExecutor, ControlActionCoordinatorImpl$showDetail$1.C07451.C07461 r4) {
        this.f$0 = taskViewFactoryImpl;
        this.f$1 = context;
        this.f$2 = delayableExecutor;
        this.f$3 = r4;
    }

    public final void run() {
        TaskViewFactoryController.TaskViewFactoryImpl taskViewFactoryImpl = this.f$0;
        Context context = this.f$1;
        Executor executor = this.f$2;
        Consumer consumer = this.f$3;
        Objects.requireNonNull(taskViewFactoryImpl);
        TaskViewFactoryController taskViewFactoryController = TaskViewFactoryController.this;
        Objects.requireNonNull(taskViewFactoryController);
        executor.execute(new BubblesManager$5$$ExternalSyntheticLambda1(consumer, new TaskView(context, taskViewFactoryController.mTaskOrganizer, taskViewFactoryController.mTaskViewTransitions, taskViewFactoryController.mSyncQueue), 3));
    }
}
