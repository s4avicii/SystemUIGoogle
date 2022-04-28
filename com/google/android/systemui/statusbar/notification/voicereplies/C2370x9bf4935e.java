package com.google.android.systemui.statusbar.notification.voicereplies;

import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController;
import java.util.Objects;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.FlowCollector;

/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$hasCandidate$2$2$invokeSuspend$$inlined$collect$1 */
/* compiled from: Collect.kt */
public final class C2370x9bf4935e implements FlowCollector<VoiceReplyState> {
    public final /* synthetic */ NotificationVoiceReplyController.Connection $this_stateMachine$inlined;

    public C2370x9bf4935e(NotificationVoiceReplyController.Connection connection) {
        this.$this_stateMachine$inlined = connection;
    }

    public final Object emit(VoiceReplyState voiceReplyState, Continuation<? super Unit> continuation) {
        NotificationVoiceReplyController.Connection connection = this.$this_stateMachine$inlined;
        Objects.requireNonNull(connection);
        connection.stateFlow.setValue(voiceReplyState);
        return Unit.INSTANCE;
    }
}
