package com.android.p012wm.shell.bubbles;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.util.ContrastColorUtil;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;

/* renamed from: com.android.wm.shell.bubbles.StackEducationView */
/* compiled from: StackEducationView.kt */
public final class StackEducationView extends LinearLayout {
    public final long ANIMATE_DURATION = 200;
    public final long ANIMATE_DURATION_SHORT = 40;
    public final BubbleController controller;
    public final Lazy descTextView$delegate;
    public boolean isHiding;
    public final BubblePositioner positioner;
    public final Lazy titleTextView$delegate;
    public final Lazy view$delegate;

    public final boolean show(PointF pointF) {
        int i;
        this.isHiding = false;
        if (getVisibility() == 0) {
            return false;
        }
        this.controller.updateWindowFlagsForBackpress(true);
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        BubblePositioner bubblePositioner = this.positioner;
        Objects.requireNonNull(bubblePositioner);
        if (bubblePositioner.mIsLargeScreen) {
            i = getContext().getResources().getDimensionPixelSize(C1777R.dimen.bubbles_user_education_width_large_screen);
        } else {
            i = -1;
        }
        layoutParams.width = i;
        setAlpha(0.0f);
        setVisibility(0);
        post(new StackEducationView$show$1(this, pointF));
        getContext().getSharedPreferences(getContext().getPackageName(), 0).edit().putBoolean("HasSeenBubblesOnboarding", true).apply();
        return true;
    }

    public StackEducationView(Context context, BubblePositioner bubblePositioner, BubbleController bubbleController) {
        super(context);
        this.positioner = bubblePositioner;
        this.controller = bubbleController;
        this.view$delegate = LazyKt__LazyJVMKt.lazy(new StackEducationView$view$2(this));
        this.titleTextView$delegate = LazyKt__LazyJVMKt.lazy(new StackEducationView$titleTextView$2(this));
        this.descTextView$delegate = LazyKt__LazyJVMKt.lazy(new StackEducationView$descTextView$2(this));
        LayoutInflater.from(context).inflate(C1777R.layout.bubble_stack_user_education, this);
        setVisibility(8);
        setElevation((float) getResources().getDimensionPixelSize(C1777R.dimen.bubble_elevation));
        setLayoutDirection(3);
    }

    public final void hide(boolean z) {
        long j;
        if (getVisibility() == 0 && !this.isHiding) {
            this.isHiding = true;
            this.controller.updateWindowFlagsForBackpress(false);
            ViewPropertyAnimator alpha = animate().alpha(0.0f);
            if (z) {
                j = this.ANIMATE_DURATION_SHORT;
            } else {
                j = this.ANIMATE_DURATION;
            }
            alpha.setDuration(j).withEndAction(new StackEducationView$hide$1(this));
        }
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        setFocusableInTouchMode(true);
        setOnKeyListener(new StackEducationView$onAttachedToWindow$1(this));
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setOnKeyListener((View.OnKeyListener) null);
        this.controller.updateWindowFlagsForBackpress(false);
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        setLayoutDirection(getResources().getConfiguration().getLayoutDirection());
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(new int[]{16843829, 16842809});
        int color = obtainStyledAttributes.getColor(0, -16777216);
        int color2 = obtainStyledAttributes.getColor(1, -1);
        obtainStyledAttributes.recycle();
        int ensureTextContrast = ContrastColorUtil.ensureTextContrast(color2, color, true);
        ((TextView) this.titleTextView$delegate.getValue()).setTextColor(ensureTextContrast);
        ((TextView) this.descTextView$delegate.getValue()).setTextColor(ensureTextContrast);
    }

    public final void setLayoutDirection(int i) {
        int i2;
        super.setLayoutDirection(i);
        View view = (View) this.view$delegate.getValue();
        if (getResources().getConfiguration().getLayoutDirection() == 0) {
            i2 = C1777R.C1778drawable.bubble_stack_user_education_bg;
        } else {
            i2 = C1777R.C1778drawable.bubble_stack_user_education_bg_rtl;
        }
        view.setBackgroundResource(i2);
    }
}
