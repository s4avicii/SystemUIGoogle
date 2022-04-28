package com.google.android.systemui.communal.dock;

import android.content.Context;
import com.google.android.systemui.communal.dock.DockEventSimulator;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class DockEventSimulator_DockingCondition_Factory implements Factory<DockEventSimulator.DockingCondition> {
    public final Provider<Context> contextProvider;

    public final Object get() {
        return new DockEventSimulator.DockingCondition(this.contextProvider.get());
    }

    public DockEventSimulator_DockingCondition_Factory(Provider<Context> provider) {
        this.contextProvider = provider;
    }
}
