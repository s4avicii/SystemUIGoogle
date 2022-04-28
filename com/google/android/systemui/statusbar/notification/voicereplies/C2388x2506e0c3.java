package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.policy.RemoteInputViewController;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2", mo21074f = "NotificationVoiceReplyManager.kt", mo21075l = {1169}, mo21076m = "invokeSuspend")
/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2 */
/* compiled from: NotificationVoiceReplyManager.kt */
final class C2388x2506e0c3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ Flow<Pair<String, RemoteInputViewController>> $remoteInputViewActivatedForVoiceReply;
    public final /* synthetic */ NotificationVoiceReplyController.Connection $this_logUiEventsForActivatedVoiceReplyInputs;
    private /* synthetic */ Object L$0;
    public int label;
    public final /* synthetic */ NotificationVoiceReplyController this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C2388x2506e0c3(Flow<? extends Pair<String, ? extends RemoteInputViewController>> flow, NotificationVoiceReplyController.Connection connection, NotificationVoiceReplyController notificationVoiceReplyController, Continuation<? super C2388x2506e0c3> continuation) {
        super(2, continuation);
        this.$remoteInputViewActivatedForVoiceReply = flow;
        this.$this_logUiEventsForActivatedVoiceReplyInputs = connection;
        this.this$0 = notificationVoiceReplyController;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        C2388x2506e0c3 notificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2 = new C2388x2506e0c3(this.$remoteInputViewActivatedForVoiceReply, this.$this_logUiEventsForActivatedVoiceReplyInputs, this.this$0, continuation);
        notificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2.L$0 = obj;
        return notificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((C2388x2506e0c3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Flow<Pair<String, RemoteInputViewController>> flow = this.$remoteInputViewActivatedForVoiceReply;
            C2349xb4924adb notificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$invokeSuspend$$inlined$collect$1 = new C2349xb4924adb((CoroutineScope) this.L$0, this.$this_logUiEventsForActivatedVoiceReplyInputs, this.this$0, flow);
            this.label = 1;
            if (flow.collect(notificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$invokeSuspend$$inlined$collect$1, this) == coroutineSingletons) {
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
