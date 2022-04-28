package com.android.systemui.privacy.logging;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: PrivacyLogger.kt */
public final class PrivacyLogger$logCurrentProfilesChanged$2 extends Lambda implements Function1<LogMessage, String> {
    public static final PrivacyLogger$logCurrentProfilesChanged$2 INSTANCE = new PrivacyLogger$logCurrentProfilesChanged$2();

    public PrivacyLogger$logCurrentProfilesChanged$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("Profiles changed: ", ((LogMessage) obj).getStr1());
    }
}
