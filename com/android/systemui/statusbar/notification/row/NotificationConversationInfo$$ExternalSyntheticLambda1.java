package com.android.systemui.statusbar.notification.row;

import android.app.NotificationChannel;
import android.os.RemoteException;
import android.service.notification.StatusBarNotification;
import android.view.View;
import com.android.systemui.statusbar.notification.row.NotificationConversationInfo;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotificationConversationInfo$$ExternalSyntheticLambda1 implements View.OnClickListener {
    public final /* synthetic */ NotificationConversationInfo f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ NotificationConversationInfo$$ExternalSyntheticLambda1(NotificationConversationInfo notificationConversationInfo, int i) {
        this.f$0 = notificationConversationInfo;
        this.f$1 = i;
    }

    public final void onClick(View view) {
        NotificationConversationInfo notificationConversationInfo = this.f$0;
        int i = this.f$1;
        int i2 = NotificationConversationInfo.$r8$clinit;
        Objects.requireNonNull(notificationConversationInfo);
        NotificationConversationInfo.OnSettingsClickListener onSettingsClickListener = notificationConversationInfo.mOnSettingsClickListener;
        NotificationChannel notificationChannel = notificationConversationInfo.mNotificationChannel;
        NotificationGutsManager$$ExternalSyntheticLambda0 notificationGutsManager$$ExternalSyntheticLambda0 = (NotificationGutsManager$$ExternalSyntheticLambda0) onSettingsClickListener;
        Objects.requireNonNull(notificationGutsManager$$ExternalSyntheticLambda0);
        NotificationGutsManager notificationGutsManager = notificationGutsManager$$ExternalSyntheticLambda0.f$0;
        NotificationGuts notificationGuts = notificationGutsManager$$ExternalSyntheticLambda0.f$1;
        StatusBarNotification statusBarNotification = notificationGutsManager$$ExternalSyntheticLambda0.f$2;
        String str = notificationGutsManager$$ExternalSyntheticLambda0.f$3;
        ExpandableNotificationRow expandableNotificationRow = notificationGutsManager$$ExternalSyntheticLambda0.f$4;
        Objects.requireNonNull(notificationGutsManager);
        notificationGutsManager.mMetricsLogger.action(205);
        notificationGuts.resetFalsingCheck();
        NotificationGutsManager.OnSettingsClickListener onSettingsClickListener2 = notificationGutsManager.mOnSettingsClickListener;
        String key = statusBarNotification.getKey();
        StatusBarNotificationPresenter.C15813 r2 = (StatusBarNotificationPresenter.C15813) onSettingsClickListener2;
        Objects.requireNonNull(r2);
        try {
            StatusBarNotificationPresenter.this.mBarService.onNotificationSettingsViewed(key);
        } catch (RemoteException unused) {
        }
        notificationGutsManager.startAppNotificationSettingsActivity(str, i, notificationChannel, expandableNotificationRow);
    }
}
