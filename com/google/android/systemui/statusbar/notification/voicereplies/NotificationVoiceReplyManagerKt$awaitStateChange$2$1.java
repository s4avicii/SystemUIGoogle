package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.NotificationShadeWindowController;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationVoiceReplyManager.kt */
final class NotificationVoiceReplyManagerKt$awaitStateChange$2$1 extends Lambda implements Function1<Throwable, Unit> {
    public final /* synthetic */ NotificationVoiceReplyManagerKt$awaitStateChange$2$cb$1 $cb;
    public final /* synthetic */ NotificationShadeWindowController $this_awaitStateChange;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public NotificationVoiceReplyManagerKt$awaitStateChange$2$1(NotificationShadeWindowController notificationShadeWindowController, NotificationVoiceReplyManagerKt$awaitStateChange$2$cb$1 notificationVoiceReplyManagerKt$awaitStateChange$2$cb$1) {
        super(1);
        this.$this_awaitStateChange = notificationShadeWindowController;
        this.$cb = notificationVoiceReplyManagerKt$awaitStateChange$2$cb$1;
    }

    public final Object invoke(Object obj) {
        Throwable th = (Throwable) obj;
        this.$this_awaitStateChange.unregisterCallback(this.$cb);
        return Unit.INSTANCE;
    }
}
