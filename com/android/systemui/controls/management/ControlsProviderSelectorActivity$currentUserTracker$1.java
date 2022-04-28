package com.android.systemui.controls.management;

import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.settings.CurrentUserTracker;

/* compiled from: ControlsProviderSelectorActivity.kt */
public final class ControlsProviderSelectorActivity$currentUserTracker$1 extends CurrentUserTracker {
    public final int startingUser;
    public final /* synthetic */ ControlsProviderSelectorActivity this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ControlsProviderSelectorActivity$currentUserTracker$1(ControlsProviderSelectorActivity controlsProviderSelectorActivity, BroadcastDispatcher broadcastDispatcher) {
        super(broadcastDispatcher);
        this.this$0 = controlsProviderSelectorActivity;
        this.startingUser = controlsProviderSelectorActivity.listingController.getCurrentUserId();
    }

    public final void onUserSwitched(int i) {
        if (i != this.startingUser) {
            stopTracking();
            this.this$0.finish();
        }
    }
}
