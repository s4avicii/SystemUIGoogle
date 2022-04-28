package com.android.keyguard;

import android.util.Log;
import com.android.keyguard.AdminSecondaryLockScreenController;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AdminSecondaryLockScreenController$4$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ AdminSecondaryLockScreenController.C04744 f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ AdminSecondaryLockScreenController$4$$ExternalSyntheticLambda0(AdminSecondaryLockScreenController.C04744 r1, int i) {
        this.f$0 = r1;
        this.f$1 = i;
    }

    public final void run() {
        AdminSecondaryLockScreenController.C04744 r0 = this.f$0;
        int i = this.f$1;
        Objects.requireNonNull(r0);
        AdminSecondaryLockScreenController.this.dismiss(i);
        Log.w("AdminSecondaryLockScreenController", "Timed out waiting for secondary lockscreen content.");
    }
}
