package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.policy.RemoteInputViewController;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__ReduceKt;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$restartedJob$1", mo21074f = "NotificationVoiceReplyManager.kt", mo21075l = {413}, mo21076m = "invokeSuspend")
/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$restartedJob$1 */
/* compiled from: NotificationVoiceReplyManager.kt */
public final class C2391x2157f550 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ CompletableDeferred<Unit> $completion;
    public final /* synthetic */ String $key;
    public final /* synthetic */ Flow<Pair<String, RemoteInputViewController>> $remoteInputViewActivatedForVoiceReply;
    public int label;

    @DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$restartedJob$1$1", mo21074f = "NotificationVoiceReplyManager.kt", mo21075l = {}, mo21076m = "invokeSuspend")
    /* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$restartedJob$1$1 */
    /* compiled from: NotificationVoiceReplyManager.kt */
    public static final class C23921 extends SuspendLambda implements Function2<Pair<? extends String, ? extends RemoteInputViewController>, Continuation<? super Boolean>, Object> {
        public /* synthetic */ Object L$0;
        public int label;

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C23921 r0 = new C23921(str, continuation);
            r0.L$0 = obj;
            return r0;
        }

        public final Object invoke(Object obj, Object obj2) {
            return ((C23921) create((Pair) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object obj) {
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                return Boolean.valueOf(Intrinsics.areEqual(((Pair) this.L$0).getFirst(), str));
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C2391x2157f550(Flow<? extends Pair<String, ? extends RemoteInputViewController>> flow, CompletableDeferred<Unit> completableDeferred, String str, Continuation<? super C2391x2157f550> continuation) {
        super(2, continuation);
        this.$remoteInputViewActivatedForVoiceReply = flow;
        this.$completion = completableDeferred;
        this.$key = str;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new C2391x2157f550(this.$remoteInputViewActivatedForVoiceReply, this.$completion, this.$key, continuation);
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((C2391x2157f550) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Flow<Pair<String, RemoteInputViewController>> flow = this.$remoteInputViewActivatedForVoiceReply;
            final String str = this.$key;
            C23921 r1 = new C23921((Continuation<? super C23921>) null);
            this.label = 1;
            if (FlowKt__ReduceKt.first(flow, r1, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else if (i == 1) {
            ResultKt.throwOnFailure(obj);
        } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        CompletableDeferred<Unit> completableDeferred = this.$completion;
        Unit unit = Unit.INSTANCE;
        completableDeferred.complete();
        return unit;
    }
}
