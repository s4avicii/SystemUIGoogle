package com.android.systemui.statusbar;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: ActionClickLogger.kt */
public final class ActionClickLogger$logWaitingToCloseKeyguard$2 extends Lambda implements Function1<LogMessage, String> {
    public static final ActionClickLogger$logWaitingToCloseKeyguard$2 INSTANCE = new ActionClickLogger$logWaitingToCloseKeyguard$2();

    public ActionClickLogger$logWaitingToCloseKeyguard$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("  [Action click] Intent ");
        m.append(((LogMessage) obj).getStr1());
        m.append(" launches an activity, dismissing keyguard first...");
        return m.toString();
    }
}
