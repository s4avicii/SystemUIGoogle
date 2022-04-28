package com.android.systemui.controls.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* compiled from: ControlsControllerImpl.kt */
public final class ControlsControllerImpl$restoreFinishedReceiver$1 extends BroadcastReceiver {
    public final /* synthetic */ ControlsControllerImpl this$0;

    public ControlsControllerImpl$restoreFinishedReceiver$1(ControlsControllerImpl controlsControllerImpl) {
        this.this$0 = controlsControllerImpl;
    }

    public final void onReceive(Context context, Intent intent) {
        if (intent.getIntExtra("android.intent.extra.USER_ID", -10000) == this.this$0.getCurrentUserId()) {
            ControlsControllerImpl controlsControllerImpl = this.this$0;
            controlsControllerImpl.executor.execute(new ControlsControllerImpl$restoreFinishedReceiver$1$onReceive$1(controlsControllerImpl));
        }
    }
}
