package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationVoiceReplyLogger.kt */
public final class NotificationVoiceReplyLogger$logHotwordAvailabilityChanged$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationVoiceReplyLogger$logHotwordAvailabilityChanged$2 INSTANCE = new NotificationVoiceReplyLogger$logHotwordAvailabilityChanged$2();

    public NotificationVoiceReplyLogger$logHotwordAvailabilityChanged$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        return logMessage.getStr1() + " hotword for userId=" + logMessage.getInt1();
    }
}
