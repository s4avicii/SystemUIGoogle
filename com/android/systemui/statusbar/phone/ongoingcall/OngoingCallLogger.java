package com.android.systemui.statusbar.phone.ongoingcall;

import com.android.internal.logging.UiEventLogger;

/* compiled from: OngoingCallLogger.kt */
public final class OngoingCallLogger {
    public boolean chipIsVisible;
    public final UiEventLogger logger;

    /* compiled from: OngoingCallLogger.kt */
    public enum OngoingCallEvents implements UiEventLogger.UiEventEnum {
        ONGOING_CALL_VISIBLE(813),
        ONGOING_CALL_CLICKED(814);
        
        private final int metricId;

        /* access modifiers changed from: public */
        OngoingCallEvents(int i) {
            this.metricId = i;
        }

        public final int getId() {
            return this.metricId;
        }
    }

    public OngoingCallLogger(UiEventLogger uiEventLogger) {
        this.logger = uiEventLogger;
    }
}
