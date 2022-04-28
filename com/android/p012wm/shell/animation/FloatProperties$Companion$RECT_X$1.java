package com.android.p012wm.shell.animation;

import android.graphics.Rect;
import androidx.dynamicanimation.animation.FloatPropertyCompat;

/* renamed from: com.android.wm.shell.animation.FloatProperties$Companion$RECT_X$1 */
/* compiled from: FloatProperties.kt */
public final class FloatProperties$Companion$RECT_X$1 extends FloatPropertyCompat<Rect> {
    public final float getValue(Object obj) {
        Rect rect = (Rect) obj;
        if (rect == null) {
            return -3.4028235E38f;
        }
        return (float) rect.left;
    }

    public final void setValue(Object obj, float f) {
        Rect rect = (Rect) obj;
        if (rect != null) {
            rect.offsetTo((int) f, rect.top);
        }
    }
}
