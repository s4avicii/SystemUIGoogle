package com.android.systemui.statusbar.notification.dagger;

import android.os.Handler;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.collection.legacy.VisualStabilityManager;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class NotificationsModule_ProvideVisualStabilityManagerFactory implements Factory<VisualStabilityManager> {
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<Handler> handlerProvider;
    public final Provider<NotificationEntryManager> notificationEntryManagerProvider;
    public final Provider<StatusBarStateController> statusBarStateControllerProvider;
    public final Provider<VisualStabilityProvider> visualStabilityProvider;
    public final Provider<WakefulnessLifecycle> wakefulnessLifecycleProvider;

    public static NotificationsModule_ProvideVisualStabilityManagerFactory create(Provider<NotificationEntryManager> provider, Provider<VisualStabilityProvider> provider2, Provider<Handler> provider3, Provider<StatusBarStateController> provider4, Provider<WakefulnessLifecycle> provider5, Provider<DumpManager> provider6) {
        return new NotificationsModule_ProvideVisualStabilityManagerFactory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    public final Object get() {
        return new VisualStabilityManager(this.notificationEntryManagerProvider.get(), this.visualStabilityProvider.get(), this.handlerProvider.get(), this.statusBarStateControllerProvider.get(), this.wakefulnessLifecycleProvider.get(), this.dumpManagerProvider.get());
    }

    public NotificationsModule_ProvideVisualStabilityManagerFactory(Provider<NotificationEntryManager> provider, Provider<VisualStabilityProvider> provider2, Provider<Handler> provider3, Provider<StatusBarStateController> provider4, Provider<WakefulnessLifecycle> provider5, Provider<DumpManager> provider6) {
        this.notificationEntryManagerProvider = provider;
        this.visualStabilityProvider = provider2;
        this.handlerProvider = provider3;
        this.statusBarStateControllerProvider = provider4;
        this.wakefulnessLifecycleProvider = provider5;
        this.dumpManagerProvider = provider6;
    }
}
