package com.android.systemui.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioAttributes;
import android.net.Uri;
import android.provider.Settings;
import com.android.internal.annotations.VisibleForTesting;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.CoreStartable;
import java.util.Arrays;

public class NotificationChannels extends CoreStartable {
    public static void createAll(Context context) {
        int i;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NotificationManager.class);
        NotificationChannel notificationChannel = new NotificationChannel("BAT", context.getString(C1777R.string.notification_channel_battery), 5);
        notificationChannel.setSound(Uri.parse("file://" + Settings.Global.getString(context.getContentResolver(), "low_battery_sound")), new AudioAttributes.Builder().setContentType(4).setUsage(10).build());
        notificationChannel.setBlockable(true);
        NotificationChannel notificationChannel2 = new NotificationChannel("ALR", context.getString(C1777R.string.notification_channel_alerts), 4);
        NotificationChannel notificationChannel3 = new NotificationChannel("GEN", context.getString(C1777R.string.notification_channel_general), 1);
        String string = context.getString(C1777R.string.notification_channel_storage);
        if (context.getPackageManager().hasSystemFeature("android.software.leanback")) {
            i = 3;
        } else {
            i = 2;
        }
        notificationManager.createNotificationChannels(Arrays.asList(new NotificationChannel[]{notificationChannel2, notificationChannel3, new NotificationChannel("DSK", string, i), createScreenshotChannel(context.getString(C1777R.string.notification_channel_screenshot)), notificationChannel, new NotificationChannel("HNT", context.getString(C1777R.string.notification_channel_hints), 3)}));
        if (context.getPackageManager().hasSystemFeature("android.software.leanback")) {
            notificationManager.createNotificationChannel(new NotificationChannel("TVPIP", context.getString(C1777R.string.notification_channel_tv_pip), 5));
        }
    }

    @VisibleForTesting
    public static NotificationChannel createScreenshotChannel(String str) {
        NotificationChannel notificationChannel = new NotificationChannel("SCN_HEADSUP", str, 4);
        notificationChannel.setSound((Uri) null, new AudioAttributes.Builder().setUsage(5).build());
        notificationChannel.setBlockable(true);
        return notificationChannel;
    }

    public final void start() {
        createAll(this.mContext);
    }

    public NotificationChannels(Context context) {
        super(context);
    }
}
