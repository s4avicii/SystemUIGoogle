package com.google.android.systemui.columbus;

import android.content.Context;
import com.android.systemui.settings.UserTracker;
import com.google.android.systemui.columbus.ColumbusContentObserver;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class ColumbusSettings_Factory implements Factory<ColumbusSettings> {
    public final Provider<ColumbusContentObserver.Factory> contentObserverFactoryProvider;
    public final Provider<Context> contextProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public final Object get() {
        return new ColumbusSettings(this.contextProvider.get(), this.userTrackerProvider.get(), this.contentObserverFactoryProvider.get());
    }

    public ColumbusSettings_Factory(Provider<Context> provider, Provider<UserTracker> provider2, Provider<ColumbusContentObserver.Factory> provider3) {
        this.contextProvider = provider;
        this.userTrackerProvider = provider2;
        this.contentObserverFactoryProvider = provider3;
    }
}
