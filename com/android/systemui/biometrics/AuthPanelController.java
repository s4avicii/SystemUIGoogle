package com.android.systemui.biometrics;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Outline;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.android.p012wm.shell.C1777R;

public final class AuthPanelController extends ViewOutlineProvider {
    public int mContainerHeight;
    public int mContainerWidth;
    public int mContentHeight;
    public int mContentWidth;
    public final Context mContext;
    public float mCornerRadius;
    public int mMargin;
    public final View mPanelView;
    public int mPosition = 1;
    public boolean mUseFullScreen;

    public final int getLeftBound(int i) {
        if (i == 1) {
            return (this.mContainerWidth - this.mContentWidth) / 2;
        }
        if (i == 2) {
            return this.mMargin;
        }
        if (i == 3) {
            return (this.mContainerWidth - this.mContentWidth) - this.mMargin;
        }
        Log.e("BiometricPrompt/AuthPanelController", "Unrecognized position: " + i);
        return getLeftBound(1);
    }

    public final int getTopBound(int i) {
        if (i == 1) {
            int i2 = this.mMargin;
            return Math.max((this.mContainerHeight - this.mContentHeight) - i2, i2);
        } else if (i == 2 || i == 3) {
            return Math.max((this.mContainerHeight - this.mContentHeight) / 2, this.mMargin);
        } else {
            Log.e("BiometricPrompt/AuthPanelController", "Unrecognized position: " + i);
            return getTopBound(1);
        }
    }

    public final void getOutline(View view, Outline outline) {
        int leftBound = getLeftBound(this.mPosition);
        int i = leftBound + this.mContentWidth;
        int topBound = getTopBound(this.mPosition);
        outline.setRoundRect(leftBound, topBound, i, Math.min(this.mContentHeight + topBound, this.mContainerHeight - this.mMargin), this.mCornerRadius);
    }

    public final void updateForContentDimensions(int i, int i2, int i3) {
        int i4;
        float f;
        if (this.mContainerWidth == 0 || this.mContainerHeight == 0) {
            Log.w("BiometricPrompt/AuthPanelController", "Not done measuring yet");
            return;
        }
        if (this.mUseFullScreen) {
            i4 = 0;
        } else {
            i4 = (int) this.mContext.getResources().getDimension(C1777R.dimen.biometric_dialog_border_padding);
        }
        if (this.mUseFullScreen) {
            f = 0.0f;
        } else {
            f = this.mContext.getResources().getDimension(C1777R.dimen.biometric_dialog_corner_size);
        }
        if (i3 > 0) {
            ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{this.mMargin, i4});
            ofInt.addUpdateListener(new AuthPanelController$$ExternalSyntheticLambda1(this));
            ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{this.mCornerRadius, f});
            ofFloat.addUpdateListener(new AuthPanelController$$ExternalSyntheticLambda2(this));
            ValueAnimator ofInt2 = ValueAnimator.ofInt(new int[]{this.mContentHeight, i2});
            ofInt2.addUpdateListener(new AuthPanelController$$ExternalSyntheticLambda0(this, 0));
            ValueAnimator ofInt3 = ValueAnimator.ofInt(new int[]{this.mContentWidth, i});
            ofInt3.addUpdateListener(new AuthPanelController$$ExternalSyntheticLambda3(this));
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration((long) i3);
            animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
            animatorSet.playTogether(new Animator[]{ofFloat, ofInt2, ofInt3, ofInt});
            animatorSet.start();
            return;
        }
        this.mMargin = i4;
        this.mCornerRadius = f;
        this.mContentWidth = i;
        this.mContentHeight = i2;
        this.mPanelView.invalidateOutline();
    }

    public AuthPanelController(Context context, View view) {
        this.mContext = context;
        this.mPanelView = view;
        this.mCornerRadius = context.getResources().getDimension(C1777R.dimen.biometric_dialog_corner_size);
        this.mMargin = (int) context.getResources().getDimension(C1777R.dimen.biometric_dialog_border_padding);
        view.setOutlineProvider(this);
        view.setClipToOutline(true);
    }
}
