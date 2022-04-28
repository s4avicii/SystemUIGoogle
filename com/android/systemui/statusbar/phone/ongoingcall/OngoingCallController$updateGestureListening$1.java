package com.android.systemui.statusbar.phone.ongoingcall;

import com.android.systemui.statusbar.gesture.SwipeStatusBarAwayGestureHandler;
import java.util.function.Consumer;

/* compiled from: OngoingCallController.kt */
public final class OngoingCallController$updateGestureListening$1<T> implements Consumer {
    public static final OngoingCallController$updateGestureListening$1<T> INSTANCE = new OngoingCallController$updateGestureListening$1<>();

    public final void accept(Object obj) {
        ((SwipeStatusBarAwayGestureHandler) obj).removeOnGestureDetectedCallback();
    }
}
