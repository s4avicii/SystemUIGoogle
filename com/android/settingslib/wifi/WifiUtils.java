package com.android.settingslib.wifi;

import android.content.Context;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline0;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.drawable.Drawable;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.os.SystemClock;
import android.util.ArraySet;
import com.android.p012wm.shell.C1777R;
import java.util.Iterator;
import java.util.Objects;

public final class WifiUtils {
    public static final int[] NO_INTERNET_WIFI_PIE = {C1777R.C1778drawable.ic_no_internet_wifi_signal_0, C1777R.C1778drawable.ic_no_internet_wifi_signal_1, C1777R.C1778drawable.ic_no_internet_wifi_signal_2, C1777R.C1778drawable.ic_no_internet_wifi_signal_3, C1777R.C1778drawable.ic_no_internet_wifi_signal_4};
    public static final int[] WIFI_PIE = {17302891, 17302892, 17302893, 17302894, 17302895};

    public static class InternetIconInjector {
        public final Context mContext;

        public final Drawable getIcon(boolean z, int i) {
            int i2;
            Context context = this.mContext;
            if (i >= 0) {
                int[] iArr = WifiUtils.WIFI_PIE;
                if (i < 5) {
                    if (z) {
                        i2 = WifiUtils.NO_INTERNET_WIFI_PIE[i];
                    } else {
                        i2 = iArr[i];
                    }
                    return context.getDrawable(i2);
                }
            }
            throw new IllegalArgumentException(VendorAtomValue$$ExternalSyntheticOutline0.m0m("No Wifi icon found for level: ", i));
        }

        public InternetIconInjector(Context context) {
            this.mContext = context;
        }
    }

    public static String getVisibilityStatus(AccessPoint accessPoint) {
        String str;
        StringBuilder sb;
        AccessPoint accessPoint2 = accessPoint;
        Objects.requireNonNull(accessPoint);
        WifiInfo wifiInfo = accessPoint2.mInfo;
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        StringBuilder sb4 = new StringBuilder();
        StringBuilder sb5 = new StringBuilder();
        int i = 0;
        if (!accessPoint.isActive() || wifiInfo == null) {
            str = null;
        } else {
            str = wifiInfo.getBSSID();
            if (str != null) {
                sb2.append(" ");
                sb2.append(str);
            }
            sb2.append(" standard = ");
            sb2.append(wifiInfo.getWifiStandard());
            sb2.append(" rssi=");
            sb2.append(wifiInfo.getRssi());
            sb2.append(" ");
            sb2.append(" score=");
            sb2.append(wifiInfo.getScore());
            if (accessPoint2.mSpeed != 0) {
                sb2.append(" speed=");
                sb2.append(AccessPoint.getSpeedLabel(accessPoint2.mContext, accessPoint2.mSpeed));
            }
            sb2.append(String.format(" tx=%.1f,", new Object[]{Double.valueOf(wifiInfo.getSuccessfulTxPacketsPerSecond())}));
            sb2.append(String.format("%.1f,", new Object[]{Double.valueOf(wifiInfo.getRetriedTxPacketsPerSecond())}));
            sb2.append(String.format("%.1f ", new Object[]{Double.valueOf(wifiInfo.getLostTxPacketsPerSecond())}));
            sb2.append(String.format("rx=%.1f", new Object[]{Double.valueOf(wifiInfo.getSuccessfulRxPacketsPerSecond())}));
        }
        long elapsedRealtime = SystemClock.elapsedRealtime();
        ArraySet arraySet = new ArraySet();
        synchronized (accessPoint2.mLock) {
            arraySet.addAll(accessPoint2.mScanResults);
            arraySet.addAll(accessPoint2.mExtraScanResults);
        }
        Iterator it = arraySet.iterator();
        int i2 = 0;
        int i3 = -127;
        int i4 = -127;
        int i5 = -127;
        int i6 = 0;
        while (true) {
            sb = sb2;
            if (!it.hasNext()) {
                break;
            }
            ScanResult scanResult = (ScanResult) it.next();
            if (scanResult == null) {
                sb2 = sb;
            } else {
                int i7 = scanResult.frequency;
                Iterator it2 = it;
                if (i7 >= 4900 && i7 <= 5900) {
                    i6++;
                    int i8 = scanResult.level;
                    if (i8 > i4) {
                        i4 = i8;
                    }
                    if (i6 <= 4) {
                        sb4.append(verboseScanResultSummary(accessPoint2, scanResult, str, elapsedRealtime));
                    }
                } else if (i7 >= 2400 && i7 <= 2500) {
                    i++;
                    int i9 = scanResult.level;
                    if (i9 > i3) {
                        i3 = i9;
                    }
                    if (i <= 4) {
                        sb3.append(verboseScanResultSummary(accessPoint2, scanResult, str, elapsedRealtime));
                    }
                } else if (i7 >= 58320 && i7 <= 70200) {
                    i2++;
                    int i10 = scanResult.level;
                    if (i10 > i5) {
                        i5 = i10;
                    }
                    if (i2 <= 4) {
                        sb5.append(verboseScanResultSummary(accessPoint2, scanResult, str, elapsedRealtime));
                    }
                }
                sb2 = sb;
                it = it2;
            }
        }
        StringBuilder sb6 = sb;
        sb6.append(" [");
        if (i > 0) {
            sb6.append("(");
            sb6.append(i);
            sb6.append(")");
            if (i > 4) {
                sb6.append("max=");
                sb6.append(i3);
                sb6.append(",");
            }
            sb6.append(sb3.toString());
        }
        sb6.append(";");
        if (i6 > 0) {
            sb6.append("(");
            sb6.append(i6);
            sb6.append(")");
            if (i6 > 4) {
                sb6.append("max=");
                sb6.append(i4);
                sb6.append(",");
            }
            sb6.append(sb4.toString());
        }
        sb6.append(";");
        if (i2 > 0) {
            sb6.append("(");
            sb6.append(i2);
            sb6.append(")");
            if (i2 > 4) {
                sb6.append("max=");
                sb6.append(i5);
                sb6.append(",");
            }
            sb6.append(sb5.toString());
        }
        sb6.append("]");
        return sb6.toString();
    }

    public static String verboseScanResultSummary(AccessPoint accessPoint, ScanResult scanResult, String str, long j) {
        int i;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m(" \n{");
        m.append(scanResult.BSSID);
        if (scanResult.BSSID.equals(str)) {
            m.append("*");
        }
        m.append("=");
        m.append(scanResult.frequency);
        m.append(",");
        m.append(scanResult.level);
        Objects.requireNonNull(accessPoint);
        TimestampedScoredNetwork timestampedScoredNetwork = (TimestampedScoredNetwork) accessPoint.mScoredNetworkCache.get(scanResult.BSSID);
        if (timestampedScoredNetwork == null) {
            i = 0;
        } else {
            i = timestampedScoredNetwork.mScore.calculateBadge(scanResult.level);
        }
        if (i != 0) {
            m.append(",");
            m.append(AccessPoint.getSpeedLabel(accessPoint.mContext, i));
        }
        m.append(",");
        m.append(((int) (j - (scanResult.timestamp / 1000))) / 1000);
        m.append("s");
        m.append("}");
        return m.toString();
    }
}
