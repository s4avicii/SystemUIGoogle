package com.google.android.systemui.columbus.actions;

import com.android.systemui.statusbar.policy.FlashlightController;

/* compiled from: ToggleFlashlight.kt */
public final class ToggleFlashlight$flashlightListener$1 implements FlashlightController.FlashlightListener {
    public final /* synthetic */ ToggleFlashlight this$0;

    public ToggleFlashlight$flashlightListener$1(ToggleFlashlight toggleFlashlight) {
        this.this$0 = toggleFlashlight;
    }

    public final void onFlashlightAvailabilityChanged(boolean z) {
        if (!z) {
            ToggleFlashlight toggleFlashlight = this.this$0;
            toggleFlashlight.handler.removeCallbacks(toggleFlashlight.turnOffFlashlight);
        }
        this.this$0.updateAvailable();
    }

    public final void onFlashlightChanged(boolean z) {
        if (!z) {
            ToggleFlashlight toggleFlashlight = this.this$0;
            toggleFlashlight.handler.removeCallbacks(toggleFlashlight.turnOffFlashlight);
        }
        this.this$0.updateAvailable();
    }

    public final void onFlashlightError() {
        ToggleFlashlight toggleFlashlight = this.this$0;
        toggleFlashlight.handler.removeCallbacks(toggleFlashlight.turnOffFlashlight);
        this.this$0.updateAvailable();
    }
}
