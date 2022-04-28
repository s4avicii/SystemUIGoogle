package com.android.systemui.statusbar.notification.collection.listbuilder;

import com.airbnb.lottie.parser.moshi.JsonReader$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: ShadeListBuilderLogger.kt */
public final class ShadeListBuilderLogger$logFinalList$4 extends Lambda implements Function1<LogMessage, String> {
    public static final ShadeListBuilderLogger$logFinalList$4 INSTANCE = new ShadeListBuilderLogger$logFinalList$4();

    public ShadeListBuilderLogger$logFinalList$4() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = JsonReader$$ExternalSyntheticOutline0.m23m('[');
        m.append(logMessage.getInt1());
        m.append("] ");
        m.append(logMessage.getStr1());
        return m.toString();
    }
}
