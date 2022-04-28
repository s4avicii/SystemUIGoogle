package com.google.android.systemui.columbus.actions;

import android.app.IActivityManager;
import android.content.Context;
import android.content.Intent;
import com.google.android.systemui.columbus.gates.SilenceAlertsDisabled;

/* compiled from: DismissTimer.kt */
public final class DismissTimer extends DeskClockAction {
    public final String tag = "Columbus/DismissTimer";

    public final String getAlertAction() {
        return "com.google.android.deskclock.action.TIMER_ALERT";
    }

    public final String getDoneAction() {
        return "com.google.android.deskclock.action.TIMER_DONE";
    }

    public final Intent createDismissIntent() {
        return new Intent("android.intent.action.DISMISS_TIMER");
    }

    public DismissTimer(Context context, SilenceAlertsDisabled silenceAlertsDisabled, IActivityManager iActivityManager) {
        super(context, silenceAlertsDisabled, iActivityManager);
    }

    /* renamed from: getTag$vendor__unbundled_google__packages__SystemUIGoogle__android_common__sysuig */
    public final String mo19228xa00bbd41() {
        return this.tag;
    }
}
