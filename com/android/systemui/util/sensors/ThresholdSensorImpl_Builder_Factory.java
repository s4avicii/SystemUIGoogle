package com.android.systemui.util.sensors;

import android.content.res.Resources;
import com.android.systemui.util.concurrency.Execution;
import com.android.systemui.util.sensors.ThresholdSensorImpl;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class ThresholdSensorImpl_Builder_Factory implements Factory<ThresholdSensorImpl.Builder> {
    public final Provider<Execution> executionProvider;
    public final Provider<Resources> resourcesProvider;
    public final Provider<AsyncSensorManager> sensorManagerProvider;

    public final Object get() {
        return new ThresholdSensorImpl.Builder(this.resourcesProvider.get(), this.sensorManagerProvider.get(), this.executionProvider.get());
    }

    public ThresholdSensorImpl_Builder_Factory(Provider<Resources> provider, Provider<AsyncSensorManager> provider2, Provider<Execution> provider3) {
        this.resourcesProvider = provider;
        this.sensorManagerProvider = provider2;
        this.executionProvider = provider3;
    }
}
