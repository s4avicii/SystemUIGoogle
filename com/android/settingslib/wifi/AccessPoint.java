package com.android.settingslib.wifi;

import android.content.Context;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.net.NetworkInfo;
import android.net.NetworkKey;
import android.net.ScoredNetwork;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkScoreCache;
import android.net.wifi.hotspot2.OsuProvider;
import android.net.wifi.hotspot2.ProvisioningCallback;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import androidx.appcompat.view.SupportMenuInflater$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.recyclerview.R$dimen;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.CollectionUtils;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.FalsingManager;
import com.android.wifitrackerlib.WifiEntry$$ExternalSyntheticLambda2;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Deprecated
public final class AccessPoint implements Comparable<AccessPoint> {
    public static final /* synthetic */ int $r8$clinit = 0;
    public String bssid;
    public WifiConfiguration mConfig;
    public final Context mContext;
    public final ArraySet<ScanResult> mExtraScanResults;
    public WifiInfo mInfo;
    public boolean mIsOweTransitionMode;
    public boolean mIsPskSaeTransitionMode;
    public boolean mIsScoredNetworkMetered;
    public String mKey;
    public final Object mLock;
    public NetworkInfo mNetworkInfo;
    public OsuProvider mOsuProvider;
    public String mOsuStatus;
    public String mPasspointUniqueId;
    public String mProviderFriendlyName;
    public int mRssi;
    public final ArraySet<ScanResult> mScanResults;
    public final HashMap mScoredNetworkCache;
    public int mSpeed;
    public WifiManager mWifiManager;
    public int networkId;
    public int pskType;
    public int security;
    public String ssid;

    @VisibleForTesting
    public class AccessPointProvisioningCallback extends ProvisioningCallback {
        public final void onProvisioningStatus(int i) {
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case FalsingManager.VERSION /*6*/:
                case 7:
                    throw null;
                case 8:
                case 9:
                case 10:
                case QSTileImpl.C1034H.STALE /*11*/:
                    throw null;
                default:
                    throw null;
            }
        }

        public final void onProvisioningComplete() {
            int i = AccessPoint.$r8$clinit;
            throw null;
        }

