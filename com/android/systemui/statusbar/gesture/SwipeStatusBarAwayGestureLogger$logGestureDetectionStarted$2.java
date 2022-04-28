package com.android.systemui.statusbar.gesture;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: SwipeStatusBarAwayGestureLogger.kt */
final class SwipeStatusBarAwayGestureLogger$logGestureDetectionStarted$2 extends Lambda implements Function1<LogMessage, String> {
    public static final SwipeStatusBarAwayGestureLogger$logGestureDetectionStarted$2 INSTANCE = new SwipeStatusBarAwayGestureLogger$logGestureDetectionStarted$2();

    public SwipeStatusBarAwayGestureLogger$logGestureDetectionStarted$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("Beginning gesture detection. y=", Integer.valueOf(((LogMessage) obj).getInt1()));
    }
}
