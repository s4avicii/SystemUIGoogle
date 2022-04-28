package com.android.systemui.dagger;

import android.media.IAudioService;
import android.os.ServiceManager;
import dagger.internal.Factory;
import java.util.Objects;

public final class FrameworkServicesModule_ProvideIAudioServiceFactory implements Factory<IAudioService> {

    public static final class InstanceHolder {
        public static final FrameworkServicesModule_ProvideIAudioServiceFactory INSTANCE = new FrameworkServicesModule_ProvideIAudioServiceFactory();
    }

    public final Object get() {
        IAudioService asInterface = IAudioService.Stub.asInterface(ServiceManager.getService("audio"));
        Objects.requireNonNull(asInterface, "Cannot return null from a non-@Nullable @Provides method");
        return asInterface;
    }
}
