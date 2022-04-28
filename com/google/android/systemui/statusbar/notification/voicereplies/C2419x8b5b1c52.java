package com.google.android.systemui.statusbar.notification.voicereplies;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.Channel;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$hunStateChanges$1$1", mo21074f = "NotificationVoiceReplyManager.kt", mo21075l = {441}, mo21076m = "invokeSuspend")
/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$hunStateChanges$1$1 */
/* compiled from: NotificationVoiceReplyManager.kt */
public final class C2419x8b5b1c52 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ Channel<Pair<NotificationEntry, Boolean>> $chan;
    public int label;
    public final /* synthetic */ NotificationVoiceReplyController this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C2419x8b5b1c52(NotificationVoiceReplyController notificationVoiceReplyController, Channel<Pair<NotificationEntry, Boolean>> channel, Continuation<? super C2419x8b5b1c52> continuation) {
        super(2, continuation);
        this.this$0 = notificationVoiceReplyController;
        this.$chan = channel;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new C2419x8b5b1c52(this.this$0, this.$chan, continuation);
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((C2419x8b5b1c52) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            HeadsUpManager headsUpManager = this.this$0.headsUpManager;
            Channel<Pair<NotificationEntry, Boolean>> channel = this.$chan;
            this.label = 1;
            NotificationVoiceReplyManagerKt.access$sendHunStateChanges(headsUpManager, channel, this);
            return coroutineSingletons;
        } else if (i == 1) {
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
