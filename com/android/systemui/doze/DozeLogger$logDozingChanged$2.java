package com.android.systemui.doze;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: DozeLogger.kt */
final class DozeLogger$logDozingChanged$2 extends Lambda implements Function1<LogMessage, String> {
    public static final DozeLogger$logDozingChanged$2 INSTANCE = new DozeLogger$logDozingChanged$2();

    public DozeLogger$logDozingChanged$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("Dozing changed dozing=", Boolean.valueOf(((LogMessage) obj).getBool1()));
    }
}
