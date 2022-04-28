package com.google.android.systemui.elmyra.gates;

import android.content.Context;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import com.android.systemui.telephony.TelephonyListenerManager;
import java.util.Objects;

public final class TelephonyActivity extends Gate {
    public boolean mIsCallBlocked;
    public final C22611 mPhoneStateListener = new TelephonyCallback.CallStateListener() {
        public final void onCallStateChanged(int i) {
            boolean z;
            Objects.requireNonNull(TelephonyActivity.this);
            if (i == 2) {
                z = true;
            } else {
                z = false;
            }
            TelephonyActivity telephonyActivity = TelephonyActivity.this;
            if (z != telephonyActivity.mIsCallBlocked) {
                telephonyActivity.mIsCallBlocked = z;
                telephonyActivity.notifyListener();
            }
        }
    };
    public final TelephonyListenerManager mTelephonyListenerManager;
    public final TelephonyManager mTelephonyManager;

    public final void onActivate() {
        boolean z;
        if (this.mTelephonyManager.getCallState() == 2) {
            z = true;
        } else {
            z = false;
        }
        this.mIsCallBlocked = z;
        this.mTelephonyListenerManager.addCallStateListener(this.mPhoneStateListener);
    }

    public final void onDeactivate() {
        this.mTelephonyListenerManager.removeCallStateListener(this.mPhoneStateListener);
    }

    public TelephonyActivity(Context context, TelephonyListenerManager telephonyListenerManager) {
        super(context);
        this.mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
        this.mTelephonyListenerManager = telephonyListenerManager;
    }

    public final boolean isBlocked() {
        return this.mIsCallBlocked;
    }
}
