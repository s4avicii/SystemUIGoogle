package com.android.systemui.statusbar.phone.ongoingcall;

import android.util.Log;
import com.android.systemui.statusbar.gesture.SwipeStatusBarAwayGestureHandler;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import java.util.Objects;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: OngoingCallController.kt */
public final class OngoingCallController$updateGestureListening$2<T> implements Consumer {
    public final /* synthetic */ OngoingCallController this$0;

    public OngoingCallController$updateGestureListening$2(OngoingCallController ongoingCallController) {
        this.this$0 = ongoingCallController;
    }

    public final void accept(Object obj) {
        ((SwipeStatusBarAwayGestureHandler) obj).addOnGestureDetectedCallback(new Function0<Unit>(this.this$0) {
            public final Object invoke() {
                OngoingCallController.CallNotificationInfo callNotificationInfo;
                OngoingCallController ongoingCallController = (OngoingCallController) this.receiver;
                Objects.requireNonNull(ongoingCallController);
                if (OngoingCallControllerKt.DEBUG) {
                    Log.d("OngoingCallController", "Swipe away gesture detected");
                }
                OngoingCallController.CallNotificationInfo callNotificationInfo2 = ongoingCallController.callNotificationInfo;
                if (callNotificationInfo2 == null) {
                    callNotificationInfo = null;
                } else {
                    callNotificationInfo = new OngoingCallController.CallNotificationInfo(callNotificationInfo2.key, callNotificationInfo2.callStartTime, callNotificationInfo2.intent, callNotificationInfo2.uid, callNotificationInfo2.isOngoing, true);
                }
                ongoingCallController.callNotificationInfo = callNotificationInfo;
                ongoingCallController.statusBarWindowController.ifPresent(OngoingCallController$onSwipeAwayGestureDetected$1.INSTANCE);
                ongoingCallController.swipeStatusBarAwayGestureHandler.ifPresent(OngoingCallController$onSwipeAwayGestureDetected$2.INSTANCE);
                return Unit.INSTANCE;
            }
        });
    }
}
