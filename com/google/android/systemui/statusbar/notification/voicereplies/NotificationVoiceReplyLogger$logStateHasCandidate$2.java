package com.google.android.systemui.statusbar.notification.voicereplies;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationVoiceReplyLogger.kt */
public final class NotificationVoiceReplyLogger$logStateHasCandidate$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationVoiceReplyLogger$logStateHasCandidate$2 INSTANCE = new NotificationVoiceReplyLogger$logStateHasCandidate$2();

    public NotificationVoiceReplyLogger$logStateHasCandidate$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("NEW STATE: HasCandidate(notifKey=");
        m.append(logMessage.getStr1());
        m.append(", ctaVis=");
        m.append(logMessage.getStr2());
        m.append(", cta=");
        m.append(logMessage.getStr3());
        m.append(')');
        return m.toString();
    }
}
