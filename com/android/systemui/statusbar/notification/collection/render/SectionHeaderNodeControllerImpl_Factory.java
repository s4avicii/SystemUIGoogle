package com.android.systemui.statusbar.notification.collection.render;

import android.view.LayoutInflater;
import com.android.systemui.plugins.ActivityStarter;
import dagger.internal.Factory;
import dagger.internal.InstanceFactory;
import javax.inject.Provider;

public final class SectionHeaderNodeControllerImpl_Factory implements Factory<SectionHeaderNodeControllerImpl> {
    public final Provider<ActivityStarter> activityStarterProvider;
    public final Provider<String> clickIntentActionProvider;
    public final Provider<Integer> headerTextResIdProvider;
    public final Provider<LayoutInflater> layoutInflaterProvider;
    public final Provider<String> nodeLabelProvider;

    public final Object get() {
        return new SectionHeaderNodeControllerImpl(this.nodeLabelProvider.get(), this.layoutInflaterProvider.get(), this.headerTextResIdProvider.get().intValue(), this.activityStarterProvider.get(), this.clickIntentActionProvider.get());
    }

    public SectionHeaderNodeControllerImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, InstanceFactory instanceFactory) {
        this.nodeLabelProvider = provider;
        this.layoutInflaterProvider = provider2;
        this.headerTextResIdProvider = provider3;
        this.activityStarterProvider = provider4;
        this.clickIntentActionProvider = instanceFactory;
    }
}
