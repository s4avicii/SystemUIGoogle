package com.google.android.systemui.statusbar.notification.voicereplies;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

/* compiled from: NotificationVoiceReplyManager.kt */
public interface NotificationVoiceReplyManager extends Job {

    /* compiled from: NotificationVoiceReplyManager.kt */
    public interface Initializer {
        NotificationVoiceReplyController$connect$1 connect(CoroutineScope coroutineScope);
    }

    NotificationVoiceReplyController$connect$1$registerHandler$1 registerHandler(NotificationVoiceReplyHandler notificationVoiceReplyHandler);

    Object requestHideQuickPhraseCTA(Continuation<? super Unit> continuation);
}
