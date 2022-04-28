package com.android.p012wm.shell.dagger;

import com.android.p012wm.shell.ShellTaskOrganizer;
import com.android.p012wm.shell.pip.PipAnimationController;
import com.android.p012wm.shell.pip.PipTransitionController;
import com.android.p012wm.shell.pip.p013tv.TvPipBoundsAlgorithm;
import com.android.p012wm.shell.pip.p013tv.TvPipBoundsState;
import com.android.p012wm.shell.pip.p013tv.TvPipMenuController;
import com.android.p012wm.shell.pip.p013tv.TvPipTransition;
import com.android.p012wm.shell.transition.Transitions;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.wm.shell.dagger.TvPipModule_ProvideTvPipTransitionFactory */
public final class TvPipModule_ProvideTvPipTransitionFactory implements Factory<PipTransitionController> {
    public final Provider<PipAnimationController> pipAnimationControllerProvider;
    public final Provider<TvPipMenuController> pipMenuControllerProvider;
    public final Provider<ShellTaskOrganizer> shellTaskOrganizerProvider;
    public final Provider<Transitions> transitionsProvider;
    public final Provider<TvPipBoundsAlgorithm> tvPipBoundsAlgorithmProvider;
    public final Provider<TvPipBoundsState> tvPipBoundsStateProvider;

    public final Object get() {
        ShellTaskOrganizer shellTaskOrganizer = this.shellTaskOrganizerProvider.get();
        PipAnimationController pipAnimationController = this.pipAnimationControllerProvider.get();
        TvPipBoundsAlgorithm tvPipBoundsAlgorithm = this.tvPipBoundsAlgorithmProvider.get();
        return new TvPipTransition(this.tvPipBoundsStateProvider.get(), this.pipMenuControllerProvider.get(), tvPipBoundsAlgorithm, pipAnimationController, this.transitionsProvider.get());
    }

    public TvPipModule_ProvideTvPipTransitionFactory(Provider<Transitions> provider, Provider<ShellTaskOrganizer> provider2, Provider<PipAnimationController> provider3, Provider<TvPipBoundsAlgorithm> provider4, Provider<TvPipBoundsState> provider5, Provider<TvPipMenuController> provider6) {
        this.transitionsProvider = provider;
        this.shellTaskOrganizerProvider = provider2;
        this.pipAnimationControllerProvider = provider3;
        this.tvPipBoundsAlgorithmProvider = provider4;
        this.tvPipBoundsStateProvider = provider5;
        this.pipMenuControllerProvider = provider6;
    }
}
