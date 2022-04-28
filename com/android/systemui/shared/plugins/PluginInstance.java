package com.android.systemui.shared.plugins;

import android.content.ComponentName;
import android.content.Context;
import android.util.ArrayMap;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.shared.plugins.PluginActionManager;
import java.util.List;

public final class PluginInstance<T extends Plugin> {
    public static final ArrayMap sClassLoaders = new ArrayMap();
    public final ComponentName mComponentName;
    public final T mPlugin;
    public final Context mPluginContext;
    public final VersionInfo mVersionInfo;

    public static class Factory {
        public final ClassLoader mBaseClassLoader;
        public final InstanceFactory<?> mInstanceFactory;
        public final boolean mIsDebug;
        public final List<String> mPrivilegedPlugins;
        public final VersionChecker mVersionChecker;

        /* JADX WARNING: Removed duplicated region for block: B:21:0x00bd  */
        /* JADX WARNING: Removed duplicated region for block: B:24:0x00cb  */
        /* JADX WARNING: Removed duplicated region for block: B:27:0x00d9  */
        /* JADX WARNING: Removed duplicated region for block: B:28:0x00f4  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final <T extends com.android.systemui.plugins.Plugin> com.android.systemui.shared.plugins.PluginInstance<T> create(android.content.Context r10, android.content.pm.ApplicationInfo r11, android.content.ComponentName r12, java.lang.Class<T> r13) throws android.content.pm.PackageManager.NameNotFoundException, java.lang.ClassNotFoundException, java.lang.InstantiationException, java.lang.IllegalAccessException {
            /*
                r9 = this;
                java.lang.ClassLoader r0 = r9.mBaseClassLoader
                boolean r1 = r9.mIsDebug
                r2 = 0
                r3 = 0
                r4 = 1
                if (r1 != 0) goto L_0x0059
                java.lang.String r1 = r11.packageName
                java.util.List<java.lang.String> r5 = r9.mPrivilegedPlugins
                java.util.Iterator r5 = r5.iterator()
            L_0x0011:
                boolean r6 = r5.hasNext()
                if (r6 == 0) goto L_0x0036
                java.lang.Object r6 = r5.next()
                java.lang.String r6 = (java.lang.String) r6
                android.content.ComponentName r7 = android.content.ComponentName.unflattenFromString(r6)
                if (r7 == 0) goto L_0x002e
                java.lang.String r6 = r7.getPackageName()
                boolean r6 = r6.equals(r1)
                if (r6 == 0) goto L_0x0011
                goto L_0x0034
            L_0x002e:
                boolean r6 = r6.equals(r1)
                if (r6 == 0) goto L_0x0011
            L_0x0034:
                r1 = r4
                goto L_0x0037
            L_0x0036:
                r1 = r2
            L_0x0037:
                if (r1 != 0) goto L_0x0059
                java.lang.String r0 = "Cannot get class loader for non-privileged plugin. Src:"
                java.lang.StringBuilder r0 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r0)
                java.lang.String r1 = r11.sourceDir
                r0.append(r1)
                java.lang.String r1 = ", pkg: "
                r0.append(r1)
                java.lang.String r1 = r11.packageName
                r0.append(r1)
                java.lang.String r0 = r0.toString()
                java.lang.String r1 = "PluginInstance"
                android.util.Log.w(r1, r0)
                r0 = r3
                goto L_0x0093
            L_0x0059:
                android.util.ArrayMap r1 = com.android.systemui.shared.plugins.PluginInstance.sClassLoaders
                java.lang.String r5 = r11.packageName
                boolean r5 = r1.containsKey(r5)
                if (r5 == 0) goto L_0x006c
                java.lang.String r0 = r11.packageName
                java.lang.Object r0 = r1.get(r0)
                java.lang.ClassLoader r0 = (java.lang.ClassLoader) r0
                goto L_0x0093
            L_0x006c:
                java.util.ArrayList r5 = new java.util.ArrayList
                r5.<init>()
                java.util.ArrayList r6 = new java.util.ArrayList
                r6.<init>()
                android.app.LoadedApk.makePaths(r3, r4, r11, r5, r6)
                dalvik.system.PathClassLoader r7 = new dalvik.system.PathClassLoader
                java.lang.String r8 = java.io.File.pathSeparator
                java.lang.String r5 = android.text.TextUtils.join(r8, r5)
                java.lang.String r6 = android.text.TextUtils.join(r8, r6)
                com.android.systemui.shared.plugins.PluginManagerImpl$ClassLoaderFilter r8 = new com.android.systemui.shared.plugins.PluginManagerImpl$ClassLoaderFilter
                r8.<init>(r0)
                r7.<init>(r5, r6, r8)
                java.lang.String r0 = r11.packageName
                r1.put(r0, r7)
                r0 = r7
            L_0x0093:
                com.android.systemui.shared.plugins.PluginActionManager$PluginContextWrapper r1 = new com.android.systemui.shared.plugins.PluginActionManager$PluginContextWrapper
                android.content.Context r10 = r10.createApplicationContext(r11, r2)
                r1.<init>(r10, r0)
                java.lang.String r10 = r12.getClassName()
                java.lang.Class r10 = java.lang.Class.forName(r10, r4, r0)
                com.android.systemui.shared.plugins.PluginInstance$InstanceFactory<?> r11 = r9.mInstanceFactory
                java.util.Objects.requireNonNull(r11)
                java.lang.Object r11 = r10.newInstance()
                com.android.systemui.plugins.Plugin r11 = (com.android.systemui.plugins.Plugin) r11
                com.android.systemui.shared.plugins.PluginInstance$VersionChecker r9 = r9.mVersionChecker
                java.util.Objects.requireNonNull(r9)
                com.android.systemui.shared.plugins.VersionInfo r9 = new com.android.systemui.shared.plugins.VersionInfo
                r9.<init>()
                java.lang.Class<?> r0 = r9.mDefault
                if (r0 != 0) goto L_0x00bf
                r9.mDefault = r13
            L_0x00bf:
                r9.addClass(r13, r2)
                com.android.systemui.shared.plugins.VersionInfo r13 = new com.android.systemui.shared.plugins.VersionInfo
                r13.<init>()
                java.lang.Class<?> r0 = r13.mDefault
                if (r0 != 0) goto L_0x00cd
                r13.mDefault = r10
            L_0x00cd:
                r13.addClass(r10, r2)
                android.util.ArrayMap<java.lang.Class<?>, com.android.systemui.shared.plugins.VersionInfo$Version> r10 = r13.mVersions
                boolean r10 = r10.isEmpty()
                r10 = r10 ^ r4
                if (r10 == 0) goto L_0x00f4
                android.util.ArrayMap r10 = new android.util.ArrayMap
                android.util.ArrayMap<java.lang.Class<?>, com.android.systemui.shared.plugins.VersionInfo$Version> r0 = r9.mVersions
                r10.<init>(r0)
                android.util.ArrayMap<java.lang.Class<?>, com.android.systemui.shared.plugins.VersionInfo$Version> r0 = r13.mVersions
                com.android.systemui.shared.plugins.VersionInfo$1 r2 = new com.android.systemui.shared.plugins.VersionInfo$1
                r2.<init>(r10)
                r0.forEach(r2)
                com.android.systemui.shared.plugins.VersionInfo$2 r9 = new com.android.systemui.shared.plugins.VersionInfo$2
                r9.<init>()
                r10.forEach(r9)
                r3 = r13
                goto L_0x0106
            L_0x00f4:
                int r10 = r11.getVersion()
                android.util.ArrayMap<java.lang.Class<?>, com.android.systemui.shared.plugins.VersionInfo$Version> r13 = r9.mVersions
                java.lang.Class<?> r9 = r9.mDefault
                java.lang.Object r9 = r13.get(r9)
                com.android.systemui.shared.plugins.VersionInfo$Version r9 = (com.android.systemui.shared.plugins.VersionInfo.Version) r9
                int r9 = r9.mVersion
                if (r10 != r9) goto L_0x010c
            L_0x0106:
                com.android.systemui.shared.plugins.PluginInstance r9 = new com.android.systemui.shared.plugins.PluginInstance
                r9.<init>(r12, r11, r1, r3)
                return r9
            L_0x010c:
                com.android.systemui.shared.plugins.VersionInfo$InvalidVersionException r9 = new com.android.systemui.shared.plugins.VersionInfo$InvalidVersionException
                java.lang.String r10 = "Invalid legacy version"
                r9.<init>(r10)
                throw r9
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shared.plugins.PluginInstance.Factory.create(android.content.Context, android.content.pm.ApplicationInfo, android.content.ComponentName, java.lang.Class):com.android.systemui.shared.plugins.PluginInstance");
        }

        public Factory(ClassLoader classLoader, InstanceFactory<?> instanceFactory, VersionChecker versionChecker, List<String> list, boolean z) {
            this.mPrivilegedPlugins = list;
            this.mBaseClassLoader = classLoader;
            this.mInstanceFactory = instanceFactory;
            this.mVersionChecker = versionChecker;
            this.mIsDebug = z;
        }
    }

    public static class InstanceFactory<T extends Plugin> {
    }

    public static class VersionChecker {
    }

    public PluginInstance(ComponentName componentName, Plugin plugin, PluginActionManager.PluginContextWrapper pluginContextWrapper, VersionInfo versionInfo) {
        this.mComponentName = componentName;
        this.mPlugin = plugin;
        this.mPluginContext = pluginContextWrapper;
        this.mVersionInfo = versionInfo;
    }

    @VisibleForTesting
    public Context getPluginContext() {
        return this.mPluginContext;
    }
}
