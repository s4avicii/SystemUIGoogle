package com.android.systemui.statusbar.phone;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.systemui.statusbar.phone.LSShadeTransitionLogger$logPulseHeightNotResetWhenFullyCollapsed$2 */
/* compiled from: LSShadeTransitionLogger.kt */
public final class C1456x831f3f06 extends Lambda implements Function1<LogMessage, String> {
    public static final C1456x831f3f06 INSTANCE = new C1456x831f3f06();

    public C1456x831f3f06() {
        super(1);
    }

    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        return "Pulse height stuck and reset after shade was fully collapsed";
    }
}
