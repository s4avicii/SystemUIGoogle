package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener;
import com.android.systemui.statusbar.notification.collection.render.NotifStackController;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DataStoreCoordinator.kt */
public final class DataStoreCoordinator$attach$1 implements OnAfterRenderListListener {
    public final /* synthetic */ DataStoreCoordinator this$0;

    public DataStoreCoordinator$attach$1(DataStoreCoordinator dataStoreCoordinator) {
        this.this$0 = dataStoreCoordinator;
    }

    public final void onAfterRenderList(List<ListEntry> list, NotifStackController notifStackController) {
        DataStoreCoordinator dataStoreCoordinator = this.this$0;
        Objects.requireNonNull(dataStoreCoordinator);
        ArrayList arrayList = new ArrayList();
        for (ListEntry listEntry : list) {
            if (listEntry instanceof NotificationEntry) {
                arrayList.add(listEntry);
            } else if (listEntry instanceof GroupEntry) {
                GroupEntry groupEntry = (GroupEntry) listEntry;
                Objects.requireNonNull(groupEntry);
                NotificationEntry notificationEntry = groupEntry.mSummary;
                if (notificationEntry != null) {
                    arrayList.add(notificationEntry);
                    arrayList.addAll(groupEntry.mUnmodifiableChildren);
                } else {
                    throw new IllegalStateException(Intrinsics.stringPlus("No Summary: ", groupEntry).toString());
                }
            } else {
                throw new IllegalStateException(Intrinsics.stringPlus("Unexpected entry ", listEntry).toString());
            }
        }
        dataStoreCoordinator.notifLiveDataStoreImpl.setActiveNotifList(arrayList);
    }
}
