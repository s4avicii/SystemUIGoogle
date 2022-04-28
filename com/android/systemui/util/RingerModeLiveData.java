package com.android.systemui.util;

import android.content.IntentFilter;
import android.os.UserHandle;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.broadcast.BroadcastDispatcher;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function0;

/* compiled from: RingerModeTrackerImpl.kt */
public final class RingerModeLiveData extends MutableLiveData<Integer> {
    public final BroadcastDispatcher broadcastDispatcher;
    public final Executor executor;
    public final IntentFilter filter;
    public final Function0<Integer> getter;
    public boolean initialSticky;
    public final RingerModeLiveData$receiver$1 receiver = new RingerModeLiveData$receiver$1(this);

    public final Integer getValue() {
        Integer num = (Integer) super.getValue();
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public final void onActive() {
        BroadcastDispatcher.registerReceiver$default(this.broadcastDispatcher, this.receiver, this.filter, this.executor, UserHandle.ALL, 16);
        this.executor.execute(new RingerModeLiveData$onActive$1(this));
    }

    public final void onInactive() {
        this.broadcastDispatcher.unregisterReceiver(this.receiver);
    }

    public RingerModeLiveData(BroadcastDispatcher broadcastDispatcher2, Executor executor2, String str, Function0<Integer> function0) {
        this.broadcastDispatcher = broadcastDispatcher2;
        this.executor = executor2;
        this.getter = function0;
        this.filter = new IntentFilter(str);
    }
}
