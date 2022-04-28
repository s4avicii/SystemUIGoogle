package com.android.systemui.p006qs;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.statusbar.DisableFlagsLogger;

/* renamed from: com.android.systemui.qs.QSFragmentDisableFlagsLogger */
/* compiled from: QSFragmentDisableFlagsLogger.kt */
public final class QSFragmentDisableFlagsLogger {
    public final LogBuffer buffer;
    public final DisableFlagsLogger disableFlagsLogger;

    public QSFragmentDisableFlagsLogger(LogBuffer logBuffer, DisableFlagsLogger disableFlagsLogger2) {
        this.buffer = logBuffer;
        this.disableFlagsLogger = disableFlagsLogger2;
    }
}
