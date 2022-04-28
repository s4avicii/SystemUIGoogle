package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManager;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.ExecutorCoroutineDispatcher;
import kotlinx.coroutines.GlobalScope;
import kotlinx.coroutines.internal.MainDispatcherLoader;

/* compiled from: NotificationVoiceReplyManager.kt */
public final class DebugNotificationVoiceReplyClient implements NotificationVoiceReplyClient {
    public final BroadcastDispatcher broadcastDispatcher;
    public final NotificationLockscreenUserManager lockscreenUserManager;
    public final NotificationVoiceReplyManager.Initializer voiceReplyInitializer;

    public final SafeSubscription startClient() {
        GlobalScope globalScope = GlobalScope.INSTANCE;
        ExecutorCoroutineDispatcher executorCoroutineDispatcher = Dispatchers.Default;
        return new SafeSubscription(new DebugNotificationVoiceReplyClient$startClient$1(BuildersKt.launch$default(globalScope, MainDispatcherLoader.dispatcher, (CoroutineStart) null, new DebugNotificationVoiceReplyClient$startClient$job$1(this, (Continuation<? super DebugNotificationVoiceReplyClient$startClient$job$1>) null), 2)));
    }

    public DebugNotificationVoiceReplyClient(BroadcastDispatcher broadcastDispatcher2, NotificationLockscreenUserManager notificationLockscreenUserManager, NotificationVoiceReplyManager.Initializer initializer) {
        this.broadcastDispatcher = broadcastDispatcher2;
        this.lockscreenUserManager = notificationLockscreenUserManager;
        this.voiceReplyInitializer = initializer;
    }
}
