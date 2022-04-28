package com.android.systemui.statusbar.notification.collection.listbuilder;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: ShadeListBuilderLogger.kt */
public final class ShadeListBuilderLogger$logSectionChangeSuppressed$2 extends Lambda implements Function1<LogMessage, String> {
    public static final ShadeListBuilderLogger$logSectionChangeSuppressed$2 INSTANCE = new ShadeListBuilderLogger$logSectionChangeSuppressed$2();

    public ShadeListBuilderLogger$logSectionChangeSuppressed$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("(Build ");
        m.append(logMessage.getLong1());
        m.append(")     Suppressing section change to ");
        m.append(logMessage.getStr1());
        m.append(" (staying at ");
        m.append(logMessage.getStr2());
        m.append(')');
        return m.toString();
    }
}
