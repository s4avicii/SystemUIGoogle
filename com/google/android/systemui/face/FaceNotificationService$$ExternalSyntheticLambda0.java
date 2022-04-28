package com.google.android.systemui.face;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.util.Log;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class FaceNotificationService$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ FaceNotificationService f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ String f$2;

    public /* synthetic */ FaceNotificationService$$ExternalSyntheticLambda0(FaceNotificationService faceNotificationService, String str, String str2) {
        this.f$0 = faceNotificationService;
        this.f$1 = str;
        this.f$2 = str2;
    }

    public final void run() {
        FaceNotificationService faceNotificationService = this.f$0;
        String str = this.f$1;
        String str2 = this.f$2;
        Objects.requireNonNull(faceNotificationService);
        faceNotificationService.mNotificationQueued = false;
        NotificationManager notificationManager = (NotificationManager) faceNotificationService.mContext.getSystemService(NotificationManager.class);
        if (notificationManager == null) {
            Log.e("FaceNotificationService", "Failed to show notification face_action_show_reenroll_dialog. Notification manager is null!");
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("face_action_show_reenroll_dialog");
        intentFilter.addAction("face_action_notification_dismissed");
        faceNotificationService.mContext.registerReceiver(faceNotificationService.mBroadcastReceiver, intentFilter, 2);
        PendingIntent broadcastAsUser = PendingIntent.getBroadcastAsUser(faceNotificationService.mContext, 0, new Intent("face_action_show_reenroll_dialog"), 0, UserHandle.CURRENT);
        PendingIntent broadcastAsUser2 = PendingIntent.getBroadcastAsUser(faceNotificationService.mContext, 0, new Intent("face_action_notification_dismissed"), 0, UserHandle.CURRENT);
        String string = faceNotificationService.mContext.getString(C1777R.string.face_notification_name);
        NotificationChannel notificationChannel = new NotificationChannel("FaceHiPriNotificationChannel", string, 4);
        Notification build = new Notification.Builder(faceNotificationService.mContext, "FaceHiPriNotificationChannel").setCategory("sys").setSmallIcon(17302484).setContentTitle(str).setContentText(str2).setSubText(string).setContentIntent(broadcastAsUser).setDeleteIntent(broadcastAsUser2).setAutoCancel(true).setLocalOnly(true).setOnlyAlertOnce(true).setVisibility(-1).build();
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notifyAsUser("FaceNotificationService", 1, build, UserHandle.CURRENT);
    }
}
