package com.android.wifitrackerlib;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StandardWifiEntry$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ StandardWifiEntry f$0;
    public final /* synthetic */ StringBuilder f$1;
    public final /* synthetic */ long f$2;

    public /* synthetic */ StandardWifiEntry$$ExternalSyntheticLambda1(StandardWifiEntry standardWifiEntry, StringBuilder sb, long j) {
        this.f$0 = standardWifiEntry;
        this.f$1 = sb;
        this.f$2 = j;
    }

    public final void accept(Object obj) {
        String sb;
        StandardWifiEntry standardWifiEntry = this.f$0;
        StringBuilder sb2 = this.f$1;
        long j = this.f$2;
        ScanResult scanResult = (ScanResult) obj;
        Objects.requireNonNull(standardWifiEntry);
        synchronized (standardWifiEntry) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(" \n{");
            sb3.append(scanResult.BSSID);
            WifiInfo wifiInfo = standardWifiEntry.mWifiInfo;
            if (wifiInfo != null && scanResult.BSSID.equals(wifiInfo.getBSSID())) {
                sb3.append("*");
            }
            sb3.append("=");
            sb3.append(scanResult.frequency);
            sb3.append(",");
            sb3.append(scanResult.level);
            sb3.append(",");
            sb3.append(((int) (j - (scanResult.timestamp / 1000))) / 1000);
            sb3.append("s");
            sb3.append("}");
            sb = sb3.toString();
        }
        sb2.append(sb);
    }
}
