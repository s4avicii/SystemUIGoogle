package com.android.systemui.statusbar.notification.collection.coordinator;

import android.util.ArraySet;
import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda2;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;
import java.util.Objects;

/* compiled from: GutsCoordinator.kt */
public final class GutsCoordinator$mLifetimeExtender$1 implements NotifLifetimeExtender {
    public final /* synthetic */ GutsCoordinator this$0;

    public final String getName() {
        return "GutsCoordinator";
    }

    public GutsCoordinator$mLifetimeExtender$1(GutsCoordinator gutsCoordinator) {
        this.this$0 = gutsCoordinator;
    }

    public final void cancelLifetimeExtension(NotificationEntry notificationEntry) {
        this.this$0.notifsExtendingLifetime.remove(notificationEntry.mKey);
    }

    public final boolean maybeExtendLifetime(NotificationEntry notificationEntry, int i) {
        GutsCoordinator gutsCoordinator = this.this$0;
        Objects.requireNonNull(gutsCoordinator);
        ArraySet<String> arraySet = gutsCoordinator.notifsWithOpenGuts;
        Objects.requireNonNull(notificationEntry);
        boolean contains = arraySet.contains(notificationEntry.mKey);
        if (contains) {
            this.this$0.notifsExtendingLifetime.add(notificationEntry.mKey);
        }
        return contains;
    }

    public final void setCallback(NotifCollection$$ExternalSyntheticLambda2 notifCollection$$ExternalSyntheticLambda2) {
        this.this$0.onEndLifetimeExtensionCallback = notifCollection$$ExternalSyntheticLambda2;
    }
}
