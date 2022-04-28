package com.android.systemui.navigationbar.buttons;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Canvas;
import android.graphics.CanvasProperty;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Trace;
import android.view.RenderNodeAnimator;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import androidx.annotation.Keep;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda15;
import com.android.systemui.ImageWallpaper$GLEngine$$ExternalSyntheticLambda0;
import java.util.ArrayList;
import java.util.HashSet;

public class KeyButtonRipple extends Drawable {
    public static final PathInterpolator ALPHA_OUT_INTERPOLATOR = new PathInterpolator(0.0f, 0.0f, 0.8f, 1.0f);
    public final C09391 mAnimatorListener = new AnimatorListenerAdapter() {
        public final void onAnimationEnd(Animator animator) {
            KeyButtonRipple.this.mRunningAnimations.remove(animator);
            if (KeyButtonRipple.this.mRunningAnimations.isEmpty()) {
                KeyButtonRipple keyButtonRipple = KeyButtonRipple.this;
                if (!keyButtonRipple.mPressed) {
                    keyButtonRipple.mVisible = false;
                    keyButtonRipple.mDrawingHardwareGlow = false;
                    keyButtonRipple.invalidateSelf();
                }
            }
        }
    };
    public CanvasProperty<Float> mBottomProp;
    public boolean mDark;
    public boolean mDelayTouchFeedback;
    public boolean mDrawingHardwareGlow;
    public final TraceAnimatorListener mEnterHwTraceAnimator = new TraceAnimatorListener("enterHardware");
    public final TraceAnimatorListener mExitHwTraceAnimator = new TraceAnimatorListener("exitHardware");
    public float mGlowAlpha = 0.0f;
    public float mGlowScale = 1.0f;
    public final Handler mHandler = new Handler();
    public final LogInterpolator mInterpolator = new LogInterpolator();
    public boolean mLastDark;
    public CanvasProperty<Float> mLeftProp;
    public int mMaxWidth;
    public final int mMaxWidthResource = C1777R.dimen.key_button_ripple_max_width;
    public CanvasProperty<Paint> mPaintProp;
    public boolean mPressed;
    public CanvasProperty<Float> mRightProp;
    public Paint mRipplePaint;
    public final HashSet<Animator> mRunningAnimations = new HashSet<>();
    public CanvasProperty<Float> mRxProp;
    public CanvasProperty<Float> mRyProp;
    public boolean mSupportHardware;
    public final View mTargetView;
    public final ArrayList<Animator> mTmpArray = new ArrayList<>();
    public CanvasProperty<Float> mTopProp;
    public Type mType = Type.ROUNDED_RECT;
    public boolean mVisible;

    public static final class LogInterpolator implements Interpolator {
        public final float getInterpolation(float f) {
            return 1.0f - ((float) Math.pow(400.0d, ((double) (-f)) * 1.4d));
        }
    }

    public static final class TraceAnimatorListener extends AnimatorListenerAdapter {
        public final String mName;

        public final void onAnimationCancel(Animator animator) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("KeyButtonRipple.cancel.");
            m.append(this.mName);
            Trace.beginSection(m.toString());
            Trace.endSection();
        }

