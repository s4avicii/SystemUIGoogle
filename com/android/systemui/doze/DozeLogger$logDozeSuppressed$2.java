package com.android.systemui.doze;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: DozeLogger.kt */
final class DozeLogger$logDozeSuppressed$2 extends Lambda implements Function1<LogMessage, String> {
    public static final DozeLogger$logDozeSuppressed$2 INSTANCE = new DozeLogger$logDozeSuppressed$2();

    public DozeLogger$logDozeSuppressed$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("Doze state suppressed, state=", ((LogMessage) obj).getStr1());
    }
}
