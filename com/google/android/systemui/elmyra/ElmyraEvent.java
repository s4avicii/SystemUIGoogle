package com.google.android.systemui.elmyra;

import com.android.internal.logging.UiEventLogger;

/* compiled from: ElmyraEvent.kt */
public enum ElmyraEvent implements UiEventLogger.UiEventEnum {
    ELMYRA_PRIMED(559),
    ELMYRA_RELEASED(560),
    ELMYRA_TRIGGERED_AP_SUSPENDED(561),
    ELMYRA_TRIGGERED_SCREEN_OFF(575),
    ELMYRA_TRIGGERED_SCREEN_ON(576);
    

    /* renamed from: id */
    private final int f144id;

    /* access modifiers changed from: public */
    ElmyraEvent(int i) {
        this.f144id = i;
    }

    public final int getId() {
        return this.f144id;
    }
}
