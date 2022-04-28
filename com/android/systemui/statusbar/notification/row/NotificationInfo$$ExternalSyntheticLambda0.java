package com.android.systemui.statusbar.notification.row;

import android.app.NotificationChannel;
import android.view.View;
import com.android.systemui.statusbar.notification.row.NotificationInfo;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotificationInfo$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ NotificationInfo f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ NotificationInfo$$ExternalSyntheticLambda0(NotificationInfo notificationInfo, int i) {
        this.f$0 = notificationInfo;
        this.f$1 = i;
    }

    public final void onClick(View view) {
        NotificationChannel notificationChannel;
        NotificationInfo notificationInfo = this.f$0;
        int i = this.f$1;
        int i2 = NotificationInfo.$r8$clinit;
        Objects.requireNonNull(notificationInfo);
        NotificationInfo.OnSettingsClickListener onSettingsClickListener = notificationInfo.mOnSettingsClickListener;
        if (notificationInfo.mNumUniqueChannelsInRow > 1) {
            notificationChannel = null;
        } else {
            notificationChannel = notificationInfo.mSingleNotificationChannel;
        }
        onSettingsClickListener.onClick(notificationChannel, i);
    }
}
