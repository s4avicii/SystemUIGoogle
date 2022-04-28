package com.google.android.systemui.columbus.gates;

import com.android.systemui.keyguard.WakefulnessLifecycle;

/* compiled from: PowerState.kt */
public final class PowerState$wakefulnessLifecycleObserver$1 implements WakefulnessLifecycle.Observer {
    public final /* synthetic */ PowerState this$0;

    public PowerState$wakefulnessLifecycleObserver$1(PowerState powerState) {
        this.this$0 = powerState;
    }

    public final void onFinishedGoingToSleep() {
        this.this$0.updateBlocking();
    }

    public final void onStartedWakingUp() {
        this.this$0.updateBlocking();
    }
}
