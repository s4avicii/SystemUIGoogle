package com.google.android.systemui.statusbar.notification.voicereplies;

import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController;
import java.util.Objects;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.FlowCollector;

/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$noCandidate$2$invokeSuspend$$inlined$collect$1 */
/* compiled from: Collect.kt */
public final class C2385xe62d49e9 implements FlowCollector<VoiceReplyTarget> {
    public final /* synthetic */ NotificationVoiceReplyController.Connection $this_stateMachine$inlined;

    public C2385xe62d49e9(NotificationVoiceReplyController.Connection connection) {
        this.$this_stateMachine$inlined = connection;
    }

    public final Object emit(VoiceReplyTarget voiceReplyTarget, Continuation<? super Unit> continuation) {
        NotificationVoiceReplyController.Connection connection = this.$this_stateMachine$inlined;
        Objects.requireNonNull(connection);
        connection.stateFlow.setValue(new HasCandidate(voiceReplyTarget));
        return Unit.INSTANCE;
    }
}
