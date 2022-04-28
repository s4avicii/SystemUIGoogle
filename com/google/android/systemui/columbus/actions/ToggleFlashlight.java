package com.google.android.systemui.columbus.actions;

import android.content.Context;
import android.os.Handler;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.google.android.systemui.columbus.ColumbusEvent;
import com.google.android.systemui.columbus.sensors.GestureSensor;
import java.util.concurrent.TimeUnit;

/* compiled from: ToggleFlashlight.kt */
public final class ToggleFlashlight extends UserAction {
    public static final long FLASHLIGHT_TIMEOUT = TimeUnit.MINUTES.toMillis(2);
    public final FlashlightController flashlightController;
    public final Handler handler;
    public final String tag = "ToggleFlashlight";
    public final ToggleFlashlight$turnOffFlashlight$1 turnOffFlashlight;
    public final UiEventLogger uiEventLogger;

    public final boolean availableOnLockscreen() {
        return true;
    }

    public final void onTrigger(GestureSensor.DetectionProperties detectionProperties) {
        this.handler.removeCallbacks(this.turnOffFlashlight);
        boolean z = !this.flashlightController.isEnabled();
        this.flashlightController.setFlashlight(z);
        if (z) {
            this.handler.postDelayed(this.turnOffFlashlight, FLASHLIGHT_TIMEOUT);
        }
        this.uiEventLogger.log(ColumbusEvent.COLUMBUS_INVOKED_FLASHLIGHT_TOGGLE);
    }

    public final void updateAvailable() {
        boolean z;
        if (!this.flashlightController.hasFlashlight() || !this.flashlightController.isAvailable()) {
            z = false;
        } else {
            z = true;
        }
        setAvailable(z);
    }

    public ToggleFlashlight(Context context, FlashlightController flashlightController2, Handler handler2, UiEventLogger uiEventLogger2) {
        super(context);
        this.flashlightController = flashlightController2;
        this.handler = handler2;
        this.uiEventLogger = uiEventLogger2;
        ToggleFlashlight$flashlightListener$1 toggleFlashlight$flashlightListener$1 = new ToggleFlashlight$flashlightListener$1(this);
        this.turnOffFlashlight = new ToggleFlashlight$turnOffFlashlight$1(this);
        flashlightController2.addCallback(toggleFlashlight$flashlightListener$1);
        updateAvailable();
    }

    /* renamed from: getTag$vendor__unbundled_google__packages__SystemUIGoogle__android_common__sysuig */
    public final String mo19228xa00bbd41() {
        return this.tag;
    }
}
