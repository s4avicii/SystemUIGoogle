package com.google.android.systemui.statusbar.notification.voicereplies;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.flow.Flow;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManagerKt$merge$2$1$1", mo21074f = "NotificationVoiceReplyManager.kt", mo21075l = {1169}, mo21076m = "invokeSuspend")
/* compiled from: NotificationVoiceReplyManager.kt */
public final class NotificationVoiceReplyManagerKt$merge$2$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ SendChannel<Object> $chan;
    public final /* synthetic */ Flow<Object> $flow;
    public int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public NotificationVoiceReplyManagerKt$merge$2$1$1(Flow<Object> flow, SendChannel<Object> sendChannel, Continuation<? super NotificationVoiceReplyManagerKt$merge$2$1$1> continuation) {
        super(2, continuation);
        this.$flow = flow;
        this.$chan = sendChannel;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new NotificationVoiceReplyManagerKt$merge$2$1$1(this.$flow, this.$chan, continuation);
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationVoiceReplyManagerKt$merge$2$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Flow<Object> flow = this.$flow;
            C2428xfdd5bdaf notificationVoiceReplyManagerKt$merge$2$1$1$invokeSuspend$$inlined$collect$1 = new C2428xfdd5bdaf(this.$chan);
            this.label = 1;
            if (flow.collect(notificationVoiceReplyManagerKt$merge$2$1$1$invokeSuspend$$inlined$collect$1, this) == coroutineSingletons) {
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
