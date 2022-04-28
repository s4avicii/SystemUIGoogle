package com.google.android.material.floatingactionbutton;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import androidx.core.graphics.ColorUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearancePathProvider;
import java.util.Objects;

public final class BorderDrawable extends Drawable {
    public ColorStateList borderTint;
    public float borderWidth;
    public int bottomInnerStrokeColor;
    public int bottomOuterStrokeColor;
    public final RectF boundsRectF = new RectF();
    public int currentBorderTintColor;
    public boolean invalidateShader = true;
    public final Paint paint;
    public final ShapeAppearancePathProvider pathProvider = ShapeAppearancePathProvider.Lazy.INSTANCE;
    public final Rect rect = new Rect();
    public final RectF rectF = new RectF();
    public ShapeAppearanceModel shapeAppearanceModel;
    public final Path shapePath = new Path();
    public final BorderState state = new BorderState();
    public int topInnerStrokeColor;
    public int topOuterStrokeColor;

    public class BorderState extends Drawable.ConstantState {
        public final int getChangingConfigurations() {
            return 0;
        }

        public BorderState() {
        }

        public final Drawable newDrawable() {
            return BorderDrawable.this;
        }
    }

    public final void onBoundsChange(Rect rect2) {
        this.invalidateShader = true;
    }

    public final void draw(Canvas canvas) {
        if (this.invalidateShader) {
            Paint paint2 = this.paint;
            Rect rect2 = this.rect;
            copyBounds(rect2);
            float height = this.borderWidth / ((float) rect2.height());
            paint2.setShader(new LinearGradient(0.0f, (float) rect2.top, 0.0f, (float) rect2.bottom, new int[]{ColorUtils.compositeColors(this.topOuterStrokeColor, this.currentBorderTintColor), ColorUtils.compositeColors(this.topInnerStrokeColor, this.currentBorderTintColor), ColorUtils.compositeColors(ColorUtils.setAlphaComponent(this.topInnerStrokeColor, 0), this.currentBorderTintColor), ColorUtils.compositeColors(ColorUtils.setAlphaComponent(this.bottomInnerStrokeColor, 0), this.currentBorderTintColor), ColorUtils.compositeColors(this.bottomInnerStrokeColor, this.currentBorderTintColor), ColorUtils.compositeColors(this.bottomOuterStrokeColor, this.currentBorderTintColor)}, new float[]{0.0f, height, 0.5f, 0.5f, 1.0f - height, 1.0f}, Shader.TileMode.CLAMP));
            this.invalidateShader = false;
        }
        float strokeWidth = this.paint.getStrokeWidth() / 2.0f;
        copyBounds(this.rect);
        this.rectF.set(this.rect);
        ShapeAppearanceModel shapeAppearanceModel2 = this.shapeAppearanceModel;
        Objects.requireNonNull(shapeAppearanceModel2);
        float min = Math.min(shapeAppearanceModel2.topLeftCornerSize.getCornerSize(getBoundsAsRectF()), this.rectF.width() / 2.0f);
        if (this.shapeAppearanceModel.isRoundRect(getBoundsAsRectF())) {
            this.rectF.inset(strokeWidth, strokeWidth);
            canvas.drawRoundRect(this.rectF, min, min, this.paint);
        }
    }

    public final RectF getBoundsAsRectF() {
        this.boundsRectF.set(getBounds());
        return this.boundsRectF;
    }

    public final int getOpacity() {
        if (this.borderWidth > 0.0f) {
            return -3;
        }
        return -2;
    }

    @TargetApi(21)
    public final void getOutline(Outline outline) {
        if (this.shapeAppearanceModel.isRoundRect(getBoundsAsRectF())) {
            ShapeAppearanceModel shapeAppearanceModel2 = this.shapeAppearanceModel;
            Objects.requireNonNull(shapeAppearanceModel2);
            outline.setRoundRect(getBounds(), shapeAppearanceModel2.topLeftCornerSize.getCornerSize(getBoundsAsRectF()));
            return;
        }
        copyBounds(this.rect);
        this.rectF.set(this.rect);
        ShapeAppearancePathProvider shapeAppearancePathProvider = this.pathProvider;
        ShapeAppearanceModel shapeAppearanceModel3 = this.shapeAppearanceModel;
        RectF rectF2 = this.rectF;
        Path path = this.shapePath;
        Objects.requireNonNull(shapeAppearancePathProvider);
        shapeAppearancePathProvider.calculatePath(shapeAppearanceModel3, 1.0f, rectF2, (MaterialShapeDrawable.C20781) null, path);
        if (this.shapePath.isConvex()) {
            outline.setConvexPath(this.shapePath);
        }
    }

    public final boolean getPadding(Rect rect2) {
        if (!this.shapeAppearanceModel.isRoundRect(getBoundsAsRectF())) {
            return true;
        }
        int round = Math.round(this.borderWidth);
        rect2.set(round, round, round, round);
        return true;
    }

    public final boolean isStateful() {
        ColorStateList colorStateList = this.borderTint;
        if ((colorStateList == null || !colorStateList.isStateful()) && !super.isStateful()) {
            return false;
        }
        return true;
    }

    public final boolean onStateChange(int[] iArr) {
        int colorForState;
        ColorStateList colorStateList = this.borderTint;
        if (!(colorStateList == null || (colorForState = colorStateList.getColorForState(iArr, this.currentBorderTintColor)) == this.currentBorderTintColor)) {
            this.invalidateShader = true;
            this.currentBorderTintColor = colorForState;
        }
        if (this.invalidateShader) {
            invalidateSelf();
        }
        return this.invalidateShader;
    }

    public final void setAlpha(int i) {
        this.paint.setAlpha(i);
        invalidateSelf();
    }

    public final void setColorFilter(ColorFilter colorFilter) {
        this.paint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public BorderDrawable(ShapeAppearanceModel shapeAppearanceModel2) {
        this.shapeAppearanceModel = shapeAppearanceModel2;
        Paint paint2 = new Paint(1);
        this.paint = paint2;
        paint2.setStyle(Paint.Style.STROKE);
    }

    public final Drawable.ConstantState getConstantState() {
        return this.state;
    }
}
