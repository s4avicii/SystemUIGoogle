package com.google.android.systemui.smartspace;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Handler;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.statusbar.policy.NextAlarmController;
import com.android.systemui.statusbar.policy.ZenModeController;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class KeyguardZenAlarmViewController_Factory implements Factory<KeyguardZenAlarmViewController> {
    public final Provider<AlarmManager> alarmManagerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<Handler> handlerProvider;
    public final Provider<NextAlarmController> nextAlarmControllerProvider;
    public final Provider<BcSmartspaceDataPlugin> pluginProvider;
    public final Provider<ZenModeController> zenModeControllerProvider;

    public final Object get() {
        return new KeyguardZenAlarmViewController(this.contextProvider.get(), this.pluginProvider.get(), this.zenModeControllerProvider.get(), this.alarmManagerProvider.get(), this.nextAlarmControllerProvider.get(), this.handlerProvider.get());
    }

    public KeyguardZenAlarmViewController_Factory(Provider<Context> provider, Provider<BcSmartspaceDataPlugin> provider2, Provider<ZenModeController> provider3, Provider<AlarmManager> provider4, Provider<NextAlarmController> provider5, Provider<Handler> provider6) {
        this.contextProvider = provider;
        this.pluginProvider = provider2;
        this.zenModeControllerProvider = provider3;
        this.alarmManagerProvider = provider4;
        this.nextAlarmControllerProvider = provider5;
        this.handlerProvider = provider6;
    }
}
