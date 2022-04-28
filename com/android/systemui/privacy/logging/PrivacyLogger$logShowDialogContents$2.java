package com.android.systemui.privacy.logging;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: PrivacyLogger.kt */
final class PrivacyLogger$logShowDialogContents$2 extends Lambda implements Function1<LogMessage, String> {
    public static final PrivacyLogger$logShowDialogContents$2 INSTANCE = new PrivacyLogger$logShowDialogContents$2();

    public PrivacyLogger$logShowDialogContents$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("Privacy dialog shown. Contents: ", ((LogMessage) obj).getStr1());
    }
}
