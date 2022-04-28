package com.google.android.systemui.statusbar.notification.voicereplies;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.flow.FlowCollector;

/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManagerKt$distinctUntilChanged$2$invokeSuspend$$inlined$collect$1 */
/* compiled from: Collect.kt */
public final class C2427xec6bebed implements FlowCollector<Object> {
    public final /* synthetic */ FlowCollector $$this$flow$inlined;
    public final /* synthetic */ Function2 $areEqual$inlined;
    public final /* synthetic */ Ref$ObjectRef $prev$inlined;

    public C2427xec6bebed(Ref$ObjectRef ref$ObjectRef, Function2 function2, FlowCollector flowCollector) {
        this.$prev$inlined = ref$ObjectRef;
        this.$areEqual$inlined = function2;
        this.$$this$flow$inlined = flowCollector;
    }

    public final Object emit(Object obj, Continuation<? super Unit> continuation) {
        T t = this.$prev$inlined.element;
        if (t == NO_VALUE.INSTANCE || !((Boolean) this.$areEqual$inlined.invoke(t, obj)).booleanValue()) {
            this.$prev$inlined.element = obj;
            Object emit = this.$$this$flow$inlined.emit(obj, continuation);
            if (emit == CoroutineSingletons.COROUTINE_SUSPENDED) {
                return emit;
            }
        }
        return Unit.INSTANCE;
    }
}
