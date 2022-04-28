package com.android.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.plugins.ClockPlugin;

public class KeyguardClockSwitch extends RelativeLayout {
    public boolean mChildrenAreLaidOut = false;
    public FrameLayout mClockFrame;
    public AnimatorSet mClockInAnim = null;
    public AnimatorSet mClockOutAnim = null;
    public ClockPlugin mClockPlugin;
    public int mClockSwitchYAmount;
    public AnimatableClockView mClockView;
    public int[] mColorPalette;
    public float mDarkAmount;
    public Integer mDisplayedClockSize = null;
    public FrameLayout mLargeClockFrame;
    public AnimatableClockView mLargeClockView;
    public int mSmartspaceTopOffset;
    public View mStatusArea;
    public ObjectAnimator mStatusAreaAnim = null;
    public boolean mSupportsDarkText;

    public final void onDensityOrFontScaleChanged() {
        this.mLargeClockView.setTextSize(0, (float) this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.large_clock_text_size));
        this.mClockView.setTextSize(0, (float) this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.clock_text_size));
        this.mClockSwitchYAmount = this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.keyguard_clock_switch_y_shift);
        this.mSmartspaceTopOffset = this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.keyguard_smartspace_top_offset);
    }

    public final void setClockPlugin(ClockPlugin clockPlugin) {
        FrameLayout frameLayout;
        FrameLayout frameLayout2;
        ClockPlugin clockPlugin2 = this.mClockPlugin;
        if (clockPlugin2 != null) {
            View view = clockPlugin2.getView();
            if (view != null && view.getParent() == (frameLayout2 = this.mClockFrame)) {
                frameLayout2.removeView(view);
            }
            View bigClockView = this.mClockPlugin.getBigClockView();
            if (bigClockView != null && bigClockView.getParent() == (frameLayout = this.mLargeClockFrame)) {
                frameLayout.removeView(bigClockView);
            }
            this.mClockPlugin.onDestroyView();
            this.mClockPlugin = null;
        }
        if (clockPlugin == null) {
            this.mClockView.setVisibility(0);
            this.mLargeClockView.setVisibility(0);
            return;
        }
        View view2 = clockPlugin.getView();
        if (view2 != null) {
            this.mClockFrame.addView(view2, -1, new ViewGroup.LayoutParams(-1, -2));
            this.mClockView.setVisibility(8);
        }
        View bigClockView2 = clockPlugin.getBigClockView();
        if (bigClockView2 != null) {
            this.mLargeClockFrame.addView(bigClockView2);
            this.mLargeClockView.setVisibility(8);
        }
        this.mClockPlugin = clockPlugin;
        clockPlugin.setStyle(this.mClockView.getPaint().getStyle());
        this.mClockPlugin.setTextColor(this.mClockView.getCurrentTextColor());
        this.mClockPlugin.setDarkAmount(this.mDarkAmount);
        int[] iArr = this.mColorPalette;
        if (iArr != null) {
            this.mClockPlugin.setColorPalette(this.mSupportsDarkText, iArr);
        }
    }

    public final void updateClockViews(boolean z, boolean z2) {
        FrameLayout frameLayout;
        float f;
        FrameLayout frameLayout2;
        AnimatorSet animatorSet = this.mClockInAnim;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        AnimatorSet animatorSet2 = this.mClockOutAnim;
        if (animatorSet2 != null) {
            animatorSet2.cancel();
        }
        ObjectAnimator objectAnimator = this.mStatusAreaAnim;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        this.mClockInAnim = null;
        this.mClockOutAnim = null;
        this.mStatusAreaAnim = null;
        int i = -1;
        if (z) {
            frameLayout = this.mClockFrame;
            frameLayout2 = this.mLargeClockFrame;
            if (indexOfChild(frameLayout2) == -1) {
                addView(frameLayout2);
            }
            f = (float) ((this.mClockFrame.getTop() - this.mStatusArea.getTop()) + this.mSmartspaceTopOffset);
        } else {
            frameLayout2 = this.mClockFrame;
            frameLayout = this.mLargeClockFrame;
            removeView(frameLayout);
            f = 0.0f;
            i = 1;
        }
        if (!z2) {
            frameLayout.setAlpha(0.0f);
            frameLayout2.setAlpha(1.0f);
            frameLayout2.setVisibility(0);
            this.mStatusArea.setTranslationY(f);
            return;
        }
        AnimatorSet animatorSet3 = new AnimatorSet();
        this.mClockOutAnim = animatorSet3;
        animatorSet3.setDuration(150);
        this.mClockOutAnim.setInterpolator(Interpolators.FAST_OUT_LINEAR_IN);
        this.mClockOutAnim.playTogether(new Animator[]{ObjectAnimator.ofFloat(frameLayout, View.ALPHA, new float[]{0.0f}), ObjectAnimator.ofFloat(frameLayout, View.TRANSLATION_Y, new float[]{0.0f, (float) ((-this.mClockSwitchYAmount) * i)})});
        this.mClockOutAnim.addListener(new AnimatorListenerAdapter() {
            public final void onAnimationEnd(Animator animator) {
                KeyguardClockSwitch.this.mClockOutAnim = null;
            }
        });
        frameLayout2.setAlpha(0.0f);
        frameLayout2.setVisibility(0);
        AnimatorSet animatorSet4 = new AnimatorSet();
        this.mClockInAnim = animatorSet4;
        animatorSet4.setDuration(200);
        this.mClockInAnim.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
        this.mClockInAnim.playTogether(new Animator[]{ObjectAnimator.ofFloat(frameLayout2, View.ALPHA, new float[]{1.0f}), ObjectAnimator.ofFloat(frameLayout2, View.TRANSLATION_Y, new float[]{(float) (i * this.mClockSwitchYAmount), 0.0f})});
        this.mClockInAnim.setStartDelay(75);
        this.mClockInAnim.addListener(new AnimatorListenerAdapter() {
            public final void onAnimationEnd(Animator animator) {
                KeyguardClockSwitch.this.mClockInAnim = null;
            }
        });
        this.mClockInAnim.start();
        this.mClockOutAnim.start();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mStatusArea, View.TRANSLATION_Y, new float[]{f});
        this.mStatusAreaAnim = ofFloat;
        ofFloat.setDuration(350);
        this.mStatusAreaAnim.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        this.mStatusAreaAnim.addListener(new AnimatorListenerAdapter() {
            public final void onAnimationEnd(Animator animator) {
                KeyguardClockSwitch.this.mStatusAreaAnim = null;
            }
        });
        this.mStatusAreaAnim.start();
    }

    public KeyguardClockSwitch(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mClockFrame = (FrameLayout) findViewById(C1777R.C1779id.lockscreen_clock_view);
        this.mClockView = (AnimatableClockView) findViewById(C1777R.C1779id.animatable_clock_view);
        this.mLargeClockFrame = (FrameLayout) findViewById(C1777R.C1779id.lockscreen_clock_view_large);
        this.mLargeClockView = (AnimatableClockView) findViewById(C1777R.C1779id.animatable_clock_view_large);
        this.mStatusArea = findViewById(C1777R.C1779id.keyguard_status_area);
        onDensityOrFontScaleChanged();
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean z2;
        super.onLayout(z, i, i2, i3, i4);
        Integer num = this.mDisplayedClockSize;
        if (num != null && !this.mChildrenAreLaidOut) {
            if (num.intValue() == 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            updateClockViews(z2, true);
        }
        this.mChildrenAreLaidOut = true;
    }
}
