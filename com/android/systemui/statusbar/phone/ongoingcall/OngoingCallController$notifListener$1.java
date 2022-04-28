package com.android.systemui.statusbar.phone.ongoingcall;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: OngoingCallController.kt */
public final class OngoingCallController$notifListener$1 implements NotifCollectionListener {
    public final /* synthetic */ OngoingCallController this$0;

    public OngoingCallController$notifListener$1(OngoingCallController ongoingCallController) {
        this.this$0 = ongoingCallController;
    }

    public final void onEntryRemoved(NotificationEntry notificationEntry, int i) {
        String str;
        String key = notificationEntry.mSbn.getKey();
        OngoingCallController.CallNotificationInfo callNotificationInfo = this.this$0.callNotificationInfo;
        if (callNotificationInfo == null) {
            str = null;
        } else {
            str = callNotificationInfo.key;
        }
        if (Intrinsics.areEqual(key, str)) {
            OngoingCallController.access$removeChip(this.this$0);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0015, code lost:
        if (r11.mSbn.getNotification().isStyle(android.app.Notification.CallStyle.class) == false) goto L_0x0017;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onEntryUpdated(com.android.systemui.statusbar.notification.collection.NotificationEntry r11) {
        /*
            r10 = this;
            com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController r0 = r10.this$0
            com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$CallNotificationInfo r0 = r0.callNotificationInfo
            r1 = 0
            if (r0 != 0) goto L_0x0017
            boolean r0 = com.android.systemui.statusbar.phone.ongoingcall.OngoingCallControllerKt.DEBUG
            android.service.notification.StatusBarNotification r0 = r11.mSbn
            android.app.Notification r0 = r0.getNotification()
            java.lang.Class<android.app.Notification$CallStyle> r2 = android.app.Notification.CallStyle.class
            boolean r0 = r0.isStyle(r2)
            if (r0 != 0) goto L_0x002d
        L_0x0017:
            android.service.notification.StatusBarNotification r0 = r11.mSbn
            java.lang.String r0 = r0.getKey()
            com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController r2 = r10.this$0
            com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$CallNotificationInfo r2 = r2.callNotificationInfo
            if (r2 != 0) goto L_0x0025
            r2 = r1
            goto L_0x0027
        L_0x0025:
            java.lang.String r2 = r2.key
        L_0x0027:
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r2)
            if (r0 == 0) goto L_0x008c
        L_0x002d:
            com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$CallNotificationInfo r0 = new com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$CallNotificationInfo
            android.service.notification.StatusBarNotification r2 = r11.mSbn
            java.lang.String r3 = r2.getKey()
            android.service.notification.StatusBarNotification r2 = r11.mSbn
            android.app.Notification r2 = r2.getNotification()
            long r4 = r2.when
            android.service.notification.StatusBarNotification r2 = r11.mSbn
            android.app.Notification r2 = r2.getNotification()
            android.app.PendingIntent r2 = r2.contentIntent
            if (r2 != 0) goto L_0x0048
            goto L_0x004c
        L_0x0048:
            android.content.Intent r1 = r2.getIntent()
        L_0x004c:
            r6 = r1
            android.service.notification.StatusBarNotification r1 = r11.mSbn
            int r7 = r1.getUid()
            android.service.notification.StatusBarNotification r11 = r11.mSbn
            android.app.Notification r11 = r11.getNotification()
            android.os.Bundle r11 = r11.extras
            r1 = -1
            java.lang.String r2 = "android.callType"
            int r11 = r11.getInt(r2, r1)
            r1 = 2
            r2 = 0
            if (r11 != r1) goto L_0x0068
            r11 = 1
            goto L_0x0069
        L_0x0068:
            r11 = r2
        L_0x0069:
            com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController r1 = r10.this$0
            com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$CallNotificationInfo r1 = r1.callNotificationInfo
            if (r1 != 0) goto L_0x0070
            goto L_0x0072
        L_0x0070:
            boolean r2 = r1.statusBarSwipedAway
        L_0x0072:
            r9 = r2
            r2 = r0
            r8 = r11
            r2.<init>(r3, r4, r6, r7, r8, r9)
            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r1)
            if (r1 == 0) goto L_0x007f
            return
        L_0x007f:
            com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController r10 = r10.this$0
            r10.callNotificationInfo = r0
            if (r11 == 0) goto L_0x0089
            r10.updateChip()
            goto L_0x008c
        L_0x0089:
            com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController.access$removeChip(r10)
        L_0x008c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$notifListener$1.onEntryUpdated(com.android.systemui.statusbar.notification.collection.NotificationEntry):void");
    }

    public final void onEntryAdded(NotificationEntry notificationEntry) {
        onEntryUpdated(notificationEntry);
    }
}
