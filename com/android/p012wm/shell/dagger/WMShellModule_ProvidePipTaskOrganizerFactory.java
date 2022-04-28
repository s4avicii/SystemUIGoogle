package com.android.p012wm.shell.dagger;

import android.content.Context;
import com.android.p012wm.shell.ShellTaskOrganizer;
import com.android.p012wm.shell.common.DisplayController;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreenController;
import com.android.p012wm.shell.pip.PipAnimationController;
import com.android.p012wm.shell.pip.PipBoundsAlgorithm;
import com.android.p012wm.shell.pip.PipBoundsState;
import com.android.p012wm.shell.pip.PipSurfaceTransactionHelper;
import com.android.p012wm.shell.pip.PipTaskOrganizer;
import com.android.p012wm.shell.pip.PipTransitionController;
import com.android.p012wm.shell.pip.PipTransitionState;
import com.android.p012wm.shell.pip.PipUiEventLogger;
import com.android.p012wm.shell.pip.phone.PhonePipMenuController;
import com.android.p012wm.shell.splitscreen.SplitScreenController;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

/* renamed from: com.android.wm.shell.dagger.WMShellModule_ProvidePipTaskOrganizerFactory */
public final class WMShellModule_ProvidePipTaskOrganizerFactory implements Factory<PipTaskOrganizer> {
    public final Provider<Context> contextProvider;
    public final Provider<DisplayController> displayControllerProvider;
    public final Provider<ShellExecutor> mainExecutorProvider;
    public final Provider<PhonePipMenuController> menuPhoneControllerProvider;
    public final Provider<Optional<SplitScreenController>> newSplitScreenOptionalProvider;
    public final Provider<PipAnimationController> pipAnimationControllerProvider;
    public final Provider<PipBoundsAlgorithm> pipBoundsAlgorithmProvider;
    public final Provider<PipBoundsState> pipBoundsStateProvider;
    public final Provider<PipSurfaceTransactionHelper> pipSurfaceTransactionHelperProvider;
    public final Provider<PipTransitionController> pipTransitionControllerProvider;
    public final Provider<PipTransitionState> pipTransitionStateProvider;
    public final Provider<PipUiEventLogger> pipUiEventLoggerProvider;
    public final Provider<ShellTaskOrganizer> shellTaskOrganizerProvider;
    public final Provider<Optional<LegacySplitScreenController>> splitScreenOptionalProvider;
    public final Provider<SyncTransactionQueue> syncTransactionQueueProvider;

    public static WMShellModule_ProvidePipTaskOrganizerFactory create(Provider<Context> provider, Provider<SyncTransactionQueue> provider2, Provider<PipTransitionState> provider3, Provider<PipBoundsState> provider4, Provider<PipBoundsAlgorithm> provider5, Provider<PhonePipMenuController> provider6, Provider<PipAnimationController> provider7, Provider<PipSurfaceTransactionHelper> provider8, Provider<PipTransitionController> provider9, Provider<Optional<LegacySplitScreenController>> provider10, Provider<Optional<SplitScreenController>> provider11, Provider<DisplayController> provider12, Provider<PipUiEventLogger> provider13, Provider<ShellTaskOrganizer> provider14, Provider<ShellExecutor> provider15) {
        return new WMShellModule_ProvidePipTaskOrganizerFactory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15);
    }

    public final Object get() {
        return new PipTaskOrganizer(this.contextProvider.get(), this.syncTransactionQueueProvider.get(), this.pipTransitionStateProvider.get(), this.pipBoundsStateProvider.get(), this.pipBoundsAlgorithmProvider.get(), this.menuPhoneControllerProvider.get(), this.pipAnimationControllerProvider.get(), this.pipSurfaceTransactionHelperProvider.get(), this.pipTransitionControllerProvider.get(), this.splitScreenOptionalProvider.get(), this.newSplitScreenOptionalProvider.get(), this.displayControllerProvider.get(), this.pipUiEventLoggerProvider.get(), this.shellTaskOrganizerProvider.get(), this.mainExecutorProvider.get());
    }

    public WMShellModule_ProvidePipTaskOrganizerFactory(Provider<Context> provider, Provider<SyncTransactionQueue> provider2, Provider<PipTransitionState> provider3, Provider<PipBoundsState> provider4, Provider<PipBoundsAlgorithm> provider5, Provider<PhonePipMenuController> provider6, Provider<PipAnimationController> provider7, Provider<PipSurfaceTransactionHelper> provider8, Provider<PipTransitionController> provider9, Provider<Optional<LegacySplitScreenController>> provider10, Provider<Optional<SplitScreenController>> provider11, Provider<DisplayController> provider12, Provider<PipUiEventLogger> provider13, Provider<ShellTaskOrganizer> provider14, Provider<ShellExecutor> provider15) {
        this.contextProvider = provider;
        this.syncTransactionQueueProvider = provider2;
        this.pipTransitionStateProvider = provider3;
        this.pipBoundsStateProvider = provider4;
        this.pipBoundsAlgorithmProvider = provider5;
        this.menuPhoneControllerProvider = provider6;
        this.pipAnimationControllerProvider = provider7;
        this.pipSurfaceTransactionHelperProvider = provider8;
        this.pipTransitionControllerProvider = provider9;
        this.splitScreenOptionalProvider = provider10;
        this.newSplitScreenOptionalProvider = provider11;
        this.displayControllerProvider = provider12;
        this.pipUiEventLoggerProvider = provider13;
        this.shellTaskOrganizerProvider = provider14;
        this.mainExecutorProvider = provider15;
    }
}
