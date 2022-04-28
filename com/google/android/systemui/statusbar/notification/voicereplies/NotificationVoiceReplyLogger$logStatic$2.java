package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationVoiceReplyLogger.kt */
public final class NotificationVoiceReplyLogger$logStatic$2 extends Lambda implements Function1<LogMessage, String> {

    /* renamed from: $s */
    public final /* synthetic */ String f146$s;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public NotificationVoiceReplyLogger$logStatic$2(String str) {
        super(1);
        this.f146$s = str;
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        return this.f146$s;
    }
}
