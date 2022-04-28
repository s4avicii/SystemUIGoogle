package com.android.systemui.statusbar.policy;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: HeadsUpManagerLogger.kt */
public final class HeadsUpManagerLogger$logUpdateNotification$2 extends Lambda implements Function1<LogMessage, String> {
    public static final HeadsUpManagerLogger$logUpdateNotification$2 INSTANCE = new HeadsUpManagerLogger$logUpdateNotification$2();

    public HeadsUpManagerLogger$logUpdateNotification$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("update notification ");
        m.append(logMessage.getStr1());
        m.append(" alert: ");
        m.append(logMessage.getBool1());
        m.append(" hasEntry: ");
        m.append(logMessage.getBool2());
        return m.toString();
    }
}
