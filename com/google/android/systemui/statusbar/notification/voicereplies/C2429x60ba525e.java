package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.plugins.statusbar.StatusBarStateController;
import kotlinx.coroutines.channels.Channel;

/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManagerKt$getDozeStateChanges$1$1$listener$1 */
/* compiled from: NotificationVoiceReplyManager.kt */
public final class C2429x60ba525e implements StatusBarStateController.StateListener {
    public final /* synthetic */ Channel<Boolean> $chan;

    public C2429x60ba525e(Channel<Boolean> channel) {
        this.$chan = channel;
    }

    public final void onDozingChanged(boolean z) {
        this.$chan.offer(Boolean.valueOf(z));
    }
}
