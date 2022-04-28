package com.google.android.systemui.columbus.actions;

import android.content.Context;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import androidx.recyclerview.widget.LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0;
import com.android.systemui.telephony.TelephonyListenerManager;
import com.google.android.systemui.columbus.feedback.FeedbackEffect;
import com.google.android.systemui.columbus.gates.SilenceAlertsDisabled;
import com.google.android.systemui.columbus.sensors.GestureSensor;
import dagger.Lazy;
import java.util.Set;

/* compiled from: SilenceCall.kt */
public final class SilenceCall extends Action {
    public boolean isPhoneRinging;
    public final SilenceCall$phoneStateListener$1 phoneStateListener = new SilenceCall$phoneStateListener$1(this);
    public final SilenceAlertsDisabled silenceAlertsDisabled;
    public final String tag = "Columbus/SilenceCall";
    public final Lazy<TelecomManager> telecomManager;
    public final Lazy<TelephonyListenerManager> telephonyListenerManager;
    public final Lazy<TelephonyManager> telephonyManager;

    public SilenceCall(Context context, SilenceAlertsDisabled silenceAlertsDisabled2, Lazy<TelecomManager> lazy, Lazy<TelephonyManager> lazy2, Lazy<TelephonyListenerManager> lazy3) {
        super(context, (Set<? extends FeedbackEffect>) null);
        this.silenceAlertsDisabled = silenceAlertsDisabled2;
        this.telecomManager = lazy;
        this.telephonyManager = lazy2;
        this.telephonyListenerManager = lazy3;
        silenceAlertsDisabled2.registerListener(new SilenceCall$gateListener$1(this));
        updatePhoneStateListener();
    }

    public final void onTrigger(GestureSensor.DetectionProperties detectionProperties) {
        this.telecomManager.get().silenceRinger();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(" [isPhoneRinging -> ");
        return LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0.m21m(sb, this.isPhoneRinging, ']');
    }

    public final void updatePhoneStateListener() {
        boolean z;
        if (this.silenceAlertsDisabled.isBlocking()) {
            this.telephonyListenerManager.get().removeCallStateListener(this.phoneStateListener);
        } else {
            this.telephonyListenerManager.get().addCallStateListener(this.phoneStateListener);
        }
        boolean z2 = false;
        if (this.telephonyManager.get().getCallState() == 1) {
            z = true;
        } else {
            z = false;
        }
        this.isPhoneRinging = z;
        if (!this.silenceAlertsDisabled.isBlocking() && this.isPhoneRinging) {
            z2 = true;
        }
        setAvailable(z2);
    }

    /* renamed from: getTag$vendor__unbundled_google__packages__SystemUIGoogle__android_common__sysuig */
    public final String mo19228xa00bbd41() {
        return this.tag;
    }
}
