package com.android.systemui.statusbar.notification.row;

import android.app.NotificationChannel;
import androidx.fragment.R$styleable;
import java.util.Comparator;

/* renamed from: com.android.systemui.statusbar.notification.row.ChannelEditorDialogController$getDisplayableChannels$$inlined$compareBy$1 */
/* compiled from: Comparisons.kt */
public final class C1306x78bbb58a<T> implements Comparator {
    public final int compare(T t, T t2) {
        String str;
        NotificationChannel notificationChannel = (NotificationChannel) t;
        CharSequence name = notificationChannel.getName();
        String str2 = null;
        if (name == null) {
            str = null;
        } else {
            str = name.toString();
        }
        if (str == null) {
            str = notificationChannel.getId();
        }
        NotificationChannel notificationChannel2 = (NotificationChannel) t2;
        CharSequence name2 = notificationChannel2.getName();
        if (name2 != null) {
            str2 = name2.toString();
        }
        if (str2 == null) {
            str2 = notificationChannel2.getId();
        }
        return R$styleable.compareValues(str, str2);
    }
}
