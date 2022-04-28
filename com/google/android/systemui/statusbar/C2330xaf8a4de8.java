package com.google.android.systemui.statusbar;

import com.google.android.systemui.statusbar.notification.voicereplies.Session;
import java.util.Objects;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.FlowCollector;

/* renamed from: com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1$startVoiceReply$4$invokeSuspend$$inlined$collect$1 */
/* compiled from: Collect.kt */
public final class C2330xaf8a4de8 implements FlowCollector<OnVoiceAuthStateChangedData> {
    public final /* synthetic */ Session $$this$startVoiceReply$inlined;

    public C2330xaf8a4de8(Session session) {
        this.$$this$startVoiceReply$inlined = session;
    }

    public final Object emit(OnVoiceAuthStateChangedData onVoiceAuthStateChangedData, Continuation<? super Unit> continuation) {
        OnVoiceAuthStateChangedData onVoiceAuthStateChangedData2 = onVoiceAuthStateChangedData;
        Objects.requireNonNull(onVoiceAuthStateChangedData2);
        this.$$this$startVoiceReply$inlined.setVoiceAuthState(onVoiceAuthStateChangedData2.newState);
        return Unit.INSTANCE;
    }
}
