package com.android.systemui.unfold;

import com.android.internal.util.FrameworkStatsLog;
import com.android.systemui.unfold.FoldStateLoggingProvider;

/* compiled from: FoldStateLogger.kt */
public final class FoldStateLogger implements FoldStateLoggingProvider.FoldStateLoggingListener {
    public final FoldStateLoggingProvider foldStateLoggingProvider;

    public final void onFoldUpdate(FoldStateChange foldStateChange) {
        FrameworkStatsLog.write(414, foldStateChange.previous, foldStateChange.current, foldStateChange.dtMillis);
    }

    public FoldStateLogger(FoldStateLoggingProvider foldStateLoggingProvider2) {
        this.foldStateLoggingProvider = foldStateLoggingProvider2;
    }
}
