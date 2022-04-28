package com.android.systemui.privacy;

import com.android.systemui.util.concurrency.DelayableExecutor;

/* compiled from: PrivacyItemController.kt */
public final class PrivacyItemController$updateListAndNotifyChanges$1 implements Runnable {
    public final /* synthetic */ DelayableExecutor $uiExecutor;
    public final /* synthetic */ PrivacyItemController this$0;

    public PrivacyItemController$updateListAndNotifyChanges$1(PrivacyItemController privacyItemController, DelayableExecutor delayableExecutor) {
        this.this$0 = privacyItemController;
        this.$uiExecutor = delayableExecutor;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0087, code lost:
        if (r11 != 101) goto L_0x0098;
     */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x0069 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x013b A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00b3  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0133  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0138  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r18 = this;
            r0 = r18
            com.android.systemui.privacy.PrivacyItemController r1 = r0.this$0
            java.util.Objects.requireNonNull(r1)
            java.lang.Runnable r2 = r1.holdingRunnableCanceler
            r3 = 0
            if (r2 != 0) goto L_0x000d
            goto L_0x0012
        L_0x000d:
            r2.run()
            r1.holdingRunnableCanceler = r3
        L_0x0012:
            boolean r2 = r1.listening
            if (r2 != 0) goto L_0x001c
            kotlin.collections.EmptyList r2 = kotlin.collections.EmptyList.INSTANCE
            r1.privacyList = r2
            goto L_0x01c4
        L_0x001c:
            com.android.systemui.appops.AppOpsController r2 = r1.appOpsController
            r4 = 1
            java.util.ArrayList r2 = r2.getActiveAppOps(r4)
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            java.util.Iterator r2 = r2.iterator()
        L_0x002c:
            boolean r6 = r2.hasNext()
            r7 = 101(0x65, float:1.42E-43)
            r8 = 0
            r9 = 100
            if (r6 == 0) goto L_0x0060
            java.lang.Object r6 = r2.next()
            r10 = r6
            com.android.systemui.appops.AppOpItem r10 = (com.android.systemui.appops.AppOpItem) r10
            java.util.List<java.lang.Integer> r11 = r1.currentUserIds
            java.util.Objects.requireNonNull(r10)
            int r12 = r10.mUid
            int r12 = android.os.UserHandle.getUserId(r12)
            java.lang.Integer r12 = java.lang.Integer.valueOf(r12)
            boolean r11 = r11.contains(r12)
            if (r11 != 0) goto L_0x0059
            int r10 = r10.mCode
            if (r10 == r9) goto L_0x0059
            if (r10 != r7) goto L_0x005a
        L_0x0059:
            r8 = r4
        L_0x005a:
            if (r8 == 0) goto L_0x002c
            r5.add(r6)
            goto L_0x002c
        L_0x0060:
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            java.util.Iterator r5 = r5.iterator()
        L_0x0069:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x00b7
            java.lang.Object r6 = r5.next()
            com.android.systemui.appops.AppOpItem r6 = (com.android.systemui.appops.AppOpItem) r6
            com.android.systemui.privacy.PrivacyType r10 = com.android.systemui.privacy.PrivacyType.TYPE_LOCATION
            int r11 = r6.mCode
            if (r11 == 0) goto L_0x0091
            if (r11 == r4) goto L_0x0091
            r12 = 26
            if (r11 == r12) goto L_0x008d
            r12 = 27
            if (r11 == r12) goto L_0x008a
            if (r11 == r9) goto L_0x008a
            if (r11 == r7) goto L_0x008d
            goto L_0x0098
        L_0x008a:
            com.android.systemui.privacy.PrivacyType r11 = com.android.systemui.privacy.PrivacyType.TYPE_MICROPHONE
            goto L_0x008f
        L_0x008d:
            com.android.systemui.privacy.PrivacyType r11 = com.android.systemui.privacy.PrivacyType.TYPE_CAMERA
        L_0x008f:
            r13 = r11
            goto L_0x0092
        L_0x0091:
            r13 = r10
        L_0x0092:
            if (r13 != r10) goto L_0x009a
            boolean r10 = r1.locationAvailable
            if (r10 != 0) goto L_0x009a
        L_0x0098:
            r10 = r3
            goto L_0x00b0
        L_0x009a:
            com.android.systemui.privacy.PrivacyApplication r14 = new com.android.systemui.privacy.PrivacyApplication
            java.lang.String r10 = r6.mPackageName
            int r11 = r6.mUid
            r14.<init>(r10, r11)
            com.android.systemui.privacy.PrivacyItem r10 = new com.android.systemui.privacy.PrivacyItem
            long r11 = r6.mTimeStartedElapsed
            boolean r6 = r6.mIsDisabled
            r15 = r11
            r12 = r10
            r17 = r6
            r12.<init>(r13, r14, r15, r17)
        L_0x00b0:
            if (r10 != 0) goto L_0x00b3
            goto L_0x0069
        L_0x00b3:
            r2.add(r10)
            goto L_0x0069
        L_0x00b7:
            java.util.Set r2 = kotlin.collections.CollectionsKt___CollectionsKt.toMutableSet(r2)
            java.util.List r2 = kotlin.collections.CollectionsKt___CollectionsKt.toList(r2)
            com.android.systemui.privacy.logging.PrivacyLogger r5 = r1.logger
            r5.logRetrievedPrivacyItemsList(r2)
            com.android.systemui.util.time.SystemClock r5 = r1.systemClock
            long r5 = r5.elapsedRealtime()
            r9 = 5000(0x1388, double:2.4703E-320)
            long r5 = r5 - r9
            monitor-enter(r1)
            java.util.List<com.android.systemui.privacy.PrivacyItem> r7 = r1.privacyList     // Catch:{ all -> 0x01ce }
            java.util.List r7 = kotlin.collections.CollectionsKt___CollectionsKt.toList(r7)     // Catch:{ all -> 0x01ce }
            monitor-exit(r1)
            java.util.ArrayList r9 = new java.util.ArrayList
            r9.<init>()
            java.util.Iterator r7 = r7.iterator()
        L_0x00de:
            boolean r10 = r7.hasNext()
            if (r10 == 0) goto L_0x013e
            java.lang.Object r10 = r7.next()
            r11 = r10
            com.android.systemui.privacy.PrivacyItem r11 = (com.android.systemui.privacy.PrivacyItem) r11
            java.util.Objects.requireNonNull(r11)
            long r12 = r11.timeStampElapsed
            int r12 = (r12 > r5 ? 1 : (r12 == r5 ? 0 : -1))
            if (r12 <= 0) goto L_0x0135
            boolean r12 = r2.isEmpty()
            if (r12 == 0) goto L_0x00fb
            goto L_0x0130
        L_0x00fb:
            java.util.Iterator r12 = r2.iterator()
        L_0x00ff:
            boolean r13 = r12.hasNext()
            if (r13 == 0) goto L_0x0130
            java.lang.Object r13 = r12.next()
            com.android.systemui.privacy.PrivacyItem r13 = (com.android.systemui.privacy.PrivacyItem) r13
            java.util.Objects.requireNonNull(r13)
            com.android.systemui.privacy.PrivacyType r14 = r13.privacyType
            com.android.systemui.privacy.PrivacyType r15 = r11.privacyType
            if (r14 != r15) goto L_0x0128
            com.android.systemui.privacy.PrivacyApplication r14 = r13.application
            com.android.systemui.privacy.PrivacyApplication r15 = r11.application
            boolean r14 = kotlin.jvm.internal.Intrinsics.areEqual(r14, r15)
            if (r14 == 0) goto L_0x0128
            long r13 = r13.timeStampElapsed
            long r3 = r11.timeStampElapsed
            int r3 = (r13 > r3 ? 1 : (r13 == r3 ? 0 : -1))
            if (r3 != 0) goto L_0x0128
            r3 = 1
            goto L_0x0129
        L_0x0128:
            r3 = r8
        L_0x0129:
            if (r3 == 0) goto L_0x012d
            r3 = 1
            goto L_0x0131
        L_0x012d:
            r3 = 0
            r4 = 1
            goto L_0x00ff
        L_0x0130:
            r3 = r8
        L_0x0131:
            if (r3 != 0) goto L_0x0135
            r3 = 1
            goto L_0x0136
        L_0x0135:
            r3 = r8
        L_0x0136:
            if (r3 == 0) goto L_0x013b
            r9.add(r10)
        L_0x013b:
            r3 = 0
            r4 = 1
            goto L_0x00de
        L_0x013e:
            boolean r3 = r9.isEmpty()
            r4 = 1
            r3 = r3 ^ r4
            if (r3 == 0) goto L_0x019b
            com.android.systemui.privacy.logging.PrivacyLogger r3 = r1.logger
            r3.logPrivacyItemsToHold(r9)
            java.util.Iterator r3 = r9.iterator()
            boolean r4 = r3.hasNext()
            if (r4 != 0) goto L_0x0157
            r3 = 0
            goto L_0x0184
        L_0x0157:
            java.lang.Object r4 = r3.next()
            boolean r7 = r3.hasNext()
            if (r7 != 0) goto L_0x0163
        L_0x0161:
            r3 = r4
            goto L_0x0184
        L_0x0163:
            r7 = r4
            com.android.systemui.privacy.PrivacyItem r7 = (com.android.systemui.privacy.PrivacyItem) r7
            java.util.Objects.requireNonNull(r7)
            long r7 = r7.timeStampElapsed
        L_0x016b:
            java.lang.Object r10 = r3.next()
            r11 = r10
            com.android.systemui.privacy.PrivacyItem r11 = (com.android.systemui.privacy.PrivacyItem) r11
            java.util.Objects.requireNonNull(r11)
            long r11 = r11.timeStampElapsed
            int r13 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1))
            if (r13 <= 0) goto L_0x017d
            r4 = r10
            r7 = r11
        L_0x017d:
            boolean r10 = r3.hasNext()
            if (r10 != 0) goto L_0x016b
            goto L_0x0161
        L_0x0184:
            kotlin.jvm.internal.Intrinsics.checkNotNull(r3)
            com.android.systemui.privacy.PrivacyItem r3 = (com.android.systemui.privacy.PrivacyItem) r3
            long r3 = r3.timeStampElapsed
            long r3 = r3 - r5
            com.android.systemui.privacy.logging.PrivacyLogger r5 = r1.logger
            r5.logPrivacyItemsUpdateScheduled(r3)
            com.android.systemui.util.concurrency.DelayableExecutor r5 = r1.bgExecutor
            com.android.systemui.privacy.PrivacyItemController$updateListAndNotifyChanges$1 r6 = r1.updateListAndNotifyChanges
            java.lang.Runnable r3 = r5.executeDelayed(r6, r3)
            r1.holdingRunnableCanceler = r3
        L_0x019b:
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            java.util.Iterator r2 = r2.iterator()
        L_0x01a4:
            boolean r4 = r2.hasNext()
            if (r4 == 0) goto L_0x01be
            java.lang.Object r4 = r2.next()
            r5 = r4
            com.android.systemui.privacy.PrivacyItem r5 = (com.android.systemui.privacy.PrivacyItem) r5
            java.util.Objects.requireNonNull(r5)
            boolean r5 = r5.paused
            r6 = 1
            r5 = r5 ^ r6
            if (r5 == 0) goto L_0x01a4
            r3.add(r4)
            goto L_0x01a4
        L_0x01be:
            java.util.ArrayList r2 = kotlin.collections.CollectionsKt___CollectionsKt.plus((java.util.List) r3, (java.util.List) r9)
            r1.privacyList = r2
        L_0x01c4:
            com.android.systemui.util.concurrency.DelayableExecutor r1 = r0.$uiExecutor
            com.android.systemui.privacy.PrivacyItemController r0 = r0.this$0
            com.android.systemui.privacy.PrivacyItemController$notifyChanges$1 r0 = r0.notifyChanges
            r1.execute(r0)
            return
        L_0x01ce:
            r0 = move-exception
            monitor-exit(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.privacy.PrivacyItemController$updateListAndNotifyChanges$1.run():void");
    }
}
