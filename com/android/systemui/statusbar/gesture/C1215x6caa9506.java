package com.android.systemui.statusbar.gesture;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.systemui.statusbar.gesture.SwipeStatusBarAwayGestureLogger$logGestureDetectionEndedWithoutTriggering$2 */
/* compiled from: SwipeStatusBarAwayGestureLogger.kt */
final class C1215x6caa9506 extends Lambda implements Function1<LogMessage, String> {
    public static final C1215x6caa9506 INSTANCE = new C1215x6caa9506();

    public C1215x6caa9506() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("Gesture finished; no swipe up gesture detected. Final y=", Integer.valueOf(((LogMessage) obj).getInt1()));
    }
}
