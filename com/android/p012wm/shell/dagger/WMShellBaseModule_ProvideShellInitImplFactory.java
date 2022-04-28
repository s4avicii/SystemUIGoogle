package com.android.p012wm.shell.dagger;

import com.android.p012wm.shell.ShellInitImpl;
import com.android.p012wm.shell.ShellTaskOrganizer;
import com.android.p012wm.shell.apppairs.AppPairsController;
import com.android.p012wm.shell.bubbles.BubbleController;
import com.android.p012wm.shell.common.DisplayController;
import com.android.p012wm.shell.common.DisplayImeController;
import com.android.p012wm.shell.common.DisplayInsetsController;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.draganddrop.DragAndDropController;
import com.android.p012wm.shell.freeform.FreeformTaskListener;
import com.android.p012wm.shell.fullscreen.FullscreenTaskListener;
import com.android.p012wm.shell.fullscreen.FullscreenUnfoldController;
import com.android.p012wm.shell.pip.phone.PipTouchHandler;
import com.android.p012wm.shell.recents.RecentTasksController;
import com.android.p012wm.shell.splitscreen.SplitScreenController;
import com.android.p012wm.shell.startingsurface.StartingWindowController;
import com.android.p012wm.shell.transition.Transitions;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

/* renamed from: com.android.wm.shell.dagger.WMShellBaseModule_ProvideShellInitImplFactory */
public final class WMShellBaseModule_ProvideShellInitImplFactory implements Factory<ShellInitImpl> {
    public final Provider<Optional<AppPairsController>> appPairsOptionalProvider;
    public final Provider<Optional<FullscreenUnfoldController>> appUnfoldTransitionControllerProvider;
    public final Provider<Optional<BubbleController>> bubblesOptionalProvider;
    public final Provider<DisplayController> displayControllerProvider;
    public final Provider<DisplayImeController> displayImeControllerProvider;
    public final Provider<DisplayInsetsController> displayInsetsControllerProvider;
    public final Provider<DragAndDropController> dragAndDropControllerProvider;
    public final Provider<Optional<FreeformTaskListener>> freeformTaskListenerProvider;
    public final Provider<FullscreenTaskListener> fullscreenTaskListenerProvider;
    public final Provider<ShellExecutor> mainExecutorProvider;
    public final Provider<Optional<PipTouchHandler>> pipTouchHandlerOptionalProvider;
    public final Provider<Optional<RecentTasksController>> recentTasksOptionalProvider;
    public final Provider<ShellTaskOrganizer> shellTaskOrganizerProvider;
    public final Provider<Optional<SplitScreenController>> splitScreenOptionalProvider;
    public final Provider<StartingWindowController> startingWindowProvider;
    public final Provider<Transitions> transitionsProvider;

    public WMShellBaseModule_ProvideShellInitImplFactory(Provider<DisplayController> provider, Provider<DisplayImeController> provider2, Provider<DisplayInsetsController> provider3, Provider<DragAndDropController> provider4, Provider<ShellTaskOrganizer> provider5, Provider<Optional<BubbleController>> provider6, Provider<Optional<SplitScreenController>> provider7, Provider<Optional<AppPairsController>> provider8, Provider<Optional<PipTouchHandler>> provider9, Provider<FullscreenTaskListener> provider10, Provider<Optional<FullscreenUnfoldController>> provider11, Provider<Optional<FreeformTaskListener>> provider12, Provider<Optional<RecentTasksController>> provider13, Provider<Transitions> provider14, Provider<StartingWindowController> provider15, Provider<ShellExecutor> provider16) {
        this.displayControllerProvider = provider;
        this.displayImeControllerProvider = provider2;
        this.displayInsetsControllerProvider = provider3;
        this.dragAndDropControllerProvider = provider4;
        this.shellTaskOrganizerProvider = provider5;
        this.bubblesOptionalProvider = provider6;
        this.splitScreenOptionalProvider = provider7;
        this.appPairsOptionalProvider = provider8;
        this.pipTouchHandlerOptionalProvider = provider9;
        this.fullscreenTaskListenerProvider = provider10;
        this.appUnfoldTransitionControllerProvider = provider11;
        this.freeformTaskListenerProvider = provider12;
        this.recentTasksOptionalProvider = provider13;
        this.transitionsProvider = provider14;
        this.startingWindowProvider = provider15;
        this.mainExecutorProvider = provider16;
    }

    public static WMShellBaseModule_ProvideShellInitImplFactory create(Provider<DisplayController> provider, Provider<DisplayImeController> provider2, Provider<DisplayInsetsController> provider3, Provider<DragAndDropController> provider4, Provider<ShellTaskOrganizer> provider5, Provider<Optional<BubbleController>> provider6, Provider<Optional<SplitScreenController>> provider7, Provider<Optional<AppPairsController>> provider8, Provider<Optional<PipTouchHandler>> provider9, Provider<FullscreenTaskListener> provider10, Provider<Optional<FullscreenUnfoldController>> provider11, Provider<Optional<FreeformTaskListener>> provider12, Provider<Optional<RecentTasksController>> provider13, Provider<Transitions> provider14, Provider<StartingWindowController> provider15, Provider<ShellExecutor> provider16) {
        return new WMShellBaseModule_ProvideShellInitImplFactory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16);
    }

    public final Object get() {
        return new ShellInitImpl(this.displayControllerProvider.get(), this.displayImeControllerProvider.get(), this.displayInsetsControllerProvider.get(), this.dragAndDropControllerProvider.get(), this.shellTaskOrganizerProvider.get(), this.bubblesOptionalProvider.get(), this.splitScreenOptionalProvider.get(), this.appPairsOptionalProvider.get(), this.pipTouchHandlerOptionalProvider.get(), this.fullscreenTaskListenerProvider.get(), this.appUnfoldTransitionControllerProvider.get(), this.freeformTaskListenerProvider.get(), this.recentTasksOptionalProvider.get(), this.transitionsProvider.get(), this.startingWindowProvider.get(), this.mainExecutorProvider.get());
    }
}
