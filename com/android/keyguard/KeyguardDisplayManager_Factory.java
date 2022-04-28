package com.android.keyguard;

import android.content.Context;
import com.android.keyguard.dagger.KeyguardStatusViewComponent;
import com.android.systemui.navigationbar.NavigationBarController;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class KeyguardDisplayManager_Factory implements Factory<KeyguardDisplayManager> {
    public final Provider<Context> contextProvider;
    public final Provider<KeyguardStatusViewComponent.Factory> keyguardStatusViewComponentFactoryProvider;
    public final Provider<NavigationBarController> navigationBarControllerLazyProvider;
    public final Provider<Executor> uiBgExecutorProvider;

    public final Object get() {
        return new KeyguardDisplayManager(this.contextProvider.get(), DoubleCheck.lazy(this.navigationBarControllerLazyProvider), this.keyguardStatusViewComponentFactoryProvider.get(), this.uiBgExecutorProvider.get());
    }

    public KeyguardDisplayManager_Factory(Provider<Context> provider, Provider<NavigationBarController> provider2, Provider<KeyguardStatusViewComponent.Factory> provider3, Provider<Executor> provider4) {
        this.contextProvider = provider;
        this.navigationBarControllerLazyProvider = provider2;
        this.keyguardStatusViewComponentFactoryProvider = provider3;
        this.uiBgExecutorProvider = provider4;
    }
}
