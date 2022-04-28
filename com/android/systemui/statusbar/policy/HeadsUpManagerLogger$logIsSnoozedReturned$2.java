package com.android.systemui.statusbar.policy;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: HeadsUpManagerLogger.kt */
final class HeadsUpManagerLogger$logIsSnoozedReturned$2 extends Lambda implements Function1<LogMessage, String> {
    public static final HeadsUpManagerLogger$logIsSnoozedReturned$2 INSTANCE = new HeadsUpManagerLogger$logIsSnoozedReturned$2();

    public HeadsUpManagerLogger$logIsSnoozedReturned$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("package snoozed when queried ", ((LogMessage) obj).getStr1());
    }
}
