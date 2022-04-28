package com.android.systemui.navigationbar.buttons;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.util.FloatProperty;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

public final class KeyButtonDrawable extends Drawable {
    public static final C09361 KEY_DRAWABLE_ROTATE = new FloatProperty<KeyButtonDrawable>() {
        public final Object get(Object obj) {
            KeyButtonDrawable keyButtonDrawable = (KeyButtonDrawable) obj;
            Objects.requireNonNull(keyButtonDrawable);
            return Float.valueOf(keyButtonDrawable.mState.mRotateDegrees);
        }

        public final void setValue(Object obj, float f) {
            ((KeyButtonDrawable) obj).setRotation(f);
        }
    };
    public static final C09372 KEY_DRAWABLE_TRANSLATE_Y = new FloatProperty<KeyButtonDrawable>() {
        public final Object get(Object obj) {
            KeyButtonDrawable keyButtonDrawable = (KeyButtonDrawable) obj;
            Objects.requireNonNull(keyButtonDrawable);
            return Float.valueOf(keyButtonDrawable.mState.mTranslationY);
        }

        public final void setValue(Object obj, float f) {
            KeyButtonDrawable keyButtonDrawable = (KeyButtonDrawable) obj;
            Objects.requireNonNull(keyButtonDrawable);
            ShadowDrawableState shadowDrawableState = keyButtonDrawable.mState;
            float f2 = shadowDrawableState.mTranslationX;
            if (f2 != f2 || shadowDrawableState.mTranslationY != f) {
                shadowDrawableState.mTranslationX = f2;
                shadowDrawableState.mTranslationY = f;
                keyButtonDrawable.invalidateSelf();
            }
        }
    };
    public AnimatedVectorDrawable mAnimatedDrawable;
    public final C09383 mAnimatedDrawableCallback;
    public final Paint mIconPaint = new Paint(3);
    public final Paint mShadowPaint = new Paint(3);
    public final ShadowDrawableState mState;

    public static class ShadowDrawableState extends Drawable.ConstantState {
        public int mAlpha = 255;
        public int mBaseHeight;
        public int mBaseWidth;
        public int mChangingConfigurations;
        public Drawable.ConstantState mChildState;
        public final int mDarkColor;
        public float mDarkIntensity;
        public boolean mHorizontalFlip;
        public boolean mIsHardwareBitmap;
        public Bitmap mLastDrawnIcon;
        public Bitmap mLastDrawnShadow;
        public final int mLightColor;
        public final Color mOvalBackgroundColor;
        public float mRotateDegrees;
        public int mShadowColor;
        public int mShadowOffsetX;
        public int mShadowOffsetY;
        public int mShadowSize;
        public final boolean mSupportsAnimation;
        public float mTranslationX;
        public float mTranslationY;

        public final boolean canApplyTheme() {
            return true;
        }

        public final Drawable newDrawable() {
            return new KeyButtonDrawable((Drawable) null, this);
        }

        public ShadowDrawableState(int i, int i2, boolean z, boolean z2) {
            this.mLightColor = i;
            this.mDarkColor = i2;
            this.mSupportsAnimation = z;
            this.mHorizontalFlip = z2;
            this.mOvalBackgroundColor = null;
        }

        public final int getChangingConfigurations() {
            return this.mChangingConfigurations;
        }
    }

    public final int getOpacity() {
        return -3;
    }

    public final boolean canApplyTheme() {
        Objects.requireNonNull(this.mState);
        return true;
    }

    public final int getIntrinsicHeight() {
        ShadowDrawableState shadowDrawableState = this.mState;
        return ((Math.abs(shadowDrawableState.mShadowOffsetY) + shadowDrawableState.mShadowSize) * 2) + shadowDrawableState.mBaseHeight;
    }

    public final int getIntrinsicWidth() {
        ShadowDrawableState shadowDrawableState = this.mState;
        return ((Math.abs(shadowDrawableState.mShadowOffsetX) + shadowDrawableState.mShadowSize) * 2) + shadowDrawableState.mBaseWidth;
    }

    public final void setAlpha(int i) {
        this.mState.mAlpha = i;
        this.mIconPaint.setAlpha(i);
        updateShadowAlpha();
        invalidateSelf();
    }

