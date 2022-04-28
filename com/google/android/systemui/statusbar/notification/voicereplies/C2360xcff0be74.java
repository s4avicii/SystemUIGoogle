package com.google.android.systemui.statusbar.notification.voicereplies;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.internal.CombineKt;

/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$notifyHandlersOfQuickPhraseReplyAvailability$6$invokeSuspend$$inlined$combine$1 */
/* compiled from: SafeCollector.common.kt */
public final class C2360xcff0be74 implements Flow<CtaState> {
    public final /* synthetic */ Flow[] $flowArray$inlined;

    @DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$notifyHandlersOfQuickPhraseReplyAvailability$6$invokeSuspend$$inlined$combine$1$3", mo21074f = "NotificationVoiceReplyManager.kt", mo21075l = {333}, mo21076m = "invokeSuspend")
    /* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$notifyHandlersOfQuickPhraseReplyAvailability$6$invokeSuspend$$inlined$combine$1$3 */
    /* compiled from: Zip.kt */
    public static final class C23623 extends SuspendLambda implements Function3<FlowCollector<? super CtaState>, CtaState[], Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        public /* synthetic */ Object L$1;
        public int label;

        public final Object invoke(Object obj, Object obj2, Object obj3) {
            C23623 r0 = new C23623((Continuation) obj3);
            r0.L$0 = (FlowCollector) obj;
            r0.L$1 = (Object[]) obj2;
            return r0.invokeSuspend(Unit.INSTANCE);
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

    public C2360xcff0be74(Flow[] flowArr) {
        this.$flowArray$inlined = flowArr;
    }

    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        final Flow[] flowArr = this.$flowArray$inlined;
        Object combineInternal = CombineKt.combineInternal(flowCollector, flowArr, new Function0<CtaState[]>() {
            public final Object invoke() {
                return new CtaState[flowArr.length];
            }
        }, new C23623((Continuation) null), continuation);
        if (combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED) {
            return combineInternal;
        }
        return Unit.INSTANCE;
    }
}
