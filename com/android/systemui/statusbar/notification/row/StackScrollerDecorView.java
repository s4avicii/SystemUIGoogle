package com.android.systemui.statusbar.notification.row;

import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.PathInterpolator;
import com.android.internal.annotations.VisibleForTesting;
import com.android.p012wm.shell.TaskView$$ExternalSyntheticLambda2;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda19;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.biometrics.UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.stack.PeopleHubView;

public abstract class StackScrollerDecorView extends ExpandableView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public View mContent;
    public boolean mContentAnimating;
    public final BubbleStackView$$ExternalSyntheticLambda19 mContentVisibilityEndRunnable = new BubbleStackView$$ExternalSyntheticLambda19(this, 5);
    public boolean mContentVisible = true;
    public int mDuration = 260;
    public boolean mIsSecondaryVisible = true;
    public boolean mIsVisible = true;
    public boolean mSecondaryAnimating = false;
    public View mSecondaryView;
    public final TaskView$$ExternalSyntheticLambda2 mSecondaryVisibilityEndRunnable = new TaskView$$ExternalSyntheticLambda2(this, 5);

    public abstract View findContentView();

    public abstract View findSecondaryView();

    public final boolean hasOverlappingRendering() {
        return false;
    }

    public boolean isTransparent() {
        return true;
    }

    public boolean needsClippingToShelf() {
        return this instanceof PeopleHubView;
    }

    public final void performAddAnimation(long j, long j2) {
        setContentVisible(true);
    }

    public final long performRemoveAnimation(long j, long j2, float f, boolean z, float f2, Runnable runnable, AnimatorListenerAdapter animatorListenerAdapter) {
        setContentVisible(false, true, runnable);
        return 0;
    }

    public final void setContentVisible(boolean z) {
        setContentVisible(z, true, (Runnable) null);
    }

    public final void performAddAnimation(long j, long j2, boolean z) {
        setContentVisible(true);
    }

    public final void setContentVisible(boolean z, boolean z2, Runnable runnable) {
        if (this.mContentVisible != z) {
            this.mContentAnimating = z2;
            this.mContentVisible = z;
            setViewVisible(this.mContent, z, z2, runnable == null ? this.mContentVisibilityEndRunnable : new UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda0(this, runnable, 1));
        } else if (runnable != null) {
            runnable.run();
        }
        if (!this.mContentAnimating) {
            this.mContentVisibilityEndRunnable.run();
        }
    }

    public final void setSecondaryVisible(boolean z, boolean z2) {
        if (this.mIsSecondaryVisible != z) {
            this.mSecondaryAnimating = z2;
            this.mIsSecondaryVisible = z;
            setViewVisible(this.mSecondaryView, z, z2, this.mSecondaryVisibilityEndRunnable);
        }
        if (!this.mSecondaryAnimating) {
            this.mSecondaryVisibilityEndRunnable.run();
        }
    }

    public final void setViewVisible(View view, boolean z, boolean z2, Runnable runnable) {
        float f;
        PathInterpolator pathInterpolator;
        if (view != null) {
            if (view.getVisibility() != 0) {
                view.setVisibility(0);
            }
            view.animate().cancel();
            if (z) {
                f = 1.0f;
            } else {
                f = 0.0f;
            }
            if (!z2) {
                view.setAlpha(f);
                if (runnable != null) {
                    runnable.run();
                    return;
                }
                return;
            }
            if (z) {
                pathInterpolator = Interpolators.ALPHA_IN;
            } else {
                pathInterpolator = Interpolators.ALPHA_OUT;
            }
            view.animate().alpha(f).setInterpolator(pathInterpolator).setDuration((long) this.mDuration).withEndAction(runnable);
        }
    }

    public void setVisible(boolean z, boolean z2) {
        int i;
        if (this.mIsVisible != z) {
            this.mIsVisible = z;
            if (z2) {
                if (z) {
                    setVisibility(0);
                    this.mWillBeGone = false;
                    notifyHeightChanged(false);
                } else {
                    this.mWillBeGone = true;
                }
                setContentVisible(z, true, (Runnable) null);
                return;
            }
            if (z) {
                i = 0;
            } else {
                i = 8;
            }
            setVisibility(i);
            setContentVisible(z, false, (Runnable) null);
            this.mWillBeGone = false;
            notifyHeightChanged(false);
        }
    }

    public StackScrollerDecorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setClipChildren(false);
    }

    public void onFinishInflate() {
        super.onFinishInflate();
        this.mContent = findContentView();
        this.mSecondaryView = findSecondaryView();
        setVisible(false, false);
        setSecondaryVisible(false, false);
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        setOutlineProvider((ViewOutlineProvider) null);
    }

    @VisibleForTesting
    public boolean isSecondaryVisible() {
        return this.mIsSecondaryVisible;
    }
}
