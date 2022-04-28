package com.google.android.systemui.elmyra.actions;

import android.content.Context;
import android.content.Intent;

public final class SnoozeAlarm extends DeskClockAction {
    public final String getAlertAction() {
        return "com.google.android.deskclock.action.ALARM_ALERT";
    }

    public final String getDoneAction() {
        return "com.google.android.deskclock.action.ALARM_DONE";
    }

    public final Intent createDismissIntent() {
        return new Intent("android.intent.action.SNOOZE_ALARM");
    }

    public SnoozeAlarm(Context context) {
        super(context);
    }
}