    public final void setColorFilter(ColorFilter colorFilter) {
        boolean z;
        this.mIconPaint.setColorFilter(colorFilter);
        AnimatedVectorDrawable animatedVectorDrawable = this.mAnimatedDrawable;
        if (animatedVectorDrawable != null) {
            if (this.mState.mOvalBackgroundColor != null) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                animatedVectorDrawable.setColorFilter(new PorterDuffColorFilter(this.mState.mLightColor, PorterDuff.Mode.SRC_IN));
            } else {
                animatedVectorDrawable.setColorFilter(colorFilter);
            }
        }
        invalidateSelf();
    }

    public final void setDarkIntensity(float f) {
        this.mState.mDarkIntensity = f;
        int intValue = ((Integer) ArgbEvaluator.getInstance().evaluate(f, Integer.valueOf(this.mState.mLightColor), Integer.valueOf(this.mState.mDarkColor))).intValue();
        updateShadowAlpha();
        setColorFilter(new PorterDuffColorFilter(intValue, PorterDuff.Mode.SRC_ATOP));
    }

    public final void setDrawableBounds(Drawable drawable) {
        ShadowDrawableState shadowDrawableState = this.mState;
        int abs = Math.abs(shadowDrawableState.mShadowOffsetX) + shadowDrawableState.mShadowSize;
        ShadowDrawableState shadowDrawableState2 = this.mState;
        int abs2 = Math.abs(shadowDrawableState2.mShadowOffsetY) + shadowDrawableState2.mShadowSize;
        drawable.setBounds(abs, abs2, getIntrinsicWidth() - abs, getIntrinsicHeight() - abs2);
    }

    public final void setRotation(float f) {
        ShadowDrawableState shadowDrawableState = this.mState;
        if (!shadowDrawableState.mSupportsAnimation && shadowDrawableState.mRotateDegrees != f) {
            shadowDrawableState.mRotateDegrees = f;
            invalidateSelf();
        }
    }

    public final void updateShadowAlpha() {
        int alpha = Color.alpha(this.mState.mShadowColor);
        Paint paint = this.mShadowPaint;
        ShadowDrawableState shadowDrawableState = this.mState;
        paint.setAlpha(Math.round((1.0f - shadowDrawableState.mDarkIntensity) * (((float) shadowDrawableState.mAlpha) / 255.0f) * ((float) alpha)));
    }

    public KeyButtonDrawable(Drawable drawable, ShadowDrawableState shadowDrawableState) {
        C09383 r0 = new Drawable.Callback() {
            public final void invalidateDrawable(Drawable drawable) {
                KeyButtonDrawable.this.invalidateSelf();
            }

            public final void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
                KeyButtonDrawable.this.scheduleSelf(runnable, j);
            }

            public final void unscheduleDrawable(Drawable drawable, Runnable runnable) {
                KeyButtonDrawable.this.unscheduleSelf(runnable);
            }
        };
        this.mAnimatedDrawableCallback = r0;
        this.mState = shadowDrawableState;
        if (drawable != null) {
            shadowDrawableState.mBaseHeight = drawable.getIntrinsicHeight();
            shadowDrawableState.mBaseWidth = drawable.getIntrinsicWidth();
            shadowDrawableState.mChangingConfigurations = drawable.getChangingConfigurations();
            shadowDrawableState.mChildState = drawable.getConstantState();
        }
        if (shadowDrawableState.mSupportsAnimation) {
            AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) shadowDrawableState.mChildState.newDrawable().mutate();
            this.mAnimatedDrawable = animatedVectorDrawable;
            animatedVectorDrawable.setCallback(r0);
            setDrawableBounds(this.mAnimatedDrawable);
        }
    }

    public static KeyButtonDrawable create(Context context, int i, int i2, int i3, boolean z) {
        boolean z2;
        Resources resources = context.getResources();
        boolean z3 = false;
        if (resources.getConfiguration().getLayoutDirection() == 1) {
            z2 = true;
        } else {
            z2 = false;
        }
        Drawable drawable = context.getDrawable(i3);
        if (z2 && drawable.isAutoMirrored()) {
            z3 = true;
        }
        KeyButtonDrawable keyButtonDrawable = new KeyButtonDrawable(drawable, new ShadowDrawableState(i, i2, drawable instanceof AnimatedVectorDrawable, z3));
        if (z) {
            int dimensionPixelSize = resources.getDimensionPixelSize(C1777R.dimen.nav_key_button_shadow_offset_x);
            int dimensionPixelSize2 = resources.getDimensionPixelSize(C1777R.dimen.nav_key_button_shadow_offset_y);
            int dimensionPixelSize3 = resources.getDimensionPixelSize(C1777R.dimen.nav_key_button_shadow_radius);
            int color = context.getColor(C1777R.color.nav_key_button_shadow_color);
            ShadowDrawableState shadowDrawableState = keyButtonDrawable.mState;
            if (!shadowDrawableState.mSupportsAnimation && !(shadowDrawableState.mShadowOffsetX == dimensionPixelSize && shadowDrawableState.mShadowOffsetY == dimensionPixelSize2 && shadowDrawableState.mShadowSize == dimensionPixelSize3 && shadowDrawableState.mShadowColor == color)) {
                shadowDrawableState.mShadowOffsetX = dimensionPixelSize;
                shadowDrawableState.mShadowOffsetY = dimensionPixelSize2;
                shadowDrawableState.mShadowSize = dimensionPixelSize3;
                shadowDrawableState.mShadowColor = color;
                keyButtonDrawable.mShadowPaint.setColorFilter(new PorterDuffColorFilter(keyButtonDrawable.mState.mShadowColor, PorterDuff.Mode.SRC_ATOP));
                keyButtonDrawable.updateShadowAlpha();
                keyButtonDrawable.invalidateSelf();
            }
        }
        return keyButtonDrawable;
    }

    public final void draw(Canvas canvas) {
        boolean z;
        Rect bounds = getBounds();
        if (!bounds.isEmpty()) {
            AnimatedVectorDrawable animatedVectorDrawable = this.mAnimatedDrawable;
            if (animatedVectorDrawable != null) {
                animatedVectorDrawable.draw(canvas);
                return;
            }
            if (this.mState.mIsHardwareBitmap != canvas.isHardwareAccelerated()) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                this.mState.mIsHardwareBitmap = canvas.isHardwareAccelerated();
            }
            if (this.mState.mLastDrawnIcon == null || z) {
                int intrinsicWidth = getIntrinsicWidth();
                int intrinsicHeight = getIntrinsicHeight();
                Bitmap createBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888);
                Canvas canvas2 = new Canvas(createBitmap);
                Drawable mutate = this.mState.mChildState.newDrawable().mutate();
                setDrawableBounds(mutate);
                canvas2.save();
                if (this.mState.mHorizontalFlip) {
                    canvas2.scale(-1.0f, 1.0f, ((float) intrinsicWidth) * 0.5f, ((float) intrinsicHeight) * 0.5f);
                }
                mutate.draw(canvas2);
                canvas2.restore();
                if (this.mState.mIsHardwareBitmap) {
                    createBitmap = createBitmap.copy(Bitmap.Config.HARDWARE, false);
                }
                this.mState.mLastDrawnIcon = createBitmap;
            }
            canvas.save();
            ShadowDrawableState shadowDrawableState = this.mState;
            canvas.translate(shadowDrawableState.mTranslationX, shadowDrawableState.mTranslationY);
            canvas.rotate(this.mState.mRotateDegrees, (float) (getIntrinsicWidth() / 2), (float) (getIntrinsicHeight() / 2));
            ShadowDrawableState shadowDrawableState2 = this.mState;
            int i = shadowDrawableState2.mShadowSize;
            if (i > 0) {
                if (shadowDrawableState2.mLastDrawnShadow == null || z) {
                    if (i == 0) {
                        shadowDrawableState2.mLastDrawnIcon = null;
                    } else {
                        int intrinsicWidth2 = getIntrinsicWidth();
                        int intrinsicHeight2 = getIntrinsicHeight();
                        Bitmap createBitmap2 = Bitmap.createBitmap(intrinsicWidth2, intrinsicHeight2, Bitmap.Config.ARGB_8888);
                        Canvas canvas3 = new Canvas(createBitmap2);
                        Drawable mutate2 = this.mState.mChildState.newDrawable().mutate();
                        setDrawableBounds(mutate2);
                        canvas3.save();
                        if (this.mState.mHorizontalFlip) {
                            canvas3.scale(-1.0f, 1.0f, ((float) intrinsicWidth2) * 0.5f, ((float) intrinsicHeight2) * 0.5f);
                        }
                        mutate2.draw(canvas3);
                        canvas3.restore();
                        Paint paint = new Paint(3);
                        paint.setMaskFilter(new BlurMaskFilter((float) this.mState.mShadowSize, BlurMaskFilter.Blur.NORMAL));
                        int[] iArr = new int[2];
                        Bitmap extractAlpha = createBitmap2.extractAlpha(paint, iArr);
                        paint.setMaskFilter((MaskFilter) null);
                        createBitmap2.eraseColor(0);
                        canvas3.drawBitmap(extractAlpha, (float) iArr[0], (float) iArr[1], paint);
                        if (this.mState.mIsHardwareBitmap) {
                            createBitmap2 = createBitmap2.copy(Bitmap.Config.HARDWARE, false);
                        }
                        this.mState.mLastDrawnShadow = createBitmap2;
                    }
                }
                double d = (double) ((float) ((((double) this.mState.mRotateDegrees) * 3.141592653589793d) / 180.0d));
                double sin = Math.sin(d) * ((double) this.mState.mShadowOffsetY);
                double cos = Math.cos(d);
                ShadowDrawableState shadowDrawableState3 = this.mState;
                float f = ((float) ((cos * ((double) shadowDrawableState3.mShadowOffsetX)) + sin)) - shadowDrawableState3.mTranslationX;
                double cos2 = Math.cos(d) * ((double) this.mState.mShadowOffsetY);
                double sin2 = Math.sin(d);
                ShadowDrawableState shadowDrawableState4 = this.mState;
                canvas.drawBitmap(shadowDrawableState4.mLastDrawnShadow, f, ((float) (cos2 - (sin2 * ((double) shadowDrawableState4.mShadowOffsetX)))) - shadowDrawableState4.mTranslationY, this.mShadowPaint);
            }
            canvas.drawBitmap(this.mState.mLastDrawnIcon, (Rect) null, bounds, this.mIconPaint);
            canvas.restore();
        }
    }

    public final void jumpToCurrentState() {
        super.jumpToCurrentState();
        AnimatedVectorDrawable animatedVectorDrawable = this.mAnimatedDrawable;
        if (animatedVectorDrawable != null) {
            animatedVectorDrawable.jumpToCurrentState();
        }
    }

    public final boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        if (visible) {
            jumpToCurrentState();
        }
        return visible;
    }

    public final Drawable.ConstantState getConstantState() {
        return this.mState;
    }
}
