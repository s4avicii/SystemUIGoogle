package com.android.systemui.statusbar.gesture;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: SwipeStatusBarAwayGestureLogger.kt */
final class SwipeStatusBarAwayGestureLogger$logGestureDetected$2 extends Lambda implements Function1<LogMessage, String> {
    public static final SwipeStatusBarAwayGestureLogger$logGestureDetected$2 INSTANCE = new SwipeStatusBarAwayGestureLogger$logGestureDetected$2();

    public SwipeStatusBarAwayGestureLogger$logGestureDetected$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("Gesture detected; notifying callbacks. y=", Integer.valueOf(((LogMessage) obj).getInt1()));
    }
}
