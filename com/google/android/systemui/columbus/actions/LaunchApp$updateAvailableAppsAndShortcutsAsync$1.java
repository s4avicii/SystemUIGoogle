package com.google.android.systemui.columbus.actions;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.ShortcutInfo;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/* compiled from: LaunchApp.kt */
public final class LaunchApp$updateAvailableAppsAndShortcutsAsync$1 implements Runnable {
    public final /* synthetic */ LaunchApp this$0;

    public LaunchApp$updateAvailableAppsAndShortcutsAsync$1(LaunchApp launchApp) {
        this.this$0 = launchApp;
    }

    public final void run() {
        List<ShortcutInfo> list;
        int currentUser = ActivityManager.getCurrentUser();
        if (this.this$0.userManager.isUserUnlocked(currentUser)) {
            this.this$0.availableApps.clear();
            this.this$0.availableShortcuts.clear();
            List<LauncherActivityInfo> activityList = this.this$0.launcherApps.getActivityList((String) null, UserHandle.of(currentUser));
            LaunchApp launchApp = this.this$0;
            Objects.requireNonNull(launchApp);
            LauncherApps.ShortcutQuery shortcutQuery = new LauncherApps.ShortcutQuery();
            shortcutQuery.setQueryFlags(9);
            try {
                list = launchApp.launcherApps.getShortcuts(shortcutQuery, UserHandle.of(currentUser));
            } catch (Exception e) {
                if ((e instanceof SecurityException) || (e instanceof IllegalStateException)) {
                    Log.e("Columbus/LaunchApp", "Failed to query for shortcuts", e);
                    list = null;
                } else {
                    throw e;
                }
            }
            for (LauncherActivityInfo next : activityList) {
                try {
                    PendingIntent mainActivityLaunchIntent = this.this$0.launcherApps.getMainActivityLaunchIntent(next.getComponentName(), (Bundle) null, UserHandle.of(currentUser));
                    if (mainActivityLaunchIntent != null) {
                        Intent intent = new Intent(mainActivityLaunchIntent.getIntent());
                        intent.putExtra("systemui_google_quick_tap_is_source", true);
                        LinkedHashMap linkedHashMap = this.this$0.availableApps;
                        ComponentName componentName = next.getComponentName();
                        LaunchApp launchApp2 = this.this$0;
                        Objects.requireNonNull(launchApp2);
                        linkedHashMap.put(componentName, PendingIntent.getActivityAsUser(launchApp2.context, 0, intent, 67108864, (Bundle) null, this.this$0.userTracker.getUserHandle()));
                        LaunchApp.access$addShortcutsForApp(this.this$0, next, list);
                    }
                } catch (RuntimeException unused) {
                }
            }
            final LaunchApp launchApp3 = this.this$0;
            launchApp3.mainHandler.post(new Runnable() {
                public final void run() {
                    launchApp3.updateAvailable();
                }
            });
            return;
        }
        Log.d("Columbus/LaunchApp", "Did not update apps and shortcuts, user " + currentUser + " not unlocked");
    }
}
