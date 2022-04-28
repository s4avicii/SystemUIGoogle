package com.google.android.systemui.columbus;

import android.content.Context;
import android.os.PowerManager;
import com.android.systemui.globalactions.GlobalActionsDialogLite;

/* compiled from: PowerManagerWrapper.kt */
public final class PowerManagerWrapper {
    public final PowerManager powerManager;

    /* compiled from: PowerManagerWrapper.kt */
    public static class WakeLockWrapper {
        public final PowerManager.WakeLock wakeLock;

        public WakeLockWrapper(PowerManager.WakeLock wakeLock2) {
            this.wakeLock = wakeLock2;
        }
    }

    public PowerManagerWrapper(Context context) {
        this.powerManager = (PowerManager) context.getSystemService(GlobalActionsDialogLite.GLOBAL_ACTION_KEY_POWER);
    }
}
