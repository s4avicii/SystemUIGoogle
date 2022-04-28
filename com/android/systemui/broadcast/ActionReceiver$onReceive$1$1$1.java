package com.android.systemui.broadcast;

import android.content.Context;
import android.content.Intent;
import com.android.systemui.broadcast.logging.BroadcastDispatcherLogger;
import java.util.Objects;

/* compiled from: ActionReceiver.kt */
public final class ActionReceiver$onReceive$1$1$1 implements Runnable {
    public final /* synthetic */ Context $context;
    public final /* synthetic */ int $id;
    public final /* synthetic */ Intent $intent;
    public final /* synthetic */ ReceiverData $it;
    public final /* synthetic */ ActionReceiver this$0;

    public ActionReceiver$onReceive$1$1$1(ReceiverData receiverData, ActionReceiver actionReceiver, Context context, Intent intent, int i) {
        this.$it = receiverData;
        this.this$0 = actionReceiver;
        this.$context = context;
        this.$intent = intent;
        this.$id = i;
    }

    public final void run() {
        ReceiverData receiverData = this.$it;
        Objects.requireNonNull(receiverData);
        receiverData.receiver.setPendingResult(this.this$0.getPendingResult());
        ReceiverData receiverData2 = this.$it;
        Objects.requireNonNull(receiverData2);
        receiverData2.receiver.onReceive(this.$context, this.$intent);
        ActionReceiver actionReceiver = this.this$0;
        BroadcastDispatcherLogger broadcastDispatcherLogger = actionReceiver.logger;
        int i = this.$id;
        String str = actionReceiver.action;
        ReceiverData receiverData3 = this.$it;
        Objects.requireNonNull(receiverData3);
        broadcastDispatcherLogger.logBroadcastDispatched(i, str, receiverData3.receiver);
    }
}
