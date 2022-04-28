package com.google.android.systemui.statusbar.notification.voicereplies;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.MutableStateFlow;

/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$hasCandidate$2$1$1$invokeSuspend$$inlined$collect$1 */
/* compiled from: Collect.kt */
public final class C2366x7e025412 implements FlowCollector<CtaState> {
    public final /* synthetic */ MutableStateFlow $ctaStateFlow$inlined;

    public C2366x7e025412(MutableStateFlow mutableStateFlow) {
        this.$ctaStateFlow$inlined = mutableStateFlow;
    }

    public final Object emit(CtaState ctaState, Continuation<? super Unit> continuation) {
        this.$ctaStateFlow$inlined.setValue(ctaState);
        return Unit.INSTANCE;
    }
}
