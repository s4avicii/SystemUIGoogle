package com.android.systemui.statusbar.phone;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logFullScreenIntentSuppressedByDnD$2 */
/* compiled from: StatusBarNotificationActivityStarterLogger.kt */
final class C1571x7a36b302 extends Lambda implements Function1<LogMessage, String> {
    public static final C1571x7a36b302 INSTANCE = new C1571x7a36b302();

    public C1571x7a36b302() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("No Fullscreen intent: suppressed by DND: ", ((LogMessage) obj).getStr1());
    }
}
