package com.google.android.systemui.statusbar;

import androidx.slice.compat.SliceProviderCompat;
import com.google.android.systemui.statusbar.notification.voicereplies.CtaState;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$connect$1$registerHandler$1;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManager;
import com.google.android.systemui.statusbar.notification.voicereplies.VoiceReplySubscription;
import java.util.Objects;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.MainCoroutineDispatcher;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.StateFlowImpl;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2", mo21074f = "NotificationVoiceReplyManagerService.kt", mo21075l = {289}, mo21076m = "invokeSuspend")
/* compiled from: NotificationVoiceReplyManagerService.kt */
final class NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ INotificationVoiceReplyServiceCallbacks $callbacks;
    public final /* synthetic */ CtaState $ctaState;
    private /* synthetic */ Object L$0;
    public int label;
    public final /* synthetic */ NotificationVoiceReplyManagerService$binder$1 this$0;
    public final /* synthetic */ NotificationVoiceReplyManagerService this$1;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2(NotificationVoiceReplyManagerService$binder$1 notificationVoiceReplyManagerService$binder$1, CtaState ctaState, INotificationVoiceReplyServiceCallbacks iNotificationVoiceReplyServiceCallbacks, NotificationVoiceReplyManagerService notificationVoiceReplyManagerService, Continuation<? super NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2> continuation) {
        super(2, continuation);
        this.this$0 = notificationVoiceReplyManagerService$binder$1;
        this.$ctaState = ctaState;
        this.$callbacks = iNotificationVoiceReplyServiceCallbacks;
        this.this$1 = notificationVoiceReplyManagerService;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2 notificationVoiceReplyManagerService$binder$1$enableCallbacks$2 = new NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2(this.this$0, this.$ctaState, this.$callbacks, this.this$1, continuation);
        notificationVoiceReplyManagerService$binder$1$enableCallbacks$2.L$0 = obj;
        return notificationVoiceReplyManagerService$binder$1$enableCallbacks$2;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationVoiceReplyManagerService$binder$1$enableCallbacks$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        VoiceReplySubscription voiceReplySubscription;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            NotificationVoiceReplyManagerService$binder$1 notificationVoiceReplyManagerService$binder$1 = this.this$0;
            Objects.requireNonNull(notificationVoiceReplyManagerService$binder$1);
            C2324xa845190b notificationVoiceReplyManagerService$binder$1$enableCallbacks$2$invokeSuspend$$inlined$map$1 = new C2324xa845190b(new C2319x8eb430a3(notificationVoiceReplyManagerService$binder$1.setFeatureEnabledFlow, this.this$0));
            Object obj2 = this.$ctaState;
            byte[][] bArr = NotificationVoiceReplyManagerServiceKt.AGSA_CERTS;
            if (obj2 == null) {
                obj2 = SliceProviderCompat.C03502.NULL;
            }
            StateFlowImpl stateFlowImpl = new StateFlowImpl(obj2);
            NotificationVoiceReplyManager notificationVoiceReplyManager = null;
            BuildersKt.launch$default(coroutineScope, (MainCoroutineDispatcher) null, (CoroutineStart) null, new NotificationVoiceReplyManagerServiceKt$stateIn$1(notificationVoiceReplyManagerService$binder$1$enableCallbacks$2$invokeSuspend$$inlined$map$1, stateFlowImpl, (Continuation<? super NotificationVoiceReplyManagerServiceKt$stateIn$1>) null), 3);
            Objects.requireNonNull(this.this$0);
            CallbacksHandler callbacksHandler = new CallbacksHandler(NotificationVoiceReplyManagerService$binder$1.getUserId(), this.$callbacks, stateFlowImpl);
            NotificationVoiceReplyManager notificationVoiceReplyManager2 = this.this$1.voiceReplyManager;
            if (notificationVoiceReplyManager2 != null) {
                notificationVoiceReplyManager = notificationVoiceReplyManager2;
            }
            NotificationVoiceReplyController$connect$1$registerHandler$1 registerHandler = notificationVoiceReplyManager.registerHandler(callbacksHandler);
            try {
                NotificationVoiceReplyManagerService$binder$1 notificationVoiceReplyManagerService$binder$12 = this.this$0;
                Objects.requireNonNull(notificationVoiceReplyManagerService$binder$12);
                SharedFlowImpl sharedFlowImpl = notificationVoiceReplyManagerService$binder$12.startVoiceReplyFlow;
                NotificationVoiceReplyManagerService$binder$1 notificationVoiceReplyManagerService$binder$13 = this.this$0;
                C2318xec9f3859 notificationVoiceReplyManagerService$binder$1$enableCallbacks$2$invokeSuspend$$inlined$collect$1 = new C2318xec9f3859(coroutineScope, notificationVoiceReplyManagerService$binder$13, registerHandler, callbacksHandler);
                this.L$0 = registerHandler;
                this.label = 1;
                sharedFlowImpl.collect(new C2322xb26a9c32(notificationVoiceReplyManagerService$binder$1$enableCallbacks$2$invokeSuspend$$inlined$collect$1, notificationVoiceReplyManagerService$binder$13), this);
                return coroutineSingletons;
            } catch (Throwable th) {
                th = th;
                voiceReplySubscription = registerHandler;
                NotificationVoiceReplyLogger notificationVoiceReplyLogger = this.this$1.logger;
                Objects.requireNonNull(this.this$0);
                notificationVoiceReplyLogger.logUnregisterCallbacks(NotificationVoiceReplyManagerService$binder$1.getUserId());
                voiceReplySubscription.unsubscribe();
                throw th;
            }
        } else if (i == 1) {
            voiceReplySubscription = (VoiceReplySubscription) this.L$0;
            try {
                ResultKt.throwOnFailure(obj);
                NotificationVoiceReplyLogger notificationVoiceReplyLogger2 = this.this$1.logger;
                Objects.requireNonNull(this.this$0);
                notificationVoiceReplyLogger2.logUnregisterCallbacks(NotificationVoiceReplyManagerService$binder$1.getUserId());
                voiceReplySubscription.unsubscribe();
                return Unit.INSTANCE;
            } catch (Throwable th2) {
                th = th2;
                NotificationVoiceReplyLogger notificationVoiceReplyLogger3 = this.this$1.logger;
                Objects.requireNonNull(this.this$0);
                notificationVoiceReplyLogger3.logUnregisterCallbacks(NotificationVoiceReplyManagerService$binder$1.getUserId());
                voiceReplySubscription.unsubscribe();
                throw th;
            }
        } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
