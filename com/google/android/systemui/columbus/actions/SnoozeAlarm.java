package com.google.android.systemui.columbus.actions;

import android.app.IActivityManager;
import android.content.Context;
import android.content.Intent;
import com.google.android.systemui.columbus.gates.SilenceAlertsDisabled;

/* compiled from: SnoozeAlarm.kt */
public final class SnoozeAlarm extends DeskClockAction {
    public final String tag = "Columbus/SnoozeAlarm";

    public final String getAlertAction() {
        return "com.google.android.deskclock.action.ALARM_ALERT";
    }

    public final String getDoneAction() {
        return "com.google.android.deskclock.action.ALARM_DONE";
    }

    public final Intent createDismissIntent() {
        return new Intent("android.intent.action.SNOOZE_ALARM");
    }

    public SnoozeAlarm(Context context, SilenceAlertsDisabled silenceAlertsDisabled, IActivityManager iActivityManager) {
        super(context, silenceAlertsDisabled, iActivityManager);
    }

    /* renamed from: getTag$vendor__unbundled_google__packages__SystemUIGoogle__android_common__sysuig */
    public final String mo19228xa00bbd41() {
        return this.tag;
    }
}
