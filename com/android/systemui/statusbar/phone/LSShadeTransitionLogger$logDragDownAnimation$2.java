package com.android.systemui.statusbar.phone;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: LSShadeTransitionLogger.kt */
public final class LSShadeTransitionLogger$logDragDownAnimation$2 extends Lambda implements Function1<LogMessage, String> {
    public static final LSShadeTransitionLogger$logDragDownAnimation$2 INSTANCE = new LSShadeTransitionLogger$logDragDownAnimation$2();

    public LSShadeTransitionLogger$logDragDownAnimation$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("Drag down amount animating to ", Double.valueOf(((LogMessage) obj).getDouble1()));
    }
}
