package com.google.android.systemui.elmyra.sensors.config;

import android.content.Context;
import android.os.PowerManager;
import android.util.TypedValue;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dependency;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import java.util.Objects;
import java.util.function.Consumer;

public final class ScreenStateAdjustment extends Adjustment {
    public final C22671 mKeyguardUpdateMonitorCallback;
    public final PowerManager mPowerManager;
    public final float mScreenOffAdjustment;

    public final float adjustSensitivity(float f) {
        if (!this.mPowerManager.isInteractive()) {
            return f + this.mScreenOffAdjustment;
        }
        return f;
    }

    public ScreenStateAdjustment(Context context) {
        super(context);
        C22671 r0 = new KeyguardUpdateMonitorCallback() {
            public final void onFinishedGoingToSleep(int i) {
                ScreenStateAdjustment screenStateAdjustment = ScreenStateAdjustment.this;
                Objects.requireNonNull(screenStateAdjustment);
                Consumer<Adjustment> consumer = screenStateAdjustment.mCallback;
                if (consumer != null) {
                    consumer.accept(screenStateAdjustment);
                }
            }

            public final void onStartedWakingUp() {
                ScreenStateAdjustment screenStateAdjustment = ScreenStateAdjustment.this;
                Objects.requireNonNull(screenStateAdjustment);
                Consumer<Adjustment> consumer = screenStateAdjustment.mCallback;
                if (consumer != null) {
                    consumer.accept(screenStateAdjustment);
                }
            }
        };
        this.mKeyguardUpdateMonitorCallback = r0;
        this.mPowerManager = (PowerManager) context.getSystemService(GlobalActionsDialogLite.GLOBAL_ACTION_KEY_POWER);
        TypedValue typedValue = new TypedValue();
        context.getResources().getValue(C1777R.dimen.elmyra_screen_off_adjustment, typedValue, true);
        this.mScreenOffAdjustment = typedValue.getFloat();
        ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).registerCallback(r0);
    }
}
