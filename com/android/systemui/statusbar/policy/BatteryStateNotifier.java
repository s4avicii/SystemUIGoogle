package com.android.systemui.statusbar.policy;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.util.concurrency.DelayableExecutor;

/* compiled from: BatteryStateNotifier.kt */
public final class BatteryStateNotifier implements BatteryController.BatteryStateChangeCallback {
    public final Context context;
    public final BatteryController controller;
    public final DelayableExecutor delayableExecutor;
    public final NotificationManager noMan;
    public boolean stateUnknown;

    public final void onBatteryUnknownStateChanged(boolean z) {
        this.stateUnknown = z;
        if (z) {
            NotificationChannel notificationChannel = new NotificationChannel("battery_status", "Battery status", 3);
            this.noMan.createNotificationChannel(notificationChannel);
            this.noMan.notify("BatteryStateNotifier", 666, new Notification.Builder(this.context, notificationChannel.getId()).setAutoCancel(false).setContentTitle(this.context.getString(C1777R.string.battery_state_unknown_notification_title)).setContentText(this.context.getString(C1777R.string.battery_state_unknown_notification_text)).setSmallIcon(17303574).setContentIntent(PendingIntent.getActivity(this.context, 0, new Intent("android.intent.action.VIEW", Uri.parse(this.context.getString(C1777R.string.config_batteryStateUnknownUrl))), 67108864)).setAutoCancel(true).setOngoing(true).build());
            return;
        }
        this.delayableExecutor.executeDelayed(new BatteryStateNotifierKt$sam$java_lang_Runnable$0(new BatteryStateNotifier$scheduleNotificationCancel$r$1(this)), 14400000);
    }

    public BatteryStateNotifier(BatteryController batteryController, NotificationManager notificationManager, DelayableExecutor delayableExecutor2, Context context2) {
        this.controller = batteryController;
        this.noMan = notificationManager;
        this.delayableExecutor = delayableExecutor2;
        this.context = context2;
    }
}
