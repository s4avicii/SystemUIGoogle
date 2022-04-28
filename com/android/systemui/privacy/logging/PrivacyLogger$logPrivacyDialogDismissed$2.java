package com.android.systemui.privacy.logging;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: PrivacyLogger.kt */
public final class PrivacyLogger$logPrivacyDialogDismissed$2 extends Lambda implements Function1<LogMessage, String> {
    public static final PrivacyLogger$logPrivacyDialogDismissed$2 INSTANCE = new PrivacyLogger$logPrivacyDialogDismissed$2();

    public PrivacyLogger$logPrivacyDialogDismissed$2() {
        super(1);
    }

    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        return "Privacy dialog dismissed";
    }
}
