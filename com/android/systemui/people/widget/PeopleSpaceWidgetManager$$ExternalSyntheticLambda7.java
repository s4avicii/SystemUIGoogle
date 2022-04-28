package com.android.systemui.people.widget;

import java.util.function.Predicate;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PeopleSpaceWidgetManager$$ExternalSyntheticLambda7 implements Predicate {
    public final /* synthetic */ PeopleSpaceWidgetManager f$0;

    public /* synthetic */ PeopleSpaceWidgetManager$$ExternalSyntheticLambda7(PeopleSpaceWidgetManager peopleSpaceWidgetManager) {
        this.f$0 = peopleSpaceWidgetManager;
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean test(java.lang.Object r4) {
        /*
            r3 = this;
            com.android.systemui.people.widget.PeopleSpaceWidgetManager r3 = r3.f$0
            com.android.systemui.statusbar.notification.collection.NotificationEntry r4 = (com.android.systemui.statusbar.notification.collection.NotificationEntry) r4
            java.util.Objects.requireNonNull(r3)
            r0 = 1
            r1 = 0
            if (r4 == 0) goto L_0x001f
            android.service.notification.NotificationListenerService$Ranking r2 = r4.mRanking
            if (r2 == 0) goto L_0x001f
            android.content.pm.ShortcutInfo r2 = r2.getConversationShortcutInfo()
            if (r2 == 0) goto L_0x001f
            android.service.notification.StatusBarNotification r2 = r4.mSbn
            android.app.Notification r2 = r2.getNotification()
            if (r2 == 0) goto L_0x001f
            r2 = r0
            goto L_0x0020
        L_0x001f:
            r2 = r1
        L_0x0020:
            if (r2 == 0) goto L_0x0064
            boolean r2 = com.android.systemui.people.NotificationHelper.isMissedCallOrHasContent(r4)
            if (r2 == 0) goto L_0x0064
            java.util.Optional<com.android.wm.shell.bubbles.Bubbles> r3 = r3.mBubblesOptional
            boolean r2 = r3.isPresent()     // Catch:{ Exception -> 0x0049 }
            if (r2 == 0) goto L_0x0060
            java.lang.Object r3 = r3.get()     // Catch:{ Exception -> 0x0049 }
            com.android.wm.shell.bubbles.Bubbles r3 = (com.android.p012wm.shell.bubbles.Bubbles) r3     // Catch:{ Exception -> 0x0049 }
            java.util.Objects.requireNonNull(r4)     // Catch:{ Exception -> 0x0049 }
            java.lang.String r2 = r4.mKey     // Catch:{ Exception -> 0x0049 }
            android.service.notification.StatusBarNotification r4 = r4.mSbn     // Catch:{ Exception -> 0x0049 }
            java.lang.String r4 = r4.getGroupKey()     // Catch:{ Exception -> 0x0049 }
            boolean r3 = r3.isBubbleNotificationSuppressedFromShade(r2, r4)     // Catch:{ Exception -> 0x0049 }
            if (r3 == 0) goto L_0x0060
            r3 = r0
            goto L_0x0061
        L_0x0049:
            r3 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r2 = "Exception checking if notification is suppressed: "
            r4.append(r2)
            r4.append(r3)
            java.lang.String r3 = r4.toString()
            java.lang.String r4 = "PeopleNotifHelper"
            android.util.Log.e(r4, r3)
        L_0x0060:
            r3 = r1
        L_0x0061:
            if (r3 != 0) goto L_0x0064
            goto L_0x0065
        L_0x0064:
            r0 = r1
        L_0x0065:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda7.test(java.lang.Object):boolean");
    }
}
