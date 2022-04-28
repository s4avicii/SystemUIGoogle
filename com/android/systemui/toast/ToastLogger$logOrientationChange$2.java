package com.android.systemui.toast;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: ToastLogger.kt */
final class ToastLogger$logOrientationChange$2 extends Lambda implements Function1<LogMessage, String> {
    public static final ToastLogger$logOrientationChange$2 INSTANCE = new ToastLogger$logOrientationChange$2();

    public ToastLogger$logOrientationChange$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Orientation change for toast. msg='");
        m.append(logMessage.getStr1());
        m.append("' isPortrait=");
        m.append(logMessage.getBool1());
        return m.toString();
    }
}
