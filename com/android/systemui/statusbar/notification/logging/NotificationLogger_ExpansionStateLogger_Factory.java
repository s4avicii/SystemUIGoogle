package com.android.systemui.statusbar.notification.logging;

import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class NotificationLogger_ExpansionStateLogger_Factory implements Factory<NotificationLogger.ExpansionStateLogger> {
    public final Provider<Executor> uiBgExecutorProvider;

    public final Object get() {
        return new NotificationLogger.ExpansionStateLogger(this.uiBgExecutorProvider.get());
    }

    public NotificationLogger_ExpansionStateLogger_Factory(Provider<Executor> provider) {
        this.uiBgExecutorProvider = provider;
    }
}
