package com.android.p012wm.shell.dagger;

import android.content.Context;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.TaskStackListenerImpl;
import com.android.p012wm.shell.recents.RecentTasksController;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Provider;

/* renamed from: com.android.wm.shell.dagger.WMShellBaseModule_ProvideRecentTasksControllerFactory */
public final class WMShellBaseModule_ProvideRecentTasksControllerFactory implements Factory<Optional<RecentTasksController>> {
    public final Provider<Context> contextProvider;
    public final Provider<ShellExecutor> mainExecutorProvider;
    public final Provider<TaskStackListenerImpl> taskStackListenerProvider;

    public final Object get() {
        RecentTasksController recentTasksController;
        Context context = this.contextProvider.get();
        TaskStackListenerImpl taskStackListenerImpl = this.taskStackListenerProvider.get();
        ShellExecutor shellExecutor = this.mainExecutorProvider.get();
        if (!context.getResources().getBoolean(17891670)) {
            recentTasksController = null;
        } else {
            recentTasksController = new RecentTasksController(context, taskStackListenerImpl, shellExecutor);
        }
        Optional ofNullable = Optional.ofNullable(recentTasksController);
        Objects.requireNonNull(ofNullable, "Cannot return null from a non-@Nullable @Provides method");
        return ofNullable;
    }

    public WMShellBaseModule_ProvideRecentTasksControllerFactory(Provider<Context> provider, Provider<TaskStackListenerImpl> provider2, Provider<ShellExecutor> provider3) {
        this.contextProvider = provider;
        this.taskStackListenerProvider = provider2;
        this.mainExecutorProvider = provider3;
    }
}
