package com.android.systemui.statusbar.phone;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logHandleClickAfterKeyguardDismissed$2 */
/* compiled from: StatusBarNotificationActivityStarterLogger.kt */
final class C1572x5700bdb1 extends Lambda implements Function1<LogMessage, String> {
    public static final C1572x5700bdb1 INSTANCE = new C1572x5700bdb1();

    public C1572x5700bdb1() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("(2/4) handleNotificationClickAfterKeyguardDismissed: ", ((LogMessage) obj).getStr1());
    }
}
