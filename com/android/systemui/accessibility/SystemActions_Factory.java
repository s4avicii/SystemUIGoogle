package com.android.systemui.accessibility;

import android.content.Context;
import com.android.systemui.recents.Recents;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.phone.StatusBar;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

public final class SystemActions_Factory implements Factory<SystemActions> {
    public final Provider<Context> contextProvider;
    public final Provider<NotificationShadeWindowController> notificationShadeControllerProvider;
    public final Provider<Optional<Recents>> recentsOptionalProvider;
    public final Provider<Optional<StatusBar>> statusBarOptionalLazyProvider;

    public final Object get() {
        return new SystemActions(this.contextProvider.get(), this.notificationShadeControllerProvider.get(), DoubleCheck.lazy(this.statusBarOptionalLazyProvider), this.recentsOptionalProvider.get());
    }

    public SystemActions_Factory(Provider<Context> provider, Provider<NotificationShadeWindowController> provider2, Provider<Optional<StatusBar>> provider3, Provider<Optional<Recents>> provider4) {
        this.contextProvider = provider;
        this.notificationShadeControllerProvider = provider2;
        this.statusBarOptionalLazyProvider = provider3;
        this.recentsOptionalProvider = provider4;
    }
}
