package com.android.p012wm.shell.dagger;

import android.content.Context;
import com.android.p012wm.shell.ShellTaskOrganizer;
import com.android.p012wm.shell.pip.PipAnimationController;
import com.android.p012wm.shell.pip.PipBoundsAlgorithm;
import com.android.p012wm.shell.pip.PipBoundsState;
import com.android.p012wm.shell.pip.PipSurfaceTransactionHelper;
import com.android.p012wm.shell.pip.PipTransition;
import com.android.p012wm.shell.pip.PipTransitionController;
import com.android.p012wm.shell.pip.PipTransitionState;
import com.android.p012wm.shell.pip.phone.PhonePipMenuController;
import com.android.p012wm.shell.splitscreen.SplitScreenController;
import com.android.p012wm.shell.transition.Transitions;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

/* renamed from: com.android.wm.shell.dagger.WMShellModule_ProvidePipTransitionControllerFactory */
public final class WMShellModule_ProvidePipTransitionControllerFactory implements Factory<PipTransitionController> {
    public final Provider<Context> contextProvider;
    public final Provider<PipAnimationController> pipAnimationControllerProvider;
    public final Provider<PipBoundsAlgorithm> pipBoundsAlgorithmProvider;
    public final Provider<PipBoundsState> pipBoundsStateProvider;
    public final Provider<PhonePipMenuController> pipMenuControllerProvider;
    public final Provider<PipSurfaceTransactionHelper> pipSurfaceTransactionHelperProvider;
    public final Provider<PipTransitionState> pipTransitionStateProvider;
    public final Provider<ShellTaskOrganizer> shellTaskOrganizerProvider;
    public final Provider<Optional<SplitScreenController>> splitScreenOptionalProvider;
    public final Provider<Transitions> transitionsProvider;

    public static WMShellModule_ProvidePipTransitionControllerFactory create(Provider<Context> provider, Provider<Transitions> provider2, Provider<ShellTaskOrganizer> provider3, Provider<PipAnimationController> provider4, Provider<PipBoundsAlgorithm> provider5, Provider<PipBoundsState> provider6, Provider<PipTransitionState> provider7, Provider<PhonePipMenuController> provider8, Provider<PipSurfaceTransactionHelper> provider9, Provider<Optional<SplitScreenController>> provider10) {
        return new WMShellModule_ProvidePipTransitionControllerFactory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10);
    }

    public final Object get() {
        ShellTaskOrganizer shellTaskOrganizer = this.shellTaskOrganizerProvider.get();
        PipAnimationController pipAnimationController = this.pipAnimationControllerProvider.get();
        PipBoundsAlgorithm pipBoundsAlgorithm = this.pipBoundsAlgorithmProvider.get();
        return new PipTransition(this.contextProvider.get(), this.pipBoundsStateProvider.get(), this.pipTransitionStateProvider.get(), this.pipMenuControllerProvider.get(), pipBoundsAlgorithm, pipAnimationController, this.transitionsProvider.get(), this.pipSurfaceTransactionHelperProvider.get(), this.splitScreenOptionalProvider.get());
    }

    public WMShellModule_ProvidePipTransitionControllerFactory(Provider<Context> provider, Provider<Transitions> provider2, Provider<ShellTaskOrganizer> provider3, Provider<PipAnimationController> provider4, Provider<PipBoundsAlgorithm> provider5, Provider<PipBoundsState> provider6, Provider<PipTransitionState> provider7, Provider<PhonePipMenuController> provider8, Provider<PipSurfaceTransactionHelper> provider9, Provider<Optional<SplitScreenController>> provider10) {
        this.contextProvider = provider;
        this.transitionsProvider = provider2;
        this.shellTaskOrganizerProvider = provider3;
        this.pipAnimationControllerProvider = provider4;
        this.pipBoundsAlgorithmProvider = provider5;
        this.pipBoundsStateProvider = provider6;
        this.pipTransitionStateProvider = provider7;
        this.pipMenuControllerProvider = provider8;
        this.pipSurfaceTransactionHelperProvider = provider9;
        this.splitScreenOptionalProvider = provider10;
    }
}
