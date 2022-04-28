package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController;
import java.util.Objects;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.FlowCollector;

/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$hasCandidate$2$3$invokeSuspend$$inlined$collect$1 */
/* compiled from: Collect.kt */
public final class C2376xd914577d implements FlowCollector<String> {
    public final /* synthetic */ NotificationVoiceReplyController.Connection $this_stateMachine$inlined;
    public final /* synthetic */ NotificationVoiceReplyController this$0;

    public C2376xd914577d(NotificationVoiceReplyController notificationVoiceReplyController, NotificationVoiceReplyController.Connection connection) {
        this.this$0 = notificationVoiceReplyController;
        this.$this_stateMachine$inlined = connection;
    }

    public final Object emit(String str, Continuation<? super Unit> continuation) {
        String str2 = str;
        NotificationVoiceReplyLogger notificationVoiceReplyLogger = this.this$0.logger;
        Objects.requireNonNull(notificationVoiceReplyLogger);
        LogBuffer logBuffer = notificationVoiceReplyLogger.logBuffer;
        LogLevel logLevel = LogLevel.DEBUG;
        NotificationVoiceReplyLogger$logStatic$2 notificationVoiceReplyLogger$logStatic$2 = new NotificationVoiceReplyLogger$logStatic$2("Candidate notification was removed");
        Objects.requireNonNull(logBuffer);
        if (!logBuffer.frozen) {
            logBuffer.push(logBuffer.obtain("NotifVoiceReply", logLevel, notificationVoiceReplyLogger$logStatic$2));
        }
        NotificationVoiceReplyController.Connection connection = this.$this_stateMachine$inlined;
        Objects.requireNonNull(connection);
        connection.stateFlow.setValue(this.this$0.queryInitialState(this.$this_stateMachine$inlined));
        return Unit.INSTANCE;
    }
}
