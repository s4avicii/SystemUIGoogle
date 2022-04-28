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
import android.net.wifi.hotspot2.OsuProvider;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.os.Handler;
import android.telephony.SubscriptionManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import androidx.lifecycle.Lifecycle;
import com.android.p012wm.shell.bubbles.BubbleData$$ExternalSyntheticLambda7;
import com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda2;
import com.android.systemui.people.PeopleSpaceUtils$$ExternalSyntheticLambda8;
import com.android.systemui.power.PowerUI$$ExternalSyntheticLambda0;
import com.android.wifitrackerlib.BaseWifiTracker;
import com.android.wifitrackerlib.StandardWifiEntry;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WifiPickerTracker extends BaseWifiTracker {
    public WifiEntry mConnectedWifiEntry;
    public NetworkInfo mCurrentNetworkInfo;
    public final WifiPickerTrackerCallback mListener;
    public final Object mLock = new Object();
    public MergedCarrierEntry mMergedCarrierEntry;
    public final ArrayMap<StandardWifiEntry.StandardWifiEntryKey, List<WifiConfiguration>> mNetworkRequestConfigCache = new ArrayMap<>();
    public NetworkRequestEntry mNetworkRequestEntry;
    public final ArrayMap mOsuWifiEntryCache = new ArrayMap();
    public final ArrayMap mPasspointConfigCache = new ArrayMap();
    public final SparseArray<WifiConfiguration> mPasspointWifiConfigCache = new SparseArray<>();
    public final ArrayMap mPasspointWifiEntryCache = new ArrayMap();
    public final ArrayMap mStandardWifiConfigCache = new ArrayMap();
    public final ArrayList mStandardWifiEntryCache = new ArrayList();
    public final ArrayMap mSuggestedConfigCache = new ArrayMap();
    public final ArrayList mSuggestedWifiEntryCache = new ArrayList();
    public final ArrayList mWifiEntries = new ArrayList();

    public interface WifiPickerTrackerCallback extends BaseWifiTracker.BaseWifiTrackerCallback {
    }

    public final void handleDefaultSubscriptionChanged(int i) {
        if (i != -1) {
            MergedCarrierEntry mergedCarrierEntry = this.mMergedCarrierEntry;
            if (mergedCarrierEntry == null || i != mergedCarrierEntry.mSubscriptionId) {
                MergedCarrierEntry mergedCarrierEntry2 = new MergedCarrierEntry(this.mWorkerHandler, this.mWifiManager, this.mContext, i);
                this.mMergedCarrierEntry = mergedCarrierEntry2;
                mergedCarrierEntry2.updateConnectionInfo(this.mWifiManager.getConnectionInfo(), this.mCurrentNetworkInfo);
            } else {
                return;
            }
        } else if (this.mMergedCarrierEntry != null) {
            this.mMergedCarrierEntry = null;
        } else {
            return;
        }
        WifiPickerTrackerCallback wifiPickerTrackerCallback = this.mListener;
        if (wifiPickerTrackerCallback != null) {
            this.mMainHandler.post(new PowerUI$$ExternalSyntheticLambda0(wifiPickerTrackerCallback, 5));
        }
    }

    public final void handleWifiStateChangedAction() {
        conditionallyUpdateScanResults(true);
        if (this.mWifiManager.getWifiState() != 3) {
            updateConnectionInfo((WifiInfo) null, (NetworkInfo) null);
        }
        updateWifiEntries();
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public WifiPickerTracker(WifiTrackerInjector wifiTrackerInjector, Lifecycle lifecycle, Context context, WifiManager wifiManager, ConnectivityManager connectivityManager, Handler handler, Handler handler2, Clock clock, long j, long j2, WifiPickerTrackerCallback wifiPickerTrackerCallback) {
        super(wifiTrackerInjector, lifecycle, context, wifiManager, connectivityManager, handler, handler2, clock, j, j2, wifiPickerTrackerCallback, "WifiPickerTracker");
        this.mListener = wifiPickerTrackerCallback;
    }

    public final void conditionallyUpdateScanResults(boolean z) {
        if (this.mWifiManager.getWifiState() == 1) {
            updateStandardWifiEntryScans(Collections.emptyList());
            updateSuggestedWifiEntryScans(Collections.emptyList());
            updatePasspointWifiEntryScans(Collections.emptyList());
            updateOsuWifiEntryScans(Collections.emptyList());
            updateNetworkRequestEntryScans(Collections.emptyList());
            Collections.emptyList();
            return;
        }
        long j = this.mMaxScanAgeMillis;
        if (z) {
            this.mScanResultUpdater.update(this.mWifiManager.getScanResults());
        } else {
            j += this.mScanIntervalMillis;
        }
        ArrayList scanResults = this.mScanResultUpdater.getScanResults(j);
        updateStandardWifiEntryScans(scanResults);
        updateSuggestedWifiEntryScans(scanResults);
        updatePasspointWifiEntryScans(scanResults);
        updateOsuWifiEntryScans(scanResults);
        updateNetworkRequestEntryScans(scanResults);
    }

    public final void handleConfiguredNetworksChangedAction(Intent intent) {
        Objects.requireNonNull(intent, "Intent cannot be null!");
        updateWifiConfigurations(this.mWifiManager.getPrivilegedConfiguredNetworks());
        updatePasspointConfigurations(this.mWifiManager.getPasspointConfigurations());
        ScanResultUpdater scanResultUpdater = this.mScanResultUpdater;
        Objects.requireNonNull(scanResultUpdater);
        ArrayList scanResults = scanResultUpdater.getScanResults(scanResultUpdater.mMaxScanAgeMillis);
        updateStandardWifiEntryScans(scanResults);
        updateNetworkRequestEntryScans(scanResults);
        updatePasspointWifiEntryScans(scanResults);
        updateOsuWifiEntryScans(scanResults);
        WifiPickerTrackerCallback wifiPickerTrackerCallback = this.mListener;
        if (wifiPickerTrackerCallback != null) {
            this.mMainHandler.post(new BaseWifiTracker$$ExternalSyntheticLambda1(wifiPickerTrackerCallback, 6));
        }
        WifiPickerTrackerCallback wifiPickerTrackerCallback2 = this.mListener;
        if (wifiPickerTrackerCallback2 != null) {
            this.mMainHandler.post(new ScreenDecorations$$ExternalSyntheticLambda2(wifiPickerTrackerCallback2, 7));
        }
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
        MergedCarrierEntry mergedCarrierEntry = this.mMergedCarrierEntry;
        if (mergedCarrierEntry != null) {
            if (mergedCarrierEntry.getConnectedState() == 2) {
                this.mMergedCarrierEntry.setIsDefaultNetwork(this.mIsWifiDefaultRoute);
            }
            MergedCarrierEntry mergedCarrierEntry2 = this.mMergedCarrierEntry;
            boolean z2 = this.mIsCellDefaultRoute;
            Objects.requireNonNull(mergedCarrierEntry2);
            synchronized (mergedCarrierEntry2) {
                mergedCarrierEntry2.mIsCellDefaultRoute = z2;
                mergedCarrierEntry2.notifyOnUpdated();
            }
        }
    }

    public final void handleLinkPropertiesChanged(LinkProperties linkProperties) {
        WifiEntry wifiEntry = this.mConnectedWifiEntry;
        if (wifiEntry != null && wifiEntry.getConnectedState() == 2) {
            this.mConnectedWifiEntry.updateLinkProperties(linkProperties);
        }
        MergedCarrierEntry mergedCarrierEntry = this.mMergedCarrierEntry;
        if (mergedCarrierEntry != null) {
            mergedCarrierEntry.updateLinkProperties(linkProperties);
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
        MergedCarrierEntry mergedCarrierEntry = this.mMergedCarrierEntry;
        if (mergedCarrierEntry != null) {
            mergedCarrierEntry.updateNetworkCapabilities(networkCapabilities);
        }
    }

    public final void handleNetworkStateChangedAction(Intent intent) {
        Objects.requireNonNull(intent, "Intent cannot be null!");
        this.mCurrentNetworkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
        updateConnectionInfo(this.mWifiManager.getConnectionInfo(), this.mCurrentNetworkInfo);
        updateWifiEntries();
    }

    public final void handleOnStart() {
        updateWifiConfigurations(this.mWifiManager.getPrivilegedConfiguredNetworks());
        updatePasspointConfigurations(this.mWifiManager.getPasspointConfigurations());
        this.mScanResultUpdater.update(this.mWifiManager.getScanResults());
        conditionallyUpdateScanResults(true);
        WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
        Network currentNetwork = this.mWifiManager.getCurrentNetwork();
        NetworkInfo networkInfo = this.mConnectivityManager.getNetworkInfo(currentNetwork);
        this.mCurrentNetworkInfo = networkInfo;
        updateConnectionInfo(connectionInfo, networkInfo);
        WifiPickerTrackerCallback wifiPickerTrackerCallback = this.mListener;
        if (wifiPickerTrackerCallback != null) {
            this.mMainHandler.post(new BaseWifiTracker$$ExternalSyntheticLambda1(wifiPickerTrackerCallback, 6));
        }
        WifiPickerTrackerCallback wifiPickerTrackerCallback2 = this.mListener;
        if (wifiPickerTrackerCallback2 != null) {
            this.mMainHandler.post(new ScreenDecorations$$ExternalSyntheticLambda2(wifiPickerTrackerCallback2, 7));
        }
        handleDefaultSubscriptionChanged(SubscriptionManager.getDefaultDataSubscriptionId());
        updateWifiEntries();
        handleNetworkCapabilitiesChanged(this.mConnectivityManager.getNetworkCapabilities(currentNetwork));
        handleLinkPropertiesChanged(this.mConnectivityManager.getLinkProperties(currentNetwork));
        handleDefaultRouteChanged();
    }

    public final void handleRssiChangedAction() {
        WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
        WifiEntry wifiEntry = this.mConnectedWifiEntry;
        if (wifiEntry != null) {
            wifiEntry.updateConnectionInfo(connectionInfo, this.mCurrentNetworkInfo);
        }
        MergedCarrierEntry mergedCarrierEntry = this.mMergedCarrierEntry;
        if (mergedCarrierEntry != null) {
            mergedCarrierEntry.updateConnectionInfo(connectionInfo, this.mCurrentNetworkInfo);
        }
    }

    public final void handleScanResultsAvailableAction(Intent intent) {
        Objects.requireNonNull(intent, "Intent cannot be null!");
        conditionallyUpdateScanResults(intent.getBooleanExtra("resultsUpdated", true));
        updateWifiEntries();
    }

    public final void updateConnectionInfo(WifiInfo wifiInfo, NetworkInfo networkInfo) {
        WifiConfiguration wifiConfiguration;
        PasspointWifiEntry passpointWifiEntry;
        WifiInfo wifiInfo2 = wifiInfo;
        NetworkInfo networkInfo2 = networkInfo;
        Iterator it = this.mStandardWifiEntryCache.iterator();
        while (it.hasNext()) {
            ((WifiEntry) it.next()).updateConnectionInfo(wifiInfo2, networkInfo2);
        }
        Iterator it2 = this.mSuggestedWifiEntryCache.iterator();
        while (it2.hasNext()) {
            ((WifiEntry) it2.next()).updateConnectionInfo(wifiInfo2, networkInfo2);
        }
        for (WifiEntry updateConnectionInfo : this.mPasspointWifiEntryCache.values()) {
            updateConnectionInfo.updateConnectionInfo(wifiInfo2, networkInfo2);
        }
        for (WifiEntry updateConnectionInfo2 : this.mOsuWifiEntryCache.values()) {
            updateConnectionInfo2.updateConnectionInfo(wifiInfo2, networkInfo2);
        }
        NetworkRequestEntry networkRequestEntry = this.mNetworkRequestEntry;
        if (networkRequestEntry != null) {
            networkRequestEntry.updateConnectionInfo(wifiInfo2, networkInfo2);
        }
        ArrayList arrayList = new ArrayList();
        if (wifiInfo2 != null) {
            int i = 0;
            while (true) {
                if (i >= this.mNetworkRequestConfigCache.size()) {
                    break;
                }
                List valueAt = this.mNetworkRequestConfigCache.valueAt(i);
                if (!valueAt.isEmpty() && ((WifiConfiguration) valueAt.get(0)).networkId == wifiInfo.getNetworkId()) {
                    arrayList.addAll(valueAt);
                    break;
                }
                i++;
            }
        }
        if (arrayList.isEmpty()) {
            this.mNetworkRequestEntry = null;
        } else {
            StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey = new StandardWifiEntry.StandardWifiEntryKey((WifiConfiguration) arrayList.get(0), false);
            NetworkRequestEntry networkRequestEntry2 = this.mNetworkRequestEntry;
            if (networkRequestEntry2 == null || !networkRequestEntry2.mKey.equals(standardWifiEntryKey)) {
                NetworkRequestEntry networkRequestEntry3 = new NetworkRequestEntry(this.mInjector, this.mContext, this.mMainHandler, standardWifiEntryKey, this.mWifiManager);
                this.mNetworkRequestEntry = networkRequestEntry3;
                networkRequestEntry3.updateConfig(arrayList);
                ScanResultUpdater scanResultUpdater = this.mScanResultUpdater;
                Objects.requireNonNull(scanResultUpdater);
                updateNetworkRequestEntryScans(scanResultUpdater.getScanResults(scanResultUpdater.mMaxScanAgeMillis));
            }
            this.mNetworkRequestEntry.updateConnectionInfo(wifiInfo2, networkInfo2);
        }
        MergedCarrierEntry mergedCarrierEntry = this.mMergedCarrierEntry;
        if (mergedCarrierEntry != null) {
            mergedCarrierEntry.updateConnectionInfo(wifiInfo2, networkInfo2);
        }
        if (wifiInfo2 != null && !wifiInfo.isPasspointAp() && !wifiInfo.isOsuAp()) {
            int networkId = wifiInfo.getNetworkId();
            Iterator it3 = this.mStandardWifiConfigCache.values().iterator();
            while (true) {
                if (!it3.hasNext()) {
                    break;
                }
                List list = (List) it3.next();
                if (list.stream().map(WifiPickerTracker$$ExternalSyntheticLambda11.INSTANCE).filter(new Utils$$ExternalSyntheticLambda0(networkId, 2)).count() != 0) {
                    StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey2 = new StandardWifiEntry.StandardWifiEntryKey((WifiConfiguration) list.get(0), true);
                    Iterator it4 = this.mStandardWifiEntryCache.iterator();
                    while (true) {
                        if (!it4.hasNext()) {
                            StandardWifiEntry standardWifiEntry = new StandardWifiEntry(this.mInjector, this.mContext, this.mMainHandler, standardWifiEntryKey2, list, (List<ScanResult>) null, this.mWifiManager, false);
                            standardWifiEntry.updateConnectionInfo(wifiInfo2, networkInfo2);
                            this.mStandardWifiEntryCache.add(standardWifiEntry);
                            break;
                        }
                        StandardWifiEntry standardWifiEntry2 = (StandardWifiEntry) it4.next();
                        Objects.requireNonNull(standardWifiEntry2);
                        if (standardWifiEntryKey2.equals(standardWifiEntry2.mKey)) {
                            break;
                        }
                    }
                }
            }
        }
        if (wifiInfo2 != null && !wifiInfo.isPasspointAp() && !wifiInfo.isOsuAp()) {
            int networkId2 = wifiInfo.getNetworkId();
            Iterator it5 = this.mSuggestedConfigCache.values().iterator();
            while (true) {
                if (!it5.hasNext()) {
                    break;
                }
                List list2 = (List) it5.next();
                if (!list2.isEmpty() && ((WifiConfiguration) list2.get(0)).networkId == networkId2) {
                    StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey3 = new StandardWifiEntry.StandardWifiEntryKey((WifiConfiguration) list2.get(0), true);
                    Iterator it6 = this.mSuggestedWifiEntryCache.iterator();
                    while (true) {
                        if (!it6.hasNext()) {
                            StandardWifiEntry standardWifiEntry3 = new StandardWifiEntry(this.mInjector, this.mContext, this.mMainHandler, standardWifiEntryKey3, list2, (List<ScanResult>) null, this.mWifiManager, false);
                            standardWifiEntry3.updateConnectionInfo(wifiInfo2, networkInfo2);
                            this.mSuggestedWifiEntryCache.add(standardWifiEntry3);
                            break;
                        }
                        StandardWifiEntry standardWifiEntry4 = (StandardWifiEntry) it6.next();
                        Objects.requireNonNull(standardWifiEntry4);
                        if (standardWifiEntryKey3.equals(standardWifiEntry4.mKey)) {
                            break;
                        }
                    }
                }
            }
        }
        if (wifiInfo2 != null && wifiInfo.isPasspointAp() && (wifiConfiguration = this.mPasspointWifiConfigCache.get(wifiInfo.getNetworkId())) != null) {
            if (!this.mPasspointWifiEntryCache.containsKey(PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(wifiConfiguration.getKey()))) {
                PasspointConfiguration passpointConfiguration = (PasspointConfiguration) this.mPasspointConfigCache.get(PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(wifiConfiguration.getKey()));
                if (passpointConfiguration != null) {
                    passpointWifiEntry = new PasspointWifiEntry(this.mInjector, this.mContext, this.mMainHandler, passpointConfiguration, this.mWifiManager, false);
                } else {
                    passpointWifiEntry = new PasspointWifiEntry(this.mInjector, this.mContext, this.mMainHandler, wifiConfiguration, this.mWifiManager);
                }
                passpointWifiEntry.updateConnectionInfo(wifiInfo2, networkInfo2);
                this.mPasspointWifiEntryCache.put(passpointWifiEntry.mKey, passpointWifiEntry);
            }
        }
    }

    public final void updateNetworkRequestEntryScans(List<ScanResult> list) {
        Objects.requireNonNull(list, "Scan Result list should not be null!");
        NetworkRequestEntry networkRequestEntry = this.mNetworkRequestEntry;
        if (networkRequestEntry != null) {
            StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey = networkRequestEntry.mKey;
            Objects.requireNonNull(standardWifiEntryKey);
            StandardWifiEntry.ScanResultKey scanResultKey = standardWifiEntryKey.mScanResultKey;
            this.mNetworkRequestEntry.updateScanResultInfo((List) list.stream().filter(new WifiPickerTracker$$ExternalSyntheticLambda14(scanResultKey)).collect(Collectors.toList()));
        }
    }

    public final void updateOsuWifiEntryScans(List<ScanResult> list) {
        Objects.requireNonNull(list, "Scan Result list should not be null!");
        Map matchingOsuProviders = this.mWifiManager.getMatchingOsuProviders(list);
        Map matchingPasspointConfigsForOsuProviders = this.mWifiManager.getMatchingPasspointConfigsForOsuProviders(matchingOsuProviders.keySet());
        for (OsuWifiEntry osuWifiEntry : this.mOsuWifiEntryCache.values()) {
            Objects.requireNonNull(osuWifiEntry);
            osuWifiEntry.updateScanResultInfo((List) matchingOsuProviders.remove(osuWifiEntry.mOsuProvider));
        }
        for (OsuProvider osuProvider : matchingOsuProviders.keySet()) {
            OsuWifiEntry osuWifiEntry2 = new OsuWifiEntry(this.mInjector, this.mContext, this.mMainHandler, osuProvider, this.mWifiManager);
            osuWifiEntry2.updateScanResultInfo((List) matchingOsuProviders.get(osuProvider));
            this.mOsuWifiEntryCache.put(OsuWifiEntry.osuProviderToOsuWifiEntryKey(osuProvider), osuWifiEntry2);
        }
        this.mOsuWifiEntryCache.values().forEach(new WifiPickerTracker$$ExternalSyntheticLambda1(this, matchingPasspointConfigsForOsuProviders, 0));
        this.mOsuWifiEntryCache.entrySet().removeIf(WifiPickerTracker$$ExternalSyntheticLambda19.INSTANCE);
    }

    public final void updatePasspointConfigurations(List<PasspointConfiguration> list) {
        Objects.requireNonNull(list, "Config list should not be null!");
        this.mPasspointConfigCache.clear();
        this.mPasspointConfigCache.putAll((Map) list.stream().collect(Collectors.toMap(WifiPickerTracker$$ExternalSyntheticLambda6.INSTANCE, Function.identity())));
        this.mPasspointWifiEntryCache.entrySet().removeIf(new WifiPickerTracker$$ExternalSyntheticLambda15(this));
    }

    public final void updatePasspointWifiEntryScans(List<ScanResult> list) {
        List<ScanResult> list2 = list;
        Objects.requireNonNull(list2, "Scan Result list should not be null!");
        TreeSet treeSet = new TreeSet();
        for (Pair pair : this.mWifiManager.getAllMatchingWifiConfigs(list2)) {
            WifiConfiguration wifiConfiguration = (WifiConfiguration) pair.first;
            List list3 = (List) ((Map) pair.second).get(0);
            List list4 = (List) ((Map) pair.second).get(1);
            String uniqueIdToPasspointWifiEntryKey = PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(wifiConfiguration.getKey());
            treeSet.add(uniqueIdToPasspointWifiEntryKey);
            if (!this.mPasspointWifiEntryCache.containsKey(uniqueIdToPasspointWifiEntryKey)) {
                if (wifiConfiguration.fromWifiNetworkSuggestion) {
                    this.mPasspointWifiEntryCache.put(uniqueIdToPasspointWifiEntryKey, new PasspointWifiEntry(this.mInjector, this.mContext, this.mMainHandler, wifiConfiguration, this.mWifiManager));
                } else if (this.mPasspointConfigCache.containsKey(uniqueIdToPasspointWifiEntryKey)) {
                    this.mPasspointWifiEntryCache.put(uniqueIdToPasspointWifiEntryKey, new PasspointWifiEntry(this.mInjector, this.mContext, this.mMainHandler, (PasspointConfiguration) this.mPasspointConfigCache.get(uniqueIdToPasspointWifiEntryKey), this.mWifiManager, false));
                }
            }
            ((PasspointWifiEntry) this.mPasspointWifiEntryCache.get(uniqueIdToPasspointWifiEntryKey)).updateScanResultInfo(wifiConfiguration, list3, list4);
        }
        this.mPasspointWifiEntryCache.entrySet().removeIf(new WifiPickerTracker$$ExternalSyntheticLambda13(treeSet, 0));
    }

    public final void updateStandardWifiEntryScans(List<ScanResult> list) {
        Objects.requireNonNull(list, "Scan Result list should not be null!");
        Map map = (Map) list.stream().filter(WifiPickerTracker$$ExternalSyntheticLambda17.INSTANCE).collect(Collectors.groupingBy(WifiPickerTracker$$ExternalSyntheticLambda9.INSTANCE));
        ArraySet arraySet = new ArraySet(map.keySet());
        this.mStandardWifiEntryCache.forEach(new WifiPickerTracker$$ExternalSyntheticLambda2(arraySet, map, 0));
        Iterator it = arraySet.iterator();
        while (it.hasNext()) {
            StandardWifiEntry.ScanResultKey scanResultKey = (StandardWifiEntry.ScanResultKey) it.next();
            StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey = new StandardWifiEntry.StandardWifiEntryKey(scanResultKey);
            this.mStandardWifiEntryCache.add(new StandardWifiEntry(this.mInjector, this.mContext, this.mMainHandler, standardWifiEntryKey, (List) this.mStandardWifiConfigCache.get(standardWifiEntryKey), (List) map.get(scanResultKey), this.mWifiManager, false));
        }
        this.mStandardWifiEntryCache.removeIf(WifiPickerTracker$$ExternalSyntheticLambda25.INSTANCE);
    }

    public final void updateSuggestedWifiEntryScans(List<ScanResult> list) {
        Objects.requireNonNull(list, "Scan Result list should not be null!");
        Set set = (Set) this.mWifiManager.getWifiConfigForMatchedNetworkSuggestionsSharedWithUser(list).stream().map(WifiPickerTracker$$ExternalSyntheticLambda10.INSTANCE).collect(Collectors.toSet());
        Map map = (Map) list.stream().filter(PeopleSpaceUtils$$ExternalSyntheticLambda8.INSTANCE$2).collect(Collectors.groupingBy(WifiPickerTracker$$ExternalSyntheticLambda7.INSTANCE));
        ArraySet arraySet = new ArraySet();
        this.mSuggestedWifiEntryCache.forEach(new WifiPickerTracker$$ExternalSyntheticLambda3(arraySet, map, set));
        for (StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey : this.mSuggestedConfigCache.keySet()) {
            Objects.requireNonNull(standardWifiEntryKey);
            StandardWifiEntry.ScanResultKey scanResultKey = standardWifiEntryKey.mScanResultKey;
            if (!arraySet.contains(standardWifiEntryKey) && map.containsKey(scanResultKey)) {
                StandardWifiEntry standardWifiEntry = new StandardWifiEntry(this.mInjector, this.mContext, this.mMainHandler, standardWifiEntryKey, (List) this.mSuggestedConfigCache.get(standardWifiEntryKey), (List) map.get(scanResultKey), this.mWifiManager, false);
                boolean contains = set.contains(standardWifiEntryKey);
                synchronized (standardWifiEntry) {
                    standardWifiEntry.mIsUserShareable = contains;
                }
                this.mSuggestedWifiEntryCache.add(standardWifiEntry);
            }
        }
        this.mSuggestedWifiEntryCache.removeIf(WifiPickerTracker$$ExternalSyntheticLambda18.INSTANCE);
    }

    public final void updateWifiConfigurations(List<WifiConfiguration> list) {
        Objects.requireNonNull(list, "Config list should not be null!");
        this.mStandardWifiConfigCache.clear();
        this.mSuggestedConfigCache.clear();
        this.mNetworkRequestConfigCache.clear();
        new ArrayList();
        for (WifiConfiguration next : list) {
            if (!next.carrierMerged) {
                StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey = new StandardWifiEntry.StandardWifiEntryKey(next, true);
                if (next.isPasspoint()) {
                    this.mPasspointWifiConfigCache.put(next.networkId, next);
                } else if (next.fromWifiNetworkSuggestion) {
                    if (!this.mSuggestedConfigCache.containsKey(standardWifiEntryKey)) {
                        this.mSuggestedConfigCache.put(standardWifiEntryKey, new ArrayList());
                    }
                    ((List) this.mSuggestedConfigCache.get(standardWifiEntryKey)).add(next);
                } else if (next.fromWifiNetworkSpecifier) {
                    if (!this.mNetworkRequestConfigCache.containsKey(standardWifiEntryKey)) {
                        this.mNetworkRequestConfigCache.put(standardWifiEntryKey, new ArrayList());
                    }
                    this.mNetworkRequestConfigCache.get(standardWifiEntryKey).add(next);
                } else {
                    if (!this.mStandardWifiConfigCache.containsKey(standardWifiEntryKey)) {
                        this.mStandardWifiConfigCache.put(standardWifiEntryKey, new ArrayList());
                    }
                    ((List) this.mStandardWifiConfigCache.get(standardWifiEntryKey)).add(next);
                }
            }
        }
        this.mStandardWifiConfigCache.values().stream().flatMap(WifiPickerTracker$$ExternalSyntheticLambda4.INSTANCE).filter(WifiPickerTracker$$ExternalSyntheticLambda20.INSTANCE).map(WifiPickerTracker$$ExternalSyntheticLambda8.INSTANCE).distinct().count();
        this.mStandardWifiEntryCache.forEach(new WifiPickerTracker$$ExternalSyntheticLambda0(this, 0));
        this.mSuggestedWifiEntryCache.removeIf(new WifiPickerTracker$$ExternalSyntheticLambda12(this, 0));
        NetworkRequestEntry networkRequestEntry = this.mNetworkRequestEntry;
        if (networkRequestEntry != null) {
            networkRequestEntry.updateConfig(this.mNetworkRequestConfigCache.get(networkRequestEntry.mKey));
        }
    }

    public final void updateWifiEntries() {
        NetworkRequestEntry networkRequestEntry;
        synchronized (this.mLock) {
            WifiEntry wifiEntry = (WifiEntry) this.mStandardWifiEntryCache.stream().filter(WifiPickerTracker$$ExternalSyntheticLambda22.INSTANCE).findAny().orElse((Object) null);
            this.mConnectedWifiEntry = wifiEntry;
            if (wifiEntry == null) {
                this.mConnectedWifiEntry = (WifiEntry) this.mSuggestedWifiEntryCache.stream().filter(WifiPickerTracker$$ExternalSyntheticLambda23.INSTANCE).findAny().orElse((Object) null);
            }
            if (this.mConnectedWifiEntry == null) {
                this.mConnectedWifiEntry = (WifiEntry) this.mPasspointWifiEntryCache.values().stream().filter(WifiPickerTracker$$ExternalSyntheticLambda24.INSTANCE).findAny().orElse((Object) null);
            }
            if (!(this.mConnectedWifiEntry != null || (networkRequestEntry = this.mNetworkRequestEntry) == null || networkRequestEntry.getConnectedState() == 0)) {
                this.mConnectedWifiEntry = this.mNetworkRequestEntry;
            }
            WifiEntry wifiEntry2 = this.mConnectedWifiEntry;
            if (wifiEntry2 != null) {
                wifiEntry2.setIsDefaultNetwork(this.mIsWifiDefaultRoute);
            }
            this.mWifiEntries.clear();
            Set set = (Set) this.mSuggestedWifiEntryCache.stream().filter(new BubbleData$$ExternalSyntheticLambda7(this, 1)).map(WifiPickerTracker$$ExternalSyntheticLambda5.INSTANCE).collect(Collectors.toSet());
            Iterator it = this.mStandardWifiEntryCache.iterator();
            while (it.hasNext()) {
                StandardWifiEntry standardWifiEntry = (StandardWifiEntry) it.next();
                if (standardWifiEntry != this.mConnectedWifiEntry) {
                    if (!standardWifiEntry.isSaved()) {
                        StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey = standardWifiEntry.mKey;
                        Objects.requireNonNull(standardWifiEntryKey);
                        if (set.contains(standardWifiEntryKey.mScanResultKey)) {
                        }
                    }
                    this.mWifiEntries.add(standardWifiEntry);
                }
            }
            this.mWifiEntries.addAll((Collection) this.mSuggestedWifiEntryCache.stream().filter(WifiPickerTracker$$ExternalSyntheticLambda26.INSTANCE).collect(Collectors.toList()));
            this.mWifiEntries.addAll((Collection) this.mPasspointWifiEntryCache.values().stream().filter(WifiPickerTracker$$ExternalSyntheticLambda16.INSTANCE).collect(Collectors.toList()));
            this.mWifiEntries.addAll((Collection) this.mOsuWifiEntryCache.values().stream().filter(WifiPickerTracker$$ExternalSyntheticLambda21.INSTANCE).collect(Collectors.toList()));
            this.mWifiEntries.addAll((Collection) Collections.emptyList().stream().filter(WifiPickerTracker$$ExternalSyntheticLambda27.INSTANCE).collect(Collectors.toList()));
            Collections.sort(this.mWifiEntries);
            if (BaseWifiTracker.sVerboseLogging) {
                Log.v("WifiPickerTracker", "Connected WifiEntry: " + this.mConnectedWifiEntry);
                Log.v("WifiPickerTracker", "Updated WifiEntries: " + Arrays.toString(this.mWifiEntries.toArray()));
            }
        }
        WifiPickerTrackerCallback wifiPickerTrackerCallback = this.mListener;
        if (wifiPickerTrackerCallback != null) {
            this.mMainHandler.post(new PowerUI$$ExternalSyntheticLambda0(wifiPickerTrackerCallback, 5));
        }
    }
}
