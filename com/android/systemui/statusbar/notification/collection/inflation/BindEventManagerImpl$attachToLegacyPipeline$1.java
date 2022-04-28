package com.android.systemui.statusbar.notification.collection.inflation;

import com.android.systemui.statusbar.notification.NotificationEntryListener;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: BindEventManagerImpl.kt */
public final class BindEventManagerImpl$attachToLegacyPipeline$1 implements NotificationEntryListener {
    public final /* synthetic */ BindEventManagerImpl this$0;

    public BindEventManagerImpl$attachToLegacyPipeline$1(BindEventManagerImpl bindEventManagerImpl) {
        this.this$0 = bindEventManagerImpl;
    }

    public final void onEntryInflated(NotificationEntry notificationEntry) {
        this.this$0.notifyViewBound(notificationEntry);
    }

    public final void onEntryReinflated(NotificationEntry notificationEntry) {
        this.this$0.notifyViewBound(notificationEntry);
    }
}
