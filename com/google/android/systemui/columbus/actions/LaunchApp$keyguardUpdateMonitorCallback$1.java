package com.google.android.systemui.columbus.actions;

import android.content.Context;
import com.android.keyguard.KeyguardUpdateMonitorCallback;

/* compiled from: LaunchApp.kt */
public final class LaunchApp$keyguardUpdateMonitorCallback$1 extends KeyguardUpdateMonitorCallback {
    public final /* synthetic */ Context $context;
    public final /* synthetic */ LaunchApp this$0;

    public LaunchApp$keyguardUpdateMonitorCallback$1(LaunchApp launchApp, Context context) {
        this.this$0 = launchApp;
        this.$context = context;
    }

    public final void onKeyguardBouncerChanged(boolean z) {
        if (z) {
            this.this$0.keyguardUpdateMonitor.removeCallback(this);
            LaunchApp launchApp = this.this$0;
            launchApp.mainHandler.post(new C2196xaa47f519(launchApp, this.$context));
        }
    }
}
