package com.android.p012wm.shell.bubbles;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

@DebugMetadata(mo21073c = "com.android.wm.shell.bubbles.BubbleDataRepository$persistToDisk$1", mo21074f = "BubbleDataRepository.kt", mo21075l = {110, 112}, mo21076m = "invokeSuspend")
/* renamed from: com.android.wm.shell.bubbles.BubbleDataRepository$persistToDisk$1 */
/* compiled from: BubbleDataRepository.kt */
final class BubbleDataRepository$persistToDisk$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ Job $prev;
    public int label;
    public final /* synthetic */ BubbleDataRepository this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public BubbleDataRepository$persistToDisk$1(Job job, BubbleDataRepository bubbleDataRepository, Continuation<? super BubbleDataRepository$persistToDisk$1> continuation) {
        super(2, continuation);
        this.$prev = job;
        this.this$0 = bubbleDataRepository;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new BubbleDataRepository$persistToDisk$1(this.$prev, this.this$0, continuation);
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((BubbleDataRepository$persistToDisk$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
        	at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
        	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
        	at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:248)
        	at java.base/java.util.Objects.checkIndex(Objects.java:372)
        	at java.base/java.util.ArrayList.get(ArrayList.java:458)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    public final java.lang.Object invokeSuspend(java.lang.Object r7) {
        /*
            r6 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r6.label
            r2 = 1
            r3 = 2
            if (r1 == 0) goto L_0x001c
            if (r1 == r2) goto L_0x0018
            if (r1 != r3) goto L_0x0010
            kotlin.ResultKt.throwOnFailure(r7)
            goto L_0x003f
        L_0x0010:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L_0x0018:
            kotlin.ResultKt.throwOnFailure(r7)
            goto L_0x0036
        L_0x001c:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlinx.coroutines.Job r7 = r6.$prev
            if (r7 != 0) goto L_0x0024
            goto L_0x0036
        L_0x0024:
            r6.label = r2
            r1 = 0
            r7.cancel(r1)
            java.lang.Object r7 = r7.join(r6)
            if (r7 != r0) goto L_0x0031
            goto L_0x0033
        L_0x0031:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
        L_0x0033:
            if (r7 != r0) goto L_0x0036
            return r0
        L_0x0036:
            r6.label = r3
            java.lang.Object r7 = kotlinx.coroutines.YieldKt.yield(r6)
            if (r7 != r0) goto L_0x003f
            return r0
        L_0x003f:
            com.android.wm.shell.bubbles.BubbleDataRepository r6 = r6.this$0
            com.android.wm.shell.bubbles.storage.BubblePersistentRepository r7 = r6.persistentRepository
            com.android.wm.shell.bubbles.storage.BubbleVolatileRepository r6 = r6.volatileRepository
            java.util.Objects.requireNonNull(r6)
            monitor-enter(r6)
            android.util.SparseArray r0 = new android.util.SparseArray     // Catch:{ all -> 0x00a6 }
            r0.<init>()     // Catch:{ all -> 0x00a6 }
            r1 = 0
            android.util.SparseArray<java.util.List<com.android.wm.shell.bubbles.storage.BubbleEntity>> r2 = r6.entitiesByUser     // Catch:{ all -> 0x00a6 }
            int r2 = r2.size()     // Catch:{ all -> 0x00a6 }
        L_0x0055:
            if (r1 >= r2) goto L_0x0070
            int r3 = r1 + 1
            android.util.SparseArray<java.util.List<com.android.wm.shell.bubbles.storage.BubbleEntity>> r4 = r6.entitiesByUser     // Catch:{ all -> 0x00a6 }
            int r4 = r4.keyAt(r1)     // Catch:{ all -> 0x00a6 }
            android.util.SparseArray<java.util.List<com.android.wm.shell.bubbles.storage.BubbleEntity>> r5 = r6.entitiesByUser     // Catch:{ all -> 0x00a6 }
            java.lang.Object r1 = r5.valueAt(r1)     // Catch:{ all -> 0x00a6 }
            java.util.List r1 = (java.util.List) r1     // Catch:{ all -> 0x00a6 }
            java.util.List r1 = kotlin.collections.CollectionsKt___CollectionsKt.toList(r1)     // Catch:{ all -> 0x00a6 }
            r0.put(r4, r1)     // Catch:{ all -> 0x00a6 }
            r1 = r3
            goto L_0x0055
        L_0x0070:
            monitor-exit(r6)
            java.util.Objects.requireNonNull(r7)
            android.util.AtomicFile r6 = r7.bubbleFile
            monitor-enter(r6)
            android.util.AtomicFile r1 = r7.bubbleFile     // Catch:{ IOException -> 0x0098 }
            java.io.FileOutputStream r1 = r1.startWrite()     // Catch:{ IOException -> 0x0098 }
            com.android.p012wm.shell.bubbles.storage.BubbleXmlHelperKt.writeXml(r1, r0)     // Catch:{ Exception -> 0x0089 }
            android.util.AtomicFile r0 = r7.bubbleFile     // Catch:{ Exception -> 0x0089 }
            r0.finishWrite(r1)     // Catch:{ Exception -> 0x0089 }
            monitor-exit(r6)
            goto L_0x00a1
        L_0x0087:
            r7 = move-exception
            goto L_0x00a4
        L_0x0089:
            r0 = move-exception
            java.lang.String r2 = "BubblePersistentRepository"
            java.lang.String r3 = "Failed to save bubble file, restoring backup"
            android.util.Log.e(r2, r3, r0)     // Catch:{ all -> 0x0087 }
            android.util.AtomicFile r7 = r7.bubbleFile     // Catch:{ all -> 0x0087 }
            r7.failWrite(r1)     // Catch:{ all -> 0x0087 }
            monitor-exit(r6)
            goto L_0x00a1
        L_0x0098:
            r7 = move-exception
            java.lang.String r0 = "BubblePersistentRepository"
            java.lang.String r1 = "Failed to save bubble file"
            android.util.Log.e(r0, r1, r7)     // Catch:{ all -> 0x0087 }
            monitor-exit(r6)
        L_0x00a1:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        L_0x00a4:
            monitor-exit(r6)
            throw r7
        L_0x00a6:
            r7 = move-exception
            monitor-exit(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.bubbles.BubbleDataRepository$persistToDisk$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
