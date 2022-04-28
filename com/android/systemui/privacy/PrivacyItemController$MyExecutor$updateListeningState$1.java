package com.android.systemui.privacy;

import com.android.systemui.privacy.PrivacyItemController;
import java.util.Objects;

/* compiled from: PrivacyItemController.kt */
public final class PrivacyItemController$MyExecutor$updateListeningState$1 implements Runnable {
    public final /* synthetic */ PrivacyItemController this$0;

    public PrivacyItemController$MyExecutor$updateListeningState$1(PrivacyItemController privacyItemController) {
        this.this$0 = privacyItemController;
    }

    public final void run() {
        boolean z;
        PrivacyItemController privacyItemController = this.this$0;
        PrivacyItemController.Companion companion = PrivacyItemController.Companion;
        Objects.requireNonNull(privacyItemController);
        boolean z2 = !privacyItemController.callbacks.isEmpty();
        if (privacyItemController.micCameraAvailable || privacyItemController.locationAvailable) {
            z = true;
        } else {
            z = false;
        }
        boolean z3 = z2 & z;
        if (privacyItemController.listening != z3) {
            privacyItemController.listening = z3;
            if (z3) {
                privacyItemController.appOpsController.addCallback(PrivacyItemController.OPS, privacyItemController.f67cb);
                privacyItemController.userTracker.addCallback(privacyItemController.userTrackerCallback, privacyItemController.bgExecutor);
                privacyItemController.update(true);
                return;
            }
            privacyItemController.appOpsController.removeCallback(PrivacyItemController.OPS, privacyItemController.f67cb);
            privacyItemController.userTracker.removeCallback(privacyItemController.userTrackerCallback);
            privacyItemController.update(false);
        }
    }
}
