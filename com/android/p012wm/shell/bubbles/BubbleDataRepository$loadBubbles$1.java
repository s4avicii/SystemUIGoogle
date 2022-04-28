package com.android.p012wm.shell.bubbles;

import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

@DebugMetadata(mo21073c = "com.android.wm.shell.bubbles.BubbleDataRepository$loadBubbles$1", mo21074f = "BubbleDataRepository.kt", mo21075l = {}, mo21076m = "invokeSuspend")
/* renamed from: com.android.wm.shell.bubbles.BubbleDataRepository$loadBubbles$1 */
/* compiled from: BubbleDataRepository.kt */
final class BubbleDataRepository$loadBubbles$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ Function1<List<? extends Bubble>, Unit> $cb;
    public final /* synthetic */ int $userId;
    public int label;
    public final /* synthetic */ BubbleDataRepository this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public BubbleDataRepository$loadBubbles$1(BubbleDataRepository bubbleDataRepository, int i, Function1<? super List<? extends Bubble>, Unit> function1, Continuation<? super BubbleDataRepository$loadBubbles$1> continuation) {
        super(2, continuation);
        this.this$0 = bubbleDataRepository;
        this.$userId = i;
        this.$cb = function1;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new BubbleDataRepository$loadBubbles$1(this.this$0, this.$userId, this.$cb, continuation);
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((BubbleDataRepository$loadBubbles$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Removed duplicated region for block: B:68:0x0171  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x010f A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r19) {
        /*
            r18 = this;
            r1 = r18
            int r0 = r1.label
            if (r0 != 0) goto L_0x0189
            kotlin.ResultKt.throwOnFailure(r19)
            com.android.wm.shell.bubbles.BubbleDataRepository r0 = r1.this$0
            com.android.wm.shell.bubbles.storage.BubblePersistentRepository r0 = r0.persistentRepository
            java.util.Objects.requireNonNull(r0)
            android.util.AtomicFile r2 = r0.bubbleFile
            monitor-enter(r2)
            android.util.AtomicFile r3 = r0.bubbleFile     // Catch:{ all -> 0x0186 }
            boolean r3 = r3.exists()     // Catch:{ all -> 0x0186 }
            r4 = 0
            if (r3 != 0) goto L_0x0023
            android.util.SparseArray r0 = new android.util.SparseArray     // Catch:{ all -> 0x0186 }
            r0.<init>()     // Catch:{ all -> 0x0186 }
            monitor-exit(r2)
            goto L_0x0049
        L_0x0023:
            android.util.AtomicFile r0 = r0.bubbleFile     // Catch:{ all -> 0x003b }
            java.io.FileInputStream r3 = r0.openRead()     // Catch:{ all -> 0x003b }
            android.util.SparseArray r0 = com.android.p012wm.shell.bubbles.storage.BubbleXmlHelperKt.readXml(r3)     // Catch:{ all -> 0x0032 }
            kotlin.p018io.CloseableKt.closeFinally(r3, r4)     // Catch:{ all -> 0x003b }
            monitor-exit(r2)
            goto L_0x0049
        L_0x0032:
            r0 = move-exception
            r5 = r0
            throw r5     // Catch:{ all -> 0x0035 }
        L_0x0035:
            r0 = move-exception
            r6 = r0
            kotlin.p018io.CloseableKt.closeFinally(r3, r5)     // Catch:{ all -> 0x003b }
            throw r6     // Catch:{ all -> 0x003b }
        L_0x003b:
            r0 = move-exception
            java.lang.String r3 = "BubblePersistentRepository"
            java.lang.String r5 = "Failed to open bubble file"
            android.util.Log.e(r3, r5, r0)     // Catch:{ all -> 0x0186 }
            android.util.SparseArray r0 = new android.util.SparseArray     // Catch:{ all -> 0x0186 }
            r0.<init>()     // Catch:{ all -> 0x0186 }
            monitor-exit(r2)
        L_0x0049:
            int r2 = r1.$userId
            java.lang.Object r0 = r0.get(r2)
            java.util.List r0 = (java.util.List) r0
            if (r0 != 0) goto L_0x0056
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x0056:
            com.android.wm.shell.bubbles.BubbleDataRepository r2 = r1.this$0
            com.android.wm.shell.bubbles.storage.BubbleVolatileRepository r2 = r2.volatileRepository
            int r3 = r1.$userId
            r2.addBubbles(r3, r0)
            java.util.ArrayList r2 = new java.util.ArrayList
            r3 = 10
            int r3 = kotlin.collections.CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(r0, r3)
            r2.<init>(r3)
            java.util.Iterator r3 = r0.iterator()
        L_0x006e:
            boolean r5 = r3.hasNext()
            if (r5 == 0) goto L_0x008a
            java.lang.Object r5 = r3.next()
            com.android.wm.shell.bubbles.storage.BubbleEntity r5 = (com.android.p012wm.shell.bubbles.storage.BubbleEntity) r5
            com.android.wm.shell.bubbles.ShortcutKey r6 = new com.android.wm.shell.bubbles.ShortcutKey
            java.util.Objects.requireNonNull(r5)
            int r7 = r5.userId
            java.lang.String r5 = r5.packageName
            r6.<init>(r7, r5)
            r2.add(r6)
            goto L_0x006e
        L_0x008a:
            java.util.Set r2 = kotlin.collections.CollectionsKt___CollectionsKt.toSet(r2)
            com.android.wm.shell.bubbles.BubbleDataRepository r3 = r1.this$0
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            java.util.Iterator r2 = r2.iterator()
        L_0x0099:
            boolean r6 = r2.hasNext()
            if (r6 == 0) goto L_0x00cd
            java.lang.Object r6 = r2.next()
            com.android.wm.shell.bubbles.ShortcutKey r6 = (com.android.p012wm.shell.bubbles.ShortcutKey) r6
            android.content.pm.LauncherApps r7 = r3.launcherApps
            android.content.pm.LauncherApps$ShortcutQuery r8 = new android.content.pm.LauncherApps$ShortcutQuery
            r8.<init>()
            java.util.Objects.requireNonNull(r6)
            java.lang.String r9 = r6.pkg
            android.content.pm.LauncherApps$ShortcutQuery r8 = r8.setPackage(r9)
            r9 = 1041(0x411, float:1.459E-42)
            android.content.pm.LauncherApps$ShortcutQuery r8 = r8.setQueryFlags(r9)
            int r6 = r6.userId
            android.os.UserHandle r6 = android.os.UserHandle.of(r6)
            java.util.List r6 = r7.getShortcuts(r8, r6)
            if (r6 != 0) goto L_0x00c9
            kotlin.collections.EmptyList r6 = kotlin.collections.EmptyList.INSTANCE
        L_0x00c9:
            kotlin.collections.CollectionsKt__ReversedViewsKt.addAll((java.util.Collection) r5, (java.util.Collection) r6)
            goto L_0x0099
        L_0x00cd:
            java.util.LinkedHashMap r2 = new java.util.LinkedHashMap
            r2.<init>()
            java.util.Iterator r3 = r5.iterator()
        L_0x00d6:
            boolean r5 = r3.hasNext()
            if (r5 == 0) goto L_0x0104
            java.lang.Object r5 = r3.next()
            r6 = r5
            android.content.pm.ShortcutInfo r6 = (android.content.pm.ShortcutInfo) r6
            com.android.wm.shell.bubbles.ShortcutKey r7 = new com.android.wm.shell.bubbles.ShortcutKey
            int r8 = r6.getUserId()
            java.lang.String r6 = r6.getPackage()
            r7.<init>(r8, r6)
            java.lang.Object r6 = r2.get(r7)
            if (r6 != 0) goto L_0x00fe
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            r2.put(r7, r6)
        L_0x00fe:
            java.util.List r6 = (java.util.List) r6
            r6.add(r5)
            goto L_0x00d6
        L_0x0104:
            com.android.wm.shell.bubbles.BubbleDataRepository r3 = r1.this$0
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            java.util.Iterator r0 = r0.iterator()
        L_0x010f:
            boolean r6 = r0.hasNext()
            if (r6 == 0) goto L_0x0175
            java.lang.Object r6 = r0.next()
            com.android.wm.shell.bubbles.storage.BubbleEntity r6 = (com.android.p012wm.shell.bubbles.storage.BubbleEntity) r6
            com.android.wm.shell.bubbles.ShortcutKey r7 = new com.android.wm.shell.bubbles.ShortcutKey
            java.util.Objects.requireNonNull(r6)
            int r8 = r6.userId
            java.lang.String r9 = r6.packageName
            r7.<init>(r8, r9)
            java.lang.Object r7 = r2.get(r7)
            java.util.List r7 = (java.util.List) r7
            if (r7 != 0) goto L_0x0130
            goto L_0x0154
        L_0x0130:
            java.util.Iterator r7 = r7.iterator()
        L_0x0134:
            boolean r8 = r7.hasNext()
            if (r8 == 0) goto L_0x014e
            java.lang.Object r8 = r7.next()
            r9 = r8
            android.content.pm.ShortcutInfo r9 = (android.content.pm.ShortcutInfo) r9
            java.lang.String r10 = r6.shortcutId
            java.lang.String r9 = r9.getId()
            boolean r9 = kotlin.jvm.internal.Intrinsics.areEqual(r10, r9)
            if (r9 == 0) goto L_0x0134
            goto L_0x014f
        L_0x014e:
            r8 = r4
        L_0x014f:
            r11 = r8
            android.content.pm.ShortcutInfo r11 = (android.content.pm.ShortcutInfo) r11
            if (r11 != 0) goto L_0x0156
        L_0x0154:
            r7 = r4
            goto L_0x016e
        L_0x0156:
            com.android.wm.shell.bubbles.Bubble r7 = new com.android.wm.shell.bubbles.Bubble
            java.lang.String r10 = r6.key
            int r12 = r6.desiredHeight
            int r13 = r6.desiredHeightResId
            java.lang.String r14 = r6.title
            int r15 = r6.taskId
            java.lang.String r6 = r6.locus
            com.android.wm.shell.common.ShellExecutor r8 = r3.mainExecutor
            r9 = r7
            r16 = r6
            r17 = r8
            r9.<init>(r10, r11, r12, r13, r14, r15, r16, r17)
        L_0x016e:
            if (r7 != 0) goto L_0x0171
            goto L_0x010f
        L_0x0171:
            r5.add(r7)
            goto L_0x010f
        L_0x0175:
            com.android.wm.shell.bubbles.BubbleDataRepository r0 = r1.this$0
            com.android.wm.shell.common.ShellExecutor r0 = r0.mainExecutor
            com.android.wm.shell.bubbles.BubbleDataRepository$loadBubbles$1$1 r2 = new com.android.wm.shell.bubbles.BubbleDataRepository$loadBubbles$1$1
            kotlin.jvm.functions.Function1<java.util.List<? extends com.android.wm.shell.bubbles.Bubble>, kotlin.Unit> r1 = r1.$cb
            r2.<init>(r1, r5)
            r0.execute(r2)
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x0186:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        L_0x0189:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.bubbles.BubbleDataRepository$loadBubbles$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
