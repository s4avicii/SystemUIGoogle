package com.google.android.systemui.statusbar;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.flow.FlowCollector;

/* renamed from: com.google.android.systemui.statusbar.NotificationVoiceReplyManagerServiceKt$startingWith$1$1$invokeSuspend$$inlined$collect$1 */
/* compiled from: Collect.kt */
public final class C2343xb1bdc2ce implements FlowCollector<Object> {
    public final /* synthetic */ FlowCollector $receiver$inlined;

    public C2343xb1bdc2ce(FlowCollector flowCollector) {
        this.$receiver$inlined = flowCollector;
    }

    public final Object emit(Object obj, Continuation<? super Unit> continuation) {
        Object emit = this.$receiver$inlined.emit(obj, continuation);
        if (emit == CoroutineSingletons.COROUTINE_SUSPENDED) {
            return emit;
        }
        return Unit.INSTANCE;
    }
}
