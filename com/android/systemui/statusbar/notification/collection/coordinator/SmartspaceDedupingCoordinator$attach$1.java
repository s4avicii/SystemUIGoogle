package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.plugins.BcSmartspaceDataPlugin;

/* compiled from: SmartspaceDedupingCoordinator.kt */
public /* synthetic */ class SmartspaceDedupingCoordinator$attach$1 implements BcSmartspaceDataPlugin.SmartspaceTargetListener {
    public final /* synthetic */ SmartspaceDedupingCoordinator $tmp0;

    public SmartspaceDedupingCoordinator$attach$1(SmartspaceDedupingCoordinator smartspaceDedupingCoordinator) {
        this.$tmp0 = smartspaceDedupingCoordinator;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0073  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onSmartspaceTargetsUpdated(java.util.List<? extends android.os.Parcelable> r6) {
        /*
            r5 = this;
            com.android.systemui.statusbar.notification.collection.coordinator.SmartspaceDedupingCoordinator r5 = r5.$tmp0
            java.util.Objects.requireNonNull(r5)
            java.util.LinkedHashMap r0 = new java.util.LinkedHashMap
            r0.<init>()
            java.util.LinkedHashMap r1 = r5.trackedSmartspaceTargets
            java.util.Iterator r6 = r6.iterator()
            boolean r2 = r6.hasNext()
            if (r2 == 0) goto L_0x0043
            java.lang.Object r6 = r6.next()
            android.os.Parcelable r6 = (android.os.Parcelable) r6
            boolean r2 = r6 instanceof android.app.smartspace.SmartspaceTarget
            if (r2 == 0) goto L_0x0023
            android.app.smartspace.SmartspaceTarget r6 = (android.app.smartspace.SmartspaceTarget) r6
            goto L_0x0024
        L_0x0023:
            r6 = 0
        L_0x0024:
            if (r6 != 0) goto L_0x0027
            goto L_0x0043
        L_0x0027:
            java.lang.String r6 = r6.getSourceNotificationKey()
            if (r6 != 0) goto L_0x002e
            goto L_0x0043
        L_0x002e:
            java.lang.Object r2 = r1.get(r6)
            if (r2 != 0) goto L_0x0039
            com.android.systemui.statusbar.notification.collection.coordinator.TrackedSmartspaceTarget r2 = new com.android.systemui.statusbar.notification.collection.coordinator.TrackedSmartspaceTarget
            r2.<init>(r6)
        L_0x0039:
            com.android.systemui.statusbar.notification.collection.coordinator.TrackedSmartspaceTarget r2 = (com.android.systemui.statusbar.notification.collection.coordinator.TrackedSmartspaceTarget) r2
            r0.put(r6, r2)
            boolean r6 = r5.updateFilterStatus(r2)
            goto L_0x0044
        L_0x0043:
            r6 = 0
        L_0x0044:
            java.util.Set r2 = r1.keySet()
            java.util.Iterator r2 = r2.iterator()
        L_0x004c:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0071
            java.lang.Object r3 = r2.next()
            java.lang.String r3 = (java.lang.String) r3
            boolean r4 = r0.containsKey(r3)
            if (r4 != 0) goto L_0x004c
            java.lang.Object r6 = r1.get(r3)
            com.android.systemui.statusbar.notification.collection.coordinator.TrackedSmartspaceTarget r6 = (com.android.systemui.statusbar.notification.collection.coordinator.TrackedSmartspaceTarget) r6
            if (r6 != 0) goto L_0x0067
            goto L_0x006f
        L_0x0067:
            java.lang.Runnable r6 = r6.cancelTimeoutRunnable
            if (r6 != 0) goto L_0x006c
            goto L_0x006f
        L_0x006c:
            r6.run()
        L_0x006f:
            r6 = 1
            goto L_0x004c
        L_0x0071:
            if (r6 == 0) goto L_0x007f
            com.android.systemui.statusbar.notification.collection.coordinator.SmartspaceDedupingCoordinator$filter$1 r6 = r5.filter
            r6.invalidateList()
            com.android.systemui.statusbar.notification.NotificationEntryManager r6 = r5.notificationEntryManager
            java.lang.String r1 = "Smartspace targets changed"
            r6.updateNotifications(r1)
        L_0x007f:
            r5.trackedSmartspaceTargets = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.coordinator.SmartspaceDedupingCoordinator$attach$1.onSmartspaceTargetsUpdated(java.util.List):void");
    }
}
