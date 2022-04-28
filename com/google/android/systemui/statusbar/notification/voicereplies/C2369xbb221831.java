package com.google.android.systemui.statusbar.notification.voicereplies;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.MutableStateFlow;

/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$hasCandidate$2$1$2$invokeSuspend$$inlined$collect$1 */
/* compiled from: Collect.kt */
public final class C2369xbb221831 implements FlowCollector<Unit> {
    public final /* synthetic */ MutableStateFlow $ctaStateFlow$inlined;

    public C2369xbb221831(MutableStateFlow mutableStateFlow) {
        this.$ctaStateFlow$inlined = mutableStateFlow;
    }

    public final Object emit(Unit unit, Continuation<? super Unit> continuation) {
        Unit unit2 = unit;
        if (this.$ctaStateFlow$inlined.getValue() == CtaState.QUICK_PHRASE) {
            this.$ctaStateFlow$inlined.setValue(CtaState.HOTWORD);
        }
        return Unit.INSTANCE;
    }
}
