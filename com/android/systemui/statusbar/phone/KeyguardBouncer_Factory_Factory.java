package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.os.Handler;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.ViewMediatorCallback;
import com.android.keyguard.dagger.KeyguardBouncerComponent;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.keyguard.DismissCallbackRegistry;
import com.android.systemui.statusbar.phone.KeyguardBouncer;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class KeyguardBouncer_Factory_Factory implements Factory<KeyguardBouncer.Factory> {
    public final Provider<ViewMediatorCallback> callbackProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DismissCallbackRegistry> dismissCallbackRegistryProvider;
    public final Provider<FalsingCollector> falsingCollectorProvider;
    public final Provider<Handler> handlerProvider;
    public final Provider<KeyguardBouncerComponent.Factory> keyguardBouncerComponentFactoryProvider;
    public final Provider<KeyguardBypassController> keyguardBypassControllerProvider;
    public final Provider<KeyguardSecurityModel> keyguardSecurityModelProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;

    public static KeyguardBouncer_Factory_Factory create(Provider<Context> provider, Provider<ViewMediatorCallback> provider2, Provider<DismissCallbackRegistry> provider3, Provider<FalsingCollector> provider4, Provider<KeyguardStateController> provider5, Provider<KeyguardUpdateMonitor> provider6, Provider<KeyguardBypassController> provider7, Provider<Handler> provider8, Provider<KeyguardSecurityModel> provider9, Provider<KeyguardBouncerComponent.Factory> provider10) {
        return new KeyguardBouncer_Factory_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10);
    }

    public final Object get() {
        return new KeyguardBouncer.Factory(this.contextProvider.get(), this.callbackProvider.get(), this.dismissCallbackRegistryProvider.get(), this.falsingCollectorProvider.get(), this.keyguardStateControllerProvider.get(), this.keyguardUpdateMonitorProvider.get(), this.keyguardBypassControllerProvider.get(), this.handlerProvider.get(), this.keyguardSecurityModelProvider.get(), this.keyguardBouncerComponentFactoryProvider.get());
    }

    public KeyguardBouncer_Factory_Factory(Provider<Context> provider, Provider<ViewMediatorCallback> provider2, Provider<DismissCallbackRegistry> provider3, Provider<FalsingCollector> provider4, Provider<KeyguardStateController> provider5, Provider<KeyguardUpdateMonitor> provider6, Provider<KeyguardBypassController> provider7, Provider<Handler> provider8, Provider<KeyguardSecurityModel> provider9, Provider<KeyguardBouncerComponent.Factory> provider10) {
        this.contextProvider = provider;
        this.callbackProvider = provider2;
        this.dismissCallbackRegistryProvider = provider3;
        this.falsingCollectorProvider = provider4;
        this.keyguardStateControllerProvider = provider5;
        this.keyguardUpdateMonitorProvider = provider6;
        this.keyguardBypassControllerProvider = provider7;
        this.handlerProvider = provider8;
        this.keyguardSecurityModelProvider = provider9;
        this.keyguardBouncerComponentFactoryProvider = provider10;
    }
}
