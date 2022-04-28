package com.google.android.systemui.columbus.gates;

import com.google.android.systemui.columbus.ColumbusSettings;

/* compiled from: FlagEnabled.kt */
public final class FlagEnabled$settingsChangeListener$1 implements ColumbusSettings.ColumbusSettingsChangeListener {
    public final /* synthetic */ FlagEnabled this$0;

    public final void onAlertSilenceEnabledChange(boolean z) {
    }

    public final void onLowSensitivityChange(boolean z) {
    }

    public final void onSelectedActionChange(String str) {
    }

    public final void onSelectedAppChange(String str) {
    }

    public final void onSelectedAppShortcutChange(String str) {
    }

    public final void onUseApSensorChange() {
    }

    public FlagEnabled$settingsChangeListener$1(FlagEnabled flagEnabled) {
        this.this$0 = flagEnabled;
    }

    public final void onColumbusEnabledChange(boolean z) {
        FlagEnabled flagEnabled = this.this$0;
        flagEnabled.columbusEnabled = z;
        flagEnabled.setBlocking(!z);
    }
}
