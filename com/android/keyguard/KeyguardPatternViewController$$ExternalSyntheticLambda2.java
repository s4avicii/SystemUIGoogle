package com.android.keyguard;

import com.android.systemui.statusbar.policy.DevicePostureController;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardPatternViewController$$ExternalSyntheticLambda2 implements DevicePostureController.Callback {
    public final /* synthetic */ KeyguardPatternViewController f$0;

    public /* synthetic */ KeyguardPatternViewController$$ExternalSyntheticLambda2(KeyguardPatternViewController keyguardPatternViewController) {
        this.f$0 = keyguardPatternViewController;
    }

    public final void onPostureChanged(int i) {
        KeyguardPatternViewController keyguardPatternViewController = this.f$0;
        Objects.requireNonNull(keyguardPatternViewController);
        ((KeyguardPatternView) keyguardPatternViewController.mView).onDevicePostureChanged(i);
    }
}
