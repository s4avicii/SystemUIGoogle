package com.android.systemui.doze;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: DozeLogger.kt */
final class DozeLogger$logPulseDropped$4 extends Lambda implements Function1<LogMessage, String> {
    public static final DozeLogger$logPulseDropped$4 INSTANCE = new DozeLogger$logPulseDropped$4();

    public DozeLogger$logPulseDropped$4() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("Pulse dropped, why=", ((LogMessage) obj).getStr1());
    }
}
