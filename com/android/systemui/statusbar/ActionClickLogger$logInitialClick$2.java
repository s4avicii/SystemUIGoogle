package com.android.systemui.statusbar;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: ActionClickLogger.kt */
final class ActionClickLogger$logInitialClick$2 extends Lambda implements Function1<LogMessage, String> {
    public static final ActionClickLogger$logInitialClick$2 INSTANCE = new ActionClickLogger$logInitialClick$2();

    public ActionClickLogger$logInitialClick$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("ACTION CLICK ");
        m.append(logMessage.getStr1());
        m.append(" (channel=");
        m.append(logMessage.getStr2());
        m.append(") for pending intent ");
        m.append(logMessage.getStr3());
        return m.toString();
    }
}
