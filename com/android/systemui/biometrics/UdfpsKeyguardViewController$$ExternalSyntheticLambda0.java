package com.android.systemui.biometrics;

import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class UdfpsKeyguardViewController$$ExternalSyntheticLambda0 implements UnlockedScreenOffAnimationController.Callback {
    public final /* synthetic */ UdfpsKeyguardViewController f$0;

    public /* synthetic */ UdfpsKeyguardViewController$$ExternalSyntheticLambda0(UdfpsKeyguardViewController udfpsKeyguardViewController) {
        this.f$0 = udfpsKeyguardViewController;
    }

    public final void onUnlockedScreenOffProgressUpdate(float f, float f2) {
        UdfpsKeyguardViewController udfpsKeyguardViewController = this.f$0;
        Objects.requireNonNull(udfpsKeyguardViewController);
        udfpsKeyguardViewController.mStateListener.onDozeAmountChanged(f, f2);
    }
}
