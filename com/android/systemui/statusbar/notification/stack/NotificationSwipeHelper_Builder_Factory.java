package com.android.systemui.statusbar.notification.stack;

import android.content.res.Resources;
import android.view.ViewConfiguration;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class NotificationSwipeHelper_Builder_Factory implements Factory<NotificationSwipeHelper.Builder> {
    public final Provider<FalsingManager> falsingManagerProvider;
    public final Provider<FeatureFlags> featureFlagsProvider;
    public final Provider<Resources> resourcesProvider;
    public final Provider<ViewConfiguration> viewConfigurationProvider;

    public final Object get() {
        return new NotificationSwipeHelper.Builder(this.resourcesProvider.get(), this.viewConfigurationProvider.get(), this.falsingManagerProvider.get(), this.featureFlagsProvider.get());
    }

    public NotificationSwipeHelper_Builder_Factory(Provider<Resources> provider, Provider<ViewConfiguration> provider2, Provider<FalsingManager> provider3, Provider<FeatureFlags> provider4) {
        this.resourcesProvider = provider;
        this.viewConfigurationProvider = provider2;
        this.falsingManagerProvider = provider3;
        this.featureFlagsProvider = provider4;
    }
}
