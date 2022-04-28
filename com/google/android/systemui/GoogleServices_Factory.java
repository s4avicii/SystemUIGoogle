package com.google.android.systemui;

import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import com.google.android.systemui.autorotate.AutorotateDataService;
import com.google.android.systemui.autorotate.AutorotateDataService_Factory;
import com.google.android.systemui.columbus.ColumbusServiceWrapper;
import com.google.android.systemui.elmyra.ServiceConfigurationGoogle;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class GoogleServices_Factory implements Factory<GoogleServices> {
    public final Provider<AutorotateDataService> autorotateDataServiceProvider;
    public final Provider<ColumbusServiceWrapper> columbusServiceLazyProvider;
    public final Provider<Context> contextProvider;
    public final Provider<ServiceConfigurationGoogle> serviceConfigurationGoogleProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;

    public final Object get() {
        return new GoogleServices(this.contextProvider.get(), DoubleCheck.lazy(this.serviceConfigurationGoogleProvider), this.uiEventLoggerProvider.get(), DoubleCheck.lazy(this.columbusServiceLazyProvider), this.autorotateDataServiceProvider.get());
    }

    public GoogleServices_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, AutorotateDataService_Factory autorotateDataService_Factory) {
        this.contextProvider = provider;
        this.serviceConfigurationGoogleProvider = provider2;
        this.uiEventLoggerProvider = provider3;
        this.columbusServiceLazyProvider = provider4;
        this.autorotateDataServiceProvider = autorotateDataService_Factory;
    }
}
