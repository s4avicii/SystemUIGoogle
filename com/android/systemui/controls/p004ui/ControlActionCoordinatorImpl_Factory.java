package com.android.systemui.controls.p004ui;

import android.content.Context;
import com.android.p012wm.shell.TaskViewFactory;
import com.android.systemui.controls.ControlsMetricsLogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

/* renamed from: com.android.systemui.controls.ui.ControlActionCoordinatorImpl_Factory */
public final class ControlActionCoordinatorImpl_Factory implements Factory<ControlActionCoordinatorImpl> {
    public final Provider<ActivityStarter> activityStarterProvider;
    public final Provider<DelayableExecutor> bgExecutorProvider;
    public final Provider<Context> contextProvider;
    public final Provider<ControlsMetricsLogger> controlsMetricsLoggerProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<Optional<TaskViewFactory>> taskViewFactoryProvider;
    public final Provider<DelayableExecutor> uiExecutorProvider;
    public final Provider<VibratorHelper> vibratorProvider;

    public static ControlActionCoordinatorImpl_Factory create(Provider<Context> provider, Provider<DelayableExecutor> provider2, Provider<DelayableExecutor> provider3, Provider<ActivityStarter> provider4, Provider<KeyguardStateController> provider5, Provider<Optional<TaskViewFactory>> provider6, Provider<ControlsMetricsLogger> provider7, Provider<VibratorHelper> provider8) {
        return new ControlActionCoordinatorImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
    }

    public final Object get() {
        return new ControlActionCoordinatorImpl(this.contextProvider.get(), this.bgExecutorProvider.get(), this.uiExecutorProvider.get(), this.activityStarterProvider.get(), this.keyguardStateControllerProvider.get(), this.taskViewFactoryProvider.get(), this.controlsMetricsLoggerProvider.get(), this.vibratorProvider.get());
    }

    public ControlActionCoordinatorImpl_Factory(Provider<Context> provider, Provider<DelayableExecutor> provider2, Provider<DelayableExecutor> provider3, Provider<ActivityStarter> provider4, Provider<KeyguardStateController> provider5, Provider<Optional<TaskViewFactory>> provider6, Provider<ControlsMetricsLogger> provider7, Provider<VibratorHelper> provider8) {
        this.contextProvider = provider;
        this.bgExecutorProvider = provider2;
        this.uiExecutorProvider = provider3;
        this.activityStarterProvider = provider4;
        this.keyguardStateControllerProvider = provider5;
        this.taskViewFactoryProvider = provider6;
        this.controlsMetricsLoggerProvider = provider7;
        this.vibratorProvider = provider8;
    }
}
