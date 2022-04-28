package com.google.android.systemui.columbus.sensors;

import com.android.systemui.keyguard.WakefulnessLifecycle;

/* compiled from: CHREGestureSensor.kt */
public final class CHREGestureSensor$wakefulnessLifecycleObserver$1 implements WakefulnessLifecycle.Observer {
    public final /* synthetic */ CHREGestureSensor this$0;

    public CHREGestureSensor$wakefulnessLifecycleObserver$1(CHREGestureSensor cHREGestureSensor) {
        this.this$0 = cHREGestureSensor;
    }

    public final void onFinishedGoingToSleep() {
        CHREGestureSensor.access$handleWakefullnessChanged(this.this$0, false);
    }

    public final void onFinishedWakingUp() {
        CHREGestureSensor.access$handleWakefullnessChanged(this.this$0, true);
    }

    public final void onStartedGoingToSleep() {
        CHREGestureSensor.access$handleWakefullnessChanged(this.this$0, false);
    }

    public final void onStartedWakingUp() {
        CHREGestureSensor.access$handleWakefullnessChanged(this.this$0, false);
    }
}
