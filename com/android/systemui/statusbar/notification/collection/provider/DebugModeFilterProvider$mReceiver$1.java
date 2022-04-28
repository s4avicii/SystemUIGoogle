package com.android.systemui.statusbar.notification.collection.provider;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DebugModeFilterProvider.kt */
public final class DebugModeFilterProvider$mReceiver$1 extends BroadcastReceiver {
    public final /* synthetic */ DebugModeFilterProvider this$0;

    public final void onReceive(Context context, Intent intent) {
        String str;
        List<String> list = null;
        if (intent == null) {
            str = null;
        } else {
            str = intent.getAction();
        }
        if (Intrinsics.areEqual("com.android.systemui.action.SET_NOTIF_DEBUG_MODE", str)) {
            DebugModeFilterProvider debugModeFilterProvider = this.this$0;
            Bundle extras = intent.getExtras();
            if (extras != null) {
                list = extras.getStringArrayList("allowed_packages");
            }
            if (list == null) {
                list = EmptyList.INSTANCE;
            }
            debugModeFilterProvider.allowedPackages = list;
            Log.d("DebugModeFilterProvider", Intrinsics.stringPlus("Updated allowedPackages: ", this.this$0.allowedPackages));
            for (Runnable run : this.this$0.listeners) {
                run.run();
            }
            return;
        }
        Log.d("DebugModeFilterProvider", Intrinsics.stringPlus("Malformed intent: ", intent));
    }

    public DebugModeFilterProvider$mReceiver$1(DebugModeFilterProvider debugModeFilterProvider) {
        this.this$0 = debugModeFilterProvider;
    }
}
