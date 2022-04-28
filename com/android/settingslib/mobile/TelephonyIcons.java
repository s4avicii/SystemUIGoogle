package com.android.settingslib.mobile;

import androidx.leanback.R$layout;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.SignalIcon$MobileIconGroup;
import java.util.HashMap;

public final class TelephonyIcons {
    public static final SignalIcon$MobileIconGroup CARRIER_MERGED_WIFI;
    public static final SignalIcon$MobileIconGroup CARRIER_NETWORK_CHANGE;
    public static final SignalIcon$MobileIconGroup DATA_DISABLED;

    /* renamed from: E */
    public static final SignalIcon$MobileIconGroup f33E;
    public static final SignalIcon$MobileIconGroup FOUR_G;
    public static final SignalIcon$MobileIconGroup FOUR_G_PLUS;

    /* renamed from: G */
    public static final SignalIcon$MobileIconGroup f34G;

    /* renamed from: H */
    public static final SignalIcon$MobileIconGroup f35H;
    public static final SignalIcon$MobileIconGroup H_PLUS;
    public static final HashMap ICON_NAME_TO_ICON;
    public static final SignalIcon$MobileIconGroup LTE;
    public static final SignalIcon$MobileIconGroup LTE_CA_5G_E;
    public static final SignalIcon$MobileIconGroup LTE_PLUS;
    public static final int[] MOBILE_CALL_STRENGTH_ICONS = {C1777R.C1778drawable.ic_mobile_call_strength_0, C1777R.C1778drawable.ic_mobile_call_strength_1, C1777R.C1778drawable.ic_mobile_call_strength_2, C1777R.C1778drawable.ic_mobile_call_strength_3, C1777R.C1778drawable.ic_mobile_call_strength_4};
    public static final SignalIcon$MobileIconGroup NOT_DEFAULT_DATA;
    public static final SignalIcon$MobileIconGroup NR_5G;
    public static final SignalIcon$MobileIconGroup NR_5G_PLUS;
    public static final SignalIcon$MobileIconGroup ONE_X;
    public static final SignalIcon$MobileIconGroup THREE_G;
    public static final SignalIcon$MobileIconGroup UNKNOWN;
    public static final SignalIcon$MobileIconGroup WFC;
    public static final int[] WIFI_CALL_STRENGTH_ICONS = {C1777R.C1778drawable.ic_wifi_call_strength_0, C1777R.C1778drawable.ic_wifi_call_strength_1, C1777R.C1778drawable.ic_wifi_call_strength_2, C1777R.C1778drawable.ic_wifi_call_strength_3, C1777R.C1778drawable.ic_wifi_call_strength_4};

