package com.android.systemui.unfold.config;

import android.content.Context;
import android.os.SystemProperties;

/* compiled from: ResourceUnfoldTransitionConfig.kt */
public final class ResourceUnfoldTransitionConfig implements UnfoldTransitionConfig {
    public final Context context;

    public final boolean isEnabled() {
        boolean z;
        if (this.context.getResources().getBoolean(17891788)) {
            if (SystemProperties.getInt("persist.unfold.transition_enabled", 1) == 1) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                return true;
            }
        }
        return false;
    }

    public final boolean isHingeAngleEnabled() {
        return this.context.getResources().getBoolean(17891789);
    }

    public ResourceUnfoldTransitionConfig(Context context2) {
        this.context = context2;
    }
}
