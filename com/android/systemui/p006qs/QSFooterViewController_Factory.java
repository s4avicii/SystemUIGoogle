package com.android.systemui.p006qs;

import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.settings.SecureSettingsImpl_Factory;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.QSFooterViewController_Factory */
public final class QSFooterViewController_Factory implements Factory<QSFooterViewController> {
    public final Provider<ActivityStarter> activityStarterProvider;
    public final Provider<FalsingManager> falsingManagerProvider;
    public final Provider<QSPanelController> qsPanelControllerProvider;
    public final Provider<UserTracker> userTrackerProvider;
    public final Provider<QSFooterView> viewProvider;

    public final Object get() {
        return new QSFooterViewController(this.viewProvider.get(), this.userTrackerProvider.get(), this.falsingManagerProvider.get(), this.activityStarterProvider.get(), this.qsPanelControllerProvider.get());
    }

    public QSFooterViewController_Factory(SecureSettingsImpl_Factory secureSettingsImpl_Factory, Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.viewProvider = secureSettingsImpl_Factory;
        this.userTrackerProvider = provider;
        this.falsingManagerProvider = provider2;
        this.activityStarterProvider = provider3;
        this.qsPanelControllerProvider = provider4;
    }
}
