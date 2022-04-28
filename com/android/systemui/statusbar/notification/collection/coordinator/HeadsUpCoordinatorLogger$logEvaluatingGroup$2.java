package com.android.systemui.statusbar.notification.collection.coordinator;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: HeadsUpCoordinatorLogger.kt */
final class HeadsUpCoordinatorLogger$logEvaluatingGroup$2 extends Lambda implements Function1<LogMessage, String> {
    public static final HeadsUpCoordinatorLogger$logEvaluatingGroup$2 INSTANCE = new HeadsUpCoordinatorLogger$logEvaluatingGroup$2();

    public HeadsUpCoordinatorLogger$logEvaluatingGroup$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("evaluating group for alert transfer: ");
        m.append(logMessage.getStr1());
        m.append(" numPostedEntries=");
        m.append(logMessage.getInt1());
        m.append(" logicalGroupSize=");
        m.append(logMessage.getInt2());
        return m.toString();
    }
}
