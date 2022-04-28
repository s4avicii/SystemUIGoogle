package com.android.systemui.statusbar.p007tv;

import android.content.Context;
import com.android.systemui.statusbar.policy.SecurityController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.systemui.statusbar.tv.VpnStatusObserver_Factory */
public final class VpnStatusObserver_Factory implements Factory<VpnStatusObserver> {
    public final Provider<Context> contextProvider;
    public final Provider<SecurityController> securityControllerProvider;

    public final Object get() {
        return new VpnStatusObserver(this.contextProvider.get(), this.securityControllerProvider.get());
    }

    public VpnStatusObserver_Factory(Provider<Context> provider, Provider<SecurityController> provider2) {
        this.contextProvider = provider;
        this.securityControllerProvider = provider2;
    }
}
