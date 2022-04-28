package com.google.android.systemui.communal.dock;

import android.content.Context;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.util.condition.Monitor;
import com.google.android.systemui.communal.dock.DockEventSimulator;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class DockEventSimulator_Factory implements Factory<DockEventSimulator> {
    public final Provider<DockEventSimulator.DockingCondition> conditionProvider;
    public final Provider<Context> contextProvider;
    public final Provider<FeatureFlags> flagsProvider;
    public final Provider<Monitor> monitorLazyProvider;

    public final Object get() {
        return new DockEventSimulator(this.contextProvider.get(), this.flagsProvider.get(), DoubleCheck.lazy(this.monitorLazyProvider), this.conditionProvider.get());
    }

    public DockEventSimulator_Factory(Provider<Context> provider, Provider<FeatureFlags> provider2, Provider<Monitor> provider3, Provider<DockEventSimulator.DockingCondition> provider4) {
        this.contextProvider = provider;
        this.flagsProvider = provider2;
        this.monitorLazyProvider = provider3;
        this.conditionProvider = provider4;
    }
}
