package com.google.android.systemui.statusbar.notification.voicereplies;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationVoiceReplyLogger.kt */
public final class NotificationVoiceReplyLogger$logUnregisterCallbacks$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationVoiceReplyLogger$logUnregisterCallbacks$2 INSTANCE = new NotificationVoiceReplyLogger$logUnregisterCallbacks$2();

    public NotificationVoiceReplyLogger$logUnregisterCallbacks$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("BINDER: unRegisterCallbacks [userId=");
        m.append(((LogMessage) obj).getInt1());
        m.append(']');
        return m.toString();
    }
}
