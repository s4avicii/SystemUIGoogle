package com.android.systemui.util.sensors;

import android.content.res.Resources;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.util.sensors.ThresholdSensorImpl;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.systemui.util.sensors.SensorModule_ProvidePostureToSecondaryProximitySensorMappingFactory */
public final class C1707xe478f0bc implements Factory<ThresholdSensor[]> {
    public final Provider<Resources> resourcesProvider;
    public final Provider<ThresholdSensorImpl.BuilderFactory> thresholdSensorImplBuilderFactoryProvider;

    public final Object get() {
        return SensorModule.createPostureToSensorMapping(this.thresholdSensorImplBuilderFactoryProvider.get(), this.resourcesProvider.get().getStringArray(C1777R.array.proximity_sensor_secondary_posture_mapping), C1777R.dimen.proximity_sensor_secondary_threshold, C1777R.dimen.proximity_sensor_secondary_threshold_latch);
    }

    public C1707xe478f0bc(ThresholdSensorImpl_BuilderFactory_Factory thresholdSensorImpl_BuilderFactory_Factory, Provider provider) {
        this.thresholdSensorImplBuilderFactoryProvider = thresholdSensorImpl_BuilderFactory_Factory;
        this.resourcesProvider = provider;
    }
}
