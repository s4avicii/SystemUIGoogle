package com.android.systemui.recents;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class OverviewProxyService$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ OverviewProxyService$1$$ExternalSyntheticLambda0(Object obj, Object obj2, Object obj3, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
        this.f$2 = obj3;
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x00ca  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r15 = this;
            int r0 = r15.$r8$classId
            r1 = 1
            r2 = 0
            switch(r0) {
                case 0: goto L_0x0008;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x0061
        L_0x0008:
            java.lang.Object r0 = r15.f$0
            com.android.systemui.recents.OverviewProxyService$1 r0 = (com.android.systemui.recents.OverviewProxyService.C10571) r0
            java.lang.Object r3 = r15.f$1
            android.view.MotionEvent r3 = (android.view.MotionEvent) r3
            java.lang.Object r15 = r15.f$2
            com.android.systemui.statusbar.phone.StatusBar r15 = (com.android.systemui.statusbar.phone.StatusBar) r15
            java.util.Objects.requireNonNull(r0)
            int r4 = r3.getActionMasked()
            if (r4 != 0) goto L_0x0037
            com.android.systemui.recents.OverviewProxyService r5 = com.android.systemui.recents.OverviewProxyService.this
            r5.mInputFocusTransferStarted = r1
            float r6 = r3.getY()
            r5.mInputFocusTransferStartY = r6
            com.android.systemui.recents.OverviewProxyService r5 = com.android.systemui.recents.OverviewProxyService.this
            long r6 = r3.getEventTime()
            r5.mInputFocusTransferStartMillis = r6
            com.android.systemui.recents.OverviewProxyService r5 = com.android.systemui.recents.OverviewProxyService.this
            boolean r5 = r5.mInputFocusTransferStarted
            r6 = 0
            r15.onInputFocusTransfer(r5, r2, r6)
        L_0x0037:
            r5 = 3
            if (r4 == r1) goto L_0x003c
            if (r4 != r5) goto L_0x005d
        L_0x003c:
            com.android.systemui.recents.OverviewProxyService r6 = com.android.systemui.recents.OverviewProxyService.this
            r6.mInputFocusTransferStarted = r2
            float r6 = r3.getY()
            com.android.systemui.recents.OverviewProxyService r7 = com.android.systemui.recents.OverviewProxyService.this
            float r7 = r7.mInputFocusTransferStartY
            float r6 = r6 - r7
            long r7 = r3.getEventTime()
            com.android.systemui.recents.OverviewProxyService r0 = com.android.systemui.recents.OverviewProxyService.this
            long r9 = r0.mInputFocusTransferStartMillis
            long r7 = r7 - r9
            float r7 = (float) r7
            float r6 = r6 / r7
            boolean r0 = r0.mInputFocusTransferStarted
            if (r4 != r5) goto L_0x0059
            goto L_0x005a
        L_0x0059:
            r1 = r2
        L_0x005a:
            r15.onInputFocusTransfer(r0, r1, r6)
        L_0x005d:
            r3.recycle()
            return
        L_0x0061:
            java.lang.Object r0 = r15.f$0
            com.android.systemui.statusbar.NotificationListener r0 = (com.android.systemui.statusbar.NotificationListener) r0
            java.lang.Object r3 = r15.f$1
            android.service.notification.StatusBarNotification r3 = (android.service.notification.StatusBarNotification) r3
            java.lang.Object r15 = r15.f$2
            android.service.notification.NotificationListenerService$RankingMap r15 = (android.service.notification.NotificationListenerService.RankingMap) r15
            int r4 = com.android.systemui.statusbar.NotificationListener.$r8$clinit
            java.util.Objects.requireNonNull(r0)
            android.app.Notification r4 = r3.getNotification()
            android.content.Context r5 = r0.mContext
            boolean r6 = com.android.systemui.statusbar.RemoteInputController.ENABLE_REMOTE_INPUT
            if (r6 != 0) goto L_0x007d
            goto L_0x00d8
        L_0x007d:
            android.os.Bundle r6 = r4.extras
            if (r6 == 0) goto L_0x00d8
            java.lang.String r7 = "android.wearable.EXTENSIONS"
            boolean r6 = r6.containsKey(r7)
            if (r6 == 0) goto L_0x00d8
            android.app.Notification$Action[] r6 = r4.actions
            if (r6 == 0) goto L_0x0090
            int r6 = r6.length
            if (r6 != 0) goto L_0x00d8
        L_0x0090:
            r6 = 0
            android.app.Notification$WearableExtender r7 = new android.app.Notification$WearableExtender
            r7.<init>(r4)
            java.util.List r7 = r7.getActions()
            int r8 = r7.size()
            r9 = r2
        L_0x009f:
            if (r9 >= r8) goto L_0x00c8
            java.lang.Object r10 = r7.get(r9)
            android.app.Notification$Action r10 = (android.app.Notification.Action) r10
            if (r10 != 0) goto L_0x00aa
            goto L_0x00c5
        L_0x00aa:
            android.app.RemoteInput[] r11 = r10.getRemoteInputs()
            if (r11 != 0) goto L_0x00b1
            goto L_0x00c5
        L_0x00b1:
            int r12 = r11.length
            r13 = r2
        L_0x00b3:
            if (r13 >= r12) goto L_0x00c2
            r14 = r11[r13]
            boolean r14 = r14.getAllowFreeFormInput()
            if (r14 == 0) goto L_0x00bf
            r6 = r10
            goto L_0x00c2
        L_0x00bf:
            int r13 = r13 + 1
            goto L_0x00b3
        L_0x00c2:
            if (r6 == 0) goto L_0x00c5
            goto L_0x00c8
        L_0x00c5:
            int r9 = r9 + 1
            goto L_0x009f
        L_0x00c8:
            if (r6 == 0) goto L_0x00d8
            android.app.Notification$Builder r4 = android.app.Notification.Builder.recoverBuilder(r5, r4)
            android.app.Notification$Action[] r1 = new android.app.Notification.Action[r1]
            r1[r2] = r6
            r4.setActions(r1)
            r4.build()
        L_0x00d8:
            java.util.ArrayList r0 = r0.mNotificationHandlers
            java.util.Iterator r0 = r0.iterator()
        L_0x00de:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x00ee
            java.lang.Object r1 = r0.next()
            com.android.systemui.statusbar.NotificationListener$NotificationHandler r1 = (com.android.systemui.statusbar.NotificationListener.NotificationHandler) r1
            r1.onNotificationPosted(r3, r15)
            goto L_0x00de
        L_0x00ee:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda0.run():void");
    }
}
