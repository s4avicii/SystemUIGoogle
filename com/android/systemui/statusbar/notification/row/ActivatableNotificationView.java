package com.android.systemui.statusbar.notification.row;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.util.AttributeSet;
import android.util.MathUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.PathInterpolator;
import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.util.ArrayUtils;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.Utils;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.R$array;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.FakeShadowView;
import java.util.Objects;

public abstract class ActivatableNotificationView extends ExpandableOutlineView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mActivated;
    public float mAnimationTranslationY;
    public float mAppearAnimationFraction = -1.0f;
    public float mAppearAnimationTranslation;
    public ValueAnimator mAppearAnimator;
    public ValueAnimator mBackgroundColorAnimator;
    public NotificationBackgroundView mBackgroundNormal;
    public int mBgTint = 0;
    public PathInterpolator mCurrentAppearInterpolator;
    public int mCurrentBackgroundTint;
    public boolean mDismissed;
    public boolean mDrawingAppearAnimation;
    public FakeShadowView mFakeShadow;
    public boolean mIsBelowSpeedBump;
    public boolean mIsHeadsUpAnimation;
    public long mLastActionUpTime;
    public int mNormalColor;
    public int mNormalRippleColor;
    public OnActivatedListener mOnActivatedListener;
    public float mOverrideAmount;
    public int mOverrideTint;
    public boolean mRefocusOnDismiss;
    public boolean mShadowHidden;
    public final PathInterpolator mSlowOutFastInInterpolator = new PathInterpolator(0.8f, 0.0f, 0.6f, 1.0f);
    public int mStartTint;
    public Point mTargetPoint;
    public int mTargetTint;
    public int mTintedRippleColor;
    public Gefingerpoken mTouchHandler;

    public interface OnActivatedListener {
        void onActivationReset(ActivatableNotificationView activatableNotificationView);
    }

    public abstract View getContentView();

    public void onAppearAnimationFinished(boolean z) {
    }

    public void onTap() {
    }

    public final void performAddAnimation(long j, long j2, boolean z) {
        float f;
        enableAppearDrawing(true);
        this.mIsHeadsUpAnimation = z;
        if (this.mDrawingAppearAnimation) {
            if (z) {
                f = 0.0f;
            } else {
                f = -1.0f;
            }
            startAppearAnimation(true, f, j, j2, (Runnable) null, (AnimatorListenerAdapter) null);
        }
    }

    public long performRemoveAnimation(long j, long j2, float f, boolean z, float f2, Runnable runnable, AnimatorListenerAdapter animatorListenerAdapter) {
        enableAppearDrawing(true);
        this.mIsHeadsUpAnimation = z;
        if (this.mDrawingAppearAnimation) {
            startAppearAnimation(false, f, j2, j, runnable, animatorListenerAdapter);
            return 0;
        } else if (runnable == null) {
            return 0;
        } else {
            runnable.run();
            return 0;
        }
    }

    public void resetAllContentAlphas() {
    }

    public void updateBackgroundTint() {
        updateBackgroundTint(false);
    }

    static {
        new PathInterpolator(0.6f, 0.0f, 0.5f, 1.0f);
        new PathInterpolator(0.0f, 0.0f, 0.5f, 1.0f);
    }

    private void updateColors() {
        this.mNormalColor = Utils.getColorAttrDefaultColor(this.mContext, 17956909);
        this.mTintedRippleColor = this.mContext.getColor(C1777R.color.notification_ripple_tinted_color);
        this.mNormalRippleColor = this.mContext.getColor(C1777R.color.notification_ripple_untinted_color);
    }

    public final int calculateBgColor(boolean z, boolean z2) {
        int i;
        if (z2 && this.mOverrideTint != 0) {
            return R$array.interpolateColors(calculateBgColor(z, false), this.mOverrideTint, this.mOverrideAmount);
        }
        if (!z || (i = this.mBgTint) == 0) {
            return this.mNormalColor;
        }
        return i;
    }

    public boolean childNeedsClipping(View view) {
        if (!(view instanceof NotificationBackgroundView) || !isClippingNeeded()) {
            return false;
        }
        return true;
    }

    public void dispatchDraw(Canvas canvas) {
        if (this.mDrawingAppearAnimation) {
            canvas.save();
            canvas.translate(0.0f, this.mAppearAnimationTranslation);
        }
        super.dispatchDraw(canvas);
        if (this.mDrawingAppearAnimation) {
            canvas.restore();
        }
    }

    public final void enableAppearDrawing(boolean z) {
        if (z != this.mDrawingAppearAnimation) {
            this.mDrawingAppearAnimation = z;
            if (!z) {
                setContentAlpha(1.0f);
                this.mAppearAnimationFraction = -1.0f;
                this.mCustomOutline = false;
                applyRoundness();
            }
            invalidate();
        }
    }

    public final float getCurrentBackgroundRadiusBottom() {
        float f;
        float f2 = this.mAppearAnimationFraction;
        if (f2 >= 0.0f) {
            f = this.mCurrentAppearInterpolator.getInterpolation(f2);
        } else {
            f = 1.0f;
        }
        return MathUtils.lerp(0.0f, this.mCurrentBottomRoundness * this.mOutlineRadius, f);
    }

    public final float getCurrentBackgroundRadiusTop() {
        float f;
        float f2 = this.mAppearAnimationFraction;
        if (f2 >= 0.0f) {
            f = this.mCurrentAppearInterpolator.getInterpolation(f2);
        } else {
            f = 1.0f;
        }
        return MathUtils.lerp(0.0f, this.mCurrentTopRoundness * this.mOutlineRadius, f);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        Gefingerpoken gefingerpoken = this.mTouchHandler;
        if (gefingerpoken == null || !gefingerpoken.onInterceptTouchEvent(motionEvent)) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        return true;
    }

    public void setBackgroundTintColor(int i) {
        if (i != this.mCurrentBackgroundTint) {
            this.mCurrentBackgroundTint = i;
            if (i == this.mNormalColor) {
                i = 0;
            }
            NotificationBackgroundView notificationBackgroundView = this.mBackgroundNormal;
            Objects.requireNonNull(notificationBackgroundView);
            if (i != 0) {
                notificationBackgroundView.mBackground.setColorFilter(i, PorterDuff.Mode.SRC_ATOP);
            } else {
                notificationBackgroundView.mBackground.clearColorFilter();
            }
            notificationBackgroundView.mTintColor = i;
            notificationBackgroundView.invalidate();
        }
    }

    public final void setBelowSpeedBump(boolean z) {
        if (z != this.mIsBelowSpeedBump) {
            this.mIsBelowSpeedBump = z;
            updateBackgroundTint();
        }
    }

    public void setFakeShadowIntensity(float f, float f2, int i, int i2) {
        boolean z;
        boolean z2 = this.mShadowHidden;
        if (f == 0.0f) {
            z = true;
        } else {
            z = false;
        }
        this.mShadowHidden = z;
        if (!z || !z2) {
            FakeShadowView fakeShadowView = this.mFakeShadow;
            float translationZ = (getTranslationZ() + 0.1f) * f;
            Objects.requireNonNull(fakeShadowView);
            if (translationZ == 0.0f) {
                fakeShadowView.mFakeShadow.setVisibility(4);
                return;
            }
            fakeShadowView.mFakeShadow.setVisibility(0);
            fakeShadowView.mFakeShadow.setTranslationZ(Math.max((float) fakeShadowView.mShadowMinHeight, translationZ));
            fakeShadowView.mFakeShadow.setTranslationX((float) i2);
            View view = fakeShadowView.mFakeShadow;
            view.setTranslationY((float) (i - view.getHeight()));
            if (f2 != fakeShadowView.mOutlineAlpha) {
                fakeShadowView.mOutlineAlpha = f2;
                fakeShadowView.mFakeShadow.invalidateOutline();
            }
        }
    }

    public final void setOverrideTintColor(int i, float f) {
        this.mOverrideTint = i;
        this.mOverrideAmount = f;
        setBackgroundTintColor(calculateBgColor(true, true));
    }

    public final void startAppearAnimation(final boolean z, float f, long j, long j2, final Runnable runnable, AnimatorListenerAdapter animatorListenerAdapter) {
        this.mAnimationTranslationY = f * ((float) this.mActualHeight);
        ValueAnimator valueAnimator = this.mAppearAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.mAppearAnimator = null;
        }
        float f2 = 0.0f;
        if (this.mAppearAnimationFraction == -1.0f) {
            if (z) {
                this.mAppearAnimationFraction = 0.0f;
                this.mAppearAnimationTranslation = this.mAnimationTranslationY;
            } else {
                this.mAppearAnimationFraction = 1.0f;
                this.mAppearAnimationTranslation = 0.0f;
            }
        }
        if (z) {
            this.mCurrentAppearInterpolator = Interpolators.FAST_OUT_SLOW_IN;
            f2 = 1.0f;
        } else {
            this.mCurrentAppearInterpolator = this.mSlowOutFastInInterpolator;
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{this.mAppearAnimationFraction, f2});
        this.mAppearAnimator = ofFloat;
        ofFloat.setInterpolator(Interpolators.LINEAR);
        this.mAppearAnimator.setDuration((long) (Math.abs(this.mAppearAnimationFraction - f2) * ((float) j2)));
        this.mAppearAnimator.addUpdateListener(new ActivatableNotificationView$$ExternalSyntheticLambda0(this, 0));
        if (animatorListenerAdapter != null) {
            this.mAppearAnimator.addListener(animatorListenerAdapter);
        }
        if (j > 0) {
            setContentAlpha(Interpolators.ALPHA_IN.getInterpolation((MathUtils.constrain(this.mAppearAnimationFraction, 0.4f, 1.0f) - 0.4f) / 0.6f));
            updateAppearRect();
            this.mAppearAnimator.setStartDelay(j);
        }
        this.mAppearAnimator.addListener(new AnimatorListenerAdapter() {
            public boolean mWasCancelled;

            public final void onAnimationCancel(Animator animator) {
                this.mWasCancelled = true;
            }

            public final void onAnimationStart(Animator animator) {
                this.mWasCancelled = false;
                InteractionJankMonitor.getInstance().begin(InteractionJankMonitor.Configuration.Builder.withView(ActivatableNotificationView.m234$$Nest$mgetCujType(ActivatableNotificationView.this, z), ActivatableNotificationView.this));
            }

            public final void onAnimationEnd(Animator animator) {
                Runnable runnable = runnable;
                if (runnable != null) {
                    runnable.run();
                }
                if (!this.mWasCancelled) {
                    ActivatableNotificationView activatableNotificationView = ActivatableNotificationView.this;
                    int i = ActivatableNotificationView.$r8$clinit;
                    activatableNotificationView.enableAppearDrawing(false);
                    ActivatableNotificationView.this.onAppearAnimationFinished(z);
                    InteractionJankMonitor.getInstance().end(ActivatableNotificationView.m234$$Nest$mgetCujType(ActivatableNotificationView.this, z));
                    return;
                }
                InteractionJankMonitor.getInstance().cancel(ActivatableNotificationView.m234$$Nest$mgetCujType(ActivatableNotificationView.this, z));
            }
        });
        this.mAppearAnimator.start();
    }

    public final void updateAppearRect() {
        float interpolation = this.mCurrentAppearInterpolator.getInterpolation(this.mAppearAnimationFraction);
        float f = (1.0f - interpolation) * this.mAnimationTranslationY;
        this.mAppearAnimationTranslation = f;
        int i = this.mActualHeight;
        float f2 = (float) i;
        float f3 = interpolation * f2;
        if (this.mTargetPoint != null) {
            int width = getWidth();
            float f4 = 1.0f - this.mAppearAnimationFraction;
            Point point = this.mTargetPoint;
            int i2 = point.x;
            float f5 = this.mAnimationTranslationY;
            int i3 = point.y;
            setOutlineRect(((float) i2) * f4, MotionController$$ExternalSyntheticOutline0.m7m(f5, (float) i3, f4, f5), ((float) width) - (((float) (width - i2)) * f4), f2 - (((float) (i - i3)) * f4));
            return;
        }
        setOutlineRect(0.0f, f, (float) getWidth(), f3 + this.mAppearAnimationTranslation);
    }

    public final void updateBackgroundTint(boolean z) {
        int i;
        ValueAnimator valueAnimator = this.mBackgroundColorAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        if (this.mBgTint != 0) {
            i = this.mTintedRippleColor;
        } else {
            i = this.mNormalRippleColor;
        }
        NotificationBackgroundView notificationBackgroundView = this.mBackgroundNormal;
        Objects.requireNonNull(notificationBackgroundView);
        Drawable drawable = notificationBackgroundView.mBackground;
        if (drawable instanceof RippleDrawable) {
            ((RippleDrawable) drawable).setColor(ColorStateList.valueOf(i));
        }
        int calculateBgColor = calculateBgColor(true, true);
        if (!z) {
            setBackgroundTintColor(calculateBgColor);
            return;
        }
        int i2 = this.mCurrentBackgroundTint;
        if (calculateBgColor != i2) {
            this.mStartTint = i2;
            this.mTargetTint = calculateBgColor;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            this.mBackgroundColorAnimator = ofFloat;
            ofFloat.addUpdateListener(new ScreenshotView$$ExternalSyntheticLambda0(this, 1));
            this.mBackgroundColorAnimator.setDuration(360);
            this.mBackgroundColorAnimator.setInterpolator(Interpolators.LINEAR);
            this.mBackgroundColorAnimator.addListener(new AnimatorListenerAdapter() {
                public final void onAnimationEnd(Animator animator) {
                    ActivatableNotificationView.this.mBackgroundColorAnimator = null;
                }
            });
            this.mBackgroundColorAnimator.start();
        }
    }

    /* renamed from: -$$Nest$mgetCujType  reason: not valid java name */
    public static int m234$$Nest$mgetCujType(ActivatableNotificationView activatableNotificationView, boolean z) {
        Objects.requireNonNull(activatableNotificationView);
        if (activatableNotificationView.mIsHeadsUpAnimation) {
            if (z) {
                return 12;
            }
            return 13;
        } else if (z) {
            return 14;
        } else {
            return 15;
        }
    }

    public ActivatableNotificationView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        new PathInterpolator(0.8f, 0.0f, 1.0f, 1.0f);
        setClipChildren(false);
        setClipToPadding(false);
        updateColors();
    }

    public void applyRoundness() {
        boolean z;
        super.applyRoundness();
        float currentBackgroundRadiusTop = getCurrentBackgroundRadiusTop();
        float currentBackgroundRadiusBottom = getCurrentBackgroundRadiusBottom();
        NotificationBackgroundView notificationBackgroundView = this.mBackgroundNormal;
        Objects.requireNonNull(notificationBackgroundView);
        float[] fArr = notificationBackgroundView.mCornerRadii;
        if (currentBackgroundRadiusTop != fArr[0] || currentBackgroundRadiusBottom != fArr[4]) {
            if (currentBackgroundRadiusBottom != 0.0f) {
                z = true;
            } else {
                z = false;
            }
            notificationBackgroundView.mBottomIsRounded = z;
            fArr[0] = currentBackgroundRadiusTop;
            fArr[1] = currentBackgroundRadiusTop;
            fArr[2] = currentBackgroundRadiusTop;
            fArr[3] = currentBackgroundRadiusTop;
            fArr[4] = currentBackgroundRadiusBottom;
            fArr[5] = currentBackgroundRadiusBottom;
            fArr[6] = currentBackgroundRadiusBottom;
            fArr[7] = currentBackgroundRadiusBottom;
            if (!notificationBackgroundView.mDontModifyCorners) {
                Drawable drawable = notificationBackgroundView.mBackground;
                if (drawable instanceof LayerDrawable) {
                    ((GradientDrawable) ((LayerDrawable) drawable).getDrawable(0)).setCornerRadii(notificationBackgroundView.mCornerRadii);
                }
            }
        }
    }

    public final void drawableStateChanged() {
        super.drawableStateChanged();
        NotificationBackgroundView notificationBackgroundView = this.mBackgroundNormal;
        int[] drawableState = getDrawableState();
        Objects.requireNonNull(notificationBackgroundView);
        Drawable drawable = notificationBackgroundView.mBackground;
        if (drawable != null && drawable.isStateful()) {
            if (!notificationBackgroundView.mIsPressedAllowed) {
                drawableState = ArrayUtils.removeInt(drawableState, 16842919);
            }
            notificationBackgroundView.mBackground.setState(drawableState);
        }
    }

    public int getHeadsUpHeightWithoutHeader() {
        return getHeight();
    }

    public void onFinishInflate() {
        boolean z;
        super.onFinishInflate();
        this.mBackgroundNormal = (NotificationBackgroundView) findViewById(C1777R.C1779id.backgroundNormal);
        FakeShadowView fakeShadowView = (FakeShadowView) findViewById(C1777R.C1779id.fake_shadow);
        this.mFakeShadow = fakeShadowView;
        if (fakeShadowView.getVisibility() != 0) {
            z = true;
        } else {
            z = false;
        }
        this.mShadowHidden = z;
        this.mBackgroundNormal.setCustomBackground$1();
        updateBackgroundTint();
        if (0.7f != this.mOutlineAlpha) {
            this.mOutlineAlpha = 0.7f;
            applyRoundness();
        }
    }

    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        setPivotX((float) (getWidth() / 2));
    }

    public void setActualHeight(int i, boolean z) {
        super.setActualHeight(i, z);
        setPivotY((float) (i / 2));
        NotificationBackgroundView notificationBackgroundView = this.mBackgroundNormal;
        Objects.requireNonNull(notificationBackgroundView);
        if (!notificationBackgroundView.mExpandAnimationRunning) {
            notificationBackgroundView.mActualHeight = i;
            notificationBackgroundView.invalidate();
        }
    }

    public void setClipBottomAmount(int i) {
        super.setClipBottomAmount(i);
        NotificationBackgroundView notificationBackgroundView = this.mBackgroundNormal;
        Objects.requireNonNull(notificationBackgroundView);
        notificationBackgroundView.mClipBottomAmount = i;
        notificationBackgroundView.invalidate();
    }

    public void setClipTopAmount(int i) {
        super.setClipTopAmount(i);
        NotificationBackgroundView notificationBackgroundView = this.mBackgroundNormal;
        Objects.requireNonNull(notificationBackgroundView);
        notificationBackgroundView.mClipTopAmount = i;
        notificationBackgroundView.invalidate();
    }

    public final void setContentAlpha(float f) {
        int i;
        View contentView = getContentView();
        if (contentView.hasOverlappingRendering()) {
            if (f == 0.0f || f == 1.0f) {
                i = 0;
            } else {
                i = 2;
            }
            contentView.setLayerType(i, (Paint) null);
        }
        contentView.setAlpha(f);
        if (f == 1.0f) {
            resetAllContentAlphas();
        }
    }

    public final void updateBackgroundColors() {
        updateColors();
        this.mBackgroundNormal.setCustomBackground$1();
        updateBackgroundTint();
    }
}
