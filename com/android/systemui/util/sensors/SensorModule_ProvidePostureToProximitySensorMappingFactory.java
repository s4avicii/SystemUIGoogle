package com.android.systemui.util.sensors;

import android.content.res.Resources;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.util.sensors.ThresholdSensorImpl;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class SensorModule_ProvidePostureToProximitySensorMappingFactory implements Factory<ThresholdSensor[]> {
    public final Provider<Resources> resourcesProvider;
    public final Provider<ThresholdSensorImpl.BuilderFactory> thresholdSensorImplBuilderFactoryProvider;

    public final Object get() {
        return SensorModule.createPostureToSensorMapping(this.thresholdSensorImplBuilderFactoryProvider.get(), this.resourcesProvider.get().getStringArray(C1777R.array.proximity_sensor_posture_mapping), C1777R.dimen.proximity_sensor_threshold, C1777R.dimen.proximity_sensor_threshold_latch);
    }

    public SensorModule_ProvidePostureToProximitySensorMappingFactory(ThresholdSensorImpl_BuilderFactory_Factory thresholdSensorImpl_BuilderFactory_Factory, Provider provider) {
        this.thresholdSensorImplBuilderFactoryProvider = thresholdSensorImpl_BuilderFactory_Factory;
        this.resourcesProvider = provider;
    }
}
