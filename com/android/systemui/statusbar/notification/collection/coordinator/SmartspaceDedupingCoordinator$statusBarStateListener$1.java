package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.plugins.statusbar.StatusBarStateController;
import java.util.Objects;

/* compiled from: SmartspaceDedupingCoordinator.kt */
public final class SmartspaceDedupingCoordinator$statusBarStateListener$1 implements StatusBarStateController.StateListener {
    public final /* synthetic */ SmartspaceDedupingCoordinator this$0;

    public SmartspaceDedupingCoordinator$statusBarStateListener$1(SmartspaceDedupingCoordinator smartspaceDedupingCoordinator) {
        this.this$0 = smartspaceDedupingCoordinator;
    }

    public final void onStateChanged(int i) {
        SmartspaceDedupingCoordinator smartspaceDedupingCoordinator = this.this$0;
        Objects.requireNonNull(smartspaceDedupingCoordinator);
        boolean z = smartspaceDedupingCoordinator.isOnLockscreen;
        boolean z2 = true;
        if (i != 1) {
            z2 = false;
        }
        smartspaceDedupingCoordinator.isOnLockscreen = z2;
        if (z2 != z) {
            smartspaceDedupingCoordinator.filter.invalidateList();
        }
    }
}
