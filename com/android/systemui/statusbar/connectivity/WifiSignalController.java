package com.android.systemui.statusbar.connectivity;

import android.content.Context;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.net.ConnectivityManager;
import android.net.NetworkScoreManager;
import android.net.wifi.WifiManager;
import com.android.internal.annotations.VisibleForTesting;
import com.android.p012wm.shell.pip.PipTaskOrganizer$$ExternalSyntheticLambda3;
import com.android.settingslib.SignalIcon$IconGroup;
import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.settingslib.graph.SignalDrawable;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.settingslib.wifi.WifiStatusTracker;
import java.io.PrintWriter;
import java.util.Objects;

public final class WifiSignalController extends SignalController<WifiState, SignalIcon$IconGroup> {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SignalIcon$MobileIconGroup mCarrierMergedWifiIconGroup = TelephonyIcons.CARRIER_MERGED_WIFI;
    public final boolean mHasMobileDataFeature;
    public final SignalIcon$IconGroup mUnmergedWifiIconGroup;
    public final WifiManager mWifiManager;
    public final WifiStatusTracker mWifiTracker;

    public class WifiTrafficStateCallback implements WifiManager.TrafficStateCallback {
        public WifiTrafficStateCallback() {
        }

        public final void onStateChanged(int i) {
            WifiSignalController.this.setActivity(i);
        }
    }

    public WifiSignalController(Context context, boolean z, CallbackHandler callbackHandler, NetworkControllerImpl networkControllerImpl, WifiManager wifiManager, ConnectivityManager connectivityManager, NetworkScoreManager networkScoreManager) {
        super("WifiSignalController", context, 1, callbackHandler, networkControllerImpl);
        SignalIcon$IconGroup signalIcon$IconGroup = WifiIcons.UNMERGED_WIFI;
        this.mUnmergedWifiIconGroup = signalIcon$IconGroup;
        this.mWifiManager = wifiManager;
        WifiStatusTracker wifiStatusTracker = new WifiStatusTracker(this.mContext, wifiManager, networkScoreManager, connectivityManager, new PipTaskOrganizer$$ExternalSyntheticLambda3(this, 2));
        this.mWifiTracker = wifiStatusTracker;
        wifiStatusTracker.mNetworkScoreManager.registerNetworkScoreCache(1, wifiStatusTracker.mWifiNetworkScoreCache, 1);
        wifiStatusTracker.mWifiNetworkScoreCache.registerListener(wifiStatusTracker.mCacheListener);
        wifiStatusTracker.mConnectivityManager.registerNetworkCallback(wifiStatusTracker.mNetworkRequest, wifiStatusTracker.mNetworkCallback, wifiStatusTracker.mHandler);
        wifiStatusTracker.mConnectivityManager.registerDefaultNetworkCallback(wifiStatusTracker.mDefaultNetworkCallback, wifiStatusTracker.mHandler);
        this.mHasMobileDataFeature = z;
        if (wifiManager != null) {
            wifiManager.registerTrafficStateCallback(context.getMainExecutor(), new WifiTrafficStateCallback());
        }
        ((WifiState) this.mLastState).iconGroup = signalIcon$IconGroup;
        ((WifiState) this.mCurrentState).iconGroup = signalIcon$IconGroup;
    }

    public final ConnectivityState cleanState() {
        return new WifiState();
    }

