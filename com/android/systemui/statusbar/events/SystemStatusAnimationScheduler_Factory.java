package com.android.systemui.statusbar.events;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class SystemStatusAnimationScheduler_Factory implements Factory<SystemStatusAnimationScheduler> {
    public final Provider<SystemEventChipAnimationController> chipAnimationControllerProvider;
    public final Provider<SystemEventCoordinator> coordinatorProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<DelayableExecutor> executorProvider;
    public final Provider<StatusBarWindowController> statusBarWindowControllerProvider;
    public final Provider<SystemClock> systemClockProvider;

    public static SystemStatusAnimationScheduler_Factory create(Provider provider, SystemEventChipAnimationController_Factory systemEventChipAnimationController_Factory, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new SystemStatusAnimationScheduler_Factory(provider, systemEventChipAnimationController_Factory, provider2, provider3, provider4, provider5);
    }

    public final Object get() {
        return new SystemStatusAnimationScheduler(this.coordinatorProvider.get(), this.chipAnimationControllerProvider.get(), this.statusBarWindowControllerProvider.get(), this.dumpManagerProvider.get(), this.systemClockProvider.get(), this.executorProvider.get());
    }

    public SystemStatusAnimationScheduler_Factory(Provider provider, SystemEventChipAnimationController_Factory systemEventChipAnimationController_Factory, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.coordinatorProvider = provider;
        this.chipAnimationControllerProvider = systemEventChipAnimationController_Factory;
        this.statusBarWindowControllerProvider = provider2;
        this.dumpManagerProvider = provider3;
        this.systemClockProvider = provider4;
        this.executorProvider = provider5;
    }
}
