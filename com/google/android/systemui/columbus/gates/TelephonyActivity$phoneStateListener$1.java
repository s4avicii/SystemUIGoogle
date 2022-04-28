package com.google.android.systemui.columbus.gates;

import android.telephony.TelephonyCallback;
import java.util.Objects;

/* compiled from: TelephonyActivity.kt */
public final class TelephonyActivity$phoneStateListener$1 implements TelephonyCallback.CallStateListener {
    public final /* synthetic */ TelephonyActivity this$0;

    public TelephonyActivity$phoneStateListener$1(TelephonyActivity telephonyActivity) {
        this.this$0 = telephonyActivity;
    }

    public final void onCallStateChanged(int i) {
        boolean z;
        TelephonyActivity telephonyActivity = this.this$0;
        Integer valueOf = Integer.valueOf(i);
        Objects.requireNonNull(telephonyActivity);
        if (valueOf != null && valueOf.intValue() == 2) {
            z = true;
        } else {
            z = false;
        }
        telephonyActivity.isCallBlocked = z;
        TelephonyActivity telephonyActivity2 = this.this$0;
        Objects.requireNonNull(telephonyActivity2);
        telephonyActivity2.setBlocking(telephonyActivity2.isCallBlocked);
    }
}
