package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationVoiceReplyLogger.kt */
final class NotificationVoiceReplyLogger$logStateNoCandidate$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationVoiceReplyLogger$logStateNoCandidate$2 INSTANCE = new NotificationVoiceReplyLogger$logStateNoCandidate$2();

    public NotificationVoiceReplyLogger$logStateNoCandidate$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("NEW STATE: ", ((LogMessage) obj).getStr1());
    }
}
