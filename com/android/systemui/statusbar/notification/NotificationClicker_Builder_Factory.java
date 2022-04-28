package com.android.systemui.statusbar.notification;

import com.android.systemui.p006qs.logging.QSLogger_Factory;
import com.android.systemui.statusbar.notification.NotificationClicker;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class NotificationClicker_Builder_Factory implements Factory<NotificationClicker.Builder> {
    public final Provider<NotificationClickerLogger> loggerProvider;

    public final Object get() {
        return new NotificationClicker.Builder(this.loggerProvider.get());
    }

    public NotificationClicker_Builder_Factory(QSLogger_Factory qSLogger_Factory) {
        this.loggerProvider = qSLogger_Factory;
    }
}
