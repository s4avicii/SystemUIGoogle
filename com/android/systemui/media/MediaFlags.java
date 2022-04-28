package com.android.systemui.media;

import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;

/* compiled from: MediaFlags.kt */
public final class MediaFlags {
    public final FeatureFlags featureFlags;

    public final boolean useMediaSessionLayout() {
        if (!this.featureFlags.isEnabled(Flags.MEDIA_SESSION_ACTIONS) || !this.featureFlags.isEnabled(Flags.MEDIA_SESSION_LAYOUT)) {
            return false;
        }
        return true;
    }

    public MediaFlags(FeatureFlags featureFlags2) {
        this.featureFlags = featureFlags2;
    }
}
