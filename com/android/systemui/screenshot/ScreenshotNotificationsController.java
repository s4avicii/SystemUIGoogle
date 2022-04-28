package com.android.systemui.screenshot;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.SystemUIApplication;

public final class ScreenshotNotificationsController {
    public final Context mContext;
    public final NotificationManager mNotificationManager;

    public final void notifyScreenshotError(int i) {
        Resources resources = this.mContext.getResources();
        String string = resources.getString(i);
        Notification.Builder color = new Notification.Builder(this.mContext, "ALR").setTicker(resources.getString(C1777R.string.screenshot_failed_title)).setContentTitle(resources.getString(C1777R.string.screenshot_failed_title)).setContentText(string).setSmallIcon(C1777R.C1778drawable.stat_notify_image_error).setWhen(System.currentTimeMillis()).setVisibility(1).setCategory("err").setAutoCancel(true).setColor(this.mContext.getColor(17170460));
        Intent createAdminSupportIntent = ((DevicePolicyManager) this.mContext.getSystemService("device_policy")).createAdminSupportIntent("policy_disable_screen_capture");
        if (createAdminSupportIntent != null) {
            color.setContentIntent(PendingIntent.getActivityAsUser(this.mContext, 0, createAdminSupportIntent, 67108864, (Bundle) null, UserHandle.CURRENT));
        }
        SystemUIApplication.overrideNotificationAppName(this.mContext, color, true);
        this.mNotificationManager.notify(1, new Notification.BigTextStyle(color).bigText(string).build());
    }

    public ScreenshotNotificationsController(Context context, WindowManager windowManager) {
        this.mContext = context;
        context.getResources();
        this.mNotificationManager = (NotificationManager) context.getSystemService("notification");
        windowManager.getDefaultDisplay().getRealMetrics(new DisplayMetrics());
    }
}
