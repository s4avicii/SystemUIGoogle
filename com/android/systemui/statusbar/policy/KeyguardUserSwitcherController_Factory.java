package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.communal.CommunalStateController;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import dagger.internal.Factory;
import dagger.internal.InstanceFactory;
import javax.inject.Provider;

public final class KeyguardUserSwitcherController_Factory implements Factory<KeyguardUserSwitcherController> {
    public final Provider<CommunalStateController> communalStateControllerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DozeParameters> dozeParametersProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;
    public final Provider<KeyguardUserSwitcherView> keyguardUserSwitcherViewProvider;
    public final Provider<LayoutInflater> layoutInflaterProvider;
    public final Provider<Resources> resourcesProvider;
    public final Provider<ScreenLifecycle> screenLifecycleProvider;
    public final Provider<ScreenOffAnimationController> screenOffAnimationControllerProvider;
    public final Provider<SysuiStatusBarStateController> statusBarStateControllerProvider;
    public final Provider<UserSwitcherController> userSwitcherControllerProvider;

    public static KeyguardUserSwitcherController_Factory create(InstanceFactory instanceFactory, Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11) {
        return new KeyguardUserSwitcherController_Factory(instanceFactory, provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11);
    }

    public final Object get() {
        DozeParameters dozeParameters = this.dozeParametersProvider.get();
        return new KeyguardUserSwitcherController(this.keyguardUserSwitcherViewProvider.get(), this.contextProvider.get(), this.resourcesProvider.get(), this.layoutInflaterProvider.get(), this.screenLifecycleProvider.get(), this.userSwitcherControllerProvider.get(), this.communalStateControllerProvider.get(), this.keyguardStateControllerProvider.get(), this.statusBarStateControllerProvider.get(), this.keyguardUpdateMonitorProvider.get(), this.screenOffAnimationControllerProvider.get());
    }

    public KeyguardUserSwitcherController_Factory(InstanceFactory instanceFactory, Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11) {
        this.keyguardUserSwitcherViewProvider = instanceFactory;
        this.contextProvider = provider;
        this.resourcesProvider = provider2;
        this.layoutInflaterProvider = provider3;
        this.screenLifecycleProvider = provider4;
        this.userSwitcherControllerProvider = provider5;
        this.communalStateControllerProvider = provider6;
        this.keyguardStateControllerProvider = provider7;
        this.statusBarStateControllerProvider = provider8;
        this.keyguardUpdateMonitorProvider = provider9;
        this.dozeParametersProvider = provider10;
        this.screenOffAnimationControllerProvider = provider11;
    }
}
