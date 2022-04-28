package com.android.systemui.p006qs.external;

import com.android.internal.logging.InstanceIdSequence;
import com.android.internal.logging.UiEventLogger;

/* renamed from: com.android.systemui.qs.external.TileRequestDialogEventLogger */
/* compiled from: TileRequestDialogEventLogger.kt */
public final class TileRequestDialogEventLogger {
    public final InstanceIdSequence instanceIdSequence;
    public final UiEventLogger uiEventLogger;

    public TileRequestDialogEventLogger(UiEventLogger uiEventLogger2, InstanceIdSequence instanceIdSequence2) {
        this.uiEventLogger = uiEventLogger2;
        this.instanceIdSequence = instanceIdSequence2;
    }
}
