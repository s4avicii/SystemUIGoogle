package com.android.systemui.wallet.controller;

import android.content.Context;
import android.service.quickaccesswallet.QuickAccessWalletClient;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class QuickAccessWalletController_Factory implements Factory<QuickAccessWalletController> {
    public final Provider<Executor> callbackExecutorProvider;
    public final Provider<SystemClock> clockProvider;
    public final Provider<Context> contextProvider;
    public final Provider<Executor> executorProvider;
    public final Provider<QuickAccessWalletClient> quickAccessWalletClientProvider;
    public final Provider<SecureSettings> secureSettingsProvider;

    public static QuickAccessWalletController_Factory create(Provider<Context> provider, Provider<Executor> provider2, Provider<Executor> provider3, Provider<SecureSettings> provider4, Provider<QuickAccessWalletClient> provider5, Provider<SystemClock> provider6) {
        return new QuickAccessWalletController_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    public final Object get() {
        return new QuickAccessWalletController(this.contextProvider.get(), this.executorProvider.get(), this.callbackExecutorProvider.get(), this.secureSettingsProvider.get(), this.quickAccessWalletClientProvider.get(), this.clockProvider.get());
    }

    public QuickAccessWalletController_Factory(Provider<Context> provider, Provider<Executor> provider2, Provider<Executor> provider3, Provider<SecureSettings> provider4, Provider<QuickAccessWalletClient> provider5, Provider<SystemClock> provider6) {
        this.contextProvider = provider;
        this.executorProvider = provider2;
        this.callbackExecutorProvider = provider3;
        this.secureSettingsProvider = provider4;
        this.quickAccessWalletClientProvider = provider5;
        this.clockProvider = provider6;
    }
}
