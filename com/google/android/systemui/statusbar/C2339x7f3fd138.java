package com.google.android.systemui.statusbar;

import com.android.systemui.statusbar.notification.people.Subscription;
import com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManagerKt;
import java.util.Objects;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.SafeFlow;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1$registerCallbacks$1$1$cbJob$1", mo21074f = "NotificationVoiceReplyManagerService.kt", mo21075l = {66}, mo21076m = "invokeSuspend")
/* renamed from: com.google.android.systemui.statusbar.NotificationVoiceReplyManagerService$binder$1$registerCallbacks$1$1$cbJob$1 */
/* compiled from: NotificationVoiceReplyManagerService.kt */
public final class C2339x7f3fd138 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ INotificationVoiceReplyServiceCallbacks $callbacks;
    public final /* synthetic */ Ref$ObjectRef<Subscription> $onDeathSub;
    public int label;
    public final /* synthetic */ NotificationVoiceReplyManagerService$binder$1 this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C2339x7f3fd138(NotificationVoiceReplyManagerService$binder$1 notificationVoiceReplyManagerService$binder$1, INotificationVoiceReplyServiceCallbacks iNotificationVoiceReplyServiceCallbacks, Ref$ObjectRef<Subscription> ref$ObjectRef, Continuation<? super C2339x7f3fd138> continuation) {
        super(2, continuation);
        this.this$0 = notificationVoiceReplyManagerService$binder$1;
        this.$callbacks = iNotificationVoiceReplyServiceCallbacks;
        this.$onDeathSub = ref$ObjectRef;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new C2339x7f3fd138(this.this$0, this.$callbacks, this.$onDeathSub, continuation);
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((C2339x7f3fd138) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        Subscription subscription = null;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            NotificationVoiceReplyManagerService$binder$1 notificationVoiceReplyManagerService$binder$1 = this.this$0;
            INotificationVoiceReplyServiceCallbacks iNotificationVoiceReplyServiceCallbacks = this.$callbacks;
            this.label = 1;
            int i2 = NotificationVoiceReplyManagerService$binder$1.$r8$clinit;
            Objects.requireNonNull(notificationVoiceReplyManagerService$binder$1);
            C2327x5754bae notificationVoiceReplyManagerService$binder$1$registerCallbacksWhenEnabled$$inlined$filter$1 = new C2327x5754bae(notificationVoiceReplyManagerService$binder$1.setFeatureEnabledFlow, notificationVoiceReplyManagerService$binder$1);
            Pair pair = new Pair(new Integer(NotificationVoiceReplyManagerService$binder$1.getUserId()), new Integer(2));
            byte[][] bArr = NotificationVoiceReplyManagerServiceKt.AGSA_CERTS;
            Object collectLatest = NotificationVoiceReplyManagerKt.collectLatest(NotificationVoiceReplyManagerKt.distinctUntilChanged(new SafeFlow(new NotificationVoiceReplyManagerServiceKt$startingWith$1$1(pair, notificationVoiceReplyManagerService$binder$1$registerCallbacksWhenEnabled$$inlined$filter$1, (Continuation<? super NotificationVoiceReplyManagerServiceKt$startingWith$1$1>) null)), C2340x1ef5d61f.INSTANCE), new C2341x1ef5d620(notificationVoiceReplyManagerService$binder$1, iNotificationVoiceReplyServiceCallbacks, (Continuation<? super C2341x1ef5d620>) null), this);
            if (collectLatest != coroutineSingletons) {
                collectLatest = Unit.INSTANCE;
            }
            if (collectLatest == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else if (i == 1) {
            ResultKt.throwOnFailure(obj);
        } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        T t = this.$onDeathSub.element;
        if (t != null) {
            subscription = (Subscription) t;
        }
        subscription.unsubscribe();
        return Unit.INSTANCE;
    }
}
