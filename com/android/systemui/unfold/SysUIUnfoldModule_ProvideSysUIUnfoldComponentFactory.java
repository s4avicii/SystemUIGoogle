package com.android.systemui.unfold;

import com.android.systemui.unfold.SysUIUnfoldComponent;
import com.android.systemui.unfold.util.NaturalRotationUnfoldProgressProvider;
import com.android.systemui.unfold.util.ScopedUnfoldTransitionProgressProvider;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Provider;

public final class SysUIUnfoldModule_ProvideSysUIUnfoldComponentFactory implements Factory<Optional<SysUIUnfoldComponent>> {
    public final Provider<SysUIUnfoldComponent.Factory> factoryProvider;
    public final SysUIUnfoldModule module;
    public final Provider<Optional<UnfoldTransitionProgressProvider>> providerProvider;
    public final Provider<Optional<NaturalRotationUnfoldProgressProvider>> rotationProvider;
    public final Provider<Optional<ScopedUnfoldTransitionProgressProvider>> scopedProvider;

    public final Object get() {
        SysUIUnfoldModule sysUIUnfoldModule = this.module;
        SysUIUnfoldComponent.Factory factory = this.factoryProvider.get();
        Objects.requireNonNull(sysUIUnfoldModule);
        UnfoldTransitionProgressProvider unfoldTransitionProgressProvider = (UnfoldTransitionProgressProvider) this.providerProvider.get().orElse((Object) null);
        NaturalRotationUnfoldProgressProvider naturalRotationUnfoldProgressProvider = (NaturalRotationUnfoldProgressProvider) this.rotationProvider.get().orElse((Object) null);
        ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider = (ScopedUnfoldTransitionProgressProvider) this.scopedProvider.get().orElse((Object) null);
        if (unfoldTransitionProgressProvider == null || naturalRotationUnfoldProgressProvider == null || scopedUnfoldTransitionProgressProvider == null) {
            return Optional.empty();
        }
        return Optional.of(factory.create(unfoldTransitionProgressProvider, naturalRotationUnfoldProgressProvider, scopedUnfoldTransitionProgressProvider));
    }

    public SysUIUnfoldModule_ProvideSysUIUnfoldComponentFactory(SysUIUnfoldModule sysUIUnfoldModule, Provider<Optional<UnfoldTransitionProgressProvider>> provider, Provider<Optional<NaturalRotationUnfoldProgressProvider>> provider2, Provider<Optional<ScopedUnfoldTransitionProgressProvider>> provider3, Provider<SysUIUnfoldComponent.Factory> provider4) {
        this.module = sysUIUnfoldModule;
        this.providerProvider = provider;
        this.rotationProvider = provider2;
        this.scopedProvider = provider3;
        this.factoryProvider = provider4;
    }
}
