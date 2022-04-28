package com.android.systemui.statusbar.phone;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logHandleClickAfterPanelCollapsed$2 */
/* compiled from: StatusBarNotificationActivityStarterLogger.kt */
final class C1573xe49d9e41 extends Lambda implements Function1<LogMessage, String> {
    public static final C1573xe49d9e41 INSTANCE = new C1573xe49d9e41();

    public C1573xe49d9e41() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("(3/4) handleNotificationClickAfterPanelCollapsed: ", ((LogMessage) obj).getStr1());
    }
}
