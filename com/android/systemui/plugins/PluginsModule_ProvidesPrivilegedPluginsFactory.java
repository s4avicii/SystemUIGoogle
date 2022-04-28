package com.android.systemui.plugins;

import android.content.Context;
import dagger.internal.Factory;
import java.util.List;
import java.util.Objects;
import javax.inject.Provider;

public final class PluginsModule_ProvidesPrivilegedPluginsFactory implements Factory<List<String>> {
    private final Provider<Context> contextProvider;

    public static PluginsModule_ProvidesPrivilegedPluginsFactory create(Provider<Context> provider) {
        return new PluginsModule_ProvidesPrivilegedPluginsFactory(provider);
    }

    public List<String> get() {
        return providesPrivilegedPlugins(this.contextProvider.get());
    }

    public PluginsModule_ProvidesPrivilegedPluginsFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static List<String> providesPrivilegedPlugins(Context context) {
        List<String> providesPrivilegedPlugins = PluginsModule.providesPrivilegedPlugins(context);
        Objects.requireNonNull(providesPrivilegedPlugins, "Cannot return null from a non-@Nullable @Provides method");
        return providesPrivilegedPlugins;
    }
}
