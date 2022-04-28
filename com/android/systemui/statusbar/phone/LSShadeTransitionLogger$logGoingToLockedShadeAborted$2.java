package com.android.systemui.statusbar.phone;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: LSShadeTransitionLogger.kt */
public final class LSShadeTransitionLogger$logGoingToLockedShadeAborted$2 extends Lambda implements Function1<LogMessage, String> {
    public static final LSShadeTransitionLogger$logGoingToLockedShadeAborted$2 INSTANCE = new LSShadeTransitionLogger$logGoingToLockedShadeAborted$2();

    public LSShadeTransitionLogger$logGoingToLockedShadeAborted$2() {
        super(1);
    }

    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        return "Going to the Locked Shade has been aborted";
    }
}
