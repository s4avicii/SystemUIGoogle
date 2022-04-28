package com.android.systemui.broadcast;

import android.content.BroadcastReceiver;
import java.util.Objects;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: ActionReceiver.kt */
final class ActionReceiver$removeReceiver$1 extends Lambda implements Function1<ReceiverData, Boolean> {
    public final /* synthetic */ BroadcastReceiver $receiver;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ActionReceiver$removeReceiver$1(BroadcastReceiver broadcastReceiver) {
        super(1);
        this.$receiver = broadcastReceiver;
    }

    public final Object invoke(Object obj) {
        ReceiverData receiverData = (ReceiverData) obj;
        Objects.requireNonNull(receiverData);
        return Boolean.valueOf(Intrinsics.areEqual(receiverData.receiver, this.$receiver));
    }
}
