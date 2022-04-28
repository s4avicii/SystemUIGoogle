package com.android.systemui.statusbar.phone;

import android.util.ArrayMap;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;
import com.android.systemui.statusbar.notification.row.RowContentBindParams;
import com.android.systemui.statusbar.phone.NotificationGroupAlertTransferHelper;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotificationGroupAlertTransferHelper$$ExternalSyntheticLambda0 implements NotifBindPipeline.BindCallback {
    public final /* synthetic */ NotificationGroupAlertTransferHelper f$0;
    public final /* synthetic */ NotificationEntry f$1;

    public /* synthetic */ NotificationGroupAlertTransferHelper$$ExternalSyntheticLambda0(NotificationGroupAlertTransferHelper notificationGroupAlertTransferHelper, NotificationEntry notificationEntry) {
        this.f$0 = notificationGroupAlertTransferHelper;
        this.f$1 = notificationEntry;
    }

    public final void onBindFinished(NotificationEntry notificationEntry) {
        NotificationGroupAlertTransferHelper notificationGroupAlertTransferHelper = this.f$0;
        NotificationEntry notificationEntry2 = this.f$1;
        Objects.requireNonNull(notificationGroupAlertTransferHelper);
        ArrayMap<String, NotificationGroupAlertTransferHelper.PendingAlertInfo> arrayMap = notificationGroupAlertTransferHelper.mPendingAlerts;
        Objects.requireNonNull(notificationEntry2);
        NotificationGroupAlertTransferHelper.PendingAlertInfo remove = arrayMap.remove(notificationEntry2.mKey);
        if (remove != null) {
            boolean z = false;
            if (!remove.mAbortOnInflation) {
                NotificationEntry notificationEntry3 = remove.mEntry;
                Objects.requireNonNull(notificationEntry3);
                if (notificationEntry3.mSbn.getGroupKey().equals(remove.mOriginalNotification.getGroupKey())) {
                    NotificationEntry notificationEntry4 = remove.mEntry;
                    Objects.requireNonNull(notificationEntry4);
                    if (notificationEntry4.mSbn.getNotification().isGroupSummary() == remove.mOriginalNotification.getNotification().isGroupSummary()) {
                        z = true;
                    }
                }
            }
            if (z) {
                notificationGroupAlertTransferHelper.alertNotificationWhenPossible(notificationEntry2);
                return;
            }
            ((RowContentBindParams) notificationGroupAlertTransferHelper.mRowContentBindStage.getStageParams(notificationEntry2)).markContentViewsFreeable(4);
            notificationGroupAlertTransferHelper.mRowContentBindStage.requestRebind(notificationEntry2, (NotifBindPipeline.BindCallback) null);
        }
    }
}
