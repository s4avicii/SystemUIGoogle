package com.android.systemui.privacy.logging;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: PrivacyLogger.kt */
public final class PrivacyLogger$logStatusBarIconsVisible$2 extends Lambda implements Function1<LogMessage, String> {
    public static final PrivacyLogger$logStatusBarIconsVisible$2 INSTANCE = new PrivacyLogger$logStatusBarIconsVisible$2();

    public PrivacyLogger$logStatusBarIconsVisible$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Status bar icons visible: camera=");
        m.append(logMessage.getBool1());
        m.append(", microphone=");
        m.append(logMessage.getBool2());
        m.append(", location=");
        m.append(logMessage.getBool3());
        return m.toString();
    }
}
