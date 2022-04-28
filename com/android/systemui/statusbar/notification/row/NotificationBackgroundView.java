package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.util.AttributeSet;
import android.view.View;
import com.android.internal.util.ArrayUtils;
import com.android.p012wm.shell.C1777R;

public class NotificationBackgroundView extends View {
    public int mActualHeight = -1;
    public int mActualWidth = -1;
    public Drawable mBackground;
    public int mBackgroundTop;
    public boolean mBottomAmountClips = true;
    public boolean mBottomIsRounded;
    public int mClipBottomAmount;
    public int mClipTopAmount;
    public final float[] mCornerRadii = new float[8];
    public final boolean mDontModifyCorners = getResources().getBoolean(C1777R.bool.config_clipNotificationsToOutline);
    public int mDrawableAlpha = 255;
    public int mExpandAnimationHeight = -1;
    public boolean mExpandAnimationRunning;
    public int mExpandAnimationWidth = -1;
    public boolean mIsPressedAllowed;
    public int mTintColor;

    public final boolean hasOverlappingRendering() {
        return false;
    }

    public final void drawableHotspotChanged(float f, float f2) {
        Drawable drawable = this.mBackground;
        if (drawable != null) {
            drawable.setHotspot(f, f2);
        }
    }

    public final int getActualHeight() {
        int i;
        if (this.mExpandAnimationRunning && (i = this.mExpandAnimationHeight) > -1) {
            return i;
        }
        int i2 = this.mActualHeight;
        if (i2 > -1) {
            return i2;
        }
        return getHeight();
    }

    public final void onDraw(Canvas canvas) {
        int i;
        int i2;
        if (this.mClipTopAmount + this.mClipBottomAmount < getActualHeight() - this.mBackgroundTop || this.mExpandAnimationRunning) {
            canvas.save();
            int i3 = 0;
            if (!this.mExpandAnimationRunning) {
                canvas.clipRect(0, this.mClipTopAmount, getWidth(), getActualHeight() - this.mClipBottomAmount);
            }
            Drawable drawable = this.mBackground;
            if (drawable != null) {
                int i4 = this.mBackgroundTop;
                int actualHeight = getActualHeight();
                if (this.mBottomIsRounded && this.mBottomAmountClips && !this.mExpandAnimationRunning) {
                    actualHeight -= this.mClipBottomAmount;
                }
                boolean isLayoutRtl = isLayoutRtl();
                int width = getWidth();
                if ((!this.mExpandAnimationRunning || (i = this.mExpandAnimationWidth) <= -1) && (i = this.mActualWidth) <= -1) {
                    i = getWidth();
                }
                if (isLayoutRtl) {
                    i3 = width - i;
                }
                if (isLayoutRtl) {
                    i2 = width;
                } else {
                    i2 = i;
                }
                if (this.mExpandAnimationRunning) {
                    i3 = (int) (((float) (width - i)) / 2.0f);
                    i2 = i3 + i;
                }
                drawable.setBounds(i3, i4, i2, actualHeight);
                drawable.draw(canvas);
            }
            canvas.restore();
        }
    }

    public final void setCustomBackground$1() {
        Drawable drawable = this.mContext.getDrawable(C1777R.C1778drawable.notification_material_bg);
        Drawable drawable2 = this.mBackground;
        if (drawable2 != null) {
            drawable2.setCallback((Drawable.Callback) null);
            unscheduleDrawable(this.mBackground);
        }
        this.mBackground = drawable;
        drawable.mutate();
        Drawable drawable3 = this.mBackground;
        if (drawable3 != null) {
            drawable3.setCallback(this);
            int i = this.mTintColor;
            if (i != 0) {
                this.mBackground.setColorFilter(i, PorterDuff.Mode.SRC_ATOP);
            } else {
                this.mBackground.clearColorFilter();
            }
            this.mTintColor = i;
            invalidate();
        }
        Drawable drawable4 = this.mBackground;
        if (drawable4 instanceof RippleDrawable) {
            ((RippleDrawable) drawable4).setForceSoftware(true);
        }
        if (!this.mDontModifyCorners) {
            Drawable drawable5 = this.mBackground;
            if (drawable5 instanceof LayerDrawable) {
                ((GradientDrawable) ((LayerDrawable) drawable5).getDrawable(0)).setCornerRadii(this.mCornerRadii);
            }
        }
        invalidate();
    }

    public NotificationBackgroundView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void drawableStateChanged() {
        int[] drawableState = getDrawableState();
        Drawable drawable = this.mBackground;
        if (drawable != null && drawable.isStateful()) {
            if (!this.mIsPressedAllowed) {
                drawableState = ArrayUtils.removeInt(drawableState, 16842919);
            }
            this.mBackground.setState(drawableState);
        }
    }

    public final boolean verifyDrawable(Drawable drawable) {
        if (super.verifyDrawable(drawable) || drawable == this.mBackground) {
            return true;
        }
        return false;
    }
}
