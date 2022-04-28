package com.android.systemui.statusbar.notification.collection.render;

import android.view.View;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import java.util.Objects;

/* compiled from: RootNodeController.kt */
public final class RootNodeController implements NodeController {
    public final NotificationListContainer listContainer;
    public final View view;

    public final String getNodeLabel() {
        return "<root>";
    }

    public final void onViewAdded() {
    }

    public final void onViewMoved() {
    }

    public final void onViewRemoved() {
    }

    public final void addChildAt(NodeController nodeController, int i) {
        ExpandableNotificationRow expandableNotificationRow;
        this.listContainer.addContainerViewAt(nodeController.getView(), i);
        Objects.requireNonNull(this.listContainer);
        View view2 = nodeController.getView();
        if (view2 instanceof ExpandableNotificationRow) {
            expandableNotificationRow = (ExpandableNotificationRow) view2;
        } else {
            expandableNotificationRow = null;
        }
        if (expandableNotificationRow != null) {
            expandableNotificationRow.mChangingPosition = false;
        }
    }

    public final View getChildAt(int i) {
        return this.listContainer.getContainerChildAt(i);
    }

    public final int getChildCount() {
        return this.listContainer.getContainerChildCount();
    }

    public final void moveChildTo(NodeController nodeController, int i) {
        this.listContainer.changeViewPosition((ExpandableView) nodeController.getView(), i);
    }

    public final void removeChild(NodeController nodeController, boolean z) {
        ExpandableNotificationRow expandableNotificationRow;
        if (z) {
            this.listContainer.setChildTransferInProgress(true);
            View view2 = nodeController.getView();
            if (view2 instanceof ExpandableNotificationRow) {
                expandableNotificationRow = (ExpandableNotificationRow) view2;
            } else {
                expandableNotificationRow = null;
            }
            if (expandableNotificationRow != null) {
                expandableNotificationRow.mChangingPosition = true;
            }
        }
        this.listContainer.removeContainerView(nodeController.getView());
        if (z) {
            this.listContainer.setChildTransferInProgress(false);
        }
    }

    public RootNodeController(NotificationStackScrollLayoutController.NotificationListContainerImpl notificationListContainerImpl, View view2) {
        this.listContainer = notificationListContainerImpl;
        this.view = view2;
    }

    public final View getView() {
        return this.view;
    }
}
