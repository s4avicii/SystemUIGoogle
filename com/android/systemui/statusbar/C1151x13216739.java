package com.android.systemui.statusbar;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

/* renamed from: com.android.systemui.statusbar.LockscreenShadeTransitionController$goToLockedShadeInternal$cancelHandler$1 */
/* compiled from: LockscreenShadeTransitionController.kt */
public final class C1151x13216739 implements Runnable {
    public final /* synthetic */ Runnable $cancelAction;
    public final /* synthetic */ LockscreenShadeTransitionController this$0;

    public C1151x13216739(LockscreenShadeTransitionController lockscreenShadeTransitionController, C1153xbbc01eb0 lockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1) {
        this.this$0 = lockscreenShadeTransitionController;
        this.$cancelAction = lockscreenShadeTransitionController$onDraggedDown$cancelRunnable$1;
    }

    public final void run() {
        LockscreenShadeTransitionController lockscreenShadeTransitionController = this.this$0;
        NotificationEntry notificationEntry = lockscreenShadeTransitionController.draggedDownEntry;
        if (notificationEntry != null) {
            ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
            if (expandableNotificationRow != null) {
                expandableNotificationRow.setUserLocked(false);
            }
            ExpandableNotificationRow expandableNotificationRow2 = notificationEntry.row;
            if (expandableNotificationRow2 != null) {
                expandableNotificationRow2.notifyHeightChanged(false);
            }
            lockscreenShadeTransitionController.draggedDownEntry = null;
        }
        Runnable runnable = this.$cancelAction;
        if (runnable != null) {
            runnable.run();
        }
    }
}
