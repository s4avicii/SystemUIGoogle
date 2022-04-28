package com.google.android.systemui.statusbar.notification.voicereplies;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManagerKt$distinctUntilChanged$2", mo21074f = "NotificationVoiceReplyManager.kt", mo21075l = {1169}, mo21076m = "invokeSuspend")
/* compiled from: NotificationVoiceReplyManager.kt */
final class NotificationVoiceReplyManagerKt$distinctUntilChanged$2 extends SuspendLambda implements Function2<FlowCollector<Object>, Continuation<? super Unit>, Object> {
    public final /* synthetic */ Function2<Object, Object, Boolean> $areEqual;
    public final /* synthetic */ Flow<Object> $this_distinctUntilChanged;
    private /* synthetic */ Object L$0;
    public int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public NotificationVoiceReplyManagerKt$distinctUntilChanged$2(Flow<Object> flow, Function2<Object, Object, Boolean> function2, Continuation<? super NotificationVoiceReplyManagerKt$distinctUntilChanged$2> continuation) {
        super(2, continuation);
        this.$this_distinctUntilChanged = flow;
        this.$areEqual = function2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        NotificationVoiceReplyManagerKt$distinctUntilChanged$2 notificationVoiceReplyManagerKt$distinctUntilChanged$2 = new NotificationVoiceReplyManagerKt$distinctUntilChanged$2(this.$this_distinctUntilChanged, this.$areEqual, continuation);
        notificationVoiceReplyManagerKt$distinctUntilChanged$2.L$0 = obj;
        return notificationVoiceReplyManagerKt$distinctUntilChanged$2;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationVoiceReplyManagerKt$distinctUntilChanged$2) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            ref$ObjectRef.element = NO_VALUE.INSTANCE;
            Flow<Object> flow = this.$this_distinctUntilChanged;
            C2427xec6bebed notificationVoiceReplyManagerKt$distinctUntilChanged$2$invokeSuspend$$inlined$collect$1 = new C2427xec6bebed(ref$ObjectRef, this.$areEqual, (FlowCollector) this.L$0);
            this.label = 1;
            if (flow.collect(notificationVoiceReplyManagerKt$distinctUntilChanged$2$invokeSuspend$$inlined$collect$1, this) == coroutineSingletons) {
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
