package com.android.systemui.statusbar.notification;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationEntryManagerLogger.kt */
final class NotificationEntryManagerLogger$logNotifUpdated$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationEntryManagerLogger$logNotifUpdated$2 INSTANCE = new NotificationEntryManagerLogger$logNotifUpdated$2();

    public NotificationEntryManagerLogger$logNotifUpdated$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("NOTIF UPDATED ", ((LogMessage) obj).getStr1());
    }
}
