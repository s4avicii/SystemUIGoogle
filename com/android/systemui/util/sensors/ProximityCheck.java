package com.android.systemui.util.sensors;

import com.android.p012wm.shell.pip.phone.PipMotionHelper$$ExternalSyntheticLambda1;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ProximityCheck implements Runnable {
    public ArrayList mCallbacks = new ArrayList();
    public final DelayableExecutor mDelayableExecutor;
    public final ProximityCheck$$ExternalSyntheticLambda0 mListener;
    public final AtomicBoolean mRegistered = new AtomicBoolean();
    public final ProximitySensor mSensor;

    public final void onProximityEvent(ThresholdSensorEvent thresholdSensorEvent) {
        this.mCallbacks.forEach(new PipMotionHelper$$ExternalSyntheticLambda1(thresholdSensorEvent, 4));
        this.mCallbacks.clear();
        this.mSensor.unregister(this.mListener);
        this.mRegistered.set(false);
        this.mRegistered.set(false);
    }

    public final void run() {
        this.mSensor.unregister(this.mListener);
        this.mRegistered.set(false);
        onProximityEvent((ThresholdSensorEvent) null);
    }

    public ProximityCheck(ProximitySensor proximitySensor, DelayableExecutor delayableExecutor) {
        this.mSensor = proximitySensor;
        proximitySensor.setTag("prox_check");
        this.mDelayableExecutor = delayableExecutor;
        this.mListener = new ProximityCheck$$ExternalSyntheticLambda0(this);
    }
}
