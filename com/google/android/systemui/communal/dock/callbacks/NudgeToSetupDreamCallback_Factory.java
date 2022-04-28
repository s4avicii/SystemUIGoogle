package com.google.android.systemui.communal.dock.callbacks;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.net.Uri;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.google.android.systemui.communal.dreams.SetupDreamComplication;
import com.google.android.systemui.communal.dreams.dagger.SetupDreamModule_ProvidesSetupDreamNotificationIdFactory;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class NudgeToSetupDreamCallback_Factory implements Factory<NudgeToSetupDreamCallback> {
    public final Provider<SetupDreamComplication> complicationProvider;
    public final Provider<ContentResolver> contentResolverProvider;
    public final Provider<DreamOverlayStateController> dreamOverlayStateControllerProvider;
    public final Provider<Boolean> dreamSelectedProvider;
    public final Provider<Integer> notificationIdProvider;
    public final Provider<Notification> notificationLazyProvider;
    public final Provider<NotificationManager> notificationManagerProvider;
    public final Provider<Uri> settingUriProvider;

    public NudgeToSetupDreamCallback_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7) {
        SetupDreamModule_ProvidesSetupDreamNotificationIdFactory setupDreamModule_ProvidesSetupDreamNotificationIdFactory = SetupDreamModule_ProvidesSetupDreamNotificationIdFactory.InstanceHolder.INSTANCE;
        this.complicationProvider = provider;
        this.dreamOverlayStateControllerProvider = provider2;
        this.dreamSelectedProvider = provider3;
        this.notificationManagerProvider = provider4;
        this.notificationLazyProvider = provider5;
        this.contentResolverProvider = provider6;
        this.settingUriProvider = provider7;
        this.notificationIdProvider = setupDreamModule_ProvidesSetupDreamNotificationIdFactory;
    }

    public final Object get() {
        return new NudgeToSetupDreamCallback(this.complicationProvider.get(), this.dreamOverlayStateControllerProvider.get(), this.dreamSelectedProvider, this.notificationManagerProvider.get(), DoubleCheck.lazy(this.notificationLazyProvider), this.contentResolverProvider.get(), this.settingUriProvider.get(), this.notificationIdProvider.get().intValue());
    }
}
