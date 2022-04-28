package com.google.android.systemui.statusbar.notification.voicereplies;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.flow.Flow;

/* renamed from: com.google.android.systemui.statusbar.notification.voicereplies.NotificationVoiceReplyController$stateMachine$2$hasCandidate$2$1$1$invokeSuspend$$inlined$combine$1$2 */
/* compiled from: Zip.kt */
public final class C2367x71c125b5 extends Lambda implements Function0<CtaState[]> {
    public final /* synthetic */ Flow[] $flowArray;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C2367x71c125b5(Flow[] flowArr) {
        super(0);
        this.$flowArray = flowArr;
    }

    public final Object invoke() {
        return new CtaState[this.$flowArray.length];
    }
}
