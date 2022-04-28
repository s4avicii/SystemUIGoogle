package com.android.systemui.privacy;

import com.android.systemui.appops.AppOpsController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.privacy.logging.PrivacyLogger_Factory;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class PrivacyItemController_Factory implements Factory<PrivacyItemController> {
    public final Provider<AppOpsController> appOpsControllerProvider;
    public final Provider<DelayableExecutor> bgExecutorProvider;
    public final Provider<DeviceConfigProxy> deviceConfigProxyProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<PrivacyLogger> loggerProvider;
    public final Provider<SystemClock> systemClockProvider;
    public final Provider<DelayableExecutor> uiExecutorProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public static PrivacyItemController_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, PrivacyLogger_Factory privacyLogger_Factory, Provider provider6, Provider provider7) {
        return new PrivacyItemController_Factory(provider, provider2, provider3, provider4, provider5, privacyLogger_Factory, provider6, provider7);
    }

    public final Object get() {
        return new PrivacyItemController(this.appOpsControllerProvider.get(), this.uiExecutorProvider.get(), this.bgExecutorProvider.get(), this.deviceConfigProxyProvider.get(), this.userTrackerProvider.get(), this.loggerProvider.get(), this.systemClockProvider.get(), this.dumpManagerProvider.get());
    }

    public PrivacyItemController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, PrivacyLogger_Factory privacyLogger_Factory, Provider provider6, Provider provider7) {
        this.appOpsControllerProvider = provider;
        this.uiExecutorProvider = provider2;
        this.bgExecutorProvider = provider3;
        this.deviceConfigProxyProvider = provider4;
        this.userTrackerProvider = provider5;
        this.loggerProvider = privacyLogger_Factory;
        this.systemClockProvider = provider6;
        this.dumpManagerProvider = provider7;
    }
}
