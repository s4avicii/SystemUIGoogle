package com.android.wifitrackerlib;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.p006qs.tiles.dialog.InternetDialogController;
import com.android.wifitrackerlib.WifiEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class PasspointWifiEntry extends WifiEntry implements WifiEntry.WifiEntryCallback {
    public final Context mContext;
    public final ArrayList mCurrentHomeScanResults = new ArrayList();
    public final ArrayList mCurrentRoamingScanResults = new ArrayList();
    public final String mFqdn;
    public final String mFriendlyName;
    public final WifiTrackerInjector mInjector;
    public final String mKey;
    public int mMeteredOverride = 0;
    public OsuWifiEntry mOsuWifiEntry;
    public PasspointConfiguration mPasspointConfig;
    public boolean mShouldAutoOpenCaptivePortal = false;
    public long mSubscriptionExpirationTimeInMillis;
    public List<Integer> mTargetSecurityTypes = List.of(11, 12);
    public WifiConfiguration mWifiConfig;

    public PasspointWifiEntry(WifiTrackerInjector wifiTrackerInjector, Context context, Handler handler, PasspointConfiguration passpointConfiguration, WifiManager wifiManager, boolean z) throws IllegalArgumentException {
        super(handler, wifiManager, z);
        Objects.requireNonNull(passpointConfiguration, "Cannot construct with null PasspointConfiguration!");
        this.mInjector = wifiTrackerInjector;
        this.mContext = context;
        this.mPasspointConfig = passpointConfiguration;
        this.mKey = uniqueIdToPasspointWifiEntryKey(passpointConfiguration.getUniqueId());
        String fqdn = passpointConfiguration.getHomeSp().getFqdn();
        this.mFqdn = fqdn;
        Objects.requireNonNull(fqdn, "Cannot construct with null PasspointConfiguration FQDN!");
        this.mFriendlyName = passpointConfiguration.getHomeSp().getFriendlyName();
        this.mSubscriptionExpirationTimeInMillis = passpointConfiguration.getSubscriptionExpirationTimeMillis();
        this.mMeteredOverride = this.mPasspointConfig.getMeteredOverride();
    }

    public final synchronized boolean canSetAutoJoinEnabled() {
        boolean z;
        if (this.mPasspointConfig == null && this.mWifiConfig == null) {
            z = false;
        } else {
            z = true;
        }
        return z;
    }

    public final synchronized boolean canSetMeteredChoice() {
        boolean z;
        if (isSuggestion() || this.mPasspointConfig == null) {
            z = false;
        } else {
            z = true;
        }
        return z;
    }

    public final synchronized boolean canSignIn() {
        boolean z;
        NetworkCapabilities networkCapabilities = this.mNetworkCapabilities;
        if (networkCapabilities == null || !networkCapabilities.hasCapability(17)) {
            z = false;
        } else {
            z = true;
        }
        return z;
    }

    public final synchronized void connect(InternetDialogController.WifiEntryConnectCallback wifiEntryConnectCallback) {
        OsuWifiEntry osuWifiEntry;
        if (!isExpired() || (osuWifiEntry = this.mOsuWifiEntry) == null) {
            this.mShouldAutoOpenCaptivePortal = true;
            this.mConnectCallback = wifiEntryConnectCallback;
            if (this.mWifiConfig == null) {
                new WifiEntry.ConnectActionListener().onFailure(0);
            }
            this.mWifiManager.stopRestrictingAutoJoinToSubscriptionId();
            this.mWifiManager.connect(this.mWifiConfig, new WifiEntry.ConnectActionListener());
            return;
        }
        osuWifiEntry.connect(wifiEntryConnectCallback);
    }

    public final synchronized int getConnectedState() {
        OsuWifiEntry osuWifiEntry;
        if (!isExpired() || super.getConnectedState() != 0 || (osuWifiEntry = this.mOsuWifiEntry) == null) {
            return super.getConnectedState();
        }
        return osuWifiEntry.getConnectedState();
    }

    public final synchronized int getMeteredChoice() {
        int i = this.mMeteredOverride;
        if (i == 1) {
            return 1;
        }
        if (i == 2) {
            return 2;
        }
        return 0;
    }

    public final synchronized String getNetworkSelectionDescription() {
        return Utils.getNetworkSelectionDescription(this.mWifiConfig);
    }

    public final String getScanResultDescription() {
        return "";
    }

    public final synchronized List<Integer> getSecurityTypes() {
        return new ArrayList(this.mTargetSecurityTypes);
    }

    public final synchronized String getSummary(boolean z) {
        StringJoiner stringJoiner;
        String str;
        stringJoiner = new StringJoiner(this.mContext.getString(C1777R.string.wifitrackerlib_summary_separator));
        if (isExpired()) {
            OsuWifiEntry osuWifiEntry = this.mOsuWifiEntry;
            if (osuWifiEntry != null) {
                stringJoiner.add(osuWifiEntry.getSummary(z));
            } else {
                stringJoiner.add(this.mContext.getString(C1777R.string.wifitrackerlib_wifi_passpoint_expired));
            }
        } else {
            int connectedState = getConnectedState();
            if (connectedState == 0) {
                str = Utils.getDisconnectedDescription(this.mInjector, this.mContext, this.mWifiConfig, this.mForSavedNetworksPage, z);
            } else if (connectedState == 1) {
                str = Utils.getConnectingDescription(this.mContext, this.mNetworkInfo);
            } else if (connectedState != 2) {
                Log.e("PasspointWifiEntry", "getConnectedState() returned unknown state: " + connectedState);
                str = null;
            } else {
                str = Utils.getConnectedDescription(this.mContext, this.mWifiConfig, this.mNetworkCapabilities, this.mIsDefaultNetwork, this.mIsLowQuality);
            }
            if (!TextUtils.isEmpty(str)) {
                stringJoiner.add(str);
            }
        }
        Context context = this.mContext;
        String str2 = "";
        if (context != null) {
            if (canSetAutoJoinEnabled()) {
                if (!isAutoJoinEnabled()) {
                    str2 = context.getString(C1777R.string.wifitrackerlib_auto_connect_disable);
                }
            }
        }
        if (!TextUtils.isEmpty(str2)) {
            stringJoiner.add(str2);
        }
        String meteredDescription = Utils.getMeteredDescription(this.mContext, this);
        if (!TextUtils.isEmpty(meteredDescription)) {
            stringJoiner.add(meteredDescription);
        }
        if (!z) {
            String verboseLoggingDescription = Utils.getVerboseLoggingDescription(this);
            if (!TextUtils.isEmpty(verboseLoggingDescription)) {
                stringJoiner.add(verboseLoggingDescription);
            }
        }
        return stringJoiner.toString();
    }

    public final synchronized boolean isAutoJoinEnabled() {
        PasspointConfiguration passpointConfiguration = this.mPasspointConfig;
        if (passpointConfiguration != null) {
            return passpointConfiguration.isAutojoinEnabled();
        }
        WifiConfiguration wifiConfiguration = this.mWifiConfig;
        if (wifiConfiguration == null) {
            return false;
        }
        return wifiConfiguration.allowAutojoin;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0018, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized boolean isExpired() {
        /*
            r6 = this;
            monitor-enter(r6)
            long r0 = r6.mSubscriptionExpirationTimeInMillis     // Catch:{ all -> 0x0019 }
            r2 = 0
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            r1 = 0
            if (r0 > 0) goto L_0x000c
            monitor-exit(r6)
            return r1
        L_0x000c:
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0019 }
            long r4 = r6.mSubscriptionExpirationTimeInMillis     // Catch:{ all -> 0x0019 }
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 < 0) goto L_0x0017
            r1 = 1
        L_0x0017:
            monitor-exit(r6)
            return r1
        L_0x0019:
            r0 = move-exception
            monitor-exit(r6)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wifitrackerlib.PasspointWifiEntry.isExpired():boolean");
    }

    public final synchronized boolean isMetered() {
        boolean z;
        WifiConfiguration wifiConfiguration;
        z = true;
        if (getMeteredChoice() != 1 && ((wifiConfiguration = this.mWifiConfig) == null || !wifiConfiguration.meteredHint)) {
            z = false;
        }
        return z;
    }

    public final synchronized boolean isSubscription() {
        boolean z;
        if (this.mPasspointConfig != null) {
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    public final synchronized boolean isSuggestion() {
        boolean z;
        WifiConfiguration wifiConfiguration = this.mWifiConfig;
        if (wifiConfiguration == null || !wifiConfiguration.fromWifiNetworkSuggestion) {
            z = false;
        } else {
            z = true;
        }
        return z;
    }

    public final synchronized void updateNetworkCapabilities(NetworkCapabilities networkCapabilities) {
        super.updateNetworkCapabilities(networkCapabilities);
        if (canSignIn() && this.mShouldAutoOpenCaptivePortal) {
            this.mShouldAutoOpenCaptivePortal = false;
            if (canSignIn()) {
                ((ConnectivityManager) this.mContext.getSystemService(ConnectivityManager.class)).startCaptivePortalApp(this.mWifiManager.getCurrentNetwork());
            }
        }
    }

    public final synchronized void updatePasspointConfig(PasspointConfiguration passpointConfiguration) {
        this.mPasspointConfig = passpointConfiguration;
        if (passpointConfiguration != null) {
            this.mSubscriptionExpirationTimeInMillis = passpointConfiguration.getSubscriptionExpirationTimeMillis();
            this.mMeteredOverride = passpointConfiguration.getMeteredOverride();
        }
        notifyOnUpdated();
    }

    public final synchronized void updateScanResultInfo(WifiConfiguration wifiConfiguration, List<ScanResult> list, List<ScanResult> list2) throws IllegalArgumentException {
        this.mWifiConfig = wifiConfiguration;
        this.mCurrentHomeScanResults.clear();
        this.mCurrentRoamingScanResults.clear();
        if (list != null) {
            this.mCurrentHomeScanResults.addAll(list);
        }
        if (list2 != null) {
            this.mCurrentRoamingScanResults.addAll(list2);
        }
        int i = -1;
        if (this.mWifiConfig != null) {
            ArrayList arrayList = new ArrayList();
            if (list != null && !list.isEmpty()) {
                arrayList.addAll(list);
            } else if (list2 != null && !list2.isEmpty()) {
                arrayList.addAll(list2);
            }
            ScanResult bestScanResultByLevel = Utils.getBestScanResultByLevel(arrayList);
            if (bestScanResultByLevel != null) {
                WifiConfiguration wifiConfiguration2 = this.mWifiConfig;
                wifiConfiguration2.SSID = "\"" + bestScanResultByLevel.SSID + "\"";
            }
            if (getConnectedState() == 0) {
                if (bestScanResultByLevel != null) {
                    i = this.mWifiManager.calculateSignalLevel(bestScanResultByLevel.level);
                }
                this.mLevel = i;
            }
        } else {
            this.mLevel = -1;
        }
        notifyOnUpdated();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0019, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void updateSecurityTypes() {
        /*
            r2 = this;
            monitor-enter(r2)
            android.net.wifi.WifiInfo r0 = r2.mWifiInfo     // Catch:{ all -> 0x001a }
            if (r0 == 0) goto L_0x0018
            int r0 = r0.getCurrentSecurityType()     // Catch:{ all -> 0x001a }
            r1 = -1
            if (r0 == r1) goto L_0x0018
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ all -> 0x001a }
            java.util.List r0 = java.util.Collections.singletonList(r0)     // Catch:{ all -> 0x001a }
            r2.mTargetSecurityTypes = r0     // Catch:{ all -> 0x001a }
            monitor-exit(r2)
            return
        L_0x0018:
            monitor-exit(r2)
            return
        L_0x001a:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wifitrackerlib.PasspointWifiEntry.updateSecurityTypes():void");
    }

    public static String uniqueIdToPasspointWifiEntryKey(String str) {
        Objects.requireNonNull(str, "Cannot create key with null unique id!");
        return "PasspointWifiEntry:" + str;
    }

    public final boolean connectionInfoMatches(WifiInfo wifiInfo) {
        if (!wifiInfo.isPasspointAp()) {
            return false;
        }
        return TextUtils.equals(wifiInfo.getPasspointFqdn(), this.mFqdn);
    }

    public PasspointWifiEntry(WifiTrackerInjector wifiTrackerInjector, Context context, Handler handler, WifiConfiguration wifiConfiguration, WifiManager wifiManager) throws IllegalArgumentException {
        super(handler, wifiManager, false);
        Objects.requireNonNull(wifiConfiguration, "Cannot construct with null WifiConfiguration!");
        if (wifiConfiguration.isPasspoint()) {
            this.mInjector = wifiTrackerInjector;
            this.mContext = context;
            this.mWifiConfig = wifiConfiguration;
            this.mKey = uniqueIdToPasspointWifiEntryKey(wifiConfiguration.getKey());
            String str = wifiConfiguration.FQDN;
            this.mFqdn = str;
            Objects.requireNonNull(str, "Cannot construct with null WifiConfiguration FQDN!");
            this.mFriendlyName = this.mWifiConfig.providerFriendlyName;
            return;
        }
        throw new IllegalArgumentException("Given WifiConfiguration is not for Passpoint!");
    }

    public final String getKey() {
        return this.mKey;
    }

    public final String getTitle() {
        return this.mFriendlyName;
    }

    public final void onUpdated() {
        notifyOnUpdated();
    }
}
