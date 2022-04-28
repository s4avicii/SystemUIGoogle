package com.android.systemui.statusbar.notification.stack;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.util.Property;
import android.view.View;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.p006qs.QSAnimator$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Stack;

public final class StackStateAnimator {
    public AnimationFilter mAnimationFilter = new AnimationFilter();
    public Stack<AnimatorListenerAdapter> mAnimationListenerPool = new Stack<>();
    public final C13881 mAnimationProperties;
    public HashSet<Animator> mAnimatorSet = new HashSet<>();
    public ValueAnimator mBottomOverScrollAnimator;
    public long mCurrentAdditionalDelay;
    public long mCurrentLength;
    public final int mGoToFullShadeAppearingTranslation;
    public HashSet<View> mHeadsUpAppearChildren = new HashSet<>();
    public int mHeadsUpAppearHeightBottom;
    public HashSet<View> mHeadsUpDisappearChildren = new HashSet<>();
    public NotificationStackScrollLayout mHostLayout;
    public StackStateLogger mLogger;
    public ArrayList<View> mNewAddChildren = new ArrayList<>();
    public ArrayList<NotificationStackScrollLayout.AnimationEvent> mNewEvents = new ArrayList<>();
    public boolean mShadeExpanded;
    public NotificationShelf mShelf;
    public int[] mTmpLocation = new int[2];
    public final ExpandableViewState mTmpState = new ExpandableViewState();
    public ValueAnimator mTopOverScrollAnimator;
    public ArrayList<ExpandableView> mTransientViewsToRemove = new ArrayList<>();

    public final void onAnimationFinished() {
        NotificationStackScrollLayout notificationStackScrollLayout = this.mHostLayout;
        Objects.requireNonNull(notificationStackScrollLayout);
        notificationStackScrollLayout.setAnimationRunning(false);
        notificationStackScrollLayout.requestChildrenUpdate();
        Iterator<Runnable> it = notificationStackScrollLayout.mAnimationFinishedRunnables.iterator();
        while (it.hasNext()) {
            it.next().run();
        }
        notificationStackScrollLayout.mAnimationFinishedRunnables.clear();
        Iterator<ExpandableView> it2 = notificationStackScrollLayout.mClearTransientViewsWhenFinished.iterator();
        while (it2.hasNext()) {
            it2.next().removeFromTransientContainer();
        }
        notificationStackScrollLayout.mClearTransientViewsWhenFinished.clear();
        for (int i = 0; i < notificationStackScrollLayout.getChildCount(); i++) {
            View childAt = notificationStackScrollLayout.getChildAt(i);
            if (childAt instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) childAt;
                expandableNotificationRow.setHeadsUpAnimatingAway(false);
                if (expandableNotificationRow.mIsSummaryWithChildren) {
                    for (ExpandableNotificationRow headsUpAnimatingAway : expandableNotificationRow.getAttachedChildren()) {
                        headsUpAnimatingAway.setHeadsUpAnimatingAway(false);
                    }
                }
            }
        }
        AmbientState ambientState = notificationStackScrollLayout.mAmbientState;
        Objects.requireNonNull(ambientState);
        if (ambientState.mClearAllInProgress) {
            notificationStackScrollLayout.setClearAllInProgress(false);
            if (notificationStackScrollLayout.mShadeNeedsToClose) {
                notificationStackScrollLayout.mShadeNeedsToClose = false;
                notificationStackScrollLayout.postDelayed(new QSAnimator$$ExternalSyntheticLambda0(notificationStackScrollLayout, 3), 200);
            }
        }
        Iterator<ExpandableView> it3 = this.mTransientViewsToRemove.iterator();
        while (it3.hasNext()) {
            it3.next().removeFromTransientContainer();
        }
        this.mTransientViewsToRemove.clear();
    }

    public static void $r8$lambda$61WUm2lxj80T6Ev5pK6kC2FMdCY(StackStateAnimator stackStateAnimator, String str) {
        Objects.requireNonNull(stackStateAnimator);
        StackStateLogger stackStateLogger = stackStateAnimator.mLogger;
        Objects.requireNonNull(stackStateLogger);
        LogBuffer logBuffer = stackStateLogger.buffer;
        LogLevel logLevel = LogLevel.INFO;
        StackStateLogger$appearAnimationEnded$2 stackStateLogger$appearAnimationEnded$2 = StackStateLogger$appearAnimationEnded$2.INSTANCE;
        Objects.requireNonNull(logBuffer);
        if (!logBuffer.frozen) {
            LogMessageImpl obtain = logBuffer.obtain("StackScroll", logLevel, stackStateLogger$appearAnimationEnded$2);
            obtain.str1 = str;
            logBuffer.push(obtain);
        }
    }

    public StackStateAnimator(NotificationStackScrollLayout notificationStackScrollLayout) {
        this.mHostLayout = notificationStackScrollLayout;
        this.mGoToFullShadeAppearingTranslation = notificationStackScrollLayout.getContext().getResources().getDimensionPixelSize(C1777R.dimen.go_to_full_shade_appearing_translation);
        notificationStackScrollLayout.getContext().getResources().getDimensionPixelSize(C1777R.dimen.pulsing_notification_appear_translation);
        this.mAnimationProperties = new AnimationProperties() {
            public final AnimationFilter getAnimationFilter() {
                return StackStateAnimator.this.mAnimationFilter;
            }

            public final AnimatorListenerAdapter getAnimationFinishListener(Property property) {
                StackStateAnimator stackStateAnimator = StackStateAnimator.this;
                Objects.requireNonNull(stackStateAnimator);
                if (!stackStateAnimator.mAnimationListenerPool.empty()) {
                    return stackStateAnimator.mAnimationListenerPool.pop();
                }
                return new AnimatorListenerAdapter() {
                    public boolean mWasCancelled;

                    public final void onAnimationCancel(Animator animator) {
                        this.mWasCancelled = true;
                    }

                    public final void onAnimationStart(Animator animator) {
                        this.mWasCancelled = false;
                        StackStateAnimator.this.mAnimatorSet.add(animator);
                    }

                    public final void onAnimationEnd(Animator animator) {
                        StackStateAnimator.this.mAnimatorSet.remove(animator);
                        if (StackStateAnimator.this.mAnimatorSet.isEmpty() && !this.mWasCancelled) {
                            StackStateAnimator.this.onAnimationFinished();
                        }
                        StackStateAnimator.this.mAnimationListenerPool.push(this);
                    }
                };
            }

            public final boolean wasAdded(View view) {
                return StackStateAnimator.this.mNewAddChildren.contains(view);
            }
        };
    }
}
