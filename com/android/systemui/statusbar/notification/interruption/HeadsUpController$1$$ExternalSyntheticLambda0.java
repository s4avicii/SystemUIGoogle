package com.android.systemui.statusbar.notification.interruption;

import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class HeadsUpController$1$$ExternalSyntheticLambda0 implements NotifBindPipeline.BindCallback {
    public final /* synthetic */ HeadsUpController f$0;

    public /* synthetic */ HeadsUpController$1$$ExternalSyntheticLambda0(HeadsUpController headsUpController) {
        this.f$0 = headsUpController;
    }

    public final void onBindFinished(NotificationEntry notificationEntry) {
        HeadsUpController headsUpController = this.f$0;
        Objects.requireNonNull(headsUpController);
        headsUpController.mHeadsUpManager.showNotification(notificationEntry);
        if (!headsUpController.mStatusBarStateController.isDozing()) {
            StatusBarNotification statusBarNotification = notificationEntry.mSbn;
            try {
                headsUpController.mNotificationListener.setNotificationsShown(new String[]{statusBarNotification.getKey()});
            } catch (RuntimeException e) {
                Log.d("HeadsUpBindController", "failed setNotificationsShown: ", e);
            }
        }
    }
}
