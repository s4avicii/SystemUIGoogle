package com.android.systemui.broadcast;

import android.content.BroadcastReceiver;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.util.Objects;

/* compiled from: BroadcastDispatcher.kt */
public final class BroadcastDispatcher$handler$1 extends Handler {
    public final /* synthetic */ BroadcastDispatcher this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public BroadcastDispatcher$handler$1(BroadcastDispatcher broadcastDispatcher, Looper looper) {
        super(looper);
        this.this$0 = broadcastDispatcher;
    }

    public final void handleMessage(Message message) {
        int i;
        int i2 = message.what;
        int i3 = 0;
        if (i2 == 0) {
            Object obj = message.obj;
            Objects.requireNonNull(obj, "null cannot be cast to non-null type com.android.systemui.broadcast.ReceiverData");
            ReceiverData receiverData = (ReceiverData) obj;
            int i4 = message.arg1;
            if (receiverData.user.getIdentifier() == -2) {
                i = this.this$0.userTracker.getUserId();
            } else {
                i = receiverData.user.getIdentifier();
            }
            if (i >= -1) {
                BroadcastDispatcher broadcastDispatcher = this.this$0;
                UserBroadcastDispatcher userBroadcastDispatcher = broadcastDispatcher.receiversByUser.get(i, broadcastDispatcher.createUBRForUser(i));
                this.this$0.receiversByUser.put(i, userBroadcastDispatcher);
                Objects.requireNonNull(userBroadcastDispatcher);
                userBroadcastDispatcher.bgHandler.obtainMessage(0, i4, 0, receiverData).sendToTarget();
                return;
            }
            throw new IllegalStateException("Attempting to register receiver for invalid user {" + i + '}');
        } else if (i2 == 1) {
            int size = this.this$0.receiversByUser.size();
            while (i3 < size) {
                int i5 = i3 + 1;
                UserBroadcastDispatcher valueAt = this.this$0.receiversByUser.valueAt(i3);
                Object obj2 = message.obj;
                Objects.requireNonNull(obj2, "null cannot be cast to non-null type android.content.BroadcastReceiver");
                Objects.requireNonNull(valueAt);
                valueAt.bgHandler.obtainMessage(1, (BroadcastReceiver) obj2).sendToTarget();
                i3 = i5;
            }
        } else if (i2 != 2) {
            super.handleMessage(message);
        } else {
            UserBroadcastDispatcher userBroadcastDispatcher2 = this.this$0.receiversByUser.get(message.arg1);
            if (userBroadcastDispatcher2 != null) {
                Object obj3 = message.obj;
                Objects.requireNonNull(obj3, "null cannot be cast to non-null type android.content.BroadcastReceiver");
                userBroadcastDispatcher2.bgHandler.obtainMessage(1, (BroadcastReceiver) obj3).sendToTarget();
            }
        }
    }
}
