package com.android.systemui.statusbar.phone;

import androidx.lifecycle.Observer;
import com.android.wifitrackerlib.WifiEntry$$ExternalSyntheticLambda2;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PhoneStatusBarPolicy$$ExternalSyntheticLambda0 implements Observer {
    public final /* synthetic */ PhoneStatusBarPolicy f$0;

    public /* synthetic */ PhoneStatusBarPolicy$$ExternalSyntheticLambda0(PhoneStatusBarPolicy phoneStatusBarPolicy) {
        this.f$0 = phoneStatusBarPolicy;
    }

    public final void onChanged(Object obj) {
        PhoneStatusBarPolicy phoneStatusBarPolicy = this.f$0;
        Integer num = (Integer) obj;
        Objects.requireNonNull(phoneStatusBarPolicy);
        phoneStatusBarPolicy.mHandler.post(new WifiEntry$$ExternalSyntheticLambda2(phoneStatusBarPolicy, 7));
    }
}
