package com.google.android.systemui.statusbar;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyLogger;
import com.google.android.systemui.statusbar.notification.voicereplies.SafeSubscription;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.MainCoroutineDispatcher;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.internal.ContextScope;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1$registerCallbacks$1", mo21074f = "NotificationVoiceReplyManagerService.kt", mo21075l = {}, mo21076m = "invokeSuspend")
/* renamed from: com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1$registerCallbacks$1 */
/* compiled from: NotificationVoiceReplyManagerService.kt */
final class C2336xfdf5bb64 extends SuspendLambda implements Function1<Continuation<? super Unit>, Object> {
    public final /* synthetic */ INotificationVoiceReplyServiceCallbacks $callbacks;
    public int label;
    public final /* synthetic */ NotificationVoiceReplyManagerService this$0;
    public final /* synthetic */ NotificationVoiceReplyManagerService$binder$1 this$1;

    @DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1$registerCallbacks$1$1", mo21074f = "NotificationVoiceReplyManagerService.kt", mo21075l = {}, mo21076m = "invokeSuspend")
    /* renamed from: com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1$registerCallbacks$1$1 */
    /* compiled from: NotificationVoiceReplyManagerService.kt */
    public static final class C23371 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        public int label;

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C23371 r0 = new C23371(iNotificationVoiceReplyServiceCallbacks, notificationVoiceReplyManagerService$binder$1, continuation);
            r0.L$0 = obj;
            return r0;
        }

        public final Object invoke(Object obj, Object obj2) {
            Unit unit = Unit.INSTANCE;
            ((C23371) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(unit);
            return unit;
        }

        public final Object invokeSuspend(Object obj) {
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                final StandaloneCoroutine launch$default = BuildersKt.launch$default((CoroutineScope) this.L$0, (MainCoroutineDispatcher) null, CoroutineStart.LAZY, new C2339x7f3fd138(notificationVoiceReplyManagerService$binder$1, iNotificationVoiceReplyServiceCallbacks, ref$ObjectRef, (Continuation<? super C2339x7f3fd138>) null), 1);
                try {
                    IBinder asBinder = iNotificationVoiceReplyServiceCallbacks.asBinder();
                    C23381 r1 = new Function0<Unit>() {
                        public final Object invoke() {
                            launch$default.cancel((CancellationException) null);
                            return Unit.INSTANCE;
                        }
                    };
                    byte[][] bArr = NotificationVoiceReplyManagerServiceKt.AGSA_CERTS;
                    NotificationVoiceReplyManagerServiceKt$onDeath$recipient$1 notificationVoiceReplyManagerServiceKt$onDeath$recipient$1 = new NotificationVoiceReplyManagerServiceKt$onDeath$recipient$1(r1);
                    asBinder.linkToDeath(notificationVoiceReplyManagerServiceKt$onDeath$recipient$1, 0);
                    ref$ObjectRef.element = new SafeSubscription(new NotificationVoiceReplyManagerServiceKt$onDeath$1(asBinder, notificationVoiceReplyManagerServiceKt$onDeath$recipient$1));
                    launch$default.start();
                } catch (RemoteException unused) {
                    launch$default.cancel((CancellationException) null);
                }
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C2336xfdf5bb64(NotificationVoiceReplyManagerService notificationVoiceReplyManagerService, NotificationVoiceReplyManagerService$binder$1 notificationVoiceReplyManagerService$binder$1, INotificationVoiceReplyServiceCallbacks iNotificationVoiceReplyServiceCallbacks, Continuation<? super C2336xfdf5bb64> continuation) {
        super(1, continuation);
        this.this$0 = notificationVoiceReplyManagerService;
        this.this$1 = notificationVoiceReplyManagerService$binder$1;
        this.$callbacks = iNotificationVoiceReplyServiceCallbacks;
    }

    public final Object invoke(Object obj) {
        C2336xfdf5bb64 notificationVoiceReplyManagerService$binder$1$registerCallbacks$1 = new C2336xfdf5bb64(this.this$0, this.this$1, this.$callbacks, (Continuation) obj);
        Unit unit = Unit.INSTANCE;
        notificationVoiceReplyManagerService$binder$1$registerCallbacks$1.invokeSuspend(unit);
        return unit;
    }

    public final Object invokeSuspend(Object obj) {
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            NotificationVoiceReplyLogger notificationVoiceReplyLogger = this.this$0.logger;
            Objects.requireNonNull(this.this$1);
            notificationVoiceReplyLogger.logRegisterCallbacks(NotificationVoiceReplyManagerService$binder$1.getUserId());
            ContextScope contextScope = this.this$0.scope;
            final INotificationVoiceReplyServiceCallbacks iNotificationVoiceReplyServiceCallbacks = this.$callbacks;
            final NotificationVoiceReplyManagerService$binder$1 notificationVoiceReplyManagerService$binder$1 = this.this$1;
            BuildersKt.launch$default(contextScope, (MainCoroutineDispatcher) null, (CoroutineStart) null, new C23371((Continuation<? super C23371>) null), 3);
            return Unit.INSTANCE;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}
