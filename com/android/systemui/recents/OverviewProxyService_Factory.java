package com.android.systemui.recents;

import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import com.android.p012wm.shell.back.BackAnimation;
import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreen;
import com.android.p012wm.shell.onehanded.OneHanded;
import com.android.p012wm.shell.pip.Pip;
import com.android.p012wm.shell.recents.RecentTasks;
import com.android.p012wm.shell.splitscreen.SplitScreen;
import com.android.p012wm.shell.startingsurface.StartingSurface;
import com.android.p012wm.shell.transition.ShellTransitions;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.phone.StatusBar;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.InstanceFactory;
import java.util.Optional;
import javax.inject.Provider;

public final class OverviewProxyService_Factory implements Factory<OverviewProxyService> {
    public final Provider<Optional<BackAnimation>> backAnimationProvider;
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<CommandQueue> commandQueueProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<Optional<LegacySplitScreen>> legacySplitScreenOptionalProvider;
    public final Provider<NavigationBarController> navBarControllerLazyProvider;
    public final Provider<NavigationModeController> navModeControllerProvider;
    public final Provider<Optional<OneHanded>> oneHandedOptionalProvider;
    public final Provider<Optional<Pip>> pipOptionalProvider;
    public final Provider<Optional<RecentTasks>> recentTasksProvider;
    public final Provider<ScreenLifecycle> screenLifecycleProvider;
    public final Provider<ShellTransitions> shellTransitionsProvider;
    public final Provider<Optional<SplitScreen>> splitScreenOptionalProvider;
    public final Provider<Optional<StartingSurface>> startingSurfaceProvider;
    public final Provider<Optional<StatusBar>> statusBarOptionalLazyProvider;
    public final Provider<NotificationShadeWindowController> statusBarWinControllerProvider;
    public final Provider<SysUiState> sysUiStateProvider;
    public final Provider<KeyguardUnlockAnimationController> sysuiUnlockAnimationControllerProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;

    public OverviewProxyService_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, InstanceFactory instanceFactory, Provider provider16, Provider provider17, Provider provider18, Provider provider19) {
        this.contextProvider = provider;
        this.commandQueueProvider = provider2;
        this.navBarControllerLazyProvider = provider3;
        this.statusBarOptionalLazyProvider = provider4;
        this.navModeControllerProvider = provider5;
        this.statusBarWinControllerProvider = provider6;
        this.sysUiStateProvider = provider7;
        this.pipOptionalProvider = provider8;
        this.legacySplitScreenOptionalProvider = provider9;
        this.splitScreenOptionalProvider = provider10;
        this.oneHandedOptionalProvider = provider11;
        this.recentTasksProvider = provider12;
        this.backAnimationProvider = provider13;
        this.startingSurfaceProvider = provider14;
        this.broadcastDispatcherProvider = provider15;
        this.shellTransitionsProvider = instanceFactory;
        this.screenLifecycleProvider = provider16;
        this.uiEventLoggerProvider = provider17;
        this.sysuiUnlockAnimationControllerProvider = provider18;
        this.dumpManagerProvider = provider19;
    }

    public static OverviewProxyService_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, InstanceFactory instanceFactory, Provider provider16, Provider provider17, Provider provider18, Provider provider19) {
        return new OverviewProxyService_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, instanceFactory, provider16, provider17, provider18, provider19);
    }

    public final Object get() {
        return new OverviewProxyService(this.contextProvider.get(), this.commandQueueProvider.get(), DoubleCheck.lazy(this.navBarControllerLazyProvider), DoubleCheck.lazy(this.statusBarOptionalLazyProvider), this.navModeControllerProvider.get(), this.statusBarWinControllerProvider.get(), this.sysUiStateProvider.get(), this.pipOptionalProvider.get(), this.legacySplitScreenOptionalProvider.get(), this.splitScreenOptionalProvider.get(), this.oneHandedOptionalProvider.get(), this.recentTasksProvider.get(), this.backAnimationProvider.get(), this.startingSurfaceProvider.get(), this.broadcastDispatcherProvider.get(), this.shellTransitionsProvider.get(), this.screenLifecycleProvider.get(), this.uiEventLoggerProvider.get(), this.sysuiUnlockAnimationControllerProvider.get(), this.dumpManagerProvider.get());
    }
}
