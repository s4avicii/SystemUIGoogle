package com.google.android.systemui.statusbar.notification.voicereplies;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationVoiceReplyLogger.kt */
public final class NotificationVoiceReplyLogger$logFocus$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationVoiceReplyLogger$logFocus$2 INSTANCE = new NotificationVoiceReplyLogger$logFocus$2();

    public NotificationVoiceReplyLogger$logFocus$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Focusing notification [key=");
        m.append(logMessage.getStr1());
        m.append(", hasContent=");
        m.append(logMessage.getBool1());
        m.append(']');
        return m.toString();
    }
}
