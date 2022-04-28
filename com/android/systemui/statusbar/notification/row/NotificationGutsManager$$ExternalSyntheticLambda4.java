package com.android.systemui.statusbar.notification.row;

import android.app.NotificationChannel;
import android.os.RemoteException;
import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.notification.row.NotificationInfo;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotificationGutsManager$$ExternalSyntheticLambda4 implements NotificationInfo.OnSettingsClickListener {
    public final /* synthetic */ NotificationGutsManager f$0;
    public final /* synthetic */ NotificationGuts f$1;
    public final /* synthetic */ StatusBarNotification f$2;
    public final /* synthetic */ String f$3;
    public final /* synthetic */ ExpandableNotificationRow f$4;

    public /* synthetic */ NotificationGutsManager$$ExternalSyntheticLambda4(NotificationGutsManager notificationGutsManager, NotificationGuts notificationGuts, StatusBarNotification statusBarNotification, String str, ExpandableNotificationRow expandableNotificationRow) {
        this.f$0 = notificationGutsManager;
        this.f$1 = notificationGuts;
        this.f$2 = statusBarNotification;
        this.f$3 = str;
        this.f$4 = expandableNotificationRow;
    }

    public final void onClick(NotificationChannel notificationChannel, int i) {
        NotificationGutsManager notificationGutsManager = this.f$0;
        NotificationGuts notificationGuts = this.f$1;
        StatusBarNotification statusBarNotification = this.f$2;
        String str = this.f$3;
        ExpandableNotificationRow expandableNotificationRow = this.f$4;
        Objects.requireNonNull(notificationGutsManager);
        notificationGutsManager.mMetricsLogger.action(205);
        notificationGuts.resetFalsingCheck();
        NotificationGutsManager.OnSettingsClickListener onSettingsClickListener = notificationGutsManager.mOnSettingsClickListener;
        String key = statusBarNotification.getKey();
        StatusBarNotificationPresenter.C15813 r1 = (StatusBarNotificationPresenter.C15813) onSettingsClickListener;
        Objects.requireNonNull(r1);
        try {
            StatusBarNotificationPresenter.this.mBarService.onNotificationSettingsViewed(key);
        } catch (RemoteException unused) {
        }
        notificationGutsManager.startAppNotificationSettingsActivity(str, i, notificationChannel, expandableNotificationRow);
    }
}
