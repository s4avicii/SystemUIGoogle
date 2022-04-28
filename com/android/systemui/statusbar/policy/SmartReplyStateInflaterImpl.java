package com.android.systemui.statusbar.policy;

import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.DevicePolicyManagerWrapper;
import com.android.systemui.shared.system.PackageManagerWrapper;

/* compiled from: SmartReplyStateInflater.kt */
public final class SmartReplyStateInflaterImpl implements SmartReplyStateInflater {
    public final ActivityManagerWrapper activityManagerWrapper;
    public final SmartReplyConstants constants;
    public final DevicePolicyManagerWrapper devicePolicyManagerWrapper;
    public final PackageManagerWrapper packageManagerWrapper;
    public final SmartActionInflater smartActionsInflater;
    public final SmartReplyInflater smartRepliesInflater;

    /* JADX WARNING: Removed duplicated region for block: B:106:0x01b3  */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x01b8  */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x01bc  */
    /* JADX WARNING: Removed duplicated region for block: B:115:0x00fb A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x0189 A[EDGE_INSN: B:118:0x0189->B:91:0x0189 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x01bf A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0078  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0096  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x009c  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00ed  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00f2  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x014b  */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x016e  */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x018c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.android.systemui.statusbar.policy.InflatedSmartReplyState inflateSmartReplyState(com.android.systemui.statusbar.notification.collection.NotificationEntry r18) {
        /*
            r17 = this;
            r1 = r17
            r0 = r18
            android.service.notification.StatusBarNotification r2 = r0.mSbn
            android.app.Notification r2 = r2.getNotification()
            r3 = 0
            android.util.Pair r4 = r2.findRemoteInputActionPair(r3)
            r5 = 1
            android.util.Pair r6 = r2.findRemoteInputActionPair(r5)
            com.android.systemui.statusbar.policy.SmartReplyConstants r7 = r1.constants
            java.util.Objects.requireNonNull(r7)
            boolean r7 = r7.mEnabled
            r8 = 0
            if (r7 != 0) goto L_0x003a
            boolean r1 = com.android.systemui.statusbar.policy.SmartReplyStateInflaterKt.DEBUG
            if (r1 == 0) goto L_0x0033
            android.service.notification.StatusBarNotification r0 = r0.mSbn
            java.lang.String r0 = r0.getKey()
            java.lang.String r1 = "Smart suggestions not enabled, not adding suggestions for "
            java.lang.String r0 = kotlin.jvm.internal.Intrinsics.stringPlus(r1, r0)
            java.lang.String r1 = "SmartReplyViewInflater"
            android.util.Log.d(r1, r0)
        L_0x0033:
            com.android.systemui.statusbar.policy.InflatedSmartReplyState r0 = new com.android.systemui.statusbar.policy.InflatedSmartReplyState
            r0.<init>(r8, r8, r8, r3)
            goto L_0x01cc
        L_0x003a:
            com.android.systemui.statusbar.policy.SmartReplyConstants r7 = r1.constants
            java.util.Objects.requireNonNull(r7)
            boolean r7 = r7.mRequiresTargetingP
            if (r7 == 0) goto L_0x004c
            int r7 = r0.targetSdk
            r9 = 28
            if (r7 < r9) goto L_0x004a
            goto L_0x004c
        L_0x004a:
            r7 = r3
            goto L_0x004d
        L_0x004c:
            r7 = r5
        L_0x004d:
            java.util.List r9 = r2.getContextualActions()
            if (r7 == 0) goto L_0x008e
            if (r4 != 0) goto L_0x0056
            goto L_0x008e
        L_0x0056:
            java.lang.Object r7 = r4.second
            android.app.Notification$Action r7 = (android.app.Notification.Action) r7
            android.app.PendingIntent r7 = r7.actionIntent
            if (r7 != 0) goto L_0x005f
            goto L_0x008e
        L_0x005f:
            java.lang.Object r10 = r4.first
            android.app.RemoteInput r10 = (android.app.RemoteInput) r10
            java.lang.CharSequence[] r10 = r10.getChoices()
            if (r10 != 0) goto L_0x006a
            goto L_0x0075
        L_0x006a:
            int r10 = r10.length
            if (r10 != 0) goto L_0x006f
            r10 = r5
            goto L_0x0070
        L_0x006f:
            r10 = r3
        L_0x0070:
            r10 = r10 ^ r5
            if (r10 != r5) goto L_0x0075
            r10 = r5
            goto L_0x0076
        L_0x0075:
            r10 = r3
        L_0x0076:
            if (r10 == 0) goto L_0x008e
            com.android.systemui.statusbar.policy.SmartReplyView$SmartReplies r10 = new com.android.systemui.statusbar.policy.SmartReplyView$SmartReplies
            java.lang.Object r11 = r4.first
            android.app.RemoteInput r11 = (android.app.RemoteInput) r11
            java.lang.CharSequence[] r11 = r11.getChoices()
            java.util.List r11 = java.util.Arrays.asList(r11)
            java.lang.Object r4 = r4.first
            android.app.RemoteInput r4 = (android.app.RemoteInput) r4
            r10.<init>(r11, r4, r7, r3)
            goto L_0x008f
        L_0x008e:
            r10 = r8
        L_0x008f:
            boolean r4 = r9.isEmpty()
            r4 = r4 ^ r5
            if (r4 == 0) goto L_0x009c
            com.android.systemui.statusbar.policy.SmartReplyView$SmartActions r4 = new com.android.systemui.statusbar.policy.SmartReplyView$SmartActions
            r4.<init>(r9, r3)
            goto L_0x009d
        L_0x009c:
            r4 = r8
        L_0x009d:
            if (r10 != 0) goto L_0x0155
            if (r4 != 0) goto L_0x0155
            android.service.notification.NotificationListenerService$Ranking r7 = r0.mRanking
            java.util.List r7 = r7.getSmartReplies()
            android.service.notification.NotificationListenerService$Ranking r0 = r0.mRanking
            java.util.List r0 = r0.getSmartActions()
            boolean r9 = r7.isEmpty()
            r9 = r9 ^ r5
            if (r9 == 0) goto L_0x00d1
            if (r6 == 0) goto L_0x00d1
            java.lang.Object r9 = r6.second
            android.app.Notification$Action r9 = (android.app.Notification.Action) r9
            boolean r9 = r9.getAllowGeneratedReplies()
            if (r9 == 0) goto L_0x00d1
            java.lang.Object r9 = r6.second
            android.app.Notification$Action r9 = (android.app.Notification.Action) r9
            android.app.PendingIntent r9 = r9.actionIntent
            if (r9 == 0) goto L_0x00d1
            com.android.systemui.statusbar.policy.SmartReplyView$SmartReplies r10 = new com.android.systemui.statusbar.policy.SmartReplyView$SmartReplies
            java.lang.Object r6 = r6.first
            android.app.RemoteInput r6 = (android.app.RemoteInput) r6
            r10.<init>(r7, r6, r9, r5)
        L_0x00d1:
            boolean r6 = r0.isEmpty()
            r6 = r6 ^ r5
            if (r6 == 0) goto L_0x0155
            boolean r6 = r2.getAllowSystemGeneratedContextualActions()
            if (r6 == 0) goto L_0x0155
            com.android.systemui.shared.system.ActivityManagerWrapper r4 = r1.activityManagerWrapper
            java.util.Objects.requireNonNull(r4)
            android.app.IActivityTaskManager r4 = android.app.ActivityTaskManager.getService()     // Catch:{ RemoteException -> 0x00ef }
            int r4 = r4.getLockTaskModeState()     // Catch:{ RemoteException -> 0x00ef }
            if (r4 != r5) goto L_0x00ef
            r4 = r5
            goto L_0x00f0
        L_0x00ef:
            r4 = r3
        L_0x00f0:
            if (r4 == 0) goto L_0x0150
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            java.util.Iterator r6 = r0.iterator()
        L_0x00fb:
            boolean r0 = r6.hasNext()
            if (r0 == 0) goto L_0x014f
            java.lang.Object r7 = r6.next()
            r0 = r7
            android.app.Notification$Action r0 = (android.app.Notification.Action) r0
            android.app.PendingIntent r0 = r0.actionIntent
            if (r0 != 0) goto L_0x010d
            goto L_0x0138
        L_0x010d:
            android.content.Intent r12 = r0.getIntent()
            if (r12 != 0) goto L_0x0114
            goto L_0x0138
        L_0x0114:
            com.android.systemui.shared.system.PackageManagerWrapper r0 = r1.packageManagerWrapper
            java.util.Objects.requireNonNull(r0)
            android.app.Application r0 = android.app.AppGlobals.getInitialApplication()
            android.content.ContentResolver r0 = r0.getContentResolver()
            java.lang.String r13 = r12.resolveTypeIfNeeded(r0)
            android.content.pm.IPackageManager r11 = com.android.systemui.shared.system.PackageManagerWrapper.mIPackageManager     // Catch:{ RemoteException -> 0x0131 }
            long r14 = (long) r3     // Catch:{ RemoteException -> 0x0131 }
            int r16 = android.os.UserHandle.getCallingUserId()     // Catch:{ RemoteException -> 0x0131 }
            android.content.pm.ResolveInfo r0 = r11.resolveIntent(r12, r13, r14, r16)     // Catch:{ RemoteException -> 0x0131 }
            goto L_0x0136
        L_0x0131:
            r0 = move-exception
            r0.printStackTrace()
            r0 = r8
        L_0x0136:
            if (r0 != 0) goto L_0x013a
        L_0x0138:
            r0 = r3
            goto L_0x0149
        L_0x013a:
            com.android.systemui.shared.system.DevicePolicyManagerWrapper r9 = r1.devicePolicyManagerWrapper
            android.content.pm.ActivityInfo r0 = r0.activityInfo
            java.lang.String r0 = r0.packageName
            java.util.Objects.requireNonNull(r9)
            android.app.admin.DevicePolicyManager r9 = com.android.systemui.shared.system.DevicePolicyManagerWrapper.sDevicePolicyManager
            boolean r0 = r9.isLockTaskPermitted(r0)
        L_0x0149:
            if (r0 == 0) goto L_0x00fb
            r4.add(r7)
            goto L_0x00fb
        L_0x014f:
            r0 = r4
        L_0x0150:
            com.android.systemui.statusbar.policy.SmartReplyView$SmartActions r4 = new com.android.systemui.statusbar.policy.SmartReplyView$SmartActions
            r4.<init>(r0, r5)
        L_0x0155:
            if (r4 != 0) goto L_0x0158
            goto L_0x0189
        L_0x0158:
            java.util.List<android.app.Notification$Action> r0 = r4.actions
            if (r0 != 0) goto L_0x015d
            goto L_0x0189
        L_0x015d:
            boolean r1 = r0.isEmpty()
            if (r1 == 0) goto L_0x0164
            goto L_0x0189
        L_0x0164:
            java.util.Iterator r0 = r0.iterator()
        L_0x0168:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0189
            java.lang.Object r1 = r0.next()
            android.app.Notification$Action r1 = (android.app.Notification.Action) r1
            boolean r6 = r1.isContextual()
            if (r6 == 0) goto L_0x0184
            int r1 = r1.getSemanticAction()
            r6 = 12
            if (r1 != r6) goto L_0x0184
            r1 = r5
            goto L_0x0185
        L_0x0184:
            r1 = r3
        L_0x0185:
            if (r1 == 0) goto L_0x0168
            r0 = r5
            goto L_0x018a
        L_0x0189:
            r0 = r3
        L_0x018a:
            if (r0 == 0) goto L_0x01c6
            android.app.Notification$Action[] r1 = r2.actions
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            int r6 = r1.length
            r7 = r3
            r9 = r7
        L_0x0196:
            if (r7 >= r6) goto L_0x01c1
            r11 = r1[r7]
            int r7 = r7 + 1
            int r12 = r9 + 1
            android.app.RemoteInput[] r11 = r11.getRemoteInputs()
            if (r11 != 0) goto L_0x01a5
            goto L_0x01b0
        L_0x01a5:
            int r11 = r11.length
            if (r11 != 0) goto L_0x01aa
            r11 = r5
            goto L_0x01ab
        L_0x01aa:
            r11 = r3
        L_0x01ab:
            r11 = r11 ^ r5
            if (r11 != r5) goto L_0x01b0
            r11 = r5
            goto L_0x01b1
        L_0x01b0:
            r11 = r3
        L_0x01b1:
            if (r11 == 0) goto L_0x01b8
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)
            goto L_0x01b9
        L_0x01b8:
            r9 = r8
        L_0x01b9:
            if (r9 != 0) goto L_0x01bc
            goto L_0x01bf
        L_0x01bc:
            r2.add(r9)
        L_0x01bf:
            r9 = r12
            goto L_0x0196
        L_0x01c1:
            com.android.systemui.statusbar.policy.InflatedSmartReplyState$SuppressedActions r8 = new com.android.systemui.statusbar.policy.InflatedSmartReplyState$SuppressedActions
            r8.<init>(r2)
        L_0x01c6:
            com.android.systemui.statusbar.policy.InflatedSmartReplyState r1 = new com.android.systemui.statusbar.policy.InflatedSmartReplyState
            r1.<init>(r10, r4, r8, r0)
            r0 = r1
        L_0x01cc:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl.inflateSmartReplyState(com.android.systemui.statusbar.notification.collection.NotificationEntry):com.android.systemui.statusbar.policy.InflatedSmartReplyState");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:50:0x007d, code lost:
        if (com.android.systemui.statusbar.NotificationUiAdjustment.areDifferent(r0, r3) == false) goto L_0x007f;
     */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00ae  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00b4  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00b6  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00cf  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00d7  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x0103  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.android.systemui.statusbar.policy.InflatedSmartReplyViewHolder inflateSmartReplyViewHolder(android.content.Context r15, android.content.Context r16, com.android.systemui.statusbar.notification.collection.NotificationEntry r17, com.android.systemui.statusbar.policy.InflatedSmartReplyState r18, com.android.systemui.statusbar.policy.InflatedSmartReplyState r19) {
        /*
            r14 = this;
            r0 = r18
            r6 = r17
            r7 = r19
            boolean r1 = com.android.systemui.statusbar.policy.SmartReplyStateInflaterKt.shouldShowSmartReplyView(r6, r7)
            r8 = 0
            if (r1 != 0) goto L_0x0013
            com.android.systemui.statusbar.policy.InflatedSmartReplyViewHolder r0 = new com.android.systemui.statusbar.policy.InflatedSmartReplyViewHolder
            r0.<init>(r8, r8)
            return r0
        L_0x0013:
            r1 = 0
            r2 = 1
            if (r0 != r7) goto L_0x0019
            goto L_0x007f
        L_0x0019:
            if (r0 == 0) goto L_0x0081
            boolean r3 = r0.hasPhishingAction
            boolean r4 = r7.hasPhishingAction
            if (r3 == r4) goto L_0x0023
            goto L_0x0081
        L_0x0023:
            com.android.systemui.statusbar.policy.SmartReplyView$SmartReplies r3 = r0.smartReplies
            if (r3 != 0) goto L_0x0029
            r3 = r8
            goto L_0x002b
        L_0x0029:
            java.util.List<java.lang.CharSequence> r3 = r3.choices
        L_0x002b:
            if (r3 != 0) goto L_0x002f
            kotlin.collections.EmptyList r3 = kotlin.collections.EmptyList.INSTANCE
        L_0x002f:
            com.android.systemui.statusbar.policy.SmartReplyView$SmartReplies r4 = r7.smartReplies
            if (r4 != 0) goto L_0x0035
            r4 = r8
            goto L_0x0037
        L_0x0035:
            java.util.List<java.lang.CharSequence> r4 = r4.choices
        L_0x0037:
            if (r4 != 0) goto L_0x003b
            kotlin.collections.EmptyList r4 = kotlin.collections.EmptyList.INSTANCE
        L_0x003b:
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual(r3, r4)
            if (r3 != 0) goto L_0x0042
            goto L_0x0081
        L_0x0042:
            com.android.systemui.statusbar.policy.InflatedSmartReplyState$SuppressedActions r3 = r0.suppressedActions
            if (r3 != 0) goto L_0x0048
            r3 = r8
            goto L_0x004a
        L_0x0048:
            java.util.List<java.lang.Integer> r3 = r3.suppressedActionIndices
        L_0x004a:
            if (r3 != 0) goto L_0x004e
            kotlin.collections.EmptyList r3 = kotlin.collections.EmptyList.INSTANCE
        L_0x004e:
            com.android.systemui.statusbar.policy.InflatedSmartReplyState$SuppressedActions r4 = r7.suppressedActions
            if (r4 != 0) goto L_0x0054
            r4 = r8
            goto L_0x0056
        L_0x0054:
            java.util.List<java.lang.Integer> r4 = r4.suppressedActionIndices
        L_0x0056:
            if (r4 != 0) goto L_0x005a
            kotlin.collections.EmptyList r4 = kotlin.collections.EmptyList.INSTANCE
        L_0x005a:
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual(r3, r4)
            if (r3 != 0) goto L_0x0061
            goto L_0x0081
        L_0x0061:
            com.android.systemui.statusbar.policy.SmartReplyView$SmartActions r0 = r0.smartActions
            if (r0 != 0) goto L_0x0067
            r0 = r8
            goto L_0x0069
        L_0x0067:
            java.util.List<android.app.Notification$Action> r0 = r0.actions
        L_0x0069:
            if (r0 != 0) goto L_0x006d
            kotlin.collections.EmptyList r0 = kotlin.collections.EmptyList.INSTANCE
        L_0x006d:
            com.android.systemui.statusbar.policy.SmartReplyView$SmartActions r3 = r7.smartActions
            if (r3 != 0) goto L_0x0073
            r3 = r8
            goto L_0x0075
        L_0x0073:
            java.util.List<android.app.Notification$Action> r3 = r3.actions
        L_0x0075:
            if (r3 != 0) goto L_0x0079
            kotlin.collections.EmptyList r3 = kotlin.collections.EmptyList.INSTANCE
        L_0x0079:
            boolean r0 = com.android.systemui.statusbar.NotificationUiAdjustment.areDifferent(r0, r3)
            if (r0 != 0) goto L_0x0081
        L_0x007f:
            r0 = r2
            goto L_0x0082
        L_0x0081:
            r0 = r1
        L_0x0082:
            r9 = r0 ^ 1
            r10 = r14
            com.android.systemui.statusbar.policy.SmartReplyConstants r0 = r10.constants
            int r2 = com.android.systemui.statusbar.policy.SmartReplyView.MEASURE_SPEC_ANY_LENGTH
            android.view.LayoutInflater r2 = android.view.LayoutInflater.from(r15)
            r3 = 2131624491(0x7f0e022b, float:1.8876163E38)
            android.view.View r2 = r2.inflate(r3, r8)
            r11 = r2
            com.android.systemui.statusbar.policy.SmartReplyView r11 = (com.android.systemui.statusbar.policy.SmartReplyView) r11
            java.util.Objects.requireNonNull(r0)
            int r2 = r0.mMaxNumActions
            java.util.Objects.requireNonNull(r11)
            r11.mMaxNumActions = r2
            int r2 = r0.mMaxSqueezeRemeasureAttempts
            r11.mMaxSqueezeRemeasureAttempts = r2
            int r0 = r0.mMinNumSystemGeneratedReplies
            r11.mMinNumSystemGeneratedReplies = r0
            com.android.systemui.statusbar.policy.SmartReplyView$SmartReplies r4 = r7.smartReplies
            if (r4 != 0) goto L_0x00ae
            goto L_0x00b0
        L_0x00ae:
            boolean r1 = r4.fromAssistant
        L_0x00b0:
            r11.mSmartRepliesGeneratedByAssistant = r1
            if (r4 != 0) goto L_0x00b6
            r0 = r8
            goto L_0x00cd
        L_0x00b6:
            java.util.List<java.lang.CharSequence> r0 = r4.choices
            kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1 r12 = new kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1
            r12.<init>(r0)
            com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl$inflateSmartReplyViewHolder$smartReplyButtons$1$1 r13 = new com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl$inflateSmartReplyViewHolder$smartReplyButtons$1$1
            r0 = r13
            r1 = r14
            r2 = r11
            r3 = r17
            r5 = r9
            r0.<init>(r1, r2, r3, r4, r5)
            kotlin.sequences.TransformingIndexedSequence r0 = new kotlin.sequences.TransformingIndexedSequence
            r0.<init>(r12, r13)
        L_0x00cd:
            if (r0 != 0) goto L_0x00d1
            kotlin.sequences.EmptySequence r0 = kotlin.sequences.EmptySequence.INSTANCE
        L_0x00d1:
            r12 = r0
            com.android.systemui.statusbar.policy.SmartReplyView$SmartActions r4 = r7.smartActions
            if (r4 != 0) goto L_0x00d7
            goto L_0x0101
        L_0x00d7:
            android.view.ContextThemeWrapper r7 = new android.view.ContextThemeWrapper
            android.content.res.Resources$Theme r0 = r15.getTheme()
            r1 = r16
            r7.<init>(r1, r0)
            java.util.List<android.app.Notification$Action> r0 = r4.actions
            kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1 r1 = new kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1
            r1.<init>(r0)
            com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl$inflateSmartReplyViewHolder$smartActionButtons$1$1 r0 = com.android.systemui.statusbar.policy.C1642xa9afa0f0.INSTANCE
            kotlin.sequences.FilteringSequence r8 = kotlin.sequences.SequencesKt___SequencesKt.filter(r1, r0)
            com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl$inflateSmartReplyViewHolder$smartActionButtons$1$2 r13 = new com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl$inflateSmartReplyViewHolder$smartActionButtons$1$2
            r0 = r13
            r1 = r14
            r2 = r11
            r3 = r17
            r5 = r9
            r6 = r7
            r0.<init>(r1, r2, r3, r4, r5, r6)
            kotlin.sequences.TransformingIndexedSequence r0 = new kotlin.sequences.TransformingIndexedSequence
            r0.<init>(r8, r13)
            r8 = r0
        L_0x0101:
            if (r8 != 0) goto L_0x0105
            kotlin.sequences.EmptySequence r8 = kotlin.sequences.EmptySequence.INSTANCE
        L_0x0105:
            com.android.systemui.statusbar.policy.InflatedSmartReplyViewHolder r0 = new com.android.systemui.statusbar.policy.InflatedSmartReplyViewHolder
            kotlin.sequences.FlatteningSequence r1 = kotlin.sequences.SequencesKt___SequencesKt.plus(r12, r8)
            java.util.List r1 = kotlin.sequences.SequencesKt___SequencesKt.toList(r1)
            r0.<init>(r11, r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl.inflateSmartReplyViewHolder(android.content.Context, android.content.Context, com.android.systemui.statusbar.notification.collection.NotificationEntry, com.android.systemui.statusbar.policy.InflatedSmartReplyState, com.android.systemui.statusbar.policy.InflatedSmartReplyState):com.android.systemui.statusbar.policy.InflatedSmartReplyViewHolder");
    }

    public SmartReplyStateInflaterImpl(SmartReplyConstants smartReplyConstants, ActivityManagerWrapper activityManagerWrapper2, PackageManagerWrapper packageManagerWrapper2, DevicePolicyManagerWrapper devicePolicyManagerWrapper2, SmartReplyInflater smartReplyInflater, SmartActionInflater smartActionInflater) {
        this.constants = smartReplyConstants;
        this.activityManagerWrapper = activityManagerWrapper2;
        this.packageManagerWrapper = packageManagerWrapper2;
        this.devicePolicyManagerWrapper = devicePolicyManagerWrapper2;
        this.smartRepliesInflater = smartReplyInflater;
        this.smartActionsInflater = smartActionInflater;
    }
}
