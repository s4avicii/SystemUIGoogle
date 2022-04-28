package com.android.systemui.statusbar.notification.row;

import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotificationInfo$$ExternalSyntheticLambda1 implements OnChannelEditorDialogFinishedListener {
    public final /* synthetic */ NotificationInfo f$0;

    public /* synthetic */ NotificationInfo$$ExternalSyntheticLambda1(NotificationInfo notificationInfo) {
        this.f$0 = notificationInfo;
    }

    public final void onChannelEditorDialogFinished() {
        NotificationInfo notificationInfo = this.f$0;
        int i = NotificationInfo.$r8$clinit;
        Objects.requireNonNull(notificationInfo);
        notificationInfo.mPresentingChannelEditorDialog = false;
        notificationInfo.mGutsContainer.closeControls(notificationInfo, false);
    }
}
