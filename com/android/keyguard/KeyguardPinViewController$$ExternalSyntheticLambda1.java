package com.android.keyguard;

import com.android.systemui.statusbar.policy.DevicePostureController;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardPinViewController$$ExternalSyntheticLambda1 implements DevicePostureController.Callback {
    public final /* synthetic */ KeyguardPinViewController f$0;

    public /* synthetic */ KeyguardPinViewController$$ExternalSyntheticLambda1(KeyguardPinViewController keyguardPinViewController) {
        this.f$0 = keyguardPinViewController;
    }

    public final void onPostureChanged(int i) {
        KeyguardPinViewController keyguardPinViewController = this.f$0;
        Objects.requireNonNull(keyguardPinViewController);
        KeyguardPINView keyguardPINView = (KeyguardPINView) keyguardPinViewController.mView;
        Objects.requireNonNull(keyguardPINView);
        keyguardPINView.mLastDevicePosture = i;
        keyguardPINView.updateMargins();
    }
}
