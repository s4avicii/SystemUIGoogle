package com.android.systemui.statusbar.phone;

import android.app.IActivityManager;
import android.content.Context;
import android.view.WindowManager;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.internal.DelegateFactory;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class NotificationShadeWindowControllerImpl_Factory implements Factory<NotificationShadeWindowControllerImpl> {
    public final Provider<IActivityManager> activityManagerProvider;
    public final Provider<AuthController> authControllerProvider;
    public final Provider<SysuiColorExtractor> colorExtractorProvider;
    public final Provider<ConfigurationController> configurationControllerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DozeParameters> dozeParametersProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<KeyguardBypassController> keyguardBypassControllerProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<KeyguardViewMediator> keyguardViewMediatorProvider;
    public final Provider<ScreenOffAnimationController> screenOffAnimationControllerProvider;
    public final Provider<StatusBarStateController> statusBarStateControllerProvider;
    public final Provider<WindowManager> windowManagerProvider;

    public static NotificationShadeWindowControllerImpl_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, DelegateFactory delegateFactory) {
        return new NotificationShadeWindowControllerImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, delegateFactory);
    }

    public final Object get() {
        return new NotificationShadeWindowControllerImpl(this.contextProvider.get(), this.windowManagerProvider.get(), this.activityManagerProvider.get(), this.dozeParametersProvider.get(), this.statusBarStateControllerProvider.get(), this.configurationControllerProvider.get(), this.keyguardViewMediatorProvider.get(), this.keyguardBypassControllerProvider.get(), this.colorExtractorProvider.get(), this.dumpManagerProvider.get(), this.keyguardStateControllerProvider.get(), this.screenOffAnimationControllerProvider.get(), this.authControllerProvider.get());
    }

    public NotificationShadeWindowControllerImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, DelegateFactory delegateFactory) {
        this.contextProvider = provider;
        this.windowManagerProvider = provider2;
        this.activityManagerProvider = provider3;
        this.dozeParametersProvider = provider4;
        this.statusBarStateControllerProvider = provider5;
        this.configurationControllerProvider = provider6;
        this.keyguardViewMediatorProvider = provider7;
        this.keyguardBypassControllerProvider = provider8;
        this.colorExtractorProvider = provider9;
        this.dumpManagerProvider = provider10;
        this.keyguardStateControllerProvider = provider11;
        this.screenOffAnimationControllerProvider = provider12;
        this.authControllerProvider = delegateFactory;
    }
}
