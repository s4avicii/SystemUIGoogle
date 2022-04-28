package com.google.android.systemui.columbus.gates;

import android.view.InputMonitor;
import com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver;
import com.google.android.setupcompat.util.Logger;
import com.google.android.systemui.columbus.gates.Gate;
import java.util.Objects;

/* compiled from: ScreenTouch.kt */
public final class ScreenTouch$gateListener$1 implements Gate.Listener {
    public final /* synthetic */ ScreenTouch this$0;

    public ScreenTouch$gateListener$1(ScreenTouch screenTouch) {
        this.this$0 = screenTouch;
    }

    public final void onGateChanged(Gate gate) {
        if (this.this$0.powerState.isBlocking()) {
            ScreenTouch screenTouch = this.this$0;
            Objects.requireNonNull(screenTouch);
            InputChannelCompat$InputEventReceiver inputChannelCompat$InputEventReceiver = screenTouch.inputEventReceiver;
            if (inputChannelCompat$InputEventReceiver != null) {
                inputChannelCompat$InputEventReceiver.mReceiver.dispose();
            }
            screenTouch.inputEventReceiver = null;
            Logger logger = screenTouch.inputMonitor;
            if (logger != null) {
                ((InputMonitor) logger.prefix).dispose();
            }
            screenTouch.inputMonitor = null;
            return;
        }
        this.this$0.startListeningForTouch();
    }
}
