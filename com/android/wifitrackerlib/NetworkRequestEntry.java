package com.android.wifitrackerlib;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Handler;
import com.android.systemui.p006qs.tiles.dialog.InternetDialogController;
import com.android.wifitrackerlib.StandardWifiEntry;

public class NetworkRequestEntry extends StandardWifiEntry {
    public NetworkRequestEntry(WifiTrackerInjector wifiTrackerInjector, Context context, Handler handler, StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey, WifiManager wifiManager) throws IllegalArgumentException {
        super(wifiTrackerInjector, context, handler, standardWifiEntryKey, wifiManager, false);
    }

    public final boolean canSetAutoJoinEnabled() {
        return false;
    }

    public final boolean canSetMeteredChoice() {
        return false;
    }

    public final void connect(InternetDialogController.WifiEntryConnectCallback wifiEntryConnectCallback) {
    }

    public final int getMeteredChoice() {
        return 0;
    }

    public final WifiConfiguration getWifiConfiguration() {
        return null;
    }

    public final boolean isAutoJoinEnabled() {
        return true;
    }

    public final boolean isMetered() {
        return false;
    }

    public final boolean isSaved() {
        return false;
    }

    public final boolean isSuggestion() {
        return false;
    }
}
