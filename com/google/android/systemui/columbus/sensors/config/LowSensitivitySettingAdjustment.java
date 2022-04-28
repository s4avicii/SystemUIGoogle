package com.google.android.systemui.columbus.sensors.config;

import android.content.Context;
import android.provider.Settings;
import com.google.android.systemui.columbus.ColumbusSettings;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: LowSensitivitySettingAdjustment.kt */
public final class LowSensitivitySettingAdjustment extends Adjustment {
    public final SensorConfiguration sensorConfiguration;
    public boolean useLowSensitivity;

    public final float adjustSensitivity(float f) {
        if (this.useLowSensitivity) {
            return this.sensorConfiguration.lowSensitivityValue;
        }
        return f;
    }

    public LowSensitivitySettingAdjustment(Context context, ColumbusSettings columbusSettings, SensorConfiguration sensorConfiguration2) {
        this.sensorConfiguration = sensorConfiguration2;
        columbusSettings.registerColumbusSettingsChangeListener(new LowSensitivitySettingAdjustment$settingsChangeListener$1(this));
        this.useLowSensitivity = Settings.Secure.getIntForUser(columbusSettings.contentResolver, "columbus_low_sensitivity", 0, columbusSettings.userTracker.getUserId()) != 0;
        Function1<? super Adjustment, Unit> function1 = this.callback;
        if (function1 != null) {
            function1.invoke(this);
        }
    }
}
