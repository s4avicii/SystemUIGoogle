package com.android.systemui.power;

import com.android.settingslib.fuelgauge.Estimate;

public final class EnhancedEstimatesImpl implements EnhancedEstimates {
    public final boolean getLowWarningEnabled() {
        return true;
    }

    public final long getLowWarningThreshold() {
        return 0;
    }

    public final long getSevereWarningThreshold() {
        return 0;
    }

    public final boolean isHybridNotificationEnabled() {
        return false;
    }

    public final Estimate getEstimate() {
        return new Estimate(-1, false, -1);
    }
}
