package com.android.settingslib.deviceinfo;

public abstract class AbstractWifiMacAddressPreferenceController extends AbstractConnectivityPreferenceController {
    public static final String[] CONNECTIVITY_INTENTS = {"android.net.conn.CONNECTIVITY_CHANGE", "android.net.wifi.LINK_CONFIGURATION_CHANGED", "android.net.wifi.STATE_CHANGE"};
    public static final String KEY_WIFI_MAC_ADDRESS = "wifi_mac_address";
    public static final int OFF = 0;

    /* renamed from: ON */
    public static final int f32ON = 1;

    public final String[] getConnectivityIntents() {
        return CONNECTIVITY_INTENTS;
    }
}
