package com.android.p012wm.shell.dagger;

import android.content.Context;
import com.android.p012wm.shell.ShellTaskOrganizer;
import com.android.p012wm.shell.common.DisplayController;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreenController;
import com.android.p012wm.shell.pip.PipAnimationController;
import com.android.p012wm.shell.pip.PipSurfaceTransactionHelper;
import com.android.p012wm.shell.pip.PipTaskOrganizer;
import com.android.p012wm.shell.pip.PipTransitionController;
import com.android.p012wm.shell.pip.PipTransitionState;
import com.android.p012wm.shell.pip.PipUiEventLogger;
import com.android.p012wm.shell.pip.p013tv.TvPipBoundsAlgorithm;
import com.android.p012wm.shell.pip.p013tv.TvPipBoundsState;
import com.android.p012wm.shell.pip.p013tv.TvPipMenuController;
import com.android.p012wm.shell.splitscreen.SplitScreenController;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

/* renamed from: com.android.wm.shell.dagger.TvPipModule_ProvidePipTaskOrganizerFactory */
public final class TvPipModule_ProvidePipTaskOrganizerFactory implements Factory<PipTaskOrganizer> {
    public final Provider<Context> contextProvider;
    public final Provider<DisplayController> displayControllerProvider;
    public final Provider<ShellExecutor> mainExecutorProvider;
    public final Provider<Optional<SplitScreenController>> newSplitScreenOptionalProvider;
    public final Provider<PipAnimationController> pipAnimationControllerProvider;
    public final Provider<PipSurfaceTransactionHelper> pipSurfaceTransactionHelperProvider;
    public final Provider<PipTransitionController> pipTransitionControllerProvider;
    public final Provider<PipTransitionState> pipTransitionStateProvider;
    public final Provider<PipUiEventLogger> pipUiEventLoggerProvider;
    public final Provider<ShellTaskOrganizer> shellTaskOrganizerProvider;
    public final Provider<Optional<LegacySplitScreenController>> splitScreenOptionalProvider;
    public final Provider<SyncTransactionQueue> syncTransactionQueueProvider;
    public final Provider<TvPipBoundsAlgorithm> tvPipBoundsAlgorithmProvider;
    public final Provider<TvPipBoundsState> tvPipBoundsStateProvider;
    public final Provider<TvPipMenuController> tvPipMenuControllerProvider;

    public final Object get() {
        SyncTransactionQueue syncTransactionQueue = this.syncTransactionQueueProvider.get();
        TvPipBoundsState tvPipBoundsState = this.tvPipBoundsStateProvider.get();
        return new PipTaskOrganizer(this.contextProvider.get(), syncTransactionQueue, this.pipTransitionStateProvider.get(), tvPipBoundsState, this.tvPipBoundsAlgorithmProvider.get(), this.tvPipMenuControllerProvider.get(), this.pipAnimationControllerProvider.get(), this.pipSurfaceTransactionHelperProvider.get(), this.pipTransitionControllerProvider.get(), this.splitScreenOptionalProvider.get(), this.newSplitScreenOptionalProvider.get(), this.displayControllerProvider.get(), this.pipUiEventLoggerProvider.get(), this.shellTaskOrganizerProvider.get(), this.mainExecutorProvider.get());
    }

    public TvPipModule_ProvidePipTaskOrganizerFactory(Provider<Context> provider, Provider<TvPipMenuController> provider2, Provider<SyncTransactionQueue> provider3, Provider<TvPipBoundsState> provider4, Provider<PipTransitionState> provider5, Provider<TvPipBoundsAlgorithm> provider6, Provider<PipAnimationController> provider7, Provider<PipTransitionController> provider8, Provider<PipSurfaceTransactionHelper> provider9, Provider<Optional<LegacySplitScreenController>> provider10, Provider<Optional<SplitScreenController>> provider11, Provider<DisplayController> provider12, Provider<PipUiEventLogger> provider13, Provider<ShellTaskOrganizer> provider14, Provider<ShellExecutor> provider15) {
        this.contextProvider = provider;
        this.tvPipMenuControllerProvider = provider2;
        this.syncTransactionQueueProvider = provider3;
        this.tvPipBoundsStateProvider = provider4;
        this.pipTransitionStateProvider = provider5;
        this.tvPipBoundsAlgorithmProvider = provider6;
        this.pipAnimationControllerProvider = provider7;
        this.pipTransitionControllerProvider = provider8;
        this.pipSurfaceTransactionHelperProvider = provider9;
        this.splitScreenOptionalProvider = provider10;
        this.newSplitScreenOptionalProvider = provider11;
        this.displayControllerProvider = provider12;
        this.pipUiEventLoggerProvider = provider13;
        this.shellTaskOrganizerProvider = provider14;
        this.mainExecutorProvider = provider15;
    }
}
