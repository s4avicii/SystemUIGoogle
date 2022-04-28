package com.google.android.systemui.columbus.actions;

/* compiled from: ToggleFlashlight.kt */
public final class ToggleFlashlight$turnOffFlashlight$1 implements Runnable {
    public final /* synthetic */ ToggleFlashlight this$0;

    public ToggleFlashlight$turnOffFlashlight$1(ToggleFlashlight toggleFlashlight) {
        this.this$0 = toggleFlashlight;
    }

    public final void run() {
        this.this$0.flashlightController.setFlashlight(false);
    }
}
