package com.android.systemui.util.sensors;

public final class SensorModule {
    /* JADX WARNING: Can't wrap try/catch for region: R(10:23|(1:25)|26|27|(1:29)|30|31|32|33|42) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:30:0x007d */
    /* JADX WARNING: Missing exception handler attribute for start block: B:32:0x0087 */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x003f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.android.systemui.util.sensors.ThresholdSensor[] createPostureToSensorMapping(com.android.systemui.util.sensors.ThresholdSensorImpl.BuilderFactory r9, java.lang.String[] r10, int r11, int r12) {
        /*
            java.util.Objects.requireNonNull(r9)
            com.android.systemui.util.sensors.AsyncSensorManager r1 = r9.mSensorManager
            com.android.systemui.util.concurrency.Execution r3 = r9.mExecution
            r6 = 3
            r2 = 0
            r5 = 0
            int r0 = (r5 > r5 ? 1 : (r5 == r5 ? 0 : -1))
            if (r0 > 0) goto L_0x0096
            com.android.systemui.util.sensors.ThresholdSensorImpl r7 = new com.android.systemui.util.sensors.ThresholdSensorImpl
            r0 = r7
            r4 = r5
            r0.<init>(r1, r2, r3, r4, r5, r6)
            r0 = 5
            com.android.systemui.util.sensors.ThresholdSensor[] r0 = new com.android.systemui.util.sensors.ThresholdSensor[r0]
            java.util.Arrays.fill(r0, r7)
            r1 = 0
            r2 = 1
            if (r10 == 0) goto L_0x0034
            int r3 = r10.length
            if (r3 != 0) goto L_0x0023
            goto L_0x0034
        L_0x0023:
            int r3 = r10.length
            r4 = r1
        L_0x0025:
            if (r4 >= r3) goto L_0x0034
            r5 = r10[r4]
            boolean r5 = android.text.TextUtils.isEmpty(r5)
            if (r5 != 0) goto L_0x0031
            r3 = r2
            goto L_0x0035
        L_0x0031:
            int r4 = r4 + 1
            goto L_0x0025
        L_0x0034:
            r3 = r1
        L_0x0035:
            if (r3 != 0) goto L_0x003f
            java.lang.String r9 = "SensorModule"
            java.lang.String r10 = "config doesn't support postures, but attempting to retrieve proxSensorMapping"
            android.util.Log.e(r9, r10)
            return r0
        L_0x003f:
            java.util.HashMap r3 = new java.util.HashMap
            r3.<init>()
        L_0x0044:
            int r4 = r10.length
            if (r1 >= r4) goto L_0x0095
            r4 = r10[r1]     // Catch:{ IllegalStateException -> 0x0092 }
            boolean r5 = r3.containsKey(r4)     // Catch:{ IllegalStateException -> 0x0092 }
            if (r5 == 0) goto L_0x0058
            java.lang.Object r4 = r3.get(r4)     // Catch:{ IllegalStateException -> 0x0092 }
            com.android.systemui.util.sensors.ThresholdSensor r4 = (com.android.systemui.util.sensors.ThresholdSensor) r4     // Catch:{ IllegalStateException -> 0x0092 }
            r0[r1] = r4     // Catch:{ IllegalStateException -> 0x0092 }
            goto L_0x0092
        L_0x0058:
            com.android.systemui.util.sensors.ThresholdSensorImpl$Builder r5 = new com.android.systemui.util.sensors.ThresholdSensorImpl$Builder     // Catch:{ IllegalStateException -> 0x0092 }
            android.content.res.Resources r6 = r9.mResources     // Catch:{ IllegalStateException -> 0x0092 }
            com.android.systemui.util.sensors.AsyncSensorManager r7 = r9.mSensorManager     // Catch:{ IllegalStateException -> 0x0092 }
            com.android.systemui.util.concurrency.Execution r8 = r9.mExecution     // Catch:{ IllegalStateException -> 0x0092 }
            r5.<init>(r6, r7, r8)     // Catch:{ IllegalStateException -> 0x0092 }
            r7 = r10[r1]     // Catch:{ IllegalStateException -> 0x0092 }
            android.hardware.Sensor r7 = r5.findSensorByType(r7, r2)     // Catch:{ IllegalStateException -> 0x0092 }
            if (r7 == 0) goto L_0x006f
            r5.mSensor = r7     // Catch:{ IllegalStateException -> 0x0092 }
            r5.mSensorSet = r2     // Catch:{ IllegalStateException -> 0x0092 }
        L_0x006f:
            float r6 = r6.getFloat(r11)     // Catch:{ NotFoundException -> 0x007d }
            r5.mThresholdValue = r6     // Catch:{ NotFoundException -> 0x007d }
            r5.mThresholdSet = r2     // Catch:{ NotFoundException -> 0x007d }
            boolean r7 = r5.mThresholdLatchValueSet     // Catch:{ NotFoundException -> 0x007d }
            if (r7 != 0) goto L_0x007d
            r5.mThresholdLatchValue = r6     // Catch:{ NotFoundException -> 0x007d }
        L_0x007d:
            android.content.res.Resources r6 = r5.mResources     // Catch:{ NotFoundException -> 0x0087 }
            float r6 = r6.getFloat(r12)     // Catch:{ NotFoundException -> 0x0087 }
            r5.mThresholdLatchValue = r6     // Catch:{ NotFoundException -> 0x0087 }
            r5.mThresholdLatchValueSet = r2     // Catch:{ NotFoundException -> 0x0087 }
        L_0x0087:
            com.android.systemui.util.sensors.ThresholdSensorImpl r5 = r5.build()     // Catch:{ IllegalStateException -> 0x0092 }
            r0[r1] = r5     // Catch:{ IllegalStateException -> 0x0092 }
            r5 = r0[r1]     // Catch:{ IllegalStateException -> 0x0092 }
            r3.put(r4, r5)     // Catch:{ IllegalStateException -> 0x0092 }
        L_0x0092:
            int r1 = r1 + 1
            goto L_0x0044
        L_0x0095:
            return r0
        L_0x0096:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "Threshold must be less than or equal to Threshold Latch"
            r9.<init>(r10)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.sensors.SensorModule.createPostureToSensorMapping(com.android.systemui.util.sensors.ThresholdSensorImpl$BuilderFactory, java.lang.String[], int, int):com.android.systemui.util.sensors.ThresholdSensor[]");
    }
}
