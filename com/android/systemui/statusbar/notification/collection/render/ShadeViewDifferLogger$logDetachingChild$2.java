package com.android.systemui.statusbar.notification.collection.render;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: ShadeViewDifferLogger.kt */
final class ShadeViewDifferLogger$logDetachingChild$2 extends Lambda implements Function1<LogMessage, String> {
    public static final ShadeViewDifferLogger$logDetachingChild$2 INSTANCE = new ShadeViewDifferLogger$logDetachingChild$2();

    public ShadeViewDifferLogger$logDetachingChild$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Detach ");
        m.append(logMessage.getStr1());
        m.append(" isTransfer=");
        m.append(logMessage.getBool1());
        m.append(" isParentRemoved=");
        m.append(logMessage.getBool2());
        m.append(" oldParent=");
        m.append(logMessage.getStr2());
        m.append(" newParent=");
        m.append(logMessage.getStr3());
        return m.toString();
    }
}
