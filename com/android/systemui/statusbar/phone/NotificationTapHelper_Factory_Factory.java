package com.android.systemui.statusbar.phone;

import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.phone.NotificationTapHelper;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class NotificationTapHelper_Factory_Factory implements Factory<NotificationTapHelper.Factory> {
    public final Provider<DelayableExecutor> delayableExecutorProvider;
    public final Provider<FalsingManager> falsingManagerProvider;

    public final Object get() {
        return new NotificationTapHelper.Factory(this.falsingManagerProvider.get(), this.delayableExecutorProvider.get());
    }

    public NotificationTapHelper_Factory_Factory(Provider<FalsingManager> provider, Provider<DelayableExecutor> provider2) {
        this.falsingManagerProvider = provider;
        this.delayableExecutorProvider = provider2;
    }
}
