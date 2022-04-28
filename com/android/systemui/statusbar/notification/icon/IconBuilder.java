package com.android.systemui.statusbar.notification.icon;

import android.content.Context;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: IconBuilder.kt */
public final class IconBuilder {
    public final Context context;

    public final StatusBarIconView createIconView(NotificationEntry notificationEntry) {
        Context context2 = this.context;
        return new StatusBarIconView(context2, notificationEntry.mSbn.getPackageName() + "/0x" + Integer.toHexString(notificationEntry.mSbn.getId()), notificationEntry.mSbn);
    }

    public IconBuilder(Context context2) {
        this.context = context2;
    }
}
