package com.google.android.systemui.statusbar.notification.voicereplies;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationVoiceReplyLogger.kt */
public final class NotificationVoiceReplyLogger$logSetFeatureEnabled$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationVoiceReplyLogger$logSetFeatureEnabled$2 INSTANCE = new NotificationVoiceReplyLogger$logSetFeatureEnabled$2();

    public NotificationVoiceReplyLogger$logSetFeatureEnabled$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("BINDER: setFeatureEnabled [userId=");
        m.append(logMessage.getInt1());
        m.append(", enabledSetting=");
        m.append(logMessage.getInt2());
        m.append(']');
        return m.toString();
    }
}
