package com.android.p012wm.shell.dagger;

import android.content.Context;
import com.android.launcher3.icons.IconProvider;
import com.android.p012wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.p012wm.shell.ShellTaskOrganizer;
import com.android.p012wm.shell.common.DisplayController;
import com.android.p012wm.shell.common.DisplayImeController;
import com.android.p012wm.shell.common.DisplayInsetsController;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import com.android.p012wm.shell.common.TransactionPool;
import com.android.p012wm.shell.recents.RecentTasksController;
import com.android.p012wm.shell.splitscreen.SplitScreenController;
import com.android.p012wm.shell.splitscreen.StageTaskUnfoldController;
import com.android.p012wm.shell.transition.Transitions;
import com.android.systemui.controls.management.ControlsProviderSelectorActivity_Factory;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

/* renamed from: com.android.wm.shell.dagger.WMShellModule_ProvideSplitScreenControllerFactory */
public final class WMShellModule_ProvideSplitScreenControllerFactory implements Factory<SplitScreenController> {
    public final Provider<Context> contextProvider;
    public final Provider<DisplayController> displayControllerProvider;
    public final Provider<DisplayImeController> displayImeControllerProvider;
    public final Provider<DisplayInsetsController> displayInsetsControllerProvider;
    public final Provider<IconProvider> iconProvider;
    public final Provider<ShellExecutor> mainExecutorProvider;
    public final Provider<Optional<RecentTasksController>> recentTasksProvider;
    public final Provider<RootTaskDisplayAreaOrganizer> rootTaskDisplayAreaOrganizerProvider;
    public final Provider<ShellTaskOrganizer> shellTaskOrganizerProvider;
    public final Provider<Optional<StageTaskUnfoldController>> stageTaskUnfoldControllerProvider;
    public final Provider<SyncTransactionQueue> syncQueueProvider;
    public final Provider<TransactionPool> transactionPoolProvider;
    public final Provider<Transitions> transitionsProvider;

    public static WMShellModule_ProvideSplitScreenControllerFactory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, ControlsProviderSelectorActivity_Factory controlsProviderSelectorActivity_Factory) {
        return new WMShellModule_ProvideSplitScreenControllerFactory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, controlsProviderSelectorActivity_Factory);
    }

    public final Object get() {
        return new SplitScreenController(this.shellTaskOrganizerProvider.get(), this.syncQueueProvider.get(), this.contextProvider.get(), this.rootTaskDisplayAreaOrganizerProvider.get(), this.mainExecutorProvider.get(), this.displayControllerProvider.get(), this.displayImeControllerProvider.get(), this.displayInsetsControllerProvider.get(), this.transitionsProvider.get(), this.transactionPoolProvider.get(), this.iconProvider.get(), this.recentTasksProvider.get(), this.stageTaskUnfoldControllerProvider);
    }

    public WMShellModule_ProvideSplitScreenControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, ControlsProviderSelectorActivity_Factory controlsProviderSelectorActivity_Factory) {
        this.shellTaskOrganizerProvider = provider;
        this.syncQueueProvider = provider2;
        this.contextProvider = provider3;
        this.rootTaskDisplayAreaOrganizerProvider = provider4;
        this.mainExecutorProvider = provider5;
        this.displayControllerProvider = provider6;
        this.displayImeControllerProvider = provider7;
        this.displayInsetsControllerProvider = provider8;
        this.transitionsProvider = provider9;
        this.transactionPoolProvider = provider10;
        this.iconProvider = provider11;
        this.recentTasksProvider = provider12;
        this.stageTaskUnfoldControllerProvider = controlsProviderSelectorActivity_Factory;
    }
}
