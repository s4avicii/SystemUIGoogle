package androidx.core.graphics.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

public abstract class RoundedBitmapDrawable extends Drawable {
    public boolean mApplyGravity = true;
    public final Bitmap mBitmap;
    public int mBitmapHeight;
    public final BitmapShader mBitmapShader;
    public int mBitmapWidth;
    public float mCornerRadius;
    public final Rect mDstRect = new Rect();
    public final RectF mDstRectF = new RectF();
    public int mGravity = 119;
    public boolean mIsCircular;
    public final Paint mPaint = new Paint(3);
    public final Matrix mShaderMatrix = new Matrix();
    public int mTargetDensity = 160;

    public abstract void gravityCompatApply(int i, int i2, int i3, Rect rect, Rect rect2);

    public final void draw(Canvas canvas) {
        Bitmap bitmap = this.mBitmap;
        if (bitmap != null) {
            updateDstRect();
            if (this.mPaint.getShader() == null) {
                canvas.drawBitmap(bitmap, (Rect) null, this.mDstRect, this.mPaint);
                return;
            }
            RectF rectF = this.mDstRectF;
            float f = this.mCornerRadius;
            canvas.drawRoundRect(rectF, f, f, this.mPaint);
        }
    }

    public final int getAlpha() {
        return this.mPaint.getAlpha();
    }

    public final ColorFilter getColorFilter() {
        return this.mPaint.getColorFilter();
    }

    public final int getOpacity() {
        Bitmap bitmap;
        boolean z;
        if (this.mGravity == 119 && !this.mIsCircular && (bitmap = this.mBitmap) != null && !bitmap.hasAlpha() && this.mPaint.getAlpha() >= 255) {
            if (this.mCornerRadius > 0.05f) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                return -3;
            }
            return -1;
        }
        return -3;
    }

    public final void setAlpha(int i) {
        if (i != this.mPaint.getAlpha()) {
            this.mPaint.setAlpha(i);
            invalidateSelf();
        }
    }

    public final void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public final void setCornerRadius(float f) {
        if (this.mCornerRadius != f) {
            boolean z = false;
            this.mIsCircular = false;
            if (f > 0.05f) {
                z = true;
            }
            if (z) {
                this.mPaint.setShader(this.mBitmapShader);
            } else {
                this.mPaint.setShader((Shader) null);
            }
            this.mCornerRadius = f;
            invalidateSelf();
        }
    }

    public final void setDither(boolean z) {
        this.mPaint.setDither(z);
        invalidateSelf();
    }

    public final void setFilterBitmap(boolean z) {
        this.mPaint.setFilterBitmap(z);
        invalidateSelf();
    }

    public final void updateDstRect() {
        if (this.mApplyGravity) {
            if (this.mIsCircular) {
                int min = Math.min(this.mBitmapWidth, this.mBitmapHeight);
                gravityCompatApply(this.mGravity, min, min, getBounds(), this.mDstRect);
                int min2 = Math.min(this.mDstRect.width(), this.mDstRect.height());
                this.mDstRect.inset(Math.max(0, (this.mDstRect.width() - min2) / 2), Math.max(0, (this.mDstRect.height() - min2) / 2));
                this.mCornerRadius = ((float) min2) * 0.5f;
            } else {
                gravityCompatApply(this.mGravity, this.mBitmapWidth, this.mBitmapHeight, getBounds(), this.mDstRect);
            }
            this.mDstRectF.set(this.mDstRect);
            if (this.mBitmapShader != null) {
                Matrix matrix = this.mShaderMatrix;
                RectF rectF = this.mDstRectF;
                matrix.setTranslate(rectF.left, rectF.top);
                this.mShaderMatrix.preScale(this.mDstRectF.width() / ((float) this.mBitmap.getWidth()), this.mDstRectF.height() / ((float) this.mBitmap.getHeight()));
                this.mBitmapShader.setLocalMatrix(this.mShaderMatrix);
                this.mPaint.setShader(this.mBitmapShader);
            }
            this.mApplyGravity = false;
        }
    }

    public RoundedBitmapDrawable(Resources resources, Bitmap bitmap) {
        if (resources != null) {
            this.mTargetDensity = resources.getDisplayMetrics().densityDpi;
        }
        this.mBitmap = bitmap;
        if (bitmap != null) {
            this.mBitmapWidth = bitmap.getScaledWidth(this.mTargetDensity);
            this.mBitmapHeight = bitmap.getScaledHeight(this.mTargetDensity);
            Shader.TileMode tileMode = Shader.TileMode.CLAMP;
            this.mBitmapShader = new BitmapShader(bitmap, tileMode, tileMode);
            return;
        }
        this.mBitmapHeight = -1;
        this.mBitmapWidth = -1;
        this.mBitmapShader = null;
    }

    public final void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        if (this.mIsCircular) {
            this.mCornerRadius = (float) (Math.min(this.mBitmapHeight, this.mBitmapWidth) / 2);
        }
        this.mApplyGravity = true;
    }

    public final int getIntrinsicHeight() {
        return this.mBitmapHeight;
    }

    public final int getIntrinsicWidth() {
        return this.mBitmapWidth;
    }
}
