package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.phone.PhoneStatusBarPolicy;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PhoneStatusBarPolicy$5$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ PhoneStatusBarPolicy.C15115 f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ PhoneStatusBarPolicy$5$$ExternalSyntheticLambda0(PhoneStatusBarPolicy.C15115 r1, boolean z) {
        this.f$0 = r1;
        this.f$1 = z;
    }

    public final void run() {
        PhoneStatusBarPolicy.C15115 r0 = this.f$0;
        boolean z = this.f$1;
        Objects.requireNonNull(r0);
        PhoneStatusBarPolicy phoneStatusBarPolicy = PhoneStatusBarPolicy.this;
        phoneStatusBarPolicy.mIconController.setIconVisibility(phoneStatusBarPolicy.mSlotSensorsOff, z);
    }
}
