package com.android.systemui.assist;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.os.RemoteException;
import com.android.systemui.BootCompleteCache;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.PackageManagerWrapper;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.statusbar.phone.StatusBar;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

public final class PhoneStateMonitor {
    public static final String[] DEFAULT_HOME_CHANGE_ACTIONS = {"android.intent.action.ACTION_PREFERRED_ACTIVITY_CHANGED", "android.intent.action.PACKAGE_ADDED", "android.intent.action.PACKAGE_CHANGED", "android.intent.action.PACKAGE_REMOVED"};
    public final Context mContext;
    public ComponentName mDefaultHome = getCurrentDefaultHome();
    public boolean mLauncherShowing;
    public final Lazy<Optional<StatusBar>> mStatusBarOptionalLazy;
    public final StatusBarStateController mStatusBarStateController;

    public static ComponentName getCurrentDefaultHome() {
        ComponentName componentName;
        ArrayList arrayList = new ArrayList();
        PackageManagerWrapper packageManagerWrapper = PackageManagerWrapper.sInstance;
        try {
            componentName = PackageManagerWrapper.mIPackageManager.getHomeActivities(arrayList);
        } catch (RemoteException e) {
            e.printStackTrace();
            componentName = null;
        }
        if (componentName != null) {
            return componentName;
        }
        int i = Integer.MIN_VALUE;
        Iterator it = arrayList.iterator();
        while (true) {
            ComponentName componentName2 = null;
            while (true) {
                if (!it.hasNext()) {
                    return componentName2;
                }
                ResolveInfo resolveInfo = (ResolveInfo) it.next();
                int i2 = resolveInfo.priority;
                if (i2 > i) {
                    componentName2 = resolveInfo.activityInfo.getComponentName();
                    i = resolveInfo.priority;
                } else if (i2 == i) {
                }
            }
        }
    }

    public final int getPhoneState() {
        boolean z;
        int state = this.mStatusBarStateController.getState();
        boolean z2 = false;
        if (state == 1 || state == 2) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            if (this.mStatusBarStateController.isDozing()) {
                return 1;
            }
            if (((Boolean) this.mStatusBarOptionalLazy.get().map(PhoneStateMonitor$$ExternalSyntheticLambda1.INSTANCE).orElse(Boolean.FALSE)).booleanValue()) {
                return 3;
            }
            KeyguardManager keyguardManager = (KeyguardManager) this.mContext.getSystemService(KeyguardManager.class);
            if (keyguardManager != null && keyguardManager.isKeyguardLocked()) {
                z2 = true;
            }
            if (z2) {
                return 2;
            }
            return 4;
        } else if (this.mLauncherShowing) {
            return 5;
        } else {
            return 9;
        }
    }

    public PhoneStateMonitor(Context context, BroadcastDispatcher broadcastDispatcher, Lazy<Optional<StatusBar>> lazy, BootCompleteCache bootCompleteCache, StatusBarStateController statusBarStateController) {
        ComponentName componentName;
        this.mContext = context;
        this.mStatusBarOptionalLazy = lazy;
        this.mStatusBarStateController = statusBarStateController;
        bootCompleteCache.addListener(new PhoneStateMonitor$$ExternalSyntheticLambda0(this));
        IntentFilter intentFilter = new IntentFilter();
        String[] strArr = DEFAULT_HOME_CHANGE_ACTIONS;
        boolean z = false;
        for (int i = 0; i < 4; i++) {
            intentFilter.addAction(strArr[i]);
        }
        broadcastDispatcher.registerReceiver(new BroadcastReceiver() {
            public final void onReceive(Context context, Intent intent) {
                PhoneStateMonitor.this.mDefaultHome = PhoneStateMonitor.getCurrentDefaultHome();
            }
        }, intentFilter);
        ActivityManager.RunningTaskInfo runningTask = ActivityManagerWrapper.sInstance.getRunningTask();
        if (!(runningTask == null || (componentName = runningTask.topActivity) == null)) {
            z = componentName.equals(this.mDefaultHome);
        }
        this.mLauncherShowing = z;
        TaskStackChangeListeners.INSTANCE.registerTaskStackListener(new TaskStackChangeListener() {
            public final void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) {
                boolean z;
                ComponentName componentName;
                PhoneStateMonitor phoneStateMonitor = PhoneStateMonitor.this;
                Objects.requireNonNull(phoneStateMonitor);
                if (runningTaskInfo == null || (componentName = runningTaskInfo.topActivity) == null) {
                    z = false;
                } else {
                    z = componentName.equals(phoneStateMonitor.mDefaultHome);
                }
                phoneStateMonitor.mLauncherShowing = z;
            }
        });
    }
}
