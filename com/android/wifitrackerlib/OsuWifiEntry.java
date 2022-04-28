package com.android.wifitrackerlib;

import android.content.Context;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.hotspot2.OsuProvider;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.net.wifi.hotspot2.ProvisioningCallback;
import android.os.Build;
import android.os.Handler;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Pair;
import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline1;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.p006qs.tiles.dialog.InternetDialogController;
import com.android.systemui.plugins.FalsingManager;
import com.android.wifitrackerlib.WifiEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class OsuWifiEntry extends WifiEntry {
    public final Context mContext;
    public final ArrayList mCurrentScanResults = new ArrayList();
    public boolean mHasAddConfigUserRestriction = false;
    public boolean mIsAlreadyProvisioned = false;
    public final String mKey;
    public final OsuProvider mOsuProvider;
    public String mOsuStatusString;
    public String mSsid;

    public class OsuWifiEntryProvisioningCallback extends ProvisioningCallback {
        public final void onProvisioningStatus(int i) {
            String str;
            boolean z = false;
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case FalsingManager.VERSION:
                case 7:
                    str = String.format(OsuWifiEntry.this.mContext.getString(C1777R.string.wifitrackerlib_osu_opening_provider), new Object[]{OsuWifiEntry.this.getTitle()});
                    break;
                case 8:
                case 9:
                case 10:
                case QSTileImpl.C1034H.STALE:
                    str = OsuWifiEntry.this.mContext.getString(C1777R.string.wifitrackerlib_osu_completing_sign_up);
                    break;
                default:
                    str = null;
                    break;
            }
            synchronized (OsuWifiEntry.this) {
                if (!TextUtils.equals(OsuWifiEntry.this.mOsuStatusString, str)) {
                    z = true;
                }
                OsuWifiEntry osuWifiEntry = OsuWifiEntry.this;
                osuWifiEntry.mOsuStatusString = str;
                if (z) {
                    osuWifiEntry.notifyOnUpdated();
                }
            }
        }

        public OsuWifiEntryProvisioningCallback() {
        }

        public final void onProvisioningComplete() {
            ScanResult scanResult;
            synchronized (OsuWifiEntry.this) {
                OsuWifiEntry osuWifiEntry = OsuWifiEntry.this;
                osuWifiEntry.mOsuStatusString = osuWifiEntry.mContext.getString(C1777R.string.wifitrackerlib_osu_sign_up_complete);
            }
            OsuWifiEntry.this.notifyOnUpdated();
            OsuWifiEntry osuWifiEntry2 = OsuWifiEntry.this;
            PasspointConfiguration passpointConfiguration = (PasspointConfiguration) osuWifiEntry2.mWifiManager.getMatchingPasspointConfigsForOsuProviders(Collections.singleton(osuWifiEntry2.mOsuProvider)).get(OsuWifiEntry.this.mOsuProvider);
            WifiEntry.ConnectCallback connectCallback = OsuWifiEntry.this.mConnectCallback;
            if (passpointConfiguration != null) {
                String uniqueId = passpointConfiguration.getUniqueId();
                WifiManager wifiManager = OsuWifiEntry.this.mWifiManager;
                Iterator it = wifiManager.getAllMatchingWifiConfigs(wifiManager.getScanResults()).iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Pair pair = (Pair) it.next();
                    WifiConfiguration wifiConfiguration = (WifiConfiguration) pair.first;
                    if (TextUtils.equals(wifiConfiguration.getKey(), uniqueId)) {
                        List list = (List) ((Map) pair.second).get(0);
                        List list2 = (List) ((Map) pair.second).get(1);
                        if (list != null && !list.isEmpty()) {
                            scanResult = Utils.getBestScanResultByLevel(list);
                        } else if (list2 != null && !list2.isEmpty()) {
                            scanResult = Utils.getBestScanResultByLevel(list2);
                        }
                        wifiConfiguration.SSID = MotionController$$ExternalSyntheticOutline1.m8m(VendorAtomValue$$ExternalSyntheticOutline1.m1m("\""), scanResult.SSID, "\"");
                        OsuWifiEntry.this.mWifiManager.connect(wifiConfiguration, (WifiManager.ActionListener) null);
                        return;
                    }
                }
                if (connectCallback != null) {
                    ((InternetDialogController.WifiEntryConnectCallback) connectCallback).onConnectResult(2);
                }
            } else if (connectCallback != null) {
                ((InternetDialogController.WifiEntryConnectCallback) connectCallback).onConnectResult(2);
            }
        }

        public final void onProvisioningFailure(int i) {
            synchronized (OsuWifiEntry.this) {
                OsuWifiEntry osuWifiEntry = OsuWifiEntry.this;
                if (TextUtils.equals(osuWifiEntry.mOsuStatusString, osuWifiEntry.mContext.getString(C1777R.string.wifitrackerlib_osu_completing_sign_up))) {
                    OsuWifiEntry osuWifiEntry2 = OsuWifiEntry.this;
                    osuWifiEntry2.mOsuStatusString = osuWifiEntry2.mContext.getString(C1777R.string.wifitrackerlib_osu_sign_up_failed);
                } else {
                    OsuWifiEntry osuWifiEntry3 = OsuWifiEntry.this;
                    osuWifiEntry3.mOsuStatusString = osuWifiEntry3.mContext.getString(C1777R.string.wifitrackerlib_osu_connect_failed);
                }
            }
            WifiEntry.ConnectCallback connectCallback = OsuWifiEntry.this.mConnectCallback;
            if (connectCallback != null) {
                ((InternetDialogController.WifiEntryConnectCallback) connectCallback).onConnectResult(2);
            }
            OsuWifiEntry.this.notifyOnUpdated();
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public OsuWifiEntry(WifiTrackerInjector wifiTrackerInjector, Context context, Handler handler, OsuProvider osuProvider, WifiManager wifiManager) throws IllegalArgumentException {
        super(handler, wifiManager, false);
        boolean z = false;
        Objects.requireNonNull(osuProvider, "Cannot construct with null osuProvider!");
        this.mContext = context;
        this.mOsuProvider = osuProvider;
        this.mKey = osuProviderToOsuWifiEntryKey(osuProvider);
        Objects.requireNonNull(wifiTrackerInjector);
        UserManager userManager = wifiTrackerInjector.mUserManager;
        String str = Build.VERSION.CODENAME;
        if (!"REL".equals(str) && str.compareTo("T") >= 0) {
            z = true;
        }
        if (z && userManager != null) {
            this.mHasAddConfigUserRestriction = userManager.hasUserRestriction("no_add_wifi_config");
        }
    }

    public final synchronized void connect(InternetDialogController.WifiEntryConnectCallback wifiEntryConnectCallback) {
        this.mConnectCallback = wifiEntryConnectCallback;
        this.mWifiManager.stopRestrictingAutoJoinToSubscriptionId();
        this.mWifiManager.startSubscriptionProvisioning(this.mOsuProvider, this.mContext.getMainExecutor(), new OsuWifiEntryProvisioningCallback());
    }

    public final String getScanResultDescription() {
        return "";
    }

    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
        	at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
        	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
        	at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:248)
        	at java.base/java.util.Objects.checkIndex(Objects.java:372)
        	at java.base/java.util.ArrayList.get(ArrayList.java:458)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:598)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    public final synchronized java.lang.String getSummary(boolean r2) {
        /*
            r1 = this;
            monitor-enter(r1)
            boolean r0 = r1.mHasAddConfigUserRestriction     // Catch:{ all -> 0x004a }
            if (r0 == 0) goto L_0x000b
            boolean r0 = r1.mIsAlreadyProvisioned     // Catch:{ all -> 0x004a }
            if (r0 != 0) goto L_0x000b
            r0 = 1
            goto L_0x000c
        L_0x000b:
            r0 = 0
        L_0x000c:
            if (r0 == 0) goto L_0x0019
            android.content.Context r2 = r1.mContext     // Catch:{ all -> 0x004a }
            r0 = 2131953612(0x7f1307cc, float:1.95437E38)
            java.lang.String r2 = r2.getString(r0)     // Catch:{ all -> 0x004a }
            monitor-exit(r1)
            return r2
        L_0x0019:
            java.lang.String r0 = r1.mOsuStatusString     // Catch:{ all -> 0x004a }
            if (r0 == 0) goto L_0x001f
            monitor-exit(r1)
            return r0
        L_0x001f:
            monitor-enter(r1)     // Catch:{ all -> 0x004a }
            boolean r0 = r1.mIsAlreadyProvisioned     // Catch:{ all -> 0x0047 }
            monitor-exit(r1)     // Catch:{ all -> 0x004a }
            if (r0 == 0) goto L_0x003c
            if (r2 == 0) goto L_0x0031
            android.content.Context r2 = r1.mContext     // Catch:{ all -> 0x004a }
            r0 = 2131953646(0x7f1307ee, float:1.9543769E38)
            java.lang.String r2 = r2.getString(r0)     // Catch:{ all -> 0x004a }
            goto L_0x003a
        L_0x0031:
            android.content.Context r2 = r1.mContext     // Catch:{ all -> 0x004a }
            r0 = 2131953629(0x7f1307dd, float:1.9543734E38)
            java.lang.String r2 = r2.getString(r0)     // Catch:{ all -> 0x004a }
        L_0x003a:
            monitor-exit(r1)
            return r2
        L_0x003c:
            android.content.Context r2 = r1.mContext     // Catch:{ all -> 0x004a }
            r0 = 2131953630(0x7f1307de, float:1.9543736E38)
            java.lang.String r2 = r2.getString(r0)     // Catch:{ all -> 0x004a }
            monitor-exit(r1)
            return r2
        L_0x0047:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x004a }
            throw r2     // Catch:{ all -> 0x004a }
        L_0x004a:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wifitrackerlib.OsuWifiEntry.getSummary(boolean):java.lang.String");
    }

    public final synchronized String getTitle() {
        String friendlyName = this.mOsuProvider.getFriendlyName();
        if (!TextUtils.isEmpty(friendlyName)) {
            return friendlyName;
        }
        if (!TextUtils.isEmpty(this.mSsid)) {
            return this.mSsid;
        }
        Uri serverUri = this.mOsuProvider.getServerUri();
        if (serverUri == null) {
            return "";
        }
        return serverUri.toString();
    }

    public final synchronized void updateScanResultInfo(List<ScanResult> list) throws IllegalArgumentException {
        if (list == null) {
            list = new ArrayList<>();
        }
        this.mCurrentScanResults.clear();
        this.mCurrentScanResults.addAll(list);
        ScanResult bestScanResultByLevel = Utils.getBestScanResultByLevel(list);
        if (bestScanResultByLevel != null) {
            this.mSsid = bestScanResultByLevel.SSID;
            if (getConnectedState() == 0) {
                this.mLevel = this.mWifiManager.calculateSignalLevel(bestScanResultByLevel.level);
            }
        } else {
            this.mLevel = -1;
        }
        notifyOnUpdated();
    }

    public static String osuProviderToOsuWifiEntryKey(OsuProvider osuProvider) {
        Objects.requireNonNull(osuProvider, "Cannot create key with null OsuProvider!");
        return "OsuWifiEntry:" + osuProvider.getFriendlyName() + "," + osuProvider.getServerUri().toString();
    }

    public final boolean connectionInfoMatches(WifiInfo wifiInfo) {
        if (!wifiInfo.isOsuAp() || !TextUtils.equals(wifiInfo.getPasspointProviderFriendlyName(), this.mOsuProvider.getFriendlyName())) {
            return false;
        }
        return true;
    }

    public final String getKey() {
        return this.mKey;
    }
}
