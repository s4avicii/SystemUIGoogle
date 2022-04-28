package com.android.systemui.biometrics;

import android.content.Context;
import android.graphics.Canvas;

/* compiled from: UdfpsFpDrawable.kt */
public final class UdfpsFpDrawable extends UdfpsDrawable {
    public final void draw(Canvas canvas) {
        if (!this.isIlluminationShowing) {
            this.fingerprintDrawable.draw(canvas);
        }
    }

    public UdfpsFpDrawable(Context context) {
        super(context);
    }
}
