package com.android.systemui.privacy.logging;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: PrivacyLogger.kt */
public final class PrivacyLogger$logUpdatedItemFromAppOps$2 extends Lambda implements Function1<LogMessage, String> {
    public static final PrivacyLogger$logUpdatedItemFromAppOps$2 INSTANCE = new PrivacyLogger$logUpdatedItemFromAppOps$2();

    public PrivacyLogger$logUpdatedItemFromAppOps$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("App Op: ");
        m.append(logMessage.getInt1());
        m.append(" for ");
        m.append(logMessage.getStr1());
        m.append('(');
        m.append(logMessage.getInt2());
        m.append("), active=");
        m.append(logMessage.getBool1());
        return m.toString();
    }
}
