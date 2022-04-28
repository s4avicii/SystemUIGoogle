package com.android.systemui.statusbar.phone;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logSendingIntentFailed$2 */
/* compiled from: StatusBarNotificationActivityStarterLogger.kt */
final class C1576x40a889d extends Lambda implements Function1<LogMessage, String> {
    public static final C1576x40a889d INSTANCE = new C1576x40a889d();

    public C1576x40a889d() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("Sending contentIntentFailed: ", ((LogMessage) obj).getStr1());
    }
}
