package com.android.systemui.statusbar.notification.collection;

import android.service.notification.StatusBarNotification;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotifCollection$$ExternalSyntheticLambda0 {
    public final /* synthetic */ NotifCollection f$0;
    public final /* synthetic */ String f$1 = "RemoteInputCoordinator";

    public /* synthetic */ NotifCollection$$ExternalSyntheticLambda0(NotifCollection notifCollection) {
        this.f$0 = notifCollection;
    }

    public final void onInternalNotificationUpdate(StatusBarNotification statusBarNotification, String str) {
        NotifCollection notifCollection = this.f$0;
        String str2 = this.f$1;
        Objects.requireNonNull(notifCollection);
        notifCollection.mMainHandler.post(new NotifCollection$$ExternalSyntheticLambda3(notifCollection, statusBarNotification, str2, str));
    }
}
