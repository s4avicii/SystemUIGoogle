package com.android.systemui.toast;

import com.airbnb.lottie.parser.moshi.JsonReader$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: ToastLogger.kt */
final class ToastLogger$logOnShowToast$2 extends Lambda implements Function1<LogMessage, String> {
    public static final ToastLogger$logOnShowToast$2 INSTANCE = new ToastLogger$logOnShowToast$2();

    public ToastLogger$logOnShowToast$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = JsonReader$$ExternalSyntheticOutline0.m23m('[');
        m.append(logMessage.getStr3());
        m.append("] Show toast for (");
        m.append(logMessage.getStr1());
        m.append(", ");
        m.append(logMessage.getInt1());
        m.append("). msg='");
        m.append(logMessage.getStr2());
        m.append('\'');
        return m.toString();
    }
}
