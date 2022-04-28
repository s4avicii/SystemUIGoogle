package com.android.systemui.statusbar.phone;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logNonClickableNotification$2 */
/* compiled from: StatusBarNotificationActivityStarterLogger.kt */
final class C1574x823c33d2 extends Lambda implements Function1<LogMessage, String> {
    public static final C1574x823c33d2 INSTANCE = new C1574x823c33d2();

    public C1574x823c33d2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("onNotificationClicked called for non-clickable notification! ", ((LogMessage) obj).getStr1());
    }
}
