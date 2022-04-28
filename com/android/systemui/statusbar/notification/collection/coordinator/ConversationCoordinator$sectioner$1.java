package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import java.util.Objects;

/* compiled from: ConversationCoordinator.kt */
public final class ConversationCoordinator$sectioner$1 extends NotifSectioner {
    public final /* synthetic */ NodeController $peopleHeaderController;
    public final /* synthetic */ ConversationCoordinator this$0;

    public final NodeController getHeaderNodeController() {
        return null;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ConversationCoordinator$sectioner$1(ConversationCoordinator conversationCoordinator, NodeController nodeController) {
        super("People", 4);
        this.this$0 = conversationCoordinator;
        this.$peopleHeaderController = nodeController;
    }

    public final NotifComparator getComparator() {
        return new ConversationCoordinator$sectioner$1$getComparator$1(this.this$0);
    }

    public final boolean isInSection(ListEntry listEntry) {
        ConversationCoordinator conversationCoordinator = this.this$0;
        Objects.requireNonNull(conversationCoordinator);
        if (conversationCoordinator.getPeopleType(listEntry) != 0) {
            return true;
        }
        return false;
    }
}
