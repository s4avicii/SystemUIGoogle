package com.android.systemui.statusbar.connectivity;

import androidx.leanback.R$layout;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.SignalIcon$IconGroup;

public final class WifiIcons {
    public static final SignalIcon$IconGroup UNMERGED_WIFI;
    public static final int WIFI_LEVEL_COUNT;

    static {
        int[][] iArr = {new int[]{C1777R.C1778drawable.ic_no_internet_wifi_signal_0, C1777R.C1778drawable.ic_no_internet_wifi_signal_1, C1777R.C1778drawable.ic_no_internet_wifi_signal_2, C1777R.C1778drawable.ic_no_internet_wifi_signal_3, C1777R.C1778drawable.ic_no_internet_wifi_signal_4}, new int[]{17302891, 17302892, 17302893, 17302894, 17302895}};
        WIFI_LEVEL_COUNT = iArr[0].length;
        UNMERGED_WIFI = new SignalIcon$IconGroup("Wi-Fi Icons", iArr, iArr, R$layout.WIFI_CONNECTION_STRENGTH, 17302891, 17302891, 17302891, 17302891, C1777R.string.accessibility_no_wifi);
    }
}
