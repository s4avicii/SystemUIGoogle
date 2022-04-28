package com.android.systemui.p006qs.tiles;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline2;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardUpdateMonitor$18$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.graph.SignalDrawable;
import com.android.settingslib.net.DataUsageController;
import com.android.systemui.p006qs.AlphaControlledSignalTileView;
import com.android.systemui.p006qs.QSHost;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.p006qs.tiles.dialog.InternetDialogFactory;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.QSIconView;
import com.android.systemui.plugins.p005qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.connectivity.AccessPointController;
import com.android.systemui.statusbar.connectivity.IconState;
import com.android.systemui.statusbar.connectivity.MobileDataIndicators;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.SignalCallback;
import com.android.systemui.statusbar.connectivity.WifiIndicators;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tiles.InternetTile */
public final class InternetTile extends QSTileImpl<QSTile.SignalState> {
    public static final Intent WIFI_SETTINGS = new Intent("android.settings.WIFI_SETTINGS");
    public final AccessPointController mAccessPointController;
    public final NetworkController mController;
    public final DataUsageController mDataController;
    public final Handler mHandler;
    public final InternetDialogFactory mInternetDialogFactory;
    public int mLastTileState = -1;
    public final InternetSignalCallback mSignalCallback;

    /* renamed from: com.android.systemui.qs.tiles.InternetTile$CellularCallbackInfo */
    public static final class CellularCallbackInfo {
        public boolean mActivityIn;
        public boolean mActivityOut;
        public boolean mAirplaneModeEnabled;
        public CharSequence mDataContentDescription;
        public CharSequence mDataSubscriptionName;
        public int mMobileSignalIconId;
        public boolean mMultipleSubs;
        public boolean mNoDefaultNetwork;
        public boolean mNoNetworksAvailable;
        public boolean mNoSim;
        public boolean mNoValidatedNetwork;
        public int mQsTypeIcon;
        public boolean mRoaming;

        public CellularCallbackInfo() {
        }

        public CellularCallbackInfo(int i) {
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("CellularCallbackInfo[");
            sb.append("mAirplaneModeEnabled=");
            sb.append(this.mAirplaneModeEnabled);
            sb.append(",mDataSubscriptionName=");
            sb.append(this.mDataSubscriptionName);
            sb.append(",mDataContentDescription=");
            sb.append(this.mDataContentDescription);
            sb.append(",mMobileSignalIconId=");
            sb.append(this.mMobileSignalIconId);
            sb.append(",mQsTypeIcon=");
            sb.append(this.mQsTypeIcon);
            sb.append(",mActivityIn=");
            sb.append(this.mActivityIn);
            sb.append(",mActivityOut=");
            sb.append(this.mActivityOut);
            sb.append(",mNoSim=");
            sb.append(this.mNoSim);
            sb.append(",mRoaming=");
            sb.append(this.mRoaming);
            sb.append(",mMultipleSubs=");
            sb.append(this.mMultipleSubs);
            sb.append(",mNoDefaultNetwork=");
            sb.append(this.mNoDefaultNetwork);
            sb.append(",mNoValidatedNetwork=");
            sb.append(this.mNoValidatedNetwork);
            sb.append(",mNoNetworksAvailable=");
            return LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0.m21m(sb, this.mNoNetworksAvailable, ']');
        }
    }

    /* renamed from: com.android.systemui.qs.tiles.InternetTile$EthernetCallbackInfo */
    public static final class EthernetCallbackInfo {
        public boolean mConnected;
        public String mEthernetContentDescription;
        public int mEthernetSignalIconId;

        public EthernetCallbackInfo() {
        }

        public EthernetCallbackInfo(int i) {
        }

        public final String toString() {
            return "EthernetCallbackInfo[" + "mConnected=" + this.mConnected + ",mEthernetSignalIconId=" + this.mEthernetSignalIconId + ",mEthernetContentDescription=" + this.mEthernetContentDescription + ']';
        }
    }

