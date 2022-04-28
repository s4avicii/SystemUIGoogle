package com.google.android.systemui.statusbar.notification.voicereplies;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationVoiceReplyLogger.kt */
public final class NotificationVoiceReplyLogger$logRejectCandidate$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationVoiceReplyLogger$logRejectCandidate$2 INSTANCE = new NotificationVoiceReplyLogger$logRejectCandidate$2();

    public NotificationVoiceReplyLogger$logRejectCandidate$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("rejecting candidate [");
        m.append(logMessage.getStr1());
        m.append("]: ");
        m.append(logMessage.getStr2());
        return m.toString();
    }
}
