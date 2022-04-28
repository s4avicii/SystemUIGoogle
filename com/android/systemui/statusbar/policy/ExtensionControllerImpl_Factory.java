package com.android.systemui.statusbar.policy;

import android.content.Context;
import com.android.systemui.shared.plugins.PluginManager;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.leak.LeakDetector;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class ExtensionControllerImpl_Factory implements Factory<ExtensionControllerImpl> {
    public final Provider<ConfigurationController> configurationControllerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<LeakDetector> leakDetectorProvider;
    public final Provider<PluginManager> pluginManagerProvider;
    public final Provider<TunerService> tunerServiceProvider;

    public final Object get() {
        return new ExtensionControllerImpl(this.contextProvider.get(), this.leakDetectorProvider.get(), this.pluginManagerProvider.get(), this.tunerServiceProvider.get(), this.configurationControllerProvider.get());
    }

    public ExtensionControllerImpl_Factory(Provider<Context> provider, Provider<LeakDetector> provider2, Provider<PluginManager> provider3, Provider<TunerService> provider4, Provider<ConfigurationController> provider5) {
        this.contextProvider = provider;
        this.leakDetectorProvider = provider2;
        this.pluginManagerProvider = provider3;
        this.tunerServiceProvider = provider4;
        this.configurationControllerProvider = provider5;
    }
}
