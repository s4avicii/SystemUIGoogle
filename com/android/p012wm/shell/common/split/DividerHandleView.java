package com.android.p012wm.shell.common.split;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.animation.PathInterpolator;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.animation.Interpolators;

/* renamed from: com.android.wm.shell.common.split.DividerHandleView */
public class DividerHandleView extends View {
    public static final C18522 HEIGHT_PROPERTY = new Property<DividerHandleView, Integer>() {
        {
            Class<Integer> cls = Integer.class;
        }

        public final Object get(Object obj) {
            return Integer.valueOf(((DividerHandleView) obj).mCurrentHeight);
        }

        public final void set(Object obj, Object obj2) {
            DividerHandleView dividerHandleView = (DividerHandleView) obj;
            dividerHandleView.mCurrentHeight = ((Integer) obj2).intValue();
            dividerHandleView.invalidate();
        }
    };
    public static final C18511 WIDTH_PROPERTY = new Property<DividerHandleView, Integer>() {
        {
            Class<Integer> cls = Integer.class;
        }

        public final Object get(Object obj) {
            return Integer.valueOf(((DividerHandleView) obj).mCurrentWidth);
        }

        public final void set(Object obj, Object obj2) {
            DividerHandleView dividerHandleView = (DividerHandleView) obj;
            dividerHandleView.mCurrentWidth = ((Integer) obj2).intValue();
            dividerHandleView.invalidate();
        }
    };
    public AnimatorSet mAnimator;
    public int mCurrentHeight;
    public int mCurrentWidth;
    public final int mHeight;
    public final Paint mPaint;
    public boolean mTouching;
    public final int mTouchingHeight;
    public final int mTouchingWidth;
    public final int mWidth;

    public final boolean hasOverlappingRendering() {
        return false;
    }

    public final void setTouching(boolean z, boolean z2) {
        int i;
        int i2;
        long j;
        PathInterpolator pathInterpolator;
        if (z != this.mTouching) {
            AnimatorSet animatorSet = this.mAnimator;
            if (animatorSet != null) {
                animatorSet.cancel();
                this.mAnimator = null;
            }
            if (!z2) {
                if (z) {
                    this.mCurrentWidth = this.mTouchingWidth;
                    this.mCurrentHeight = this.mTouchingHeight;
                } else {
                    this.mCurrentWidth = this.mWidth;
                    this.mCurrentHeight = this.mHeight;
                }
                invalidate();
            } else {
                if (z) {
                    i = this.mTouchingWidth;
                } else {
                    i = this.mWidth;
                }
                if (z) {
                    i2 = this.mTouchingHeight;
                } else {
                    i2 = this.mHeight;
                }
                ObjectAnimator ofInt = ObjectAnimator.ofInt(this, WIDTH_PROPERTY, new int[]{this.mCurrentWidth, i});
                ObjectAnimator ofInt2 = ObjectAnimator.ofInt(this, HEIGHT_PROPERTY, new int[]{this.mCurrentHeight, i2});
                AnimatorSet animatorSet2 = new AnimatorSet();
                this.mAnimator = animatorSet2;
                animatorSet2.playTogether(new Animator[]{ofInt, ofInt2});
                AnimatorSet animatorSet3 = this.mAnimator;
                if (z) {
                    j = 150;
                } else {
                    j = 200;
                }
                animatorSet3.setDuration(j);
                AnimatorSet animatorSet4 = this.mAnimator;
                if (z) {
                    pathInterpolator = Interpolators.TOUCH_RESPONSE;
                } else {
                    pathInterpolator = Interpolators.FAST_OUT_SLOW_IN;
                }
                animatorSet4.setInterpolator(pathInterpolator);
                this.mAnimator.addListener(new AnimatorListenerAdapter() {
                    public final void onAnimationEnd(Animator animator) {
                        DividerHandleView.this.mAnimator = null;
                    }
                });
                this.mAnimator.start();
            }
            this.mTouching = z;
        }
    }

    public DividerHandleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        int i;
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setColor(getResources().getColor(C1777R.color.docked_divider_handle, (Resources.Theme) null));
        paint.setAntiAlias(true);
        int dimensionPixelSize = getResources().getDimensionPixelSize(C1777R.dimen.split_divider_handle_width);
        this.mWidth = dimensionPixelSize;
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(C1777R.dimen.split_divider_handle_height);
        this.mHeight = dimensionPixelSize2;
        this.mCurrentWidth = dimensionPixelSize;
        this.mCurrentHeight = dimensionPixelSize2;
        if (dimensionPixelSize > dimensionPixelSize2) {
            i = dimensionPixelSize / 2;
        } else {
            i = dimensionPixelSize;
        }
        this.mTouchingWidth = i;
        this.mTouchingHeight = dimensionPixelSize2 > dimensionPixelSize ? dimensionPixelSize2 / 2 : dimensionPixelSize2;
    }

    public final void onDraw(Canvas canvas) {
        int width = (getWidth() / 2) - (this.mCurrentWidth / 2);
        int i = this.mCurrentHeight;
        int height = (getHeight() / 2) - (i / 2);
        float min = (float) (Math.min(this.mCurrentWidth, i) / 2);
        canvas.drawRoundRect((float) width, (float) height, (float) (width + this.mCurrentWidth), (float) (height + this.mCurrentHeight), min, min, this.mPaint);
    }
}
