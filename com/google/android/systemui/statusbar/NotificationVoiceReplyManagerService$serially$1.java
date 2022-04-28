package com.google.android.systemui.statusbar;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.AbstractChannel;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$serially$1", mo21074f = "NotificationVoiceReplyManagerService.kt", mo21075l = {191}, mo21076m = "invokeSuspend")
/* compiled from: NotificationVoiceReplyManagerService.kt */
final class NotificationVoiceReplyManagerService$serially$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ Function1<Continuation<? super Unit>, Object> $block;
    public int label;
    public final /* synthetic */ NotificationVoiceReplyManagerService this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public NotificationVoiceReplyManagerService$serially$1(NotificationVoiceReplyManagerService notificationVoiceReplyManagerService, Function1<? super Continuation<? super Unit>, ? extends Object> function1, Continuation<? super NotificationVoiceReplyManagerService$serially$1> continuation) {
        super(2, continuation);
        this.this$0 = notificationVoiceReplyManagerService;
        this.$block = function1;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new NotificationVoiceReplyManagerService$serially$1(this.this$0, this.$block, continuation);
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationVoiceReplyManagerService$serially$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            AbstractChannel abstractChannel = this.this$0.serializer;
            Function1<Continuation<? super Unit>, Object> function1 = this.$block;
            this.label = 1;
            if (abstractChannel.send(function1, this) == coroutineSingletons) {
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
