package com.android.systemui.globalactions;

import com.android.systemui.globalactions.GlobalActionsDialogLite;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class GlobalActionsDialogLite$LockDownAction$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ GlobalActionsDialogLite.LockDownAction f$0;

    public /* synthetic */ GlobalActionsDialogLite$LockDownAction$$ExternalSyntheticLambda0(GlobalActionsDialogLite.LockDownAction lockDownAction) {
        this.f$0 = lockDownAction;
    }

    public final void run() {
        GlobalActionsDialogLite.LockDownAction lockDownAction = this.f$0;
        Objects.requireNonNull(lockDownAction);
        GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
        Objects.requireNonNull(globalActionsDialogLite);
        int i = globalActionsDialogLite.getCurrentUser().id;
        for (int i2 : globalActionsDialogLite.mUserManager.getEnabledProfileIds(i)) {
            if (i2 != i) {
                globalActionsDialogLite.mTrustManager.setDeviceLockedForUser(i2, true);
            }
        }
    }
}
