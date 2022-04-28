package com.android.systemui.statusbar.phone;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.systemui.statusbar.phone.LSShadeTransitionLogger$logDragDownAmountResetWhenFullyCollapsed$2 */
/* compiled from: LSShadeTransitionLogger.kt */
public final class C1455xc8baf00b extends Lambda implements Function1<LogMessage, String> {
    public static final C1455xc8baf00b INSTANCE = new C1455xc8baf00b();

    public C1455xc8baf00b() {
        super(1);
    }

    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        return "Drag down amount stuck and reset after shade was fully collapsed";
    }
}
