package com.android.systemui.statusbar.notification.collection.notifcollection;

/* compiled from: NotifEvent.kt */
public final class RankingAppliedEvent extends NotifEvent {
    public RankingAppliedEvent() {
        super(0);
    }

    public final void dispatchToListener(NotifCollectionListener notifCollectionListener) {
        notifCollectionListener.onRankingApplied();
    }
}
