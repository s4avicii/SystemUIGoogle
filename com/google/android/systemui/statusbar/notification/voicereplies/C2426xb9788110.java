package com.google.android.systemui.statusbar.notification.voicereplies;

import java.util.concurrent.CancellationException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.MainCoroutineDispatcher;
import kotlinx.coroutines.flow.FlowCollector;

/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyManagerKt$collectLatest$2$invokeSuspend$$inlined$collect$1 */
/* compiled from: Collect.kt */
public final class C2426xb9788110 implements FlowCollector<T> {
    public final /* synthetic */ CoroutineScope $$this$coroutineScope$inlined;
    public final /* synthetic */ Function2 $collector$inlined;
    public final /* synthetic */ Ref$ObjectRef $job$inlined;

    public C2426xb9788110(Ref$ObjectRef ref$ObjectRef, CoroutineScope coroutineScope, Function2 function2) {
        this.$job$inlined = ref$ObjectRef;
        this.$$this$coroutineScope$inlined = coroutineScope;
        this.$collector$inlined = function2;
    }

    public final Object emit(T t, Continuation<? super Unit> continuation) {
        Job job = (Job) this.$job$inlined.element;
        if (job != null) {
            job.cancel((CancellationException) null);
        }
        this.$job$inlined.element = BuildersKt.launch$default(this.$$this$coroutineScope$inlined, (MainCoroutineDispatcher) null, (CoroutineStart) null, new NotificationVoiceReplyManagerKt$collectLatest$2$1$1(this.$collector$inlined, t, (Continuation<? super NotificationVoiceReplyManagerKt$collectLatest$2$1$1>) null), 3);
        return Unit.INSTANCE;
    }
}
