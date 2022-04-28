package com.google.android.systemui.statusbar.notification.voicereplies;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationVoiceReplyLogger.kt */
final class NotificationVoiceReplyLogger$logStateInSession$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationVoiceReplyLogger$logStateInSession$2 INSTANCE = new NotificationVoiceReplyLogger$logStateInSession$2();

    public NotificationVoiceReplyLogger$logStateInSession$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("NEW STATE: InSession(notifKey=");
        m.append(((LogMessage) obj).getStr1());
        m.append(')');
        return m.toString();
    }
}
