package com.google.android.systemui.columbus.gates;

import com.google.android.systemui.columbus.ColumbusSettings;
import java.util.Objects;

/* compiled from: SilenceAlertsDisabled.kt */
public final class SilenceAlertsDisabled$settingsChangeListener$1 implements ColumbusSettings.ColumbusSettingsChangeListener {
    public final /* synthetic */ SilenceAlertsDisabled this$0;

    public final void onColumbusEnabledChange(boolean z) {
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

    public SilenceAlertsDisabled$settingsChangeListener$1(SilenceAlertsDisabled silenceAlertsDisabled) {
        this.this$0 = silenceAlertsDisabled;
    }

    public final void onAlertSilenceEnabledChange(boolean z) {
        SilenceAlertsDisabled silenceAlertsDisabled = this.this$0;
        Objects.requireNonNull(silenceAlertsDisabled);
        silenceAlertsDisabled.setBlocking(!z);
    }
}
