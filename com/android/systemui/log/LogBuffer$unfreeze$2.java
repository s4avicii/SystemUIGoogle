package com.android.systemui.log;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: LogBuffer.kt */
public final class LogBuffer$unfreeze$2 extends Lambda implements Function1<LogMessage, String> {
    public static final LogBuffer$unfreeze$2 INSTANCE = new LogBuffer$unfreeze$2();

    public LogBuffer$unfreeze$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus(((LogMessage) obj).getStr1(), " unfrozen");
    }
}
