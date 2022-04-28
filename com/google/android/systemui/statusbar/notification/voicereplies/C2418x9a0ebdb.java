package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.flow.MutableSharedFlow;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$hasCandidate$2$2$newCandidateEvents$1$2", mo21074f = "NotificationVoiceReplyManager.kt", mo21075l = {1176, 558}, mo21076m = "invokeSuspend")
/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$hasCandidate$2$2$newCandidateEvents$1$2 */
/* compiled from: NotificationVoiceReplyManager.kt */
public final class C2418x9a0ebdb extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ Channel<NotificationEntry> $chan;
    public final /* synthetic */ MutableSharedFlow<NotificationEntry> $flow;
    public Object L$0;
    public Object L$1;
    public Object L$2;
    public int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C2418x9a0ebdb(Channel<NotificationEntry> channel, MutableSharedFlow<NotificationEntry> mutableSharedFlow, Continuation<? super C2418x9a0ebdb> continuation) {
        super(2, continuation);
        this.$chan = channel;
        this.$flow = mutableSharedFlow;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new C2418x9a0ebdb(this.$chan, this.$flow, continuation);
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((C2418x9a0ebdb) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0078, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0079, code lost:
        com.android.systemui.R$array.cancelConsumed(r4, r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x007c, code lost:
        throw r9;
     */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x004f A[Catch:{ all -> 0x0078 }] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x005b A[Catch:{ all -> 0x0078 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r8.label
            r2 = 1
            r3 = 2
            if (r1 == 0) goto L_0x0035
            if (r1 == r2) goto L_0x0025
            if (r1 != r3) goto L_0x001d
            java.lang.Object r1 = r8.L$2
            kotlinx.coroutines.channels.ChannelIterator r1 = (kotlinx.coroutines.channels.ChannelIterator) r1
            java.lang.Object r4 = r8.L$1
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            java.lang.Object r5 = r8.L$0
            kotlinx.coroutines.flow.MutableSharedFlow r5 = (kotlinx.coroutines.flow.MutableSharedFlow) r5
            kotlin.ResultKt.throwOnFailure(r9)     // Catch:{ all -> 0x0076 }
        L_0x001b:
            r9 = r5
            goto L_0x0040
        L_0x001d:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L_0x0025:
            java.lang.Object r1 = r8.L$2
            kotlinx.coroutines.channels.ChannelIterator r1 = (kotlinx.coroutines.channels.ChannelIterator) r1
            java.lang.Object r4 = r8.L$1
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            java.lang.Object r5 = r8.L$0
            kotlinx.coroutines.flow.MutableSharedFlow r5 = (kotlinx.coroutines.flow.MutableSharedFlow) r5
            kotlin.ResultKt.throwOnFailure(r9)     // Catch:{ all -> 0x0076 }
            goto L_0x0052
        L_0x0035:
            kotlin.ResultKt.throwOnFailure(r9)
            kotlinx.coroutines.channels.Channel<com.android.systemui.statusbar.notification.collection.NotificationEntry> r4 = r8.$chan
            kotlinx.coroutines.flow.MutableSharedFlow<com.android.systemui.statusbar.notification.collection.NotificationEntry> r9 = r8.$flow
            kotlinx.coroutines.channels.AbstractChannel$Itr r1 = r4.iterator()     // Catch:{ all -> 0x0076 }
        L_0x0040:
            r8.L$0 = r9     // Catch:{ all -> 0x0076 }
            r8.L$1 = r4     // Catch:{ all -> 0x0076 }
            r8.L$2 = r1     // Catch:{ all -> 0x0076 }
            r8.label = r2     // Catch:{ all -> 0x0076 }
            java.lang.Object r5 = r1.hasNext(r8)     // Catch:{ all -> 0x0076 }
            if (r5 != r0) goto L_0x004f
            return r0
        L_0x004f:
            r7 = r5
            r5 = r9
            r9 = r7
        L_0x0052:
            r6 = 0
            java.lang.Boolean r9 = (java.lang.Boolean) r9     // Catch:{ all -> 0x0076 }
            boolean r9 = r9.booleanValue()     // Catch:{ all -> 0x0076 }
            if (r9 == 0) goto L_0x0070
            java.lang.Object r9 = r1.next()     // Catch:{ all -> 0x0076 }
            com.android.systemui.statusbar.notification.collection.NotificationEntry r9 = (com.android.systemui.statusbar.notification.collection.NotificationEntry) r9     // Catch:{ all -> 0x0076 }
            r8.L$0 = r5     // Catch:{ all -> 0x0076 }
            r8.L$1 = r4     // Catch:{ all -> 0x0076 }
            r8.L$2 = r1     // Catch:{ all -> 0x0076 }
            r8.label = r3     // Catch:{ all -> 0x0076 }
            java.lang.Object r9 = r5.emit(r9, r8)     // Catch:{ all -> 0x0076 }
            if (r9 != r0) goto L_0x001b
            return r0
        L_0x0070:
            com.android.systemui.R$array.cancelConsumed(r4, r6)
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        L_0x0076:
            r8 = move-exception
            throw r8     // Catch:{ all -> 0x0078 }
        L_0x0078:
            r9 = move-exception
            com.android.systemui.R$array.cancelConsumed(r4, r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.statusbar.notification.voicereplies.C2418x9a0ebdb.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
