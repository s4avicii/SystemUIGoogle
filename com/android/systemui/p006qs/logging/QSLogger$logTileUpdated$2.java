package com.android.systemui.p006qs.logging;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.airbnb.lottie.parser.moshi.JsonReader$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.systemui.qs.logging.QSLogger$logTileUpdated$2 */
/* compiled from: QSLogger.kt */
public final class QSLogger$logTileUpdated$2 extends Lambda implements Function1<LogMessage, String> {
    public static final QSLogger$logTileUpdated$2 INSTANCE = new QSLogger$logTileUpdated$2();

    public QSLogger$logTileUpdated$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        String str;
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = JsonReader$$ExternalSyntheticOutline0.m23m('[');
        m.append(logMessage.getStr1());
        m.append("] Tile updated. Label=");
        m.append(logMessage.getStr2());
        m.append(". State=");
        m.append(logMessage.getInt1());
        m.append(". Icon=");
        m.append(logMessage.getStr3());
        m.append('.');
        if (logMessage.getBool1()) {
            StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m(" Activity in/out=");
            m2.append(logMessage.getBool2());
            m2.append('/');
            m2.append(logMessage.getBool3());
            str = m2.toString();
        } else {
            str = "";
        }
        m.append(str);
        return m.toString();
    }
}
