package com.android.p012wm.shell.dagger;

import com.android.p012wm.shell.ShellTaskOrganizer;
import com.android.p012wm.shell.TaskViewFactoryController;
import com.android.p012wm.shell.TaskViewTransitions;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.wm.shell.dagger.WMShellBaseModule_ProvideTaskViewFactoryControllerFactory */
public final class WMShellBaseModule_ProvideTaskViewFactoryControllerFactory implements Factory<TaskViewFactoryController> {
    public final Provider<ShellExecutor> mainExecutorProvider;
    public final Provider<ShellTaskOrganizer> shellTaskOrganizerProvider;
    public final Provider<SyncTransactionQueue> syncQueueProvider;
    public final Provider<TaskViewTransitions> taskViewTransitionsProvider;

    public final Object get() {
        return new TaskViewFactoryController(this.shellTaskOrganizerProvider.get(), this.mainExecutorProvider.get(), this.syncQueueProvider.get(), this.taskViewTransitionsProvider.get());
    }

    public WMShellBaseModule_ProvideTaskViewFactoryControllerFactory(Provider<ShellTaskOrganizer> provider, Provider<ShellExecutor> provider2, Provider<SyncTransactionQueue> provider3, Provider<TaskViewTransitions> provider4) {
        this.shellTaskOrganizerProvider = provider;
        this.mainExecutorProvider = provider2;
        this.syncQueueProvider = provider3;
        this.taskViewTransitionsProvider = provider4;
    }
}
