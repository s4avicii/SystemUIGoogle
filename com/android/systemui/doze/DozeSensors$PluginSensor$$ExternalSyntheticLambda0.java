package com.android.systemui.doze;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.SystemClock;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline2;
import com.android.systemui.doze.DozeSensors;
import com.android.systemui.plugins.SensorManagerPlugin;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DozeSensors$PluginSensor$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ DozeSensors.PluginSensor f$0;
    public final /* synthetic */ SensorManagerPlugin.SensorEvent f$1;

    public /* synthetic */ DozeSensors$PluginSensor$$ExternalSyntheticLambda0(DozeSensors.PluginSensor pluginSensor, SensorManagerPlugin.SensorEvent sensorEvent) {
        this.f$0 = pluginSensor;
        this.f$1 = sensorEvent;
    }

    public final void run() {
        DozeSensors.PluginSensor pluginSensor = this.f$0;
        SensorManagerPlugin.SensorEvent sensorEvent = this.f$1;
        int i = DozeSensors.PluginSensor.$r8$clinit;
        Objects.requireNonNull(pluginSensor);
        if (SystemClock.uptimeMillis() < DozeSensors.this.mDebounceFrom + pluginSensor.mDebounce) {
            ExifInterface$$ExternalSyntheticOutline2.m15m(VendorAtomValue$$ExternalSyntheticOutline1.m1m("onSensorEvent dropped: "), DozeSensors.PluginSensor.triggerEventToString(sensorEvent), "DozeSensors");
            return;
        }
        if (DozeSensors.DEBUG) {
            ExifInterface$$ExternalSyntheticOutline2.m15m(VendorAtomValue$$ExternalSyntheticOutline1.m1m("onSensorEvent: "), DozeSensors.PluginSensor.triggerEventToString(sensorEvent), "DozeSensors");
        }
        DozeSensors.Callback callback = DozeSensors.this.mSensorCallback;
        int i2 = pluginSensor.mPulseReason;
        float[] values = sensorEvent.getValues();
        DozeTriggers$$ExternalSyntheticLambda0 dozeTriggers$$ExternalSyntheticLambda0 = (DozeTriggers$$ExternalSyntheticLambda0) callback;
        Objects.requireNonNull(dozeTriggers$$ExternalSyntheticLambda0);
        ((DozeTriggers) dozeTriggers$$ExternalSyntheticLambda0.f$0).onSensor(i2, -1.0f, -1.0f, values);
    }
}
