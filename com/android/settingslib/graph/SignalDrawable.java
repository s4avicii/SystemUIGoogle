package com.android.settingslib.graph;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.DrawableWrapper;
import android.os.Handler;
import android.telephony.CellSignalStrength;
import android.util.PathParser;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.Utils;

public final class SignalDrawable extends DrawableWrapper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mAnimating;
    public final Path mAttributionPath;
    public final Matrix mAttributionScaleMatrix;
    public final C05991 mChangeDot;
    public int mCurrentDot;
    public final float mCutoutHeightFraction;
    public final Path mCutoutPath;
    public final float mCutoutWidthFraction;
    public float mDarkIntensity;
    public final Paint mForegroundPaint = new Paint(1);
    public final Path mForegroundPath;
    public final Handler mHandler;
    public final int mIntrinsicSize;
    public final Path mScaledAttributionPath;
    public final Paint mTransparentPaint;

    public final void draw(Canvas canvas) {
        boolean z;
        boolean z2;
        canvas.saveLayer((RectF) null, (Paint) null);
        float width = (float) getBounds().width();
        float height = (float) getBounds().height();
        boolean z3 = false;
        if (getLayoutDirection() == 1) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            canvas.save();
            canvas.translate(width, 0.0f);
            canvas.scale(-1.0f, 1.0f);
        }
        super.draw(canvas);
        this.mCutoutPath.reset();
        this.mCutoutPath.setFillType(Path.FillType.WINDING);
        float round = (float) Math.round(0.083333336f * width);
        if (((getLevel() & 16711680) >> 16) == 3) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            float f = 0.125f * height;
            float f2 = 0.0625f * height;
            float f3 = f2 + f;
            float f4 = (width - round) - f;
            this.mForegroundPath.reset();
            float f5 = (height - round) - f;
            float f6 = f2;
            float f7 = f;
            drawDotAndPadding(f4, f5, f6, f7, 2);
            drawDotAndPadding(f4 - f3, f5, f6, f7, 1);
            drawDotAndPadding(f4 - (f3 * 2.0f), f5, f6, f7, 0);
            canvas.drawPath(this.mCutoutPath, this.mTransparentPaint);
            canvas.drawPath(this.mForegroundPath, this.mForegroundPaint);
        } else {
            if (((getLevel() & 16711680) >> 16) == 2) {
                z3 = true;
            }
            if (z3) {
                float f8 = (this.mCutoutWidthFraction * width) / 24.0f;
                float f9 = (this.mCutoutHeightFraction * height) / 24.0f;
                this.mCutoutPath.moveTo(width, height);
                this.mCutoutPath.rLineTo(-f8, 0.0f);
                this.mCutoutPath.rLineTo(0.0f, -f9);
                this.mCutoutPath.rLineTo(f8, 0.0f);
                this.mCutoutPath.rLineTo(0.0f, f9);
                canvas.drawPath(this.mCutoutPath, this.mTransparentPaint);
                canvas.drawPath(this.mScaledAttributionPath, this.mForegroundPaint);
            }
        }
        if (z) {
            canvas.restore();
        }
        canvas.restore();
    }

    public final void drawDotAndPadding(float f, float f2, float f3, float f4, int i) {
        if (i == this.mCurrentDot) {
            float f5 = f + f4;
            float f6 = f2 + f4;
            this.mForegroundPath.addRect(f, f2, f5, f6, Path.Direction.CW);
            this.mCutoutPath.addRect(f - f3, f2 - f3, f5 + f3, f6 + f3, Path.Direction.CW);
        }
    }

    public SignalDrawable(Context context) {
        super(context.getDrawable(17302845));
        Paint paint = new Paint(1);
        this.mTransparentPaint = paint;
        this.mCutoutPath = new Path();
        this.mForegroundPath = new Path();
        Path path = new Path();
        this.mAttributionPath = path;
        this.mAttributionScaleMatrix = new Matrix();
        this.mScaledAttributionPath = new Path();
        this.mDarkIntensity = -1.0f;
        this.mChangeDot = new Runnable() {
            public final void run() {
                SignalDrawable signalDrawable = SignalDrawable.this;
                int i = signalDrawable.mCurrentDot + 1;
                signalDrawable.mCurrentDot = i;
                if (i == 3) {
                    signalDrawable.mCurrentDot = 0;
                }
                signalDrawable.invalidateSelf();
                SignalDrawable signalDrawable2 = SignalDrawable.this;
                signalDrawable2.mHandler.postDelayed(signalDrawable2.mChangeDot, 1000);
            }
        };
        path.set(PathParser.createPathFromPathData(context.getString(17040023)));
        updateScaledAttributionPath();
        this.mCutoutWidthFraction = context.getResources().getFloat(17105113);
        this.mCutoutHeightFraction = context.getResources().getFloat(17105112);
        int colorStateListDefaultColor = Utils.getColorStateListDefaultColor(context, C1777R.color.dark_mode_icon_color_single_tone);
        int colorStateListDefaultColor2 = Utils.getColorStateListDefaultColor(context, C1777R.color.light_mode_icon_color_single_tone);
        this.mIntrinsicSize = context.getResources().getDimensionPixelSize(C1777R.dimen.signal_icon_size);
        paint.setColor(context.getColor(17170445));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        this.mHandler = new Handler();
        if (0.0f != this.mDarkIntensity) {
            setTintList(ColorStateList.valueOf(((Integer) ArgbEvaluator.getInstance().evaluate(0.0f, Integer.valueOf(colorStateListDefaultColor2), Integer.valueOf(colorStateListDefaultColor))).intValue()));
        }
    }

    public final void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        updateScaledAttributionPath();
        invalidateSelf();
    }

    public final boolean onLevelChange(int i) {
        int i2;
        if (((65280 & i) >> 8) == CellSignalStrength.getNumSignalStrengthLevels() + 1) {
            i2 = 10;
        } else {
            i2 = 0;
        }
        super.onLevelChange((i & 255) + i2);
        updateAnimation();
        setTintList(ColorStateList.valueOf(this.mForegroundPaint.getColor()));
        invalidateSelf();
        return true;
    }

    public final void setAlpha(int i) {
        super.setAlpha(i);
        this.mForegroundPaint.setAlpha(i);
    }

    public final void setColorFilter(ColorFilter colorFilter) {
        super.setColorFilter(colorFilter);
        this.mForegroundPaint.setColorFilter(colorFilter);
    }

    public final void setTintList(ColorStateList colorStateList) {
        super.setTintList(colorStateList);
        int color = this.mForegroundPaint.getColor();
        this.mForegroundPaint.setColor(colorStateList.getDefaultColor());
        if (color != this.mForegroundPaint.getColor()) {
            invalidateSelf();
        }
    }

    public final boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        updateAnimation();
        return visible;
    }

    public final void updateAnimation() {
        boolean z;
        boolean z2 = true;
        if (((getLevel() & 16711680) >> 16) == 3) {
            z = true;
        } else {
            z = false;
        }
        if (!z || !isVisible()) {
            z2 = false;
        }
        if (z2 != this.mAnimating) {
            this.mAnimating = z2;
            if (z2) {
                this.mChangeDot.run();
            } else {
                this.mHandler.removeCallbacks(this.mChangeDot);
            }
        }
    }

    public final void updateScaledAttributionPath() {
        if (getBounds().isEmpty()) {
            this.mAttributionScaleMatrix.setScale(1.0f, 1.0f);
        } else {
            this.mAttributionScaleMatrix.setScale(((float) getBounds().width()) / 24.0f, ((float) getBounds().height()) / 24.0f);
        }
        this.mAttributionPath.transform(this.mAttributionScaleMatrix, this.mScaledAttributionPath);
    }

    public final int getIntrinsicHeight() {
        return this.mIntrinsicSize;
    }

    public final int getIntrinsicWidth() {
        return this.mIntrinsicSize;
    }
}
