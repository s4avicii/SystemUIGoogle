package com.android.systemui.p006qs;

import com.android.systemui.statusbar.policy.ConfigurationController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.QSContainerImplController_Factory */
public final class QSContainerImplController_Factory implements Factory<QSContainerImplController> {
    public final Provider<ConfigurationController> configurationControllerProvider;
    public final Provider<QSPanelController> qsPanelControllerProvider;
    public final Provider<QuickStatusBarHeaderController> quickStatusBarHeaderControllerProvider;
    public final Provider<QSContainerImpl> viewProvider;

    public final Object get() {
        return new QSContainerImplController(this.viewProvider.get(), this.qsPanelControllerProvider.get(), this.quickStatusBarHeaderControllerProvider.get(), this.configurationControllerProvider.get());
    }

    public QSContainerImplController_Factory(Provider<QSContainerImpl> provider, Provider<QSPanelController> provider2, Provider<QuickStatusBarHeaderController> provider3, Provider<ConfigurationController> provider4) {
        this.viewProvider = provider;
        this.qsPanelControllerProvider = provider2;
        this.quickStatusBarHeaderControllerProvider = provider3;
        this.configurationControllerProvider = provider4;
    }
}
