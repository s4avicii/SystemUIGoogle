package com.android.systemui.statusbar.connectivity;

import android.telephony.TelephonyCallback;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NetworkControllerImpl$$ExternalSyntheticLambda0 implements TelephonyCallback.ActiveDataSubscriptionIdListener {
    public final /* synthetic */ NetworkControllerImpl f$0;

    public /* synthetic */ NetworkControllerImpl$$ExternalSyntheticLambda0(NetworkControllerImpl networkControllerImpl) {
        this.f$0 = networkControllerImpl;
    }

    public final void onActiveDataSubscriptionIdChanged(int i) {
        NetworkControllerImpl networkControllerImpl = this.f$0;
        Objects.requireNonNull(networkControllerImpl);
        networkControllerImpl.mBgExecutor.execute(new NetworkControllerImpl$$ExternalSyntheticLambda1(networkControllerImpl, i, 0));
    }
}
