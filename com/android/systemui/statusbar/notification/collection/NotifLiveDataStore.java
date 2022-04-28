package com.android.systemui.statusbar.notification.collection;

/* compiled from: NotifLiveDataStore.kt */
public interface NotifLiveDataStore {
    NotifLiveDataImpl getActiveNotifCount();

    NotifLiveDataImpl getActiveNotifList();

    NotifLiveDataImpl getHasActiveNotifs();
}
