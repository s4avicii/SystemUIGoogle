package com.android.settingslib.mobile;

import android.content.Context;
import android.content.res.Resources;
import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionManager;
import com.android.p012wm.shell.C1777R;

public final class MobileMappings {

    public static class Config {
        public boolean alwaysShowCdmaRssi = false;
        public boolean alwaysShowDataRatIcon = false;
        public boolean hideLtePlus = false;
        public boolean hspaDataDistinguishable;
        public boolean show4gFor3g = false;
        public boolean show4gForLte = false;
        public boolean showAtLeast3G = false;

        public static Config readConfig(Context context) {
            Config config = new Config();
            Resources resources = context.getResources();
            config.showAtLeast3G = resources.getBoolean(C1777R.bool.config_showMin3G);
            config.alwaysShowCdmaRssi = resources.getBoolean(17891364);
            config.hspaDataDistinguishable = resources.getBoolean(C1777R.bool.config_hspa_data_distinguishable);
            SubscriptionManager.from(context);
            PersistableBundle configForSubId = ((CarrierConfigManager) context.getSystemService("carrier_config")).getConfigForSubId(SubscriptionManager.getDefaultDataSubscriptionId());
            if (configForSubId != null) {
                config.alwaysShowDataRatIcon = configForSubId.getBoolean("always_show_data_rat_icon_bool");
                config.show4gForLte = configForSubId.getBoolean("show_4g_for_lte_data_icon_bool");
                config.show4gFor3g = configForSubId.getBoolean("show_4g_for_3g_data_icon_bool");
                config.hideLtePlus = configForSubId.getBoolean("hide_lte_plus_data_icon_bool");
            }
            return config;
        }
    }

