package com.android.keyguard;

import android.media.AudioManager;
import android.telephony.TelephonyManager;
import com.android.keyguard.KeyguardSecurityContainerController;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class KeyguardHostViewController_Factory implements Factory<KeyguardHostViewController> {
    public final Provider<AudioManager> audioManagerProvider;
    public final Provider<KeyguardSecurityContainerController.Factory> keyguardSecurityContainerControllerFactoryProvider;
    public final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;
    public final Provider<TelephonyManager> telephonyManagerProvider;
    public final Provider<ViewMediatorCallback> viewMediatorCallbackProvider;
    public final Provider<KeyguardHostView> viewProvider;

    public static KeyguardHostViewController_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, KeyguardSecurityContainerController_Factory_Factory keyguardSecurityContainerController_Factory_Factory) {
        return new KeyguardHostViewController_Factory(provider, provider2, provider3, provider4, provider5, keyguardSecurityContainerController_Factory_Factory);
    }

    public final Object get() {
        return new KeyguardHostViewController(this.viewProvider.get(), this.keyguardUpdateMonitorProvider.get(), this.audioManagerProvider.get(), this.telephonyManagerProvider.get(), this.viewMediatorCallbackProvider.get(), this.keyguardSecurityContainerControllerFactoryProvider.get());
    }

    public KeyguardHostViewController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, KeyguardSecurityContainerController_Factory_Factory keyguardSecurityContainerController_Factory_Factory) {
        this.viewProvider = provider;
        this.keyguardUpdateMonitorProvider = provider2;
        this.audioManagerProvider = provider3;
        this.telephonyManagerProvider = provider4;
        this.viewMediatorCallbackProvider = provider5;
        this.keyguardSecurityContainerControllerFactoryProvider = keyguardSecurityContainerController_Factory_Factory;
    }
}
