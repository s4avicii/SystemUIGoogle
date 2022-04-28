package com.android.systemui.p006qs.tiles.dialog;

import android.content.Context;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.settingslib.notification.ZenModeDialogMetricsLogger;
import com.android.systemui.p006qs.QSEvents;

/* renamed from: com.android.systemui.qs.tiles.dialog.QSZenModeDialogMetricsLogger */
public final class QSZenModeDialogMetricsLogger extends ZenModeDialogMetricsLogger {
    public final UiEventLoggerImpl mUiEventLogger = QSEvents.qsUiEventsLogger;

    public QSZenModeDialogMetricsLogger(Context context) {
        super(context);
    }
}
