package com.android.systemui.media;

import androidx.dynamicanimation.animation.FloatPropertyCompat;

/* compiled from: MediaCarouselScrollHandler.kt */
public final class MediaCarouselScrollHandler$Companion$CONTENT_TRANSLATION$1 extends FloatPropertyCompat<MediaCarouselScrollHandler> {
    public final float getValue(Object obj) {
        return ((MediaCarouselScrollHandler) obj).contentTranslation;
    }

    public final void setValue(Object obj, float f) {
        ((MediaCarouselScrollHandler) obj).setContentTranslation(f);
    }
}
