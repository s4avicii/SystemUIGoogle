package com.android.p012wm.shell.animation;

import android.graphics.Rect;
import androidx.dynamicanimation.animation.FloatPropertyCompat;

/* renamed from: com.android.wm.shell.animation.FloatProperties$Companion$RECT_WIDTH$1 */
/* compiled from: FloatProperties.kt */
public final class FloatProperties$Companion$RECT_WIDTH$1 extends FloatPropertyCompat<Rect> {
    public final float getValue(Object obj) {
        return (float) ((Rect) obj).width();
    }

    public final void setValue(Object obj, float f) {
        Rect rect = (Rect) obj;
        rect.right = rect.left + ((int) f);
    }
}
