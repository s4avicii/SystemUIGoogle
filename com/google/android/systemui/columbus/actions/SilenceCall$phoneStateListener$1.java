package com.google.android.systemui.columbus.actions;

import android.telephony.TelephonyCallback;
import java.util.Objects;

/* compiled from: SilenceCall.kt */
public final class SilenceCall$phoneStateListener$1 implements TelephonyCallback.CallStateListener {
    public final /* synthetic */ SilenceCall this$0;

    public SilenceCall$phoneStateListener$1(SilenceCall silenceCall) {
        this.this$0 = silenceCall;
    }

    public final void onCallStateChanged(int i) {
        boolean z;
        SilenceCall silenceCall = this.this$0;
        Objects.requireNonNull(silenceCall);
        boolean z2 = false;
        if (i == 1) {
            z = true;
        } else {
            z = false;
        }
        silenceCall.isPhoneRinging = z;
        SilenceCall silenceCall2 = this.this$0;
        Objects.requireNonNull(silenceCall2);
        if (!silenceCall2.silenceAlertsDisabled.isBlocking() && silenceCall2.isPhoneRinging) {
            z2 = true;
        }
        silenceCall2.setAvailable(z2);
    }
}
