package com.google.android.systemui.columbus.actions;

import android.content.ComponentName;
import com.google.android.systemui.columbus.ColumbusSettings;

/* compiled from: LaunchApp.kt */
public final class LaunchApp$settingsListener$1 implements ColumbusSettings.ColumbusSettingsChangeListener {
    public final /* synthetic */ LaunchApp this$0;

    public final void onAlertSilenceEnabledChange(boolean z) {
    }

    public final void onColumbusEnabledChange(boolean z) {
    }

    public final void onLowSensitivityChange(boolean z) {
    }

    public final void onSelectedActionChange(String str) {
    }

    public final void onUseApSensorChange() {
    }

    public LaunchApp$settingsListener$1(LaunchApp launchApp) {
        this.this$0 = launchApp;
    }

    public final void onSelectedAppChange(String str) {
        this.this$0.currentApp = ComponentName.unflattenFromString(str);
        this.this$0.updateAvailable();
    }

    public final void onSelectedAppShortcutChange(String str) {
        LaunchApp launchApp = this.this$0;
        launchApp.currentShortcut = str;
        launchApp.updateAvailable();
    }
}
