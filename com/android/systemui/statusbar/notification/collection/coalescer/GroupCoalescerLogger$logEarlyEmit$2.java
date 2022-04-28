package com.android.systemui.statusbar.notification.collection.coalescer;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: GroupCoalescerLogger.kt */
final class GroupCoalescerLogger$logEarlyEmit$2 extends Lambda implements Function1<LogMessage, String> {
    public static final GroupCoalescerLogger$logEarlyEmit$2 INSTANCE = new GroupCoalescerLogger$logEarlyEmit$2();

    public GroupCoalescerLogger$logEarlyEmit$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Modification of notif ");
        m.append(logMessage.getStr1());
        m.append(" triggered early emit of batched group ");
        m.append(logMessage.getStr2());
        return m.toString();
    }
}
