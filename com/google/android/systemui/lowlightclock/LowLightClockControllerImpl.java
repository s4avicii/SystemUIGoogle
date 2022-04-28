package com.google.android.systemui.lowlightclock;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.annotations.VisibleForTesting;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.R$anim;
import com.android.systemui.classifier.HistoryTracker$$ExternalSyntheticLambda2;
import com.android.systemui.lowlightclock.LowLightClockController;
import com.android.systemui.recents.OverviewProxyService$$ExternalSyntheticLambda5;
import com.android.systemui.statusbar.phone.NotificationShadeWindowView;
import com.google.common.base.Preconditions;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.function.Consumer;

public final class LowLightClockControllerImpl implements LowLightClockController {
    public final boolean mIsLowLightClockEnabled;
    public boolean mIsShowing;
    public final LayoutInflater mLayoutInflater;
    public final int mMaxBurnInOffset;
    public ViewGroup mParent;
    public View mView;
    public final WeakHashMap mViewsToHide = new WeakHashMap();

    public final void attachLowLightClockView(NotificationShadeWindowView notificationShadeWindowView) {
        boolean z;
        if (this.mParent == null) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkState(z, "Attempting to attach low-light clock when it is already attached.");
        Preconditions.checkState(this.mIsLowLightClockEnabled, "Attempting to attach low-light clock when it is not enabled.");
        this.mParent = notificationShadeWindowView;
        View inflate = this.mLayoutInflater.inflate(C1777R.layout.low_light_clock, (ViewGroup) null);
        this.mView = inflate;
        inflate.setVisibility(4);
        View findViewById = this.mParent.findViewById(C1777R.C1779id.notification_panel);
        Objects.requireNonNull(findViewById, "NotificationPanel must not be null");
        this.mViewsToHide.put(findViewById, Float.valueOf(1.0f));
        ViewGroup viewGroup = this.mParent;
        viewGroup.addView(this.mView, viewGroup.indexOfChild(findViewById) + 1);
        View findViewById2 = this.mParent.findViewById(C1777R.C1779id.keyguard_message_area);
        if (findViewById2 != null) {
            this.mViewsToHide.put(findViewById2, Float.valueOf(1.0f));
        }
    }

    public final void dozeTimeTick() {
        View view = this.mView;
        if (view != null) {
            view.setTranslationX((float) (R$anim.getBurnInOffset(this.mMaxBurnInOffset * 2, true) - this.mMaxBurnInOffset));
            this.mView.setTranslationY((float) (R$anim.getBurnInOffset(this.mMaxBurnInOffset * 2, false) - this.mMaxBurnInOffset));
        }
    }

    public final boolean showLowLightClock(boolean z) {
        Consumer consumer;
        View view = this.mView;
        int i = 0;
        if (view == null) {
            return false;
        }
        boolean z2 = this.mIsShowing;
        if (z2 == z) {
            return z2;
        }
        this.mIsShowing = z;
        if (!z) {
            i = 4;
        }
        view.setVisibility(i);
        Set entrySet = this.mViewsToHide.entrySet();
        if (this.mIsShowing) {
            consumer = new HistoryTracker$$ExternalSyntheticLambda2(this, 1);
        } else {
            consumer = new OverviewProxyService$$ExternalSyntheticLambda5(this, 3);
        }
        entrySet.forEach(consumer);
        return this.mIsShowing;
    }

    public LowLightClockControllerImpl(Resources resources, LayoutInflater layoutInflater) {
        this.mLayoutInflater = layoutInflater;
        this.mIsLowLightClockEnabled = resources.getBoolean(C1777R.bool.config_show_low_light_clock_when_docked);
        this.mMaxBurnInOffset = resources.getDimensionPixelSize(C1777R.dimen.default_burn_in_prevention_offset);
    }

    @VisibleForTesting
    public View getClockView() {
        return this.mView;
    }

    public final boolean isLowLightClockEnabled() {
        return this.mIsLowLightClockEnabled;
    }
}
