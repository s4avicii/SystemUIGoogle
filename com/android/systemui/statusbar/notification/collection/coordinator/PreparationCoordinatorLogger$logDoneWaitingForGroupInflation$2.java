package com.android.systemui.statusbar.notification.collection.coordinator;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: PreparationCoordinatorLogger.kt */
final class PreparationCoordinatorLogger$logDoneWaitingForGroupInflation$2 extends Lambda implements Function1<LogMessage, String> {
    public static final PreparationCoordinatorLogger$logDoneWaitingForGroupInflation$2 INSTANCE = new PreparationCoordinatorLogger$logDoneWaitingForGroupInflation$2();

    public PreparationCoordinatorLogger$logDoneWaitingForGroupInflation$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Finished inflating all members of group ");
        m.append(((LogMessage) obj).getStr1());
        m.append(", releasing group");
        return m.toString();
    }
}
