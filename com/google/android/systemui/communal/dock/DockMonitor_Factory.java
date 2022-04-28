package com.google.android.systemui.communal.dock;

import android.content.Context;
import com.android.systemui.util.condition.Monitor;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class DockMonitor_Factory implements Factory<DockMonitor> {
    public final Provider<Monitor> conditionMonitorProvider;
    public final Provider<Context> contextProvider;

    public final Object get() {
        return new DockMonitor(this.contextProvider.get(), DoubleCheck.lazy(this.conditionMonitorProvider));
    }

    public DockMonitor_Factory(Provider<Context> provider, Provider<Monitor> provider2) {
        this.contextProvider = provider;
        this.conditionMonitorProvider = provider2;
    }
}
