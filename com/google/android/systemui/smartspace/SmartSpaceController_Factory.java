package com.google.android.systemui.smartspace;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Handler;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.dump.DumpManager;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class SmartSpaceController_Factory implements Factory<SmartSpaceController> {
    public final Provider<AlarmManager> alarmManagerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<Handler> handlerProvider;
    public final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;

    public final Object get() {
        return new SmartSpaceController(this.contextProvider.get(), this.keyguardUpdateMonitorProvider.get(), this.handlerProvider.get(), this.alarmManagerProvider.get(), this.dumpManagerProvider.get());
    }

    public SmartSpaceController_Factory(Provider<Context> provider, Provider<KeyguardUpdateMonitor> provider2, Provider<Handler> provider3, Provider<AlarmManager> provider4, Provider<DumpManager> provider5) {
        this.contextProvider = provider;
        this.keyguardUpdateMonitorProvider = provider2;
        this.handlerProvider = provider3;
        this.alarmManagerProvider = provider4;
        this.dumpManagerProvider = provider5;
    }
}
