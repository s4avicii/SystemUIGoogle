package com.android.systemui.util.sensors;

import android.hardware.SensorManager;
import com.android.systemui.util.sensors.ThresholdSensorImpl;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class SensorModule_ProvidePrimaryProximitySensorFactory implements Factory<ThresholdSensor> {
    public final Provider<SensorManager> sensorManagerProvider;
    public final Provider<ThresholdSensorImpl.Builder> thresholdSensorBuilderProvider;

    /* JADX WARNING: Can't wrap try/catch for region: R(11:1|2|(1:4)|5|(3:6|7|(1:9))|10|12|13|14|15|26) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:14:0x004a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object get() {
        /*
            r4 = this;
            javax.inject.Provider<android.hardware.SensorManager> r0 = r4.sensorManagerProvider
            java.lang.Object r0 = r0.get()
            android.hardware.SensorManager r0 = (android.hardware.SensorManager) r0
            javax.inject.Provider<com.android.systemui.util.sensors.ThresholdSensorImpl$Builder> r4 = r4.thresholdSensorBuilderProvider
            java.lang.Object r4 = r4.get()
            com.android.systemui.util.sensors.ThresholdSensorImpl$Builder r4 = (com.android.systemui.util.sensors.ThresholdSensorImpl.Builder) r4
            r1 = 1
            java.util.Objects.requireNonNull(r4)     // Catch:{ IllegalStateException -> 0x004f }
            r2 = 3
            r4.mSensorDelay = r2     // Catch:{ IllegalStateException -> 0x004f }
            r2 = 2131953036(0x7f13058c, float:1.9542532E38)
            android.content.res.Resources r3 = r4.mResources     // Catch:{ IllegalStateException -> 0x004f }
            java.lang.String r2 = r3.getString(r2)     // Catch:{ IllegalStateException -> 0x004f }
            android.hardware.Sensor r2 = r4.findSensorByType(r2, r1)     // Catch:{ IllegalStateException -> 0x004f }
            if (r2 == 0) goto L_0x002a
            r4.mSensor = r2     // Catch:{ IllegalStateException -> 0x004f }
            r4.mSensorSet = r1     // Catch:{ IllegalStateException -> 0x004f }
        L_0x002a:
            r2 = 2131166832(0x7f070670, float:1.794792E38)
            android.content.res.Resources r3 = r4.mResources     // Catch:{ NotFoundException -> 0x003d }
            float r2 = r3.getFloat(r2)     // Catch:{ NotFoundException -> 0x003d }
            r4.mThresholdValue = r2     // Catch:{ NotFoundException -> 0x003d }
            r4.mThresholdSet = r1     // Catch:{ NotFoundException -> 0x003d }
            boolean r3 = r4.mThresholdLatchValueSet     // Catch:{ NotFoundException -> 0x003d }
            if (r3 != 0) goto L_0x003d
            r4.mThresholdLatchValue = r2     // Catch:{ NotFoundException -> 0x003d }
        L_0x003d:
            r2 = 2131166833(0x7f070671, float:1.7947923E38)
            android.content.res.Resources r3 = r4.mResources     // Catch:{ NotFoundException -> 0x004a }
            float r2 = r3.getFloat(r2)     // Catch:{ NotFoundException -> 0x004a }
            r4.mThresholdLatchValue = r2     // Catch:{ NotFoundException -> 0x004a }
            r4.mThresholdLatchValueSet = r1     // Catch:{ NotFoundException -> 0x004a }
        L_0x004a:
            com.android.systemui.util.sensors.ThresholdSensorImpl r4 = r4.build()     // Catch:{ IllegalStateException -> 0x004f }
            goto L_0x0072
        L_0x004f:
            r2 = 8
            android.hardware.Sensor r0 = r0.getDefaultSensor(r2, r1)
            java.util.Objects.requireNonNull(r4)
            r4.mSensor = r0
            r4.mSensorSet = r1
            if (r0 == 0) goto L_0x0063
            float r0 = r0.getMaximumRange()
            goto L_0x0064
        L_0x0063:
            r0 = 0
        L_0x0064:
            r4.mThresholdValue = r0
            r4.mThresholdSet = r1
            boolean r1 = r4.mThresholdLatchValueSet
            if (r1 != 0) goto L_0x006e
            r4.mThresholdLatchValue = r0
        L_0x006e:
            com.android.systemui.util.sensors.ThresholdSensorImpl r4 = r4.build()
        L_0x0072:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.sensors.SensorModule_ProvidePrimaryProximitySensorFactory.get():java.lang.Object");
    }

    public SensorModule_ProvidePrimaryProximitySensorFactory(Provider<SensorManager> provider, Provider<ThresholdSensorImpl.Builder> provider2) {
        this.sensorManagerProvider = provider;
        this.thresholdSensorBuilderProvider = provider2;
    }
}
