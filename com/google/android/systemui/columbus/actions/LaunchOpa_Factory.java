package com.google.android.systemui.columbus.actions;

import android.app.KeyguardManager;
import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.tuner.TunerService;
import com.google.android.systemui.columbus.ColumbusContentObserver;
import com.google.android.systemui.columbus.feedback.FeedbackEffect;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.SetFactory;
import java.util.Set;
import javax.inject.Provider;

public final class LaunchOpa_Factory implements Factory<LaunchOpa> {
    public final Provider<AssistManager> assistManagerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<Set<FeedbackEffect>> feedbackEffectsProvider;
    public final Provider<KeyguardManager> keyguardManagerProvider;
    public final Provider<ColumbusContentObserver.Factory> settingsObserverFactoryProvider;
    public final Provider<StatusBar> statusBarProvider;
    public final Provider<TunerService> tunerServiceProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;

    public final Object get() {
        return new LaunchOpa(this.contextProvider.get(), this.statusBarProvider.get(), this.feedbackEffectsProvider.get(), this.assistManagerProvider.get(), DoubleCheck.lazy(this.keyguardManagerProvider), this.tunerServiceProvider.get(), this.settingsObserverFactoryProvider.get(), this.uiEventLoggerProvider.get());
    }

    public LaunchOpa_Factory(Provider provider, Provider provider2, SetFactory setFactory, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7) {
        this.contextProvider = provider;
        this.statusBarProvider = provider2;
        this.feedbackEffectsProvider = setFactory;
        this.assistManagerProvider = provider3;
        this.keyguardManagerProvider = provider4;
        this.tunerServiceProvider = provider5;
        this.settingsObserverFactoryProvider = provider6;
        this.uiEventLoggerProvider = provider7;
    }
}
