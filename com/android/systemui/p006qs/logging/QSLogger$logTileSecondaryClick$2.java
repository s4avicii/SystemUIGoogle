package com.android.systemui.p006qs.logging;

import com.airbnb.lottie.parser.moshi.JsonReader$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.systemui.qs.logging.QSLogger$logTileSecondaryClick$2 */
/* compiled from: QSLogger.kt */
public final class QSLogger$logTileSecondaryClick$2 extends Lambda implements Function1<LogMessage, String> {
    public static final QSLogger$logTileSecondaryClick$2 INSTANCE = new QSLogger$logTileSecondaryClick$2();

    public QSLogger$logTileSecondaryClick$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = JsonReader$$ExternalSyntheticOutline0.m23m('[');
        m.append(logMessage.getStr1());
        m.append("] Tile long clicked. StatusBarState=");
        m.append(logMessage.getStr2());
        m.append(". TileState=");
        m.append(logMessage.getStr3());
        return m.toString();
    }
}
