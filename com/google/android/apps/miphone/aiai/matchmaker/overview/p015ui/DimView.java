package com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/* renamed from: com.google.android.apps.miphone.aiai.matchmaker.overview.ui.DimView */
public final class DimView extends FrameLayout {
    public final Paint backgroundPaint;
    public final Rect tmpRect = new Rect();
    public final RectF tmpRectF = new RectF();

    public DimView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0, 0);
        Paint paint = new Paint();
        this.backgroundPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0);
        setWillNotDraw(false);
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        setOnTouchListener(new DimView$$ExternalSyntheticLambda0(this));
    }

    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getHeight() != 0) {
            getDrawingRect(this.tmpRect);
            this.tmpRectF.set(this.tmpRect);
            canvas.drawRoundRect(this.tmpRectF, 0.0f, 0.0f, this.backgroundPaint);
        }
    }
}
