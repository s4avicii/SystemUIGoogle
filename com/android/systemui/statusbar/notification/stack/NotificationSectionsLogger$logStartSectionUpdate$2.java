package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationSectionsLogger.kt */
final class NotificationSectionsLogger$logStartSectionUpdate$2 extends Lambda implements Function1<LogMessage, String> {
    public final /* synthetic */ String $reason;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public NotificationSectionsLogger$logStartSectionUpdate$2(String str) {
        super(1);
        this.$reason = str;
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        return Intrinsics.stringPlus("Updating section boundaries: ", this.$reason);
    }
}
