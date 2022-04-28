package com.android.systemui.statusbar.notification.icon;

import android.util.Log;
import com.android.systemui.statusbar.notification.InflationException;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Objects;

/* compiled from: IconManager.kt */
public final class IconManager$sensitivityListener$1 implements NotificationEntry.OnSensitivityChangedListener {
    public final /* synthetic */ IconManager this$0;

    public IconManager$sensitivityListener$1(IconManager iconManager) {
        this.this$0 = iconManager;
    }

    public final void onSensitivityChanged(NotificationEntry notificationEntry) {
        IconManager iconManager = this.this$0;
        Objects.requireNonNull(iconManager);
        try {
            iconManager.updateIcons(notificationEntry);
        } catch (InflationException e) {
            Log.e("IconManager", "Unable to update icon", e);
        }
    }
}
