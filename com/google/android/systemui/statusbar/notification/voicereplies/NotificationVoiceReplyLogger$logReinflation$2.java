package com.google.android.systemui.statusbar.notification.voicereplies;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationVoiceReplyLogger.kt */
final class NotificationVoiceReplyLogger$logReinflation$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationVoiceReplyLogger$logReinflation$2 INSTANCE = new NotificationVoiceReplyLogger$logReinflation$2();

    public NotificationVoiceReplyLogger$logReinflation$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("STATE UPDATE: Reinflation - key=");
        m.append(logMessage.getStr1());
        m.append(", reason=");
        m.append(logMessage.getStr2());
        return m.toString();
    }
}
