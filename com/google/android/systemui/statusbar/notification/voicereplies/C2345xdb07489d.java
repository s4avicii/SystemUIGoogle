package com.google.android.systemui.statusbar.notification.voicereplies;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$IntRef;
import kotlinx.coroutines.CoroutineScope;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.notification.voicereplies.DebugNotificationVoiceReplyClient$startClient$job$1$receiver$1$onReceive$1", mo21074f = "NotificationVoiceReplyManager.kt", mo21075l = {1145}, mo21076m = "invokeSuspend")
/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.DebugNotificationVoiceReplyClient$startClient$job$1$receiver$1$onReceive$1 */
/* compiled from: NotificationVoiceReplyManager.kt */
public final class C2345xdb07489d extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ VoiceReplySubscription $subscription;
    public final /* synthetic */ Ref$IntRef $token;
    public int label;

    @DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.notification.voicereplies.DebugNotificationVoiceReplyClient$startClient$job$1$receiver$1$onReceive$1$2", mo21074f = "NotificationVoiceReplyManager.kt", mo21075l = {}, mo21076m = "invokeSuspend")
    /* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.DebugNotificationVoiceReplyClient$startClient$job$1$receiver$1$onReceive$1$2 */
    /* compiled from: NotificationVoiceReplyManager.kt */
    public static final class C23472 extends SuspendLambda implements Function2<Session, Continuation<? super Unit>, Object> {
        public int label;

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C23472(continuation);
        }

        public final Object invoke(Object obj, Object obj2) {
            Session session = (Session) obj;
            C23472 r0 = new C23472((Continuation) obj2);
            Unit unit = Unit.INSTANCE;
            r0.invokeSuspend(unit);
            return unit;
        }

        public final Object invokeSuspend(Object obj) {
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C2345xdb07489d(VoiceReplySubscription voiceReplySubscription, Ref$IntRef ref$IntRef, Continuation<? super C2345xdb07489d> continuation) {
        super(2, continuation);
        this.$subscription = voiceReplySubscription;
        this.$token = ref$IntRef;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new C2345xdb07489d(this.$subscription, this.$token, continuation);
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((C2345xdb07489d) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            VoiceReplySubscription voiceReplySubscription = this.$subscription;
            Ref$IntRef ref$IntRef = this.$token;
            int i2 = ref$IntRef.element;
            ref$IntRef.element = i2 + 1;
            C23461 r4 = C23461.INSTANCE;
            C23472 r5 = new C23472((Continuation<? super C23472>) null);
            this.label = 1;
            if (voiceReplySubscription.startVoiceReply(i2, (String) null, r4, r5, this) == coroutineSingletons) {
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
