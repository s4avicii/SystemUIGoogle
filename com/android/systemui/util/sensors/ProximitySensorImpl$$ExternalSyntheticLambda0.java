package com.android.systemui.util.sensors;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.util.sensors.ThresholdSensor;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ProximitySensorImpl$$ExternalSyntheticLambda0 implements ThresholdSensor.Listener {
    public final /* synthetic */ ProximitySensorImpl f$0;

    public /* synthetic */ ProximitySensorImpl$$ExternalSyntheticLambda0(ProximitySensorImpl proximitySensorImpl) {
        this.f$0 = proximitySensorImpl;
    }

    public final void onThresholdCrossed(ThresholdSensorEvent thresholdSensorEvent) {
        String str;
        ProximitySensorImpl proximitySensorImpl = this.f$0;
        Objects.requireNonNull(proximitySensorImpl);
        proximitySensorImpl.mExecution.assertIsMainThread();
        if (proximitySensorImpl.mLastPrimaryEvent != null) {
            Objects.requireNonNull(thresholdSensorEvent);
            boolean z = thresholdSensorEvent.mBelow;
            ThresholdSensorEvent thresholdSensorEvent2 = proximitySensorImpl.mLastPrimaryEvent;
            Objects.requireNonNull(thresholdSensorEvent2);
            if (z == thresholdSensorEvent2.mBelow) {
                return;
            }
        }
        proximitySensorImpl.mLastPrimaryEvent = thresholdSensorEvent;
        if (proximitySensorImpl.mSecondarySafe && proximitySensorImpl.mSecondaryThresholdSensor.isLoaded()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Primary sensor reported ");
            Objects.requireNonNull(thresholdSensorEvent);
            if (thresholdSensorEvent.mBelow) {
                str = "near";
            } else {
                str = "far";
            }
            sb.append(str);
            sb.append(". Checking secondary.");
            proximitySensorImpl.logDebug(sb.toString());
            if (proximitySensorImpl.mCancelSecondaryRunnable == null) {
                proximitySensorImpl.mSecondaryThresholdSensor.resume();
            }
        } else if (!proximitySensorImpl.mSecondaryThresholdSensor.isLoaded()) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Primary sensor event: ");
            Objects.requireNonNull(thresholdSensorEvent);
            sb2.append(thresholdSensorEvent.mBelow);
            sb2.append(". No secondary.");
            proximitySensorImpl.logDebug(sb2.toString());
            proximitySensorImpl.onSensorEvent(thresholdSensorEvent);
        } else {
            Objects.requireNonNull(thresholdSensorEvent);
            if (thresholdSensorEvent.mBelow) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Primary sensor event: ");
                m.append(thresholdSensorEvent.mBelow);
                m.append(". Checking secondary.");
                proximitySensorImpl.logDebug(m.toString());
                Runnable runnable = proximitySensorImpl.mCancelSecondaryRunnable;
                if (runnable != null) {
                    runnable.run();
                }
                proximitySensorImpl.mSecondaryThresholdSensor.resume();
                return;
            }
            proximitySensorImpl.onSensorEvent(thresholdSensorEvent);
        }
    }
}
