package com.android.systemui.statusbar.phone.ongoingcall;

import com.android.systemui.statusbar.gesture.SwipeStatusBarAwayGestureHandler;
import java.util.function.Consumer;

/* compiled from: OngoingCallController.kt */
public final class OngoingCallController$removeChip$2<T> implements Consumer {
    public static final OngoingCallController$removeChip$2<T> INSTANCE = new OngoingCallController$removeChip$2<>();

    public final void accept(Object obj) {
        ((SwipeStatusBarAwayGestureHandler) obj).removeOnGestureDetectedCallback();
    }
}
