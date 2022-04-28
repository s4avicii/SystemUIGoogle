package com.android.wifitrackerlib;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import androidx.lifecycle.Lifecycle;
import com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda1;
import com.android.settingslib.dream.DreamBackend$$ExternalSyntheticLambda0;
import com.android.systemui.SystemUIApplication$$ExternalSyntheticLambda4;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda18;
import com.android.systemui.wmshell.WMShell$$ExternalSyntheticLambda2;
import com.android.wifitrackerlib.BaseWifiTracker;
import com.android.wifitrackerlib.StandardWifiEntry;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SavedNetworkTracker extends BaseWifiTracker {
    public WifiEntry mConnectedWifiEntry;
    public NetworkInfo mCurrentNetworkInfo;
    public final SavedNetworkTrackerCallback mListener;
    public final Object mLock = new Object();
    public final HashMap mPasspointWifiEntryCache = new HashMap();
    public final ArrayList mSavedWifiEntries = new ArrayList();
    public final ArrayList mStandardWifiEntryCache = new ArrayList();
    public final ArrayList mSubscriptionWifiEntries = new ArrayList();

    public interface SavedNetworkTrackerCallback extends BaseWifiTracker.BaseWifiTrackerCallback {
        void onSavedWifiEntriesChanged();

        void onSubscriptionWifiEntriesChanged();
    }

    public final void handleWifiStateChangedAction() {
        conditionallyUpdateScanResults(true);
        updateWifiEntries();
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public SavedNetworkTracker(WifiTrackerInjector wifiTrackerInjector, Lifecycle lifecycle, Context context, WifiManager wifiManager, ConnectivityManager connectivityManager, Handler handler, Handler handler2, Clock clock, long j, long j2, SavedNetworkTrackerCallback savedNetworkTrackerCallback) {
        super(wifiTrackerInjector, lifecycle, context, wifiManager, connectivityManager, handler, handler2, clock, j, j2, savedNetworkTrackerCallback, "SavedNetworkTracker");
        this.mListener = savedNetworkTrackerCallback;
    }

    public final void conditionallyUpdateScanResults(boolean z) {
        if (this.mWifiManager.getWifiState() == 1) {
            updateStandardWifiEntryScans(Collections.emptyList());
            updatePasspointWifiEntryScans(Collections.emptyList());
            return;
        }
        long j = this.mMaxScanAgeMillis;
        if (z) {
            this.mScanResultUpdater.update(this.mWifiManager.getScanResults());
        } else {
            j += this.mScanIntervalMillis;
        }
        updateStandardWifiEntryScans(this.mScanResultUpdater.getScanResults(j));
        updatePasspointWifiEntryScans(this.mScanResultUpdater.getScanResults(j));
    }

    public final void handleConfiguredNetworksChangedAction(Intent intent) {
        Objects.requireNonNull(intent, "Intent cannot be null!");
        updateStandardWifiEntryConfigs(this.mWifiManager.getConfiguredNetworks());
        updatePasspointWifiEntryConfigs(this.mWifiManager.getPasspointConfigurations());
        updateWifiEntries();
    }

    public final void handleDefaultRouteChanged() {
        boolean z;
        WifiEntry wifiEntry = this.mConnectedWifiEntry;
        if (wifiEntry != null) {
            wifiEntry.setIsDefaultNetwork(this.mIsWifiDefaultRoute);
            WifiEntry wifiEntry2 = this.mConnectedWifiEntry;
            if (!this.mIsWifiValidated || !this.mIsCellDefaultRoute) {
                z = false;
            } else {
                z = true;
            }
            wifiEntry2.setIsLowQuality(z);
        }
    }

    public final void handleLinkPropertiesChanged(LinkProperties linkProperties) {
        WifiEntry wifiEntry = this.mConnectedWifiEntry;
        if (wifiEntry != null && wifiEntry.getConnectedState() == 2) {
            this.mConnectedWifiEntry.updateLinkProperties(linkProperties);
        }
    }

    public final void handleNetworkCapabilitiesChanged(NetworkCapabilities networkCapabilities) {
        boolean z;
        WifiEntry wifiEntry = this.mConnectedWifiEntry;
        if (wifiEntry != null && wifiEntry.getConnectedState() == 2) {
            this.mConnectedWifiEntry.updateNetworkCapabilities(networkCapabilities);
            WifiEntry wifiEntry2 = this.mConnectedWifiEntry;
            if (!this.mIsWifiValidated || !this.mIsCellDefaultRoute) {
                z = false;
            } else {
                z = true;
            }
            wifiEntry2.setIsLowQuality(z);
        }
    }

    public final void handleNetworkStateChangedAction(Intent intent) {
        Objects.requireNonNull(intent, "Intent cannot be null!");
        this.mCurrentNetworkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
        updateConnectionInfo(this.mWifiManager.getConnectionInfo(), this.mCurrentNetworkInfo);
    }

    public final void handleOnStart() {
        updateStandardWifiEntryConfigs(this.mWifiManager.getConfiguredNetworks());
        updatePasspointWifiEntryConfigs(this.mWifiManager.getPasspointConfigurations());
        boolean z = true;
        conditionallyUpdateScanResults(true);
        WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
        Network currentNetwork = this.mWifiManager.getCurrentNetwork();
        NetworkInfo networkInfo = this.mConnectivityManager.getNetworkInfo(currentNetwork);
        this.mCurrentNetworkInfo = networkInfo;
        updateConnectionInfo(connectionInfo, networkInfo);
        updateWifiEntries();
        handleNetworkCapabilitiesChanged(this.mConnectivityManager.getNetworkCapabilities(currentNetwork));
        handleLinkPropertiesChanged(this.mConnectivityManager.getLinkProperties(currentNetwork));
        WifiEntry wifiEntry = this.mConnectedWifiEntry;
        if (wifiEntry != null) {
            wifiEntry.setIsDefaultNetwork(this.mIsWifiDefaultRoute);
            WifiEntry wifiEntry2 = this.mConnectedWifiEntry;
            if (!this.mIsWifiValidated || !this.mIsCellDefaultRoute) {
                z = false;
            }
            wifiEntry2.setIsLowQuality(z);
        }
    }

    public final void handleRssiChangedAction() {
        WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
        WifiEntry wifiEntry = this.mConnectedWifiEntry;
        if (wifiEntry != null) {
            wifiEntry.updateConnectionInfo(connectionInfo, this.mCurrentNetworkInfo);
        }
    }

    public final void handleScanResultsAvailableAction(Intent intent) {
        Objects.requireNonNull(intent, "Intent cannot be null!");
        conditionallyUpdateScanResults(intent.getBooleanExtra("resultsUpdated", true));
        updateWifiEntries();
    }

    public final void updateConnectionInfo(WifiInfo wifiInfo, NetworkInfo networkInfo) {
        Iterator it = this.mStandardWifiEntryCache.iterator();
        while (it.hasNext()) {
            WifiEntry wifiEntry = (WifiEntry) it.next();
            wifiEntry.updateConnectionInfo(wifiInfo, networkInfo);
            if (wifiEntry.getConnectedState() != 0) {
                this.mConnectedWifiEntry = wifiEntry;
            }
        }
        for (WifiEntry wifiEntry2 : this.mPasspointWifiEntryCache.values()) {
            wifiEntry2.updateConnectionInfo(wifiInfo, networkInfo);
            if (wifiEntry2.getConnectedState() != 0) {
                this.mConnectedWifiEntry = wifiEntry2;
            }
        }
    }

    public final void updatePasspointWifiEntryConfigs(List<PasspointConfiguration> list) {
        Objects.requireNonNull(list, "Config list should not be null!");
        Map map = (Map) list.stream().collect(Collectors.toMap(SystemUIApplication$$ExternalSyntheticLambda4.INSTANCE$2, Function.identity()));
        this.mPasspointWifiEntryCache.entrySet().removeIf(new SavedNetworkTracker$$ExternalSyntheticLambda2(map));
        for (String str : map.keySet()) {
            this.mPasspointWifiEntryCache.put(str, new PasspointWifiEntry(this.mInjector, this.mContext, this.mMainHandler, (PasspointConfiguration) map.get(str), this.mWifiManager, true));
        }
    }

    public final void updatePasspointWifiEntryScans(List<ScanResult> list) {
        Objects.requireNonNull(list, "Scan Result list should not be null!");
        TreeSet treeSet = new TreeSet();
        for (Pair pair : this.mWifiManager.getAllMatchingWifiConfigs(list)) {
            WifiConfiguration wifiConfiguration = (WifiConfiguration) pair.first;
            String uniqueIdToPasspointWifiEntryKey = PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(wifiConfiguration.getKey());
            treeSet.add(uniqueIdToPasspointWifiEntryKey);
            if (this.mPasspointWifiEntryCache.containsKey(uniqueIdToPasspointWifiEntryKey)) {
                ((PasspointWifiEntry) this.mPasspointWifiEntryCache.get(uniqueIdToPasspointWifiEntryKey)).updateScanResultInfo(wifiConfiguration, (List) ((Map) pair.second).get(0), (List) ((Map) pair.second).get(1));
            }
        }
        for (PasspointWifiEntry passpointWifiEntry : this.mPasspointWifiEntryCache.values()) {
            Objects.requireNonNull(passpointWifiEntry);
            if (!treeSet.contains(passpointWifiEntry.mKey)) {
                passpointWifiEntry.updateScanResultInfo((WifiConfiguration) null, (List<ScanResult>) null, (List<ScanResult>) null);
            }
        }
    }

    public final void updateStandardWifiEntryConfigs(List<WifiConfiguration> list) {
        Objects.requireNonNull(list, "Config list should not be null!");
        Map map = (Map) list.stream().filter(SavedNetworkTracker$$ExternalSyntheticLambda3.INSTANCE).collect(Collectors.groupingBy(SavedNetworkTracker$$ExternalSyntheticLambda0.INSTANCE));
        this.mStandardWifiEntryCache.removeIf(new SavedNetworkTracker$$ExternalSyntheticLambda1(map, 0));
        for (StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey : map.keySet()) {
            this.mStandardWifiEntryCache.add(new StandardWifiEntry(this.mInjector, this.mContext, this.mMainHandler, standardWifiEntryKey, (List) map.get(standardWifiEntryKey), (List<ScanResult>) null, this.mWifiManager, true));
        }
    }

    public final void updateStandardWifiEntryScans(List<ScanResult> list) {
        Objects.requireNonNull(list, "Scan Result list should not be null!");
        this.mStandardWifiEntryCache.forEach(new WMShell$$ExternalSyntheticLambda2((Map) list.stream().collect(Collectors.groupingBy(DreamBackend$$ExternalSyntheticLambda0.INSTANCE$1)), 4));
    }

    public final void updateWifiEntries() {
        synchronized (this.mLock) {
            this.mConnectedWifiEntry = null;
            Iterator it = this.mStandardWifiEntryCache.iterator();
            while (it.hasNext()) {
                WifiEntry wifiEntry = (WifiEntry) it.next();
                if (wifiEntry.getConnectedState() != 0) {
                    this.mConnectedWifiEntry = wifiEntry;
                }
            }
            Iterator it2 = this.mSubscriptionWifiEntries.iterator();
            while (it2.hasNext()) {
                WifiEntry wifiEntry2 = (WifiEntry) it2.next();
                if (wifiEntry2.getConnectedState() != 0) {
                    this.mConnectedWifiEntry = wifiEntry2;
                }
            }
            WifiEntry wifiEntry3 = this.mConnectedWifiEntry;
            if (wifiEntry3 != null) {
                wifiEntry3.setIsDefaultNetwork(this.mIsWifiDefaultRoute);
            }
            this.mSavedWifiEntries.clear();
            this.mSavedWifiEntries.addAll(this.mStandardWifiEntryCache);
            Collections.sort(this.mSavedWifiEntries);
            this.mSubscriptionWifiEntries.clear();
            this.mSubscriptionWifiEntries.addAll(this.mPasspointWifiEntryCache.values());
            Collections.sort(this.mSubscriptionWifiEntries);
            if (BaseWifiTracker.sVerboseLogging) {
                Log.v("SavedNetworkTracker", "Updated SavedWifiEntries: " + Arrays.toString(this.mSavedWifiEntries.toArray()));
                Log.v("SavedNetworkTracker", "Updated SubscriptionWifiEntries: " + Arrays.toString(this.mSubscriptionWifiEntries.toArray()));
            }
        }
        SavedNetworkTrackerCallback savedNetworkTrackerCallback = this.mListener;
        if (savedNetworkTrackerCallback != null) {
            this.mMainHandler.post(new LockIconViewController$$ExternalSyntheticLambda1(savedNetworkTrackerCallback, 8));
        }
        SavedNetworkTrackerCallback savedNetworkTrackerCallback2 = this.mListener;
        if (savedNetworkTrackerCallback2 != null) {
            this.mMainHandler.post(new StatusBar$$ExternalSyntheticLambda18(savedNetworkTrackerCallback2, 13));
        }
    }
}
