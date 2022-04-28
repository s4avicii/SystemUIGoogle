package com.google.android.systemui.columbus.actions;

import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.google.android.systemui.columbus.gates.Gate;
import java.util.Objects;

/* compiled from: UnpinNotifications.kt */
public final class UnpinNotifications$gateListener$1 implements Gate.Listener {
    public final /* synthetic */ UnpinNotifications this$0;

    public UnpinNotifications$gateListener$1(UnpinNotifications unpinNotifications) {
        this.this$0 = unpinNotifications;
    }

    public final void onGateChanged(Gate gate) {
        boolean z;
        if (this.this$0.silenceAlertsDisabled.isBlocking()) {
            UnpinNotifications unpinNotifications = this.this$0;
            Objects.requireNonNull(unpinNotifications);
            HeadsUpManager headsUpManager = unpinNotifications.headsUpManager;
            if (headsUpManager != null) {
                headsUpManager.mListeners.remove(unpinNotifications.headsUpChangedListener);
                return;
            }
            return;
        }
        UnpinNotifications unpinNotifications2 = this.this$0;
        Objects.requireNonNull(unpinNotifications2);
        HeadsUpManager headsUpManager2 = unpinNotifications2.headsUpManager;
        if (headsUpManager2 != null) {
            headsUpManager2.addListener(unpinNotifications2.headsUpChangedListener);
        }
        HeadsUpManager headsUpManager3 = unpinNotifications2.headsUpManager;
        if (headsUpManager3 == null) {
            z = false;
        } else {
            z = headsUpManager3.mHasPinnedNotification;
        }
        unpinNotifications2.hasPinnedHeadsUp = z;
    }
}
