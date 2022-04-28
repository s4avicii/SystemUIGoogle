package com.google.android.systemui.statusbar;

import com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplySubscription;
import java.util.Objects;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2$2$1", mo21074f = "NotificationVoiceReplyManagerService.kt", mo21075l = {148}, mo21076m = "invokeSuspend")
/* renamed from: com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2$2$1 */
/* compiled from: NotificationVoiceReplyManagerService.kt */
public final class C2333xf794a6a0 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ String $content;
    public final /* synthetic */ CallbacksHandler $handler;
    public final /* synthetic */ VoiceReplySubscription $registration;
    public final /* synthetic */ int $token;
    public int label;
    public final /* synthetic */ NotificationVoiceReplyManagerService$binder$1 this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C2333xf794a6a0(NotificationVoiceReplyManagerService$binder$1 notificationVoiceReplyManagerService$binder$1, VoiceReplySubscription voiceReplySubscription, CallbacksHandler callbacksHandler, int i, String str, Continuation<? super C2333xf794a6a0> continuation) {
        super(2, continuation);
        this.this$0 = notificationVoiceReplyManagerService$binder$1;
        this.$registration = voiceReplySubscription;
        this.$handler = callbacksHandler;
        this.$token = i;
        this.$content = str;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new C2333xf794a6a0(this.this$0, this.$registration, this.$handler, this.$token, this.$content, continuation);
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((C2333xf794a6a0) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            NotificationVoiceReplyManagerService$binder$1 notificationVoiceReplyManagerService$binder$1 = this.this$0;
            VoiceReplySubscription voiceReplySubscription = this.$registration;
            CallbacksHandler callbacksHandler = this.$handler;
            int i2 = this.$token;
            String str = this.$content;
            this.label = 1;
            int i3 = NotificationVoiceReplyManagerService$binder$1.$r8$clinit;
            Objects.requireNonNull(notificationVoiceReplyManagerService$binder$1);
            Object startVoiceReply = voiceReplySubscription.startVoiceReply(i2, str, new NotificationVoiceReplyManagerService$binder$1$startVoiceReply$3(callbacksHandler, i2), new NotificationVoiceReplyManagerService$binder$1$startVoiceReply$4(callbacksHandler, i2, notificationVoiceReplyManagerService$binder$1, (Continuation<? super NotificationVoiceReplyManagerService$binder$1$startVoiceReply$4>) null), this);
            if (startVoiceReply != coroutineSingletons) {
                startVoiceReply = Unit.INSTANCE;
            }
            if (startVoiceReply == coroutineSingletons) {
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
