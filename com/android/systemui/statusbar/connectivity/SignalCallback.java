package com.android.systemui.statusbar.connectivity;

import android.telephony.SubscriptionInfo;
import java.util.List;

/* compiled from: SignalCallback.kt */
public interface SignalCallback {
    void setCallIndicator(IconState iconState, int i) {
    }

    void setConnectivityStatus(boolean z, boolean z2, boolean z3) {
    }

    void setEthernetIndicators(IconState iconState) {
    }

    void setIsAirplaneMode(IconState iconState) {
    }

    void setMobileDataEnabled(boolean z) {
    }

    void setMobileDataIndicators(MobileDataIndicators mobileDataIndicators) {
    }

    void setNoSims(boolean z, boolean z2) {
    }

    void setSubs(List<SubscriptionInfo> list) {
    }

    void setWifiIndicators(WifiIndicators wifiIndicators) {
    }
}
