package com.android.systemui.classifier;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.shared.plugins.PluginManager;
import com.android.systemui.util.DeviceConfigProxy;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class FalsingManagerProxy_Factory implements Factory<FalsingManagerProxy> {
    public final Provider<BrightLineFalsingManager> brightLineFalsingManagerProvider;
    public final Provider<DeviceConfigProxy> deviceConfigProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<Executor> executorProvider;
    public final Provider<PluginManager> pluginManagerProvider;

    public final Object get() {
        return new FalsingManagerProxy(this.pluginManagerProvider.get(), this.executorProvider.get(), this.deviceConfigProvider.get(), this.dumpManagerProvider.get(), this.brightLineFalsingManagerProvider);
    }

    public FalsingManagerProxy_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, BrightLineFalsingManager_Factory brightLineFalsingManager_Factory) {
        this.pluginManagerProvider = provider;
        this.executorProvider = provider2;
        this.deviceConfigProvider = provider3;
        this.dumpManagerProvider = provider4;
        this.brightLineFalsingManagerProvider = brightLineFalsingManager_Factory;
    }
}
