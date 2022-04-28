package com.google.android.systemui.statusbar;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.MutableStateFlow;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.NotificationVoiceReplyManagerServiceKt$stateIn$1", mo21074f = "NotificationVoiceReplyManagerService.kt", mo21075l = {274}, mo21076m = "invokeSuspend")
/* compiled from: NotificationVoiceReplyManagerService.kt */
final class NotificationVoiceReplyManagerServiceKt$stateIn$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ MutableStateFlow<Object> $stateFlow;
    public final /* synthetic */ Flow<Object> $this_stateIn;
    public int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public NotificationVoiceReplyManagerServiceKt$stateIn$1(Flow<Object> flow, MutableStateFlow<Object> mutableStateFlow, Continuation<? super NotificationVoiceReplyManagerServiceKt$stateIn$1> continuation) {
        super(2, continuation);
        this.$this_stateIn = flow;
        this.$stateFlow = mutableStateFlow;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new NotificationVoiceReplyManagerServiceKt$stateIn$1(this.$this_stateIn, this.$stateFlow, continuation);
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationVoiceReplyManagerServiceKt$stateIn$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Flow<Object> flow = this.$this_stateIn;
            C2344x4b652d6b notificationVoiceReplyManagerServiceKt$stateIn$1$invokeSuspend$$inlined$collect$1 = new C2344x4b652d6b(this.$stateFlow);
            this.label = 1;
            if (flow.collect(notificationVoiceReplyManagerServiceKt$stateIn$1$invokeSuspend$$inlined$collect$1, this) == coroutineSingletons) {
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
