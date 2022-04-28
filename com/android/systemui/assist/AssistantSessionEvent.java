package com.android.systemui.assist;

import com.android.internal.logging.UiEventLogger;

/* compiled from: AssistantSessionEvent.kt */
public enum AssistantSessionEvent implements UiEventLogger.UiEventEnum {
    ASSISTANT_SESSION_TIMEOUT_DISMISS(524),
    ASSISTANT_SESSION_INVOCATION_CANCELLED(526),
    ASSISTANT_SESSION_USER_DISMISS(527),
    ASSISTANT_SESSION_UPDATE(528),
    ASSISTANT_SESSION_CLOSE(529);
    

    /* renamed from: id */
    private final int f38id;

    /* access modifiers changed from: public */
    AssistantSessionEvent(int i) {
        this.f38id = i;
    }

    public final int getId() {
        return this.f38id;
    }
}
