package com.android.systemui.statusbar.notification.collection.notifcollection;

/* compiled from: NotifEvent.kt */
public abstract class NotifEvent {
    public NotifEvent() {
    }

    public abstract void dispatchToListener(NotifCollectionListener notifCollectionListener);

    public NotifEvent(int i) {
    }
}
