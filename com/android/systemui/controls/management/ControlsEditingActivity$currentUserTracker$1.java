package com.android.systemui.controls.management;

import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.settings.CurrentUserTracker;

/* compiled from: ControlsEditingActivity.kt */
public final class ControlsEditingActivity$currentUserTracker$1 extends CurrentUserTracker {
    public final int startingUser;
    public final /* synthetic */ ControlsEditingActivity this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ControlsEditingActivity$currentUserTracker$1(ControlsEditingActivity controlsEditingActivity, BroadcastDispatcher broadcastDispatcher) {
        super(broadcastDispatcher);
        this.this$0 = controlsEditingActivity;
        this.startingUser = controlsEditingActivity.controller.getCurrentUserId();
    }

    public final void onUserSwitched(int i) {
        if (i != this.startingUser) {
            stopTracking();
            this.this$0.finish();
        }
    }
}
