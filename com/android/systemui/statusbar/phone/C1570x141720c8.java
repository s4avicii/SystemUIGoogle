package com.android.systemui.statusbar.phone;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logFullScreenIntentNotImportantEnough$2 */
/* compiled from: StatusBarNotificationActivityStarterLogger.kt */
final class C1570x141720c8 extends Lambda implements Function1<LogMessage, String> {
    public static final C1570x141720c8 INSTANCE = new C1570x141720c8();

    public C1570x141720c8() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("No Fullscreen intent: not important enough: ", ((LogMessage) obj).getStr1());
    }
}
