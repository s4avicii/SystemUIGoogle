package com.google.android.systemui.gamedashboard;

import android.content.Context;
import android.os.Handler;
import android.view.WindowManager;
import com.android.p012wm.shell.tasksurfacehelper.TaskSurfaceHelper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

public final class ShortcutBarController_Factory implements Factory<ShortcutBarController> {
    public final Provider<ConfigurationController> configurationControllerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<FpsController> fpsControllerProvider;
    public final Provider<ScreenRecordController> screenRecordControllerProvider;
    public final Provider<Optional<TaskSurfaceHelper>> screenshotControllerProvider;
    public final Provider<Handler> screenshotHandlerProvider;
    public final Provider<ToastController> toastProvider;
    public final Provider<GameDashboardUiEventLogger> uiEventLoggerProvider;
    public final Provider<WindowManager> windowManagerProvider;

    public final Object get() {
        return new ShortcutBarController(this.contextProvider.get(), this.windowManagerProvider.get(), this.fpsControllerProvider.get(), this.configurationControllerProvider.get(), this.screenshotHandlerProvider.get(), this.screenRecordControllerProvider.get(), this.screenshotControllerProvider.get(), this.uiEventLoggerProvider.get(), this.toastProvider.get());
    }

    public ShortcutBarController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, GameDashboardUiEventLogger_Factory gameDashboardUiEventLogger_Factory, Provider provider8) {
        this.contextProvider = provider;
        this.windowManagerProvider = provider2;
        this.fpsControllerProvider = provider3;
        this.configurationControllerProvider = provider4;
        this.screenshotHandlerProvider = provider5;
        this.screenRecordControllerProvider = provider6;
        this.screenshotControllerProvider = provider7;
        this.uiEventLoggerProvider = gameDashboardUiEventLogger_Factory;
        this.toastProvider = provider8;
    }
}
