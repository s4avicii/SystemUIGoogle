package com.android.systemui.broadcast;

import android.content.Context;
import android.content.Intent;
import android.util.ArraySet;
import java.util.Objects;

/* compiled from: ActionReceiver.kt */
public final class ActionReceiver$onReceive$1 implements Runnable {
    public final /* synthetic */ Context $context;
    public final /* synthetic */ int $id;
    public final /* synthetic */ Intent $intent;
    public final /* synthetic */ ActionReceiver this$0;

    public ActionReceiver$onReceive$1(ActionReceiver actionReceiver, Intent intent, Context context, int i) {
        this.this$0 = actionReceiver;
        this.$intent = intent;
        this.$context = context;
        this.$id = i;
    }

    public final void run() {
        ActionReceiver actionReceiver = this.this$0;
        ArraySet<ReceiverData> arraySet = actionReceiver.receiverDatas;
        Intent intent = this.$intent;
        Context context = this.$context;
        int i = this.$id;
        for (ReceiverData receiverData : arraySet) {
            Objects.requireNonNull(receiverData);
            if (receiverData.filter.matchCategories(intent.getCategories()) == null) {
                receiverData.executor.execute(new ActionReceiver$onReceive$1$1$1(receiverData, actionReceiver, context, intent, i));
            }
        }
    }
}
