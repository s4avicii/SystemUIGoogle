package com.google.android.systemui.columbus.sensors.config;

import android.content.Context;
import com.android.p012wm.shell.C1777R;

public final class SensorConfiguration {
    public final float defaultSensitivityValue;
    public final float lowSensitivityValue;

    public SensorConfiguration(Context context) {
        this.defaultSensitivityValue = ((float) context.getResources().getInteger(C1777R.integer.columbus_default_sensitivity_percent)) * 0.01f;
        this.lowSensitivityValue = ((float) context.getResources().getInteger(C1777R.integer.columbus_low_sensitivity_percent)) * 0.01f;
    }
}
