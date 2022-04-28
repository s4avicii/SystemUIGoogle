package com.android.p012wm.shell.dagger;

import android.content.Context;
import com.android.p012wm.shell.common.FloatingContentCoordinator;
import com.android.p012wm.shell.pip.PipBoundsState;
import com.android.p012wm.shell.pip.PipSnapAlgorithm;
import com.android.p012wm.shell.pip.PipTaskOrganizer;
import com.android.p012wm.shell.pip.PipTransitionController;
import com.android.p012wm.shell.pip.phone.PhonePipMenuController;
import com.android.p012wm.shell.pip.phone.PipMotionHelper;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.wm.shell.dagger.WMShellModule_ProvidePipMotionHelperFactory */
public final class WMShellModule_ProvidePipMotionHelperFactory implements Factory<PipMotionHelper> {
    public final Provider<Context> contextProvider;
    public final Provider<FloatingContentCoordinator> floatingContentCoordinatorProvider;
    public final Provider<PhonePipMenuController> menuControllerProvider;
    public final Provider<PipBoundsState> pipBoundsStateProvider;
    public final Provider<PipSnapAlgorithm> pipSnapAlgorithmProvider;
    public final Provider<PipTaskOrganizer> pipTaskOrganizerProvider;
    public final Provider<PipTransitionController> pipTransitionControllerProvider;

    public final Object get() {
        return new PipMotionHelper(this.contextProvider.get(), this.pipBoundsStateProvider.get(), this.pipTaskOrganizerProvider.get(), this.menuControllerProvider.get(), this.pipSnapAlgorithmProvider.get(), this.pipTransitionControllerProvider.get(), this.floatingContentCoordinatorProvider.get());
    }

    public WMShellModule_ProvidePipMotionHelperFactory(Provider<Context> provider, Provider<PipBoundsState> provider2, Provider<PipTaskOrganizer> provider3, Provider<PhonePipMenuController> provider4, Provider<PipSnapAlgorithm> provider5, Provider<PipTransitionController> provider6, Provider<FloatingContentCoordinator> provider7) {
        this.contextProvider = provider;
        this.pipBoundsStateProvider = provider2;
        this.pipTaskOrganizerProvider = provider3;
        this.menuControllerProvider = provider4;
        this.pipSnapAlgorithmProvider = provider5;
        this.pipTransitionControllerProvider = provider6;
        this.floatingContentCoordinatorProvider = provider7;
    }
}
