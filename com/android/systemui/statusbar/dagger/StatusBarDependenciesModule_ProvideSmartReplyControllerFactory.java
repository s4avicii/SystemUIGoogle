package com.android.systemui.statusbar.dagger;

import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.NotificationClickNotifier;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class StatusBarDependenciesModule_ProvideSmartReplyControllerFactory implements Factory<SmartReplyController> {
    public final Provider<NotificationClickNotifier> clickNotifierProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<IStatusBarService> statusBarServiceProvider;
    public final Provider<NotificationVisibilityProvider> visibilityProvider;

    public final Object get() {
        return new SmartReplyController(this.dumpManagerProvider.get(), this.visibilityProvider.get(), this.statusBarServiceProvider.get(), this.clickNotifierProvider.get());
    }

    public StatusBarDependenciesModule_ProvideSmartReplyControllerFactory(Provider<DumpManager> provider, Provider<NotificationVisibilityProvider> provider2, Provider<IStatusBarService> provider3, Provider<NotificationClickNotifier> provider4) {
        this.dumpManagerProvider = provider;
        this.visibilityProvider = provider2;
        this.statusBarServiceProvider = provider3;
        this.clickNotifierProvider = provider4;
    }
}
