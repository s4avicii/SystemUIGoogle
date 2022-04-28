package com.google.android.systemui.columbus.gates;

import android.content.Context;
import android.content.IntentFilter;
import android.os.PowerManager;
import androidx.recyclerview.widget.LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0;
import com.android.systemui.globalactions.GlobalActionsDialogLite;

/* compiled from: PowerSaveState.kt */
public final class PowerSaveState extends Gate {
    public boolean batterySaverEnabled;
    public boolean isDeviceInteractive;
    public final PowerManager powerManager;
    public final PowerSaveState$receiver$1 receiver = new PowerSaveState$receiver$1(this);

    public final void onActivate() {
        IntentFilter intentFilter = new IntentFilter("android.os.action.POWER_SAVE_MODE_CHANGED");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        this.context.registerReceiver(this.receiver, intentFilter);
        refreshStatus();
    }

    public final void onDeactivate() {
        this.context.unregisterReceiver(this.receiver);
    }

    public final void refreshStatus() {
        boolean z;
        boolean z2;
        android.os.PowerSaveState powerSaveState;
        PowerManager powerManager2 = this.powerManager;
        boolean z3 = true;
        if (powerManager2 == null || (powerSaveState = powerManager2.getPowerSaveState(13)) == null || !powerSaveState.batterySaverEnabled) {
            z = false;
        } else {
            z = true;
        }
        this.batterySaverEnabled = z;
        PowerManager powerManager3 = this.powerManager;
        if (powerManager3 != null && powerManager3.isInteractive()) {
            z2 = true;
        } else {
            z2 = false;
        }
        this.isDeviceInteractive = z2;
        if (!this.batterySaverEnabled || z2) {
            z3 = false;
        }
        setBlocking(z3);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("[batterySaverEnabled -> ");
        sb.append(this.batterySaverEnabled);
        sb.append("; isDeviceInteractive -> ");
        return LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0.m21m(sb, this.isDeviceInteractive, ']');
    }

    public PowerSaveState(Context context) {
        super(context);
        this.powerManager = (PowerManager) context.getSystemService(GlobalActionsDialogLite.GLOBAL_ACTION_KEY_POWER);
    }
}
