package com.android.systemui.statusbar.phone;

import androidx.lifecycle.Observer;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class LightsOutNotifController$$ExternalSyntheticLambda0 implements Observer {
    public final /* synthetic */ LightsOutNotifController f$0;

    public /* synthetic */ LightsOutNotifController$$ExternalSyntheticLambda0(LightsOutNotifController lightsOutNotifController) {
        this.f$0 = lightsOutNotifController;
    }

    public final void onChanged(Object obj) {
        LightsOutNotifController lightsOutNotifController = this.f$0;
        Boolean bool = (Boolean) obj;
        Objects.requireNonNull(lightsOutNotifController);
        lightsOutNotifController.updateLightsOutView();
    }
}
