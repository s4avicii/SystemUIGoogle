package com.google.android.systemui.statusbar.notification.voicereplies;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationVoiceReplyLogger.kt */
public final class NotificationVoiceReplyLogger$logStartVoiceReply$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationVoiceReplyLogger$logStartVoiceReply$2 INSTANCE = new NotificationVoiceReplyLogger$logStartVoiceReply$2();

    public NotificationVoiceReplyLogger$logStartVoiceReply$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("BINDER: startVoiceReply [userId=");
        m.append(logMessage.getInt1());
        m.append(", sessionToken=");
        m.append(logMessage.getInt2());
        m.append(", hasContent=");
        m.append(logMessage.getBool1());
        m.append(']');
        return m.toString();
    }
}
