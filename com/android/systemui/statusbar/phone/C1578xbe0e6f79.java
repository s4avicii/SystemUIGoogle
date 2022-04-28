package com.android.systemui.statusbar.phone;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logStartingActivityFromClick$2 */
/* compiled from: StatusBarNotificationActivityStarterLogger.kt */
final class C1578xbe0e6f79 extends Lambda implements Function1<LogMessage, String> {
    public static final C1578xbe0e6f79 INSTANCE = new C1578xbe0e6f79();

    public C1578xbe0e6f79() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("(1/4) onNotificationClicked: ", ((LogMessage) obj).getStr1());
    }
}
