package com.android.systemui.p006qs.logging;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.systemui.qs.logging.QSLogger$logAllTilesChangeListening$2 */
/* compiled from: QSLogger.kt */
public final class QSLogger$logAllTilesChangeListening$2 extends Lambda implements Function1<LogMessage, String> {
    public static final QSLogger$logAllTilesChangeListening$2 INSTANCE = new QSLogger$logAllTilesChangeListening$2();

    public QSLogger$logAllTilesChangeListening$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Tiles listening=");
        m.append(logMessage.getBool1());
        m.append(" in ");
        m.append(logMessage.getStr1());
        m.append(". ");
        m.append(logMessage.getStr2());
        return m.toString();
    }
}
