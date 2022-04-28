package com.android.systemui.statusbar.phone;

import com.android.p012wm.shell.C1777R;
import java.util.Objects;
import java.util.concurrent.Callable;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PhoneStatusBarPolicy$$ExternalSyntheticLambda1 implements Callable {
    public final /* synthetic */ PhoneStatusBarPolicy f$0;

    public /* synthetic */ PhoneStatusBarPolicy$$ExternalSyntheticLambda1(PhoneStatusBarPolicy phoneStatusBarPolicy) {
        this.f$0 = phoneStatusBarPolicy;
    }

    public final Object call() {
        PhoneStatusBarPolicy phoneStatusBarPolicy = this.f$0;
        Objects.requireNonNull(phoneStatusBarPolicy);
        return phoneStatusBarPolicy.mResources.getString(C1777R.string.accessibility_managed_profile);
    }
}
