package com.android.systemui.plugins;

import com.android.systemui.util.concurrency.ThreadFactory;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class PluginsModule_ProvidesPluginExecutorFactory implements Factory<Executor> {
    private final Provider<ThreadFactory> threadFactoryProvider;

    public static PluginsModule_ProvidesPluginExecutorFactory create(Provider<ThreadFactory> provider) {
        return new PluginsModule_ProvidesPluginExecutorFactory(provider);
    }

    public Executor get() {
        return providesPluginExecutor(this.threadFactoryProvider.get());
    }

    public PluginsModule_ProvidesPluginExecutorFactory(Provider<ThreadFactory> provider) {
        this.threadFactoryProvider = provider;
    }

    public static Executor providesPluginExecutor(ThreadFactory threadFactory) {
        Executor providesPluginExecutor = PluginsModule.providesPluginExecutor(threadFactory);
        Objects.requireNonNull(providesPluginExecutor, "Cannot return null from a non-@Nullable @Provides method");
        return providesPluginExecutor;
    }
}
