package com.android.systemui.screenshot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.WindowManager;
import com.android.p012wm.shell.C1777R;

public class ScreenshotServiceErrorReceiver extends BroadcastReceiver {
    public final void onReceive(Context context, Intent intent) {
        new ScreenshotNotificationsController(context, (WindowManager) context.getSystemService("window")).notifyScreenshotError(C1777R.string.screenshot_failed_to_save_unknown_text);
    }
}