    public final void copyWifiStates() {
        SignalIcon$IconGroup signalIcon$IconGroup;
        WifiState wifiState = (WifiState) this.mCurrentState;
        WifiStatusTracker wifiStatusTracker = this.mWifiTracker;
        wifiState.enabled = wifiStatusTracker.enabled;
        wifiState.isDefault = wifiStatusTracker.isDefaultNetwork;
        wifiState.connected = wifiStatusTracker.connected;
        wifiState.ssid = wifiStatusTracker.ssid;
        wifiState.rssi = wifiStatusTracker.rssi;
        int i = wifiStatusTracker.level;
        if (i != wifiState.level) {
            NetworkControllerImpl networkControllerImpl = this.mNetworkController;
            Objects.requireNonNull(networkControllerImpl);
            for (int i2 = 0; i2 < networkControllerImpl.mMobileSignalControllers.size(); i2++) {
                MobileSignalController valueAt = networkControllerImpl.mMobileSignalControllers.valueAt(i2);
                Objects.requireNonNull(valueAt);
                if (valueAt.mProviderModelBehavior) {
                    valueAt.mLastWlanLevel = i;
                    if (valueAt.mImsType == 2) {
                        valueAt.notifyCallStateChange(new IconState(true, MobileSignalController.getCallStrengthIcon(i, true), valueAt.getCallStrengthDescription(i, true)), valueAt.mSubscriptionInfo.getSubscriptionId());
                    }
                }
            }
        }
        WifiState wifiState2 = (WifiState) this.mCurrentState;
        WifiStatusTracker wifiStatusTracker2 = this.mWifiTracker;
        wifiState2.level = wifiStatusTracker2.level;
        wifiState2.statusLabel = wifiStatusTracker2.statusLabel;
        boolean z = wifiStatusTracker2.isCarrierMerged;
        wifiState2.isCarrierMerged = z;
        wifiState2.subId = wifiStatusTracker2.subId;
        if (z) {
            signalIcon$IconGroup = this.mCarrierMergedWifiIconGroup;
        } else {
            signalIcon$IconGroup = this.mUnmergedWifiIconGroup;
        }
        wifiState2.iconGroup = signalIcon$IconGroup;
    }

