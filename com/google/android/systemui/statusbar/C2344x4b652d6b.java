package com.google.android.systemui.statusbar;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.MutableStateFlow;

/* renamed from: com.google.android.systemui.statusbar.NotificationVoiceReplyManagerServiceKt$stateIn$1$invokeSuspend$$inlined$collect$1 */
/* compiled from: Collect.kt */
public final class C2344x4b652d6b implements FlowCollector<Object> {
    public final /* synthetic */ MutableStateFlow $stateFlow$inlined;

    public C2344x4b652d6b(MutableStateFlow mutableStateFlow) {
        this.$stateFlow$inlined = mutableStateFlow;
    }

    public final Object emit(Object obj, Continuation<? super Unit> continuation) {
        this.$stateFlow$inlined.setValue(obj);
        return Unit.INSTANCE;
    }
}
