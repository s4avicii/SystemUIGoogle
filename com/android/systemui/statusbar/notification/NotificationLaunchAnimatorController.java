package com.android.systemui.statusbar.notification;

import android.graphics.PorterDuffXfermode;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.animation.LaunchAnimator;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationBackgroundView;
import com.android.systemui.statusbar.notification.stack.C1383xbae1b0c2;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;
import com.android.systemui.statusbar.phone.NotificationShadeWindowViewController;
import java.util.Objects;

/* compiled from: NotificationLaunchAnimatorController.kt */
public final class NotificationLaunchAnimatorController implements ActivityLaunchAnimator.Controller {
    public final HeadsUpManagerPhone headsUpManager;
    public final InteractionJankMonitor jankMonitor;
    public final ExpandableNotificationRow notification;
    public final NotificationEntry notificationEntry;
    public final String notificationKey;
    public final NotificationListContainer notificationListContainer;
    public final NotificationShadeWindowViewController notificationShadeWindowViewController;

    public final void setLaunchContainer(ViewGroup viewGroup) {
    }

    public final void applyParams(ExpandAnimationParameters expandAnimationParameters) {
        int i;
        ExpandableNotificationRow expandableNotificationRow = this.notification;
        Objects.requireNonNull(expandableNotificationRow);
        if (expandAnimationParameters != null) {
            if (expandAnimationParameters.visible) {
                PathInterpolator pathInterpolator = Interpolators.FAST_OUT_SLOW_IN;
                PorterDuffXfermode porterDuffXfermode = LaunchAnimator.SRC_MODE;
                LaunchAnimator.Timings timings = ActivityLaunchAnimator.TIMINGS;
                float lerp = MathUtils.lerp(expandAnimationParameters.startTranslationZ, (float) expandableNotificationRow.mNotificationLaunchHeight, pathInterpolator.getInterpolation(LaunchAnimator.Companion.getProgress(timings, expandAnimationParameters.linearProgress, 0, 50)));
                expandableNotificationRow.setTranslationZ(lerp);
                float width = (float) ((expandAnimationParameters.right - expandAnimationParameters.left) - expandableNotificationRow.getWidth());
                expandableNotificationRow.mExtraWidthForClipping = width;
                expandableNotificationRow.updateClipping();
                expandableNotificationRow.invalidate();
                if (expandAnimationParameters.startRoundedTopClipping > 0) {
                    float interpolation = pathInterpolator.getInterpolation(LaunchAnimator.Companion.getProgress(timings, expandAnimationParameters.linearProgress, 0, 100));
                    float f = expandAnimationParameters.startNotificationTop;
                    i = (int) Math.min(MathUtils.lerp(f, (float) expandAnimationParameters.top, interpolation), f);
                } else {
                    i = expandAnimationParameters.top;
                }
                int i2 = expandAnimationParameters.bottom - i;
                expandableNotificationRow.setActualHeight(i2, true);
                int i3 = expandAnimationParameters.startClipTopAmount;
                int lerp2 = (int) MathUtils.lerp(i3, 0, expandAnimationParameters.progress);
                ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow.mNotificationParent;
                if (expandableNotificationRow2 != null) {
                    float translationY = expandableNotificationRow2.getTranslationY();
                    i = (int) (((float) i) - translationY);
                    expandableNotificationRow.mNotificationParent.setTranslationZ(lerp);
                    expandableNotificationRow.mNotificationParent.setClipTopAmount(Math.min(expandAnimationParameters.parentStartClipTopAmount, lerp2 + i));
                    ExpandableNotificationRow expandableNotificationRow3 = expandableNotificationRow.mNotificationParent;
                    Objects.requireNonNull(expandableNotificationRow3);
                    expandableNotificationRow3.mExtraWidthForClipping = width;
                    expandableNotificationRow3.updateClipping();
                    expandableNotificationRow3.invalidate();
                    ExpandableNotificationRow expandableNotificationRow4 = expandableNotificationRow.mNotificationParent;
                    Objects.requireNonNull(expandableNotificationRow4);
                    ExpandableNotificationRow expandableNotificationRow5 = expandableNotificationRow.mNotificationParent;
                    Objects.requireNonNull(expandableNotificationRow5);
                    ExpandableNotificationRow expandableNotificationRow6 = expandableNotificationRow.mNotificationParent;
                    Objects.requireNonNull(expandableNotificationRow6);
                    expandableNotificationRow6.mMinimumHeightForClipping = (int) (Math.max((float) expandAnimationParameters.bottom, (((float) expandableNotificationRow4.mActualHeight) + translationY) - ((float) expandableNotificationRow5.mClipBottomAmount)) - Math.min((float) expandAnimationParameters.top, translationY));
                    expandableNotificationRow6.updateClipping();
                    expandableNotificationRow6.invalidate();
                } else if (i3 != 0) {
                    expandableNotificationRow.setClipTopAmount(lerp2);
                }
                expandableNotificationRow.setTranslationY((float) i);
                expandableNotificationRow.invalidateOutline();
                NotificationBackgroundView notificationBackgroundView = expandableNotificationRow.mBackgroundNormal;
                Objects.requireNonNull(notificationBackgroundView);
                notificationBackgroundView.mExpandAnimationHeight = expandAnimationParameters.right - expandAnimationParameters.left;
                notificationBackgroundView.mExpandAnimationWidth = i2;
                notificationBackgroundView.invalidate();
            } else if (expandableNotificationRow.getVisibility() == 0) {
                expandableNotificationRow.setVisibility(4);
            }
        }
        this.notificationListContainer.applyExpandAnimationParams(expandAnimationParameters);
    }

