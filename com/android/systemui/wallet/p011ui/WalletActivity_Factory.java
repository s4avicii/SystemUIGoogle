package com.android.systemui.wallet.p011ui;

import android.os.Handler;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* renamed from: com.android.systemui.wallet.ui.WalletActivity_Factory */
public final class WalletActivity_Factory implements Factory<WalletActivity> {
    public final Provider<ActivityStarter> activityStarterProvider;
    public final Provider<Executor> executorProvider;
    public final Provider<FalsingCollector> falsingCollectorProvider;
    public final Provider<FalsingManager> falsingManagerProvider;
    public final Provider<Handler> handlerProvider;
    public final Provider<KeyguardDismissUtil> keyguardDismissUtilProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;
    public final Provider<StatusBarKeyguardViewManager> keyguardViewManagerProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public static WalletActivity_Factory create(Provider<KeyguardStateController> provider, Provider<KeyguardDismissUtil> provider2, Provider<ActivityStarter> provider3, Provider<Executor> provider4, Provider<Handler> provider5, Provider<FalsingManager> provider6, Provider<FalsingCollector> provider7, Provider<UserTracker> provider8, Provider<KeyguardUpdateMonitor> provider9, Provider<StatusBarKeyguardViewManager> provider10, Provider<UiEventLogger> provider11) {
        return new WalletActivity_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11);
    }

    public final Object get() {
        return new WalletActivity(this.keyguardStateControllerProvider.get(), this.keyguardDismissUtilProvider.get(), this.activityStarterProvider.get(), this.executorProvider.get(), this.handlerProvider.get(), this.falsingManagerProvider.get(), this.falsingCollectorProvider.get(), this.userTrackerProvider.get(), this.keyguardUpdateMonitorProvider.get(), this.keyguardViewManagerProvider.get(), this.uiEventLoggerProvider.get());
    }

    public WalletActivity_Factory(Provider<KeyguardStateController> provider, Provider<KeyguardDismissUtil> provider2, Provider<ActivityStarter> provider3, Provider<Executor> provider4, Provider<Handler> provider5, Provider<FalsingManager> provider6, Provider<FalsingCollector> provider7, Provider<UserTracker> provider8, Provider<KeyguardUpdateMonitor> provider9, Provider<StatusBarKeyguardViewManager> provider10, Provider<UiEventLogger> provider11) {
        this.keyguardStateControllerProvider = provider;
        this.keyguardDismissUtilProvider = provider2;
        this.activityStarterProvider = provider3;
        this.executorProvider = provider4;
        this.handlerProvider = provider5;
        this.falsingManagerProvider = provider6;
        this.falsingCollectorProvider = provider7;
        this.userTrackerProvider = provider8;
        this.keyguardUpdateMonitorProvider = provider9;
        this.keyguardViewManagerProvider = provider10;
        this.uiEventLoggerProvider = provider11;
    }
}
