package com.android.keyguard;

import android.os.IBinder;
import android.util.Log;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AdminSecondaryLockScreenController$$ExternalSyntheticLambda0 implements IBinder.DeathRecipient {
    public final /* synthetic */ AdminSecondaryLockScreenController f$0;

    public /* synthetic */ AdminSecondaryLockScreenController$$ExternalSyntheticLambda0(AdminSecondaryLockScreenController adminSecondaryLockScreenController) {
        this.f$0 = adminSecondaryLockScreenController;
    }

    public final void binderDied() {
        AdminSecondaryLockScreenController adminSecondaryLockScreenController = this.f$0;
        Objects.requireNonNull(adminSecondaryLockScreenController);
        adminSecondaryLockScreenController.hide();
        Log.d("AdminSecondaryLockScreenController", "KeyguardClient service died");
    }
}
