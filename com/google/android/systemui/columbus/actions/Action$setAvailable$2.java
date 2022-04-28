package com.google.android.systemui.columbus.actions;

import com.google.android.systemui.columbus.sensors.GestureSensor;

/* compiled from: Action.kt */
public final class Action$setAvailable$2 implements Runnable {
    public final /* synthetic */ Action this$0;

    public Action$setAvailable$2(Action action) {
        this.this$0 = action;
    }

    public final void run() {
        this.this$0.updateFeedbackEffects(0, (GestureSensor.DetectionProperties) null);
    }
}
