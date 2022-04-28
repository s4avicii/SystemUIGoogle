package com.android.systemui.assist;

import android.content.Context;
import com.android.internal.app.AssistUtils;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.statusbar.phone.TapAgainView;
import com.android.systemui.statusbar.phone.TapAgainViewController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class AssistLogger_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider assistUtilsProvider;
    public final Provider contextProvider;
    public final Provider phoneStateMonitorProvider;
    public final Provider uiEventLoggerProvider;

    public /* synthetic */ AssistLogger_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.uiEventLoggerProvider = provider2;
        this.assistUtilsProvider = provider3;
        this.phoneStateMonitorProvider = provider4;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new AssistLogger((Context) this.contextProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), (AssistUtils) this.assistUtilsProvider.get(), (PhoneStateMonitor) this.phoneStateMonitorProvider.get());
            default:
                return new TapAgainViewController((TapAgainView) this.contextProvider.get(), (DelayableExecutor) this.uiEventLoggerProvider.get(), (ConfigurationController) this.assistUtilsProvider.get(), ((Long) this.phoneStateMonitorProvider.get()).longValue());
        }
    }
}