    /* renamed from: com.android.systemui.qs.tiles.InternetTile$InternetSignalCallback */
    public final class InternetSignalCallback implements SignalCallback {
        public final CellularCallbackInfo mCellularInfo = new CellularCallbackInfo(0);
        public final EthernetCallbackInfo mEthernetInfo = new EthernetCallbackInfo(0);
        public final WifiCallbackInfo mWifiInfo = new WifiCallbackInfo(0);

        public InternetSignalCallback() {
        }

        public final void setConnectivityStatus(boolean z, boolean z2, boolean z3) {
            if (QSTileImpl.DEBUG) {
                String str = InternetTile.this.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("setConnectivityStatus: noDefaultNetwork = ");
                sb.append(z);
                sb.append(",noValidatedNetwork = ");
                sb.append(z2);
                sb.append(",noNetworksAvailable = ");
                KeyguardUpdateMonitor$18$$ExternalSyntheticOutline0.m29m(sb, z3, str);
            }
            CellularCallbackInfo cellularCallbackInfo = this.mCellularInfo;
            cellularCallbackInfo.mNoDefaultNetwork = z;
            cellularCallbackInfo.mNoValidatedNetwork = z2;
            cellularCallbackInfo.mNoNetworksAvailable = z3;
            WifiCallbackInfo wifiCallbackInfo = this.mWifiInfo;
            wifiCallbackInfo.mNoDefaultNetwork = z;
            wifiCallbackInfo.mNoValidatedNetwork = z2;
            wifiCallbackInfo.mNoNetworksAvailable = z3;
            InternetTile.this.refreshState(wifiCallbackInfo);
        }

        public final void setEthernetIndicators(IconState iconState) {
            String str;
            if (QSTileImpl.DEBUG) {
                String str2 = InternetTile.this.TAG;
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("setEthernetIndicators: icon = ");
                if (iconState == null) {
                    str = "";
                } else {
                    str = iconState.toString();
                }
                ExifInterface$$ExternalSyntheticOutline2.m15m(m, str, str2);
            }
            EthernetCallbackInfo ethernetCallbackInfo = this.mEthernetInfo;
            boolean z = iconState.visible;
            ethernetCallbackInfo.mConnected = z;
            ethernetCallbackInfo.mEthernetSignalIconId = iconState.icon;
            ethernetCallbackInfo.mEthernetContentDescription = iconState.contentDescription;
            if (z) {
                InternetTile.this.refreshState(ethernetCallbackInfo);
            }
        }

        public final void setIsAirplaneMode(IconState iconState) {
            String str;
            if (QSTileImpl.DEBUG) {
                String str2 = InternetTile.this.TAG;
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("setIsAirplaneMode: icon = ");
                if (iconState == null) {
                    str = "";
                } else {
                    str = iconState.toString();
                }
                ExifInterface$$ExternalSyntheticOutline2.m15m(m, str, str2);
            }
            CellularCallbackInfo cellularCallbackInfo = this.mCellularInfo;
            boolean z = cellularCallbackInfo.mAirplaneModeEnabled;
            boolean z2 = iconState.visible;
            if (z != z2) {
                cellularCallbackInfo.mAirplaneModeEnabled = z2;
                WifiCallbackInfo wifiCallbackInfo = this.mWifiInfo;
                wifiCallbackInfo.mAirplaneModeEnabled = z2;
                InternetTile internetTile = InternetTile.this;
                if (internetTile.mSignalCallback.mEthernetInfo.mConnected) {
                    return;
                }
                if (!wifiCallbackInfo.mEnabled || wifiCallbackInfo.mWifiSignalIconId <= 0 || wifiCallbackInfo.mSsid == null) {
                    internetTile.refreshState(cellularCallbackInfo);
                } else {
                    internetTile.refreshState(wifiCallbackInfo);
                }
            }
        }

