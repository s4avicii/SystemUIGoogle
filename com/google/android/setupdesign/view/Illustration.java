package com.google.android.setupdesign.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import com.google.android.setupdesign.R$styleable;

public class Illustration extends FrameLayout {
    public float aspectRatio = 0.0f;
    public Drawable background;
    public float baselineGridSize;
    public Drawable illustration;
    public final Rect illustrationBounds = new Rect();
    public float scale = 1.0f;
    public final Rect viewBounds = new Rect();

    public Illustration(Context context) {
        super(context);
        init((AttributeSet) null, 0);
    }

    public final void onDraw(Canvas canvas) {
        boolean z;
        boolean z2 = false;
        if (this.background != null) {
            canvas.save();
            canvas.translate(0.0f, (float) this.illustrationBounds.height());
            float f = this.scale;
            canvas.scale(f, f, 0.0f, 0.0f);
            Drawable drawable = this.background;
            if (getLayoutDirection() == 1) {
                z = drawable.isAutoMirrored();
            } else {
                z = false;
            }
            if (z) {
                canvas.scale(-1.0f, 1.0f);
                canvas.translate((float) (-this.background.getBounds().width()), 0.0f);
            }
            this.background.draw(canvas);
            canvas.restore();
        }
        if (this.illustration != null) {
            canvas.save();
            Drawable drawable2 = this.illustration;
            if (getLayoutDirection() == 1) {
                z2 = drawable2.isAutoMirrored();
            }
            if (z2) {
                canvas.scale(-1.0f, 1.0f);
                canvas.translate((float) (-this.illustrationBounds.width()), 0.0f);
            }
            this.illustration.draw(canvas);
            canvas.restore();
        }
        super.onDraw(canvas);
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5 = i3 - i;
        int i6 = i4 - i2;
        Drawable drawable = this.illustration;
        if (drawable != null) {
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = this.illustration.getIntrinsicHeight();
            this.viewBounds.set(0, 0, i5, i6);
            if (this.aspectRatio != 0.0f) {
                float f = ((float) i5) / ((float) intrinsicWidth);
                this.scale = f;
                intrinsicHeight = (int) (((float) intrinsicHeight) * f);
                intrinsicWidth = i5;
            }
            Gravity.apply(55, intrinsicWidth, intrinsicHeight, this.viewBounds, this.illustrationBounds);
            this.illustration.setBounds(this.illustrationBounds);
        }
        Drawable drawable2 = this.background;
        if (drawable2 != null) {
            drawable2.setBounds(0, 0, (int) Math.ceil((double) (((float) i5) / this.scale)), (int) Math.ceil((double) (((float) (i6 - this.illustrationBounds.height())) / this.scale)));
        }
        super.onLayout(z, i, i2, i3, i4);
    }

    public final void onMeasure(int i, int i2) {
        if (this.aspectRatio != 0.0f) {
            float size = (float) ((int) (((float) View.MeasureSpec.getSize(i)) / this.aspectRatio));
            setPadding(0, (int) (size - (size % this.baselineGridSize)), 0, 0);
        }
        setOutlineProvider(ViewOutlineProvider.BOUNDS);
        super.onMeasure(i, i2);
    }

    public final void setBackgroundDrawable(Drawable drawable) {
        if (drawable != this.background) {
            this.background = drawable;
            invalidate();
            requestLayout();
        }
    }

    @Deprecated
    public final void setForeground(Drawable drawable) {
        if (drawable != this.illustration) {
            this.illustration = drawable;
            invalidate();
            requestLayout();
        }
    }

    public final void init(AttributeSet attributeSet, int i) {
        if (!isInEditMode()) {
            if (attributeSet != null) {
                TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.SudIllustration, i, 0);
                this.aspectRatio = obtainStyledAttributes.getFloat(0, 0.0f);
                obtainStyledAttributes.recycle();
            }
            this.baselineGridSize = getResources().getDisplayMetrics().density * 8.0f;
            setWillNotDraw(false);
        }
    }

    public Illustration(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet, 0);
    }

    @TargetApi(11)
    public Illustration(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet, i);
    }
}
