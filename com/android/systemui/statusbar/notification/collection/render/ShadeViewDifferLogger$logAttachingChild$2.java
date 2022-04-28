package com.android.systemui.statusbar.notification.collection.render;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: ShadeViewDifferLogger.kt */
final class ShadeViewDifferLogger$logAttachingChild$2 extends Lambda implements Function1<LogMessage, String> {
    public static final ShadeViewDifferLogger$logAttachingChild$2 INSTANCE = new ShadeViewDifferLogger$logAttachingChild$2();

    public ShadeViewDifferLogger$logAttachingChild$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Attaching view ");
        m.append(logMessage.getStr1());
        m.append(" to ");
        m.append(logMessage.getStr2());
        return m.toString();
    }
}
