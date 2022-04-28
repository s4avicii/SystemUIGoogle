package com.android.systemui.communal.dagger;

import com.android.systemui.util.condition.Condition;
import com.android.systemui.util.condition.Monitor;
import com.android.systemui.util.condition.dagger.MonitorComponent;
import dagger.internal.Factory;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.inject.Provider;

public final class CommunalModule_ProvideCommunalSourceMonitorFactory implements Factory<Monitor> {
    public final Provider<Set<Condition>> communalConditionsProvider;
    public final Provider<MonitorComponent.Factory> factoryProvider;

    public final Object get() {
        Monitor monitor = this.factoryProvider.get().create(this.communalConditionsProvider.get(), new HashSet()).getMonitor();
        Objects.requireNonNull(monitor, "Cannot return null from a non-@Nullable @Provides method");
        return monitor;
    }

    public CommunalModule_ProvideCommunalSourceMonitorFactory(Provider<Set<Condition>> provider, Provider<MonitorComponent.Factory> provider2) {
        this.communalConditionsProvider = provider;
        this.factoryProvider = provider2;
    }
}
