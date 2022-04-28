package com.android.systemui.statusbar.notification.collection.listbuilder;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: ShadeListBuilderLogger.kt */
public final class ShadeListBuilderLogger$logDuplicateSummary$2 extends Lambda implements Function1<LogMessage, String> {
    public static final ShadeListBuilderLogger$logDuplicateSummary$2 INSTANCE = new ShadeListBuilderLogger$logDuplicateSummary$2();

    public ShadeListBuilderLogger$logDuplicateSummary$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("(Build ");
        m.append(logMessage.getLong1());
        m.append(") Duplicate summary for group \"");
        m.append(logMessage.getStr1());
        m.append("\": \"");
        m.append(logMessage.getStr2());
        m.append("\" vs. \"");
        m.append(logMessage.getStr3());
        m.append('\"');
        return m.toString();
    }
}
