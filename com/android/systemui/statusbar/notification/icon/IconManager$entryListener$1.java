package com.android.systemui.statusbar.notification.icon;

import android.util.Log;
import com.android.systemui.statusbar.notification.InflationException;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import java.util.Objects;

/* compiled from: IconManager.kt */
public final class IconManager$entryListener$1 implements NotifCollectionListener {
    public final /* synthetic */ IconManager this$0;

    public IconManager$entryListener$1(IconManager iconManager) {
        this.this$0 = iconManager;
    }

    public final void onEntryCleanUp(NotificationEntry notificationEntry) {
        IconManager$sensitivityListener$1 iconManager$sensitivityListener$1 = this.this$0.sensitivityListener;
        Objects.requireNonNull(notificationEntry);
        notificationEntry.mOnSensitivityChangedListeners.remove(iconManager$sensitivityListener$1);
    }

    public final void onEntryInit(NotificationEntry notificationEntry) {
        IconManager$sensitivityListener$1 iconManager$sensitivityListener$1 = this.this$0.sensitivityListener;
        Objects.requireNonNull(notificationEntry);
        notificationEntry.mOnSensitivityChangedListeners.add(iconManager$sensitivityListener$1);
    }

    public final void onRankingApplied() {
        for (NotificationEntry next : this.this$0.notifCollection.getAllNotifs()) {
            Objects.requireNonNull(this.this$0);
            boolean isImportantConversation = IconManager.isImportantConversation(next);
            IconPack iconPack = next.mIcons;
            Objects.requireNonNull(iconPack);
            if (iconPack.mAreIconsAvailable) {
                IconPack iconPack2 = next.mIcons;
                Objects.requireNonNull(iconPack2);
                if (isImportantConversation != iconPack2.mIsImportantConversation) {
                    IconManager iconManager = this.this$0;
                    Objects.requireNonNull(iconManager);
                    try {
                        iconManager.updateIcons(next);
                    } catch (InflationException e) {
                        Log.e("IconManager", "Unable to update icon", e);
                    }
                }
            }
            IconPack iconPack3 = next.mIcons;
            Objects.requireNonNull(iconPack3);
            iconPack3.mIsImportantConversation = isImportantConversation;
        }
    }
}
