package com.google.android.systemui.columbus.sensors.config;

import com.google.android.systemui.columbus.ColumbusSettings;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: LowSensitivitySettingAdjustment.kt */
public final class LowSensitivitySettingAdjustment$settingsChangeListener$1 implements ColumbusSettings.ColumbusSettingsChangeListener {
    public final /* synthetic */ LowSensitivitySettingAdjustment this$0;

    public final void onAlertSilenceEnabledChange(boolean z) {
    }

    public final void onColumbusEnabledChange(boolean z) {
    }

    public final void onSelectedActionChange(String str) {
    }

    public final void onSelectedAppChange(String str) {
    }

    public final void onSelectedAppShortcutChange(String str) {
    }

    public final void onUseApSensorChange() {
    }

    public LowSensitivitySettingAdjustment$settingsChangeListener$1(LowSensitivitySettingAdjustment lowSensitivitySettingAdjustment) {
        this.this$0 = lowSensitivitySettingAdjustment;
    }

    public final void onLowSensitivityChange(boolean z) {
        LowSensitivitySettingAdjustment lowSensitivitySettingAdjustment = this.this$0;
        if (lowSensitivitySettingAdjustment.useLowSensitivity != z) {
            lowSensitivitySettingAdjustment.useLowSensitivity = z;
            Function1<? super Adjustment, Unit> function1 = lowSensitivitySettingAdjustment.callback;
            if (function1 != null) {
                function1.invoke(lowSensitivitySettingAdjustment);
            }
        }
    }
}
