package com.google.android.systemui.columbus.gates;

import android.view.InputEvent;
import android.view.MotionEvent;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import java.util.Objects;

/* compiled from: ScreenTouch.kt */
public final class ScreenTouch$inputEventListener$1 implements InputChannelCompat$InputEventListener {
    public final /* synthetic */ ScreenTouch this$0;

    public ScreenTouch$inputEventListener$1(ScreenTouch screenTouch) {
        this.this$0 = screenTouch;
    }

    public final void onInputEvent(InputEvent inputEvent) {
        MotionEvent motionEvent;
        boolean z;
        if (inputEvent != null) {
            ScreenTouch screenTouch = this.this$0;
            Objects.requireNonNull(screenTouch);
            if (inputEvent instanceof MotionEvent) {
                motionEvent = (MotionEvent) inputEvent;
            } else {
                motionEvent = null;
            }
            if (motionEvent != null) {
                if (motionEvent.getAction() != 0 || !screenTouch.touchRegion.contains(motionEvent.getRawX(), motionEvent.getRawY())) {
                    z = false;
                } else {
                    z = true;
                }
                if (z) {
                    screenTouch.handler.removeCallbacks(screenTouch.clearBlocking);
                    screenTouch.setBlocking(true);
                } else if (motionEvent.getAction() == 1 && screenTouch.isBlocking()) {
                    screenTouch.handler.postDelayed(screenTouch.clearBlocking, 500);
                }
            }
        }
    }
}
