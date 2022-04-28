package com.google.android.systemui.columbus.actions;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.pm.ShortcutInfo;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Rect;
import android.os.Bundle;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.plugins.ActivityStarter;
import com.google.android.systemui.columbus.ColumbusEvent;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/* compiled from: LaunchApp.kt */
public final class LaunchApp$onDismissKeyguardAction$1 implements ActivityStarter.OnDismissAction {
    public final /* synthetic */ LaunchApp this$0;

    public LaunchApp$onDismissKeyguardAction$1(LaunchApp launchApp) {
        this.this$0 = launchApp;
    }

    public final boolean onDismiss() {
        String str;
        ShortcutInfo shortcutInfo;
        String str2;
        LaunchApp launchApp = this.this$0;
        Objects.requireNonNull(launchApp);
        String str3 = null;
        if (launchApp.usingShortcut()) {
            LinkedHashMap linkedHashMap = launchApp.availableShortcuts;
            ComponentName componentName = launchApp.currentApp;
            if (componentName == null) {
                str = null;
            } else {
                str = componentName.getPackageName();
            }
            Map map = (Map) linkedHashMap.get(str);
            if (!(map == null || (shortcutInfo = (ShortcutInfo) map.get(launchApp.currentShortcut)) == null)) {
                UiEventLogger uiEventLogger = launchApp.uiEventLogger;
                ColumbusEvent columbusEvent = ColumbusEvent.COLUMBUS_INVOKED_LAUNCH_SHORTCUT;
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("");
                ComponentName componentName2 = launchApp.currentApp;
                if (componentName2 == null) {
                    str2 = null;
                } else {
                    str2 = componentName2.getPackageName();
                }
                m.append(str2);
                m.append('/');
                m.append(shortcutInfo.getId());
                uiEventLogger.log(columbusEvent, 0, m.toString());
                launchApp.launcherApps.startShortcut(shortcutInfo, (Rect) null, (Bundle) null);
            }
        } else {
            PendingIntent pendingIntent = (PendingIntent) launchApp.availableApps.get(launchApp.currentApp);
            if (pendingIntent != null) {
                UiEventLogger uiEventLogger2 = launchApp.uiEventLogger;
                ColumbusEvent columbusEvent2 = ColumbusEvent.COLUMBUS_INVOKED_LAUNCH_APP;
                ComponentName componentName3 = launchApp.currentApp;
                if (componentName3 != null) {
                    str3 = componentName3.flattenToString();
                }
                uiEventLogger2.log(columbusEvent2, 0, str3);
                pendingIntent.send();
            }
        }
        return false;
    }
}
