package com.google.android.systemui.columbus.actions;

import com.google.android.systemui.columbus.gates.Gate;

/* compiled from: LaunchApp.kt */
public final class LaunchApp$gateListener$1 implements Gate.Listener {
    public final /* synthetic */ LaunchApp this$0;

    public LaunchApp$gateListener$1(LaunchApp launchApp) {
        this.this$0 = launchApp;
    }

    public final void onGateChanged(Gate gate) {
        if (!gate.isBlocking()) {
            this.this$0.updateAvailableAppsAndShortcutsAsync();
        }
    }
}