        public final void onAnimationEnd(Animator animator) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("KeyButtonRipple.end.");
            m.append(this.mName);
            Trace.beginSection(m.toString());
            Trace.endSection();
        }

        public final void onAnimationStart(Animator animator) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("KeyButtonRipple.start.");
            m.append(this.mName);
            Trace.beginSection(m.toString());
            Trace.endSection();
        }

        public TraceAnimatorListener(String str) {
            this.mName = str;
        }
    }

    public enum Type {
        OVAL,
        ROUNDED_RECT
    }

    public final void exitSoftware() {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "glowAlpha", new float[]{this.mGlowAlpha, 0.0f});
        ofFloat.setInterpolator(ALPHA_OUT_INTERPOLATOR);
        ofFloat.setDuration(450);
        ofFloat.addListener(this.mAnimatorListener);
        ofFloat.start();
        this.mRunningAnimations.add(ofFloat);
    }

    public final int getOpacity() {
        return -3;
    }

    public final boolean hasFocusStateSpecified() {
        return true;
    }

    public final boolean isStateful() {
        return true;
    }

    public final boolean onStateChange(int[] iArr) {
        boolean z;
        int i = 0;
        while (true) {
            if (i >= iArr.length) {
                z = false;
                break;
            } else if (iArr[i] == 16842919) {
                z = true;
                break;
            } else {
                i++;
            }
        }
        if (z == this.mPressed) {
            return false;
        }
        boolean z2 = this.mDark;
        if (z2 != this.mLastDark && z) {
            this.mRipplePaint = null;
            this.mLastDark = z2;
        }
        if (this.mSupportHardware) {
            if (!z) {
                exitHardware();
            } else if (!this.mDelayTouchFeedback) {
                enterHardware();
            } else if (this.mRunningAnimations.isEmpty()) {
                this.mHandler.removeCallbacksAndMessages((Object) null);
                this.mHandler.postDelayed(new ImageWallpaper$GLEngine$$ExternalSyntheticLambda0(this, 2), (long) ViewConfiguration.getTapTimeout());
            } else if (this.mVisible) {
                enterHardware();
            }
        } else if (!z) {
            exitSoftware();
        } else if (!this.mDelayTouchFeedback) {
            enterSoftware();
        } else if (this.mRunningAnimations.isEmpty()) {
            this.mHandler.removeCallbacksAndMessages((Object) null);
            this.mHandler.postDelayed(new BubbleStackView$$ExternalSyntheticLambda15(this, 3), (long) ViewConfiguration.getTapTimeout());
        } else if (this.mVisible) {
            enterSoftware();
        }
        this.mPressed = z;
        return true;
    }

    public final void setAlpha(int i) {
    }

    public final void setColorFilter(ColorFilter colorFilter) {
    }

    public final void draw(Canvas canvas) {
        boolean z;
        float f;
        float f2;
        Type type = Type.ROUNDED_RECT;
        boolean isHardwareAccelerated = canvas.isHardwareAccelerated();
        this.mSupportHardware = isHardwareAccelerated;
        if (isHardwareAccelerated) {
            RecordingCanvas recordingCanvas = (RecordingCanvas) canvas;
            if (!this.mDrawingHardwareGlow) {
                return;
            }
            if (this.mType == type) {
                recordingCanvas.drawRoundRect(this.mLeftProp, this.mTopProp, this.mRightProp, this.mBottomProp, this.mRxProp, this.mRyProp, this.mPaintProp);
            } else {
                recordingCanvas.drawCircle(CanvasProperty.createFloat((float) (getBounds().width() / 2)), CanvasProperty.createFloat((float) (getBounds().height() / 2)), CanvasProperty.createFloat((((float) Math.min(getBounds().width(), getBounds().height())) * 1.0f) / 2.0f), this.mPaintProp);
            }
        } else if (this.mGlowAlpha > 0.0f) {
            Paint ripplePaint = getRipplePaint();
            ripplePaint.setAlpha((int) (this.mGlowAlpha * 255.0f));
            float width = (float) getBounds().width();
            float height = (float) getBounds().height();
            if (width > height) {
                z = true;
            } else {
                z = false;
            }
            float rippleSize = ((float) getRippleSize()) * this.mGlowScale * 0.5f;
            float f3 = width * 0.5f;
            float f4 = height * 0.5f;
            if (z) {
                f = rippleSize;
            } else {
                f = f3;
            }
            if (z) {
                rippleSize = f4;
            }
            if (z) {
                f2 = f4;
            } else {
                f2 = f3;
            }
            if (this.mType == type) {
                canvas.drawRoundRect(f3 - f, f4 - rippleSize, f + f3, f4 + rippleSize, f2, f2, ripplePaint);
                return;
            }
            canvas.save();
            canvas.translate(f3, f4);
            float min = Math.min(f, rippleSize);
            float f5 = -min;
            canvas.drawOval(f5, f5, min, min, ripplePaint);
            canvas.restore();
        }
    }

    public final void endAnimations(String str, boolean z) {
        Trace.beginSection("KeyButtonRipple.endAnim: reason=" + str + " cancel=" + z);
        Trace.endSection();
        this.mVisible = false;
        this.mTmpArray.addAll(this.mRunningAnimations);
        int size = this.mTmpArray.size();
        for (int i = 0; i < size; i++) {
            Animator animator = this.mTmpArray.get(i);
            if (z) {
                animator.cancel();
            } else {
                animator.end();
            }
        }
        this.mTmpArray.clear();
        this.mRunningAnimations.clear();
        this.mHandler.removeCallbacksAndMessages((Object) null);
    }

    public final void enterHardware() {
        CanvasProperty<Float> canvasProperty;
        CanvasProperty<Float> canvasProperty2;
        float f;
        endAnimations("enterHardware", true);
        this.mVisible = true;
        this.mDrawingHardwareGlow = true;
        CanvasProperty<Float> createFloat = CanvasProperty.createFloat((float) (getExtendSize() / 2));
        if (isHorizontal()) {
            this.mLeftProp = createFloat;
        } else {
            this.mTopProp = createFloat;
        }
        if (isHorizontal()) {
            canvasProperty = this.mLeftProp;
        } else {
            canvasProperty = this.mTopProp;
        }
        RenderNodeAnimator renderNodeAnimator = new RenderNodeAnimator(canvasProperty, ((float) (getExtendSize() / 2)) - ((((float) getRippleSize()) * 1.35f) / 2.0f));
        renderNodeAnimator.setDuration(350);
        renderNodeAnimator.setInterpolator(this.mInterpolator);
        renderNodeAnimator.addListener(this.mAnimatorListener);
        renderNodeAnimator.setTarget(this.mTargetView);
        CanvasProperty<Float> createFloat2 = CanvasProperty.createFloat((float) (getExtendSize() / 2));
        if (isHorizontal()) {
            this.mRightProp = createFloat2;
        } else {
            this.mBottomProp = createFloat2;
        }
        if (isHorizontal()) {
            canvasProperty2 = this.mRightProp;
        } else {
            canvasProperty2 = this.mBottomProp;
        }
        RenderNodeAnimator renderNodeAnimator2 = new RenderNodeAnimator(canvasProperty2, ((((float) getRippleSize()) * 1.35f) / 2.0f) + ((float) (getExtendSize() / 2)));
        renderNodeAnimator2.setDuration(350);
        renderNodeAnimator2.setInterpolator(this.mInterpolator);
        renderNodeAnimator2.addListener(this.mAnimatorListener);
        renderNodeAnimator2.addListener(this.mEnterHwTraceAnimator);
        renderNodeAnimator2.setTarget(this.mTargetView);
        if (isHorizontal()) {
            this.mTopProp = CanvasProperty.createFloat(0.0f);
            this.mBottomProp = CanvasProperty.createFloat((float) getBounds().height());
            this.mRxProp = CanvasProperty.createFloat((float) (getBounds().height() / 2));
            this.mRyProp = CanvasProperty.createFloat((float) (getBounds().height() / 2));
        } else {
            this.mLeftProp = CanvasProperty.createFloat(0.0f);
            this.mRightProp = CanvasProperty.createFloat((float) getBounds().width());
            this.mRxProp = CanvasProperty.createFloat((float) (getBounds().width() / 2));
            this.mRyProp = CanvasProperty.createFloat((float) (getBounds().width() / 2));
        }
        this.mGlowScale = 1.35f;
        if (this.mLastDark) {
            f = 0.1f;
        } else {
            f = 0.2f;
        }
        this.mGlowAlpha = f;
        Paint ripplePaint = getRipplePaint();
        this.mRipplePaint = ripplePaint;
        ripplePaint.setAlpha((int) (this.mGlowAlpha * 255.0f));
        this.mPaintProp = CanvasProperty.createPaint(this.mRipplePaint);
        renderNodeAnimator.start();
        renderNodeAnimator2.start();
        this.mRunningAnimations.add(renderNodeAnimator);
        this.mRunningAnimations.add(renderNodeAnimator2);
        invalidateSelf();
        if (this.mDelayTouchFeedback && !this.mPressed) {
            exitHardware();
        }
    }

    public final void enterSoftware() {
        float f;
        endAnimations("enterSoftware", true);
        this.mVisible = true;
        if (this.mLastDark) {
            f = 0.1f;
        } else {
            f = 0.2f;
        }
        this.mGlowAlpha = f;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "glowScale", new float[]{0.0f, 1.35f});
        ofFloat.setInterpolator(this.mInterpolator);
        ofFloat.setDuration(350);
        ofFloat.addListener(this.mAnimatorListener);
        ofFloat.start();
        this.mRunningAnimations.add(ofFloat);
        if (this.mDelayTouchFeedback && !this.mPressed) {
            exitSoftware();
        }
    }

    public final Paint getRipplePaint() {
        int i;
        if (this.mRipplePaint == null) {
            Paint paint = new Paint();
            this.mRipplePaint = paint;
            paint.setAntiAlias(true);
            Paint paint2 = this.mRipplePaint;
            if (this.mLastDark) {
                i = -16777216;
            } else {
                i = -1;
            }
            paint2.setColor(i);
        }
        return this.mRipplePaint;
    }

    public final void jumpToCurrentState() {
        endAnimations("jumpToCurrentState", false);
    }

    @Keep
    public void setGlowAlpha(float f) {
        this.mGlowAlpha = f;
        invalidateSelf();
    }

    @Keep
    public void setGlowScale(float f) {
        this.mGlowScale = f;
        invalidateSelf();
    }

    public KeyButtonRipple(Context context, View view) {
        this.mMaxWidth = context.getResources().getDimensionPixelSize(C1777R.dimen.key_button_ripple_max_width);
        this.mTargetView = view;
    }

    public final void exitHardware() {
        this.mPaintProp = CanvasProperty.createPaint(getRipplePaint());
        RenderNodeAnimator renderNodeAnimator = new RenderNodeAnimator(this.mPaintProp, 1, 0.0f);
        renderNodeAnimator.setDuration(450);
        renderNodeAnimator.setInterpolator(ALPHA_OUT_INTERPOLATOR);
        renderNodeAnimator.addListener(this.mAnimatorListener);
        renderNodeAnimator.addListener(this.mExitHwTraceAnimator);
        renderNodeAnimator.setTarget(this.mTargetView);
        renderNodeAnimator.start();
        this.mRunningAnimations.add(renderNodeAnimator);
        invalidateSelf();
    }

    public final int getExtendSize() {
        boolean isHorizontal = isHorizontal();
        Rect bounds = getBounds();
        if (isHorizontal) {
            return bounds.width();
        }
        return bounds.height();
    }

    public final int getRippleSize() {
        int i;
        if (isHorizontal()) {
            i = getBounds().width();
        } else {
            i = getBounds().height();
        }
        return Math.min(i, this.mMaxWidth);
    }

    public final boolean isHorizontal() {
        if (getBounds().width() > getBounds().height()) {
            return true;
        }
        return false;
    }

    public final boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        if (visible) {
            jumpToCurrentState();
        }
        return visible;
    }

    @Keep
    public float getGlowAlpha() {
        return this.mGlowAlpha;
    }

    @Keep
    public float getGlowScale() {
        return this.mGlowScale;
    }
}
