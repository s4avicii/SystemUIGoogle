package com.google.android.systemui.communal.dock.conditions;

import com.android.systemui.util.condition.Condition;

public final class TimeoutToUserZeroFeatureCondition extends Condition {
    public final boolean mEnabled;

    public final void stop() {
    }

    public final void start() {
        updateCondition(this.mEnabled);
    }

    public TimeoutToUserZeroFeatureCondition(boolean z) {
        this.mEnabled = z;
    }
}
