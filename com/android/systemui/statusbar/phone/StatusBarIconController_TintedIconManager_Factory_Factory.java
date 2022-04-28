package com.android.systemui.statusbar.phone;

import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class StatusBarIconController_TintedIconManager_Factory_Factory implements Factory<StatusBarIconController.TintedIconManager.Factory> {
    public final Provider<FeatureFlags> featureFlagsProvider;

    public final Object get() {
        return new StatusBarIconController.TintedIconManager.Factory(this.featureFlagsProvider.get());
    }

    public StatusBarIconController_TintedIconManager_Factory_Factory(Provider<FeatureFlags> provider) {
        this.featureFlagsProvider = provider;
    }
}
