package com.android.systemui.statusbar.policy;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: HeadsUpManagerLogger.kt */
public final class HeadsUpManagerLogger$logShowNotification$2 extends Lambda implements Function1<LogMessage, String> {
    public static final HeadsUpManagerLogger$logShowNotification$2 INSTANCE = new HeadsUpManagerLogger$logShowNotification$2();

    public HeadsUpManagerLogger$logShowNotification$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("show notification ", ((LogMessage) obj).getStr1());
    }
}
