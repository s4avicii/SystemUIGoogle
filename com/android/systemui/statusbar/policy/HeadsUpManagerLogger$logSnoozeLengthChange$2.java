package com.android.systemui.statusbar.policy;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: HeadsUpManagerLogger.kt */
final class HeadsUpManagerLogger$logSnoozeLengthChange$2 extends Lambda implements Function1<LogMessage, String> {
    public static final HeadsUpManagerLogger$logSnoozeLengthChange$2 INSTANCE = new HeadsUpManagerLogger$logSnoozeLengthChange$2();

    public HeadsUpManagerLogger$logSnoozeLengthChange$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("snooze length changed: ");
        m.append(((LogMessage) obj).getInt1());
        m.append("ms");
        return m.toString();
    }
}
