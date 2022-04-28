package com.android.systemui.statusbar.notification.collection.listbuilder;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: ShadeListBuilderLogger.kt */
public final class ShadeListBuilderLogger$logEntryAttachStateChanged$2 extends Lambda implements Function1<LogMessage, String> {
    public static final ShadeListBuilderLogger$logEntryAttachStateChanged$2 INSTANCE = new ShadeListBuilderLogger$logEntryAttachStateChanged$2();

    public ShadeListBuilderLogger$logEntryAttachStateChanged$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        String str;
        LogMessage logMessage = (LogMessage) obj;
        if (logMessage.getStr2() == null && logMessage.getStr3() != null) {
            str = "ATTACHED";
        } else if (logMessage.getStr2() != null && logMessage.getStr3() == null) {
            str = "DETACHED";
        } else if (logMessage.getStr2() == null && logMessage.getStr3() == null) {
            str = "MODIFIED (DETACHED)";
        } else {
            str = "MODIFIED (ATTACHED)";
        }
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("(Build ");
        m.append(logMessage.getLong1());
        m.append(") ");
        m.append(str);
        m.append(" {");
        m.append(logMessage.getStr1());
        m.append('}');
        return m.toString();
    }
}