        public final void onProvisioningFailure(int i) {
            throw null;
        }
    }

    public AccessPoint(Context context, Bundle bundle) {
        this.mLock = new Object();
        ArraySet<ScanResult> arraySet = new ArraySet<>();
        this.mScanResults = arraySet;
        this.mExtraScanResults = new ArraySet<>();
        this.mScoredNetworkCache = new HashMap();
        this.networkId = -1;
        this.pskType = 0;
        this.mRssi = Integer.MIN_VALUE;
        this.mSpeed = 0;
        this.mIsScoredNetworkMetered = false;
        this.mIsPskSaeTransitionMode = false;
        this.mIsOweTransitionMode = false;
        this.mContext = context;
        if (bundle.containsKey("key_config")) {
            this.mConfig = (WifiConfiguration) bundle.getParcelable("key_config");
        }
        WifiConfiguration wifiConfiguration = this.mConfig;
        if (wifiConfiguration != null) {
            loadConfig(wifiConfiguration);
        }
        if (bundle.containsKey("key_ssid")) {
            this.ssid = bundle.getString("key_ssid");
        }
        if (bundle.containsKey("key_security")) {
            this.security = bundle.getInt("key_security");
        }
        if (bundle.containsKey("key_speed")) {
            this.mSpeed = bundle.getInt("key_speed");
        }
        if (bundle.containsKey("key_psktype")) {
            this.pskType = bundle.getInt("key_psktype");
        }
        if (bundle.containsKey("eap_psktype")) {
            bundle.getInt("eap_psktype");
        }
        this.mInfo = (WifiInfo) bundle.getParcelable("key_wifiinfo");
        if (bundle.containsKey("key_networkinfo")) {
            this.mNetworkInfo = (NetworkInfo) bundle.getParcelable("key_networkinfo");
        }
        if (bundle.containsKey("key_scanresults")) {
            Parcelable[] parcelableArray = bundle.getParcelableArray("key_scanresults");
            arraySet.clear();
            for (Parcelable parcelable : parcelableArray) {
                this.mScanResults.add((ScanResult) parcelable);
            }
        }
        if (bundle.containsKey("key_scorednetworkcache")) {
            Iterator it = bundle.getParcelableArrayList("key_scorednetworkcache").iterator();
            while (it.hasNext()) {
                TimestampedScoredNetwork timestampedScoredNetwork = (TimestampedScoredNetwork) it.next();
                HashMap hashMap = this.mScoredNetworkCache;
                Objects.requireNonNull(timestampedScoredNetwork);
                hashMap.put(timestampedScoredNetwork.mScore.networkKey.wifiKey.bssid, timestampedScoredNetwork);
            }
        }
        if (bundle.containsKey("key_passpoint_unique_id")) {
            this.mPasspointUniqueId = bundle.getString("key_passpoint_unique_id");
        }
        if (bundle.containsKey("key_fqdn")) {
            bundle.getString("key_fqdn");
        }
        if (bundle.containsKey("key_provider_friendly_name")) {
            this.mProviderFriendlyName = bundle.getString("key_provider_friendly_name");
        }
        if (bundle.containsKey("key_subscription_expiration_time_in_millis")) {
            bundle.getLong("key_subscription_expiration_time_in_millis");
        }
        if (bundle.containsKey("key_passpoint_configuration_version")) {
            bundle.getInt("key_passpoint_configuration_version");
        }
        if (bundle.containsKey("key_is_psk_sae_transition_mode")) {
            this.mIsPskSaeTransitionMode = bundle.getBoolean("key_is_psk_sae_transition_mode");
        }
        if (bundle.containsKey("key_is_owe_transition_mode")) {
            this.mIsOweTransitionMode = bundle.getBoolean("key_is_owe_transition_mode");
        }
        update(this.mConfig, this.mInfo, this.mNetworkInfo);
        updateKey();
        updateBestRssiInfo();
    }

    public static String getKey(OsuProvider osuProvider) {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("OSU:");
        m.append(osuProvider.getFriendlyName());
        m.append(',');
        m.append(osuProvider.getServerUri());
        return m.toString();
    }

    public static int getSecurity(Context context, ScanResult scanResult) {
        boolean contains = scanResult.capabilities.contains("WEP");
        boolean contains2 = scanResult.capabilities.contains("SAE");
        boolean contains3 = scanResult.capabilities.contains("PSK");
        boolean contains4 = scanResult.capabilities.contains("EAP_SUITE_B_192");
        boolean contains5 = scanResult.capabilities.contains("EAP");
        boolean contains6 = scanResult.capabilities.contains("OWE");
        boolean contains7 = scanResult.capabilities.contains("OWE_TRANSITION");
        if (!contains2 || !contains3) {
            if (contains7) {
                if (((WifiManager) context.getSystemService("wifi")).isEnhancedOpenSupported()) {
                    return 4;
                }
                return 0;
            } else if (contains) {
                return 1;
            } else {
                if (contains2) {
                    return 5;
                }
                if (contains3) {
                    return 2;
                }
                if (contains4) {
                    return 6;
                }
                if (contains5) {
                    return 3;
                }
                return contains6 ? 4 : 0;
            }
        } else if (((WifiManager) context.getSystemService("wifi")).isWpa3SaeSupported()) {
            return 5;
        } else {
            return 2;
        }
    }

    public static String getSpeedLabel(Context context, int i) {
        if (i == 5) {
            return context.getString(C1777R.string.speed_label_slow);
        }
        if (i == 10) {
            return context.getString(C1777R.string.speed_label_okay);
        }
        if (i == 20) {
            return context.getString(C1777R.string.speed_label_fast);
        }
        if (i != 30) {
            return null;
        }
        return context.getString(C1777R.string.speed_label_very_fast);
    }

    public final boolean matches(WifiConfiguration wifiConfiguration) {
        if (wifiConfiguration.isPasspoint()) {
            if (!isPasspoint() || !wifiConfiguration.getKey().equals(this.mConfig.getKey())) {
                return false;
            }
            return true;
        } else if (!this.ssid.equals(removeDoubleQuotes(wifiConfiguration.SSID))) {
            return false;
        } else {
            WifiConfiguration wifiConfiguration2 = this.mConfig;
            if (wifiConfiguration2 != null && wifiConfiguration2.shared != wifiConfiguration.shared) {
                return false;
            }
            int security2 = getSecurity(wifiConfiguration);
            if (this.mIsPskSaeTransitionMode && ((security2 == 5 && getWifiManager().isWpa3SaeSupported()) || security2 == 2)) {
                return true;
            }
            if (this.mIsOweTransitionMode && ((security2 == 4 && getWifiManager().isEnhancedOpenSupported()) || security2 == 0)) {
                return true;
            }
            if (this.security == getSecurity(wifiConfiguration)) {
                return true;
            }
            return false;
        }
    }

    public final boolean update(WifiNetworkScoreCache wifiNetworkScoreCache, boolean z, long j) {
        boolean z2;
        WifiInfo wifiInfo;
        if (z) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            synchronized (this.mLock) {
                Iterator<ScanResult> it = this.mScanResults.iterator();
                while (it.hasNext()) {
                    ScanResult next = it.next();
                    ScoredNetwork scoredNetwork = wifiNetworkScoreCache.getScoredNetwork(next);
                    if (scoredNetwork != null) {
                        TimestampedScoredNetwork timestampedScoredNetwork = (TimestampedScoredNetwork) this.mScoredNetworkCache.get(next.BSSID);
                        if (timestampedScoredNetwork == null) {
                            this.mScoredNetworkCache.put(next.BSSID, new TimestampedScoredNetwork(scoredNetwork, elapsedRealtime));
                        } else {
                            timestampedScoredNetwork.mScore = scoredNetwork;
                            timestampedScoredNetwork.mUpdatedTimestampMillis = elapsedRealtime;
                        }
                    }
                }
            }
            Iterator it2 = this.mScoredNetworkCache.values().iterator();
            it2.forEachRemaining(new AccessPoint$$ExternalSyntheticLambda2(elapsedRealtime - j, it2));
            z2 = updateSpeed();
        } else {
            z2 = false;
        }
        boolean z3 = this.mIsScoredNetworkMetered;
        this.mIsScoredNetworkMetered = false;
        if (!isActive() || (wifiInfo = this.mInfo) == null) {
            synchronized (this.mLock) {
                Iterator<ScanResult> it3 = this.mScanResults.iterator();
                while (it3.hasNext()) {
                    ScoredNetwork scoredNetwork2 = wifiNetworkScoreCache.getScoredNetwork(it3.next());
                    if (scoredNetwork2 != null) {
                        this.mIsScoredNetworkMetered = scoredNetwork2.meteredHint | this.mIsScoredNetworkMetered;
                    }
                }
            }
        } else {
            ScoredNetwork scoredNetwork3 = wifiNetworkScoreCache.getScoredNetwork(NetworkKey.createFromWifiInfo(wifiInfo));
            if (scoredNetwork3 != null) {
                this.mIsScoredNetworkMetered = scoredNetwork3.meteredHint | this.mIsScoredNetworkMetered;
            }
        }
        if ((z3 != this.mIsScoredNetworkMetered) || z2) {
            return true;
        }
        return false;
    }

    static {
        new AtomicInteger(0);
    }

    public static boolean isVerboseLoggingEnabled() {
        if (WifiTracker.sVerboseLogging || Log.isLoggable("SettingsLib.AccessPoint", 2)) {
            return true;
        }
        return false;
    }

    public final int compareTo(AccessPoint accessPoint) {
        if (isActive() && !accessPoint.isActive()) {
            return -1;
        }
        if (!isActive() && accessPoint.isActive()) {
            return 1;
        }
        boolean z = false;
        if (this.mRssi != Integer.MIN_VALUE) {
            Objects.requireNonNull(accessPoint);
            if (!(accessPoint.mRssi != Integer.MIN_VALUE)) {
                return -1;
            }
        }
        if (!(this.mRssi != Integer.MIN_VALUE)) {
            Objects.requireNonNull(accessPoint);
            if (accessPoint.mRssi != Integer.MIN_VALUE) {
                return 1;
            }
        }
        if (this.mConfig != null) {
            Objects.requireNonNull(accessPoint);
            if (!(accessPoint.mConfig != null)) {
                return -1;
            }
        }
        if (!(this.mConfig != null)) {
            Objects.requireNonNull(accessPoint);
            if (accessPoint.mConfig != null) {
                z = true;
            }
            if (z) {
                return 1;
            }
        }
        int i = this.mSpeed;
        Objects.requireNonNull(accessPoint);
        int i2 = accessPoint.mSpeed;
        if (i != i2) {
            return i2 - this.mSpeed;
        }
        WifiManager wifiManager = getWifiManager();
        int calculateSignalLevel = wifiManager.calculateSignalLevel(accessPoint.mRssi) - wifiManager.calculateSignalLevel(this.mRssi);
        if (calculateSignalLevel != 0) {
            return calculateSignalLevel;
        }
        int compareToIgnoreCase = getTitle().compareToIgnoreCase(accessPoint.getTitle());
        if (compareToIgnoreCase != 0) {
            return compareToIgnoreCase;
        }
        return this.ssid.compareTo(accessPoint.ssid);
    }

    public final boolean equals(Object obj) {
        if ((obj instanceof AccessPoint) && compareTo((AccessPoint) obj) == 0) {
            return true;
        }
        return false;
    }

    public final WifiManager getWifiManager() {
        if (this.mWifiManager == null) {
            this.mWifiManager = (WifiManager) this.mContext.getSystemService("wifi");
        }
        return this.mWifiManager;
    }

    public final int hashCode() {
        WifiInfo wifiInfo = this.mInfo;
        int i = 0;
        if (wifiInfo != null) {
            i = 0 + (wifiInfo.hashCode() * 13);
        }
        int i2 = (this.mRssi * 19) + i;
        return (this.ssid.hashCode() * 29) + (this.networkId * 23) + i2;
    }

    public final boolean isActive() {
        NetworkInfo networkInfo = this.mNetworkInfo;
        if (networkInfo == null || (this.networkId == -1 && networkInfo.getState() == NetworkInfo.State.DISCONNECTED)) {
            return false;
        }
        return true;
    }

    public final boolean isPasspoint() {
        WifiConfiguration wifiConfiguration = this.mConfig;
        if (wifiConfiguration == null || !wifiConfiguration.isPasspoint()) {
            return false;
        }
        return true;
    }

    @VisibleForTesting
    public void loadConfig(WifiConfiguration wifiConfiguration) {
        String str;
        String str2 = wifiConfiguration.SSID;
        if (str2 == null) {
            str = "";
        } else {
            str = removeDoubleQuotes(str2);
        }
        this.ssid = str;
        this.bssid = wifiConfiguration.BSSID;
        this.security = getSecurity(wifiConfiguration);
        this.networkId = wifiConfiguration.networkId;
        this.mConfig = wifiConfiguration;
    }

    public final void setScanResultsPasspoint(List list, List list2) {
        synchronized (this.mLock) {
            this.mExtraScanResults.clear();
            if (!CollectionUtils.isEmpty(list)) {
                if (!CollectionUtils.isEmpty(list2)) {
                    this.mExtraScanResults.addAll(list2);
                }
                setScanResults(list);
            } else if (!CollectionUtils.isEmpty(list2)) {
                setScanResults(list2);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x007f  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0097  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x009a  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x00db  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0103  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String toString() {
        /*
            r8 = this;
            java.lang.String r0 = "AccessPoint("
            java.lang.StringBuilder r0 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r0)
            java.lang.String r1 = r8.ssid
            r0.append(r1)
            java.lang.String r1 = r8.bssid
            if (r1 == 0) goto L_0x0019
            java.lang.String r1 = ":"
            r0.append(r1)
            java.lang.String r1 = r8.bssid
            r0.append(r1)
        L_0x0019:
            android.net.wifi.WifiConfiguration r1 = r8.mConfig
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L_0x0021
            r1 = r2
            goto L_0x0022
        L_0x0021:
            r1 = r3
        L_0x0022:
            r4 = 44
            if (r1 == 0) goto L_0x002f
            r0.append(r4)
            java.lang.String r1 = "saved"
            r0.append(r1)
        L_0x002f:
            boolean r1 = r8.isActive()
            if (r1 == 0) goto L_0x003d
            r0.append(r4)
            java.lang.String r1 = "active"
            r0.append(r1)
        L_0x003d:
            android.net.wifi.WifiInfo r1 = r8.mInfo
            if (r1 == 0) goto L_0x0055
            boolean r1 = r1.isEphemeral()
            if (r1 == 0) goto L_0x0055
            android.net.NetworkInfo r1 = r8.mNetworkInfo
            if (r1 == 0) goto L_0x0055
            android.net.NetworkInfo$State r1 = r1.getState()
            android.net.NetworkInfo$State r5 = android.net.NetworkInfo.State.DISCONNECTED
            if (r1 == r5) goto L_0x0055
            r1 = r2
            goto L_0x0056
        L_0x0055:
            r1 = r3
        L_0x0056:
            if (r1 == 0) goto L_0x0060
            r0.append(r4)
            java.lang.String r1 = "ephemeral"
            r0.append(r1)
        L_0x0060:
            int r1 = r8.getLevel()
            r5 = -1
            if (r1 == r5) goto L_0x007c
            android.net.NetworkInfo r1 = r8.mNetworkInfo
            if (r1 == 0) goto L_0x0070
            android.net.NetworkInfo$DetailedState r1 = r1.getDetailedState()
            goto L_0x0078
        L_0x0070:
            java.lang.String r1 = "SettingsLib.AccessPoint"
            java.lang.String r5 = "NetworkInfo is null, cannot return detailed state"
            android.util.Log.w(r1, r5)
            r1 = 0
        L_0x0078:
            if (r1 != 0) goto L_0x007c
            r1 = r2
            goto L_0x007d
        L_0x007c:
            r1 = r3
        L_0x007d:
            if (r1 == 0) goto L_0x0087
            r0.append(r4)
            java.lang.String r1 = "connectable"
            r0.append(r1)
        L_0x0087:
            int r1 = r8.security
            if (r1 == 0) goto L_0x00cb
            r5 = 4
            if (r1 == r5) goto L_0x00cb
            r0.append(r4)
            int r1 = r8.security
            int r4 = r8.pskType
            if (r1 != r2) goto L_0x009a
            java.lang.String r1 = "WEP"
            goto L_0x00c8
        L_0x009a:
            r6 = 3
            r7 = 2
            if (r1 != r7) goto L_0x00b0
            if (r4 != r2) goto L_0x00a3
            java.lang.String r1 = "WPA"
            goto L_0x00c8
        L_0x00a3:
            if (r4 != r7) goto L_0x00a8
            java.lang.String r1 = "WPA2"
            goto L_0x00c8
        L_0x00a8:
            if (r4 != r6) goto L_0x00ad
            java.lang.String r1 = "WPA_WPA2"
            goto L_0x00c8
        L_0x00ad:
            java.lang.String r1 = "PSK"
            goto L_0x00c8
        L_0x00b0:
            if (r1 != r6) goto L_0x00b5
            java.lang.String r1 = "EAP"
            goto L_0x00c8
        L_0x00b5:
            r4 = 5
            if (r1 != r4) goto L_0x00bb
            java.lang.String r1 = "SAE"
            goto L_0x00c8
        L_0x00bb:
            r4 = 6
            if (r1 != r4) goto L_0x00c1
            java.lang.String r1 = "SUITE_B"
            goto L_0x00c8
        L_0x00c1:
            if (r1 != r5) goto L_0x00c6
            java.lang.String r1 = "OWE"
            goto L_0x00c8
        L_0x00c6:
            java.lang.String r1 = "NONE"
        L_0x00c8:
            r0.append(r1)
        L_0x00cb:
            java.lang.String r1 = ",level="
            r0.append(r1)
            int r1 = r8.getLevel()
            r0.append(r1)
            int r1 = r8.mSpeed
            if (r1 == 0) goto L_0x00e5
            java.lang.String r1 = ",speed="
            r0.append(r1)
            int r1 = r8.mSpeed
            r0.append(r1)
        L_0x00e5:
            java.lang.String r1 = ",metered="
            r0.append(r1)
            boolean r1 = r8.mIsScoredNetworkMetered
            if (r1 != 0) goto L_0x00fa
            android.net.wifi.WifiConfiguration r1 = r8.mConfig
            android.net.wifi.WifiInfo r4 = r8.mInfo
            boolean r1 = android.net.wifi.WifiConfiguration.isMetered(r1, r4)
            if (r1 == 0) goto L_0x00f9
            goto L_0x00fa
        L_0x00f9:
            r2 = r3
        L_0x00fa:
            r0.append(r2)
            boolean r1 = isVerboseLoggingEnabled()
            if (r1 == 0) goto L_0x012a
            java.lang.String r1 = ",rssi="
            r0.append(r1)
            int r1 = r8.mRssi
            r0.append(r1)
            java.lang.Object r1 = r8.mLock
            monitor-enter(r1)
            java.lang.String r2 = ",scan cache size="
            r0.append(r2)     // Catch:{ all -> 0x0127 }
            android.util.ArraySet<android.net.wifi.ScanResult> r2 = r8.mScanResults     // Catch:{ all -> 0x0127 }
            int r2 = r2.size()     // Catch:{ all -> 0x0127 }
            android.util.ArraySet<android.net.wifi.ScanResult> r8 = r8.mExtraScanResults     // Catch:{ all -> 0x0127 }
            int r8 = r8.size()     // Catch:{ all -> 0x0127 }
            int r2 = r2 + r8
            r0.append(r2)     // Catch:{ all -> 0x0127 }
            monitor-exit(r1)     // Catch:{ all -> 0x0127 }
            goto L_0x012a
        L_0x0127:
            r8 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0127 }
            throw r8
        L_0x012a:
            r8 = 41
            r0.append(r8)
            java.lang.String r8 = r0.toString()
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.wifi.AccessPoint.toString():java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x0091  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0093  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean updateSpeed() {
        /*
            r10 = this;
            int r0 = r10.mSpeed
            java.util.HashMap r1 = r10.mScoredNetworkCache
            boolean r1 = r1.isEmpty()
            r2 = 1
            r3 = 0
            r4 = 2
            java.lang.String r5 = "SettingsLib.AccessPoint"
            if (r1 == 0) goto L_0x0012
        L_0x000f:
            r1 = r3
            goto L_0x008d
        L_0x0012:
            r1 = 3
            boolean r1 = android.util.Log.isLoggable(r5, r1)
            if (r1 == 0) goto L_0x002c
            java.lang.Object[] r1 = new java.lang.Object[r4]
            java.lang.String r6 = r10.ssid
            r1[r3] = r6
            java.util.HashMap r6 = r10.mScoredNetworkCache
            r1[r2] = r6
            java.lang.String r6 = "Generating fallbackspeed for %s using cache: %s"
            java.lang.String r1 = java.lang.String.format(r6, r1)
            android.util.Log.d(r5, r1)
        L_0x002c:
            java.util.HashMap r1 = r10.mScoredNetworkCache
            java.util.Collection r1 = r1.values()
            java.util.Iterator r1 = r1.iterator()
            r6 = r3
            r7 = r6
        L_0x0038:
            boolean r8 = r1.hasNext()
            if (r8 == 0) goto L_0x0055
            java.lang.Object r8 = r1.next()
            com.android.settingslib.wifi.TimestampedScoredNetwork r8 = (com.android.settingslib.wifi.TimestampedScoredNetwork) r8
            java.util.Objects.requireNonNull(r8)
            android.net.ScoredNetwork r8 = r8.mScore
            int r9 = r10.mRssi
            int r8 = r8.calculateBadge(r9)
            if (r8 == 0) goto L_0x0038
            int r6 = r6 + 1
            int r7 = r7 + r8
            goto L_0x0038
        L_0x0055:
            if (r6 != 0) goto L_0x0059
            r7 = r3
            goto L_0x005a
        L_0x0059:
            int r7 = r7 / r6
        L_0x005a:
            boolean r1 = isVerboseLoggingEnabled()
            if (r1 == 0) goto L_0x0075
            java.lang.Object[] r1 = new java.lang.Object[r4]
            java.lang.String r6 = r10.ssid
            r1[r3] = r6
            java.lang.Integer r6 = java.lang.Integer.valueOf(r7)
            r1[r2] = r6
            java.lang.String r6 = "%s generated fallback speed is: %d"
            java.lang.String r1 = java.lang.String.format(r6, r1)
            android.util.Log.i(r5, r1)
        L_0x0075:
            r1 = 5
            if (r7 >= r1) goto L_0x0079
            goto L_0x000f
        L_0x0079:
            r6 = 7
            if (r7 >= r6) goto L_0x007d
            goto L_0x008d
        L_0x007d:
            r1 = 15
            if (r7 >= r1) goto L_0x0084
            r1 = 10
            goto L_0x008d
        L_0x0084:
            r1 = 25
            if (r7 >= r1) goto L_0x008b
            r1 = 20
            goto L_0x008d
        L_0x008b:
            r1 = 30
        L_0x008d:
            r10.mSpeed = r1
            if (r0 == r1) goto L_0x0093
            r0 = r2
            goto L_0x0094
        L_0x0093:
            r0 = r3
        L_0x0094:
            boolean r1 = isVerboseLoggingEnabled()
            if (r1 == 0) goto L_0x00b3
            if (r0 == 0) goto L_0x00b3
            java.lang.Object[] r1 = new java.lang.Object[r4]
            java.lang.String r4 = r10.ssid
            r1[r3] = r4
            int r10 = r10.mSpeed
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
            r1[r2] = r10
            java.lang.String r10 = "%s: Set speed to %d"
            java.lang.String r10 = java.lang.String.format(r10, r1)
            android.util.Log.i(r5, r10)
        L_0x00b3:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.wifi.AccessPoint.updateSpeed():boolean");
    }

    public static String removeDoubleQuotes(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        int length = str.length();
        if (length <= 1 || str.charAt(0) != '\"') {
            return str;
        }
        int i = length - 1;
        if (str.charAt(i) == '\"') {
            return str.substring(1, i);
        }
        return str;
    }

    public final int getLevel() {
        return getWifiManager().calculateSignalLevel(this.mRssi);
    }

    public final String getTitle() {
        boolean z;
        if (isPasspoint() && !TextUtils.isEmpty(this.mConfig.providerFriendlyName)) {
            return this.mConfig.providerFriendlyName;
        }
        boolean z2 = true;
        if (this.mPasspointUniqueId == null || this.mConfig != null) {
            z = false;
        } else {
            z = true;
        }
        if (z && !TextUtils.isEmpty(this.mProviderFriendlyName)) {
            return this.mProviderFriendlyName;
        }
        OsuProvider osuProvider = this.mOsuProvider;
        if (osuProvider == null) {
            z2 = false;
        }
        if (z2 && !TextUtils.isEmpty(osuProvider.getFriendlyName())) {
            return this.mOsuProvider.getFriendlyName();
        }
        if (!TextUtils.isEmpty(this.ssid)) {
            return this.ssid;
        }
        return "";
    }

    public final void setScanResults(List list) {
        boolean z;
        if (CollectionUtils.isEmpty(list)) {
            Log.d("SettingsLib.AccessPoint", "Cannot set scan results to empty list");
            return;
        }
        if (this.mKey != null && !isPasspoint()) {
            if (this.mOsuProvider != null) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    ScanResult scanResult = (ScanResult) it.next();
                    if (!matches(scanResult)) {
                        Log.d("SettingsLib.AccessPoint", String.format("ScanResult %s\nkey of %s did not match current AP key %s", new Object[]{scanResult, getKey(scanResult.SSID, scanResult.BSSID, getSecurity(this.mContext, scanResult)), this.mKey}));
                        return;
                    }
                }
            }
        }
        int level = getLevel();
        synchronized (this.mLock) {
            this.mScanResults.clear();
            this.mScanResults.addAll(list);
        }
        updateBestRssiInfo();
        int level2 = getLevel();
        if (level2 > 0 && level2 != level) {
            updateSpeed();
            R$dimen.postOnMainThread(new AccessPoint$$ExternalSyntheticLambda1(this, 0));
        }
        R$dimen.postOnMainThread(new AccessPoint$$ExternalSyntheticLambda0(this, 0));
    }

    public final void updateBestRssiInfo() {
        int i;
        int i2;
        if (!isActive()) {
            ScanResult scanResult = null;
            synchronized (this.mLock) {
                Iterator<ScanResult> it = this.mScanResults.iterator();
                i = Integer.MIN_VALUE;
                while (it.hasNext()) {
                    ScanResult next = it.next();
                    int i3 = next.level;
                    if (i3 > i) {
                        scanResult = next;
                        i = i3;
                    }
                }
            }
            int i4 = 2;
            if (i == Integer.MIN_VALUE || (i2 = this.mRssi) == Integer.MIN_VALUE) {
                this.mRssi = i;
            } else {
                this.mRssi = (i2 + i) / 2;
            }
            if (scanResult != null) {
                this.ssid = scanResult.SSID;
                this.bssid = scanResult.BSSID;
                int security2 = getSecurity(this.mContext, scanResult);
                this.security = security2;
                boolean z = true;
                if (security2 == 2 || security2 == 5) {
                    boolean contains = scanResult.capabilities.contains("WPA-PSK");
                    boolean contains2 = scanResult.capabilities.contains("RSN-PSK");
                    boolean contains3 = scanResult.capabilities.contains("RSN-SAE");
                    if (contains2 && contains) {
                        i4 = 3;
                    } else if (!contains2) {
                        if (contains) {
                            i4 = 1;
                        } else {
                            if (!contains3) {
                                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Received abnormal flag string: ");
                                m.append(scanResult.capabilities);
                                Log.w("SettingsLib.AccessPoint", m.toString());
                            }
                            i4 = 0;
                        }
                    }
                    this.pskType = i4;
                }
                if (this.security == 3 && !scanResult.capabilities.contains("RSN-EAP")) {
                    scanResult.capabilities.contains("WPA-EAP");
                }
                if (!scanResult.capabilities.contains("PSK") || !scanResult.capabilities.contains("SAE")) {
                    z = false;
                }
                this.mIsPskSaeTransitionMode = z;
                this.mIsOweTransitionMode = scanResult.capabilities.contains("OWE_TRANSITION");
            }
            if (isPasspoint()) {
                this.mConfig.SSID = AbstractResolvableFuture$$ExternalSyntheticOutline0.m6m("\"", this.ssid, "\"");
            }
        }
    }

    public final void updateKey() {
        boolean z;
        if (isPasspoint()) {
            this.mKey = getKey(this.mConfig);
            return;
        }
        String str = this.mPasspointUniqueId;
        boolean z2 = true;
        if (str == null || this.mConfig != null) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            this.mKey = SupportMenuInflater$$ExternalSyntheticOutline0.m4m("PASSPOINT:", str);
            return;
        }
        OsuProvider osuProvider = this.mOsuProvider;
        if (osuProvider == null) {
            z2 = false;
        }
        if (z2) {
            this.mKey = getKey(osuProvider);
        } else {
            this.mKey = getKey(this.ssid, this.bssid, this.security);
        }
    }

    public static String getKey(String str, String str2, int i) {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("AP:");
        if (TextUtils.isEmpty(str)) {
            m.append(str2);
        } else {
            m.append(str);
        }
        m.append(',');
        m.append(i);
        return m.toString();
    }

    @VisibleForTesting
    public boolean matches(ScanResult scanResult) {
        String str;
        if (scanResult == null) {
            return false;
        }
        if (!isPasspoint()) {
            if (!(this.mOsuProvider != null)) {
                if (!(TextUtils.equals(this.ssid, scanResult.SSID) || ((str = scanResult.BSSID) != null && TextUtils.equals(this.bssid, str)))) {
                    return false;
                }
                if (!this.mIsPskSaeTransitionMode) {
                    int i = this.security;
                    if (i == 5 || i == 2) {
                        if (scanResult.capabilities.contains("PSK") && scanResult.capabilities.contains("SAE")) {
                            return true;
                        }
                    }
                } else if ((scanResult.capabilities.contains("SAE") && getWifiManager().isWpa3SaeSupported()) || scanResult.capabilities.contains("PSK")) {
                    return true;
                }
                if (this.mIsOweTransitionMode) {
                    int security2 = getSecurity(this.mContext, scanResult);
                    if ((security2 == 4 && getWifiManager().isEnhancedOpenSupported()) || security2 == 0) {
                        return true;
                    }
                } else {
                    int i2 = this.security;
                    if ((i2 == 4 || i2 == 0) && scanResult.capabilities.contains("OWE_TRANSITION")) {
                        return true;
                    }
                }
                if (this.security == getSecurity(this.mContext, scanResult)) {
                    return true;
                }
                return false;
            }
        }
        throw new IllegalStateException("Should not matches a Passpoint by ScanResult");
    }

    public static int getSecurity(WifiConfiguration wifiConfiguration) {
        if (wifiConfiguration.allowedKeyManagement.get(8)) {
            return 5;
        }
        if (wifiConfiguration.allowedKeyManagement.get(1)) {
            return 2;
        }
        if (wifiConfiguration.allowedKeyManagement.get(10)) {
            return 6;
        }
        if (wifiConfiguration.allowedKeyManagement.get(2) || wifiConfiguration.allowedKeyManagement.get(3)) {
            return 3;
        }
        if (wifiConfiguration.allowedKeyManagement.get(9)) {
            return 4;
        }
        int i = wifiConfiguration.wepTxKeyIndex;
        if (i >= 0) {
            String[] strArr = wifiConfiguration.wepKeys;
            if (i >= strArr.length || strArr[i] == null) {
                return 0;
            }
            return 1;
        }
        return 0;
    }

    public static String getKey(WifiConfiguration wifiConfiguration) {
        if (wifiConfiguration.isPasspoint()) {
            return SupportMenuInflater$$ExternalSyntheticOutline0.m4m("PASSPOINT:", wifiConfiguration.getKey());
        }
        return getKey(removeDoubleQuotes(wifiConfiguration.SSID), wifiConfiguration.BSSID, getSecurity(wifiConfiguration));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0029, code lost:
        if (r2 == r6.getNetworkId()) goto L_0x002b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x005f, code lost:
        if ((android.text.TextUtils.equals(r4.ssid, removeDoubleQuotes(r6.getSSID())) || (r6.getBSSID() != null && android.text.TextUtils.equals(r4.bssid, r6.getBSSID()))) == false) goto L_0x002e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x009c, code lost:
        if (android.text.TextUtils.equals(r6.getPasspointProviderFriendlyName(), r4.mConfig.providerFriendlyName) != false) goto L_0x002b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00a7, code lost:
        if (r4.mOsuStatus != null) goto L_0x002b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00e4, code lost:
        if (r5.getDetailedState() != r7.getDetailedState()) goto L_0x00d4;
     */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00ac  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean update(android.net.wifi.WifiConfiguration r5, android.net.wifi.WifiInfo r6, android.net.NetworkInfo r7) {
        /*
            r4 = this;
            r4.getLevel()
            r0 = 0
            r1 = 1
            if (r6 == 0) goto L_0x00ec
            boolean r2 = r6.isOsuAp()
            if (r2 != 0) goto L_0x009f
            java.lang.String r2 = r4.mOsuStatus
            if (r2 == 0) goto L_0x0013
            goto L_0x009f
        L_0x0013:
            boolean r2 = r6.isPasspointAp()
            if (r2 != 0) goto L_0x0076
            boolean r2 = r4.isPasspoint()
            if (r2 == 0) goto L_0x0020
            goto L_0x0076
        L_0x0020:
            int r2 = r4.networkId
            r3 = -1
            if (r2 == r3) goto L_0x0031
            int r3 = r6.getNetworkId()
            if (r2 != r3) goto L_0x002e
        L_0x002b:
            r2 = r1
            goto L_0x00aa
        L_0x002e:
            r2 = r0
            goto L_0x00aa
        L_0x0031:
            if (r5 == 0) goto L_0x0067
            boolean r2 = r5.isPasspoint()
            if (r2 != 0) goto L_0x0062
            java.lang.String r2 = r4.ssid
            java.lang.String r3 = r6.getSSID()
            java.lang.String r3 = removeDoubleQuotes(r3)
            boolean r2 = android.text.TextUtils.equals(r2, r3)
            if (r2 == 0) goto L_0x004a
            goto L_0x005c
        L_0x004a:
            java.lang.String r2 = r6.getBSSID()
            if (r2 == 0) goto L_0x005e
            java.lang.String r2 = r4.bssid
            java.lang.String r3 = r6.getBSSID()
            boolean r2 = android.text.TextUtils.equals(r2, r3)
            if (r2 == 0) goto L_0x005e
        L_0x005c:
            r2 = r1
            goto L_0x005f
        L_0x005e:
            r2 = r0
        L_0x005f:
            if (r2 != 0) goto L_0x0062
            goto L_0x002e
        L_0x0062:
            boolean r2 = r4.matches((android.net.wifi.WifiConfiguration) r5)
            goto L_0x00aa
        L_0x0067:
            java.lang.String r2 = r6.getSSID()
            java.lang.String r2 = removeDoubleQuotes(r2)
            java.lang.String r3 = r4.ssid
            boolean r2 = android.text.TextUtils.equals(r2, r3)
            goto L_0x00aa
        L_0x0076:
            boolean r2 = r6.isPasspointAp()
            if (r2 == 0) goto L_0x002e
            boolean r2 = r4.isPasspoint()
            if (r2 == 0) goto L_0x002e
            java.lang.String r2 = r6.getPasspointFqdn()
            android.net.wifi.WifiConfiguration r3 = r4.mConfig
            java.lang.String r3 = r3.FQDN
            boolean r2 = android.text.TextUtils.equals(r2, r3)
            if (r2 == 0) goto L_0x002e
            java.lang.String r2 = r6.getPasspointProviderFriendlyName()
            android.net.wifi.WifiConfiguration r3 = r4.mConfig
            java.lang.String r3 = r3.providerFriendlyName
            boolean r2 = android.text.TextUtils.equals(r2, r3)
            if (r2 == 0) goto L_0x002e
            goto L_0x002b
        L_0x009f:
            boolean r2 = r6.isOsuAp()
            if (r2 == 0) goto L_0x002e
            java.lang.String r2 = r4.mOsuStatus
            if (r2 == 0) goto L_0x002e
            goto L_0x002b
        L_0x00aa:
            if (r2 == 0) goto L_0x00ec
            android.net.wifi.WifiInfo r2 = r4.mInfo
            if (r2 != 0) goto L_0x00b1
            r0 = r1
        L_0x00b1:
            boolean r2 = r4.isPasspoint()
            if (r2 != 0) goto L_0x00be
            android.net.wifi.WifiConfiguration r2 = r4.mConfig
            if (r2 == r5) goto L_0x00be
            r4.update(r5)
        L_0x00be:
            int r5 = r4.mRssi
            int r2 = r6.getRssi()
            if (r5 == r2) goto L_0x00d6
            int r5 = r6.getRssi()
            r2 = -127(0xffffffffffffff81, float:NaN)
            if (r5 == r2) goto L_0x00d6
            int r5 = r6.getRssi()
            r4.mRssi = r5
        L_0x00d4:
            r0 = r1
            goto L_0x00e7
        L_0x00d6:
            android.net.NetworkInfo r5 = r4.mNetworkInfo
            if (r5 == 0) goto L_0x00e7
            if (r7 == 0) goto L_0x00e7
            android.net.NetworkInfo$DetailedState r5 = r5.getDetailedState()
            android.net.NetworkInfo$DetailedState r2 = r7.getDetailedState()
            if (r5 == r2) goto L_0x00e7
            goto L_0x00d4
        L_0x00e7:
            r4.mInfo = r6
            r4.mNetworkInfo = r7
            goto L_0x00f6
        L_0x00ec:
            android.net.wifi.WifiInfo r5 = r4.mInfo
            if (r5 == 0) goto L_0x00f6
            r5 = 0
            r4.mInfo = r5
            r4.mNetworkInfo = r5
            r0 = r1
        L_0x00f6:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.wifi.AccessPoint.update(android.net.wifi.WifiConfiguration, android.net.wifi.WifiInfo, android.net.NetworkInfo):boolean");
    }

    public final void update(WifiConfiguration wifiConfiguration) {
        this.mConfig = wifiConfiguration;
        if (wifiConfiguration != null && !isPasspoint()) {
            this.ssid = removeDoubleQuotes(this.mConfig.SSID);
        }
        this.networkId = wifiConfiguration != null ? wifiConfiguration.networkId : -1;
        R$dimen.postOnMainThread(new WifiEntry$$ExternalSyntheticLambda2(this, 1));
    }

    public AccessPoint(Context context, WifiConfiguration wifiConfiguration) {
        this.mLock = new Object();
        this.mScanResults = new ArraySet<>();
        this.mExtraScanResults = new ArraySet<>();
        this.mScoredNetworkCache = new HashMap();
        this.networkId = -1;
        this.pskType = 0;
        this.mRssi = Integer.MIN_VALUE;
        this.mSpeed = 0;
        this.mIsScoredNetworkMetered = false;
        this.mIsPskSaeTransitionMode = false;
        this.mIsOweTransitionMode = false;
        this.mContext = context;
        loadConfig(wifiConfiguration);
        updateKey();
    }

    public AccessPoint(Context context, WifiConfiguration wifiConfiguration, List list, List list2) {
        this.mLock = new Object();
        this.mScanResults = new ArraySet<>();
        this.mExtraScanResults = new ArraySet<>();
        this.mScoredNetworkCache = new HashMap();
        this.pskType = 0;
        this.mRssi = Integer.MIN_VALUE;
        this.mSpeed = 0;
        this.mIsScoredNetworkMetered = false;
        this.mIsPskSaeTransitionMode = false;
        this.mIsOweTransitionMode = false;
        this.mContext = context;
        this.networkId = wifiConfiguration.networkId;
        this.mConfig = wifiConfiguration;
        this.mPasspointUniqueId = wifiConfiguration.getKey();
        setScanResultsPasspoint(list, list2);
        updateKey();
    }

    public AccessPoint(Context context, OsuProvider osuProvider, List list) {
        this.mLock = new Object();
        this.mScanResults = new ArraySet<>();
        this.mExtraScanResults = new ArraySet<>();
        this.mScoredNetworkCache = new HashMap();
        this.networkId = -1;
        this.pskType = 0;
        this.mRssi = Integer.MIN_VALUE;
        this.mSpeed = 0;
        this.mIsScoredNetworkMetered = false;
        this.mIsPskSaeTransitionMode = false;
        this.mIsOweTransitionMode = false;
        this.mContext = context;
        this.mOsuProvider = osuProvider;
        setScanResults(list);
        updateKey();
    }

    public AccessPoint(Context context, List list) {
        this.mLock = new Object();
        this.mScanResults = new ArraySet<>();
        this.mExtraScanResults = new ArraySet<>();
        this.mScoredNetworkCache = new HashMap();
        this.networkId = -1;
        this.pskType = 0;
        this.mRssi = Integer.MIN_VALUE;
        this.mSpeed = 0;
        this.mIsScoredNetworkMetered = false;
        this.mIsPskSaeTransitionMode = false;
        this.mIsOweTransitionMode = false;
        this.mContext = context;
        setScanResults(list);
        updateKey();
    }

    @VisibleForTesting
    public void setRssi(int i) {
        this.mRssi = i;
    }
}
