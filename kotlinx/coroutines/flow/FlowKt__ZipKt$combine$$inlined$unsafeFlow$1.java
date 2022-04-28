package kotlinx.coroutines.flow;

import com.google.android.systemui.statusbar.notification.voicereplies.C2354x972167a3;
import com.google.android.systemui.statusbar.notification.voicereplies.C2357x972167a4;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: SafeCollector.common.kt */
public final class FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 implements Flow<Object> {
    public final /* synthetic */ Flow $flow$inlined;
    public final /* synthetic */ Flow $this_combine$inlined;
    public final /* synthetic */ Function3 $transform$inlined;

    public final Object collect(FlowCollector<Object> flowCollector, Continuation<? super Unit> continuation) {
        Object combineInternal = CombineKt.combineInternal(flowCollector, new Flow[]{this.$this_combine$inlined, this.$flow$inlined}, FlowKt__ZipKt$nullArrayFactory$1.INSTANCE, new FlowKt__ZipKt$combine$1$1(this.$transform$inlined, (Continuation<? super FlowKt__ZipKt$combine$1$1>) null), continuation);
        if (combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED) {
            return combineInternal;
        }
        return Unit.INSTANCE;
    }

    public FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(C2354x972167a3 notificationVoiceReplyController$notifyHandlersOfQuickPhraseReplyAvailability$$inlined$map$1, C2357x972167a4 notificationVoiceReplyController$notifyHandlersOfQuickPhraseReplyAvailability$$inlined$map$2, Function3 function3) {
        this.$this_combine$inlined = notificationVoiceReplyController$notifyHandlersOfQuickPhraseReplyAvailability$$inlined$map$1;
        this.$flow$inlined = notificationVoiceReplyController$notifyHandlersOfQuickPhraseReplyAvailability$$inlined$map$2;
        this.$transform$inlined = function3;
    }
}
