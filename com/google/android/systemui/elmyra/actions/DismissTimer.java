package com.google.android.systemui.elmyra.actions;

import android.content.Context;
import android.content.Intent;

public final class DismissTimer extends DeskClockAction {
    public final String getAlertAction() {
        return "com.google.android.deskclock.action.TIMER_ALERT";
    }

    public final String getDoneAction() {
        return "com.google.android.deskclock.action.TIMER_DONE";
    }

    public final Intent createDismissIntent() {
        return new Intent("android.intent.action.DISMISS_TIMER");
    }

    public DismissTimer(Context context) {
        super(context);
    }
}
