package com.android.systemui.statusbar.notification.collection.coordinator;

import android.os.Trace;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener;
import com.android.systemui.statusbar.notification.collection.render.NotifStackController;
import com.android.systemui.statusbar.notification.collection.render.NotifStats;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import java.util.List;
import java.util.Objects;

/* compiled from: StackCoordinator.kt */
public /* synthetic */ class StackCoordinator$attach$1 implements OnAfterRenderListListener {
    public final /* synthetic */ StackCoordinator $tmp0;

    public StackCoordinator$attach$1(StackCoordinator stackCoordinator) {
        this.$tmp0 = stackCoordinator;
    }

    public final void onAfterRenderList(List<? extends ListEntry> list, NotifStackController notifStackController) {
        StackCoordinator stackCoordinator = this.$tmp0;
        Objects.requireNonNull(stackCoordinator);
        Trace.beginSection("StackCoordinator.onAfterRenderList");
        try {
            NotifStats calculateNotifStats = StackCoordinator.calculateNotifStats(list);
            NotificationStackScrollLayoutController.NotifStackControllerImpl notifStackControllerImpl = (NotificationStackScrollLayoutController.NotifStackControllerImpl) notifStackController;
            Objects.requireNonNull(notifStackControllerImpl);
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
            notificationStackScrollLayoutController.mNotifStats = calculateNotifStats;
            notificationStackScrollLayoutController.updateFooter();
            NotificationStackScrollLayoutController.this.updateShowEmptyShadeView();
            stackCoordinator.notificationIconAreaController.updateNotificationIcons(list);
        } finally {
            Trace.endSection();
        }
    }
}
