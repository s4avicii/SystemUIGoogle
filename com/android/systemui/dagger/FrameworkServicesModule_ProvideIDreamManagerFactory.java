package com.android.systemui.dagger;

import android.os.ServiceManager;
import android.service.dreams.IDreamManager;
import dagger.internal.Factory;
import java.util.Objects;

public final class FrameworkServicesModule_ProvideIDreamManagerFactory implements Factory<IDreamManager> {

    public static final class InstanceHolder {
        public static final FrameworkServicesModule_ProvideIDreamManagerFactory INSTANCE = new FrameworkServicesModule_ProvideIDreamManagerFactory();
    }

    public final Object get() {
        IDreamManager asInterface = IDreamManager.Stub.asInterface(ServiceManager.checkService("dreams"));
        Objects.requireNonNull(asInterface, "Cannot return null from a non-@Nullable @Provides method");
        return asInterface;
    }
}