        public final void setMobileDataIndicators(MobileDataIndicators mobileDataIndicators) {
            CharSequence charSequence;
            if (QSTileImpl.DEBUG) {
                String str = InternetTile.this.TAG;
                Log.d(str, "setMobileDataIndicators: " + mobileDataIndicators);
            }
            if (mobileDataIndicators.qsIcon != null) {
                CellularCallbackInfo cellularCallbackInfo = this.mCellularInfo;
                CharSequence charSequence2 = mobileDataIndicators.qsDescription;
                if (charSequence2 == null) {
                    charSequence2 = InternetTile.this.mController.getMobileDataNetworkName();
                }
                cellularCallbackInfo.mDataSubscriptionName = charSequence2;
                CellularCallbackInfo cellularCallbackInfo2 = this.mCellularInfo;
                if (mobileDataIndicators.qsDescription != null) {
                    charSequence = mobileDataIndicators.typeContentDescriptionHtml;
                } else {
                    charSequence = null;
                }
                cellularCallbackInfo2.mDataContentDescription = charSequence;
                cellularCallbackInfo2.mMobileSignalIconId = mobileDataIndicators.qsIcon.icon;
                cellularCallbackInfo2.mQsTypeIcon = mobileDataIndicators.qsType;
                cellularCallbackInfo2.mActivityIn = mobileDataIndicators.activityIn;
                cellularCallbackInfo2.mActivityOut = mobileDataIndicators.activityOut;
                cellularCallbackInfo2.mRoaming = mobileDataIndicators.roaming;
                boolean z = true;
                if (InternetTile.this.mController.getNumberSubscriptions() <= 1) {
                    z = false;
                }
                cellularCallbackInfo2.mMultipleSubs = z;
                InternetTile.this.refreshState(this.mCellularInfo);
            }
        }

        public final void setNoSims(boolean z, boolean z2) {
            if (QSTileImpl.DEBUG) {
                String str = InternetTile.this.TAG;
                Log.d(str, "setNoSims: show = " + z + ",simDetected = " + z2);
            }
            CellularCallbackInfo cellularCallbackInfo = this.mCellularInfo;
            cellularCallbackInfo.mNoSim = z;
            if (z) {
                cellularCallbackInfo.mMobileSignalIconId = 0;
                cellularCallbackInfo.mQsTypeIcon = 0;
            }
        }

        public final void setWifiIndicators(WifiIndicators wifiIndicators) {
            if (QSTileImpl.DEBUG) {
                String str = InternetTile.this.TAG;
                Log.d(str, "setWifiIndicators: " + wifiIndicators);
            }
            WifiCallbackInfo wifiCallbackInfo = this.mWifiInfo;
            boolean z = wifiIndicators.enabled;
            wifiCallbackInfo.mEnabled = z;
            IconState iconState = wifiIndicators.qsIcon;
            if (iconState != null) {
                wifiCallbackInfo.mConnected = iconState.visible;
                wifiCallbackInfo.mWifiSignalIconId = iconState.icon;
                wifiCallbackInfo.mWifiSignalContentDescription = iconState.contentDescription;
                wifiCallbackInfo.mEnabled = z;
                wifiCallbackInfo.mSsid = wifiIndicators.description;
                wifiCallbackInfo.mActivityIn = wifiIndicators.activityIn;
                wifiCallbackInfo.mActivityOut = wifiIndicators.activityOut;
                wifiCallbackInfo.mIsTransient = wifiIndicators.isTransient;
                Objects.requireNonNull(wifiCallbackInfo);
                InternetTile.this.refreshState(this.mWifiInfo);
            }
        }

        public final String toString() {
            return "InternetSignalCallback[" + "mWifiInfo=" + this.mWifiInfo + ",mCellularInfo=" + this.mCellularInfo + ",mEthernetInfo=" + this.mEthernetInfo + ']';
        }
    }

    /* renamed from: com.android.systemui.qs.tiles.InternetTile$SignalIcon */
    public static class SignalIcon extends QSTile.Icon {
        public final int mState;

        public final Drawable getDrawable(Context context) {
            SignalDrawable signalDrawable = new SignalDrawable(context);
            signalDrawable.setLevel(this.mState);
            return signalDrawable;
        }

        public SignalIcon(int i) {
            this.mState = i;
        }
    }

