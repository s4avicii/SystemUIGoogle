package com.google.android.systemui.statusbar;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.NotificationVoiceReplyManagerServiceKt$startingWith$1$1", mo21074f = "NotificationVoiceReplyManagerService.kt", mo21075l = {210, 274}, mo21076m = "invokeSuspend")
/* compiled from: NotificationVoiceReplyManagerService.kt */
final class NotificationVoiceReplyManagerServiceKt$startingWith$1$1 extends SuspendLambda implements Function2<FlowCollector<Object>, Continuation<? super Unit>, Object> {
    public final /* synthetic */ Flow<Object> $this_run;
    public final /* synthetic */ Object $value;
    private /* synthetic */ Object L$0;
    public int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public NotificationVoiceReplyManagerServiceKt$startingWith$1$1(Object obj, Flow<Object> flow, Continuation<? super NotificationVoiceReplyManagerServiceKt$startingWith$1$1> continuation) {
        super(2, continuation);
        this.$value = obj;
        this.$this_run = flow;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        NotificationVoiceReplyManagerServiceKt$startingWith$1$1 notificationVoiceReplyManagerServiceKt$startingWith$1$1 = new NotificationVoiceReplyManagerServiceKt$startingWith$1$1(this.$value, this.$this_run, continuation);
        notificationVoiceReplyManagerServiceKt$startingWith$1$1.L$0 = obj;
        return notificationVoiceReplyManagerServiceKt$startingWith$1$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationVoiceReplyManagerServiceKt$startingWith$1$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: kotlinx.coroutines.flow.FlowCollector} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r5) {
        /*
            r4 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r4.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L_0x0020
            if (r1 == r3) goto L_0x0018
            if (r1 != r2) goto L_0x0010
            kotlin.ResultKt.throwOnFailure(r5)
            goto L_0x0048
        L_0x0010:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L_0x0018:
            java.lang.Object r1 = r4.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r5)
            goto L_0x0035
        L_0x0020:
            kotlin.ResultKt.throwOnFailure(r5)
            java.lang.Object r5 = r4.L$0
            r1 = r5
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            java.lang.Object r5 = r4.$value
            r4.L$0 = r1
            r4.label = r3
            java.lang.Object r5 = r1.emit(r5, r4)
            if (r5 != r0) goto L_0x0035
            return r0
        L_0x0035:
            kotlinx.coroutines.flow.Flow<java.lang.Object> r5 = r4.$this_run
            com.google.android.systemui.statusbar.NotificationVoiceReplyManagerServiceKt$startingWith$1$1$invokeSuspend$$inlined$collect$1 r3 = new com.google.android.systemui.statusbar.NotificationVoiceReplyManagerServiceKt$startingWith$1$1$invokeSuspend$$inlined$collect$1
            r3.<init>(r1)
            r1 = 0
            r4.L$0 = r1
            r4.label = r2
            java.lang.Object r4 = r5.collect(r3, r4)
            if (r4 != r0) goto L_0x0048
            return r0
        L_0x0048:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.statusbar.NotificationVoiceReplyManagerServiceKt$startingWith$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
