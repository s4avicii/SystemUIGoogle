package com.android.systemui.screenshot;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import java.util.function.Consumer;

public class ScreenshotSelectorView extends View {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Consumer<Rect> mOnScreenshotSelected;
    public final Paint mPaintBackground;
    public final Paint mPaintSelection;
    public Rect mSelectionRect;
    public Point mStartPoint;

    public ScreenshotSelectorView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ScreenshotSelectorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Paint paint = new Paint(-16777216);
        this.mPaintBackground = paint;
        paint.setAlpha(160);
        Paint paint2 = new Paint(0);
        this.mPaintSelection = paint2;
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        setOnTouchListener(new ScreenshotSelectorView$$ExternalSyntheticLambda0(this));
    }

    public final void draw(Canvas canvas) {
        canvas.drawRect((float) this.mLeft, (float) this.mTop, (float) this.mRight, (float) this.mBottom, this.mPaintBackground);
        Rect rect = this.mSelectionRect;
        if (rect != null) {
            canvas.drawRect(rect, this.mPaintSelection);
        }
    }
}