    /* renamed from: com.android.systemui.qs.tiles.InternetTile$WifiCallbackInfo */
    public static final class WifiCallbackInfo {
        public boolean mActivityIn;
        public boolean mActivityOut;
        public boolean mAirplaneModeEnabled;
        public boolean mConnected;
        public boolean mEnabled;
        public boolean mIsTransient;
        public boolean mNoDefaultNetwork;
        public boolean mNoNetworksAvailable;
        public boolean mNoValidatedNetwork;
        public String mSsid;
        public String mWifiSignalContentDescription;
        public int mWifiSignalIconId;

        public WifiCallbackInfo() {
        }

        public WifiCallbackInfo(int i) {
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("WifiCallbackInfo[");
            sb.append("mAirplaneModeEnabled=");
            sb.append(this.mAirplaneModeEnabled);
            sb.append(",mEnabled=");
            sb.append(this.mEnabled);
            sb.append(",mConnected=");
            sb.append(this.mConnected);
            sb.append(",mWifiSignalIconId=");
            sb.append(this.mWifiSignalIconId);
            sb.append(",mSsid=");
            sb.append(this.mSsid);
            sb.append(",mActivityIn=");
            sb.append(this.mActivityIn);
            sb.append(",mActivityOut=");
            sb.append(this.mActivityOut);
            sb.append(",mWifiSignalContentDescription=");
            sb.append(this.mWifiSignalContentDescription);
            sb.append(",mIsTransient=");
            sb.append(this.mIsTransient);
            sb.append(",mNoDefaultNetwork=");
            sb.append(this.mNoDefaultNetwork);
            sb.append(",mNoValidatedNetwork=");
            sb.append(this.mNoValidatedNetwork);
            sb.append(",mNoNetworksAvailable=");
            return LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0.m21m(sb, this.mNoNetworksAvailable, ']');
        }
    }

    public final int getMetricsCategory() {
        return 126;
    }

