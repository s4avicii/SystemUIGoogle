package com.android.systemui.statusbar.policy;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: HeadsUpManagerLogger.kt */
final class HeadsUpManagerLogger$logPackageUnsnoozed$2 extends Lambda implements Function1<LogMessage, String> {
    public static final HeadsUpManagerLogger$logPackageUnsnoozed$2 INSTANCE = new HeadsUpManagerLogger$logPackageUnsnoozed$2();

    public HeadsUpManagerLogger$logPackageUnsnoozed$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("package unsnoozed ", ((LogMessage) obj).getStr1());
    }
}
