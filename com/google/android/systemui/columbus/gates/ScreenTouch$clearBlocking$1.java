package com.google.android.systemui.columbus.gates;

/* compiled from: ScreenTouch.kt */
public final class ScreenTouch$clearBlocking$1 implements Runnable {
    public final /* synthetic */ ScreenTouch this$0;

    public ScreenTouch$clearBlocking$1(ScreenTouch screenTouch) {
        this.this$0 = screenTouch;
    }

    public final void run() {
        this.this$0.setBlocking(false);
    }
}
