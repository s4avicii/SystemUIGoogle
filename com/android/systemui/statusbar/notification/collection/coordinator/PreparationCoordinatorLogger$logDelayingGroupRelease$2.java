package com.android.systemui.statusbar.notification.collection.coordinator;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: PreparationCoordinatorLogger.kt */
public final class PreparationCoordinatorLogger$logDelayingGroupRelease$2 extends Lambda implements Function1<LogMessage, String> {
    public static final PreparationCoordinatorLogger$logDelayingGroupRelease$2 INSTANCE = new PreparationCoordinatorLogger$logDelayingGroupRelease$2();

    public PreparationCoordinatorLogger$logDelayingGroupRelease$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Delaying release of group ");
        m.append(logMessage.getStr1());
        m.append(" because child ");
        m.append(logMessage.getStr2());
        m.append(" is still inflating");
        return m.toString();
    }
}
