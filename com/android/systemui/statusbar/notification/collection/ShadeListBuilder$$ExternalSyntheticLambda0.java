package com.android.systemui.statusbar.notification.collection;

import java.util.Comparator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ShadeListBuilder$$ExternalSyntheticLambda0 implements Comparator {
    public final /* synthetic */ ShadeListBuilder f$0;

    public /* synthetic */ ShadeListBuilder$$ExternalSyntheticLambda0(ShadeListBuilder shadeListBuilder) {
        this.f$0 = shadeListBuilder;
    }

    public final int compare(Object obj, Object obj2) {
        int i;
        ShadeListBuilder shadeListBuilder = this.f$0;
        ListEntry listEntry = (ListEntry) obj;
        ListEntry listEntry2 = (ListEntry) obj2;
        Objects.requireNonNull(shadeListBuilder);
        int i2 = -1;
        if (shadeListBuilder.canReorder(listEntry)) {
            i = -1;
        } else {
            Objects.requireNonNull(listEntry);
            ListAttachState listAttachState = listEntry.mPreviousAttachState;
            Objects.requireNonNull(listAttachState);
            i = listAttachState.stableIndex;
        }
        if (!shadeListBuilder.canReorder(listEntry2)) {
            Objects.requireNonNull(listEntry2);
            ListAttachState listAttachState2 = listEntry2.mPreviousAttachState;
            Objects.requireNonNull(listAttachState2);
            i2 = listAttachState2.stableIndex;
        }
        int compare = Integer.compare(i, i2);
        if (compare != 0) {
            return compare;
        }
        NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
        Objects.requireNonNull(representativeEntry);
        int rank = representativeEntry.mRanking.getRank();
        NotificationEntry representativeEntry2 = listEntry2.getRepresentativeEntry();
        Objects.requireNonNull(representativeEntry2);
        int rank2 = rank - representativeEntry2.mRanking.getRank();
        if (rank2 != 0) {
            return rank2;
        }
        NotificationEntry representativeEntry3 = listEntry2.getRepresentativeEntry();
        Objects.requireNonNull(representativeEntry3);
        long j = representativeEntry3.mSbn.getNotification().when;
        NotificationEntry representativeEntry4 = listEntry.getRepresentativeEntry();
        Objects.requireNonNull(representativeEntry4);
        return Long.compare(j, representativeEntry4.mSbn.getNotification().when);
    }
}
