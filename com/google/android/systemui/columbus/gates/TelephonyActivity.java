package com.google.android.systemui.columbus.gates;

import android.content.Context;
import android.telephony.TelephonyManager;
import com.android.systemui.telephony.TelephonyListenerManager;
import dagger.Lazy;

/* compiled from: TelephonyActivity.kt */
public final class TelephonyActivity extends Gate {
    public boolean isCallBlocked;
    public final TelephonyActivity$phoneStateListener$1 phoneStateListener = new TelephonyActivity$phoneStateListener$1(this);
    public final Lazy<TelephonyListenerManager> telephonyListenerManager;
    public final Lazy<TelephonyManager> telephonyManager;

    public final void onActivate() {
        boolean z;
        Integer valueOf = Integer.valueOf(this.telephonyManager.get().getCallState());
        if (valueOf != null && valueOf.intValue() == 2) {
            z = true;
        } else {
            z = false;
        }
        this.isCallBlocked = z;
        this.telephonyListenerManager.get().addCallStateListener(this.phoneStateListener);
        setBlocking(this.isCallBlocked);
    }

    public final void onDeactivate() {
        this.telephonyListenerManager.get().removeCallStateListener(this.phoneStateListener);
    }

    public TelephonyActivity(Context context, Lazy<TelephonyManager> lazy, Lazy<TelephonyListenerManager> lazy2) {
        super(context);
        this.telephonyManager = lazy;
        this.telephonyListenerManager = lazy2;
    }
}
