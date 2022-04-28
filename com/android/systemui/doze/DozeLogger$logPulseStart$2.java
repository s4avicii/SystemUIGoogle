package com.android.systemui.doze;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: DozeLogger.kt */
final class DozeLogger$logPulseStart$2 extends Lambda implements Function1<LogMessage, String> {
    public static final DozeLogger$logPulseStart$2 INSTANCE = new DozeLogger$logPulseStart$2();

    public DozeLogger$logPulseStart$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("Pulse start, reason=", DozeLog.reasonToString(((LogMessage) obj).getInt1()));
    }
}
