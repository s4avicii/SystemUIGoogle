package com.google.android.systemui.statusbar.notification.voicereplies;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.jvm.internal.Ref$IntRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.MainCoroutineDispatcher;

/* compiled from: NotificationVoiceReplyManager.kt */
public final class DebugNotificationVoiceReplyClient$startClient$job$1$receiver$1 extends BroadcastReceiver {
    public final /* synthetic */ CoroutineScope $$this$launch;
    public final /* synthetic */ Ref$BooleanRef $notifAvailable;
    public final /* synthetic */ VoiceReplySubscription $subscription;
    public final /* synthetic */ Ref$IntRef $token;

    public DebugNotificationVoiceReplyClient$startClient$job$1$receiver$1(Ref$BooleanRef ref$BooleanRef, CoroutineScope coroutineScope, VoiceReplySubscription voiceReplySubscription, Ref$IntRef ref$IntRef) {
        this.$notifAvailable = ref$BooleanRef;
        this.$$this$launch = coroutineScope;
        this.$subscription = voiceReplySubscription;
        this.$token = ref$IntRef;
    }

    public final void onReceive(Context context, Intent intent) {
        if (!this.$notifAvailable.element) {
            Log.d("NotifVoiceReplyDebug", "no notification available for voice reply");
        }
        BuildersKt.launch$default(this.$$this$launch, (MainCoroutineDispatcher) null, (CoroutineStart) null, new C2345xdb07489d(this.$subscription, this.$token, (Continuation<? super C2345xdb07489d>) null), 3);
    }
}
