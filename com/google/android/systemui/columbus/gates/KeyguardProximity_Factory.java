package com.google.android.systemui.columbus.gates;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class KeyguardProximity_Factory implements Factory<KeyguardProximity> {
    public final Provider<Context> contextProvider;
    public final Provider<KeyguardVisibility> keyguardGateProvider;
    public final Provider<Proximity> proximityProvider;

    public final Object get() {
        return new KeyguardProximity(this.contextProvider.get(), this.keyguardGateProvider.get(), this.proximityProvider.get());
    }

    public KeyguardProximity_Factory(Provider<Context> provider, Provider<KeyguardVisibility> provider2, Provider<Proximity> provider3) {
        this.contextProvider = provider;
        this.keyguardGateProvider = provider2;
        this.proximityProvider = provider3;
    }
}
