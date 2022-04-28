package com.android.systemui.assist;

import android.content.Context;
import android.os.Handler;
import com.android.internal.app.AssistUtils;
import com.android.systemui.assist.p003ui.DefaultUiController;
import com.android.systemui.model.SysUiState;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class AssistManager_Factory implements Factory<AssistManager> {
    public final Provider<AssistLogger> assistLoggerProvider;
    public final Provider<AssistUtils> assistUtilsProvider;
    public final Provider<CommandQueue> commandQueueProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DeviceProvisionedController> controllerProvider;
    public final Provider<DefaultUiController> defaultUiControllerProvider;
    public final Provider<OverviewProxyService> overviewProxyServiceProvider;
    public final Provider<PhoneStateMonitor> phoneStateMonitorProvider;
    public final Provider<SysUiState> sysUiStateProvider;
    public final Provider<Handler> uiHandlerProvider;

    public final Object get() {
        return new AssistManager(this.controllerProvider.get(), this.contextProvider.get(), this.assistUtilsProvider.get(), this.commandQueueProvider.get(), this.phoneStateMonitorProvider.get(), this.overviewProxyServiceProvider.get(), DoubleCheck.lazy(this.sysUiStateProvider), this.defaultUiControllerProvider.get(), this.assistLoggerProvider.get(), this.uiHandlerProvider.get());
    }

    public AssistManager_Factory(Provider<DeviceProvisionedController> provider, Provider<Context> provider2, Provider<AssistUtils> provider3, Provider<CommandQueue> provider4, Provider<PhoneStateMonitor> provider5, Provider<OverviewProxyService> provider6, Provider<SysUiState> provider7, Provider<DefaultUiController> provider8, Provider<AssistLogger> provider9, Provider<Handler> provider10) {
        this.controllerProvider = provider;
        this.contextProvider = provider2;
        this.assistUtilsProvider = provider3;
        this.commandQueueProvider = provider4;
        this.phoneStateMonitorProvider = provider5;
        this.overviewProxyServiceProvider = provider6;
        this.sysUiStateProvider = provider7;
        this.defaultUiControllerProvider = provider8;
        this.assistLoggerProvider = provider9;
        this.uiHandlerProvider = provider10;
    }
}
