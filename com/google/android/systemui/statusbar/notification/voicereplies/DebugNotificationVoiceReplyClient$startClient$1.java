package com.google.android.systemui.statusbar.notification.voicereplies;

import java.util.concurrent.CancellationException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: NotificationVoiceReplyManager.kt */
final class DebugNotificationVoiceReplyClient$startClient$1 extends Lambda implements Function0<Unit> {
    public final /* synthetic */ Job $job;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DebugNotificationVoiceReplyClient$startClient$1(StandaloneCoroutine standaloneCoroutine) {
        super(0);
        this.$job = standaloneCoroutine;
    }

    public final Object invoke() {
        this.$job.cancel((CancellationException) null);
        return Unit.INSTANCE;
    }
}
