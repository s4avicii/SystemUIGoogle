package com.android.systemui.util.sensors;

import com.android.systemui.util.sensors.ThresholdSensorImpl;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class SensorModule_ProvideSecondaryProximitySensorFactory implements Factory<ThresholdSensor> {
    public final Provider<ThresholdSensorImpl.Builder> thresholdSensorBuilderProvider;

    /* JADX WARNING: Can't wrap try/catch for region: R(11:1|2|(1:4)|5|(3:6|7|(1:9))|10|12|13|14|15|22) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:14:0x003f */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object get() {
        /*
            r3 = this;
            javax.inject.Provider<com.android.systemui.util.sensors.ThresholdSensorImpl$Builder> r3 = r3.thresholdSensorBuilderProvider
            java.lang.Object r3 = r3.get()
            com.android.systemui.util.sensors.ThresholdSensorImpl$Builder r3 = (com.android.systemui.util.sensors.ThresholdSensorImpl.Builder) r3
            r0 = 1
            java.util.Objects.requireNonNull(r3)     // Catch:{ IllegalStateException -> 0x0044 }
            android.content.res.Resources r1 = r3.mResources     // Catch:{ IllegalStateException -> 0x0044 }
            r2 = 2131953035(0x7f13058b, float:1.954253E38)
            java.lang.String r1 = r1.getString(r2)     // Catch:{ IllegalStateException -> 0x0044 }
            android.hardware.Sensor r1 = r3.findSensorByType(r1, r0)     // Catch:{ IllegalStateException -> 0x0044 }
            if (r1 == 0) goto L_0x001f
            r3.mSensor = r1     // Catch:{ IllegalStateException -> 0x0044 }
            r3.mSensorSet = r0     // Catch:{ IllegalStateException -> 0x0044 }
        L_0x001f:
            r1 = 2131166830(0x7f07066e, float:1.7947916E38)
            android.content.res.Resources r2 = r3.mResources     // Catch:{ NotFoundException -> 0x0032 }
            float r1 = r2.getFloat(r1)     // Catch:{ NotFoundException -> 0x0032 }
            r3.mThresholdValue = r1     // Catch:{ NotFoundException -> 0x0032 }
            r3.mThresholdSet = r0     // Catch:{ NotFoundException -> 0x0032 }
            boolean r2 = r3.mThresholdLatchValueSet     // Catch:{ NotFoundException -> 0x0032 }
            if (r2 != 0) goto L_0x0032
            r3.mThresholdLatchValue = r1     // Catch:{ NotFoundException -> 0x0032 }
        L_0x0032:
            r1 = 2131166831(0x7f07066f, float:1.7947918E38)
            android.content.res.Resources r2 = r3.mResources     // Catch:{ NotFoundException -> 0x003f }
            float r1 = r2.getFloat(r1)     // Catch:{ NotFoundException -> 0x003f }
            r3.mThresholdLatchValue = r1     // Catch:{ NotFoundException -> 0x003f }
            r3.mThresholdLatchValueSet = r0     // Catch:{ NotFoundException -> 0x003f }
        L_0x003f:
            com.android.systemui.util.sensors.ThresholdSensorImpl r3 = r3.build()     // Catch:{ IllegalStateException -> 0x0044 }
            goto L_0x005b
        L_0x0044:
            r1 = 0
            java.util.Objects.requireNonNull(r3)
            r3.mSensor = r1
            r3.mSensorSet = r0
            r1 = 0
            r3.mThresholdValue = r1
            r3.mThresholdSet = r0
            boolean r0 = r3.mThresholdLatchValueSet
            if (r0 != 0) goto L_0x0057
            r3.mThresholdLatchValue = r1
        L_0x0057:
            com.android.systemui.util.sensors.ThresholdSensorImpl r3 = r3.build()
        L_0x005b:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.sensors.SensorModule_ProvideSecondaryProximitySensorFactory.get():java.lang.Object");
    }

    public SensorModule_ProvideSecondaryProximitySensorFactory(Provider<ThresholdSensorImpl.Builder> provider) {
        this.thresholdSensorBuilderProvider = provider;
    }
}
