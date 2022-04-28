package com.google.android.systemui.statusbar;

import com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplySubscription;
import java.util.Objects;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.MainCoroutineDispatcher;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.FlowCollector;

/* renamed from: com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2$invokeSuspend$$inlined$collect$1 */
/* compiled from: Collect.kt */
public final class C2318xec9f3859 implements FlowCollector<StartVoiceReplyData> {
    public final /* synthetic */ CoroutineScope $$this$coroutineScope$inlined;
    public final /* synthetic */ CallbacksHandler $handler$inlined;
    public final /* synthetic */ VoiceReplySubscription $registration$inlined;
    public final /* synthetic */ NotificationVoiceReplyManagerService$binder$1 this$0;

    public C2318xec9f3859(CoroutineScope coroutineScope, NotificationVoiceReplyManagerService$binder$1 notificationVoiceReplyManagerService$binder$1, VoiceReplySubscription voiceReplySubscription, CallbacksHandler callbacksHandler) {
        this.$$this$coroutineScope$inlined = coroutineScope;
        this.this$0 = notificationVoiceReplyManagerService$binder$1;
        this.$registration$inlined = voiceReplySubscription;
        this.$handler$inlined = callbacksHandler;
    }

    public final Object emit(StartVoiceReplyData startVoiceReplyData, Continuation<? super Unit> continuation) {
        StartVoiceReplyData startVoiceReplyData2 = startVoiceReplyData;
        Objects.requireNonNull(startVoiceReplyData2);
        StandaloneCoroutine launch$default = BuildersKt.launch$default(this.$$this$coroutineScope$inlined, (MainCoroutineDispatcher) null, (CoroutineStart) null, new C2333xf794a6a0(this.this$0, this.$registration$inlined, this.$handler$inlined, startVoiceReplyData2.sessionToken, startVoiceReplyData2.userMessageContent, (Continuation<? super C2333xf794a6a0>) null), 3);
        if (launch$default == CoroutineSingletons.COROUTINE_SUSPENDED) {
            return launch$default;
        }
        return Unit.INSTANCE;
    }
}
