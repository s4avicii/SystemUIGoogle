package com.android.systemui.log;

/* compiled from: LogcatEchoTrackerProd.kt */
public final class LogcatEchoTrackerProd implements LogcatEchoTracker {
    public final boolean isBufferLoggable(String str, LogLevel logLevel) {
        if (logLevel.compareTo(LogLevel.WARNING) >= 0) {
            return true;
        }
        return false;
    }

    public final boolean isTagLoggable(String str, LogLevel logLevel) {
        if (logLevel.compareTo(LogLevel.WARNING) >= 0) {
            return true;
        }
        return false;
    }
}
