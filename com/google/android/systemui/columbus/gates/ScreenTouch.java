package com.google.android.systemui.columbus.gates;

import android.content.Context;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.Choreographer;
import android.view.InputMonitor;
import com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver;
import com.google.android.setupcompat.util.Logger;

/* compiled from: ScreenTouch.kt */
public final class ScreenTouch extends Gate {
    public final ScreenTouch$clearBlocking$1 clearBlocking = new ScreenTouch$clearBlocking$1(this);
    public final ScreenTouch$gateListener$1 gateListener = new ScreenTouch$gateListener$1(this);
    public final Handler handler;
    public final ScreenTouch$inputEventListener$1 inputEventListener = new ScreenTouch$inputEventListener$1(this);
    public InputChannelCompat$InputEventReceiver inputEventReceiver;
    public Logger inputMonitor;
    public final PowerState powerState;
    public final RectF touchRegion;

    public final void onActivate() {
        this.powerState.registerListener(this.gateListener);
        if (!this.powerState.isBlocking()) {
            startListeningForTouch();
        }
        setBlocking(false);
    }

    public final void onDeactivate() {
        this.powerState.unregisterListener(this.gateListener);
        InputChannelCompat$InputEventReceiver inputChannelCompat$InputEventReceiver = this.inputEventReceiver;
        if (inputChannelCompat$InputEventReceiver != null) {
            inputChannelCompat$InputEventReceiver.mReceiver.dispose();
        }
        this.inputEventReceiver = null;
        Logger logger = this.inputMonitor;
        if (logger != null) {
            ((InputMonitor) logger.prefix).dispose();
        }
        this.inputMonitor = null;
    }

    public final void startListeningForTouch() {
        if (this.inputEventReceiver == null) {
            Logger logger = new Logger("Quick Tap", 0);
            this.inputMonitor = logger;
            this.inputEventReceiver = logger.getInputReceiver(Looper.getMainLooper(), Choreographer.getInstance(), this.inputEventListener);
        }
    }

    public ScreenTouch(Context context, PowerState powerState2, Handler handler2) {
        super(context);
        this.powerState = powerState2;
        this.handler = handler2;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float f = displayMetrics.density * ((float) 32);
        this.touchRegion = new RectF(f, f, ((float) displayMetrics.widthPixels) - f, ((float) displayMetrics.heightPixels) - f);
    }
}
