package com.android.systemui.flags;

/* compiled from: FeatureFlags.kt */
public interface FeatureFlags extends FlagListenable {
    boolean isEnabled(BooleanFlag booleanFlag);

    boolean isEnabled(ResourceBooleanFlag resourceBooleanFlag);
}
