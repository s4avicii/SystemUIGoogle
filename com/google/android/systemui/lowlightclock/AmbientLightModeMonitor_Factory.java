package com.google.android.systemui.lowlightclock;

import com.android.systemui.util.sensors.AsyncSensorManager;
import com.google.android.systemui.lowlightclock.AmbientLightModeMonitor;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class AmbientLightModeMonitor_Factory implements Factory<AmbientLightModeMonitor> {
    public final Provider<AmbientLightModeMonitor.DebounceAlgorithm> algorithmProvider;
    public final Provider<AsyncSensorManager> sensorManagerProvider;

    public final Object get() {
        return new AmbientLightModeMonitor(this.algorithmProvider.get(), this.sensorManagerProvider.get());
    }

    public AmbientLightModeMonitor_Factory(Provider<AmbientLightModeMonitor.DebounceAlgorithm> provider, Provider<AsyncSensorManager> provider2) {
        this.algorithmProvider = provider;
        this.sensorManagerProvider = provider2;
    }
}
