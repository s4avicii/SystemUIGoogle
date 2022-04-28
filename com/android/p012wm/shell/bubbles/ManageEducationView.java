package com.android.p012wm.shell.bubbles;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.TaskView;
import java.util.Objects;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;

/* renamed from: com.android.wm.shell.bubbles.ManageEducationView */
/* compiled from: ManageEducationView.kt */
public final class ManageEducationView extends LinearLayout {
    public final long ANIMATE_DURATION = 200;
    public BubbleExpandedView bubbleExpandedView;
    public final Lazy gotItButton$delegate;
    public boolean isHiding;
    public final Lazy manageButton$delegate;
    public final Lazy manageView$delegate;
    public final BubblePositioner positioner;
    public Rect realManageButtonRect;

    public final Button getManageButton() {
        return (Button) this.manageButton$delegate.getValue();
    }

    public final ViewGroup getManageView() {
        return (ViewGroup) this.manageView$delegate.getValue();
    }

    public final void hide() {
        TaskView taskView;
        BubbleExpandedView bubbleExpandedView2 = this.bubbleExpandedView;
        if (!(bubbleExpandedView2 == null || (taskView = bubbleExpandedView2.mTaskView) == null)) {
            taskView.mObscuredTouchRect = null;
        }
        if (getVisibility() == 0 && !this.isHiding) {
            animate().withStartAction(new ManageEducationView$hide$1(this)).alpha(0.0f).setDuration(this.ANIMATE_DURATION).withEndAction(new ManageEducationView$hide$2(this));
        }
    }

    public final void show(BubbleExpandedView bubbleExpandedView2) {
        int i;
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(new int[]{17956900});
        int color = obtainStyledAttributes.getColor(0, 0);
        obtainStyledAttributes.recycle();
        getManageButton().setTextColor(this.mContext.getColor(17170472));
        getManageButton().setBackgroundDrawable(new ColorDrawable(color));
        ((Button) this.gotItButton$delegate.getValue()).setBackgroundDrawable(new ColorDrawable(color));
        if (getVisibility() != 0) {
            this.bubbleExpandedView = bubbleExpandedView2;
            TaskView taskView = bubbleExpandedView2.mTaskView;
            if (taskView != null) {
                BubblePositioner bubblePositioner = this.positioner;
                Objects.requireNonNull(bubblePositioner);
                taskView.mObscuredTouchRect = new Rect(bubblePositioner.mScreenRect);
            }
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            BubblePositioner bubblePositioner2 = this.positioner;
            Objects.requireNonNull(bubblePositioner2);
            if (bubblePositioner2.mIsLargeScreen) {
                i = getContext().getResources().getDimensionPixelSize(C1777R.dimen.bubbles_user_education_width_large_screen);
            } else {
                i = -1;
            }
            layoutParams.width = i;
            setAlpha(0.0f);
            setVisibility(0);
            bubbleExpandedView2.mManageButton.getBoundsOnScreen(this.realManageButtonRect);
            getManageView().setPadding(this.realManageButtonRect.left - ((LinearLayout.LayoutParams) bubbleExpandedView2.mManageButton.getLayoutParams()).getMarginStart(), getManageView().getPaddingTop(), getManageView().getPaddingRight(), getManageView().getPaddingBottom());
            post(new ManageEducationView$show$1(this, bubbleExpandedView2));
            getContext().getSharedPreferences(getContext().getPackageName(), 0).edit().putBoolean("HasSeenBubblesManageOnboarding", true).apply();
        }
    }

    public ManageEducationView(Context context, BubblePositioner bubblePositioner) {
        super(context);
        this.positioner = bubblePositioner;
        this.manageView$delegate = LazyKt__LazyJVMKt.lazy(new ManageEducationView$manageView$2(this));
        this.manageButton$delegate = LazyKt__LazyJVMKt.lazy(new ManageEducationView$manageButton$2(this));
        this.gotItButton$delegate = LazyKt__LazyJVMKt.lazy(new ManageEducationView$gotItButton$2(this));
        this.realManageButtonRect = new Rect();
        LayoutInflater.from(context).inflate(C1777R.layout.bubbles_manage_button_education, this);
        setVisibility(8);
        setElevation((float) getResources().getDimensionPixelSize(C1777R.dimen.bubble_elevation));
        setLayoutDirection(3);
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        setLayoutDirection(getResources().getConfiguration().getLayoutDirection());
    }

    public final void setLayoutDirection(int i) {
        int i2;
        super.setLayoutDirection(i);
        ViewGroup manageView = getManageView();
        if (getResources().getConfiguration().getLayoutDirection() == 1) {
            i2 = C1777R.C1778drawable.bubble_stack_user_education_bg_rtl;
        } else {
            i2 = C1777R.C1778drawable.bubble_stack_user_education_bg;
        }
        manageView.setBackgroundResource(i2);
    }
}
