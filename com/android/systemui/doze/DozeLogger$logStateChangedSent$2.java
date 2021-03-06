package com.android.systemui.doze;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: DozeLogger.kt */
final class DozeLogger$logStateChangedSent$2 extends Lambda implements Function1<LogMessage, String> {
    public static final DozeLogger$logStateChangedSent$2 INSTANCE = new DozeLogger$logStateChangedSent$2();

    public DozeLogger$logStateChangedSent$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("Doze state sent to all DozeMachineParts stateSent=", ((LogMessage) obj).getStr1());
    }
}
