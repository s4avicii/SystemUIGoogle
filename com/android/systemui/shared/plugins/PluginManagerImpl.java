package com.android.systemui.shared.plugins;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Build;
import android.os.Handler;
import android.os.SystemProperties;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.widget.Toast;
import com.android.p012wm.shell.back.C1789x62452dae;
import com.android.p012wm.shell.pip.phone.PipController$$ExternalSyntheticLambda3;
import com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda3;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.shared.plugins.PluginActionManager;
import com.android.systemui.shared.plugins.PluginManager;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda22;
import java.lang.Thread;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class PluginManagerImpl extends BroadcastReceiver implements PluginManager {
    public final PluginActionManager.Factory mActionManagerFactory;
    public final ArrayMap mClassLoaders = new ArrayMap();
    public final Context mContext;
    public final boolean mIsDebuggable;
    public boolean mListening;
    public final PluginEnabler mPluginEnabler;
    public final ArrayMap<PluginListener<?>, PluginActionManager<?>> mPluginMap = new ArrayMap<>();
    public final PluginPrefs mPluginPrefs;
    public final ArraySet<String> mPrivilegedPlugins;

    public static class ClassLoaderFilter extends ClassLoader {
        public final ClassLoader mBase;
        public final String mPackage = "com.android.systemui.plugin";

        public final Class<?> loadClass(String str, boolean z) throws ClassNotFoundException {
            if (!str.startsWith(this.mPackage)) {
                super.loadClass(str, z);
            }
            return this.mBase.loadClass(str);
        }

        public ClassLoaderFilter(ClassLoader classLoader) {
            super(ClassLoader.getSystemClassLoader());
            this.mBase = classLoader;
        }
    }

    public class PluginExceptionHandler implements Thread.UncaughtExceptionHandler {
        public final Optional<Thread.UncaughtExceptionHandler> mExceptionHandlerOptional;

        public final boolean checkStack(Throwable th) {
            boolean z;
            if (th == null) {
                return false;
            }
            synchronized (this) {
                z = false;
                for (StackTraceElement stackTraceElement : th.getStackTrace()) {
                    for (PluginActionManager<?> checkAndDisable : PluginManagerImpl.this.mPluginMap.values()) {
                        z |= checkAndDisable.checkAndDisable(stackTraceElement.getClassName());
                    }
                }
            }
            return checkStack(th.getCause()) | z;
        }

        public PluginExceptionHandler(Optional optional) {
            this.mExceptionHandlerOptional = optional;
        }

        public final void uncaughtException(Thread thread, Throwable th) {
            if (SystemProperties.getBoolean("plugin.debugging", false)) {
                this.mExceptionHandlerOptional.ifPresent(new C1789x62452dae(thread, th, 1));
                return;
            }
            boolean checkStack = checkStack(th);
            if (!checkStack) {
                synchronized (this) {
                    for (PluginActionManager<?> disableAll : PluginManagerImpl.this.mPluginMap.values()) {
                        checkStack |= disableAll.disableAll();
                    }
                }
            }
            if (checkStack) {
                th = new CrashWhilePluginActiveException(th);
            }
            this.mExceptionHandlerOptional.ifPresent(new C1115x97bb6207(thread, th));
        }
    }

    public final <T extends Plugin> void addPluginListener(PluginListener<T> pluginListener, Class<T> cls) {
        addPluginListener(pluginListener, cls, false);
    }

    public final <T> boolean dependsOn(Plugin plugin, Class<T> cls) {
        synchronized (this) {
            for (int i = 0; i < this.mPluginMap.size(); i++) {
                if (this.mPluginMap.valueAt(i).dependsOn(plugin, cls)) {
                    return true;
                }
            }
            return false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002c, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void removePluginListener(com.android.systemui.plugins.PluginListener<?> r2) {
        /*
            r1 = this;
            monitor-enter(r1)
            android.util.ArrayMap<com.android.systemui.plugins.PluginListener<?>, com.android.systemui.shared.plugins.PluginActionManager<?>> r0 = r1.mPluginMap     // Catch:{ all -> 0x002d }
            boolean r0 = r0.containsKey(r2)     // Catch:{ all -> 0x002d }
            if (r0 != 0) goto L_0x000b
            monitor-exit(r1)     // Catch:{ all -> 0x002d }
            return
        L_0x000b:
            android.util.ArrayMap<com.android.systemui.plugins.PluginListener<?>, com.android.systemui.shared.plugins.PluginActionManager<?>> r0 = r1.mPluginMap     // Catch:{ all -> 0x002d }
            java.lang.Object r2 = r0.remove(r2)     // Catch:{ all -> 0x002d }
            com.android.systemui.shared.plugins.PluginActionManager r2 = (com.android.systemui.shared.plugins.PluginActionManager) r2     // Catch:{ all -> 0x002d }
            r2.destroy()     // Catch:{ all -> 0x002d }
            android.util.ArrayMap<com.android.systemui.plugins.PluginListener<?>, com.android.systemui.shared.plugins.PluginActionManager<?>> r2 = r1.mPluginMap     // Catch:{ all -> 0x002d }
            int r2 = r2.size()     // Catch:{ all -> 0x002d }
            if (r2 != 0) goto L_0x002b
            boolean r2 = r1.mListening     // Catch:{ all -> 0x002d }
            if (r2 != 0) goto L_0x0023
            goto L_0x002b
        L_0x0023:
            r2 = 0
            r1.mListening = r2     // Catch:{ all -> 0x002d }
            android.content.Context r2 = r1.mContext     // Catch:{ all -> 0x002d }
            r2.unregisterReceiver(r1)     // Catch:{ all -> 0x002d }
        L_0x002b:
            monitor-exit(r1)     // Catch:{ all -> 0x002d }
            return
        L_0x002d:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x002d }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shared.plugins.PluginManagerImpl.removePluginListener(com.android.systemui.plugins.PluginListener):void");
    }

    public final <T extends Plugin> void addPluginListener(PluginListener<T> pluginListener, Class<T> cls, boolean z) {
        addPluginListener(PluginManager.Helper.getAction(cls), pluginListener, cls, z);
    }

    public final String[] getPrivilegedPlugins() {
        return (String[]) this.mPrivilegedPlugins.toArray(new String[0]);
    }

    public final void onReceive(Context context, Intent intent) {
        int disableReason;
        boolean z = true;
        if ("android.intent.action.USER_UNLOCKED".equals(intent.getAction())) {
            synchronized (this) {
                for (PluginActionManager next : this.mPluginMap.values()) {
                    Objects.requireNonNull(next);
                    next.mBgExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda3(next, 1));
                }
            }
            return;
        }
        boolean z2 = false;
        if ("com.android.systemui.action.DISABLE_PLUGIN".equals(intent.getAction())) {
            ComponentName unflattenFromString = ComponentName.unflattenFromString(intent.getData().toString().substring(10));
            Iterator<String> it = this.mPrivilegedPlugins.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                String next2 = it.next();
                ComponentName unflattenFromString2 = ComponentName.unflattenFromString(next2);
                if (unflattenFromString2 != null) {
                    if (unflattenFromString2.equals(unflattenFromString)) {
                        break;
                    }
                } else if (next2.equals(unflattenFromString.getPackageName())) {
                    break;
                }
            }
            if (!z) {
                this.mPluginEnabler.setDisabled(unflattenFromString, 2);
                ((NotificationManager) this.mContext.getSystemService(NotificationManager.class)).cancel(unflattenFromString.getClassName(), 6);
                return;
            }
            return;
        }
        String encodedSchemeSpecificPart = intent.getData().getEncodedSchemeSpecificPart();
        ComponentName unflattenFromString3 = ComponentName.unflattenFromString(encodedSchemeSpecificPart);
        if (this.mClassLoaders.remove(encodedSchemeSpecificPart) != null) {
            z2 = true;
        }
        if (z2) {
            if (Build.IS_ENG) {
                Toast.makeText(this.mContext, "Reloading " + encodedSchemeSpecificPart, 1).show();
            } else {
                Log.v("PluginManagerImpl", "Reloading " + encodedSchemeSpecificPart);
            }
        }
        if ("android.intent.action.PACKAGE_REPLACED".equals(intent.getAction()) && unflattenFromString3 != null && ((disableReason = this.mPluginEnabler.getDisableReason(unflattenFromString3)) == 3 || disableReason == 4 || disableReason == 2)) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Re-enabling previously disabled plugin that has been updated: ");
            m.append(unflattenFromString3.flattenToShortString());
            Log.i("PluginManagerImpl", m.toString());
            this.mPluginEnabler.setEnabled(unflattenFromString3);
        }
        synchronized (this) {
            if (!"android.intent.action.PACKAGE_ADDED".equals(intent.getAction()) && !"android.intent.action.PACKAGE_CHANGED".equals(intent.getAction())) {
                if (!"android.intent.action.PACKAGE_REPLACED".equals(intent.getAction())) {
                    for (PluginActionManager next3 : this.mPluginMap.values()) {
                        Objects.requireNonNull(next3);
                        next3.mBgExecutor.execute(new StatusBar$$ExternalSyntheticLambda22(next3, encodedSchemeSpecificPart, 2));
                    }
                }
            }
            for (PluginActionManager next4 : this.mPluginMap.values()) {
                Objects.requireNonNull(next4);
                next4.mBgExecutor.execute(new PipController$$ExternalSyntheticLambda3(next4, encodedSchemeSpecificPart, 3));
            }
        }
    }

    public PluginManagerImpl(Context context, PluginActionManager.Factory factory, boolean z, Optional<Thread.UncaughtExceptionHandler> optional, PluginEnabler pluginEnabler, PluginPrefs pluginPrefs, List<String> list) {
        ArraySet<String> arraySet = new ArraySet<>();
        this.mPrivilegedPlugins = arraySet;
        this.mContext = context;
        this.mActionManagerFactory = factory;
        this.mIsDebuggable = z;
        arraySet.addAll(list);
        this.mPluginPrefs = pluginPrefs;
        this.mPluginEnabler = pluginEnabler;
        Thread.setUncaughtExceptionPreHandler(new PluginExceptionHandler(optional));
    }

    public final <T extends Plugin> void addPluginListener(String str, PluginListener<T> pluginListener, Class<T> cls) {
        addPluginListener(str, pluginListener, cls, false);
    }

    public final <T extends Plugin> void addPluginListener(String str, PluginListener<T> pluginListener, Class<T> cls, boolean z) {
        PluginPrefs pluginPrefs = this.mPluginPrefs;
        Objects.requireNonNull(pluginPrefs);
        synchronized (pluginPrefs) {
            if (pluginPrefs.mPluginActions.add(str)) {
                pluginPrefs.mSharedPrefs.edit().putStringSet("actions", pluginPrefs.mPluginActions).apply();
            }
        }
        PluginActionManager.Factory factory = this.mActionManagerFactory;
        boolean z2 = this.mIsDebuggable;
        Objects.requireNonNull(factory);
        PluginActionManager pluginActionManager = new PluginActionManager(factory.mContext, factory.mPackageManager, str, pluginListener, cls, z, factory.mMainExecutor, factory.mBgExecutor, z2, factory.mNotificationManager, factory.mPluginEnabler, factory.mPrivilegedPlugins, factory.mPluginInstanceFactory);
        pluginActionManager.mBgExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda3(pluginActionManager, 1));
        synchronized (this) {
            this.mPluginMap.put(pluginListener, pluginActionManager);
        }
        if (!this.mListening) {
            this.mListening = true;
            IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
            intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
            intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
            intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
            intentFilter.addDataScheme("package");
            this.mContext.registerReceiver(this, intentFilter);
            intentFilter.addAction("com.android.systemui.action.PLUGIN_CHANGED");
            intentFilter.addAction("com.android.systemui.action.DISABLE_PLUGIN");
            intentFilter.addDataScheme("package");
            this.mContext.registerReceiver(this, intentFilter, "com.android.systemui.permission.PLUGIN", (Handler) null, 2);
            this.mContext.registerReceiver(this, new IntentFilter("android.intent.action.USER_UNLOCKED"));
        }
    }

    public static class CrashWhilePluginActiveException extends RuntimeException {
        public CrashWhilePluginActiveException(Throwable th) {
            super(th);
        }
    }

    static {
        Class<PluginManagerImpl> cls = PluginManagerImpl.class;
    }
}
