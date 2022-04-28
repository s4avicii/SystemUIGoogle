package com.android.systemui;

import android.content.Context;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.decor.PrivacyDotDecorProviderFactory;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.events.PrivacyDotViewController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.concurrency.ThreadFactory;
import com.android.systemui.util.concurrency.ThreadFactoryImpl_Factory;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class ScreenDecorations_Factory implements Factory<ScreenDecorations> {
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<Context> contextProvider;
    public final Provider<PrivacyDotDecorProviderFactory> dotFactoryProvider;
    public final Provider<PrivacyDotViewController> dotViewControllerProvider;
    public final Provider<Executor> mainExecutorProvider;
    public final Provider<SecureSettings> secureSettingsProvider;
    public final Provider<ThreadFactory> threadFactoryProvider;
    public final Provider<TunerService> tunerServiceProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public ScreenDecorations_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        ThreadFactoryImpl_Factory threadFactoryImpl_Factory = ThreadFactoryImpl_Factory.InstanceHolder.INSTANCE;
        this.contextProvider = provider;
        this.mainExecutorProvider = provider2;
        this.secureSettingsProvider = provider3;
        this.broadcastDispatcherProvider = provider4;
        this.tunerServiceProvider = provider5;
        this.userTrackerProvider = provider6;
        this.dotViewControllerProvider = provider7;
        this.threadFactoryProvider = threadFactoryImpl_Factory;
        this.dotFactoryProvider = provider8;
    }

    public static ScreenDecorations_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        return new ScreenDecorations_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
    }

    public final Object get() {
        return new ScreenDecorations(this.contextProvider.get(), this.mainExecutorProvider.get(), this.secureSettingsProvider.get(), this.broadcastDispatcherProvider.get(), this.tunerServiceProvider.get(), this.userTrackerProvider.get(), this.dotViewControllerProvider.get(), this.threadFactoryProvider.get(), this.dotFactoryProvider.get());
    }
}
