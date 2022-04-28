package com.android.systemui.statusbar.notification.row;

import android.view.View;

/* renamed from: com.android.systemui.statusbar.notification.row.ExpandableNotificationRowDragController$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1314x70846d1a implements View.OnDragListener {
    public final /* synthetic */ ExpandableNotificationRowDragController f$0;

    public /* synthetic */ C1314x70846d1a(ExpandableNotificationRowDragController expandableNotificationRowDragController) {
        this.f$0 = expandableNotificationRowDragController;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0086, code lost:
        if (r9 != false) goto L_0x0088;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onDrag(android.view.View r8, android.view.DragEvent r9) {
        /*
            r7 = this;
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRowDragController r7 = r7.f$0
            java.util.Objects.requireNonNull(r7)
            int r0 = r9.getAction()
            r1 = 1
            r2 = 0
            if (r0 == r1) goto L_0x0095
            r7 = 4
            if (r0 == r7) goto L_0x0013
            r1 = r2
            goto L_0x00b3
        L_0x0013:
            boolean r7 = r9.getResult()
            if (r7 == 0) goto L_0x00b3
            boolean r7 = r8 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
            if (r7 == 0) goto L_0x00b3
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r8 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r8
            java.util.Objects.requireNonNull(r8)
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow$OnDragSuccessListener r7 = r8.mOnDragSuccessListener
            if (r7 == 0) goto L_0x00b3
            com.android.systemui.statusbar.notification.collection.NotificationEntry r8 = r8.mEntry
            com.android.systemui.statusbar.notification.NotificationClicker$1 r7 = (com.android.systemui.statusbar.notification.NotificationClicker.C12331) r7
            com.android.systemui.statusbar.notification.NotificationClicker r7 = com.android.systemui.statusbar.notification.NotificationClicker.this
            com.android.systemui.statusbar.notification.NotificationActivityStarter r7 = r7.mNotificationActivityStarter
            com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter r7 = (com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter) r7
            java.util.Objects.requireNonNull(r7)
            com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider r9 = r7.mVisibilityProvider
            com.android.internal.statusbar.NotificationVisibility r9 = r9.obtain(r8, r1)
            java.util.Objects.requireNonNull(r8)
            android.service.notification.StatusBarNotification r0 = r8.mSbn
            android.app.Notification r0 = r0.getNotification()
            int r0 = r0.flags
            r3 = r0 & 16
            r4 = 16
            if (r3 == r4) goto L_0x004c
        L_0x004a:
            r0 = r2
            goto L_0x0052
        L_0x004c:
            r0 = r0 & 64
            if (r0 == 0) goto L_0x0051
            goto L_0x004a
        L_0x0051:
            r0 = r1
        L_0x0052:
            if (r0 == 0) goto L_0x005b
            com.android.systemui.statusbar.notification.row.OnUserInteractionCallback r3 = r7.mOnUserInteractionCallback
            com.android.systemui.statusbar.notification.collection.NotificationEntry r3 = r3.getGroupSummaryToDismiss(r8)
            goto L_0x005c
        L_0x005b:
            r3 = 0
        L_0x005c:
            java.lang.String r4 = r8.mKey
            com.android.systemui.statusbar.NotificationClickNotifier r5 = r7.mClickNotifier
            java.util.Objects.requireNonNull(r5)
            com.android.internal.statusbar.IStatusBarService r6 = r5.barService     // Catch:{ RemoteException -> 0x0068 }
            r6.onNotificationClick(r4, r9)     // Catch:{ RemoteException -> 0x0068 }
        L_0x0068:
            java.util.concurrent.Executor r9 = r5.mainExecutor
            com.android.systemui.statusbar.NotificationClickNotifier$onNotificationClick$1 r6 = new com.android.systemui.statusbar.NotificationClickNotifier$onNotificationClick$1
            r6.<init>(r5, r4)
            r9.execute(r6)
            if (r0 != 0) goto L_0x0088
            com.android.systemui.statusbar.NotificationRemoteInputManager r9 = r7.mRemoteInputManager
            java.util.Objects.requireNonNull(r9)
            com.android.systemui.statusbar.NotificationRemoteInputManager$RemoteInputListener r9 = r9.mRemoteInputListener
            if (r9 == 0) goto L_0x0085
            boolean r9 = r9.isNotificationKeptForRemoteInputHistory(r4)
            if (r9 == 0) goto L_0x0085
            r9 = r1
            goto L_0x0086
        L_0x0085:
            r9 = r2
        L_0x0086:
            if (r9 == 0) goto L_0x0092
        L_0x0088:
            android.os.Handler r9 = r7.mMainThreadHandler
            com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda2 r0 = new com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda2
            r0.<init>(r7, r8, r3)
            r9.post(r0)
        L_0x0092:
            r7.mIsCollapsingToShowActivityOverLockscreen = r2
            goto L_0x00b3
        L_0x0095:
            r8.performHapticFeedback(r2)
            boolean r9 = r8 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
            if (r9 == 0) goto L_0x00b3
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r8 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r8
            boolean r8 = r8.mIsPinned
            if (r8 == 0) goto L_0x00a8
            com.android.systemui.statusbar.policy.HeadsUpManager r7 = r7.mHeadsUpManager
            r7.releaseAllImmediately()
            goto L_0x00b3
        L_0x00a8:
            java.lang.Class<com.android.systemui.statusbar.phone.ShadeController> r7 = com.android.systemui.statusbar.phone.ShadeController.class
            java.lang.Object r7 = com.android.systemui.Dependency.get(r7)
            com.android.systemui.statusbar.phone.ShadeController r7 = (com.android.systemui.statusbar.phone.ShadeController) r7
            r7.animateCollapsePanels$1(r2)
        L_0x00b3:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.row.C1314x70846d1a.onDrag(android.view.View, android.view.DragEvent):boolean");
    }
}
