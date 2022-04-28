package com.android.systemui.p006qs.logging;

import com.airbnb.lottie.parser.moshi.JsonReader$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.systemui.qs.logging.QSLogger$logTileAdded$2 */
/* compiled from: QSLogger.kt */
public final class QSLogger$logTileAdded$2 extends Lambda implements Function1<LogMessage, String> {
    public static final QSLogger$logTileAdded$2 INSTANCE = new QSLogger$logTileAdded$2();

    public QSLogger$logTileAdded$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        StringBuilder m = JsonReader$$ExternalSyntheticOutline0.m23m('[');
        m.append(((LogMessage) obj).getStr1());
        m.append("] Tile added");
        return m.toString();
    }
}
