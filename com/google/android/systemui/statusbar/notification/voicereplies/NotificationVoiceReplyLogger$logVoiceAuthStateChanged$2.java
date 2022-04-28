package com.google.android.systemui.statusbar.notification.voicereplies;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationVoiceReplyLogger.kt */
public final class NotificationVoiceReplyLogger$logVoiceAuthStateChanged$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationVoiceReplyLogger$logVoiceAuthStateChanged$2 INSTANCE = new NotificationVoiceReplyLogger$logVoiceAuthStateChanged$2();

    public NotificationVoiceReplyLogger$logVoiceAuthStateChanged$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("BINDER: onVoiceAuthStateChanged [userId=");
        m.append(logMessage.getInt1());
        m.append(", sessionToken=");
        m.append(logMessage.getInt2());
        m.append(", newState=");
        m.append(logMessage.getBool1());
        m.append(']');
        return m.toString();
    }
}
