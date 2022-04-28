package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import kotlin.Pair;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelsKt__ChannelsKt;
import kotlinx.coroutines.channels.SendChannel;

/* compiled from: NotificationVoiceReplyManager.kt */
public final class NotificationVoiceReplyManagerKt$sendHunStateChanges$listener$1 implements OnHeadsUpChangedListener {
    public final /* synthetic */ SendChannel<Pair<NotificationEntry, Boolean>> $chan;

    public NotificationVoiceReplyManagerKt$sendHunStateChanges$listener$1(Channel channel) {
        this.$chan = channel;
    }

    public final void onHeadsUpStateChanged(NotificationEntry notificationEntry, boolean z) {
        ChannelsKt__ChannelsKt.sendBlocking(this.$chan, new Pair(notificationEntry, Boolean.valueOf(z)));
    }
}
