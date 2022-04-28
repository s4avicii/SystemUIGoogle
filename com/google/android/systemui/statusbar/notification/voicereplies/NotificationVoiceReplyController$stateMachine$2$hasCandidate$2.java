package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$hasCandidate$2", mo21074f = "NotificationVoiceReplyManager.kt", mo21075l = {574}, mo21076m = "invokeSuspend")
/* compiled from: NotificationVoiceReplyManager.kt */
final class NotificationVoiceReplyController$stateMachine$2$hasCandidate$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ VoiceReplyTarget $candidate;
    public final /* synthetic */ Flow<Pair<NotificationEntry, Boolean>> $hunStateChanges;
    public final /* synthetic */ Flow<NotificationEntry> $reinflations;
    public final /* synthetic */ NotificationVoiceReplyController.Connection $this_stateMachine;
    private /* synthetic */ Object L$0;
    public int label;
    public final /* synthetic */ NotificationVoiceReplyController this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public NotificationVoiceReplyController$stateMachine$2$hasCandidate$2(NotificationVoiceReplyController.Connection connection, VoiceReplyTarget voiceReplyTarget, NotificationVoiceReplyController notificationVoiceReplyController, Flow<Pair<NotificationEntry, Boolean>> flow, Flow<NotificationEntry> flow2, Continuation<? super NotificationVoiceReplyController$stateMachine$2$hasCandidate$2> continuation) {
        super(2, continuation);
        this.$this_stateMachine = connection;
        this.$candidate = voiceReplyTarget;
        this.this$0 = notificationVoiceReplyController;
        this.$hunStateChanges = flow;
        this.$reinflations = flow2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        NotificationVoiceReplyController$stateMachine$2$hasCandidate$2 notificationVoiceReplyController$stateMachine$2$hasCandidate$2 = new NotificationVoiceReplyController$stateMachine$2$hasCandidate$2(this.$this_stateMachine, this.$candidate, this.this$0, this.$hunStateChanges, this.$reinflations, continuation);
        notificationVoiceReplyController$stateMachine$2$hasCandidate$2.L$0 = obj;
        return notificationVoiceReplyController$stateMachine$2$hasCandidate$2;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationVoiceReplyController$stateMachine$2$hasCandidate$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0071  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r15) {
        /*
            r14 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r14.label
            r2 = 1
            if (r1 == 0) goto L_0x0019
            if (r1 != r2) goto L_0x0011
            java.lang.Object r1 = r14.L$0
            kotlinx.coroutines.channels.ChannelIterator r1 = (kotlinx.coroutines.channels.ChannelIterator) r1
            kotlin.ResultKt.throwOnFailure(r15)
            goto L_0x0069
        L_0x0011:
            java.lang.IllegalStateException r14 = new java.lang.IllegalStateException
            java.lang.String r15 = "call to 'resume' before 'invoke' with coroutine"
            r14.<init>(r15)
            throw r14
        L_0x0019:
            kotlin.ResultKt.throwOnFailure(r15)
            java.lang.Object r15 = r14.L$0
            kotlinx.coroutines.CoroutineScope r15 = (kotlinx.coroutines.CoroutineScope) r15
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$hasCandidate$2$1 r1 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$hasCandidate$2$1
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r3 = r14.$candidate
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$Connection r4 = r14.$this_stateMachine
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController r5 = r14.this$0
            r6 = 0
            r1.<init>(r3, r4, r5, r6)
            r3 = 3
            kotlinx.coroutines.BuildersKt.launch$default(r15, r6, r6, r1, r3)
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$hasCandidate$2$2 r1 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$hasCandidate$2$2
            kotlinx.coroutines.flow.Flow<kotlin.Pair<com.android.systemui.statusbar.notification.collection.NotificationEntry, java.lang.Boolean>> r8 = r14.$hunStateChanges
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r9 = r14.$candidate
            kotlinx.coroutines.flow.Flow<com.android.systemui.statusbar.notification.collection.NotificationEntry> r10 = r14.$reinflations
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController r11 = r14.this$0
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$Connection r12 = r14.$this_stateMachine
            r13 = 0
            r7 = r1
            r7.<init>(r8, r9, r10, r11, r12, r13)
            kotlinx.coroutines.BuildersKt.launch$default(r15, r6, r6, r1, r3)
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$hasCandidate$2$3 r1 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$hasCandidate$2$3
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$Connection r4 = r14.$this_stateMachine
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r5 = r14.$candidate
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController r7 = r14.this$0
            r1.<init>(r4, r5, r7, r6)
            kotlinx.coroutines.BuildersKt.launch$default(r15, r6, r6, r1, r3)
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$Connection r15 = r14.$this_stateMachine
            java.util.Objects.requireNonNull(r15)
            kotlinx.coroutines.channels.Channel<com.google.android.systemui.statusbar.notification.voicereplies.StartSessionRequest> r15 = r15.startSessionRequests
            kotlinx.coroutines.channels.AbstractChannel$Itr r15 = r15.iterator()
            r1 = r15
        L_0x005e:
            r14.L$0 = r1
            r14.label = r2
            java.lang.Object r15 = r1.hasNext(r14)
            if (r15 != r0) goto L_0x0069
            return r0
        L_0x0069:
            java.lang.Boolean r15 = (java.lang.Boolean) r15
            boolean r15 = r15.booleanValue()
            if (r15 == 0) goto L_0x00d3
            java.lang.Object r15 = r1.next()
            com.google.android.systemui.statusbar.notification.voicereplies.StartSessionRequest r15 = (com.google.android.systemui.statusbar.notification.voicereplies.StartSessionRequest) r15
            java.util.Objects.requireNonNull(r15)
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyHandler r3 = r15.handler
            int r3 = r3.getUserId()
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r4 = r14.$candidate
            java.util.Objects.requireNonNull(r4)
            int r4 = r4.userId
            if (r3 == r4) goto L_0x00bd
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController r3 = r14.this$0
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger r3 = r3.logger
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyHandler r4 = r15.handler
            int r4 = r4.getUserId()
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r5 = r14.$candidate
            java.util.Objects.requireNonNull(r5)
            int r5 = r5.userId
            java.util.Objects.requireNonNull(r3)
            com.android.systemui.log.LogBuffer r3 = r3.logBuffer
            com.android.systemui.log.LogLevel r6 = com.android.systemui.log.LogLevel.DEBUG
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logUserIdMismatch$2 r7 = com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logUserIdMismatch$2.INSTANCE
            java.util.Objects.requireNonNull(r3)
            boolean r8 = r3.frozen
            if (r8 != 0) goto L_0x00b7
            java.lang.String r8 = "NotifVoiceReply"
            com.android.systemui.log.LogMessageImpl r6 = r3.obtain(r8, r6, r7)
            r6.int1 = r4
            r6.int2 = r5
            r3.push(r6)
        L_0x00b7:
            kotlin.jvm.functions.Function0<kotlin.Unit> r15 = r15.onFail
            r15.invoke()
            goto L_0x005e
        L_0x00bd:
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$Connection r3 = r14.$this_stateMachine
            java.util.Objects.requireNonNull(r3)
            kotlinx.coroutines.flow.MutableStateFlow<com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyState> r3 = r3.stateFlow
            com.google.android.systemui.statusbar.notification.voicereplies.InSession r4 = new com.google.android.systemui.statusbar.notification.voicereplies.InSession
            java.lang.String r5 = r15.initialContent
            kotlin.jvm.functions.Function2<com.google.android.systemui.statusbar.notification.voicereplies.Session, kotlin.coroutines.Continuation<? super kotlin.Unit>, java.lang.Object> r15 = r15.block
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r6 = r14.$candidate
            r4.<init>(r5, r15, r6)
            r3.setValue(r4)
            goto L_0x005e
        L_0x00d3:
            kotlin.Unit r14 = kotlin.Unit.INSTANCE
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$hasCandidate$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
