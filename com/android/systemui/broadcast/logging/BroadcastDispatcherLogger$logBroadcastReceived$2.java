package com.android.systemui.broadcast.logging;

import com.airbnb.lottie.parser.moshi.JsonReader$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: BroadcastDispatcherLogger.kt */
public final class BroadcastDispatcherLogger$logBroadcastReceived$2 extends Lambda implements Function1<LogMessage, String> {
    public static final BroadcastDispatcherLogger$logBroadcastReceived$2 INSTANCE = new BroadcastDispatcherLogger$logBroadcastReceived$2();

    public BroadcastDispatcherLogger$logBroadcastReceived$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = JsonReader$$ExternalSyntheticOutline0.m23m('[');
        m.append(logMessage.getInt1());
        m.append("] Broadcast received for user ");
        m.append(logMessage.getInt2());
        m.append(": ");
        m.append(logMessage.getStr1());
        return m.toString();
    }
}
