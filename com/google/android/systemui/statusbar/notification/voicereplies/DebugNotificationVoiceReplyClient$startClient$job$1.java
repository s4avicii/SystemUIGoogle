package com.google.android.systemui.statusbar.notification.voicereplies;

import android.content.IntentFilter;
import android.os.UserHandle;
import androidx.preference.R$color;
import com.android.systemui.broadcast.BroadcastDispatcher;
import java.util.concurrent.Executor;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.jvm.internal.Ref$IntRef;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.notification.voicereplies.DebugNotificationVoiceReplyClient$startClient$job$1", mo21074f = "NotificationVoiceReplyManager.kt", mo21075l = {1173}, mo21076m = "invokeSuspend")
/* compiled from: NotificationVoiceReplyManager.kt */
final class DebugNotificationVoiceReplyClient$startClient$job$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    public Object L$1;
    public Object L$2;
    public int label;
    public final /* synthetic */ DebugNotificationVoiceReplyClient this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DebugNotificationVoiceReplyClient$startClient$job$1(DebugNotificationVoiceReplyClient debugNotificationVoiceReplyClient, Continuation<? super DebugNotificationVoiceReplyClient$startClient$job$1> continuation) {
        super(2, continuation);
        this.this$0 = debugNotificationVoiceReplyClient;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        DebugNotificationVoiceReplyClient$startClient$job$1 debugNotificationVoiceReplyClient$startClient$job$1 = new DebugNotificationVoiceReplyClient$startClient$job$1(this.this$0, continuation);
        debugNotificationVoiceReplyClient$startClient$job$1.L$0 = obj;
        return debugNotificationVoiceReplyClient$startClient$job$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        ((DebugNotificationVoiceReplyClient$startClient$job$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        return CoroutineSingletons.COROUTINE_SUSPENDED;
    }

    public final Object invokeSuspend(Object obj) {
        DebugNotificationVoiceReplyClient debugNotificationVoiceReplyClient;
        DebugNotificationVoiceReplyClient$startClient$job$1$receiver$1 debugNotificationVoiceReplyClient$startClient$job$1$receiver$1;
        VoiceReplySubscription voiceReplySubscription;
        VoiceReplySubscription voiceReplySubscription2;
        DebugNotificationVoiceReplyClient$startClient$job$1$receiver$1 debugNotificationVoiceReplyClient$startClient$job$1$receiver$12;
        DebugNotificationVoiceReplyClient debugNotificationVoiceReplyClient2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            NotificationVoiceReplyController$connect$1 connect = this.this$0.voiceReplyInitializer.connect(coroutineScope);
            Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
            voiceReplySubscription = connect.registerHandler(new C2348x2f5c495e(this.this$0, ref$BooleanRef));
            debugNotificationVoiceReplyClient$startClient$job$1$receiver$1 = new DebugNotificationVoiceReplyClient$startClient$job$1$receiver$1(ref$BooleanRef, coroutineScope, voiceReplySubscription, new Ref$IntRef());
            BroadcastDispatcher broadcastDispatcher = this.this$0.broadcastDispatcher;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.google.android.systemui.START_VOICE_REPLY");
            BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, debugNotificationVoiceReplyClient$startClient$job$1$receiver$1, intentFilter, (Executor) null, (UserHandle) null, 28);
            debugNotificationVoiceReplyClient = this.this$0;
            try {
                this.L$0 = voiceReplySubscription;
                this.L$1 = debugNotificationVoiceReplyClient$startClient$job$1$receiver$1;
                this.L$2 = debugNotificationVoiceReplyClient;
                this.label = 1;
                CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(R$color.intercepted(this), 1);
                cancellableContinuationImpl.initCancellability();
                if (cancellableContinuationImpl.getResult() == coroutineSingletons) {
                    return coroutineSingletons;
                }
                debugNotificationVoiceReplyClient2 = debugNotificationVoiceReplyClient;
                voiceReplySubscription2 = voiceReplySubscription;
                debugNotificationVoiceReplyClient$startClient$job$1$receiver$12 = debugNotificationVoiceReplyClient$startClient$job$1$receiver$1;
            } catch (Throwable th) {
                th = th;
                debugNotificationVoiceReplyClient.broadcastDispatcher.unregisterReceiver(debugNotificationVoiceReplyClient$startClient$job$1$receiver$1);
                voiceReplySubscription.unsubscribe();
                throw th;
            }
        } else if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        } else {
            debugNotificationVoiceReplyClient2 = (DebugNotificationVoiceReplyClient) this.L$2;
            debugNotificationVoiceReplyClient$startClient$job$1$receiver$12 = (DebugNotificationVoiceReplyClient$startClient$job$1$receiver$1) this.L$1;
            voiceReplySubscription2 = (VoiceReplySubscription) this.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (Throwable th2) {
                debugNotificationVoiceReplyClient$startClient$job$1$receiver$1 = debugNotificationVoiceReplyClient$startClient$job$1$receiver$12;
                voiceReplySubscription = voiceReplySubscription2;
                th = th2;
                debugNotificationVoiceReplyClient = debugNotificationVoiceReplyClient2;
            }
        }
        throw new KotlinNothingValueException();
    }
}
