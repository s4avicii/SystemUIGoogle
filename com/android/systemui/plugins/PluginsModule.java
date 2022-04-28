package com.android.systemui.plugins;

import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.dagger.PluginModule;
import com.android.systemui.shared.plugins.PluginActionManager;
import com.android.systemui.shared.plugins.PluginEnabler;
import com.android.systemui.shared.plugins.PluginInstance;
import com.android.systemui.shared.plugins.PluginManager;
import com.android.systemui.shared.plugins.PluginManagerImpl;
import com.android.systemui.shared.plugins.PluginPrefs;
import com.android.systemui.util.concurrency.ThreadFactory;
import java.lang.Thread;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;

public abstract class PluginsModule {
    public static final String PLUGIN_DEBUG = "plugin_debug";
    public static final String PLUGIN_PRIVILEGED = "plugin_privileged";
    public static final String PLUGIN_THREAD = "plugin_thread";

    public abstract PluginEnabler bindsPluginEnablerImpl(PluginEnablerImpl pluginEnablerImpl);

    public static PluginActionManager.Factory providePluginInstanceManagerFactory(Context context, PackageManager packageManager, Executor executor, Executor executor2, NotificationManager notificationManager, PluginEnabler pluginEnabler, List<String> list, PluginInstance.Factory factory) {
        return new PluginActionManager.Factory(context, packageManager, executor, executor2, notificationManager, pluginEnabler, list, factory);
    }

    public static Executor providesPluginExecutor(ThreadFactory threadFactory) {
        return threadFactory.buildExecutorOnNewThread("plugin");
    }

    public static PluginInstance.Factory providesPluginInstanceFactory(List<String> list, boolean z) {
        return new PluginInstance.Factory(PluginModule.class.getClassLoader(), new PluginInstance.InstanceFactory(), new PluginInstance.VersionChecker(), list, z);
    }

    public static PluginManager providesPluginManager(Context context, PluginActionManager.Factory factory, boolean z, Optional<Thread.UncaughtExceptionHandler> optional, PluginEnabler pluginEnabler, PluginPrefs pluginPrefs, List<String> list) {
        return new PluginManagerImpl(context, factory, z, optional, pluginEnabler, pluginPrefs, list);
    }

    public static PluginPrefs providesPluginPrefs(Context context) {
        return new PluginPrefs(context);
    }

    public static List<String> providesPrivilegedPlugins(Context context) {
        return Arrays.asList(context.getResources().getStringArray(C1777R.array.config_pluginWhitelist));
    }

    public static boolean providesPluginDebug() {
        return Build.IS_DEBUGGABLE;
    }
}
