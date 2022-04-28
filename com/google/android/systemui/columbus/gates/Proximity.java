package com.google.android.systemui.columbus.gates;

import android.content.Context;
import com.android.systemui.util.sensors.ProximitySensor;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Proximity.kt */
public final class Proximity extends Gate {
    public final Proximity$proximityListener$1 proximityListener = new Proximity$proximityListener$1(this);
    public final ProximitySensor proximitySensor;

    public final void onActivate() {
        this.proximitySensor.register(this.proximityListener);
        setBlocking(!Intrinsics.areEqual(this.proximitySensor.isNear(), Boolean.FALSE));
    }

    public final void onDeactivate() {
        this.proximitySensor.unregister(this.proximityListener);
    }

    public Proximity(Context context, ProximitySensor proximitySensor2) {
        super(context);
        this.proximitySensor = proximitySensor2;
        proximitySensor2.setTag("Columbus/Proximity");
    }
}
