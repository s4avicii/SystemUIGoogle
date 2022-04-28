package com.google.android.systemui.statusbar;

import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManager;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class NotificationVoiceReplyManagerService_Factory implements Factory<NotificationVoiceReplyManagerService> {
    public final Provider<NotificationVoiceReplyLogger> loggerProvider;
    public final Provider<NotificationVoiceReplyManager.Initializer> managerInitializerProvider;

    public final Object get() {
        return new NotificationVoiceReplyManagerService(this.managerInitializerProvider.get(), this.loggerProvider.get());
    }

    public NotificationVoiceReplyManagerService_Factory(Provider<NotificationVoiceReplyManager.Initializer> provider, Provider<NotificationVoiceReplyLogger> provider2) {
        this.managerInitializerProvider = provider;
        this.loggerProvider = provider2;
    }
}
