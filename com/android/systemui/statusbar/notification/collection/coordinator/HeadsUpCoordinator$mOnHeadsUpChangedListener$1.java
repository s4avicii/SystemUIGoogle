package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda2;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import java.util.Objects;

/* compiled from: HeadsUpCoordinator.kt */
public final class HeadsUpCoordinator$mOnHeadsUpChangedListener$1 implements OnHeadsUpChangedListener {
    public final /* synthetic */ HeadsUpCoordinator this$0;

    public HeadsUpCoordinator$mOnHeadsUpChangedListener$1(HeadsUpCoordinator headsUpCoordinator) {
        this.this$0 = headsUpCoordinator;
    }

    public final void onHeadsUpStateChanged(NotificationEntry notificationEntry, boolean z) {
        NotifLifetimeExtender.OnEndLifetimeExtensionCallback onEndLifetimeExtensionCallback;
        if (!z) {
            this.this$0.mHeadsUpViewBinder.unbindHeadsUpView(notificationEntry);
            HeadsUpCoordinator headsUpCoordinator = this.this$0;
            Objects.requireNonNull(headsUpCoordinator);
            if (headsUpCoordinator.mNotifsExtendingLifetime.remove(notificationEntry) && (onEndLifetimeExtensionCallback = headsUpCoordinator.mEndLifetimeExtension) != null) {
                ((NotifCollection$$ExternalSyntheticLambda2) onEndLifetimeExtensionCallback).onEndLifetimeExtension(headsUpCoordinator.mLifetimeExtender, notificationEntry);
            }
        }
    }
}
