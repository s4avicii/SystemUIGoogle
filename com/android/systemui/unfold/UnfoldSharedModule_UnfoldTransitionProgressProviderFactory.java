package com.android.systemui.unfold;

import com.android.systemui.unfold.config.UnfoldTransitionConfig;
import com.android.systemui.unfold.progress.FixedTimingTransitionProgressProvider;
import com.android.systemui.unfold.progress.PhysicsBasedUnfoldTransitionProgressProvider;
import com.android.systemui.unfold.updates.FoldStateProvider;
import com.android.systemui.unfold.util.ATraceLoggerTransitionProgressListener;
import com.android.systemui.unfold.util.ScaleAwareTransitionProgressProvider;
import com.android.systemui.unfold.util.ScopedUnfoldTransitionProgressProvider;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Provider;

public final class UnfoldSharedModule_UnfoldTransitionProgressProviderFactory implements Factory<Optional<UnfoldTransitionProgressProvider>> {
    public final Provider<UnfoldTransitionConfig> configProvider;
    public final Provider<FoldStateProvider> foldStateProvider;
    public final UnfoldSharedModule module;
    public final Provider<ScaleAwareTransitionProgressProvider.Factory> scaleAwareProviderFactoryProvider;
    public final Provider<ATraceLoggerTransitionProgressListener> tracingListenerProvider;

    public final Object get() {
        UnfoldTransitionProgressProvider unfoldTransitionProgressProvider;
        UnfoldSharedModule unfoldSharedModule = this.module;
        UnfoldTransitionConfig unfoldTransitionConfig = this.configProvider.get();
        ScaleAwareTransitionProgressProvider.Factory factory = this.scaleAwareProviderFactoryProvider.get();
        ATraceLoggerTransitionProgressListener aTraceLoggerTransitionProgressListener = this.tracingListenerProvider.get();
        FoldStateProvider foldStateProvider2 = this.foldStateProvider.get();
        Objects.requireNonNull(unfoldSharedModule);
        if (!unfoldTransitionConfig.isEnabled()) {
            return Optional.empty();
        }
        if (unfoldTransitionConfig.isHingeAngleEnabled()) {
            unfoldTransitionProgressProvider = new PhysicsBasedUnfoldTransitionProgressProvider(foldStateProvider2);
        } else {
            unfoldTransitionProgressProvider = new FixedTimingTransitionProgressProvider(foldStateProvider2);
        }
        ScaleAwareTransitionProgressProvider wrap = factory.wrap(unfoldTransitionProgressProvider);
        Objects.requireNonNull(wrap);
        ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider = wrap.scopedUnfoldTransitionProgressProvider;
        Objects.requireNonNull(scopedUnfoldTransitionProgressProvider);
        scopedUnfoldTransitionProgressProvider.listeners.add(aTraceLoggerTransitionProgressListener);
        return Optional.of(wrap);
    }

    public UnfoldSharedModule_UnfoldTransitionProgressProviderFactory(UnfoldSharedModule unfoldSharedModule, Provider<UnfoldTransitionConfig> provider, Provider<ScaleAwareTransitionProgressProvider.Factory> provider2, Provider<ATraceLoggerTransitionProgressListener> provider3, Provider<FoldStateProvider> provider4) {
        this.module = unfoldSharedModule;
        this.configProvider = provider;
        this.scaleAwareProviderFactoryProvider = provider2;
        this.tracingListenerProvider = provider3;
        this.foldStateProvider = provider4;
    }
}
