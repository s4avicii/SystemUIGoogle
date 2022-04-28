package com.google.android.systemui.statusbar.notification.voicereplies;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManagerKt$collectLatest$2", mo21074f = "NotificationVoiceReplyManager.kt", mo21075l = {1169}, mo21076m = "invokeSuspend")
/* compiled from: NotificationVoiceReplyManager.kt */
public final class NotificationVoiceReplyManagerKt$collectLatest$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ Function2<T, Continuation<? super Unit>, Object> $collector;
    public final /* synthetic */ Flow<T> $this_collectLatest;
    private /* synthetic */ Object L$0;
    public int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public NotificationVoiceReplyManagerKt$collectLatest$2(Flow<? extends T> flow, Function2<? super T, ? super Continuation<? super Unit>, ? extends Object> function2, Continuation<? super NotificationVoiceReplyManagerKt$collectLatest$2> continuation) {
        super(2, continuation);
        this.$this_collectLatest = flow;
        this.$collector = function2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        NotificationVoiceReplyManagerKt$collectLatest$2 notificationVoiceReplyManagerKt$collectLatest$2 = new NotificationVoiceReplyManagerKt$collectLatest$2(this.$this_collectLatest, this.$collector, continuation);
        notificationVoiceReplyManagerKt$collectLatest$2.L$0 = obj;
        return notificationVoiceReplyManagerKt$collectLatest$2;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationVoiceReplyManagerKt$collectLatest$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            Flow<T> flow = this.$this_collectLatest;
            C2426xb9788110 notificationVoiceReplyManagerKt$collectLatest$2$invokeSuspend$$inlined$collect$1 = new C2426xb9788110(ref$ObjectRef, (CoroutineScope) this.L$0, this.$collector);
            this.label = 1;
            if (flow.collect(notificationVoiceReplyManagerKt$collectLatest$2$invokeSuspend$$inlined$collect$1, this) == coroutineSingletons) {
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
