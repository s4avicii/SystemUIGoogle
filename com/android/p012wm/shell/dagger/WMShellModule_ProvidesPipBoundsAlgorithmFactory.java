package com.android.p012wm.shell.dagger;

import android.content.Context;
import com.android.p012wm.shell.pip.PipBoundsAlgorithm;
import com.android.p012wm.shell.pip.PipBoundsState;
import com.android.p012wm.shell.pip.PipSnapAlgorithm;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.wm.shell.dagger.WMShellModule_ProvidesPipBoundsAlgorithmFactory */
public final class WMShellModule_ProvidesPipBoundsAlgorithmFactory implements Factory<PipBoundsAlgorithm> {
    public final Provider<Context> contextProvider;
    public final Provider<PipBoundsState> pipBoundsStateProvider;
    public final Provider<PipSnapAlgorithm> pipSnapAlgorithmProvider;

    public final Object get() {
        return new PipBoundsAlgorithm(this.contextProvider.get(), this.pipBoundsStateProvider.get(), this.pipSnapAlgorithmProvider.get());
    }

    public WMShellModule_ProvidesPipBoundsAlgorithmFactory(Provider<Context> provider, Provider<PipBoundsState> provider2, Provider<PipSnapAlgorithm> provider3) {
        this.contextProvider = provider;
        this.pipBoundsStateProvider = provider2;
        this.pipSnapAlgorithmProvider = provider3;
    }
}
