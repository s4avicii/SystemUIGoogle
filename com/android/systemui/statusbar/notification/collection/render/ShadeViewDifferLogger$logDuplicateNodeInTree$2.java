package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: ShadeViewDifferLogger.kt */
final class ShadeViewDifferLogger$logDuplicateNodeInTree$2 extends Lambda implements Function1<LogMessage, String> {
    public static final ShadeViewDifferLogger$logDuplicateNodeInTree$2 INSTANCE = new ShadeViewDifferLogger$logDuplicateNodeInTree$2();

    public ShadeViewDifferLogger$logDuplicateNodeInTree$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        return logMessage.getStr1() + " when mapping tree: " + logMessage.getStr2();
    }
}
