package com.google.android.systemui.columbus.actions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DeskClockAction.kt */
public final class DeskClockAction$alertReceiver$1 extends BroadcastReceiver {
    public final /* synthetic */ DeskClockAction this$0;

    public final void onReceive(Context context, Intent intent) {
        String str;
        String str2 = null;
        if (intent == null) {
            str = null;
        } else {
            str = intent.getAction();
        }
        if (Intrinsics.areEqual(str, this.this$0.getAlertAction())) {
            this.this$0.alertFiring = true;
        } else {
            if (intent != null) {
                str2 = intent.getAction();
            }
            if (Intrinsics.areEqual(str2, this.this$0.getDoneAction())) {
                this.this$0.alertFiring = false;
            }
        }
        DeskClockAction deskClockAction = this.this$0;
        Objects.requireNonNull(deskClockAction);
        deskClockAction.setAvailable(deskClockAction.alertFiring);
    }

    public DeskClockAction$alertReceiver$1(DeskClockAction deskClockAction) {
        this.this$0 = deskClockAction;
    }
}
