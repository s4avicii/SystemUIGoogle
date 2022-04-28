package com.google.android.systemui.assist;

import android.content.Context;
import android.os.Handler;
import android.view.IWindowManager;
import com.android.internal.app.AssistUtils;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.assist.AssistLogger;
import com.android.systemui.assist.PhoneStateMonitor;
import com.android.systemui.assist.p003ui.DefaultUiController;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.google.android.systemui.assist.uihints.AssistantPresenceHandler;
import com.google.android.systemui.assist.uihints.GoogleDefaultUiController;
import com.google.android.systemui.assist.uihints.NgaMessageHandler;
import com.google.android.systemui.assist.uihints.NgaUiController;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class AssistManagerGoogle_Factory implements Factory<AssistManagerGoogle> {
    public final Provider<AssistLogger> assistLoggerProvider;
    public final Provider<AssistUtils> assistUtilsProvider;
    public final Provider<AssistantPresenceHandler> assistantPresenceHandlerProvider;
    public final Provider<CommandQueue> commandQueueProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DeviceProvisionedController> controllerProvider;
    public final Provider<DefaultUiController> defaultUiControllerProvider;
    public final Provider<GoogleDefaultUiController> googleDefaultUiControllerProvider;
    public final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;
    public final Provider<NavigationModeController> navigationModeControllerProvider;
    public final Provider<NgaMessageHandler> ngaMessageHandlerProvider;
    public final Provider<NgaUiController> ngaUiControllerProvider;
    public final Provider<OpaEnabledDispatcher> opaEnabledDispatcherProvider;
    public final Provider<OpaEnabledReceiver> opaEnabledReceiverProvider;
    public final Provider<OverviewProxyService> overviewProxyServiceProvider;
    public final Provider<PhoneStateMonitor> phoneStateMonitorProvider;
    public final Provider<SysUiState> sysUiStateProvider;
    public final Provider<Handler> uiHandlerProvider;
    public final Provider<IWindowManager> windowManagerServiceProvider;

    public AssistManagerGoogle_Factory(Provider<DeviceProvisionedController> provider, Provider<Context> provider2, Provider<AssistUtils> provider3, Provider<NgaUiController> provider4, Provider<CommandQueue> provider5, Provider<OpaEnabledReceiver> provider6, Provider<PhoneStateMonitor> provider7, Provider<OverviewProxyService> provider8, Provider<OpaEnabledDispatcher> provider9, Provider<KeyguardUpdateMonitor> provider10, Provider<NavigationModeController> provider11, Provider<AssistantPresenceHandler> provider12, Provider<NgaMessageHandler> provider13, Provider<SysUiState> provider14, Provider<Handler> provider15, Provider<DefaultUiController> provider16, Provider<GoogleDefaultUiController> provider17, Provider<IWindowManager> provider18, Provider<AssistLogger> provider19) {
        this.controllerProvider = provider;
        this.contextProvider = provider2;
        this.assistUtilsProvider = provider3;
        this.ngaUiControllerProvider = provider4;
        this.commandQueueProvider = provider5;
        this.opaEnabledReceiverProvider = provider6;
        this.phoneStateMonitorProvider = provider7;
        this.overviewProxyServiceProvider = provider8;
        this.opaEnabledDispatcherProvider = provider9;
        this.keyguardUpdateMonitorProvider = provider10;
        this.navigationModeControllerProvider = provider11;
        this.assistantPresenceHandlerProvider = provider12;
        this.ngaMessageHandlerProvider = provider13;
        this.sysUiStateProvider = provider14;
        this.uiHandlerProvider = provider15;
        this.defaultUiControllerProvider = provider16;
        this.googleDefaultUiControllerProvider = provider17;
        this.windowManagerServiceProvider = provider18;
        this.assistLoggerProvider = provider19;
    }

    public static AssistManagerGoogle_Factory create(Provider<DeviceProvisionedController> provider, Provider<Context> provider2, Provider<AssistUtils> provider3, Provider<NgaUiController> provider4, Provider<CommandQueue> provider5, Provider<OpaEnabledReceiver> provider6, Provider<PhoneStateMonitor> provider7, Provider<OverviewProxyService> provider8, Provider<OpaEnabledDispatcher> provider9, Provider<KeyguardUpdateMonitor> provider10, Provider<NavigationModeController> provider11, Provider<AssistantPresenceHandler> provider12, Provider<NgaMessageHandler> provider13, Provider<SysUiState> provider14, Provider<Handler> provider15, Provider<DefaultUiController> provider16, Provider<GoogleDefaultUiController> provider17, Provider<IWindowManager> provider18, Provider<AssistLogger> provider19) {
        return new AssistManagerGoogle_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17, provider18, provider19);
    }

    public final Object get() {
        return new AssistManagerGoogle(this.controllerProvider.get(), this.contextProvider.get(), this.assistUtilsProvider.get(), this.ngaUiControllerProvider.get(), this.commandQueueProvider.get(), this.opaEnabledReceiverProvider.get(), this.phoneStateMonitorProvider.get(), this.overviewProxyServiceProvider.get(), this.opaEnabledDispatcherProvider.get(), this.keyguardUpdateMonitorProvider.get(), this.navigationModeControllerProvider.get(), this.assistantPresenceHandlerProvider.get(), this.ngaMessageHandlerProvider.get(), DoubleCheck.lazy(this.sysUiStateProvider), this.uiHandlerProvider.get(), this.defaultUiControllerProvider.get(), this.googleDefaultUiControllerProvider.get(), this.windowManagerServiceProvider.get(), this.assistLoggerProvider.get());
    }
}