    public static String removeDoubleQuotes(String str) {
        if (str == null) {
            return null;
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

    public final QSIconView createTileView(Context context) {
        return new AlphaControlledSignalTileView(context);
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("InternetTile:");
        printWriter.print("    ");
        printWriter.println(((QSTile.SignalState) this.mState).toString());
        printWriter.print("    ");
        printWriter.println("mLastTileState=" + this.mLastTileState);
        printWriter.print("    ");
        printWriter.println("mSignalCallback=" + this.mSignalCallback.toString());
    }

    public final CharSequence getTileLabel() {
        return this.mContext.getString(C1777R.string.quick_settings_internet_label);
    }

    public final void handleClick(View view) {
        this.mHandler.post(new InternetTile$$ExternalSyntheticLambda0(this, view, 0));
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0069  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x006b  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0074  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0076  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x008a  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00aa  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00dc  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0166  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0169  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x016f  */
    /* JADX WARNING: Removed duplicated region for block: B:73:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void handleUpdateCellularState(com.android.systemui.plugins.p005qs.QSTile.SignalState r11, java.lang.Object r12) {
        /*
            r10 = this;
            com.android.systemui.qs.tiles.InternetTile$CellularCallbackInfo r12 = (com.android.systemui.p006qs.tiles.InternetTile.CellularCallbackInfo) r12
            boolean r0 = com.android.systemui.p006qs.tileimpl.QSTileImpl.DEBUG
            if (r0 == 0) goto L_0x001c
            java.lang.String r1 = r10.TAG
            java.lang.String r2 = "handleUpdateCellularState: CellularCallbackInfo = "
            java.lang.StringBuilder r2 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r2)
            java.lang.String r3 = r12.toString()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            android.util.Log.d(r1, r2)
        L_0x001c:
            android.content.Context r1 = r10.mContext
            android.content.res.Resources r1 = r1.getResources()
            r2 = 2131953115(0x7f1305db, float:1.9542692E38)
            java.lang.String r2 = r1.getString(r2)
            r11.label = r2
            r2 = 2
            r11.state = r2
            com.android.settingslib.net.DataUsageController r3 = r10.mDataController
            java.util.Objects.requireNonNull(r3)
            android.telephony.TelephonyManager r4 = r3.getTelephonyManager()
            boolean r4 = r4.isDataCapable()
            r5 = 1
            r6 = 0
            if (r4 == 0) goto L_0x004c
            android.telephony.TelephonyManager r3 = r3.getTelephonyManager()
            int r3 = r3.getSimState()
            r4 = 5
            if (r3 != r4) goto L_0x004c
            r3 = r5
            goto L_0x004d
        L_0x004c:
            r3 = r6
        L_0x004d:
            if (r3 == 0) goto L_0x0060
            com.android.settingslib.net.DataUsageController r3 = r10.mDataController
            java.util.Objects.requireNonNull(r3)
            android.telephony.TelephonyManager r3 = r3.getTelephonyManager()
            boolean r3 = r3.isDataEnabled()
            if (r3 == 0) goto L_0x0060
            r3 = r5
            goto L_0x0061
        L_0x0060:
            r3 = r6
        L_0x0061:
            r11.value = r3
            if (r3 == 0) goto L_0x006b
            boolean r4 = r12.mActivityIn
            if (r4 == 0) goto L_0x006b
            r4 = r5
            goto L_0x006c
        L_0x006b:
            r4 = r6
        L_0x006c:
            r11.activityIn = r4
            if (r3 == 0) goto L_0x0076
            boolean r3 = r12.mActivityOut
            if (r3 == 0) goto L_0x0076
            r3 = r5
            goto L_0x0077
        L_0x0076:
            r3 = r6
        L_0x0077:
            r11.activityOut = r3
            java.lang.Class<android.widget.Switch> r3 = android.widget.Switch.class
            java.lang.String r3 = r3.getName()
            r11.expandedAccessibilityClassName = r3
            boolean r3 = r12.mAirplaneModeEnabled
            r4 = 2131232229(0x7f0805e5, float:1.8080561E38)
            java.lang.String r7 = ""
            if (r3 == 0) goto L_0x00a6
            int r3 = r12.mQsTypeIcon
            com.android.settingslib.SignalIcon$MobileIconGroup r8 = com.android.settingslib.mobile.TelephonyIcons.CARRIER_NETWORK_CHANGE
            r8 = 2131231777(0x7f080421, float:1.8079645E38)
            if (r3 == r8) goto L_0x00a6
            r11.state = r5
            com.android.systemui.plugins.qs.QSTile$Icon r12 = com.android.systemui.p006qs.tileimpl.QSTileImpl.ResourceIcon.get(r4)
            r11.icon = r12
            r12 = 2131953313(0x7f1306a1, float:1.9543093E38)
            java.lang.String r12 = r1.getString(r12)
            r11.secondaryLabel = r12
            goto L_0x015e
        L_0x00a6:
            boolean r3 = r12.mNoDefaultNetwork
            if (r3 == 0) goto L_0x00dc
            boolean r12 = r12.mNoNetworksAvailable
            if (r12 != 0) goto L_0x00cb
            com.android.systemui.qs.tiles.InternetTile$InternetSignalCallback r12 = r10.mSignalCallback
            com.android.systemui.qs.tiles.InternetTile$WifiCallbackInfo r12 = r12.mWifiInfo
            boolean r12 = r12.mEnabled
            if (r12 != 0) goto L_0x00b7
            goto L_0x00cb
        L_0x00b7:
            r12 = 2131232228(0x7f0805e4, float:1.808056E38)
            com.android.systemui.plugins.qs.QSTile$Icon r12 = com.android.systemui.p006qs.tileimpl.QSTileImpl.ResourceIcon.get(r12)
            r11.icon = r12
            r12 = 2131953121(0x7f1305e1, float:1.9542704E38)
            java.lang.String r12 = r1.getString(r12)
            r11.secondaryLabel = r12
            goto L_0x015e
        L_0x00cb:
            com.android.systemui.plugins.qs.QSTile$Icon r12 = com.android.systemui.p006qs.tileimpl.QSTileImpl.ResourceIcon.get(r4)
            r11.icon = r12
            r12 = 2131953122(0x7f1305e2, float:1.9542706E38)
            java.lang.String r12 = r1.getString(r12)
            r11.secondaryLabel = r12
            goto L_0x015e
        L_0x00dc:
            com.android.systemui.qs.tiles.InternetTile$SignalIcon r1 = new com.android.systemui.qs.tiles.InternetTile$SignalIcon
            int r3 = r12.mMobileSignalIconId
            r1.<init>(r3)
            r11.icon = r1
            java.lang.CharSequence r1 = r12.mDataSubscriptionName
            boolean r3 = r12.mRoaming
            r4 = 2131952236(0x7f13026c, float:1.954091E38)
            if (r3 == 0) goto L_0x0116
            java.lang.CharSequence r3 = r12.mDataContentDescription
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 != 0) goto L_0x0116
            android.content.Context r3 = r10.mContext
            java.lang.String r3 = r3.getString(r4)
            java.lang.CharSequence r12 = r12.mDataContentDescription
            if (r12 != 0) goto L_0x0102
            r12 = r7
            goto L_0x0106
        L_0x0102:
            java.lang.String r12 = r12.toString()
        L_0x0106:
            android.content.Context r4 = r10.mContext
            r8 = 2131952774(0x7f130486, float:1.9542E38)
            java.lang.Object[] r9 = new java.lang.Object[r2]
            r9[r6] = r3
            r9[r5] = r12
            java.lang.String r12 = r4.getString(r8, r9)
            goto L_0x0123
        L_0x0116:
            boolean r3 = r12.mRoaming
            if (r3 == 0) goto L_0x0121
            android.content.Context r12 = r10.mContext
            java.lang.String r12 = r12.getString(r4)
            goto L_0x0123
        L_0x0121:
            java.lang.CharSequence r12 = r12.mDataContentDescription
        L_0x0123:
            boolean r3 = android.text.TextUtils.isEmpty(r12)
            if (r3 == 0) goto L_0x0136
            if (r1 != 0) goto L_0x012d
            r12 = r7
            goto L_0x0131
        L_0x012d:
            java.lang.String r12 = r1.toString()
        L_0x0131:
            android.text.Spanned r12 = android.text.Html.fromHtml(r12, r6)
            goto L_0x015c
        L_0x0136:
            boolean r3 = android.text.TextUtils.isEmpty(r1)
            if (r3 == 0) goto L_0x0149
            if (r12 != 0) goto L_0x0140
            r12 = r7
            goto L_0x0144
        L_0x0140:
            java.lang.String r12 = r12.toString()
        L_0x0144:
            android.text.Spanned r12 = android.text.Html.fromHtml(r12, r6)
            goto L_0x015c
        L_0x0149:
            android.content.Context r3 = r10.mContext
            r4 = 2131952763(0x7f13047b, float:1.9541978E38)
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r2[r6] = r1
            r2[r5] = r12
            java.lang.String r12 = r3.getString(r4, r2)
            android.text.Spanned r12 = android.text.Html.fromHtml(r12, r6)
        L_0x015c:
            r11.secondaryLabel = r12
        L_0x015e:
            java.lang.CharSequence r12 = r11.label
            r11.contentDescription = r12
            int r12 = r11.state
            if (r12 != r5) goto L_0x0169
            r11.stateDescription = r7
            goto L_0x016d
        L_0x0169:
            java.lang.CharSequence r12 = r11.secondaryLabel
            r11.stateDescription = r12
        L_0x016d:
            if (r0 == 0) goto L_0x0185
            java.lang.String r10 = r10.TAG
            java.lang.String r12 = "handleUpdateCellularState: SignalState = "
            java.lang.StringBuilder r12 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r12)
            java.lang.String r11 = r11.toString()
            r12.append(r11)
            java.lang.String r11 = r12.toString()
            android.util.Log.d(r10, r11)
        L_0x0185:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.p006qs.tiles.InternetTile.handleUpdateCellularState(com.android.systemui.plugins.qs.QSTile$SignalState, java.lang.Object):void");
    }

    public final void handleUpdateEthernetState(QSTile.SignalState signalState, Object obj) {
        EthernetCallbackInfo ethernetCallbackInfo = (EthernetCallbackInfo) obj;
        boolean z = QSTileImpl.DEBUG;
        if (z) {
            String str = this.TAG;
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("handleUpdateEthernetState: EthernetCallbackInfo = ");
            m.append(ethernetCallbackInfo.toString());
            Log.d(str, m.toString());
        }
        signalState.label = this.mContext.getResources().getString(C1777R.string.quick_settings_internet_label);
        signalState.state = 2;
        signalState.icon = QSTileImpl.ResourceIcon.get(ethernetCallbackInfo.mEthernetSignalIconId);
        signalState.secondaryLabel = ethernetCallbackInfo.mEthernetContentDescription;
        if (z) {
            String str2 = this.TAG;
            StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("handleUpdateEthernetState: SignalState = ");
            m2.append(signalState.toString());
            Log.d(str2, m2.toString());
        }
    }

    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.SignalState signalState = (QSTile.SignalState) state;
        if (obj instanceof CellularCallbackInfo) {
            this.mLastTileState = 0;
            handleUpdateCellularState(signalState, obj);
        } else if (obj instanceof WifiCallbackInfo) {
            this.mLastTileState = 1;
            handleUpdateWifiState(signalState, obj);
        } else if (obj instanceof EthernetCallbackInfo) {
            this.mLastTileState = 2;
            handleUpdateEthernetState(signalState, obj);
        } else {
            int i = this.mLastTileState;
            if (i == 0) {
                handleUpdateCellularState(signalState, this.mSignalCallback.mCellularInfo);
            } else if (i == 1) {
                handleUpdateWifiState(signalState, this.mSignalCallback.mWifiInfo);
            } else if (i == 2) {
                handleUpdateEthernetState(signalState, this.mSignalCallback.mEthernetInfo);
            }
        }
    }

    public final void handleUpdateWifiState(QSTile.SignalState signalState, Object obj) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        WifiCallbackInfo wifiCallbackInfo = (WifiCallbackInfo) obj;
        boolean z5 = QSTileImpl.DEBUG;
        if (z5) {
            String str = this.TAG;
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("handleUpdateWifiState: WifiCallbackInfo = ");
            m.append(wifiCallbackInfo.toString());
            Log.d(str, m.toString());
        }
        if (!wifiCallbackInfo.mEnabled || wifiCallbackInfo.mWifiSignalIconId <= 0 || wifiCallbackInfo.mSsid == null) {
            z = false;
        } else {
            z = true;
        }
        if (wifiCallbackInfo.mWifiSignalIconId <= 0 || wifiCallbackInfo.mSsid != null) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (signalState.slash == null) {
            QSTile.SlashState slashState = new QSTile.SlashState();
            signalState.slash = slashState;
            slashState.rotation = 6.0f;
        }
        signalState.slash.isSlashed = false;
        boolean z6 = wifiCallbackInfo.mIsTransient;
        String removeDoubleQuotes = removeDoubleQuotes(wifiCallbackInfo.mSsid);
        if (z6) {
            removeDoubleQuotes = this.mContext.getString(C1777R.string.quick_settings_wifi_secondary_label_transient);
        }
        signalState.secondaryLabel = removeDoubleQuotes;
        signalState.state = 2;
        signalState.dualTarget = true;
        boolean z7 = wifiCallbackInfo.mEnabled;
        signalState.value = z7;
        if (!z7 || !wifiCallbackInfo.mActivityIn) {
            z3 = false;
        } else {
            z3 = true;
        }
        signalState.activityIn = z3;
        if (!z7 || !wifiCallbackInfo.mActivityOut) {
            z4 = false;
        } else {
            z4 = true;
        }
        signalState.activityOut = z4;
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        Resources resources = this.mContext.getResources();
        signalState.label = resources.getString(C1777R.string.quick_settings_internet_label);
        if (wifiCallbackInfo.mAirplaneModeEnabled) {
            if (!signalState.value) {
                signalState.state = 1;
                signalState.icon = QSTileImpl.ResourceIcon.get(C1777R.C1778drawable.ic_qs_no_internet_unavailable);
                signalState.secondaryLabel = resources.getString(C1777R.string.status_bar_airplane);
            } else if (!z) {
                signalState.icon = QSTileImpl.ResourceIcon.get(C1777R.C1778drawable.ic_qs_no_internet_unavailable);
                if (wifiCallbackInfo.mNoNetworksAvailable) {
                    signalState.secondaryLabel = resources.getString(C1777R.string.quick_settings_networks_unavailable);
                } else {
                    signalState.secondaryLabel = resources.getString(C1777R.string.quick_settings_networks_available);
                }
            } else {
                signalState.icon = QSTileImpl.ResourceIcon.get(wifiCallbackInfo.mWifiSignalIconId);
            }
        } else if (wifiCallbackInfo.mNoDefaultNetwork) {
            if (wifiCallbackInfo.mNoNetworksAvailable || !wifiCallbackInfo.mEnabled) {
                signalState.icon = QSTileImpl.ResourceIcon.get(C1777R.C1778drawable.ic_qs_no_internet_unavailable);
                signalState.secondaryLabel = resources.getString(C1777R.string.quick_settings_networks_unavailable);
            } else {
                signalState.icon = QSTileImpl.ResourceIcon.get(C1777R.C1778drawable.ic_qs_no_internet_available);
                signalState.secondaryLabel = resources.getString(C1777R.string.quick_settings_networks_available);
            }
        } else if (wifiCallbackInfo.mIsTransient) {
            signalState.icon = QSTileImpl.ResourceIcon.get(17302859);
        } else if (!signalState.value) {
            signalState.slash.isSlashed = true;
            signalState.state = 1;
            signalState.icon = QSTileImpl.ResourceIcon.get(17302891);
        } else if (z) {
            signalState.icon = QSTileImpl.ResourceIcon.get(wifiCallbackInfo.mWifiSignalIconId);
        } else if (z2) {
            signalState.icon = QSTileImpl.ResourceIcon.get(17302891);
        } else {
            signalState.icon = QSTileImpl.ResourceIcon.get(17302891);
        }
        stringBuffer.append(this.mContext.getString(C1777R.string.quick_settings_internet_label));
        stringBuffer.append(",");
        if (signalState.value && z) {
            stringBuffer2.append(wifiCallbackInfo.mWifiSignalContentDescription);
            stringBuffer.append(removeDoubleQuotes(wifiCallbackInfo.mSsid));
        } else if (!TextUtils.isEmpty(signalState.secondaryLabel)) {
            stringBuffer.append(",");
            stringBuffer.append(signalState.secondaryLabel);
        }
        signalState.stateDescription = stringBuffer2.toString();
        signalState.contentDescription = stringBuffer.toString();
        signalState.dualLabelContentDescription = resources.getString(C1777R.string.accessibility_quick_settings_open_settings, new Object[]{getTileLabel()});
        signalState.expandedAccessibilityClassName = Switch.class.getName();
        if (z5) {
            String str2 = this.TAG;
            StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("handleUpdateWifiState: SignalState = ");
            m2.append(signalState.toString());
            Log.d(str2, m2.toString());
        }
    }

    public final boolean isAvailable() {
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.wifi") || (this.mController.hasMobileDataFeature() && this.mHost.getUserContext().getUserId() == 0)) {
            return true;
        }
        return false;
    }

    public final QSTile.State newTileState() {
        QSTile.SignalState signalState = new QSTile.SignalState();
        signalState.forceExpandIcon = true;
        return signalState;
    }

    public InternetTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, NetworkController networkController, AccessPointController accessPointController, InternetDialogFactory internetDialogFactory) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        InternetSignalCallback internetSignalCallback = new InternetSignalCallback();
        this.mSignalCallback = internetSignalCallback;
        this.mInternetDialogFactory = internetDialogFactory;
        this.mHandler = handler;
        this.mController = networkController;
        this.mAccessPointController = accessPointController;
        this.mDataController = networkController.getMobileDataController();
        networkController.observe((Lifecycle) this.mLifecycle, internetSignalCallback);
    }

    public final Intent getLongClickIntent() {
        return WIFI_SETTINGS;
    }
}
