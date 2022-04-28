package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.SectionClassifier;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;

public final class RankingCoordinator implements Coordinator {
    public final NodeController mAlertingHeaderController;
    public final C12751 mAlertingNotifSectioner = new NotifSectioner() {
        public final NodeController getHeaderNodeController() {
            return null;
        }

        public final boolean isInSection(ListEntry listEntry) {
            return RankingCoordinator.this.mHighPriorityProvider.isHighPriority(listEntry);
        }
    };
    public final C12795 mDndVisualEffectsFilter = new NotifFilter() {
        public final boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            if (RankingCoordinator.this.mStatusBarStateController.isDozing()) {
                Objects.requireNonNull(notificationEntry);
                if (notificationEntry.shouldSuppressVisualEffect(128)) {
                    return true;
                }
            }
            if (!RankingCoordinator.this.mStatusBarStateController.isDozing()) {
                Objects.requireNonNull(notificationEntry);
                if (notificationEntry.shouldSuppressVisualEffect(256)) {
                    return true;
                }
            }
            return false;
        }
    };
    public boolean mHasMinimizedEntries;
    public boolean mHasSilentEntries;
    public final HighPriorityProvider mHighPriorityProvider;
    public final C12773 mMinimizedNotifSectioner = new NotifSectioner() {
        public final NodeController getHeaderNodeController() {
            return RankingCoordinator.this.mSilentNodeController;
        }

        public final boolean isInSection(ListEntry listEntry) {
            if (RankingCoordinator.this.mHighPriorityProvider.isHighPriority(listEntry) || !listEntry.getRepresentativeEntry().isAmbient()) {
                return false;
            }
            return true;
        }

        public final void onEntriesUpdated(ArrayList arrayList) {
            int i = 0;
            RankingCoordinator.this.mHasMinimizedEntries = false;
            while (true) {
                if (i >= arrayList.size()) {
                    break;
                }
                NotificationEntry representativeEntry = ((ListEntry) arrayList.get(i)).getRepresentativeEntry();
                Objects.requireNonNull(representativeEntry);
                if (representativeEntry.mSbn.isClearable()) {
                    RankingCoordinator.this.mHasMinimizedEntries = true;
                    break;
                }
                i++;
            }
            RankingCoordinator rankingCoordinator = RankingCoordinator.this;
            rankingCoordinator.mSilentHeaderController.setClearSectionEnabled(rankingCoordinator.mHasMinimizedEntries | rankingCoordinator.mHasSilentEntries);
        }
    };
    public final SectionClassifier mSectionClassifier;
    public final SectionHeaderController mSilentHeaderController;
    public final NodeController mSilentNodeController;
    public final C12762 mSilentNotifSectioner = new NotifSectioner() {
        public final NodeController getHeaderNodeController() {
            return RankingCoordinator.this.mSilentNodeController;
        }

        public final boolean isInSection(ListEntry listEntry) {
            if (RankingCoordinator.this.mHighPriorityProvider.isHighPriority(listEntry) || listEntry.getRepresentativeEntry().isAmbient()) {
                return false;
            }
            return true;
        }

        public final void onEntriesUpdated(ArrayList arrayList) {
            int i = 0;
            RankingCoordinator.this.mHasSilentEntries = false;
            while (true) {
                if (i >= arrayList.size()) {
                    break;
                }
                NotificationEntry representativeEntry = ((ListEntry) arrayList.get(i)).getRepresentativeEntry();
                Objects.requireNonNull(representativeEntry);
                if (representativeEntry.mSbn.isClearable()) {
                    RankingCoordinator.this.mHasSilentEntries = true;
                    break;
                }
                i++;
            }
            RankingCoordinator rankingCoordinator = RankingCoordinator.this;
            rankingCoordinator.mSilentHeaderController.setClearSectionEnabled(rankingCoordinator.mHasMinimizedEntries | rankingCoordinator.mHasSilentEntries);
        }
    };
    public final C12806 mStatusBarStateCallback = new StatusBarStateController.StateListener() {
        public final void onDozingChanged(boolean z) {
            RankingCoordinator.this.mDndVisualEffectsFilter.invalidateList();
        }
    };
    public final StatusBarStateController mStatusBarStateController;
    public final C12784 mSuspendedFilter = new NotifFilter() {
        public final boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            Objects.requireNonNull(notificationEntry);
            return notificationEntry.mRanking.isSuspended();
        }
    };

    public final void attach(NotifPipeline notifPipeline) {
        this.mStatusBarStateController.addCallback(this.mStatusBarStateCallback);
        SectionClassifier sectionClassifier = this.mSectionClassifier;
        Set singleton = Collections.singleton(this.mMinimizedNotifSectioner);
        Objects.requireNonNull(sectionClassifier);
        sectionClassifier.lowPrioritySections = CollectionsKt___CollectionsKt.toSet(singleton);
        notifPipeline.addPreGroupFilter(this.mSuspendedFilter);
        notifPipeline.addPreGroupFilter(this.mDndVisualEffectsFilter);
    }

    public RankingCoordinator(StatusBarStateController statusBarStateController, HighPriorityProvider highPriorityProvider, SectionClassifier sectionClassifier, NodeController nodeController, SectionHeaderController sectionHeaderController, NodeController nodeController2) {
        this.mStatusBarStateController = statusBarStateController;
        this.mHighPriorityProvider = highPriorityProvider;
        this.mSectionClassifier = sectionClassifier;
        this.mAlertingHeaderController = nodeController;
        this.mSilentNodeController = nodeController2;
        this.mSilentHeaderController = sectionHeaderController;
    }
}
