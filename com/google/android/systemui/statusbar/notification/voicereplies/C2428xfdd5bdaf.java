package com.google.android.systemui.statusbar.notification.voicereplies;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.flow.FlowCollector;

/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManagerKt$merge$2$1$1$invokeSuspend$$inlined$collect$1 */
/* compiled from: Collect.kt */
public final class C2428xfdd5bdaf implements FlowCollector<Object> {
    public final /* synthetic */ SendChannel $chan$inlined;

    public C2428xfdd5bdaf(SendChannel sendChannel) {
        this.$chan$inlined = sendChannel;
    }

    public final Object emit(Object obj, Continuation<? super Unit> continuation) {
        Object send = this.$chan$inlined.send(obj, continuation);
        if (send == CoroutineSingletons.COROUTINE_SUSPENDED) {
            return send;
        }
        return Unit.INSTANCE;
    }
}
