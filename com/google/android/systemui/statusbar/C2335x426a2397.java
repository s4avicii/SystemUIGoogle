package com.google.android.systemui.statusbar;

import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger;
import java.util.Objects;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.SharedFlowImpl;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1$onVoiceAuthStateChanged$1", mo21074f = "NotificationVoiceReplyManagerService.kt", mo21075l = {101}, mo21076m = "invokeSuspend")
/* renamed from: com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1$onVoiceAuthStateChanged$1 */
/* compiled from: NotificationVoiceReplyManagerService.kt */
final class C2335x426a2397 extends SuspendLambda implements Function1<Continuation<? super Unit>, Object> {
    public final /* synthetic */ int $newState;
    public final /* synthetic */ int $sessionToken;
    public int label;
    public final /* synthetic */ NotificationVoiceReplyManagerService this$0;
    public final /* synthetic */ NotificationVoiceReplyManagerService$binder$1 this$1;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C2335x426a2397(NotificationVoiceReplyManagerService notificationVoiceReplyManagerService, NotificationVoiceReplyManagerService$binder$1 notificationVoiceReplyManagerService$binder$1, int i, int i2, Continuation<? super C2335x426a2397> continuation) {
        super(1, continuation);
        this.this$0 = notificationVoiceReplyManagerService;
        this.this$1 = notificationVoiceReplyManagerService$binder$1;
        this.$sessionToken = i;
        this.$newState = i2;
    }

    public final Object invoke(Object obj) {
        return new C2335x426a2397(this.this$0, this.this$1, this.$sessionToken, this.$newState, (Continuation) obj).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            NotificationVoiceReplyLogger notificationVoiceReplyLogger = this.this$0.logger;
            Objects.requireNonNull(this.this$1);
            notificationVoiceReplyLogger.logVoiceAuthStateChanged(NotificationVoiceReplyManagerService$binder$1.getUserId(), this.$sessionToken, this.$newState);
            NotificationVoiceReplyManagerService$binder$1 notificationVoiceReplyManagerService$binder$1 = this.this$1;
            Objects.requireNonNull(notificationVoiceReplyManagerService$binder$1);
            SharedFlowImpl sharedFlowImpl = notificationVoiceReplyManagerService$binder$1.onVoiceAuthStateChangedFlow;
            Objects.requireNonNull(this.this$1);
            OnVoiceAuthStateChangedData onVoiceAuthStateChangedData = new OnVoiceAuthStateChangedData(NotificationVoiceReplyManagerService$binder$1.getUserId(), this.$sessionToken, this.$newState);
            this.label = 1;
            if (sharedFlowImpl.emit(onVoiceAuthStateChangedData, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else if (i == 1) {
            ResultKt.throwOnFailure(obj);
        } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return Unit.INSTANCE;
    }
}
