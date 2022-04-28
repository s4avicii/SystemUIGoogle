package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.statusbar.notification.collection.listbuilder.NotifSection;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator;
import java.util.Comparator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ShadeListBuilder$$ExternalSyntheticLambda1 implements Comparator {
    public final /* synthetic */ ShadeListBuilder f$0;

    public /* synthetic */ ShadeListBuilder$$ExternalSyntheticLambda1(ShadeListBuilder shadeListBuilder) {
        this.f$0 = shadeListBuilder;
    }

    public final int compare(Object obj, Object obj2) {
        int i;
        int i2;
        int i3;
        NotifComparator notifComparator;
        int compare;
        ShadeListBuilder shadeListBuilder = this.f$0;
        ListEntry listEntry = (ListEntry) obj;
        ListEntry listEntry2 = (ListEntry) obj2;
        Objects.requireNonNull(shadeListBuilder);
        Objects.requireNonNull(listEntry);
        ListAttachState listAttachState = listEntry.mAttachState;
        Objects.requireNonNull(listAttachState);
        int i4 = -1;
        if (listAttachState.section != null) {
            ListAttachState listAttachState2 = listEntry.mAttachState;
            Objects.requireNonNull(listAttachState2);
            NotifSection notifSection = listAttachState2.section;
            Objects.requireNonNull(notifSection);
            i = notifSection.index;
        } else {
            i = -1;
        }
        Objects.requireNonNull(listEntry2);
        ListAttachState listAttachState3 = listEntry2.mAttachState;
        Objects.requireNonNull(listAttachState3);
        if (listAttachState3.section != null) {
            ListAttachState listAttachState4 = listEntry2.mAttachState;
            Objects.requireNonNull(listAttachState4);
            NotifSection notifSection2 = listAttachState4.section;
            Objects.requireNonNull(notifSection2);
            i2 = notifSection2.index;
        } else {
            i2 = -1;
        }
        int compare2 = Integer.compare(i, i2);
        if (compare2 != 0) {
            return compare2;
        }
        if (shadeListBuilder.canReorder(listEntry)) {
            i3 = -1;
        } else {
            ListAttachState listAttachState5 = listEntry.mPreviousAttachState;
            Objects.requireNonNull(listAttachState5);
            i3 = listAttachState5.stableIndex;
        }
        if (!shadeListBuilder.canReorder(listEntry2)) {
            ListAttachState listAttachState6 = listEntry2.mPreviousAttachState;
            Objects.requireNonNull(listAttachState6);
            i4 = listAttachState6.stableIndex;
        }
        int compare3 = Integer.compare(i3, i4);
        if (compare3 != 0) {
            return compare3;
        }
        NotifSection section = listEntry.getSection();
        if (section == listEntry2.getSection()) {
            if (section != null) {
                notifComparator = section.comparator;
            } else {
                notifComparator = null;
            }
            if (notifComparator != null && (compare = notifComparator.compare(listEntry, listEntry2)) != 0) {
                return compare;
            }
            for (int i5 = 0; i5 < shadeListBuilder.mNotifComparators.size(); i5++) {
                int compare4 = ((NotifComparator) shadeListBuilder.mNotifComparators.get(i5)).compare(listEntry, listEntry2);
                if (compare4 != 0) {
                    return compare4;
                }
            }
            NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
            NotificationEntry representativeEntry2 = listEntry2.getRepresentativeEntry();
            Objects.requireNonNull(representativeEntry);
            int rank = representativeEntry.mRanking.getRank();
            Objects.requireNonNull(representativeEntry2);
            int rank2 = rank - representativeEntry2.mRanking.getRank();
            if (rank2 != 0) {
                return rank2;
            }
            return Long.compare(representativeEntry2.mSbn.getNotification().when, representativeEntry.mSbn.getNotification().when);
        }
        throw new RuntimeException("Entry ordering should only be done within sections");
    }
}
