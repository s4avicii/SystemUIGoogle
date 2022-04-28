package com.google.android.systemui.statusbar.notification.voicereplies;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationVoiceReplyLogger.kt */
public final class NotificationVoiceReplyLogger$logMsgSent$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationVoiceReplyLogger$logMsgSent$2 INSTANCE = new NotificationVoiceReplyLogger$logMsgSent$2();

    public NotificationVoiceReplyLogger$logMsgSent$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("sent voice reply [notifKey=");
        m.append(logMessage.getStr1());
        m.append(", type=");
        m.append(logMessage.getStr2());
        m.append(']');
        return m.toString();
    }
}
