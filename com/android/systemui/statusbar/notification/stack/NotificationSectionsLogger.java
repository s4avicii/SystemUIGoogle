package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessageImpl;
import java.util.Objects;

/* compiled from: NotificationSectionsLogger.kt */
public final class NotificationSectionsLogger {
    public final LogBuffer logBuffer;

    public final void logPosition(int i, String str, boolean z) {
        String str2 = z ? " (HUN)" : "";
        LogBuffer logBuffer2 = this.logBuffer;
        LogLevel logLevel = LogLevel.DEBUG;
        NotificationSectionsLogger$logPosition$2 notificationSectionsLogger$logPosition$2 = NotificationSectionsLogger$logPosition$2.INSTANCE;
        Objects.requireNonNull(logBuffer2);
        if (!logBuffer2.frozen) {
            LogMessageImpl obtain = logBuffer2.obtain("NotifSections", logLevel, notificationSectionsLogger$logPosition$2);
            obtain.int1 = i;
            obtain.str1 = str;
            obtain.str2 = str2;
            logBuffer2.push(obtain);
        }
    }

    public final void logStr(String str) {
        LogBuffer logBuffer2 = this.logBuffer;
        LogLevel logLevel = LogLevel.DEBUG;
        NotificationSectionsLogger$logStr$2 notificationSectionsLogger$logStr$2 = NotificationSectionsLogger$logStr$2.INSTANCE;
        Objects.requireNonNull(logBuffer2);
        if (!logBuffer2.frozen) {
            LogMessageImpl obtain = logBuffer2.obtain("NotifSections", logLevel, notificationSectionsLogger$logStr$2);
            obtain.str1 = str;
            logBuffer2.push(obtain);
        }
    }

    public NotificationSectionsLogger(LogBuffer logBuffer2) {
        this.logBuffer = logBuffer2;
    }

    public final void logPosition(int i, String str) {
        LogBuffer logBuffer2 = this.logBuffer;
        LogLevel logLevel = LogLevel.DEBUG;
        NotificationSectionsLogger$logPosition$4 notificationSectionsLogger$logPosition$4 = NotificationSectionsLogger$logPosition$4.INSTANCE;
        Objects.requireNonNull(logBuffer2);
        if (!logBuffer2.frozen) {
            LogMessageImpl obtain = logBuffer2.obtain("NotifSections", logLevel, notificationSectionsLogger$logPosition$4);
            obtain.int1 = i;
            obtain.str1 = str;
            logBuffer2.push(obtain);
        }
    }
}
