package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationSectionsLogger.kt */
final class NotificationSectionsLogger$logOther$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationSectionsLogger$logOther$2 INSTANCE = new NotificationSectionsLogger$logOther$2();

    public NotificationSectionsLogger$logOther$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        return logMessage.getInt1() + ": other (" + logMessage.getStr1() + ')';
    }
}
