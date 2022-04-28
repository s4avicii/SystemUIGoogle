package com.android.systemui.statusbar.notification.row;

import android.util.ArrayMap;
import android.util.SparseArray;
import android.widget.RemoteViews;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;

public final class NotifRemoteViewCacheImpl implements NotifRemoteViewCache {
    public final C13181 mCollectionListener;
    public final ArrayMap mNotifCachedContentViews = new ArrayMap();

    public final void clearCache(NotificationEntry notificationEntry) {
        SparseArray sparseArray = (SparseArray) this.mNotifCachedContentViews.get(notificationEntry);
        if (sparseArray != null) {
            sparseArray.clear();
        }
    }

    public final RemoteViews getCachedView(NotificationEntry notificationEntry, int i) {
        SparseArray sparseArray = (SparseArray) this.mNotifCachedContentViews.get(notificationEntry);
        if (sparseArray == null) {
            return null;
        }
        return (RemoteViews) sparseArray.get(i);
    }

    public final void putCachedView(NotificationEntry notificationEntry, int i, RemoteViews remoteViews) {
        SparseArray sparseArray = (SparseArray) this.mNotifCachedContentViews.get(notificationEntry);
        if (sparseArray != null) {
            sparseArray.put(i, remoteViews);
        }
    }

    public final void removeCachedView(NotificationEntry notificationEntry, int i) {
        SparseArray sparseArray = (SparseArray) this.mNotifCachedContentViews.get(notificationEntry);
        if (sparseArray != null) {
            sparseArray.remove(i);
        }
    }

    public NotifRemoteViewCacheImpl(CommonNotifCollection commonNotifCollection) {
        C13181 r0 = new NotifCollectionListener() {
            public final void onEntryCleanUp(NotificationEntry notificationEntry) {
                NotifRemoteViewCacheImpl.this.mNotifCachedContentViews.remove(notificationEntry);
            }

            public final void onEntryInit(NotificationEntry notificationEntry) {
                NotifRemoteViewCacheImpl.this.mNotifCachedContentViews.put(notificationEntry, new SparseArray());
            }
        };
        this.mCollectionListener = r0;
        commonNotifCollection.addCollectionListener(r0);
    }

    public final boolean hasCachedView(NotificationEntry notificationEntry, int i) {
        if (getCachedView(notificationEntry, i) != null) {
            return true;
        }
        return false;
    }
}
