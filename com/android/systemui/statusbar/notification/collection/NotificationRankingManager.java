package com.android.systemui.statusbar.notification.collection;

import android.service.notification.NotificationListenerService;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.NotificationEntryManagerLogger;
import com.android.systemui.statusbar.notification.NotificationFilter;
import com.android.systemui.statusbar.notification.NotificationSectionsFeatureManager;
import com.android.systemui.statusbar.notification.collection.legacy.LegacyNotificationRanker;
import com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy;
import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p018io.CloseableKt;
import kotlin.sequences.FilteringSequence;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt$sortedWith$1;

/* compiled from: NotificationRankingManager.kt */
public final class NotificationRankingManager implements LegacyNotificationRanker {
    public final NotificationGroupManagerLegacy groupManager;
    public final HeadsUpManager headsUpManager;
    public final HighPriorityProvider highPriorityProvider;
    public final NotificationEntryManager.KeyguardEnvironment keyguardEnvironment;
    public final NotificationEntryManagerLogger logger;
    public final Lazy mediaManager$delegate = LazyKt__LazyJVMKt.lazy(new NotificationRankingManager$mediaManager$2(this));
    public final dagger.Lazy<NotificationMediaManager> mediaManagerLazy;
    public final NotificationFilter notifFilter;
    public final PeopleNotificationIdentifier peopleNotificationIdentifier;
    public final NotificationRankingManager$rankingComparator$1 rankingComparator = new NotificationRankingManager$rankingComparator$1(this);
    public NotificationListenerService.RankingMap rankingMap;
    public final NotificationSectionsFeatureManager sectionsFeatureManager;

    public final List<NotificationEntry> filterAndSortLocked(Collection<NotificationEntry> collection, String str) {
        int i;
        boolean z;
        this.logger.logFilterAndSort(str);
        List<NotificationEntry> list = SequencesKt___SequencesKt.toList(new SequencesKt___SequencesKt$sortedWith$1(new FilteringSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(collection), false, new NotificationRankingManager$filterAndSortLocked$filtered$1(this)), this.rankingComparator));
        for (NotificationEntry notificationEntry : collection) {
            boolean access$isImportantCall = CloseableKt.access$isImportantCall(notificationEntry);
            boolean isRowHeadsUp = notificationEntry.isRowHeadsUp();
            boolean isImportantMedia = isImportantMedia(notificationEntry);
            boolean access$isSystemMax = CloseableKt.access$isSystemMax(notificationEntry);
            if (CloseableKt.access$isColorizedForegroundService(notificationEntry) || access$isImportantCall) {
                i = 3;
            } else {
                if (this.sectionsFeatureManager.isFilteringEnabled()) {
                    if (this.peopleNotificationIdentifier.getPeopleNotificationType(notificationEntry) != 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (z) {
                        i = 4;
                    }
                }
                if (isRowHeadsUp || isImportantMedia || access$isSystemMax || this.highPriorityProvider.isHighPriority(notificationEntry)) {
                    i = 5;
                } else {
                    i = 6;
                }
            }
            notificationEntry.mBucket = i;
        }
        return list;
    }

    public final boolean isNotificationForCurrentProfiles(NotificationEntry notificationEntry) {
        return this.keyguardEnvironment.isNotificationForCurrentProfiles(notificationEntry.mSbn);
    }

    public final List<NotificationEntry> updateRanking(NotificationListenerService.RankingMap rankingMap2, Collection<NotificationEntry> collection, String str) {
        List<NotificationEntry> filterAndSortLocked;
        if (rankingMap2 != null) {
            this.rankingMap = rankingMap2;
            synchronized (collection) {
                for (NotificationEntry notificationEntry : collection) {
                    NotificationListenerService.Ranking ranking = new NotificationListenerService.Ranking();
                    Objects.requireNonNull(notificationEntry);
                    if (rankingMap2.getRanking(notificationEntry.mKey, ranking)) {
                        notificationEntry.setRanking(ranking);
                        String overrideGroupKey = ranking.getOverrideGroupKey();
                        if (!Objects.equals(notificationEntry.mSbn.getOverrideGroupKey(), overrideGroupKey)) {
                            String groupKey = notificationEntry.mSbn.getGroupKey();
                            boolean isGroup = notificationEntry.mSbn.isGroup();
                            boolean isGroupSummary = notificationEntry.mSbn.getNotification().isGroupSummary();
                            notificationEntry.mSbn.setOverrideGroupKey(overrideGroupKey);
                            this.groupManager.onEntryUpdated(notificationEntry, groupKey, isGroup, isGroupSummary);
                        }
                    }
                }
            }
        }
        synchronized (this) {
            filterAndSortLocked = filterAndSortLocked(collection, str);
        }
        return filterAndSortLocked;
    }

    public NotificationRankingManager(dagger.Lazy<NotificationMediaManager> lazy, NotificationGroupManagerLegacy notificationGroupManagerLegacy, HeadsUpManager headsUpManager2, NotificationFilter notificationFilter, NotificationEntryManagerLogger notificationEntryManagerLogger, NotificationSectionsFeatureManager notificationSectionsFeatureManager, PeopleNotificationIdentifier peopleNotificationIdentifier2, HighPriorityProvider highPriorityProvider2, NotificationEntryManager.KeyguardEnvironment keyguardEnvironment2) {
        this.mediaManagerLazy = lazy;
        this.groupManager = notificationGroupManagerLegacy;
        this.headsUpManager = headsUpManager2;
        this.notifFilter = notificationFilter;
        this.logger = notificationEntryManagerLogger;
        this.sectionsFeatureManager = notificationSectionsFeatureManager;
        this.peopleNotificationIdentifier = peopleNotificationIdentifier2;
        this.highPriorityProvider = highPriorityProvider2;
        this.keyguardEnvironment = keyguardEnvironment2;
    }

    public final boolean isImportantMedia(NotificationEntry notificationEntry) {
        Objects.requireNonNull(notificationEntry);
        String str = notificationEntry.mKey;
        NotificationMediaManager notificationMediaManager = (NotificationMediaManager) this.mediaManager$delegate.getValue();
        Objects.requireNonNull(notificationMediaManager);
        if (!Intrinsics.areEqual(str, notificationMediaManager.mMediaNotificationKey) || notificationEntry.getImportance() <= 1) {
            return false;
        }
        return true;
    }

    public final NotificationListenerService.RankingMap getRankingMap() {
        return this.rankingMap;
    }
}
