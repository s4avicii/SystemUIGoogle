package com.google.android.systemui.fingerprint;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.util.concurrency.Execution;
import com.google.android.systemui.fingerprint.UdfpsGhbmProvider_Factory;
import com.google.android.systemui.fingerprint.UdfpsLhbmProvider_Factory;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class UdfpsHbmController_Factory implements Factory<UdfpsHbmController> {
    public final Provider<AuthController> authControllerProvider;
    public final Provider<Executor> biometricExecutorProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DisplayManager> displayManagerProvider;
    public final Provider<Execution> executionProvider;
    public final Provider<UdfpsGhbmProvider> ghbmProvider;
    public final Provider<UdfpsLhbmProvider> lhbmProvider;
    public final Provider<Handler> mainHandlerProvider;

    public UdfpsHbmController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        UdfpsGhbmProvider_Factory udfpsGhbmProvider_Factory = UdfpsGhbmProvider_Factory.InstanceHolder.INSTANCE;
        UdfpsLhbmProvider_Factory udfpsLhbmProvider_Factory = UdfpsLhbmProvider_Factory.InstanceHolder.INSTANCE;
        this.contextProvider = provider;
        this.executionProvider = provider2;
        this.mainHandlerProvider = provider3;
        this.biometricExecutorProvider = provider4;
        this.ghbmProvider = udfpsGhbmProvider_Factory;
        this.lhbmProvider = udfpsLhbmProvider_Factory;
        this.authControllerProvider = provider5;
        this.displayManagerProvider = provider6;
    }

    public final Object get() {
        return new UdfpsHbmController(this.contextProvider.get(), this.executionProvider.get(), this.mainHandlerProvider.get(), this.biometricExecutorProvider.get(), this.ghbmProvider.get(), this.lhbmProvider.get(), this.authControllerProvider.get(), this.displayManagerProvider.get());
    }
}
