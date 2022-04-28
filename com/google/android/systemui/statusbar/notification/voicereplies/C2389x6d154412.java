package com.google.android.systemui.statusbar.notification.voicereplies;

import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController;
import java.util.Objects;
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
import kotlinx.coroutines.flow.FlowKt__ReduceKt;
import kotlinx.coroutines.flow.MutableSharedFlow;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$removalJob$1", mo21074f = "NotificationVoiceReplyManager.kt", mo21075l = {409}, mo21076m = "invokeSuspend")
/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$removalJob$1 */
/* compiled from: NotificationVoiceReplyManager.kt */
public final class C2389x6d154412 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ CompletableDeferred<Unit> $completion;
    public final /* synthetic */ String $key;
    public final /* synthetic */ NotificationVoiceReplyController.Connection $this_logUiEventsForActivatedVoiceReplyInputs;
    public int label;

    @DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$removalJob$1$1", mo21074f = "NotificationVoiceReplyManager.kt", mo21075l = {}, mo21076m = "invokeSuspend")
    /* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$logUiEventsForActivatedVoiceReplyInputs$2$1$removalJob$1$1 */
    /* compiled from: NotificationVoiceReplyManager.kt */
    public static final class C23901 extends SuspendLambda implements Function2<String, Continuation<? super Boolean>, Object> {
        public /* synthetic */ Object L$0;
        public int label;

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C23901 r0 = new C23901(str, continuation);
            r0.L$0 = obj;
            return r0;
        }

        public final Object invoke(Object obj, Object obj2) {
            return ((C23901) create((String) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object obj) {
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                return Boolean.valueOf(Intrinsics.areEqual((String) this.L$0, str));
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C2389x6d154412(NotificationVoiceReplyController.Connection connection, CompletableDeferred<Unit> completableDeferred, String str, Continuation<? super C2389x6d154412> continuation) {
        super(2, continuation);
        this.$this_logUiEventsForActivatedVoiceReplyInputs = connection;
        this.$completion = completableDeferred;
        this.$key = str;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new C2389x6d154412(this.$this_logUiEventsForActivatedVoiceReplyInputs, this.$completion, this.$key, continuation);
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((C2389x6d154412) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            NotificationVoiceReplyController.Connection connection = this.$this_logUiEventsForActivatedVoiceReplyInputs;
            Objects.requireNonNull(connection);
            MutableSharedFlow<String> mutableSharedFlow = connection.entryRemovals;
            final String str = this.$key;
            C23901 r1 = new C23901((Continuation<? super C23901>) null);
            this.label = 1;
            if (FlowKt__ReduceKt.first(mutableSharedFlow, r1, this) == coroutineSingletons) {
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
