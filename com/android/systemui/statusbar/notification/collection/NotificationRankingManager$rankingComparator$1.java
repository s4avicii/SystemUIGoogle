package com.android.systemui.statusbar.notification.collection;

import android.service.notification.StatusBarNotification;
import java.util.Comparator;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p018io.CloseableKt;

/* compiled from: NotificationRankingManager.kt */
public final class NotificationRankingManager$rankingComparator$1<T> implements Comparator {
    public final /* synthetic */ NotificationRankingManager this$0;

    public NotificationRankingManager$rankingComparator$1(NotificationRankingManager notificationRankingManager) {
        this.this$0 = notificationRankingManager;
    }

    public final int compare(Object obj, Object obj2) {
        NotificationEntry notificationEntry = (NotificationEntry) obj;
        NotificationEntry notificationEntry2 = (NotificationEntry) obj2;
        Objects.requireNonNull(notificationEntry);
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        Objects.requireNonNull(notificationEntry2);
        StatusBarNotification statusBarNotification2 = notificationEntry2.mSbn;
        int rank = notificationEntry.mRanking.getRank();
        int rank2 = notificationEntry2.mRanking.getRank();
        boolean access$isColorizedForegroundService = CloseableKt.access$isColorizedForegroundService(notificationEntry);
        boolean access$isColorizedForegroundService2 = CloseableKt.access$isColorizedForegroundService(notificationEntry2);
        boolean access$isImportantCall = CloseableKt.access$isImportantCall(notificationEntry);
        boolean access$isImportantCall2 = CloseableKt.access$isImportantCall(notificationEntry2);
        NotificationRankingManager notificationRankingManager = this.this$0;
        Objects.requireNonNull(notificationRankingManager);
        int peopleNotificationType = notificationRankingManager.peopleNotificationIdentifier.getPeopleNotificationType(notificationEntry);
        NotificationRankingManager notificationRankingManager2 = this.this$0;
        Objects.requireNonNull(notificationRankingManager2);
        int peopleNotificationType2 = notificationRankingManager2.peopleNotificationIdentifier.getPeopleNotificationType(notificationEntry2);
        boolean isImportantMedia = this.this$0.isImportantMedia(notificationEntry);
        boolean isImportantMedia2 = this.this$0.isImportantMedia(notificationEntry2);
        boolean access$isSystemMax = CloseableKt.access$isSystemMax(notificationEntry);
        StatusBarNotification statusBarNotification3 = statusBarNotification;
        boolean access$isSystemMax2 = CloseableKt.access$isSystemMax(notificationEntry2);
        StatusBarNotification statusBarNotification4 = statusBarNotification2;
        boolean isRowHeadsUp = notificationEntry.isRowHeadsUp();
        int i = rank;
        boolean isRowHeadsUp2 = notificationEntry2.isRowHeadsUp();
        int i2 = rank2;
        NotificationRankingManager notificationRankingManager3 = this.this$0;
        Objects.requireNonNull(notificationRankingManager3);
        boolean isHighPriority = notificationRankingManager3.highPriorityProvider.isHighPriority(notificationEntry);
        NotificationRankingManager notificationRankingManager4 = this.this$0;
        Objects.requireNonNull(notificationRankingManager4);
        boolean isHighPriority2 = notificationRankingManager4.highPriorityProvider.isHighPriority(notificationEntry2);
        if (isRowHeadsUp != isRowHeadsUp2) {
            if (!isRowHeadsUp) {
                return 1;
            }
        } else if (isRowHeadsUp) {
            return this.this$0.headsUpManager.compare(notificationEntry, notificationEntry2);
        } else {
            if (access$isColorizedForegroundService != access$isColorizedForegroundService2) {
                if (!access$isColorizedForegroundService) {
                    return 1;
                }
            } else if (access$isImportantCall == access$isImportantCall2) {
                NotificationRankingManager notificationRankingManager5 = this.this$0;
                Objects.requireNonNull(notificationRankingManager5);
                if (notificationRankingManager5.sectionsFeatureManager.isFilteringEnabled() && peopleNotificationType != peopleNotificationType2) {
                    return this.this$0.peopleNotificationIdentifier.compareTo(peopleNotificationType, peopleNotificationType2);
                }
                if (isImportantMedia != isImportantMedia2) {
                    if (!isImportantMedia) {
                        return 1;
                    }
                } else if (access$isSystemMax == access$isSystemMax2) {
                    boolean z = isHighPriority;
                    if (z != isHighPriority2) {
                        return Intrinsics.compare(z ? 1 : 0, isHighPriority2 ? 1 : 0) * -1;
                    }
                    int i3 = i;
                    int i4 = i2;
                    if (i3 != i4) {
                        return i3 - i4;
                    }
                    int i5 = (statusBarNotification4.getNotification().when > statusBarNotification3.getNotification().when ? 1 : (statusBarNotification4.getNotification().when == statusBarNotification3.getNotification().when ? 0 : -1));
                    if (i5 >= 0) {
                        if (i5 == 0) {
                            return 0;
                        }
                        return 1;
                    }
                } else if (!access$isSystemMax) {
                    return 1;
                }
            } else if (!access$isImportantCall) {
                return 1;
            }
        }
        return -1;
    }
}
