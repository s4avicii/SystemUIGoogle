package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationSectionsLogger.kt */
public final class NotificationSectionsLogger$logStr$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationSectionsLogger$logStr$2 INSTANCE = new NotificationSectionsLogger$logStr$2();

    public NotificationSectionsLogger$logStr$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return String.valueOf(((LogMessage) obj).getStr1());
    }
}