    public static String toDisplayIconKey(int i) {
        if (i == 1) {
            return Integer.toString(13) + "_CA";
        } else if (i == 2) {
            return Integer.toString(13) + "_CA_Plus";
        } else if (i == 3) {
            return Integer.toString(20);
        } else {
            if (i != 5) {
                return "unsupported";
            }
            return Integer.toString(20) + "_Plus";
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x00db  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x00fa  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.HashMap mapIconSets(com.android.settingslib.mobile.MobileMappings.Config r10) {
        /*
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            r1 = 5
            java.lang.String r2 = java.lang.Integer.toString(r1)
            com.android.settingslib.SignalIcon$MobileIconGroup r3 = com.android.settingslib.mobile.TelephonyIcons.THREE_G
            r0.put(r2, r3)
            r2 = 6
            java.lang.String r2 = java.lang.Integer.toString(r2)
            r0.put(r2, r3)
            r2 = 12
            java.lang.String r2 = java.lang.Integer.toString(r2)
            r0.put(r2, r3)
            r2 = 14
            java.lang.String r2 = java.lang.Integer.toString(r2)
            r0.put(r2, r3)
            boolean r2 = r10.show4gFor3g
            r4 = 3
            if (r2 == 0) goto L_0x0038
            java.lang.String r2 = java.lang.Integer.toString(r4)
            com.android.settingslib.SignalIcon$MobileIconGroup r5 = com.android.settingslib.mobile.TelephonyIcons.FOUR_G
            r0.put(r2, r5)
            goto L_0x003f
        L_0x0038:
            java.lang.String r2 = java.lang.Integer.toString(r4)
            r0.put(r2, r3)
        L_0x003f:
            r2 = 17
            java.lang.String r2 = java.lang.Integer.toString(r2)
            r0.put(r2, r3)
            boolean r2 = r10.showAtLeast3G
            r5 = 7
            r6 = 4
            r7 = 0
            r8 = 2
            r9 = 1
            if (r2 != 0) goto L_0x007d
            java.lang.String r2 = java.lang.Integer.toString(r7)
            com.android.settingslib.SignalIcon$MobileIconGroup r7 = com.android.settingslib.mobile.TelephonyIcons.UNKNOWN
            r0.put(r2, r7)
            java.lang.String r2 = java.lang.Integer.toString(r8)
            com.android.settingslib.SignalIcon$MobileIconGroup r7 = com.android.settingslib.mobile.TelephonyIcons.f33E
            r0.put(r2, r7)
            java.lang.String r2 = java.lang.Integer.toString(r9)
            com.android.settingslib.SignalIcon$MobileIconGroup r7 = com.android.settingslib.mobile.TelephonyIcons.f34G
            r0.put(r2, r7)
            java.lang.String r2 = java.lang.Integer.toString(r6)
            com.android.settingslib.SignalIcon$MobileIconGroup r6 = com.android.settingslib.mobile.TelephonyIcons.ONE_X
            r0.put(r2, r6)
            java.lang.String r2 = java.lang.Integer.toString(r5)
            r0.put(r2, r6)
            goto L_0x00a0
        L_0x007d:
            java.lang.String r2 = java.lang.Integer.toString(r7)
            r0.put(r2, r3)
            java.lang.String r2 = java.lang.Integer.toString(r8)
            r0.put(r2, r3)
            java.lang.String r2 = java.lang.Integer.toString(r9)
            r0.put(r2, r3)
            java.lang.String r2 = java.lang.Integer.toString(r6)
            r0.put(r2, r3)
            java.lang.String r2 = java.lang.Integer.toString(r5)
            r0.put(r2, r3)
        L_0x00a0:
            boolean r2 = r10.show4gFor3g
            if (r2 == 0) goto L_0x00a7
            com.android.settingslib.SignalIcon$MobileIconGroup r3 = com.android.settingslib.mobile.TelephonyIcons.FOUR_G
            goto L_0x00b0
        L_0x00a7:
            boolean r2 = r10.hspaDataDistinguishable
            if (r2 == 0) goto L_0x00b0
            com.android.settingslib.SignalIcon$MobileIconGroup r2 = com.android.settingslib.mobile.TelephonyIcons.f35H
            com.android.settingslib.SignalIcon$MobileIconGroup r3 = com.android.settingslib.mobile.TelephonyIcons.H_PLUS
            goto L_0x00b1
        L_0x00b0:
            r2 = r3
        L_0x00b1:
            r5 = 8
            java.lang.String r5 = java.lang.Integer.toString(r5)
            r0.put(r5, r2)
            r5 = 9
            java.lang.String r5 = java.lang.Integer.toString(r5)
            r0.put(r5, r2)
            r5 = 10
            java.lang.String r5 = java.lang.Integer.toString(r5)
            r0.put(r5, r2)
            r2 = 15
            java.lang.String r2 = java.lang.Integer.toString(r2)
            r0.put(r2, r3)
            boolean r2 = r10.show4gForLte
            r3 = 13
            if (r2 == 0) goto L_0x00fa
            java.lang.String r2 = java.lang.Integer.toString(r3)
            com.android.settingslib.SignalIcon$MobileIconGroup r3 = com.android.settingslib.mobile.TelephonyIcons.FOUR_G
            r0.put(r2, r3)
            boolean r10 = r10.hideLtePlus
            if (r10 == 0) goto L_0x00f0
            java.lang.String r10 = toDisplayIconKey(r9)
            r0.put(r10, r3)
            goto L_0x0118
        L_0x00f0:
            java.lang.String r10 = toDisplayIconKey(r9)
            com.android.settingslib.SignalIcon$MobileIconGroup r2 = com.android.settingslib.mobile.TelephonyIcons.FOUR_G_PLUS
            r0.put(r10, r2)
            goto L_0x0118
        L_0x00fa:
            java.lang.String r2 = java.lang.Integer.toString(r3)
            com.android.settingslib.SignalIcon$MobileIconGroup r3 = com.android.settingslib.mobile.TelephonyIcons.LTE
            r0.put(r2, r3)
            boolean r10 = r10.hideLtePlus
            if (r10 == 0) goto L_0x010f
            java.lang.String r10 = toDisplayIconKey(r9)
            r0.put(r10, r3)
            goto L_0x0118
        L_0x010f:
            java.lang.String r10 = toDisplayIconKey(r9)
            com.android.settingslib.SignalIcon$MobileIconGroup r2 = com.android.settingslib.mobile.TelephonyIcons.LTE_PLUS
            r0.put(r10, r2)
        L_0x0118:
            r10 = 18
            java.lang.String r10 = java.lang.Integer.toString(r10)
            com.android.settingslib.SignalIcon$MobileIconGroup r2 = com.android.settingslib.mobile.TelephonyIcons.WFC
            r0.put(r10, r2)
            java.lang.String r10 = toDisplayIconKey(r8)
            com.android.settingslib.SignalIcon$MobileIconGroup r2 = com.android.settingslib.mobile.TelephonyIcons.LTE_CA_5G_E
            r0.put(r10, r2)
            java.lang.String r10 = toDisplayIconKey(r4)
            com.android.settingslib.SignalIcon$MobileIconGroup r2 = com.android.settingslib.mobile.TelephonyIcons.NR_5G
            r0.put(r10, r2)
            java.lang.String r10 = toDisplayIconKey(r1)
            com.android.settingslib.SignalIcon$MobileIconGroup r1 = com.android.settingslib.mobile.TelephonyIcons.NR_5G_PLUS
            r0.put(r10, r1)
            r10 = 20
            java.lang.String r10 = java.lang.Integer.toString(r10)
            r0.put(r10, r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.mobile.MobileMappings.mapIconSets(com.android.settingslib.mobile.MobileMappings$Config):java.util.HashMap");
    }
}
