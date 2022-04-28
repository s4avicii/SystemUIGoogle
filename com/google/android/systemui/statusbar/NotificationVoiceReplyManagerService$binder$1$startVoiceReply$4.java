package com.google.android.systemui.statusbar;

import com.google.android.systemui.statusbar.notification.voicereplies.Session;
import java.util.Objects;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.SharedFlowImpl;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1$startVoiceReply$4", mo21074f = "NotificationVoiceReplyManagerService.kt", mo21075l = {279}, mo21076m = "invokeSuspend")
/* compiled from: NotificationVoiceReplyManagerService.kt */
final class NotificationVoiceReplyManagerService$binder$1$startVoiceReply$4 extends SuspendLambda implements Function2<Session, Continuation<? super Unit>, Object> {
    public final /* synthetic */ CallbacksHandler $handler;
    public final /* synthetic */ int $token;
    private /* synthetic */ Object L$0;
    public int label;
    public final /* synthetic */ NotificationVoiceReplyManagerService$binder$1 this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public NotificationVoiceReplyManagerService$binder$1$startVoiceReply$4(CallbacksHandler callbacksHandler, int i, NotificationVoiceReplyManagerService$binder$1 notificationVoiceReplyManagerService$binder$1, Continuation<? super NotificationVoiceReplyManagerService$binder$1$startVoiceReply$4> continuation) {
        super(2, continuation);
        this.$handler = callbacksHandler;
        this.$token = i;
        this.this$0 = notificationVoiceReplyManagerService$binder$1;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        NotificationVoiceReplyManagerService$binder$1$startVoiceReply$4 notificationVoiceReplyManagerService$binder$1$startVoiceReply$4 = new NotificationVoiceReplyManagerService$binder$1$startVoiceReply$4(this.$handler, this.$token, this.this$0, continuation);
        notificationVoiceReplyManagerService$binder$1$startVoiceReply$4.L$0 = obj;
        return notificationVoiceReplyManagerService$binder$1$startVoiceReply$4;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationVoiceReplyManagerService$binder$1$startVoiceReply$4) create((Session) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CallbacksHandler callbacksHandler = this.$handler;
            int i2 = this.$token;
            Objects.requireNonNull(callbacksHandler);
            callbacksHandler.callbacks.onVoiceReplyHandled(i2, 0);
            NotificationVoiceReplyManagerService$binder$1 notificationVoiceReplyManagerService$binder$1 = this.this$0;
            Objects.requireNonNull(notificationVoiceReplyManagerService$binder$1);
            SharedFlowImpl sharedFlowImpl = notificationVoiceReplyManagerService$binder$1.onVoiceAuthStateChangedFlow;
            NotificationVoiceReplyManagerService$binder$1 notificationVoiceReplyManagerService$binder$12 = this.this$0;
            int i3 = this.$token;
            C2330xaf8a4de8 notificationVoiceReplyManagerService$binder$1$startVoiceReply$4$invokeSuspend$$inlined$collect$1 = new C2330xaf8a4de8((Session) this.L$0);
            this.label = 1;
            sharedFlowImpl.collect(new C2331x4ce234c2(notificationVoiceReplyManagerService$binder$1$startVoiceReply$4$invokeSuspend$$inlined$collect$1, notificationVoiceReplyManagerService$binder$12, i3), this);
            return coroutineSingletons;
        } else if (i == 1) {
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
