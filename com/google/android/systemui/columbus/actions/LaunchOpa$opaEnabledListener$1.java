package com.google.android.systemui.columbus.actions;

import android.content.Context;
import android.util.Log;
import com.google.android.systemui.assist.OpaEnabledListener;

/* compiled from: LaunchOpa.kt */
public final class LaunchOpa$opaEnabledListener$1 implements OpaEnabledListener {
    public final /* synthetic */ LaunchOpa this$0;

    public LaunchOpa$opaEnabledListener$1(LaunchOpa launchOpa) {
        this.this$0 = launchOpa;
    }

    public final void onOpaEnabledReceived(Context context, boolean z, boolean z2, boolean z3, boolean z4) {
        boolean z5;
        boolean z6;
        boolean z7 = true;
        if (z2 || this.this$0.enableForAnyAssistant) {
            z5 = true;
        } else {
            z5 = false;
        }
        Log.i("Columbus/LaunchOpa", "eligible: " + z + ", supported: " + z5 + ", opa: " + z3);
        LaunchOpa launchOpa = this.this$0;
        if (!z || !z5 || !z3) {
            z6 = false;
        } else {
            z6 = true;
        }
        launchOpa.isOpaEnabled = z6;
        if (!launchOpa.isGestureEnabled || !z6) {
            z7 = false;
        }
        launchOpa.setAvailable(z7);
    }
}
