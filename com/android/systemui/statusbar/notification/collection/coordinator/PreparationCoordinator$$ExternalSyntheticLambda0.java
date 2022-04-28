package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener;
import com.android.systemui.statusbar.notification.collection.render.NotifViewBarn;
import java.util.List;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PreparationCoordinator$$ExternalSyntheticLambda0 implements OnBeforeFinalizeFilterListener {
    public final /* synthetic */ PreparationCoordinator f$0;

    public /* synthetic */ PreparationCoordinator$$ExternalSyntheticLambda0(PreparationCoordinator preparationCoordinator) {
        this.f$0 = preparationCoordinator;
    }

    public final void onBeforeFinalizeFilter(List list) {
        boolean z;
        PreparationCoordinator preparationCoordinator = this.f$0;
        Objects.requireNonNull(preparationCoordinator);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ListEntry listEntry = (ListEntry) list.get(i);
            if (listEntry instanceof GroupEntry) {
                GroupEntry groupEntry = (GroupEntry) listEntry;
                Objects.requireNonNull(groupEntry);
                NotificationEntry notificationEntry = groupEntry.mSummary;
                List<NotificationEntry> list2 = groupEntry.mUnmodifiableChildren;
                preparationCoordinator.inflateRequiredNotifViews(notificationEntry);
                for (int i2 = 0; i2 < list2.size(); i2++) {
                    NotificationEntry notificationEntry2 = list2.get(i2);
                    boolean z2 = true;
                    if (i2 < preparationCoordinator.mChildBindCutoff) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (z) {
                        preparationCoordinator.inflateRequiredNotifViews(notificationEntry2);
                    } else {
                        if (preparationCoordinator.mInflatingNotifs.contains(notificationEntry2)) {
                            preparationCoordinator.abortInflation(notificationEntry2, "Past last visible group child");
                        }
                        int inflationState = preparationCoordinator.getInflationState(notificationEntry2);
                        if (!(inflationState == 1 || inflationState == 2)) {
                            z2 = false;
                        }
                        if (z2) {
                            NotifViewBarn notifViewBarn = preparationCoordinator.mViewBarn;
                            Objects.requireNonNull(notifViewBarn);
                            notifViewBarn.rowMap.remove(notificationEntry2.getKey());
                            preparationCoordinator.mInflationStates.put(notificationEntry2, 0);
                        }
                    }
                }
            } else {
                preparationCoordinator.inflateRequiredNotifViews((NotificationEntry) listEntry);
            }
        }
    }
}
