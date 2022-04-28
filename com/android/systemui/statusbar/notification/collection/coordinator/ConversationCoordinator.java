package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier;

/* compiled from: ConversationCoordinator.kt */
public final class ConversationCoordinator implements Coordinator {
    public final ConversationCoordinator$notificationPromoter$1 notificationPromoter = new ConversationCoordinator$notificationPromoter$1();
    public final PeopleNotificationIdentifier peopleNotificationIdentifier;
    public final ConversationCoordinator$sectioner$1 sectioner;

    public final void attach(NotifPipeline notifPipeline) {
        notifPipeline.addPromoter(this.notificationPromoter);
    }

    public ConversationCoordinator(PeopleNotificationIdentifier peopleNotificationIdentifier2, NodeController nodeController) {
        this.peopleNotificationIdentifier = peopleNotificationIdentifier2;
        this.sectioner = new ConversationCoordinator$sectioner$1(this, nodeController);
    }

    public final int getPeopleType(ListEntry listEntry) {
        NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
        if (representativeEntry == null) {
            return 0;
        }
        return this.peopleNotificationIdentifier.getPeopleNotificationType(representativeEntry);
    }
}
