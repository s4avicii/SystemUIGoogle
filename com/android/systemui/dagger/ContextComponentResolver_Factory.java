package com.android.systemui.dagger;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import com.android.systemui.recents.RecentsImplementation;
import dagger.internal.Factory;
import dagger.internal.MapProviderFactory;
import java.util.Map;
import javax.inject.Provider;

public final class ContextComponentResolver_Factory implements Factory<ContextComponentResolver> {
    public final Provider<Map<Class<?>, Provider<Activity>>> activityCreatorsProvider;
    public final Provider<Map<Class<?>, Provider<BroadcastReceiver>>> broadcastReceiverCreatorsProvider;
    public final Provider<Map<Class<?>, Provider<RecentsImplementation>>> recentsCreatorsProvider;
    public final Provider<Map<Class<?>, Provider<Service>>> serviceCreatorsProvider;

    public final Object get() {
        return new ContextComponentResolver(this.activityCreatorsProvider.get(), this.serviceCreatorsProvider.get(), this.recentsCreatorsProvider.get(), this.broadcastReceiverCreatorsProvider.get());
    }

    public ContextComponentResolver_Factory(Provider provider, Provider provider2, Provider provider3, MapProviderFactory mapProviderFactory) {
        this.activityCreatorsProvider = provider;
        this.serviceCreatorsProvider = provider2;
        this.recentsCreatorsProvider = provider3;
        this.broadcastReceiverCreatorsProvider = mapProviderFactory;
    }
}