    static {
        int[] iArr = R$layout.PHONE_SIGNAL_STRENGTH;
        int[] iArr2 = iArr;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup = new SignalIcon$MobileIconGroup("CARRIER_NETWORK_CHANGE", iArr2, iArr[0], C1777R.string.carrier_network_change_mode, 0);
        CARRIER_NETWORK_CHANGE = signalIcon$MobileIconGroup;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup2 = new SignalIcon$MobileIconGroup("3G", iArr2, iArr[0], C1777R.string.data_connection_3g, C1777R.C1778drawable.ic_3g_mobiledata);
        THREE_G = signalIcon$MobileIconGroup2;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup3 = new SignalIcon$MobileIconGroup("WFC", iArr2, iArr[0], 0, 0);
        WFC = signalIcon$MobileIconGroup3;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup4 = new SignalIcon$MobileIconGroup("Unknown", iArr2, iArr[0], 0, 0);
        UNKNOWN = signalIcon$MobileIconGroup4;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup5 = new SignalIcon$MobileIconGroup("E", iArr2, iArr[0], C1777R.string.data_connection_edge, C1777R.C1778drawable.ic_e_mobiledata);
        f33E = signalIcon$MobileIconGroup5;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup6 = new SignalIcon$MobileIconGroup("1X", iArr2, iArr[0], C1777R.string.data_connection_cdma, C1777R.C1778drawable.ic_1x_mobiledata);
        ONE_X = signalIcon$MobileIconGroup6;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup7 = new SignalIcon$MobileIconGroup("G", iArr2, iArr[0], C1777R.string.data_connection_gprs, C1777R.C1778drawable.ic_g_mobiledata);
        f34G = signalIcon$MobileIconGroup7;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup8 = new SignalIcon$MobileIconGroup("H", iArr2, iArr[0], C1777R.string.data_connection_3_5g, C1777R.C1778drawable.ic_h_mobiledata);
        f35H = signalIcon$MobileIconGroup8;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup9 = r0;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup10 = new SignalIcon$MobileIconGroup("H+", iArr2, iArr[0], C1777R.string.data_connection_3_5g_plus, C1777R.C1778drawable.ic_h_plus_mobiledata);
        H_PLUS = signalIcon$MobileIconGroup9;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup11 = signalIcon$MobileIconGroup9;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup12 = r0;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup13 = new SignalIcon$MobileIconGroup("4G", iArr2, iArr[0], C1777R.string.data_connection_4g, C1777R.C1778drawable.ic_4g_mobiledata);
        FOUR_G = signalIcon$MobileIconGroup12;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup14 = signalIcon$MobileIconGroup12;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup15 = r0;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup16 = new SignalIcon$MobileIconGroup("4G+", iArr2, iArr[0], C1777R.string.data_connection_4g_plus, C1777R.C1778drawable.ic_4g_plus_mobiledata);
        FOUR_G_PLUS = signalIcon$MobileIconGroup15;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup17 = signalIcon$MobileIconGroup15;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup18 = r0;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup19 = new SignalIcon$MobileIconGroup("LTE", iArr2, iArr[0], C1777R.string.data_connection_lte, C1777R.C1778drawable.ic_lte_mobiledata);
        LTE = signalIcon$MobileIconGroup18;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup20 = signalIcon$MobileIconGroup18;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup21 = r0;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup22 = new SignalIcon$MobileIconGroup("LTE+", iArr2, iArr[0], C1777R.string.data_connection_lte_plus, C1777R.C1778drawable.ic_lte_plus_mobiledata);
        LTE_PLUS = signalIcon$MobileIconGroup21;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup23 = signalIcon$MobileIconGroup21;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup24 = r0;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup25 = new SignalIcon$MobileIconGroup("5Ge", iArr2, iArr[0], C1777R.string.data_connection_5ge_html, C1777R.C1778drawable.ic_5g_e_mobiledata);
        LTE_CA_5G_E = signalIcon$MobileIconGroup24;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup26 = signalIcon$MobileIconGroup24;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup27 = r0;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup28 = new SignalIcon$MobileIconGroup("5G", iArr2, iArr[0], C1777R.string.data_connection_5g, C1777R.C1778drawable.ic_5g_mobiledata);
        NR_5G = signalIcon$MobileIconGroup27;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup29 = signalIcon$MobileIconGroup27;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup30 = r0;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup31 = new SignalIcon$MobileIconGroup("5G_PLUS", iArr2, iArr[0], C1777R.string.data_connection_5g_plus, C1777R.C1778drawable.ic_5g_plus_mobiledata);
        NR_5G_PLUS = signalIcon$MobileIconGroup30;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup32 = signalIcon$MobileIconGroup30;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup33 = r0;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup34 = new SignalIcon$MobileIconGroup("DataDisabled", iArr2, iArr[0], C1777R.string.cell_data_off_content_description, 0);
        DATA_DISABLED = signalIcon$MobileIconGroup33;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup35 = signalIcon$MobileIconGroup33;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup36 = r0;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup37 = new SignalIcon$MobileIconGroup("NotDefaultData", iArr2, iArr[0], C1777R.string.not_default_data_content_description, 0);
        NOT_DEFAULT_DATA = signalIcon$MobileIconGroup36;
        CARRIER_MERGED_WIFI = new SignalIcon$MobileIconGroup("CWF", iArr2, iArr[0], C1777R.string.data_connection_carrier_wifi, C1777R.C1778drawable.ic_carrier_wifi);
        HashMap hashMap = new HashMap();
        ICON_NAME_TO_ICON = hashMap;
        hashMap.put("carrier_network_change", signalIcon$MobileIconGroup);
        hashMap.put("3g", signalIcon$MobileIconGroup2);
        hashMap.put("wfc", signalIcon$MobileIconGroup3);
        hashMap.put("unknown", signalIcon$MobileIconGroup4);
        hashMap.put("e", signalIcon$MobileIconGroup5);
        hashMap.put("1x", signalIcon$MobileIconGroup6);
        hashMap.put("g", signalIcon$MobileIconGroup7);
        hashMap.put("h", signalIcon$MobileIconGroup8);
        hashMap.put("h+", signalIcon$MobileIconGroup11);
        hashMap.put("4g", signalIcon$MobileIconGroup14);
        hashMap.put("4g+", signalIcon$MobileIconGroup17);
        hashMap.put("5ge", signalIcon$MobileIconGroup26);
        hashMap.put("lte", signalIcon$MobileIconGroup20);
        hashMap.put("lte+", signalIcon$MobileIconGroup23);
        hashMap.put("5g", signalIcon$MobileIconGroup29);
        hashMap.put("5g_plus", signalIcon$MobileIconGroup32);
        hashMap.put("datadisable", signalIcon$MobileIconGroup35);
        hashMap.put("notdefaultdata", signalIcon$MobileIconGroup36);
    }
}
