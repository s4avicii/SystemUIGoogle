package com.google.android.systemui.statusbar;

import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationVoiceReplyManagerService.kt */
final class NotificationVoiceReplyManagerService$binder$1$startVoiceReply$3 extends Lambda implements Function0<Unit> {
    public final /* synthetic */ CallbacksHandler $handler;
    public final /* synthetic */ int $token;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public NotificationVoiceReplyManagerService$binder$1$startVoiceReply$3(CallbacksHandler callbacksHandler, int i) {
        super(0);
        this.$handler = callbacksHandler;
        this.$token = i;
    }

    public final Object invoke() {
        CallbacksHandler callbacksHandler = this.$handler;
        int i = this.$token;
        Objects.requireNonNull(callbacksHandler);
        callbacksHandler.callbacks.onVoiceReplyHandled(i, 1);
        return Unit.INSTANCE;
    }
}
