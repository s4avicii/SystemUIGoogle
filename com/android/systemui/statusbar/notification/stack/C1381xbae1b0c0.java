package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;

/* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1381xbae1b0c0 implements GroupExpansionManager.OnGroupExpansionChangeListener {
    public final /* synthetic */ NotificationStackScrollLayoutController f$0;

    public /* synthetic */ C1381xbae1b0c0(NotificationStackScrollLayoutController notificationStackScrollLayoutController) {
        this.f$0 = notificationStackScrollLayoutController;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0019, code lost:
        if (r4.mIsPinned != false) goto L_0x001b;
     */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0020  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onGroupExpansionChange(com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r4, boolean r5) {
        /*
            r3 = this;
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController r3 = r3.f$0
            java.util.Objects.requireNonNull(r3)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r3 = r3.mView
            java.util.Objects.requireNonNull(r3)
            boolean r0 = r3.mAnimationsEnabled
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x001d
            boolean r0 = r3.mIsExpanded
            if (r0 != 0) goto L_0x001b
            java.util.Objects.requireNonNull(r4)
            boolean r0 = r4.mIsPinned
            if (r0 == 0) goto L_0x001d
        L_0x001b:
            r0 = r1
            goto L_0x001e
        L_0x001d:
            r0 = r2
        L_0x001e:
            if (r0 == 0) goto L_0x0024
            r3.mExpandedGroupView = r4
            r3.mNeedsAnimation = r1
        L_0x0024:
            java.util.Objects.requireNonNull(r4)
            r4.mChildrenExpanded = r5
            com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer r0 = r4.mChildrenContainer
            if (r0 == 0) goto L_0x0030
            r0.setChildrenExpanded$1(r5)
        L_0x0030:
            r4.updateBackgroundForGroupState()
            r4.updateClickAndFocus()
            r3.onChildHeightChanged(r4, r2)
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$10 r5 = new com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$10
            r5.<init>()
            java.util.HashSet<java.lang.Runnable> r3 = r3.mAnimationFinishedRunnables
            r3.add(r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.C1381xbae1b0c0.onGroupExpansionChange(com.android.systemui.statusbar.notification.row.ExpandableNotificationRow, boolean):void");
    }
}
