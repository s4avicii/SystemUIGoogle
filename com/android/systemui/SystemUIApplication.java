package com.android.systemui;

import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.Application;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Bundle;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.Dumpable;
import android.util.DumpableContainer;
import android.util.Log;
import android.util.TimingsTraceLog;
import android.view.SurfaceControl;
import android.view.ThreadedRenderer;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.SystemUIAppComponentFactory;
import com.android.systemui.dagger.ContextComponentHelper;
import com.android.systemui.dagger.GlobalRootComponent;
import com.android.systemui.dagger.SysUIComponent;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.NotificationChannels;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class SystemUIApplication extends Application implements SystemUIAppComponentFactory.ContextInitializer, DumpableContainer {
    public static final /* synthetic */ int $r8$clinit = 0;
    public BootCompleteCacheImpl mBootCompleteCache;
    public ContextComponentHelper mComponentHelper;
    public SystemUIAppComponentFactory.ContextAvailableCallback mContextAvailableCallback;
    public DumpManager mDumpManager;
    public final ArrayMap<String, Dumpable> mDumpables = new ArrayMap<>();
    public GlobalRootComponent mRootComponent;
    public CoreStartable[] mServices;
    public boolean mServicesStarted;
    public SysUIComponent mSysUIComponent;

    public final void startServicesIfNeeded() {
        SystemUIFactory systemUIFactory = SystemUIFactory.mFactory;
        Resources resources = getResources();
        Objects.requireNonNull(systemUIFactory);
        String string = resources.getString(C1777R.string.config_systemUIVendorServiceComponent);
        TreeMap treeMap = new TreeMap(Comparator.comparing(SystemUIApplication$$ExternalSyntheticLambda4.INSTANCE));
        SystemUIFactory systemUIFactory2 = SystemUIFactory.mFactory;
        Objects.requireNonNull(systemUIFactory2);
        treeMap.putAll(systemUIFactory2.mSysUIComponent.getStartables());
        SystemUIFactory systemUIFactory3 = SystemUIFactory.mFactory;
        Objects.requireNonNull(systemUIFactory3);
        treeMap.putAll(systemUIFactory3.mSysUIComponent.getPerUserStartables());
        startServicesIfNeeded(treeMap, "StartServices", string);
    }

    public static void overrideNotificationAppName(Context context, Notification.Builder builder, boolean z) {
        String str;
        Bundle bundle = new Bundle();
        if (z) {
            str = context.getString(17040853);
        } else {
            str = context.getString(17040852);
        }
        bundle.putString("android.substName", str);
        builder.addExtras(bundle);
    }

    public final void onConfigurationChanged(Configuration configuration) {
        if (this.mServicesStarted) {
            this.mSysUIComponent.getConfigurationController().onConfigurationChanged(configuration);
            int length = this.mServices.length;
            for (int i = 0; i < length; i++) {
                CoreStartable[] coreStartableArr = this.mServices;
                if (coreStartableArr[i] != null) {
                    coreStartableArr[i].onConfigurationChanged(configuration);
                }
            }
        }
    }

    public final boolean removeDumpable(Dumpable dumpable) {
        Log.w("SystemUIService", "removeDumpable(" + dumpable + "): not implemented");
        return false;
    }

    public SystemUIApplication() {
        Log.v("SystemUIService", "SystemUIApplication constructed.");
    }

    public static void timeInitialization(String str, Runnable runnable, TimingsTraceLog timingsTraceLog, String str2) {
        long currentTimeMillis = System.currentTimeMillis();
        timingsTraceLog.traceBegin(str2 + " " + str);
        runnable.run();
        timingsTraceLog.traceEnd();
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (currentTimeMillis2 > 1000) {
            Log.w("SystemUIService", "Initialization of " + str + " took " + currentTimeMillis2 + " ms");
        }
    }

    public final boolean addDumpable(Dumpable dumpable) {
        String dumpableName = dumpable.getDumpableName();
        if (this.mDumpables.containsKey(dumpableName)) {
            return false;
        }
        this.mDumpables.put(dumpableName, dumpable);
        this.mDumpManager.registerDumpable(dumpable.getDumpableName(), new SystemUIApplication$$ExternalSyntheticLambda0(dumpable));
        return true;
    }

    public final void onCreate() {
        super.onCreate();
        Log.v("SystemUIService", "SystemUIApplication created.");
        TimingsTraceLog timingsTraceLog = new TimingsTraceLog("SystemUIBootTiming", 4096);
        timingsTraceLog.traceBegin("DependencyInjection");
        this.mContextAvailableCallback.onContextAvailable(this);
        SystemUIFactory systemUIFactory = SystemUIFactory.mFactory;
        Objects.requireNonNull(systemUIFactory);
        this.mRootComponent = systemUIFactory.mRootComponent;
        SystemUIFactory systemUIFactory2 = SystemUIFactory.mFactory;
        Objects.requireNonNull(systemUIFactory2);
        SysUIComponent sysUIComponent = systemUIFactory2.mSysUIComponent;
        this.mSysUIComponent = sysUIComponent;
        this.mComponentHelper = sysUIComponent.getContextComponentHelper();
        this.mBootCompleteCache = this.mSysUIComponent.provideBootCacheImpl();
        timingsTraceLog.traceEnd();
        setTheme(2132018181);
        if (Process.myUserHandle().equals(UserHandle.SYSTEM)) {
            IntentFilter intentFilter = new IntentFilter("android.intent.action.BOOT_COMPLETED");
            intentFilter.setPriority(1000);
            int gPUContextPriority = SurfaceControl.getGPUContextPriority();
            Log.i("SystemUIService", "Found SurfaceFlinger's GPU Priority: " + gPUContextPriority);
            if (gPUContextPriority == 13143) {
                Log.i("SystemUIService", "Setting SysUI's GPU Context priority to: 12545");
                ThreadedRenderer.setContextPriority(12545);
            }
            try {
                ActivityManager.getService().enableBinderTracing();
            } catch (RemoteException e) {
                Log.e("SystemUIService", "Unable to enable binder tracing", e);
            }
            registerReceiver(new BroadcastReceiver() {
                public final void onReceive(Context context, Intent intent) {
                    if (!SystemUIApplication.this.mBootCompleteCache.isBootComplete()) {
                        SystemUIApplication.this.unregisterReceiver(this);
                        SystemUIApplication.this.mBootCompleteCache.setBootComplete();
                        SystemUIApplication systemUIApplication = SystemUIApplication.this;
                        if (systemUIApplication.mServicesStarted) {
                            int length = systemUIApplication.mServices.length;
                            for (int i = 0; i < length; i++) {
                                SystemUIApplication.this.mServices[i].onBootCompleted();
                            }
                        }
                    }
                }
            }, intentFilter);
            registerReceiver(new BroadcastReceiver() {
                public final void onReceive(Context context, Intent intent) {
                    if ("android.intent.action.LOCALE_CHANGED".equals(intent.getAction()) && SystemUIApplication.this.mBootCompleteCache.isBootComplete()) {
                        NotificationChannels.createAll(context);
                    }
                }
            }, new IntentFilter("android.intent.action.LOCALE_CHANGED"));
            return;
        }
        String currentProcessName = ActivityThread.currentProcessName();
        ApplicationInfo applicationInfo = getApplicationInfo();
        if (currentProcessName != null) {
            if (currentProcessName.startsWith(applicationInfo.processName + ":")) {
                return;
            }
        }
        TreeMap treeMap = new TreeMap(Comparator.comparing(SystemUIApplication$$ExternalSyntheticLambda3.INSTANCE));
        SystemUIFactory systemUIFactory3 = SystemUIFactory.mFactory;
        Objects.requireNonNull(systemUIFactory3);
        treeMap.putAll(systemUIFactory3.mSysUIComponent.getPerUserStartables());
        startServicesIfNeeded(treeMap, "StartSecondaryServices", (String) null);
    }

    public final void startServicesIfNeeded(TreeMap treeMap, String str, String str2) {
        if (!this.mServicesStarted) {
            this.mServices = new CoreStartable[(treeMap.size() + (str2 == null ? 0 : 1))];
            if (!this.mBootCompleteCache.isBootComplete() && "1".equals(SystemProperties.get("sys.boot_completed"))) {
                this.mBootCompleteCache.setBootComplete();
            }
            this.mDumpManager = this.mSysUIComponent.createDumpManager();
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Starting SystemUI services for user ");
            m.append(Process.myUserHandle().getIdentifier());
            m.append(".");
            Log.v("SystemUIService", m.toString());
            TimingsTraceLog timingsTraceLog = new TimingsTraceLog("SystemUIBootTiming", 4096);
            timingsTraceLog.traceBegin(str);
            int i = 0;
            for (Map.Entry entry : treeMap.entrySet()) {
                String name = ((Class) entry.getKey()).getName();
                timeInitialization(name, new SystemUIApplication$$ExternalSyntheticLambda2(this, i, name, entry), timingsTraceLog, str);
                i++;
            }
            if (str2 != null) {
                timeInitialization(str2, new SystemUIApplication$$ExternalSyntheticLambda1(this, str2, 0), timingsTraceLog, str);
            }
            for (int i2 = 0; i2 < this.mServices.length; i2++) {
                if (this.mBootCompleteCache.isBootComplete()) {
                    this.mServices[i2].onBootCompleted();
                }
                this.mDumpManager.registerDumpable(this.mServices[i2].getClass().getName(), this.mServices[i2]);
            }
            InitController initController = this.mSysUIComponent.getInitController();
            Objects.requireNonNull(initController);
            while (!initController.mTasks.isEmpty()) {
                initController.mTasks.remove(0).run();
            }
            initController.mTasksExecuted = true;
            timingsTraceLog.traceEnd();
            this.mServicesStarted = true;
        }
    }

    public final void setContextAvailableCallback(SystemUIAppComponentFactory.ContextAvailableCallback contextAvailableCallback) {
        this.mContextAvailableCallback = contextAvailableCallback;
    }
}
