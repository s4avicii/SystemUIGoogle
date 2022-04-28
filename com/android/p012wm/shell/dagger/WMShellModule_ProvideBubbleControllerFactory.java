package com.android.p012wm.shell.dagger;

import android.content.Context;
import android.content.pm.LauncherApps;
import android.os.Handler;
import android.view.WindowManager;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.statusbar.IStatusBarService;
import com.android.p012wm.shell.ShellTaskOrganizer;
import com.android.p012wm.shell.TaskViewTransitions;
import com.android.p012wm.shell.WindowManagerShellWrapper;
import com.android.p012wm.shell.bubbles.BubbleController;
import com.android.p012wm.shell.bubbles.BubbleData;
import com.android.p012wm.shell.bubbles.BubbleDataRepository;
import com.android.p012wm.shell.bubbles.BubbleLogger;
import com.android.p012wm.shell.bubbles.BubblePositioner;
import com.android.p012wm.shell.bubbles.BubbleStackView;
import com.android.p012wm.shell.common.DisplayController;
import com.android.p012wm.shell.common.FloatingContentCoordinator;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import com.android.p012wm.shell.common.TaskStackListenerImpl;
import com.android.p012wm.shell.onehanded.OneHandedController;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

/* renamed from: com.android.wm.shell.dagger.WMShellModule_ProvideBubbleControllerFactory */
public final class WMShellModule_ProvideBubbleControllerFactory implements Factory<BubbleController> {
    public final Provider<Context> contextProvider;
    public final Provider<DisplayController> displayControllerProvider;
    public final Provider<FloatingContentCoordinator> floatingContentCoordinatorProvider;
    public final Provider<LauncherApps> launcherAppsProvider;
    public final Provider<ShellExecutor> mainExecutorProvider;
    public final Provider<Handler> mainHandlerProvider;
    public final Provider<Optional<OneHandedController>> oneHandedOptionalProvider;
    public final Provider<ShellTaskOrganizer> organizerProvider;
    public final Provider<IStatusBarService> statusBarServiceProvider;
    public final Provider<SyncTransactionQueue> syncQueueProvider;
    public final Provider<TaskStackListenerImpl> taskStackListenerProvider;
    public final Provider<TaskViewTransitions> taskViewTransitionsProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;
    public final Provider<WindowManager> windowManagerProvider;
    public final Provider<WindowManagerShellWrapper> windowManagerShellWrapperProvider;

    public static WMShellModule_ProvideBubbleControllerFactory create(Provider<Context> provider, Provider<FloatingContentCoordinator> provider2, Provider<IStatusBarService> provider3, Provider<WindowManager> provider4, Provider<WindowManagerShellWrapper> provider5, Provider<LauncherApps> provider6, Provider<TaskStackListenerImpl> provider7, Provider<UiEventLogger> provider8, Provider<ShellTaskOrganizer> provider9, Provider<DisplayController> provider10, Provider<Optional<OneHandedController>> provider11, Provider<ShellExecutor> provider12, Provider<Handler> provider13, Provider<TaskViewTransitions> provider14, Provider<SyncTransactionQueue> provider15) {
        return new WMShellModule_ProvideBubbleControllerFactory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15);
    }

    public final Object get() {
        Context context = this.contextProvider.get();
        WindowManager windowManager = this.windowManagerProvider.get();
        LauncherApps launcherApps = this.launcherAppsProvider.get();
        ShellExecutor shellExecutor = this.mainExecutorProvider.get();
        BubbleLogger bubbleLogger = r0;
        BubbleLogger bubbleLogger2 = new BubbleLogger(this.uiEventLoggerProvider.get());
        BubblePositioner bubblePositioner = r4;
        BubblePositioner bubblePositioner2 = new BubblePositioner(context, windowManager);
        BubblePositioner bubblePositioner3 = bubblePositioner2;
        BubbleData bubbleData = r2;
        BubbleData bubbleData2 = new BubbleData(context, bubbleLogger2, bubblePositioner3, shellExecutor);
        BubbleController bubbleController = r2;
        ShellExecutor shellExecutor2 = shellExecutor;
        BubbleDataRepository bubbleDataRepository = r3;
        BubbleDataRepository bubbleDataRepository2 = new BubbleDataRepository(context, launcherApps, shellExecutor2);
        BubbleController bubbleController2 = new BubbleController(context, bubbleData, (BubbleStackView.SurfaceSynchronizer) null, this.floatingContentCoordinatorProvider.get(), bubbleDataRepository, this.statusBarServiceProvider.get(), windowManager, this.windowManagerShellWrapperProvider.get(), launcherApps, bubbleLogger, this.taskStackListenerProvider.get(), this.organizerProvider.get(), bubblePositioner, this.displayControllerProvider.get(), this.oneHandedOptionalProvider.get(), shellExecutor, this.mainHandlerProvider.get(), this.taskViewTransitionsProvider.get(), this.syncQueueProvider.get());
        return bubbleController;
    }

    public WMShellModule_ProvideBubbleControllerFactory(Provider<Context> provider, Provider<FloatingContentCoordinator> provider2, Provider<IStatusBarService> provider3, Provider<WindowManager> provider4, Provider<WindowManagerShellWrapper> provider5, Provider<LauncherApps> provider6, Provider<TaskStackListenerImpl> provider7, Provider<UiEventLogger> provider8, Provider<ShellTaskOrganizer> provider9, Provider<DisplayController> provider10, Provider<Optional<OneHandedController>> provider11, Provider<ShellExecutor> provider12, Provider<Handler> provider13, Provider<TaskViewTransitions> provider14, Provider<SyncTransactionQueue> provider15) {
        this.contextProvider = provider;
        this.floatingContentCoordinatorProvider = provider2;
        this.statusBarServiceProvider = provider3;
        this.windowManagerProvider = provider4;
        this.windowManagerShellWrapperProvider = provider5;
        this.launcherAppsProvider = provider6;
        this.taskStackListenerProvider = provider7;
        this.uiEventLoggerProvider = provider8;
        this.organizerProvider = provider9;
        this.displayControllerProvider = provider10;
        this.oneHandedOptionalProvider = provider11;
        this.mainExecutorProvider = provider12;
        this.mainHandlerProvider = provider13;
        this.taskViewTransitionsProvider = provider14;
        this.syncQueueProvider = provider15;
    }
}
