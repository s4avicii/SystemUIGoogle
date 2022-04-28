package com.android.systemui.statusbar.notification.collection.coordinator;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: GutsCoordinatorLogger.kt */
final class GutsCoordinatorLogger$logGutsOpened$2 extends Lambda implements Function1<LogMessage, String> {
    public static final GutsCoordinatorLogger$logGutsOpened$2 INSTANCE = new GutsCoordinatorLogger$logGutsOpened$2();

    public GutsCoordinatorLogger$logGutsOpened$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Guts of type ");
        m.append(logMessage.getStr2());
        m.append(" (leave behind: ");
        m.append(logMessage.getBool1());
        m.append(") opened for class ");
        m.append(logMessage.getStr1());
        return m.toString();
    }
}
