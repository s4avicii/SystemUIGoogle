package com.google.android.systemui.statusbar.notification.voicereplies;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationVoiceReplyLogger.kt */
public final class NotificationVoiceReplyLogger$logRegisterCallbacks$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationVoiceReplyLogger$logRegisterCallbacks$2 INSTANCE = new NotificationVoiceReplyLogger$logRegisterCallbacks$2();

    public NotificationVoiceReplyLogger$logRegisterCallbacks$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("BINDER: registerCallbacks [userId=");
        m.append(((LogMessage) obj).getInt1());
        m.append(']');
        return m.toString();
    }
}
