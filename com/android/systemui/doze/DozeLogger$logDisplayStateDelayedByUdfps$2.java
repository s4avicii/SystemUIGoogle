package com.android.systemui.doze;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: DozeLogger.kt */
final class DozeLogger$logDisplayStateDelayedByUdfps$2 extends Lambda implements Function1<LogMessage, String> {
    public static final DozeLogger$logDisplayStateDelayedByUdfps$2 INSTANCE = new DozeLogger$logDisplayStateDelayedByUdfps$2();

    public DozeLogger$logDisplayStateDelayedByUdfps$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Delaying display state change to: ");
        m.append(((LogMessage) obj).getStr1());
        m.append(" due to UDFPS activity");
        return m.toString();
    }
}
