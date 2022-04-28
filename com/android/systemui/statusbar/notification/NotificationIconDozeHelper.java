package com.android.systemui.statusbar.notification;

import android.content.Context;
import com.android.p012wm.shell.C1777R;

public final class NotificationIconDozeHelper extends NotificationDozeHelper {
    public NotificationIconDozeHelper(Context context) {
        context.getResources().getInteger(C1777R.integer.doze_small_icon_alpha);
    }
}
