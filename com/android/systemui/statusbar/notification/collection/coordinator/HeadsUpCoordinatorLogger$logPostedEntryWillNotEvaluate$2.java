package com.android.systemui.statusbar.notification.collection.coordinator;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: HeadsUpCoordinatorLogger.kt */
final class HeadsUpCoordinatorLogger$logPostedEntryWillNotEvaluate$2 extends Lambda implements Function1<LogMessage, String> {
    public static final HeadsUpCoordinatorLogger$logPostedEntryWillNotEvaluate$2 INSTANCE = new HeadsUpCoordinatorLogger$logPostedEntryWillNotEvaluate$2();

    public HeadsUpCoordinatorLogger$logPostedEntryWillNotEvaluate$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("will not evaluate posted entry ");
        m.append(logMessage.getStr1());
        m.append(": reason=");
        m.append(logMessage.getStr2());
        return m.toString();
    }
}
