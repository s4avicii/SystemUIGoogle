package com.android.p012wm.shell.dagger;

import android.content.Context;
import android.content.om.IOverlayManager;
import android.os.Handler;
import android.os.ServiceManager;
import android.view.WindowManager;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.UiEventLogger;
import com.android.p012wm.shell.common.DisplayController;
import com.android.p012wm.shell.common.DisplayLayout;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.TaskStackListenerImpl;
import com.android.p012wm.shell.onehanded.BackgroundWindowManager;
import com.android.p012wm.shell.onehanded.OneHandedAccessibilityUtil;
import com.android.p012wm.shell.onehanded.OneHandedAnimationController;
import com.android.p012wm.shell.onehanded.OneHandedController;
import com.android.p012wm.shell.onehanded.OneHandedDisplayAreaOrganizer;
import com.android.p012wm.shell.onehanded.OneHandedSettingsUtil;
import com.android.p012wm.shell.onehanded.OneHandedState;
import com.android.p012wm.shell.onehanded.OneHandedTimeoutHandler;
import com.android.p012wm.shell.onehanded.OneHandedTouchHandler;
import com.android.p012wm.shell.onehanded.OneHandedTutorialHandler;
import com.android.p012wm.shell.onehanded.OneHandedUiEventLogger;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.wm.shell.dagger.WMShellModule_ProvideOneHandedControllerFactory */
public final class WMShellModule_ProvideOneHandedControllerFactory implements Factory<OneHandedController> {
    public final Provider<Context> contextProvider;
    public final Provider<DisplayController> displayControllerProvider;
    public final Provider<DisplayLayout> displayLayoutProvider;
    public final Provider<InteractionJankMonitor> jankMonitorProvider;
    public final Provider<ShellExecutor> mainExecutorProvider;
    public final Provider<Handler> mainHandlerProvider;
    public final Provider<TaskStackListenerImpl> taskStackListenerProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;
    public final Provider<WindowManager> windowManagerProvider;

    public static WMShellModule_ProvideOneHandedControllerFactory create(Provider<Context> provider, Provider<WindowManager> provider2, Provider<DisplayController> provider3, Provider<DisplayLayout> provider4, Provider<TaskStackListenerImpl> provider5, Provider<UiEventLogger> provider6, Provider<InteractionJankMonitor> provider7, Provider<ShellExecutor> provider8, Provider<Handler> provider9) {
        return new WMShellModule_ProvideOneHandedControllerFactory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9);
    }

    public final Object get() {
        Context context = this.contextProvider.get();
        InteractionJankMonitor interactionJankMonitor = this.jankMonitorProvider.get();
        ShellExecutor shellExecutor = this.mainExecutorProvider.get();
        OneHandedSettingsUtil oneHandedSettingsUtil = new OneHandedSettingsUtil();
        OneHandedAccessibilityUtil oneHandedAccessibilityUtil = new OneHandedAccessibilityUtil(context);
        OneHandedTimeoutHandler oneHandedTimeoutHandler = new OneHandedTimeoutHandler(shellExecutor);
        OneHandedState oneHandedState = new OneHandedState();
        OneHandedTutorialHandler oneHandedTutorialHandler = new OneHandedTutorialHandler(context, oneHandedSettingsUtil, this.windowManagerProvider.get(), new BackgroundWindowManager(context));
        OneHandedAnimationController oneHandedAnimationController = new OneHandedAnimationController(context);
        Context context2 = context;
        OneHandedTouchHandler oneHandedTouchHandler = new OneHandedTouchHandler(oneHandedTimeoutHandler, shellExecutor);
        OneHandedTutorialHandler oneHandedTutorialHandler2 = oneHandedTutorialHandler;
        OneHandedDisplayAreaOrganizer oneHandedDisplayAreaOrganizer = new OneHandedDisplayAreaOrganizer(context2, this.displayLayoutProvider.get(), oneHandedAnimationController, oneHandedTutorialHandler, interactionJankMonitor, shellExecutor);
        OneHandedUiEventLogger oneHandedUiEventLogger = new OneHandedUiEventLogger(this.uiEventLoggerProvider.get());
        DisplayController displayController = this.displayControllerProvider.get();
        OneHandedUiEventLogger oneHandedUiEventLogger2 = oneHandedUiEventLogger;
        OneHandedSettingsUtil oneHandedSettingsUtil2 = oneHandedSettingsUtil;
        ShellExecutor shellExecutor2 = shellExecutor;
        return new OneHandedController(context2, displayController, oneHandedDisplayAreaOrganizer, oneHandedTouchHandler, oneHandedTutorialHandler2, oneHandedSettingsUtil2, oneHandedAccessibilityUtil, oneHandedTimeoutHandler, oneHandedState, interactionJankMonitor, oneHandedUiEventLogger2, IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay")), this.taskStackListenerProvider.get(), shellExecutor2, this.mainHandlerProvider.get());
    }

    public WMShellModule_ProvideOneHandedControllerFactory(Provider<Context> provider, Provider<WindowManager> provider2, Provider<DisplayController> provider3, Provider<DisplayLayout> provider4, Provider<TaskStackListenerImpl> provider5, Provider<UiEventLogger> provider6, Provider<InteractionJankMonitor> provider7, Provider<ShellExecutor> provider8, Provider<Handler> provider9) {
        this.contextProvider = provider;
        this.windowManagerProvider = provider2;
        this.displayControllerProvider = provider3;
        this.displayLayoutProvider = provider4;
        this.taskStackListenerProvider = provider5;
        this.uiEventLoggerProvider = provider6;
        this.jankMonitorProvider = provider7;
        this.mainExecutorProvider = provider8;
        this.mainHandlerProvider = provider9;
    }
}
