package com.android.systemui.util.leak;

import android.content.Context;
import com.android.systemui.util.leak.GarbageMonitor;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class GarbageMonitor_Service_Factory implements Factory<GarbageMonitor.Service> {
    public final Provider<Context> contextProvider;
    public final Provider<GarbageMonitor> garbageMonitorProvider;

    public final Object get() {
        return new GarbageMonitor.Service(this.contextProvider.get(), this.garbageMonitorProvider.get());
    }

    public GarbageMonitor_Service_Factory(Provider<Context> provider, Provider<GarbageMonitor> provider2) {
        this.contextProvider = provider;
        this.garbageMonitorProvider = provider2;
    }
}
