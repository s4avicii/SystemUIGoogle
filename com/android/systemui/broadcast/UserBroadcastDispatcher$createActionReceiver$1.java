package com.android.systemui.broadcast;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.UserHandle;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;

/* compiled from: UserBroadcastDispatcher.kt */
public final class UserBroadcastDispatcher$createActionReceiver$1 extends Lambda implements Function2<BroadcastReceiver, IntentFilter, Unit> {
    public final /* synthetic */ int $flags;
    public final /* synthetic */ UserBroadcastDispatcher this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public UserBroadcastDispatcher$createActionReceiver$1(UserBroadcastDispatcher userBroadcastDispatcher, int i) {
        super(2);
        this.this$0 = userBroadcastDispatcher;
        this.$flags = i;
    }

    public final Object invoke(Object obj, Object obj2) {
        IntentFilter intentFilter = (IntentFilter) obj2;
        UserBroadcastDispatcher userBroadcastDispatcher = this.this$0;
        userBroadcastDispatcher.context.registerReceiverAsUser((BroadcastReceiver) obj, UserHandle.of(userBroadcastDispatcher.userId), intentFilter, (String) null, this.this$0.bgHandler, this.$flags);
        UserBroadcastDispatcher userBroadcastDispatcher2 = this.this$0;
        userBroadcastDispatcher2.logger.logContextReceiverRegistered(userBroadcastDispatcher2.userId, this.$flags, intentFilter);
        return Unit.INSTANCE;
    }
}
