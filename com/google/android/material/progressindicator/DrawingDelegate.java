package com.google.android.material.progressindicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.google.android.material.progressindicator.BaseProgressIndicatorSpec;

public abstract class DrawingDelegate<S extends BaseProgressIndicatorSpec> {
    public DrawableWithAnimatedVisibilityChange drawable;
    public S spec;

    public abstract void adjustCanvas(Canvas canvas, float f);

    public abstract void fillIndicator(Canvas canvas, Paint paint, float f, float f2, int i);

    public abstract void fillTrack(Canvas canvas, Paint paint);

    public abstract int getPreferredHeight();

    public abstract int getPreferredWidth();

    public DrawingDelegate(S s) {
        this.spec = s;
    }
}
