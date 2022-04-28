package com.android.systemui.biometrics;

import android.animation.AnimatorSet;
import android.content.Context;
import android.util.AttributeSet;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.Utils;
import com.android.systemui.R$anim;

public class UdfpsKeyguardView extends UdfpsAnimationView {
    public int mAlpha;
    public LottieAnimationView mAodFp;
    public AnimatorSet mBackgroundInAnimator = new AnimatorSet();
    public ImageView mBgProtection;
    public float mBurnInOffsetX;
    public float mBurnInOffsetY;
    public float mBurnInProgress;
    public UdfpsFpDrawable mFingerprintDrawable;
    public boolean mFullyInflated;
    public float mInterpolatedDarkAmount;
    public final C07072 mLayoutInflaterFinishListener = new AsyncLayoutInflater.OnInflateFinishedListener() {
        public final void onInflateFinished(View view, ViewGroup viewGroup) {
            UdfpsKeyguardView.this.mFullyInflated = true;
            viewGroup.addView(view);
            UdfpsKeyguardView udfpsKeyguardView = UdfpsKeyguardView.this;
            udfpsKeyguardView.mAodFp = (LottieAnimationView) udfpsKeyguardView.findViewById(C1777R.C1779id.udfps_aod_fp);
            UdfpsKeyguardView udfpsKeyguardView2 = UdfpsKeyguardView.this;
            udfpsKeyguardView2.mLockScreenFp = (LottieAnimationView) udfpsKeyguardView2.findViewById(C1777R.C1779id.udfps_lockscreen_fp);
            UdfpsKeyguardView udfpsKeyguardView3 = UdfpsKeyguardView.this;
            udfpsKeyguardView3.mBgProtection = (ImageView) udfpsKeyguardView3.findViewById(C1777R.C1779id.udfps_keyguard_fp_bg);
            UdfpsKeyguardView.this.updateBurnInOffsets();
            UdfpsKeyguardView.this.updateColor();
            UdfpsKeyguardView.this.updateAlpha();
            UdfpsKeyguardView.this.mLockScreenFp.addValueCallback(new KeyPath("**"), LottieProperty.COLOR_FILTER, new UdfpsKeyguardView$2$$ExternalSyntheticLambda0(this));
        }
    };
    public LottieAnimationView mLockScreenFp;
    public final int mMaxBurnInOffsetX;
    public final int mMaxBurnInOffsetY;
    public int mTextColorPrimary;
    public boolean mUdfpsRequested;

    public final void onIlluminationStarting() {
    }

    public final void onIlluminationStopped() {
    }

    public final int calculateAlpha() {
        if (this.mPauseAuth) {
            return 0;
        }
        return this.mAlpha;
    }

    public final void updateBurnInOffsets() {
        if (this.mFullyInflated) {
            this.mBurnInOffsetX = MathUtils.lerp(0.0f, (float) (R$anim.getBurnInOffset(this.mMaxBurnInOffsetX * 2, true) - this.mMaxBurnInOffsetX), this.mInterpolatedDarkAmount);
            this.mBurnInOffsetY = MathUtils.lerp(0.0f, (float) (R$anim.getBurnInOffset(this.mMaxBurnInOffsetY * 2, false) - this.mMaxBurnInOffsetY), this.mInterpolatedDarkAmount);
            this.mBurnInProgress = MathUtils.lerp(0.0f, R$anim.zigzag(((float) System.currentTimeMillis()) / 60000.0f, 1.0f, 89.0f), this.mInterpolatedDarkAmount);
            this.mAodFp.setTranslationX(this.mBurnInOffsetX);
            this.mAodFp.setTranslationY(this.mBurnInOffsetY);
            this.mAodFp.setProgress(this.mBurnInProgress);
            this.mAodFp.setAlpha(this.mInterpolatedDarkAmount * 255.0f);
            this.mLockScreenFp.setTranslationX(this.mBurnInOffsetX);
            this.mLockScreenFp.setTranslationY(this.mBurnInOffsetY);
            this.mLockScreenFp.setProgress(1.0f - this.mInterpolatedDarkAmount);
            this.mLockScreenFp.setAlpha((1.0f - this.mInterpolatedDarkAmount) * 255.0f);
        }
    }

    public final void updateColor() {
        if (this.mFullyInflated) {
            this.mTextColorPrimary = Utils.getColorAttrDefaultColor(this.mContext, 16842806);
            this.mBgProtection.setImageDrawable(getContext().getDrawable(C1777R.C1778drawable.fingerprint_bg));
            this.mLockScreenFp.invalidate();
        }
    }

    public UdfpsKeyguardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFingerprintDrawable = new UdfpsFpDrawable(context);
        this.mMaxBurnInOffsetX = context.getResources().getDimensionPixelSize(C1777R.dimen.udfps_burn_in_offset_x);
        this.mMaxBurnInOffsetY = context.getResources().getDimensionPixelSize(C1777R.dimen.udfps_burn_in_offset_y);
    }

    public final boolean dozeTimeTick() {
        updateBurnInOffsets();
        return true;
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        new AsyncLayoutInflater(this.mContext).inflate(C1777R.layout.udfps_keyguard_view_internal, this, this.mLayoutInflaterFinishListener);
    }

    public final int updateAlpha() {
        int updateAlpha = super.updateAlpha();
        if (this.mFullyInflated) {
            float f = ((float) updateAlpha) / 255.0f;
            this.mLockScreenFp.setAlpha(f);
            float f2 = this.mInterpolatedDarkAmount;
            if (f2 != 0.0f) {
                this.mBgProtection.setAlpha(1.0f - f2);
            } else {
                this.mBgProtection.setAlpha(f);
            }
        }
        return updateAlpha;
    }

    public final UdfpsDrawable getDrawable() {
        return this.mFingerprintDrawable;
    }
}
