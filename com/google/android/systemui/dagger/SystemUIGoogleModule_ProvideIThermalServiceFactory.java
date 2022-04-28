package com.google.android.systemui.dagger;

import android.os.IThermalService;
import android.os.ServiceManager;
import dagger.internal.Factory;
import java.util.Objects;

public final class SystemUIGoogleModule_ProvideIThermalServiceFactory implements Factory<IThermalService> {

    public static final class InstanceHolder {
        public static final SystemUIGoogleModule_ProvideIThermalServiceFactory INSTANCE = new SystemUIGoogleModule_ProvideIThermalServiceFactory();
    }

    public final Object get() {
        IThermalService asInterface = IThermalService.Stub.asInterface(ServiceManager.getService("thermalservice"));
        Objects.requireNonNull(asInterface, "Cannot return null from a non-@Nullable @Provides method");
        return asInterface;
    }
}
