package com.android.systemui.p006qs.logging;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.systemui.qs.logging.QSLogger$logPanelExpanded$2 */
/* compiled from: QSLogger.kt */
public final class QSLogger$logPanelExpanded$2 extends Lambda implements Function1<LogMessage, String> {
    public static final QSLogger$logPanelExpanded$2 INSTANCE = new QSLogger$logPanelExpanded$2();

    public QSLogger$logPanelExpanded$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        return logMessage.getStr1() + " expanded=" + logMessage.getBool1();
    }
}
