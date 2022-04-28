package com.google.android.systemui.assist.uihints.edgelights.mode;

import com.android.internal.logging.UiEventLogger;

/* compiled from: AssistantModeChangeEvent.kt */
public enum AssistantModeChangeEvent implements UiEventLogger.UiEventEnum {
    ASSISTANT_SESSION_MODE_GONE(585),
    ASSISTANT_SESSION_MODE_FULL_LISTENING(587),
    ASSISTANT_SESSION_MODE_FULFILL_BOTTOM(588),
    ASSISTANT_SESSION_MODE_FULFILL_PERIMETER(589),
    ASSISTANT_SESSION_MODE_UNKNOWN(590);
    

    /* renamed from: id */
    private final int f138id;

    /* access modifiers changed from: public */
    AssistantModeChangeEvent(int i) {
        this.f138id = i;
    }

    public final int getId() {
        return this.f138id;
    }
}
