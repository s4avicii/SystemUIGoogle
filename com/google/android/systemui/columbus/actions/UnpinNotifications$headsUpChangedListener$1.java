package com.google.android.systemui.columbus.actions;

import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;

/* compiled from: UnpinNotifications.kt */
public final class UnpinNotifications$headsUpChangedListener$1 implements OnHeadsUpChangedListener {
    public final /* synthetic */ UnpinNotifications this$0;

    public UnpinNotifications$headsUpChangedListener$1(UnpinNotifications unpinNotifications) {
        this.this$0 = unpinNotifications;
    }

    public final void onHeadsUpPinnedModeChanged(boolean z) {
        boolean z2;
        UnpinNotifications unpinNotifications = this.this$0;
        unpinNotifications.hasPinnedHeadsUp = z;
        if (unpinNotifications.silenceAlertsDisabled.isBlocking() || !unpinNotifications.hasPinnedHeadsUp) {
            z2 = false;
        } else {
            z2 = true;
        }
        unpinNotifications.setAvailable(z2);
    }
}
