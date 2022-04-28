package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.policy.RemoteInputViewController;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.MutableSharedFlow;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$inSession$2", mo21074f = "NotificationVoiceReplyManager.kt", mo21075l = {619}, mo21076m = "invokeSuspend")
/* compiled from: NotificationVoiceReplyManager.kt */
final class NotificationVoiceReplyController$stateMachine$2$inSession$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ Function2<Session, Continuation<? super Unit>, Object> $block;
    public final /* synthetic */ String $initialContent;
    public final /* synthetic */ MutableSharedFlow<Pair<String, RemoteInputViewController>> $remoteInputViewActivatedForVoiceReply;
    public final /* synthetic */ VoiceReplyTarget $target;
    public final /* synthetic */ NotificationVoiceReplyController.Connection $this_stateMachine;
    private /* synthetic */ Object L$0;
    public int label;
    public final /* synthetic */ NotificationVoiceReplyController this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public NotificationVoiceReplyController$stateMachine$2$inSession$2(NotificationVoiceReplyController notificationVoiceReplyController, VoiceReplyTarget voiceReplyTarget, NotificationVoiceReplyController.Connection connection, String str, MutableSharedFlow<Pair<String, RemoteInputViewController>> mutableSharedFlow, Function2<? super Session, ? super Continuation<? super Unit>, ? extends Object> function2, Continuation<? super NotificationVoiceReplyController$stateMachine$2$inSession$2> continuation) {
        super(2, continuation);
        this.this$0 = notificationVoiceReplyController;
        this.$target = voiceReplyTarget;
        this.$this_stateMachine = connection;
        this.$initialContent = str;
        this.$remoteInputViewActivatedForVoiceReply = mutableSharedFlow;
        this.$block = function2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        NotificationVoiceReplyController$stateMachine$2$inSession$2 notificationVoiceReplyController$stateMachine$2$inSession$2 = new NotificationVoiceReplyController$stateMachine$2$inSession$2(this.this$0, this.$target, this.$this_stateMachine, this.$initialContent, this.$remoteInputViewActivatedForVoiceReply, this.$block, continuation);
        notificationVoiceReplyController$stateMachine$2$inSession$2.L$0 = obj;
        return notificationVoiceReplyController$stateMachine$2$inSession$2;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationVoiceReplyController$stateMachine$2$inSession$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x008f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r18) {
        /*
            r17 = this;
            r0 = r17
            com.android.systemui.log.LogLevel r1 = com.android.systemui.log.LogLevel.DEBUG
            kotlin.coroutines.intrinsics.CoroutineSingletons r2 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r3 = r0.label
            r4 = 1
            java.lang.String r5 = "NotifVoiceReply"
            if (r3 == 0) goto L_0x0021
            if (r3 != r4) goto L_0x0019
            java.lang.Object r3 = r0.L$0
            kotlinx.coroutines.channels.ChannelIterator r3 = (kotlinx.coroutines.channels.ChannelIterator) r3
            kotlin.ResultKt.throwOnFailure(r18)
            r6 = r18
            goto L_0x0087
        L_0x0019:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0021:
            kotlin.ResultKt.throwOnFailure(r18)
            java.lang.Object r3 = r0.L$0
            kotlinx.coroutines.CoroutineScope r3 = (kotlinx.coroutines.CoroutineScope) r3
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController r6 = r0.this$0
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger r6 = r6.logger
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r7 = r0.$target
            java.util.Objects.requireNonNull(r7)
            java.lang.String r7 = r7.notifKey
            java.util.Objects.requireNonNull(r6)
            com.android.systemui.log.LogBuffer r6 = r6.logBuffer
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logStateInSession$2 r8 = com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logStateInSession$2.INSTANCE
            java.util.Objects.requireNonNull(r6)
            boolean r9 = r6.frozen
            if (r9 != 0) goto L_0x004a
            com.android.systemui.log.LogMessageImpl r8 = r6.obtain(r5, r1, r8)
            r8.str1 = r7
            r6.push(r8)
        L_0x004a:
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$inSession$2$1 r6 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$inSession$2$1
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r10 = r0.$target
            java.lang.String r11 = r0.$initialContent
            kotlinx.coroutines.flow.MutableSharedFlow<kotlin.Pair<java.lang.String, com.android.systemui.statusbar.policy.RemoteInputViewController>> r12 = r0.$remoteInputViewActivatedForVoiceReply
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$Connection r13 = r0.$this_stateMachine
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController r14 = r0.this$0
            kotlin.jvm.functions.Function2<com.google.android.systemui.statusbar.notification.voicereplies.Session, kotlin.coroutines.Continuation<? super kotlin.Unit>, java.lang.Object> r15 = r0.$block
            r16 = 0
            r9 = r6
            r9.<init>(r10, r11, r12, r13, r14, r15, r16)
            r7 = 0
            r8 = 3
            kotlinx.coroutines.BuildersKt.launch$default(r3, r7, r7, r6, r8)
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$inSession$2$2 r6 = new com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$inSession$2$2
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$Connection r9 = r0.$this_stateMachine
            com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplyTarget r10 = r0.$target
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController r11 = r0.this$0
            r6.<init>(r9, r10, r11, r7)
            kotlinx.coroutines.BuildersKt.launch$default(r3, r7, r7, r6, r8)
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$Connection r3 = r0.$this_stateMachine
            java.util.Objects.requireNonNull(r3)
            kotlinx.coroutines.channels.Channel<com.google.android.systemui.statusbar.notification.voicereplies.StartSessionRequest> r3 = r3.startSessionRequests
            kotlinx.coroutines.channels.AbstractChannel$Itr r3 = r3.iterator()
        L_0x007c:
            r0.L$0 = r3
            r0.label = r4
            java.lang.Object r6 = r3.hasNext(r0)
            if (r6 != r2) goto L_0x0087
            return r2
        L_0x0087:
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            if (r6 == 0) goto L_0x00bf
            java.lang.Object r6 = r3.next()
            com.google.android.systemui.statusbar.notification.voicereplies.StartSessionRequest r6 = (com.google.android.systemui.statusbar.notification.voicereplies.StartSessionRequest) r6
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController r7 = r0.this$0
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger r7 = r7.logger
            java.util.Objects.requireNonNull(r6)
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyHandler r8 = r6.handler
            int r8 = r8.getUserId()
            java.util.Objects.requireNonNull(r7)
            com.android.systemui.log.LogBuffer r7 = r7.logBuffer
            com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logSessionAlreadyInProgress$2 r9 = com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger$logSessionAlreadyInProgress$2.INSTANCE
            java.util.Objects.requireNonNull(r7)
            boolean r10 = r7.frozen
            if (r10 != 0) goto L_0x00b9
            com.android.systemui.log.LogMessageImpl r9 = r7.obtain(r5, r1, r9)
            r9.int1 = r8
            r7.push(r9)
        L_0x00b9:
            kotlin.jvm.functions.Function0<kotlin.Unit> r6 = r6.onFail
            r6.invoke()
            goto L_0x007c
        L_0x00bf:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$inSession$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
