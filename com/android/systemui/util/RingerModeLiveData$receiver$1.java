package com.android.systemui.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* compiled from: RingerModeTrackerImpl.kt */
public final class RingerModeLiveData$receiver$1 extends BroadcastReceiver {
    public final /* synthetic */ RingerModeLiveData this$0;

    public RingerModeLiveData$receiver$1(RingerModeLiveData ringerModeLiveData) {
        this.this$0 = ringerModeLiveData;
    }

    public final void onReceive(Context context, Intent intent) {
        this.this$0.initialSticky = isInitialStickyBroadcast();
        this.this$0.postValue(Integer.valueOf(intent.getIntExtra("android.media.EXTRA_RINGER_MODE", -1)));
    }
}
