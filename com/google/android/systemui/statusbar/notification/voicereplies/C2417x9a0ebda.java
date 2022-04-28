package com.google.android.systemui.statusbar.notification.voicereplies;

import androidx.mediarouter.R$dimen;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.flow.Flow;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$hasCandidate$2$2$newCandidateEvents$1$1", mo21074f = "NotificationVoiceReplyManager.kt", mo21075l = {557}, mo21076m = "invokeSuspend")
/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$hasCandidate$2$2$newCandidateEvents$1$1 */
/* compiled from: NotificationVoiceReplyManager.kt */
public final class C2417x9a0ebda extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ Channel<NotificationEntry> $chan;
    public final /* synthetic */ Flow<NotificationEntry> $huns;
    public final /* synthetic */ Flow<NotificationEntry> $reinflations;
    public int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C2417x9a0ebda(Channel<NotificationEntry> channel, Flow<NotificationEntry> flow, Flow<NotificationEntry> flow2, Continuation<? super C2417x9a0ebda> continuation) {
        super(2, continuation);
        this.$chan = channel;
        this.$reinflations = flow;
        this.$huns = flow2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new C2417x9a0ebda(this.$chan, this.$reinflations, this.$huns, continuation);
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((C2417x9a0ebda) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Channel<NotificationEntry> channel = this.$chan;
            Flow[] flowArr = {this.$reinflations, this.$huns};
            this.label = 1;
            Object coroutineScope = R$dimen.coroutineScope(new NotificationVoiceReplyManagerKt$merge$2(flowArr, channel, (Continuation<? super NotificationVoiceReplyManagerKt$merge$2>) null), this);
            if (coroutineScope != coroutineSingletons) {
                coroutineScope = Unit.INSTANCE;
            }
            if (coroutineScope == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else if (i == 1) {
            ResultKt.throwOnFailure(obj);
        } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return Unit.INSTANCE;
    }
}
