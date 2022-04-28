package com.android.systemui.controls.p004ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* renamed from: com.android.systemui.controls.ui.ControlsActivity$initBroadcastReceiver$1 */
/* compiled from: ControlsActivity.kt */
public final class ControlsActivity$initBroadcastReceiver$1 extends BroadcastReceiver {
    public final /* synthetic */ ControlsActivity this$0;

    public ControlsActivity$initBroadcastReceiver$1(ControlsActivity controlsActivity) {
        this.this$0 = controlsActivity;
    }

    public final void onReceive(Context context, Intent intent) {
        if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
            this.this$0.finish();
        }
    }
}
