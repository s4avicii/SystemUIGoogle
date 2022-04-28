package com.android.p012wm.shell.dagger;

import android.content.Context;
import android.window.ITaskOrganizerController;
import com.android.p012wm.shell.ShellTaskOrganizer;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.compatui.CompatUIController;
import com.android.p012wm.shell.recents.RecentTasksController;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

/* renamed from: com.android.wm.shell.dagger.WMShellBaseModule_ProvideShellTaskOrganizerFactory */
public final class WMShellBaseModule_ProvideShellTaskOrganizerFactory implements Factory<ShellTaskOrganizer> {
    public final Provider<CompatUIController> compatUIProvider;
    public final Provider<Context> contextProvider;
    public final Provider<ShellExecutor> mainExecutorProvider;
    public final Provider<Optional<RecentTasksController>> recentTasksOptionalProvider;

    public final Object get() {
        return new ShellTaskOrganizer((ITaskOrganizerController) null, this.mainExecutorProvider.get(), this.contextProvider.get(), this.compatUIProvider.get(), this.recentTasksOptionalProvider.get());
    }

    public WMShellBaseModule_ProvideShellTaskOrganizerFactory(Provider<ShellExecutor> provider, Provider<Context> provider2, Provider<CompatUIController> provider3, Provider<Optional<RecentTasksController>> provider4) {
        this.mainExecutorProvider = provider;
        this.contextProvider = provider2;
        this.compatUIProvider = provider3;
        this.recentTasksOptionalProvider = provider4;
    }
}