    public final int getCurrentIconIdForCarrierWifi() {
        int i = ((WifiState) this.mCurrentState).level;
        boolean z = true;
        int maxSignalLevel = this.mWifiManager.getMaxSignalLevel() + 1;
        WifiState wifiState = (WifiState) this.mCurrentState;
        int i2 = 0;
        if (wifiState.inetCondition != 0) {
            z = false;
        }
        if (wifiState.connected) {
            int i3 = SignalDrawable.$r8$clinit;
            if (z) {
                i2 = 2;
            }
            return (i2 << 16) | (maxSignalLevel << 8) | i;
        } else if (!wifiState.enabled) {
            return 0;
        } else {
            int i4 = SignalDrawable.$r8$clinit;
            return 131072 | (maxSignalLevel << 8) | 0;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:58:0x014b, code lost:
        if (r2.mConnectedTransports.get(3) == false) goto L_0x0150;
     */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0193  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x0195  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x019c  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x019e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void notifyListeners(com.android.systemui.statusbar.connectivity.SignalCallback r23) {
        /*
            r22 = this;
            r0 = r22
            r1 = r23
            T r2 = r0.mCurrentState
            com.android.systemui.statusbar.connectivity.WifiState r2 = (com.android.systemui.statusbar.connectivity.WifiState) r2
            boolean r3 = r2.isCarrierMerged
            r4 = 2131952235(0x7f13026b, float:1.9540907E38)
            r5 = 0
            r6 = 0
            r7 = 1
            if (r3 == 0) goto L_0x00c1
            boolean r2 = r2.isDefault
            if (r2 != 0) goto L_0x0020
            com.android.systemui.statusbar.connectivity.NetworkControllerImpl r2 = r0.mNetworkController
            java.util.Objects.requireNonNull(r2)
            boolean r2 = r2.mAirplaneMode
            r2 = r2 ^ r7
            if (r2 != 0) goto L_0x01ae
        L_0x0020:
            com.android.settingslib.SignalIcon$MobileIconGroup r2 = r0.mCarrierMergedWifiIconGroup
            int r3 = r22.getContentDescription()
            java.lang.CharSequence r3 = r0.getTextIfExists(r3)
            java.lang.String r3 = r3.toString()
            int r8 = r2.dataContentDescription
            java.lang.CharSequence r17 = r0.getTextIfExists(r8)
            java.lang.String r8 = r17.toString()
            android.text.Spanned r8 = android.text.Html.fromHtml(r8, r6)
            java.lang.String r8 = r8.toString()
            T r9 = r0.mCurrentState
            com.android.systemui.statusbar.connectivity.WifiState r9 = (com.android.systemui.statusbar.connectivity.WifiState) r9
            int r9 = r9.inetCondition
            if (r9 != 0) goto L_0x0051
            android.content.Context r8 = r0.mContext
            java.lang.String r4 = r8.getString(r4)
            r16 = r4
            goto L_0x0053
        L_0x0051:
            r16 = r8
        L_0x0053:
            T r4 = r0.mCurrentState
            com.android.systemui.statusbar.connectivity.WifiState r4 = (com.android.systemui.statusbar.connectivity.WifiState) r4
            boolean r8 = r4.enabled
            if (r8 == 0) goto L_0x0064
            boolean r8 = r4.connected
            if (r8 == 0) goto L_0x0064
            boolean r4 = r4.isDefault
            if (r4 == 0) goto L_0x0064
            goto L_0x0065
        L_0x0064:
            r7 = r6
        L_0x0065:
            com.android.systemui.statusbar.connectivity.IconState r10 = new com.android.systemui.statusbar.connectivity.IconState
            int r4 = r22.getCurrentIconIdForCarrierWifi()
            r10.<init>(r7, r4, r3)
            if (r7 == 0) goto L_0x0074
            int r4 = r2.dataType
            r12 = r4
            goto L_0x0075
        L_0x0074:
            r12 = r6
        L_0x0075:
            if (r7 == 0) goto L_0x0088
            int r6 = r2.dataType
            com.android.systemui.statusbar.connectivity.IconState r5 = new com.android.systemui.statusbar.connectivity.IconState
            T r2 = r0.mCurrentState
            com.android.systemui.statusbar.connectivity.WifiState r2 = (com.android.systemui.statusbar.connectivity.WifiState) r2
            boolean r2 = r2.connected
            int r4 = r22.getCurrentIconIdForCarrierWifi()
            r5.<init>(r2, r4, r3)
        L_0x0088:
            r11 = r5
            r13 = r6
            com.android.systemui.statusbar.connectivity.NetworkControllerImpl r2 = r0.mNetworkController
            T r3 = r0.mCurrentState
            com.android.systemui.statusbar.connectivity.WifiState r3 = (com.android.systemui.statusbar.connectivity.WifiState) r3
            int r3 = r3.subId
            java.util.Objects.requireNonNull(r2)
            com.android.systemui.statusbar.connectivity.MobileSignalController r2 = r2.getControllerWithSubId(r3)
            if (r2 == 0) goto L_0x00a2
            android.telephony.TelephonyManager r2 = r2.mPhone
            java.lang.String r2 = r2.getSimOperatorName()
            goto L_0x00a4
        L_0x00a2:
            java.lang.String r2 = ""
        L_0x00a4:
            r18 = r2
            com.android.systemui.statusbar.connectivity.MobileDataIndicators r2 = new com.android.systemui.statusbar.connectivity.MobileDataIndicators
            T r0 = r0.mCurrentState
            com.android.systemui.statusbar.connectivity.WifiState r0 = (com.android.systemui.statusbar.connectivity.WifiState) r0
            boolean r14 = r0.activityIn
            boolean r15 = r0.activityOut
            int r0 = r0.subId
            r20 = 0
            r21 = 1
            r9 = r2
            r19 = r0
            r9.<init>(r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21)
            r1.setMobileDataIndicators(r2)
            goto L_0x01ae
        L_0x00c1:
            android.content.Context r2 = r0.mContext
            android.content.res.Resources r2 = r2.getResources()
            r3 = 2131034170(0x7f05003a, float:1.767885E38)
            boolean r2 = r2.getBoolean(r3)
            T r3 = r0.mCurrentState
            com.android.systemui.statusbar.connectivity.WifiState r3 = (com.android.systemui.statusbar.connectivity.WifiState) r3
            boolean r8 = r3.enabled
            if (r8 == 0) goto L_0x00ea
            boolean r8 = r3.connected
            if (r8 == 0) goto L_0x00de
            int r8 = r3.inetCondition
            if (r8 == r7) goto L_0x00e8
        L_0x00de:
            boolean r8 = r0.mHasMobileDataFeature
            if (r8 == 0) goto L_0x00e8
            boolean r8 = r3.isDefault
            if (r8 != 0) goto L_0x00e8
            if (r2 == 0) goto L_0x00ea
        L_0x00e8:
            r2 = r7
            goto L_0x00eb
        L_0x00ea:
            r2 = r6
        L_0x00eb:
            boolean r8 = r3.connected
            if (r8 == 0) goto L_0x00f3
            java.lang.String r8 = r3.ssid
            r15 = r8
            goto L_0x00f4
        L_0x00f3:
            r15 = r5
        L_0x00f4:
            if (r2 == 0) goto L_0x00fc
            java.lang.String r3 = r3.ssid
            if (r3 == 0) goto L_0x00fc
            r3 = r7
            goto L_0x00fd
        L_0x00fc:
            r3 = r6
        L_0x00fd:
            int r8 = r22.getContentDescription()
            java.lang.CharSequence r8 = r0.getTextIfExists(r8)
            java.lang.String r8 = r8.toString()
            T r9 = r0.mCurrentState
            com.android.systemui.statusbar.connectivity.WifiState r9 = (com.android.systemui.statusbar.connectivity.WifiState) r9
            int r9 = r9.inetCondition
            if (r9 != 0) goto L_0x0124
            java.lang.String r9 = ","
            java.lang.StringBuilder r8 = android.hidl.base.V1_0.DebugInfo$$ExternalSyntheticOutline0.m2m(r8, r9)
            android.content.Context r9 = r0.mContext
            java.lang.String r4 = r9.getString(r4)
            r8.append(r4)
            java.lang.String r8 = r8.toString()
        L_0x0124:
            com.android.systemui.statusbar.connectivity.IconState r11 = new com.android.systemui.statusbar.connectivity.IconState
            int r4 = r22.getCurrentIconId()
            r11.<init>(r2, r4, r8)
            T r2 = r0.mCurrentState
            com.android.systemui.statusbar.connectivity.WifiState r2 = (com.android.systemui.statusbar.connectivity.WifiState) r2
            boolean r2 = r2.isDefault
            if (r2 != 0) goto L_0x0150
            com.android.systemui.statusbar.connectivity.NetworkControllerImpl r2 = r0.mNetworkController
            java.util.Objects.requireNonNull(r2)
            boolean r2 = r2.mAirplaneMode
            r2 = r2 ^ r7
            if (r2 != 0) goto L_0x014e
            com.android.systemui.statusbar.connectivity.NetworkControllerImpl r2 = r0.mNetworkController
            java.util.Objects.requireNonNull(r2)
            java.util.BitSet r2 = r2.mConnectedTransports
            r4 = 3
            boolean r2 = r2.get(r4)
            if (r2 != 0) goto L_0x014e
            goto L_0x0150
        L_0x014e:
            r12 = r5
            goto L_0x0185
        L_0x0150:
            com.android.systemui.statusbar.connectivity.IconState r5 = new com.android.systemui.statusbar.connectivity.IconState
            T r2 = r0.mCurrentState
            r4 = r2
            com.android.systemui.statusbar.connectivity.WifiState r4 = (com.android.systemui.statusbar.connectivity.WifiState) r4
            boolean r4 = r4.connected
            com.android.settingslib.wifi.WifiStatusTracker r9 = r0.mWifiTracker
            boolean r9 = r9.isCaptivePortal
            if (r9 == 0) goto L_0x0163
            r2 = 2131232237(0x7f0805ed, float:1.8080578E38)
            goto L_0x0181
        L_0x0163:
            boolean r9 = r2.connected
            if (r9 == 0) goto L_0x0174
            com.android.settingslib.SignalIcon$IconGroup r9 = r2.iconGroup
            int[][] r9 = r9.qsIcons
            int r10 = r2.inetCondition
            r9 = r9[r10]
            int r2 = r2.level
            r2 = r9[r2]
            goto L_0x0181
        L_0x0174:
            boolean r9 = r2.enabled
            if (r9 == 0) goto L_0x017d
            com.android.settingslib.SignalIcon$IconGroup r2 = r2.iconGroup
            int r2 = r2.qsDiscState
            goto L_0x0181
        L_0x017d:
            com.android.settingslib.SignalIcon$IconGroup r2 = r2.iconGroup
            int r2 = r2.qsNullState
        L_0x0181:
            r5.<init>(r4, r2, r8)
            goto L_0x014e
        L_0x0185:
            com.android.systemui.statusbar.connectivity.WifiIndicators r2 = new com.android.systemui.statusbar.connectivity.WifiIndicators
            T r0 = r0.mCurrentState
            com.android.systemui.statusbar.connectivity.WifiState r0 = (com.android.systemui.statusbar.connectivity.WifiState) r0
            boolean r10 = r0.enabled
            if (r3 == 0) goto L_0x0195
            boolean r4 = r0.activityIn
            if (r4 == 0) goto L_0x0195
            r13 = r7
            goto L_0x0196
        L_0x0195:
            r13 = r6
        L_0x0196:
            if (r3 == 0) goto L_0x019e
            boolean r3 = r0.activityOut
            if (r3 == 0) goto L_0x019e
            r14 = r7
            goto L_0x019f
        L_0x019e:
            r14 = r6
        L_0x019f:
            boolean r3 = r0.isTransient
            java.lang.String r0 = r0.statusLabel
            r9 = r2
            r16 = r3
            r17 = r0
            r9.<init>(r10, r11, r12, r13, r14, r15, r16, r17)
            r1.setWifiIndicators(r2)
        L_0x01ae:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.connectivity.WifiSignalController.notifyListeners(com.android.systemui.statusbar.connectivity.SignalCallback):void");
    }

    @VisibleForTesting
    public void setActivity(int i) {
        boolean z;
        T t = this.mCurrentState;
        WifiState wifiState = (WifiState) t;
        boolean z2 = false;
        if (i == 3 || i == 1) {
            z = true;
        } else {
            z = false;
        }
        wifiState.activityIn = z;
        WifiState wifiState2 = (WifiState) t;
        if (i == 3 || i == 2) {
            z2 = true;
        }
        wifiState2.activityOut = z2;
        notifyListenersIfNecessary();
    }

    public final void dump(PrintWriter printWriter) {
        super.dump(printWriter);
        WifiStatusTracker wifiStatusTracker = this.mWifiTracker;
        Objects.requireNonNull(wifiStatusTracker);
        printWriter.println("  - WiFi Network History ------");
        int i = 0;
        for (int i2 = 0; i2 < 32; i2++) {
            if (wifiStatusTracker.mHistory[i2] != null) {
                i++;
            }
        }
        int i3 = wifiStatusTracker.mHistoryIndex + 32;
        while (true) {
            i3--;
            if (i3 >= (wifiStatusTracker.mHistoryIndex + 32) - i) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("  Previous WiFiNetwork(");
                m.append((wifiStatusTracker.mHistoryIndex + 32) - i3);
                m.append("): ");
                m.append(wifiStatusTracker.mHistory[i3 & 31]);
                printWriter.println(m.toString());
            } else {
                return;
            }
        }
    }
}