    public final LaunchAnimator.State createAnimatorState() {
        float f;
        ExpandableNotificationRow expandableNotificationRow = this.notification;
        Objects.requireNonNull(expandableNotificationRow);
        int i = expandableNotificationRow.mActualHeight;
        ExpandableNotificationRow expandableNotificationRow2 = this.notification;
        Objects.requireNonNull(expandableNotificationRow2);
        int max = Math.max(0, i - expandableNotificationRow2.mClipBottomAmount);
        int[] locationOnScreen = this.notification.getLocationOnScreen();
        int topClippingStartLocation = this.notificationListContainer.getTopClippingStartLocation();
        int max2 = Math.max(topClippingStartLocation - locationOnScreen[1], 0);
        int i2 = locationOnScreen[1] + max2;
        if (max2 > 0) {
            f = 0.0f;
        } else {
            f = this.notification.getCurrentBackgroundRadiusTop();
        }
        ExpandAnimationParameters expandAnimationParameters = new ExpandAnimationParameters(i2, locationOnScreen[1] + max, locationOnScreen[0], this.notification.getWidth() + locationOnScreen[0], f, this.notification.getCurrentBackgroundRadiusBottom());
        expandAnimationParameters.startTranslationZ = this.notification.getTranslationZ();
        expandAnimationParameters.startNotificationTop = this.notification.getTranslationY();
        expandAnimationParameters.startRoundedTopClipping = max2;
        ExpandableNotificationRow expandableNotificationRow3 = this.notification;
        Objects.requireNonNull(expandableNotificationRow3);
        expandAnimationParameters.startClipTopAmount = expandableNotificationRow3.mClipTopAmount;
        if (this.notification.isChildInGroup()) {
            float f2 = expandAnimationParameters.startNotificationTop;
            ExpandableNotificationRow expandableNotificationRow4 = this.notification;
            Objects.requireNonNull(expandableNotificationRow4);
            expandAnimationParameters.startNotificationTop = expandableNotificationRow4.mNotificationParent.getTranslationY() + f2;
            ExpandableNotificationRow expandableNotificationRow5 = this.notification;
            Objects.requireNonNull(expandableNotificationRow5);
            expandAnimationParameters.parentStartRoundedTopClipping = Math.max(topClippingStartLocation - expandableNotificationRow5.mNotificationParent.getLocationOnScreen()[1], 0);
            ExpandableNotificationRow expandableNotificationRow6 = this.notification;
            Objects.requireNonNull(expandableNotificationRow6);
            ExpandableNotificationRow expandableNotificationRow7 = expandableNotificationRow6.mNotificationParent;
            Objects.requireNonNull(expandableNotificationRow7);
            int i3 = expandableNotificationRow7.mClipTopAmount;
            expandAnimationParameters.parentStartClipTopAmount = i3;
            if (i3 != 0) {
                float translationY = ((float) i3) - this.notification.getTranslationY();
                if (translationY > 0.0f) {
                    expandAnimationParameters.startClipTopAmount = (int) Math.ceil((double) translationY);
                }
            }
        }
        return expandAnimationParameters;
    }

    public final ViewGroup getLaunchContainer() {
        View rootView = this.notification.getRootView();
        Objects.requireNonNull(rootView, "null cannot be cast to non-null type android.view.ViewGroup");
        return (ViewGroup) rootView;
    }

    public final void onIntentStarted(boolean z) {
        NotificationShadeWindowViewController notificationShadeWindowViewController2 = this.notificationShadeWindowViewController;
        Objects.requireNonNull(notificationShadeWindowViewController2);
        if (notificationShadeWindowViewController2.mExpandAnimationRunning != z) {
            notificationShadeWindowViewController2.mExpandAnimationRunning = z;
            notificationShadeWindowViewController2.mNotificationShadeWindowController.setLaunchingActivity(z);
        }
        NotificationEntry notificationEntry2 = this.notificationEntry;
        Objects.requireNonNull(notificationEntry2);
        notificationEntry2.mExpandAnimationRunning = z;
        if (!z) {
            removeHun(true);
        }
    }

