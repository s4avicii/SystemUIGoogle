package com.android.systemui.statusbar.policy;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: HeadsUpManagerLogger.kt */
public final class HeadsUpManagerLogger$logRemoveNotification$2 extends Lambda implements Function1<LogMessage, String> {
    public static final HeadsUpManagerLogger$logRemoveNotification$2 INSTANCE = new HeadsUpManagerLogger$logRemoveNotification$2();

    public HeadsUpManagerLogger$logRemoveNotification$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("remove notification ");
        m.append(logMessage.getStr1());
        m.append(" releaseImmediately: ");
        m.append(logMessage.getBool1());
        return m.toString();
    }
}
