package com.google.android.systemui.statusbar.notification.voicereplies;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$hasCandidate$2$1$1$invokeSuspend$$inlined$combine$1$3", mo21074f = "NotificationVoiceReplyManager.kt", mo21075l = {333}, mo21076m = "invokeSuspend")
/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$hasCandidate$2$1$1$invokeSuspend$$inlined$combine$1$3 */
/* compiled from: Zip.kt */
public final class C2368x71c125b6 extends SuspendLambda implements Function3<FlowCollector<? super CtaState>, CtaState[], Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    public /* synthetic */ Object L$1;
    public int label;

    public C2368x71c125b6(Continuation continuation) {
        super(3, continuation);
    }

    public final Object invoke(Object obj, Object obj2, Object obj3) {
        C2368x71c125b6 notificationVoiceReplyController$stateMachine$2$hasCandidate$2$1$1$invokeSuspend$$inlined$combine$1$3 = new C2368x71c125b6((Continuation) obj3);
        notificationVoiceReplyController$stateMachine$2$hasCandidate$2$1$1$invokeSuspend$$inlined$combine$1$3.L$0 = (FlowCollector) obj;
        notificationVoiceReplyController$stateMachine$2$hasCandidate$2$1$1$invokeSuspend$$inlined$combine$1$3.L$1 = (Object[]) obj2;
        return notificationVoiceReplyController$stateMachine$2$hasCandidate$2$1$1$invokeSuspend$$inlined$combine$1$3.invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            CtaState ctaState = (CtaState) ArraysKt___ArraysKt.maxOrNull((CtaState[]) ((Object[]) this.L$1));
            if (ctaState == null) {
                ctaState = CtaState.NONE;
            }
            this.label = 1;
            if (flowCollector.emit(ctaState, this) == coroutineSingletons) {
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