    public final void onLaunchAnimationCancelled() {
        NotificationShadeWindowViewController notificationShadeWindowViewController2 = this.notificationShadeWindowViewController;
        Objects.requireNonNull(notificationShadeWindowViewController2);
        if (notificationShadeWindowViewController2.mExpandAnimationRunning) {
            notificationShadeWindowViewController2.mExpandAnimationRunning = false;
            notificationShadeWindowViewController2.mNotificationShadeWindowController.setLaunchingActivity(false);
        }
        NotificationEntry notificationEntry2 = this.notificationEntry;
        Objects.requireNonNull(notificationEntry2);
        notificationEntry2.mExpandAnimationRunning = false;
        removeHun(true);
    }

    public final void onLaunchAnimationEnd(boolean z) {
        this.jankMonitor.end(16);
        this.notification.setExpandAnimationRunning(false);
        NotificationShadeWindowViewController notificationShadeWindowViewController2 = this.notificationShadeWindowViewController;
        Objects.requireNonNull(notificationShadeWindowViewController2);
        if (notificationShadeWindowViewController2.mExpandAnimationRunning) {
            notificationShadeWindowViewController2.mExpandAnimationRunning = false;
            notificationShadeWindowViewController2.mNotificationShadeWindowController.setLaunchingActivity(false);
        }
        NotificationEntry notificationEntry2 = this.notificationEntry;
        Objects.requireNonNull(notificationEntry2);
        notificationEntry2.mExpandAnimationRunning = false;
        this.notificationListContainer.setExpandingNotification((ExpandableNotificationRow) null);
        applyParams((ExpandAnimationParameters) null);
        removeHun(false);
    }

    public final void onLaunchAnimationProgress(LaunchAnimator.State state, float f, float f2) {
        ExpandAnimationParameters expandAnimationParameters = (ExpandAnimationParameters) state;
        expandAnimationParameters.progress = f;
        expandAnimationParameters.linearProgress = f2;
        applyParams(expandAnimationParameters);
    }

    public final void onLaunchAnimationStart(boolean z) {
        this.notification.setExpandAnimationRunning(true);
        this.notificationListContainer.setExpandingNotification(this.notification);
        this.jankMonitor.begin(this.notification, 16);
    }

    public final void removeHun(boolean z) {
        Boolean bool;
        if (this.headsUpManager.isAlerting(this.notificationKey)) {
            ExpandableNotificationRow expandableNotificationRow = this.notification;
            if (z) {
                bool = Boolean.TRUE;
            } else {
                bool = null;
            }
            expandableNotificationRow.setTag(C1777R.C1779id.is_clicked_heads_up_tag, bool);
            HeadsUpManagerPhone headsUpManagerPhone = this.headsUpManager;
            String str = this.notificationKey;
            Objects.requireNonNull(headsUpManagerPhone);
            if (z) {
                headsUpManagerPhone.removeNotification(str, true);
                return;
            }
            C1383xbae1b0c2 notificationStackScrollLayoutController$$ExternalSyntheticLambda3 = (C1383xbae1b0c2) headsUpManagerPhone.mAnimationStateHandler;
            Objects.requireNonNull(notificationStackScrollLayoutController$$ExternalSyntheticLambda3);
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController$$ExternalSyntheticLambda3.f$0;
            Objects.requireNonNull(notificationStackScrollLayout);
            notificationStackScrollLayout.mHeadsUpGoingAwayAnimationsAllowed = false;
            headsUpManagerPhone.removeNotification(str, true);
            C1383xbae1b0c2 notificationStackScrollLayoutController$$ExternalSyntheticLambda32 = (C1383xbae1b0c2) headsUpManagerPhone.mAnimationStateHandler;
            Objects.requireNonNull(notificationStackScrollLayoutController$$ExternalSyntheticLambda32);
            NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController$$ExternalSyntheticLambda32.f$0;
            Objects.requireNonNull(notificationStackScrollLayout2);
            notificationStackScrollLayout2.mHeadsUpGoingAwayAnimationsAllowed = true;
        }
    }

    public NotificationLaunchAnimatorController(NotificationShadeWindowViewController notificationShadeWindowViewController2, NotificationListContainer notificationListContainer2, HeadsUpManagerPhone headsUpManagerPhone, ExpandableNotificationRow expandableNotificationRow, InteractionJankMonitor interactionJankMonitor) {
        this.notificationShadeWindowViewController = notificationShadeWindowViewController2;
        this.notificationListContainer = notificationListContainer2;
        this.headsUpManager = headsUpManagerPhone;
        this.notification = expandableNotificationRow;
        this.jankMonitor = interactionJankMonitor;
        Objects.requireNonNull(expandableNotificationRow);
        NotificationEntry notificationEntry2 = expandableNotificationRow.mEntry;
        this.notificationEntry = notificationEntry2;
        Objects.requireNonNull(notificationEntry2);
        this.notificationKey = notificationEntry2.mSbn.getKey();
    }
}
