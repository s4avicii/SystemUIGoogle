package com.google.android.material.tooltip;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import com.google.android.material.internal.TextDrawableHelper;
import com.google.android.material.shape.MarkerEdgeTreatment;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.OffsetEdgeTreatment;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.util.Objects;

public final class TooltipDrawable extends MaterialShapeDrawable implements TextDrawableHelper.TextDrawableDelegate {
    public int arrowSize;
    public final C21341 attachedViewLayoutChangeListener;
    public final Context context;
    public final Rect displayFrame;
    public final Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
    public float labelOpacity;
    public int layoutMargin;
    public int locationOnScreenX;
    public int minHeight;
    public int minWidth;
    public int padding;
    public CharSequence text;
    public final TextDrawableHelper textDrawableHelper;
    public float tooltipPivotY;
    public float tooltipScaleX;
    public float tooltipScaleY;

    public TooltipDrawable(Context context2, int i) {
        super(context2, (AttributeSet) null, 0, i);
        TextDrawableHelper textDrawableHelper2 = new TextDrawableHelper(this);
        this.textDrawableHelper = textDrawableHelper2;
        this.attachedViewLayoutChangeListener = new View.OnLayoutChangeListener() {
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                TooltipDrawable tooltipDrawable = TooltipDrawable.this;
                Objects.requireNonNull(tooltipDrawable);
                int[] iArr = new int[2];
                view.getLocationOnScreen(iArr);
                tooltipDrawable.locationOnScreenX = iArr[0];
                view.getWindowVisibleDisplayFrame(tooltipDrawable.displayFrame);
            }
        };
        this.displayFrame = new Rect();
        this.tooltipScaleX = 1.0f;
        this.tooltipScaleY = 1.0f;
        this.tooltipPivotY = 0.5f;
        this.labelOpacity = 1.0f;
        this.context = context2;
        textDrawableHelper2.textPaint.density = context2.getResources().getDisplayMetrics().density;
        textDrawableHelper2.textPaint.setTextAlign(Paint.Align.CENTER);
    }

    public final float calculatePointerOffset() {
        int i;
        if (((this.displayFrame.right - getBounds().right) - this.locationOnScreenX) - this.layoutMargin < 0) {
            i = ((this.displayFrame.right - getBounds().right) - this.locationOnScreenX) - this.layoutMargin;
        } else if (((this.displayFrame.left - getBounds().left) - this.locationOnScreenX) + this.layoutMargin <= 0) {
            return 0.0f;
        } else {
            i = ((this.displayFrame.left - getBounds().left) - this.locationOnScreenX) + this.layoutMargin;
        }
        return (float) i;
    }

    public final int getIntrinsicHeight() {
        TextDrawableHelper textDrawableHelper2 = this.textDrawableHelper;
        Objects.requireNonNull(textDrawableHelper2);
        return (int) Math.max(textDrawableHelper2.textPaint.getTextSize(), (float) this.minHeight);
    }

    public final int getIntrinsicWidth() {
        float f;
        float f2 = (float) (this.padding * 2);
        CharSequence charSequence = this.text;
        if (charSequence == null) {
            f = 0.0f;
        } else {
            f = this.textDrawableHelper.getTextWidth(charSequence.toString());
        }
        return (int) Math.max(f2 + f, (float) this.minWidth);
    }

    public final OffsetEdgeTreatment createMarkerEdge() {
        float width = ((float) (((double) getBounds().width()) - (Math.sqrt(2.0d) * ((double) this.arrowSize)))) / 2.0f;
        return new OffsetEdgeTreatment(new MarkerEdgeTreatment((float) this.arrowSize), Math.min(Math.max(-calculatePointerOffset(), -width), width));
    }

    public final void draw(Canvas canvas) {
        canvas.save();
        float calculatePointerOffset = calculatePointerOffset();
        double sqrt = Math.sqrt(2.0d);
        canvas.scale(this.tooltipScaleX, this.tooltipScaleY, (((float) getBounds().width()) * 0.5f) + ((float) getBounds().left), (((float) getBounds().height()) * this.tooltipPivotY) + ((float) getBounds().top));
        canvas.translate(calculatePointerOffset, (float) (-((sqrt * ((double) this.arrowSize)) - ((double) this.arrowSize))));
        super.draw(canvas);
        if (this.text != null) {
            Rect bounds = getBounds();
            TextDrawableHelper textDrawableHelper2 = this.textDrawableHelper;
            Objects.requireNonNull(textDrawableHelper2);
            textDrawableHelper2.textPaint.getFontMetrics(this.fontMetrics);
            Paint.FontMetrics fontMetrics2 = this.fontMetrics;
            int centerY = (int) (((float) bounds.centerY()) - ((fontMetrics2.descent + fontMetrics2.ascent) / 2.0f));
            TextDrawableHelper textDrawableHelper3 = this.textDrawableHelper;
            Objects.requireNonNull(textDrawableHelper3);
            if (textDrawableHelper3.textAppearance != null) {
                TextDrawableHelper textDrawableHelper4 = this.textDrawableHelper;
                Objects.requireNonNull(textDrawableHelper4);
                textDrawableHelper4.textPaint.drawableState = getState();
                TextDrawableHelper textDrawableHelper5 = this.textDrawableHelper;
                Context context2 = this.context;
                Objects.requireNonNull(textDrawableHelper5);
                textDrawableHelper5.textAppearance.updateDrawState(context2, textDrawableHelper5.textPaint, textDrawableHelper5.fontCallback);
                TextDrawableHelper textDrawableHelper6 = this.textDrawableHelper;
                Objects.requireNonNull(textDrawableHelper6);
                textDrawableHelper6.textPaint.setAlpha((int) (this.labelOpacity * 255.0f));
            }
            CharSequence charSequence = this.text;
            TextDrawableHelper textDrawableHelper7 = this.textDrawableHelper;
            Objects.requireNonNull(textDrawableHelper7);
            canvas.drawText(charSequence, 0, charSequence.length(), (float) bounds.centerX(), (float) centerY, textDrawableHelper7.textPaint);
        }
        canvas.restore();
    }

    public final void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        ShapeAppearanceModel shapeAppearanceModel = this.drawableState.shapeAppearanceModel;
        Objects.requireNonNull(shapeAppearanceModel);
        ShapeAppearanceModel.Builder builder = new ShapeAppearanceModel.Builder(shapeAppearanceModel);
        builder.bottomEdge = createMarkerEdge();
        setShapeAppearanceModel(new ShapeAppearanceModel(builder));
    }

    public final boolean onStateChange(int[] iArr) {
        return super.onStateChange(iArr);
    }

    public final void onTextSizeChange() {
        invalidateSelf();
    }
}
