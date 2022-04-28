package com.android.systemui.statusbar.phone.ongoingcall;

import android.app.IUidObserver;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import java.util.Objects;

/* compiled from: OngoingCallController.kt */
public final class OngoingCallController$setUpUidObserver$1 extends IUidObserver.Stub {
    public final /* synthetic */ OngoingCallController.CallNotificationInfo $currentCallNotificationInfo;
    public final /* synthetic */ OngoingCallController this$0;

    public final void onUidActive(int i) {
    }

    public final void onUidCachedChanged(int i, boolean z) {
    }

    public final void onUidGone(int i, boolean z) {
    }

    public final void onUidIdle(int i, boolean z) {
    }

    public OngoingCallController$setUpUidObserver$1(OngoingCallController.CallNotificationInfo callNotificationInfo, OngoingCallController ongoingCallController) {
        this.$currentCallNotificationInfo = callNotificationInfo;
        this.this$0 = ongoingCallController;
    }

    public final void onUidStateChanged(int i, int i2, long j, int i3) {
        boolean z;
        OngoingCallController.CallNotificationInfo callNotificationInfo = this.$currentCallNotificationInfo;
        Objects.requireNonNull(callNotificationInfo);
        if (i == callNotificationInfo.uid) {
            OngoingCallController ongoingCallController = this.this$0;
            boolean z2 = ongoingCallController.isCallAppVisible;
            if (i2 <= 2) {
                z = true;
            } else {
                z = false;
            }
            ongoingCallController.isCallAppVisible = z;
            if (z2 != z) {
                ongoingCallController.mainExecutor.execute(new OngoingCallController$setUpUidObserver$1$onUidStateChanged$1(ongoingCallController));
            }
        }
    }
}
