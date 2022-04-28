package com.google.android.systemui.statusbar.notification.voicereplies;

import androidx.preference.R$color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IteratorsJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.SafeFlow;

@DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$notifyHandlersOfQuickPhraseReplyAvailability$6", mo21074f = "NotificationVoiceReplyManager.kt", mo21075l = {330}, mo21076m = "invokeSuspend")
/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$notifyHandlersOfQuickPhraseReplyAvailability$6 */
/* compiled from: NotificationVoiceReplyManager.kt */
final class C2399xb38f1f7f extends SuspendLambda implements Function2<VoiceReplyTarget, Continuation<? super Unit>, Object> {
    public /* synthetic */ Object L$0;
    public int label;
    public final /* synthetic */ NotificationVoiceReplyController this$0;

    @DebugMetadata(mo21073c = "com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$notifyHandlersOfQuickPhraseReplyAvailability$6$4", mo21074f = "NotificationVoiceReplyManager.kt", mo21075l = {1174}, mo21076m = "invokeSuspend")
    /* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$notifyHandlersOfQuickPhraseReplyAvailability$6$4 */
    /* compiled from: NotificationVoiceReplyManager.kt */
    public static final class C24004 extends SuspendLambda implements Function2<Boolean, Continuation<? super Unit>, Object> {
        public Object L$0;
        public Object L$1;
        public /* synthetic */ boolean Z$0;
        public int label;

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C24004 r0 = new C24004(notificationVoiceReplyController, voiceReplyTarget, continuation);
            r0.Z$0 = ((Boolean) obj).booleanValue();
            return r0;
        }

        public final Object invoke(Object obj, Object obj2) {
            return ((C24004) create(Boolean.valueOf(((Boolean) obj).booleanValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object obj) {
            Throwable th;
            NotificationVoiceReplyController notificationVoiceReplyController;
            VoiceReplyTarget voiceReplyTarget;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                if (!this.Z$0) {
                    return Unit.INSTANCE;
                }
                NotificationVoiceReplyLogger notificationVoiceReplyLogger = notificationVoiceReplyController.logger;
                VoiceReplyTarget voiceReplyTarget2 = voiceReplyTarget;
                Objects.requireNonNull(voiceReplyTarget2);
                notificationVoiceReplyLogger.logQuickPhraseAvailabilityChanged(voiceReplyTarget2.userId, true);
                VoiceReplyTarget voiceReplyTarget3 = voiceReplyTarget;
                Objects.requireNonNull(voiceReplyTarget3);
                for (NotificationVoiceReplyHandler onNotifAvailableForQuickPhraseReplyChanged : voiceReplyTarget3.handlers) {
                    onNotifAvailableForQuickPhraseReplyChanged.onNotifAvailableForQuickPhraseReplyChanged(true);
                }
                NotificationVoiceReplyController notificationVoiceReplyController2 = notificationVoiceReplyController;
                VoiceReplyTarget voiceReplyTarget4 = voiceReplyTarget;
                try {
                    this.L$0 = notificationVoiceReplyController2;
                    this.L$1 = voiceReplyTarget4;
                    this.label = 1;
                    CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(R$color.intercepted(this), 1);
                    cancellableContinuationImpl.initCancellability();
                    if (cancellableContinuationImpl.getResult() == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    notificationVoiceReplyController = notificationVoiceReplyController2;
                    voiceReplyTarget = voiceReplyTarget4;
                } catch (Throwable th2) {
                    voiceReplyTarget = voiceReplyTarget4;
                    NotificationVoiceReplyController notificationVoiceReplyController3 = notificationVoiceReplyController2;
                    th = th2;
                    notificationVoiceReplyController = notificationVoiceReplyController3;
                    NotificationVoiceReplyLogger notificationVoiceReplyLogger2 = notificationVoiceReplyController.logger;
                    Objects.requireNonNull(voiceReplyTarget);
                    notificationVoiceReplyLogger2.logQuickPhraseAvailabilityChanged(voiceReplyTarget.userId, false);
                    for (NotificationVoiceReplyHandler onNotifAvailableForQuickPhraseReplyChanged2 : voiceReplyTarget.handlers) {
                        onNotifAvailableForQuickPhraseReplyChanged2.onNotifAvailableForQuickPhraseReplyChanged(false);
                    }
                    throw th;
                }
            } else if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            } else {
                voiceReplyTarget = (VoiceReplyTarget) this.L$1;
                notificationVoiceReplyController = (NotificationVoiceReplyController) this.L$0;
                try {
                    ResultKt.throwOnFailure(obj);
                } catch (Throwable th3) {
                    th = th3;
                }
            }
            throw new KotlinNothingValueException();
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C2399xb38f1f7f(NotificationVoiceReplyController notificationVoiceReplyController, Continuation<? super C2399xb38f1f7f> continuation) {
        super(2, continuation);
        this.this$0 = notificationVoiceReplyController;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        C2399xb38f1f7f notificationVoiceReplyController$notifyHandlersOfQuickPhraseReplyAvailability$6 = new C2399xb38f1f7f(this.this$0, continuation);
        notificationVoiceReplyController$notifyHandlersOfQuickPhraseReplyAvailability$6.L$0 = obj;
        return notificationVoiceReplyController$notifyHandlersOfQuickPhraseReplyAvailability$6;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((C2399xb38f1f7f) create((VoiceReplyTarget) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final VoiceReplyTarget voiceReplyTarget = (VoiceReplyTarget) this.L$0;
            if (voiceReplyTarget == null) {
                return Unit.INSTANCE;
            }
            List<NotificationVoiceReplyHandler> list = voiceReplyTarget.handlers;
            ArrayList arrayList = new ArrayList(CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(list, 10));
            for (NotificationVoiceReplyHandler showCta : list) {
                arrayList.add(showCta.getShowCta());
            }
            Object[] array = CollectionsKt___CollectionsKt.toList(arrayList).toArray(new Flow[0]);
            Objects.requireNonNull(array, "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
            SafeFlow distinctUntilChanged = NotificationVoiceReplyManagerKt.distinctUntilChanged(new C2363x28f04751(new C2360xcff0be74((Flow[]) array)), NotificationVoiceReplyManagerKt$distinctUntilChanged$1.INSTANCE);
            final NotificationVoiceReplyController notificationVoiceReplyController = this.this$0;
            C24004 r3 = new C24004((Continuation<? super C24004>) null);
            this.label = 1;
            if (NotificationVoiceReplyManagerKt.collectLatest(distinctUntilChanged, r3, this) == coroutineSingletons) {
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
