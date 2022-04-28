package com.google.android.systemui.elmyra.gates;

import android.content.Context;
import android.os.PowerManager;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Dependency;
import com.android.systemui.globalactions.GlobalActionsDialogLite;

public class PowerState extends Gate {
    public final C22581 mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() {
        public final void onFinishedGoingToSleep(int i) {
            PowerState.this.notifyListener();
        }

        public final void onStartedWakingUp() {
            PowerState.this.notifyListener();
        }
    };
    public final PowerManager mPowerManager;

    public boolean isBlocked() {
        return !this.mPowerManager.isInteractive();
    }

    public void onActivate() {
        ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).registerCallback(this.mKeyguardUpdateMonitorCallback);
    }

    public void onDeactivate() {
        ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).removeCallback(this.mKeyguardUpdateMonitorCallback);
    }

    public PowerState(Context context) {
        super(context);
        this.mPowerManager = (PowerManager) context.getSystemService(GlobalActionsDialogLite.GLOBAL_ACTION_KEY_POWER);
    }
}
