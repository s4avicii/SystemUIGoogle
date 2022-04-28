package com.android.systemui.statusbar.notification;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationEntryManagerLogger.kt */
public final class NotificationEntryManagerLogger$logUseWhileNewPipelineActive$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationEntryManagerLogger$logUseWhileNewPipelineActive$2 INSTANCE = new NotificationEntryManagerLogger$logUseWhileNewPipelineActive$2();

    public NotificationEntryManagerLogger$logUseWhileNewPipelineActive$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("While running New Pipeline: ");
        m.append(logMessage.getStr1());
        m.append("(reason=");
        m.append(logMessage.getStr2());
        m.append(')');
        return m.toString();
    }
}
