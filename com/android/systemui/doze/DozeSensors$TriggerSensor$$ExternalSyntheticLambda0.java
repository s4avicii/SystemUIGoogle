package com.android.systemui.doze;

import android.hardware.Sensor;
import android.hardware.TriggerEvent;
import com.android.systemui.doze.DozeSensors;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DozeSensors$TriggerSensor$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ DozeSensors.TriggerSensor f$0;
    public final /* synthetic */ TriggerEvent f$1;
    public final /* synthetic */ Sensor f$2;

    public /* synthetic */ DozeSensors$TriggerSensor$$ExternalSyntheticLambda0(DozeSensors.TriggerSensor triggerSensor, TriggerEvent triggerEvent, Sensor sensor) {
        this.f$0 = triggerSensor;
        this.f$1 = triggerEvent;
        this.f$2 = sensor;
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0097  */
    /* JADX WARNING: Removed duplicated region for block: B:28:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r8 = this;
            com.android.systemui.doze.DozeSensors$TriggerSensor r0 = r8.f$0
            android.hardware.TriggerEvent r1 = r8.f$1
            android.hardware.Sensor r8 = r8.f$2
            int r2 = com.android.systemui.doze.DozeSensors.TriggerSensor.$r8$clinit
            java.util.Objects.requireNonNull(r0)
            boolean r2 = com.android.systemui.doze.DozeSensors.DEBUG
            r3 = 0
            if (r2 == 0) goto L_0x0059
            java.lang.String r2 = "onTrigger: "
            java.lang.StringBuilder r2 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r2)
            if (r1 != 0) goto L_0x001a
            r4 = 0
            goto L_0x0054
        L_0x001a:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "SensorEvent["
            r4.<init>(r5)
            long r5 = r1.timestamp
            r4.append(r5)
            r5 = 44
            r4.append(r5)
            android.hardware.Sensor r6 = r1.sensor
            java.lang.String r6 = r6.getName()
            r4.append(r6)
            float[] r6 = r1.values
            if (r6 == 0) goto L_0x004b
            r6 = r3
        L_0x0039:
            float[] r7 = r1.values
            int r7 = r7.length
            if (r6 >= r7) goto L_0x004b
            r4.append(r5)
            float[] r7 = r1.values
            r7 = r7[r6]
            r4.append(r7)
            int r6 = r6 + 1
            goto L_0x0039
        L_0x004b:
            r5 = 93
            r4.append(r5)
            java.lang.String r4 = r4.toString()
        L_0x0054:
            java.lang.String r5 = "DozeSensors"
            androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline2.m15m(r2, r4, r5)
        L_0x0059:
            if (r8 == 0) goto L_0x006a
            int r8 = r8.getType()
            r2 = 25
            if (r8 != r2) goto L_0x006a
            com.android.internal.logging.UiEventLoggerImpl r8 = com.android.systemui.doze.DozeSensors.UI_EVENT_LOGGER
            com.android.systemui.doze.DozeSensors$DozeSensorsUiEvent r2 = com.android.systemui.doze.DozeSensors.DozeSensorsUiEvent.ACTION_AMBIENT_GESTURE_PICKUP
            r8.log(r2)
        L_0x006a:
            r0.mRegistered = r3
            boolean r8 = r0.mReportsTouchCoordinates
            r2 = -1082130432(0xffffffffbf800000, float:-1.0)
            if (r8 == 0) goto L_0x007e
            float[] r8 = r1.values
            int r4 = r8.length
            r5 = 2
            if (r4 < r5) goto L_0x007e
            r2 = r8[r3]
            r3 = 1
            r8 = r8[r3]
            goto L_0x007f
        L_0x007e:
            r8 = r2
        L_0x007f:
            com.android.systemui.doze.DozeSensors r3 = com.android.systemui.doze.DozeSensors.this
            com.android.systemui.doze.DozeSensors$Callback r3 = r3.mSensorCallback
            int r4 = r0.mPulseReason
            float[] r1 = r1.values
            com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda0 r3 = (com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda0) r3
            java.util.Objects.requireNonNull(r3)
            java.lang.Object r3 = r3.f$0
            com.android.systemui.doze.DozeTriggers r3 = (com.android.systemui.doze.DozeTriggers) r3
            r3.onSensor(r4, r2, r8, r1)
            boolean r8 = r0.mRegistered
            if (r8 != 0) goto L_0x009a
            r0.updateListening()
        L_0x009a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.doze.DozeSensors$TriggerSensor$$ExternalSyntheticLambda0.run():void");
    }
}
