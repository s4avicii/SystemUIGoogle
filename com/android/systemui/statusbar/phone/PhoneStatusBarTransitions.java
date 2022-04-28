package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import com.android.p012wm.shell.C1777R;

public final class PhoneStatusBarTransitions extends BarTransitions {
    public View mBattery;
    public AnimatorSet mCurrentAnimation;
    public final float mIconAlphaWhenOpaque;
    public View mLeftSide;
    public View mStatusIcons;

    public static ObjectAnimator animateTransitionTo(View view, float f) {
        return ObjectAnimator.ofFloat(view, "alpha", new float[]{view.getAlpha(), f});
    }

    public final float getNonBatteryClockAlphaFor(int i) {
        boolean z;
        boolean z2 = false;
        if (i == 3 || i == 6) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return 0.0f;
        }
        if (!(i == 1 || i == 2 || i == 0 || i == 6)) {
            z2 = true;
        }
        if (!z2) {
            return 1.0f;
        }
        return this.mIconAlphaWhenOpaque;
    }

    public final void applyMode(int i, boolean z) {
        boolean z2;
        float f;
        if (this.mLeftSide != null) {
            float nonBatteryClockAlphaFor = getNonBatteryClockAlphaFor(i);
            boolean z3 = false;
            if (i == 3 || i == 6) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2) {
                f = 0.5f;
            } else {
                f = getNonBatteryClockAlphaFor(i);
            }
            AnimatorSet animatorSet = this.mCurrentAnimation;
            if (animatorSet != null) {
                animatorSet.cancel();
            }
            if (z) {
                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.playTogether(new Animator[]{animateTransitionTo(this.mLeftSide, nonBatteryClockAlphaFor), animateTransitionTo(this.mStatusIcons, nonBatteryClockAlphaFor), animateTransitionTo(this.mBattery, f)});
                if (i == 3 || i == 6) {
                    z3 = true;
                }
                if (z3) {
                    animatorSet2.setDuration(1500);
                }
                animatorSet2.start();
                this.mCurrentAnimation = animatorSet2;
                return;
            }
            this.mLeftSide.setAlpha(nonBatteryClockAlphaFor);
            this.mStatusIcons.setAlpha(nonBatteryClockAlphaFor);
            this.mBattery.setAlpha(f);
        }
    }

    public PhoneStatusBarTransitions(PhoneStatusBarView phoneStatusBarView, View view) {
        super(view, C1777R.C1778drawable.status_background);
        this.mIconAlphaWhenOpaque = phoneStatusBarView.getContext().getResources().getFraction(C1777R.dimen.status_bar_icon_drawing_alpha, 1, 1);
        this.mLeftSide = phoneStatusBarView.findViewById(C1777R.C1779id.status_bar_left_side);
        this.mStatusIcons = phoneStatusBarView.findViewById(C1777R.C1779id.statusIcons);
        this.mBattery = phoneStatusBarView.findViewById(C1777R.C1779id.battery);
        applyModeBackground(this.mMode, false);
        applyMode(this.mMode, false);
    }

    public final void onTransition(int i, int i2, boolean z) {
        applyModeBackground(i2, z);
        applyMode(i2, z);
    }
}
