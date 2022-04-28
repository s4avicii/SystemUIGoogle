package com.android.systemui.statusbar.policy;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: HeadsUpManagerLogger.kt */
final class HeadsUpManagerLogger$logSetEntryPinned$2 extends Lambda implements Function1<LogMessage, String> {
    public static final HeadsUpManagerLogger$logSetEntryPinned$2 INSTANCE = new HeadsUpManagerLogger$logSetEntryPinned$2();

    public HeadsUpManagerLogger$logSetEntryPinned$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("set entry pinned ");
        m.append(logMessage.getStr1());
        m.append(" pinned: ");
        m.append(logMessage.getBool1());
        return m.toString();
    }
}
