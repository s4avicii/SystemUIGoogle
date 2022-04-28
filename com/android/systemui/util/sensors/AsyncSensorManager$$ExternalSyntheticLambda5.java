package com.android.systemui.util.sensors;

import android.hardware.SensorManager;
import android.os.Handler;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AsyncSensorManager$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ AsyncSensorManager f$0;
    public final /* synthetic */ SensorManager.DynamicSensorCallback f$1;
    public final /* synthetic */ Handler f$2;

    public /* synthetic */ AsyncSensorManager$$ExternalSyntheticLambda5(AsyncSensorManager asyncSensorManager, SensorManager.DynamicSensorCallback dynamicSensorCallback, Handler handler) {
        this.f$0 = asyncSensorManager;
        this.f$1 = dynamicSensorCallback;
        this.f$2 = handler;
    }

    public final void run() {
        AsyncSensorManager asyncSensorManager = this.f$0;
        SensorManager.DynamicSensorCallback dynamicSensorCallback = this.f$1;
        Handler handler = this.f$2;
        Objects.requireNonNull(asyncSensorManager);
        asyncSensorManager.mInner.registerDynamicSensorCallback(dynamicSensorCallback, handler);
    }
}
