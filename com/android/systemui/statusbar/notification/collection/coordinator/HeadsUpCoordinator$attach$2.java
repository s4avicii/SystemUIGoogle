package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener;

/* compiled from: HeadsUpCoordinator.kt */
public /* synthetic */ class HeadsUpCoordinator$attach$2 implements OnBeforeFinalizeFilterListener {
    public final /* synthetic */ HeadsUpCoordinator $tmp0;

    public HeadsUpCoordinator$attach$2(HeadsUpCoordinator headsUpCoordinator) {
        this.$tmp0 = headsUpCoordinator;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0184, code lost:
        if (r7 != false) goto L_0x0186;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x0217, code lost:
        if (r7 != false) goto L_0x021b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onBeforeFinalizeFilter(java.util.List<? extends com.android.systemui.statusbar.notification.collection.ListEntry> r25) {
        /*
            r24 = this;
            r0 = r24
            com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator r0 = r0.$tmp0
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.log.LogLevel r1 = com.android.systemui.log.LogLevel.VERBOSE
            java.util.LinkedHashMap<java.lang.String, com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$PostedEntry> r2 = r0.mPostedEntries
            boolean r2 = r2.isEmpty()
            if (r2 == 0) goto L_0x0013
            goto L_0x037a
        L_0x0013:
            java.util.LinkedHashMap<java.lang.String, com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$PostedEntry> r2 = r0.mPostedEntries
            java.util.Collection r2 = r2.values()
            java.util.LinkedHashMap r3 = new java.util.LinkedHashMap
            r3.<init>()
            java.util.Iterator r2 = r2.iterator()
        L_0x0022:
            boolean r4 = r2.hasNext()
            if (r4 == 0) goto L_0x0051
            java.lang.Object r4 = r2.next()
            r5 = r4
            com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$PostedEntry r5 = (com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator.PostedEntry) r5
            java.util.Objects.requireNonNull(r5)
            com.android.systemui.statusbar.notification.collection.NotificationEntry r5 = r5.entry
            java.util.Objects.requireNonNull(r5)
            android.service.notification.StatusBarNotification r5 = r5.mSbn
            java.lang.String r5 = r5.getGroupKey()
            java.lang.Object r6 = r3.get(r5)
            if (r6 != 0) goto L_0x004b
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            r3.put(r5, r6)
        L_0x004b:
            java.util.List r6 = (java.util.List) r6
            r6.add(r4)
            goto L_0x0022
        L_0x0051:
            com.android.systemui.statusbar.notification.collection.NotifPipeline r2 = r0.mNotifPipeline
            r4 = 0
            if (r2 != 0) goto L_0x0057
            r2 = r4
        L_0x0057:
            java.util.Collection r2 = r2.getAllNotifs()
            kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1 r5 = new kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1
            r5.<init>(r2)
            com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$onBeforeFinalizeFilter$logicalMembersByGroup$1 r2 = new com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$onBeforeFinalizeFilter$logicalMembersByGroup$1
            r2.<init>(r3)
            kotlin.sequences.FilteringSequence r2 = kotlin.sequences.SequencesKt___SequencesKt.filter(r5, r2)
            java.util.LinkedHashMap r5 = new java.util.LinkedHashMap
            r5.<init>()
            kotlin.sequences.FilteringSequence$iterator$1 r6 = new kotlin.sequences.FilteringSequence$iterator$1
            r6.<init>(r2)
        L_0x0073:
            boolean r2 = r6.hasNext()
            if (r2 == 0) goto L_0x009d
            java.lang.Object r2 = r6.next()
            r7 = r2
            com.android.systemui.statusbar.notification.collection.NotificationEntry r7 = (com.android.systemui.statusbar.notification.collection.NotificationEntry) r7
            java.util.Objects.requireNonNull(r7)
            android.service.notification.StatusBarNotification r7 = r7.mSbn
            java.lang.String r7 = r7.getGroupKey()
            java.lang.Object r8 = r5.get(r7)
            if (r8 != 0) goto L_0x0097
            java.util.ArrayList r8 = new java.util.ArrayList
            r8.<init>()
            r5.put(r7, r8)
        L_0x0097:
            java.util.List r8 = (java.util.List) r8
            r8.add(r2)
            goto L_0x0073
        L_0x009d:
            com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$onBeforeFinalizeFilter$groupLocationsByKey$2 r2 = new com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$onBeforeFinalizeFilter$groupLocationsByKey$2
            r6 = r25
            r2.<init>(r0, r6)
            kotlin.Lazy r2 = kotlin.LazyKt__LazyJVMKt.lazy(r2)
            com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger r6 = r0.mLogger
            int r7 = r3.size()
            java.util.Objects.requireNonNull(r6)
            boolean r8 = r6.verbose
            java.lang.String r9 = "HeadsUpCoordinator"
            if (r8 != 0) goto L_0x00b8
            goto L_0x00cc
        L_0x00b8:
            com.android.systemui.log.LogBuffer r6 = r6.buffer
            com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$logEvaluatingGroups$2 r8 = com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$logEvaluatingGroups$2.INSTANCE
            java.util.Objects.requireNonNull(r6)
            boolean r10 = r6.frozen
            if (r10 != 0) goto L_0x00cc
            com.android.systemui.log.LogMessageImpl r8 = r6.obtain(r9, r1, r8)
            r8.int1 = r7
            r6.push(r8)
        L_0x00cc:
            java.util.Set r3 = r3.entrySet()
            java.util.Iterator r3 = r3.iterator()
        L_0x00d4:
            boolean r6 = r3.hasNext()
            if (r6 == 0) goto L_0x0375
            java.lang.Object r6 = r3.next()
            java.util.Map$Entry r6 = (java.util.Map.Entry) r6
            java.lang.Object r7 = r6.getKey()
            java.lang.String r7 = (java.lang.String) r7
            java.lang.Object r6 = r6.getValue()
            java.util.List r6 = (java.util.List) r6
            java.lang.Object r8 = r5.get(r7)
            java.util.List r8 = (java.util.List) r8
            if (r8 != 0) goto L_0x00f6
            kotlin.collections.EmptyList r8 = kotlin.collections.EmptyList.INSTANCE
        L_0x00f6:
            java.util.Iterator r10 = r8.iterator()
        L_0x00fa:
            boolean r11 = r10.hasNext()
            if (r11 == 0) goto L_0x0117
            java.lang.Object r11 = r10.next()
            r12 = r11
            com.android.systemui.statusbar.notification.collection.NotificationEntry r12 = (com.android.systemui.statusbar.notification.collection.NotificationEntry) r12
            java.util.Objects.requireNonNull(r12)
            android.service.notification.StatusBarNotification r12 = r12.mSbn
            android.app.Notification r12 = r12.getNotification()
            boolean r12 = r12.isGroupSummary()
            if (r12 == 0) goto L_0x00fa
            goto L_0x0118
        L_0x0117:
            r11 = r4
        L_0x0118:
            com.android.systemui.statusbar.notification.collection.NotificationEntry r11 = (com.android.systemui.statusbar.notification.collection.NotificationEntry) r11
            com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger r10 = r0.mLogger
            int r12 = r6.size()
            int r13 = r8.size()
            java.util.Objects.requireNonNull(r10)
            boolean r14 = r10.verbose
            if (r14 != 0) goto L_0x012c
            goto L_0x0144
        L_0x012c:
            com.android.systemui.log.LogBuffer r10 = r10.buffer
            com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$logEvaluatingGroup$2 r14 = com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$logEvaluatingGroup$2.INSTANCE
            java.util.Objects.requireNonNull(r10)
            boolean r15 = r10.frozen
            if (r15 != 0) goto L_0x0144
            com.android.systemui.log.LogMessageImpl r14 = r10.obtain(r9, r1, r14)
            r14.str1 = r7
            r14.int1 = r12
            r14.int2 = r13
            r10.push(r14)
        L_0x0144:
            if (r11 != 0) goto L_0x015c
            java.util.Iterator r6 = r6.iterator()
        L_0x014a:
            boolean r7 = r6.hasNext()
            if (r7 == 0) goto L_0x00d4
            java.lang.Object r7 = r6.next()
            com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$PostedEntry r7 = (com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator.PostedEntry) r7
            java.lang.String r8 = "logical-summary-missing"
            r0.handlePostedEntry(r7, r8)
            goto L_0x014a
        L_0x015c:
            java.util.LinkedHashMap<java.lang.String, com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$PostedEntry> r7 = r0.mPostedEntries
            java.lang.String r10 = r11.mKey
            java.lang.Object r7 = r7.get(r10)
            com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$PostedEntry r7 = (com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator.PostedEntry) r7
            r10 = 1
            r15 = 0
            if (r7 != 0) goto L_0x016c
            r7 = r4
            goto L_0x018d
        L_0x016c:
            boolean r12 = r7.shouldHeadsUpEver
            if (r12 == 0) goto L_0x0188
            boolean r12 = r7.wasAdded
            if (r12 != 0) goto L_0x0186
            boolean r12 = r7.shouldHeadsUpAgain
            if (r12 != 0) goto L_0x0186
            boolean r12 = r7.isAlerting
            if (r12 != 0) goto L_0x0183
            boolean r7 = r7.isBinding
            if (r7 == 0) goto L_0x0181
            goto L_0x0183
        L_0x0181:
            r7 = r15
            goto L_0x0184
        L_0x0183:
            r7 = r10
        L_0x0184:
            if (r7 == 0) goto L_0x0188
        L_0x0186:
            r7 = r10
            goto L_0x0189
        L_0x0188:
            r7 = r15
        L_0x0189:
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)
        L_0x018d:
            if (r7 != 0) goto L_0x01a6
            com.android.systemui.statusbar.policy.HeadsUpManager r7 = r0.mHeadsUpManager
            java.lang.String r12 = r11.getKey()
            boolean r7 = r7.isAlerting(r12)
            if (r7 != 0) goto L_0x01a4
            boolean r7 = r0.isEntryBinding(r11)
            if (r7 == 0) goto L_0x01a2
            goto L_0x01a4
        L_0x01a2:
            r7 = r15
            goto L_0x01aa
        L_0x01a4:
            r7 = r10
            goto L_0x01aa
        L_0x01a6:
            boolean r7 = r7.booleanValue()
        L_0x01aa:
            if (r7 != 0) goto L_0x01c2
            java.util.Iterator r6 = r6.iterator()
        L_0x01b0:
            boolean r7 = r6.hasNext()
            if (r7 == 0) goto L_0x00d4
            java.lang.Object r7 = r6.next()
            com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$PostedEntry r7 = (com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator.PostedEntry) r7
            java.lang.String r8 = "logical-summary-not-alerting"
            r0.handlePostedEntry(r7, r8)
            goto L_0x01b0
        L_0x01c2:
            com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$onBeforeFinalizeFilter$1$3 r7 = new com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$onBeforeFinalizeFilter$1$3
            java.lang.Object r12 = r2.getValue()
            java.util.Map r12 = (java.util.Map) r12
            r7.<init>(r12)
            kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1 r12 = new kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1
            r12.<init>(r6)
            com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$findAlertOverride$1 r13 = com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$findAlertOverride$1.INSTANCE
            kotlin.sequences.FilteringSequence r12 = kotlin.sequences.SequencesKt___SequencesKt.filter(r12, r13)
            com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$findAlertOverride$$inlined$sortedBy$1 r13 = new com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$findAlertOverride$$inlined$sortedBy$1
            r13.<init>()
            java.util.ArrayList r12 = kotlin.sequences.SequencesKt___SequencesKt.toMutableList(r12)
            kotlin.collections.CollectionsKt__MutableCollectionsJVMKt.sortWith(r12, r13)
            java.util.Iterator r12 = r12.iterator()
            boolean r13 = r12.hasNext()
            if (r13 != 0) goto L_0x01f0
            r12 = r4
            goto L_0x01f4
        L_0x01f0:
            java.lang.Object r12 = r12.next()
        L_0x01f4:
            com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$PostedEntry r12 = (com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator.PostedEntry) r12
            if (r12 != 0) goto L_0x01f9
            goto L_0x021a
        L_0x01f9:
            com.android.systemui.statusbar.notification.collection.NotificationEntry r12 = r12.entry
            java.util.Objects.requireNonNull(r12)
            java.lang.String r13 = r12.mKey
            java.lang.Object r7 = r7.invoke(r13)
            com.android.systemui.statusbar.notification.collection.coordinator.GroupLocation r13 = com.android.systemui.statusbar.notification.collection.coordinator.GroupLocation.Isolated
            if (r7 != r13) goto L_0x0216
            android.service.notification.StatusBarNotification r7 = r12.mSbn
            android.app.Notification r7 = r7.getNotification()
            int r7 = r7.getGroupAlertBehavior()
            if (r7 != r10) goto L_0x0216
            r7 = r10
            goto L_0x0217
        L_0x0216:
            r7 = r15
        L_0x0217:
            if (r7 == 0) goto L_0x021a
            goto L_0x021b
        L_0x021a:
            r12 = r4
        L_0x021b:
            if (r12 == 0) goto L_0x0220
            java.lang.String r7 = "alertOverride"
            goto L_0x0223
        L_0x0220:
            java.lang.String r7 = "undefined"
        L_0x0223:
            java.lang.Object r13 = r2.getValue()
            java.util.Map r13 = (java.util.Map) r13
            java.lang.String r14 = r11.mKey
            boolean r13 = r13.containsKey(r14)
            if (r13 != 0) goto L_0x0283
            if (r12 != 0) goto L_0x0283
            com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$onBeforeFinalizeFilter$1$4 r12 = new com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$onBeforeFinalizeFilter$1$4
            java.lang.Object r14 = r2.getValue()
            java.util.Map r14 = (java.util.Map) r14
            r12.<init>(r14)
            kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1 r14 = new kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1
            r14.<init>(r8)
            com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$findBestTransferChild$1 r8 = com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$findBestTransferChild$1.INSTANCE
            kotlin.sequences.FilteringSequence r8 = kotlin.sequences.SequencesKt___SequencesKt.filter(r14, r8)
            com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$findBestTransferChild$2 r14 = new com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$findBestTransferChild$2
            r14.<init>(r12)
            kotlin.sequences.FilteringSequence r8 = kotlin.sequences.SequencesKt___SequencesKt.filter(r8, r14)
            r12 = 2
            kotlin.jvm.functions.Function1[] r12 = new kotlin.jvm.functions.Function1[r12]
            com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$findBestTransferChild$3 r14 = new com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$findBestTransferChild$3
            r14.<init>(r0)
            r12[r15] = r14
            com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$findBestTransferChild$4 r14 = com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$findBestTransferChild$4.INSTANCE
            r12[r10] = r14
            kotlin.comparisons.ComparisonsKt__ComparisonsKt$compareBy$1 r14 = new kotlin.comparisons.ComparisonsKt__ComparisonsKt$compareBy$1
            r14.<init>(r12)
            java.util.ArrayList r8 = kotlin.sequences.SequencesKt___SequencesKt.toMutableList(r8)
            kotlin.collections.CollectionsKt__MutableCollectionsJVMKt.sortWith(r8, r14)
            java.util.Iterator r8 = r8.iterator()
            boolean r12 = r8.hasNext()
            if (r12 != 0) goto L_0x0278
            r8 = r4
            goto L_0x027c
        L_0x0278:
            java.lang.Object r8 = r8.next()
        L_0x027c:
            r12 = r8
            com.android.systemui.statusbar.notification.collection.NotificationEntry r12 = (com.android.systemui.statusbar.notification.collection.NotificationEntry) r12
            if (r12 == 0) goto L_0x0283
            java.lang.String r7 = "bestChild"
        L_0x0283:
            r8 = r12
            if (r8 != 0) goto L_0x029c
            java.util.Iterator r6 = r6.iterator()
        L_0x028a:
            boolean r7 = r6.hasNext()
            if (r7 == 0) goto L_0x00d4
            java.lang.Object r7 = r6.next()
            com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$PostedEntry r7 = (com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator.PostedEntry) r7
            java.lang.String r8 = "no-transfer-target"
            r0.handlePostedEntry(r7, r8)
            goto L_0x028a
        L_0x029c:
            java.util.LinkedHashMap<java.lang.String, com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$PostedEntry> r12 = r0.mPostedEntries
            java.lang.String r14 = r11.mKey
            java.lang.Object r12 = r12.get(r14)
            com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$PostedEntry r12 = (com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator.PostedEntry) r12
            if (r13 != 0) goto L_0x02e0
            if (r12 != 0) goto L_0x02ac
            r12 = r4
            goto L_0x02ae
        L_0x02ac:
            r12.shouldHeadsUpEver = r15
        L_0x02ae:
            if (r12 != 0) goto L_0x02d8
            com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$PostedEntry r20 = new com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$PostedEntry
            r14 = 0
            r16 = 0
            r17 = 0
            r18 = 0
            com.android.systemui.statusbar.policy.HeadsUpManager r12 = r0.mHeadsUpManager
            java.lang.String r13 = r11.mKey
            boolean r19 = r12.isAlerting(r13)
            boolean r21 = r0.isEntryBinding(r11)
            r12 = r20
            r13 = r11
            r22 = r15
            r15 = r16
            r16 = r17
            r17 = r18
            r18 = r19
            r19 = r21
            r12.<init>(r13, r14, r15, r16, r17, r18, r19)
            goto L_0x02da
        L_0x02d8:
            r22 = r15
        L_0x02da:
            java.lang.String r13 = "detached-summary-remove-alert"
            r0.handlePostedEntry(r12, r13)
            goto L_0x0308
        L_0x02e0:
            r22 = r15
            if (r12 == 0) goto L_0x0308
            com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger r13 = r0.mLogger
            java.util.Objects.requireNonNull(r13)
            boolean r14 = r13.verbose
            if (r14 != 0) goto L_0x02ee
            goto L_0x0308
        L_0x02ee:
            com.android.systemui.log.LogBuffer r13 = r13.buffer
            com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$logPostedEntryWillNotEvaluate$2 r14 = com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$logPostedEntryWillNotEvaluate$2.INSTANCE
            java.util.Objects.requireNonNull(r13)
            boolean r15 = r13.frozen
            if (r15 != 0) goto L_0x0308
            com.android.systemui.log.LogMessageImpl r14 = r13.obtain(r9, r1, r14)
            java.lang.String r12 = r12.key
            r14.str1 = r12
            java.lang.String r12 = "attached-summary-transferred"
            r14.str2 = r12
            r13.push(r14)
        L_0x0308:
            kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1 r12 = new kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1
            r12.<init>(r6)
            com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$onBeforeFinalizeFilter$1$6 r6 = new com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$onBeforeFinalizeFilter$1$6
            r6.<init>(r11)
            kotlin.sequences.FilteringSequence r6 = kotlin.sequences.SequencesKt___SequencesKt.filter(r12, r6)
            kotlin.sequences.FilteringSequence$iterator$1 r11 = new kotlin.sequences.FilteringSequence$iterator$1
            r11.<init>(r6)
            r15 = r22
        L_0x031d:
            boolean r6 = r11.hasNext()
            if (r6 == 0) goto L_0x034b
            java.lang.Object r6 = r11.next()
            com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$PostedEntry r6 = (com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator.PostedEntry) r6
            java.lang.String r12 = r8.mKey
            java.util.Objects.requireNonNull(r6)
            java.lang.String r13 = r6.key
            boolean r12 = kotlin.jvm.internal.Intrinsics.areEqual(r12, r13)
            if (r12 == 0) goto L_0x0345
            r6.shouldHeadsUpEver = r10
            r6.shouldHeadsUpAgain = r10
            java.lang.String r12 = "child-alert-transfer-target-"
            java.lang.String r12 = kotlin.jvm.internal.Intrinsics.stringPlus(r12, r7)
            r0.handlePostedEntry(r6, r12)
            r15 = r10
            goto L_0x031d
        L_0x0345:
            java.lang.String r12 = "child-alert-non-target"
            r0.handlePostedEntry(r6, r12)
            goto L_0x031d
        L_0x034b:
            if (r15 != 0) goto L_0x00d4
            com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$PostedEntry r6 = new com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$PostedEntry
            r18 = 0
            r19 = 0
            r20 = 1
            r21 = 1
            com.android.systemui.statusbar.policy.HeadsUpManager r10 = r0.mHeadsUpManager
            java.lang.String r11 = r8.mKey
            boolean r22 = r10.isAlerting(r11)
            boolean r23 = r0.isEntryBinding(r8)
            r16 = r6
            r17 = r8
            r16.<init>(r17, r18, r19, r20, r21, r22, r23)
            java.lang.String r8 = "non-posted-child-alert-transfer-target-"
            java.lang.String r7 = kotlin.jvm.internal.Intrinsics.stringPlus(r8, r7)
            r0.handlePostedEntry(r6, r7)
            goto L_0x00d4
        L_0x0375:
            java.util.LinkedHashMap<java.lang.String, com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$PostedEntry> r0 = r0.mPostedEntries
            r0.clear()
        L_0x037a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$attach$2.onBeforeFinalizeFilter(java.util.List):void");
    }
}
