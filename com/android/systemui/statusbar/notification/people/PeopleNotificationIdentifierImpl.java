package com.android.systemui.statusbar.notification.people;

import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PeopleNotificationIdentifier.kt */
public final class PeopleNotificationIdentifierImpl implements PeopleNotificationIdentifier {
    public final GroupMembershipManager groupManager;
    public final NotificationPersonExtractor personExtractor;

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0065 A[LOOP:0: B:24:0x0065->B:27:0x007f, LOOP_START, PHI: r3 
      PHI: (r3v2 int) = (r3v0 int), (r3v3 int) binds: [B:23:0x005f, B:27:0x007f] A[DONT_GENERATE, DONT_INLINE]] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int getPeopleNotificationType(com.android.systemui.statusbar.notification.collection.NotificationEntry r6) {
        /*
            r5 = this;
            android.service.notification.NotificationListenerService$Ranking r0 = r6.mRanking
            boolean r1 = r0.isConversation()
            r2 = 1
            r3 = 0
            r4 = 3
            if (r1 != 0) goto L_0x000d
            r2 = r3
            goto L_0x0028
        L_0x000d:
            android.content.pm.ShortcutInfo r1 = r0.getConversationShortcutInfo()
            if (r1 != 0) goto L_0x0014
            goto L_0x0028
        L_0x0014:
            android.app.NotificationChannel r0 = r0.getChannel()
            if (r0 != 0) goto L_0x001b
            goto L_0x0022
        L_0x001b:
            boolean r0 = r0.isImportantConversation()
            if (r0 != r2) goto L_0x0022
            goto L_0x0023
        L_0x0022:
            r2 = r3
        L_0x0023:
            if (r2 == 0) goto L_0x0027
            r2 = r4
            goto L_0x0028
        L_0x0027:
            r2 = 2
        L_0x0028:
            if (r2 != r4) goto L_0x002b
            goto L_0x0085
        L_0x002b:
            android.service.notification.StatusBarNotification r0 = r6.mSbn
            com.android.systemui.statusbar.notification.people.NotificationPersonExtractor r1 = r5.personExtractor
            boolean r0 = r1.isPersonNotification(r0)
            int r0 = java.lang.Math.max(r2, r0)
            if (r0 != r4) goto L_0x003a
            goto L_0x0085
        L_0x003a:
            com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager r1 = r5.groupManager
            boolean r1 = r1.isGroupSummary(r6)
            if (r1 != 0) goto L_0x0043
            goto L_0x0081
        L_0x0043:
            com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager r1 = r5.groupManager
            java.util.List r6 = r1.getChildren(r6)
            if (r6 != 0) goto L_0x004d
            r5 = 0
            goto L_0x005c
        L_0x004d:
            kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1 r1 = new kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1
            r1.<init>(r6)
            com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifierImpl$getPeopleTypeOfSummary$childTypes$1 r6 = new com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifierImpl$getPeopleTypeOfSummary$childTypes$1
            r6.<init>(r5)
            kotlin.sequences.TransformingSequence r5 = new kotlin.sequences.TransformingSequence
            r5.<init>(r1, r6)
        L_0x005c:
            if (r5 != 0) goto L_0x005f
            goto L_0x0081
        L_0x005f:
            kotlin.sequences.Sequence<T> r6 = r5.sequence
            java.util.Iterator r6 = r6.iterator()
        L_0x0065:
            boolean r1 = r6.hasNext()
            if (r1 == 0) goto L_0x0081
            kotlin.jvm.functions.Function1<T, R> r1 = r5.transformer
            java.lang.Object r2 = r6.next()
            java.lang.Object r1 = r1.invoke(r2)
            java.lang.Number r1 = (java.lang.Number) r1
            int r1 = r1.intValue()
            int r3 = java.lang.Math.max(r3, r1)
            if (r3 != r4) goto L_0x0065
        L_0x0081:
            int r4 = java.lang.Math.max(r0, r3)
        L_0x0085:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifierImpl.getPeopleNotificationType(com.android.systemui.statusbar.notification.collection.NotificationEntry):int");
    }

    public PeopleNotificationIdentifierImpl(NotificationPersonExtractor notificationPersonExtractor, GroupMembershipManager groupMembershipManager) {
        this.personExtractor = notificationPersonExtractor;
        this.groupManager = groupMembershipManager;
    }

    public final int compareTo(int i, int i2) {
        return Intrinsics.compare(i2, i);
    }
}
