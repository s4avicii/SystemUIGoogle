package com.google.android.systemui.statusbar.notification.voicereplies;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationVoiceReplyLogger.kt */
final class NotificationVoiceReplyLogger$logUserIdMismatch$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationVoiceReplyLogger$logUserIdMismatch$2 INSTANCE = new NotificationVoiceReplyLogger$logUserIdMismatch$2();

    public NotificationVoiceReplyLogger$logUserIdMismatch$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("UserId mismatch, notifying handler of failure [handerId=");
        m.append(logMessage.getInt1());
        m.append(", candidateId=");
        m.append(logMessage.getInt2());
        m.append(']');
        return m.toString();
    }
}
