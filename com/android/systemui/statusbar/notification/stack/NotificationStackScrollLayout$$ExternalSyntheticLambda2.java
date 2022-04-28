package com.android.systemui.statusbar.notification.stack;

import java.util.ArrayList;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotificationStackScrollLayout$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ NotificationStackScrollLayout f$0;
    public final /* synthetic */ ArrayList f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ NotificationStackScrollLayout$$ExternalSyntheticLambda2(NotificationStackScrollLayout notificationStackScrollLayout, ArrayList arrayList, int i) {
        this.f$0 = notificationStackScrollLayout;
        this.f$1 = arrayList;
        this.f$2 = i;
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0096  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0098  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x009b  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00b3 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r9 = this;
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r0 = r9.f$0
            java.util.ArrayList r1 = r9.f$1
            int r9 = r9.f$2
            boolean r2 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.SPEW
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$ClearAllAnimationListener r0 = r0.mClearAllAnimationListener
            if (r0 == 0) goto L_0x0136
            com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda3 r0 = (com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda3) r0
            java.lang.Object r0 = r0.f$0
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r0 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController) r0
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.statusbar.notification.NotifPipelineFlags r2 = r0.mNotifPipelineFlags
            boolean r2 = r2.isNewPipelineEnabled()
            r3 = 3
            r4 = 1
            if (r2 == 0) goto L_0x00f2
            if (r9 != 0) goto L_0x00be
            com.android.systemui.statusbar.notification.collection.NotifCollection r9 = r0.mNotifCollection
            com.android.systemui.statusbar.NotificationLockscreenUserManager r0 = r0.mLockscreenUserManager
            int r0 = r0.getCurrentUserId()
            java.util.Objects.requireNonNull(r9)
            com.android.systemui.util.Assert.isMainThread()
            r9.checkForReentrantCall()
            com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger r1 = r9.mLogger
            r1.logDismissAll(r0)
            com.android.internal.statusbar.IStatusBarService r1 = r9.mStatusBarService     // Catch:{ RemoteException -> 0x0040 }
            r1.onClearAllNotifications(r0)     // Catch:{ RemoteException -> 0x0040 }
            goto L_0x0046
        L_0x0040:
            r1 = move-exception
            com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger r2 = r9.mLogger
            r2.logRemoteExceptionOnClearAllNotifications(r1)
        L_0x0046:
            java.util.ArrayList r1 = new java.util.ArrayList
            com.android.systemui.util.Assert.isMainThread()
            java.util.Collection<com.android.systemui.statusbar.notification.collection.NotificationEntry> r2 = r9.mReadOnlyNotificationSet
            r1.<init>(r2)
            int r2 = r1.size()
            int r2 = r2 - r4
        L_0x0055:
            if (r2 < 0) goto L_0x00b6
            java.lang.Object r3 = r1.get(r2)
            com.android.systemui.statusbar.notification.collection.NotificationEntry r3 = (com.android.systemui.statusbar.notification.collection.NotificationEntry) r3
            r5 = -1
            r6 = 0
            if (r0 == r5) goto L_0x007f
            java.util.Objects.requireNonNull(r3)
            android.service.notification.StatusBarNotification r7 = r3.mSbn
            android.os.UserHandle r7 = r7.getUser()
            int r7 = r7.getIdentifier()
            if (r7 == r5) goto L_0x007f
            android.service.notification.StatusBarNotification r5 = r3.mSbn
            android.os.UserHandle r5 = r5.getUser()
            int r5 = r5.getIdentifier()
            if (r5 != r0) goto L_0x007d
            goto L_0x007f
        L_0x007d:
            r5 = r6
            goto L_0x0080
        L_0x007f:
            r5 = r4
        L_0x0080:
            if (r5 == 0) goto L_0x0098
            boolean r5 = r3.isClearable()
            if (r5 == 0) goto L_0x0098
            r5 = 4096(0x1000, float:5.74E-42)
            boolean r5 = com.android.systemui.statusbar.notification.collection.NotifCollection.hasFlag(r3, r5)
            if (r5 != 0) goto L_0x0098
            com.android.systemui.statusbar.notification.collection.NotificationEntry$DismissState r5 = r3.mDismissState
            com.android.systemui.statusbar.notification.collection.NotificationEntry$DismissState r7 = com.android.systemui.statusbar.notification.collection.NotificationEntry.DismissState.DISMISSED
            if (r5 == r7) goto L_0x0098
            r5 = r4
            goto L_0x0099
        L_0x0098:
            r5 = r6
        L_0x0099:
            if (r5 != 0) goto L_0x00b3
            r9.updateDismissInterceptors(r3)
            java.util.ArrayList r5 = r3.mDismissInterceptors
            int r5 = r5.size()
            if (r5 <= 0) goto L_0x00a7
            r6 = r4
        L_0x00a7:
            if (r6 == 0) goto L_0x00b0
            com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger r5 = r9.mLogger
            java.lang.String r3 = r3.mKey
            r5.logNotifClearAllDismissalIntercepted(r3)
        L_0x00b0:
            r1.remove(r2)
        L_0x00b3:
            int r2 = r2 + -1
            goto L_0x0055
        L_0x00b6:
            r9.locallyDismissNotifications(r1)
            r9.dispatchEventsAndRebuildList()
            goto L_0x0136
        L_0x00be:
            java.util.ArrayList r9 = new java.util.ArrayList
            r9.<init>()
            java.util.Iterator r1 = r1.iterator()
        L_0x00c7:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x00ec
            java.lang.Object r2 = r1.next()
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r2 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r2
            java.util.Objects.requireNonNull(r2)
            com.android.systemui.statusbar.notification.collection.NotificationEntry r2 = r2.mEntry
            android.util.Pair r5 = new android.util.Pair
            com.android.systemui.statusbar.notification.collection.notifcollection.DismissedByUserStats r6 = new com.android.systemui.statusbar.notification.collection.notifcollection.DismissedByUserStats
            com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider r7 = r0.mVisibilityProvider
            com.android.internal.statusbar.NotificationVisibility r7 = r7.obtain(r2, r4)
            r6.<init>(r3, r7)
            r5.<init>(r2, r6)
            r9.add(r5)
            goto L_0x00c7
        L_0x00ec:
            com.android.systemui.statusbar.notification.collection.NotifCollection r0 = r0.mNotifCollection
            r0.dismissNotifications(r9)
            goto L_0x0136
        L_0x00f2:
            java.util.Iterator r1 = r1.iterator()
        L_0x00f6:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x0129
            java.lang.Object r2 = r1.next()
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r2 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r2
            boolean r5 = com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.canChildBeCleared(r2)
            if (r5 == 0) goto L_0x0125
            com.android.systemui.statusbar.notification.NotificationEntryManager r5 = r0.mNotificationEntryManager
            java.util.Objects.requireNonNull(r2)
            com.android.systemui.statusbar.notification.collection.NotificationEntry r6 = r2.mEntry
            java.util.Objects.requireNonNull(r6)
            android.service.notification.StatusBarNotification r6 = r6.mSbn
            com.android.systemui.statusbar.notification.collection.NotificationEntry r2 = r2.mEntry
            com.android.systemui.statusbar.notification.collection.notifcollection.DismissedByUserStats r7 = new com.android.systemui.statusbar.notification.collection.notifcollection.DismissedByUserStats
            com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider r8 = r0.mVisibilityProvider
            com.android.internal.statusbar.NotificationVisibility r2 = r8.obtain(r2, r4)
            r7.<init>(r3, r2)
            r5.performRemoveNotification(r6, r7, r3)
            goto L_0x00f6
        L_0x0125:
            r2.resetTranslation()
            goto L_0x00f6
        L_0x0129:
            if (r9 != 0) goto L_0x0136
            com.android.internal.statusbar.IStatusBarService r9 = r0.mIStatusBarService     // Catch:{ Exception -> 0x0136 }
            com.android.systemui.statusbar.NotificationLockscreenUserManager r0 = r0.mLockscreenUserManager     // Catch:{ Exception -> 0x0136 }
            int r0 = r0.getCurrentUserId()     // Catch:{ Exception -> 0x0136 }
            r9.onClearAllNotifications(r0)     // Catch:{ Exception -> 0x0136 }
        L_0x0136:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda2.run():void");
    }
}
