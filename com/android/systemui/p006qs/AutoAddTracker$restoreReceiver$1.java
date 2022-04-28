package com.android.systemui.p006qs;

import android.content.BroadcastReceiver;

/* renamed from: com.android.systemui.qs.AutoAddTracker$restoreReceiver$1 */
/* compiled from: AutoAddTracker.kt */
public final class AutoAddTracker$restoreReceiver$1 extends BroadcastReceiver {
    public final /* synthetic */ AutoAddTracker this$0;

    public AutoAddTracker$restoreReceiver$1(AutoAddTracker autoAddTracker) {
        this.this$0 = autoAddTracker;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v0, resolved type: kotlin.collections.EmptyList} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: kotlin.Unit} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: kotlin.collections.EmptyList} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: kotlin.collections.EmptyList} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v14, resolved type: kotlin.Unit} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v15, resolved type: kotlin.collections.EmptyList} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v16, resolved type: java.util.List} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v17, resolved type: kotlin.collections.EmptySet} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v18, resolved type: java.util.Set} */
    /* JADX WARNING: type inference failed for: r1v10 */
    /* JADX WARNING: type inference failed for: r1v11, types: [java.util.Set<java.lang.String>] */
    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onReceive(android.content.Context r6, android.content.Intent r7) {
        /*
            r5 = this;
            java.lang.String r6 = r7.getAction()
            java.lang.String r0 = "android.os.action.SETTING_RESTORED"
            boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r0)
            if (r6 != 0) goto L_0x000d
            return
        L_0x000d:
            com.android.systemui.qs.AutoAddTracker r5 = r5.this$0
            java.util.Objects.requireNonNull(r5)
            java.lang.String r6 = "setting_name"
            java.lang.String r6 = r7.getStringExtra(r6)
            java.lang.String r0 = "sysui_qs_tiles"
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r0)
            r1 = 0
            if (r0 == 0) goto L_0x0053
            java.lang.String r6 = "new_value"
            java.lang.String r6 = r7.getStringExtra(r6)
            if (r6 != 0) goto L_0x002c
            goto L_0x003a
        L_0x002c:
            java.lang.String r7 = ","
            java.lang.String[] r7 = new java.lang.String[]{r7}
            java.util.List r6 = kotlin.text.StringsKt__StringsKt.split$default(r6, r7)
            java.util.Set r1 = kotlin.collections.CollectionsKt___CollectionsKt.toSet(r6)
        L_0x003a:
            if (r1 != 0) goto L_0x004f
            java.lang.String r6 = "AutoAddTracker"
            java.lang.String r7 = "Null restored tiles for user "
            int r0 = r5.userId
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            java.lang.String r7 = kotlin.jvm.internal.Intrinsics.stringPlus(r7, r0)
            android.util.Log.w(r6, r7)
            kotlin.collections.EmptySet r1 = kotlin.collections.EmptySet.INSTANCE
        L_0x004f:
            r5.restoredTiles = r1
            goto L_0x00fe
        L_0x0053:
            java.lang.String r0 = "qs_auto_tiles"
            boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r0)
            if (r6 == 0) goto L_0x00fe
            java.util.Set<java.lang.String> r6 = r5.restoredTiles
            if (r6 != 0) goto L_0x0062
            goto L_0x00e6
        L_0x0062:
            java.lang.String r0 = "new_value"
            java.lang.String r0 = r7.getStringExtra(r0)
            if (r0 != 0) goto L_0x006c
            r0 = r1
            goto L_0x0076
        L_0x006c:
            java.lang.String r2 = ","
            java.lang.String[] r2 = new java.lang.String[]{r2}
            java.util.List r0 = kotlin.text.StringsKt__StringsKt.split$default(r0, r2)
        L_0x0076:
            if (r0 != 0) goto L_0x007a
            kotlin.collections.EmptyList r0 = kotlin.collections.EmptyList.INSTANCE
        L_0x007a:
            java.lang.String r2 = "previous_value"
            java.lang.String r7 = r7.getStringExtra(r2)
            if (r7 != 0) goto L_0x0083
            goto L_0x008d
        L_0x0083:
            java.lang.String r1 = ","
            java.lang.String[] r1 = new java.lang.String[]{r1}
            java.util.List r1 = kotlin.text.StringsKt__StringsKt.split$default(r7, r1)
        L_0x008d:
            if (r1 != 0) goto L_0x0091
            kotlin.collections.EmptyList r1 = kotlin.collections.EmptyList.INSTANCE
        L_0x0091:
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            java.util.Iterator r2 = r0.iterator()
        L_0x009a:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x00b3
            java.lang.Object r3 = r2.next()
            r4 = r3
            java.lang.String r4 = (java.lang.String) r4
            boolean r4 = r6.contains(r4)
            r4 = r4 ^ 1
            if (r4 == 0) goto L_0x009a
            r7.add(r3)
            goto L_0x009a
        L_0x00b3:
            boolean r6 = r7.isEmpty()
            r6 = r6 ^ 1
            if (r6 == 0) goto L_0x00c0
            com.android.systemui.qs.QSHost r6 = r5.qsHost
            r6.removeTiles(r7)
        L_0x00c0:
            android.util.ArraySet<java.lang.String> r6 = r5.autoAdded
            monitor-enter(r6)
            android.util.ArraySet<java.lang.String> r7 = r5.autoAdded     // Catch:{ all -> 0x00fb }
            r7.clear()     // Catch:{ all -> 0x00fb }
            android.util.ArraySet<java.lang.String> r7 = r5.autoAdded     // Catch:{ all -> 0x00fb }
            java.util.ArrayList r0 = kotlin.collections.CollectionsKt___CollectionsKt.plus((java.util.List) r0, (java.util.List) r1)     // Catch:{ all -> 0x00fb }
            r7.addAll(r0)     // Catch:{ all -> 0x00fb }
            android.util.ArraySet<java.lang.String> r7 = r5.autoAdded     // Catch:{ all -> 0x00fb }
            java.lang.String r0 = ","
            java.lang.String r7 = android.text.TextUtils.join(r0, r7)     // Catch:{ all -> 0x00fb }
            monitor-exit(r6)
            com.android.systemui.util.settings.SecureSettings r6 = r5.secureSettings
            int r0 = r5.userId
            java.lang.String r1 = "qs_auto_tiles"
            r6.putStringForUser$1(r1, r7, r0)
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
        L_0x00e6:
            if (r1 != 0) goto L_0x00fe
            java.lang.String r6 = "AutoAddTracker"
            java.lang.String r7 = "qs_auto_tiles restored before sysui_qs_tiles for user "
            int r5 = r5.userId
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            java.lang.String r5 = kotlin.jvm.internal.Intrinsics.stringPlus(r7, r5)
            android.util.Log.w(r6, r5)
            goto L_0x00fe
        L_0x00fb:
            r5 = move-exception
            monitor-exit(r6)
            throw r5
        L_0x00fe:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.p006qs.AutoAddTracker$restoreReceiver$1.onReceive(android.content.Context, android.content.Intent):void");
    }
}
