package com.google.android.systemui.statusbar.notification.voicereplies;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.MainCoroutineDispatcher;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.flow.Flow;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManagerKt$merge$2", mo21074f = "NotificationVoiceReplyManager.kt", mo21075l = {}, mo21076m = "invokeSuspend")
/* compiled from: NotificationVoiceReplyManager.kt */
final class NotificationVoiceReplyManagerKt$merge$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ SendChannel<Object> $chan;
    public final /* synthetic */ Flow<Object>[] $flows;
    private /* synthetic */ Object L$0;
    public int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public NotificationVoiceReplyManagerKt$merge$2(Flow<Object>[] flowArr, SendChannel<Object> sendChannel, Continuation<? super NotificationVoiceReplyManagerKt$merge$2> continuation) {
        super(2, continuation);
        this.$flows = flowArr;
        this.$chan = sendChannel;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        NotificationVoiceReplyManagerKt$merge$2 notificationVoiceReplyManagerKt$merge$2 = new NotificationVoiceReplyManagerKt$merge$2(this.$flows, this.$chan, continuation);
        notificationVoiceReplyManagerKt$merge$2.L$0 = obj;
        return notificationVoiceReplyManagerKt$merge$2;
    }

    public final Object invoke(Object obj, Object obj2) {
        Unit unit = Unit.INSTANCE;
        ((NotificationVoiceReplyManagerKt$merge$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(unit);
        return unit;
    }

    public final Object invokeSuspend(Object obj) {
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            Flow<Object>[] flowArr = this.$flows;
            SendChannel<Object> sendChannel = this.$chan;
            int i = 0;
            int length = flowArr.length;
            while (i < length) {
                Flow<Object> flow = flowArr[i];
                i++;
                BuildersKt.launch$default(coroutineScope, (MainCoroutineDispatcher) null, (CoroutineStart) null, new NotificationVoiceReplyManagerKt$merge$2$1$1(flow, sendChannel, (Continuation<? super NotificationVoiceReplyManagerKt$merge$2$1$1>) null), 3);
            }
            return Unit.INSTANCE;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}
