package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.p012wm.shell.bubbles.Bubbles;
import com.android.systemui.dump.LogBufferEulogizer;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.notifcollection.DismissedByUserStats;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifDismissInterceptor;
import com.android.systemui.util.Assert;
import com.android.systemui.wmshell.BubblesManager;
import com.google.android.systemui.elmyra.actions.UnpinNotifications$$ExternalSyntheticLambda0;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;

public final class BubbleCoordinator implements Coordinator {
    public final Optional<BubblesManager> mBubblesManagerOptional;
    public final Optional<Bubbles> mBubblesOptional;
    public final C12532 mDismissInterceptor = new NotifDismissInterceptor() {
        public final void getName() {
        }

        public final void cancelDismissInterception(NotificationEntry notificationEntry) {
            HashSet hashSet = BubbleCoordinator.this.mInterceptedDismissalEntries;
            Objects.requireNonNull(notificationEntry);
            hashSet.remove(notificationEntry.mKey);
        }

        public final boolean shouldInterceptDismissal(NotificationEntry notificationEntry) {
            if (!BubbleCoordinator.this.mBubblesManagerOptional.isPresent() || !BubbleCoordinator.this.mBubblesManagerOptional.get().handleDismissalInterception(notificationEntry)) {
                HashSet hashSet = BubbleCoordinator.this.mInterceptedDismissalEntries;
                Objects.requireNonNull(notificationEntry);
                hashSet.remove(notificationEntry.mKey);
                return false;
            }
            HashSet hashSet2 = BubbleCoordinator.this.mInterceptedDismissalEntries;
            Objects.requireNonNull(notificationEntry);
            hashSet2.add(notificationEntry.mKey);
            return true;
        }
    };
    public final HashSet mInterceptedDismissalEntries = new HashSet();
    public final C12543 mNotifCallback = new BubblesManager.NotifCallback() {
        public final void maybeCancelSummary(NotificationEntry notificationEntry) {
        }

        public final void invalidateNotifications(String str) {
            BubbleCoordinator.this.mNotifFilter.invalidateList();
        }

        public final void removeNotification(NotificationEntry notificationEntry, DismissedByUserStats dismissedByUserStats, int i) {
            NotifPipeline notifPipeline = BubbleCoordinator.this.mNotifPipeline;
            Objects.requireNonNull(notifPipeline);
            if (!notifPipeline.isNewPipelineEnabled) {
                NotifPipeline notifPipeline2 = BubbleCoordinator.this.mNotifPipeline;
                Objects.requireNonNull(notificationEntry);
                NotificationEntry entry = notifPipeline2.getEntry(notificationEntry.mKey);
                if (entry != null) {
                    notificationEntry = entry;
                }
            }
            BubbleCoordinator bubbleCoordinator = BubbleCoordinator.this;
            Objects.requireNonNull(bubbleCoordinator);
            HashSet hashSet = bubbleCoordinator.mInterceptedDismissalEntries;
            Objects.requireNonNull(notificationEntry);
            if (hashSet.contains(notificationEntry.mKey)) {
                BubbleCoordinator.this.mInterceptedDismissalEntries.remove(notificationEntry.mKey);
                BubbleCoordinator bubbleCoordinator2 = BubbleCoordinator.this;
                NotifDismissInterceptor.OnEndDismissInterception onEndDismissInterception = bubbleCoordinator2.mOnEndDismissInterception;
                C12532 r3 = bubbleCoordinator2.mDismissInterceptor;
                NotifCollection$$ExternalSyntheticLambda1 notifCollection$$ExternalSyntheticLambda1 = (NotifCollection$$ExternalSyntheticLambda1) onEndDismissInterception;
                Objects.requireNonNull(notifCollection$$ExternalSyntheticLambda1);
                NotifCollection notifCollection = notifCollection$$ExternalSyntheticLambda1.f$0;
                Objects.requireNonNull(notifCollection);
                Assert.isMainThread();
                if (notifCollection.mAttached) {
                    notifCollection.checkForReentrantCall();
                    boolean z = true;
                    if (notificationEntry.mDismissInterceptors.remove(r3)) {
                        if (notificationEntry.mDismissInterceptors.size() <= 0) {
                            z = false;
                        }
                        if (!z) {
                            notifCollection.dismissNotification(notificationEntry, dismissedByUserStats);
                            return;
                        }
                        return;
                    }
                    LogBufferEulogizer logBufferEulogizer = notifCollection.mEulogizer;
                    Objects.requireNonNull(r3);
                    IllegalStateException illegalStateException = new IllegalStateException(String.format("Cannot end dismiss interceptor for interceptor \"%s\" (%s)", new Object[]{"BubbleCoordinator", r3}));
                    logBufferEulogizer.record(illegalStateException);
                    throw illegalStateException;
                }
            } else if (BubbleCoordinator.this.mNotifPipeline.getEntry(notificationEntry.mKey) != null) {
                BubbleCoordinator.this.mNotifCollection.dismissNotification(notificationEntry, dismissedByUserStats);
            }
        }
    };
    public final NotifCollection mNotifCollection;
    public final C12521 mNotifFilter = new NotifFilter() {
        public final boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            if (BubbleCoordinator.this.mBubblesOptional.isPresent()) {
                Objects.requireNonNull(notificationEntry);
                if (BubbleCoordinator.this.mBubblesOptional.get().isBubbleNotificationSuppressedFromShade(notificationEntry.mKey, notificationEntry.mSbn.getGroupKey())) {
                    return true;
                }
            }
            return false;
        }
    };
    public NotifPipeline mNotifPipeline;
    public NotifDismissInterceptor.OnEndDismissInterception mOnEndDismissInterception;

    public final void attach(NotifPipeline notifPipeline) {
        this.mNotifPipeline = notifPipeline;
        C12532 r0 = this.mDismissInterceptor;
        Objects.requireNonNull(notifPipeline);
        NotifCollection notifCollection = notifPipeline.mNotifCollection;
        Objects.requireNonNull(notifCollection);
        Assert.isMainThread();
        notifCollection.checkForReentrantCall();
        if (!notifCollection.mDismissInterceptors.contains(r0)) {
            notifCollection.mDismissInterceptors.add(r0);
            NotifCollection$$ExternalSyntheticLambda1 notifCollection$$ExternalSyntheticLambda1 = new NotifCollection$$ExternalSyntheticLambda1(notifCollection);
            Objects.requireNonNull(r0);
            BubbleCoordinator.this.mOnEndDismissInterception = notifCollection$$ExternalSyntheticLambda1;
            this.mNotifPipeline.addPreGroupFilter(this.mNotifFilter);
            this.mBubblesManagerOptional.ifPresent(new UnpinNotifications$$ExternalSyntheticLambda0(this, 1));
            return;
        }
        throw new IllegalArgumentException("Interceptor " + r0 + " already added.");
    }

    public BubbleCoordinator(Optional<BubblesManager> optional, Optional<Bubbles> optional2, NotifCollection notifCollection) {
        this.mBubblesManagerOptional = optional;
        this.mBubblesOptional = optional2;
        this.mNotifCollection = notifCollection;
    }
}
