package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Handler;
import android.telephony.SubscriptionInfo;
import android.util.ArraySet;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline2;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline1;
import com.android.keyguard.KeyguardUpdateMonitor$18$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda3;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.statusbar.connectivity.IconState;
import com.android.systemui.statusbar.connectivity.MobileDataIndicators;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.SignalCallback;
import com.android.systemui.statusbar.connectivity.WifiIndicators;
import com.android.systemui.statusbar.policy.SecurityController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.CarrierConfigTracker;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class StatusBarSignalPolicy implements SignalCallback, SecurityController.SecurityControllerCallback, TunerService.Tunable {
    public static final boolean DEBUG = Log.isLoggable("StatusBarSignalPolicy", 3);
    public boolean mActivityEnabled;
    public ArrayList<CallIndicatorIconState> mCallIndicatorStates = new ArrayList<>();
    public final CarrierConfigTracker mCarrierConfigTracker;
    public final Context mContext;
    public final FeatureFlags mFeatureFlags;
    public final Handler mHandler = Handler.getMain();
    public boolean mHideAirplane;
    public boolean mHideEthernet;
    public boolean mHideMobile;
    public boolean mHideWifi;
    public final StatusBarIconController mIconController;
    public boolean mInitialized;
    public boolean mIsAirplaneMode = false;
    public boolean mIsWifiEnabled = false;
    public ArrayList<MobileIconState> mMobileStates = new ArrayList<>();
    public final NetworkController mNetworkController;
    public final SecurityController mSecurityController;
    public final String mSlotAirplane;
    public final String mSlotCallStrength;
    public final String mSlotEthernet;
    public final String mSlotMobile;
    public final String mSlotNoCalling;
    public final String mSlotVpn;
    public final String mSlotWifi;
    public final TunerService mTunerService;
    public WifiIconState mWifiIconState = new WifiIconState();

    public static class CallIndicatorIconState {
        public String callStrengthDescription;
        public int callStrengthResId = TelephonyIcons.MOBILE_CALL_STRENGTH_ICONS[0];
        public boolean isNoCalling;
        public String noCallingDescription;
        public int noCallingResId = C1777R.C1778drawable.ic_qs_no_calling_sms;
        public int subId;

        public final boolean equals(Object obj) {
            if (obj == null || CallIndicatorIconState.class != obj.getClass()) {
                return false;
            }
            CallIndicatorIconState callIndicatorIconState = (CallIndicatorIconState) obj;
            return this.isNoCalling == callIndicatorIconState.isNoCalling && this.noCallingResId == callIndicatorIconState.noCallingResId && this.callStrengthResId == callIndicatorIconState.callStrengthResId && this.subId == callIndicatorIconState.subId && this.noCallingDescription == callIndicatorIconState.noCallingDescription && this.callStrengthDescription == callIndicatorIconState.callStrengthDescription;
        }

        public final int hashCode() {
            return Objects.hash(new Object[]{Boolean.valueOf(this.isNoCalling), Integer.valueOf(this.noCallingResId), Integer.valueOf(this.callStrengthResId), Integer.valueOf(this.subId), this.noCallingDescription, this.callStrengthDescription});
        }

        /* renamed from: -$$Nest$smcopyStates  reason: not valid java name */
        public static ArrayList m248$$Nest$smcopyStates(ArrayList arrayList) {
            ArrayList arrayList2 = new ArrayList();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                CallIndicatorIconState callIndicatorIconState = (CallIndicatorIconState) it.next();
                CallIndicatorIconState callIndicatorIconState2 = new CallIndicatorIconState(callIndicatorIconState.subId);
                callIndicatorIconState2.isNoCalling = callIndicatorIconState.isNoCalling;
                callIndicatorIconState2.noCallingResId = callIndicatorIconState.noCallingResId;
                callIndicatorIconState2.callStrengthResId = callIndicatorIconState.callStrengthResId;
                callIndicatorIconState2.subId = callIndicatorIconState.subId;
                callIndicatorIconState2.noCallingDescription = callIndicatorIconState.noCallingDescription;
                callIndicatorIconState2.callStrengthDescription = callIndicatorIconState.callStrengthDescription;
                arrayList2.add(callIndicatorIconState2);
            }
            return arrayList2;
        }

        public CallIndicatorIconState(int i) {
            this.subId = i;
        }
    }

    public static class MobileIconState extends SignalIconState {
        public boolean needsLeadingPadding;
        public boolean roaming;
        public boolean showTriangle;
        public int strengthId;
        public int subId;
        public CharSequence typeContentDescription;
        public int typeId;

        public MobileIconState(int i) {
            super(0);
            this.subId = i;
        }

        public final boolean equals(Object obj) {
            if (obj == null || MobileIconState.class != obj.getClass() || !super.equals(obj)) {
                return false;
            }
            MobileIconState mobileIconState = (MobileIconState) obj;
            return this.subId == mobileIconState.subId && this.strengthId == mobileIconState.strengthId && this.typeId == mobileIconState.typeId && this.showTriangle == mobileIconState.showTriangle && this.roaming == mobileIconState.roaming && this.needsLeadingPadding == mobileIconState.needsLeadingPadding && Objects.equals(this.typeContentDescription, mobileIconState.typeContentDescription);
        }

        public final void copyTo(MobileIconState mobileIconState) {
            mobileIconState.visible = this.visible;
            mobileIconState.activityIn = this.activityIn;
            mobileIconState.activityOut = this.activityOut;
            mobileIconState.slot = this.slot;
            mobileIconState.contentDescription = this.contentDescription;
            mobileIconState.subId = this.subId;
            mobileIconState.strengthId = this.strengthId;
            mobileIconState.typeId = this.typeId;
            mobileIconState.showTriangle = this.showTriangle;
            mobileIconState.roaming = this.roaming;
            mobileIconState.needsLeadingPadding = this.needsLeadingPadding;
            mobileIconState.typeContentDescription = this.typeContentDescription;
        }

        public final int hashCode() {
            return Objects.hash(new Object[]{Integer.valueOf(super.hashCode()), Integer.valueOf(this.subId), Integer.valueOf(this.strengthId), Integer.valueOf(this.typeId), Boolean.valueOf(this.showTriangle), Boolean.valueOf(this.roaming), Boolean.valueOf(this.needsLeadingPadding), this.typeContentDescription});
        }

        public final String toString() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("MobileIconState(subId=");
            m.append(this.subId);
            m.append(", strengthId=");
            m.append(this.strengthId);
            m.append(", showTriangle=");
            m.append(this.showTriangle);
            m.append(", roaming=");
            m.append(this.roaming);
            m.append(", typeId=");
            m.append(this.typeId);
            m.append(", visible=");
            m.append(this.visible);
            m.append(")");
            return m.toString();
        }
    }

    public static abstract class SignalIconState {
        public boolean activityIn;
        public boolean activityOut;
        public String contentDescription;
        public String slot;
        public boolean visible;

        public SignalIconState() {
        }

        public boolean equals(Object obj) {
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            SignalIconState signalIconState = (SignalIconState) obj;
            return this.visible == signalIconState.visible && this.activityOut == signalIconState.activityOut && this.activityIn == signalIconState.activityIn && Objects.equals(this.contentDescription, signalIconState.contentDescription) && Objects.equals(this.slot, signalIconState.slot);
        }

        public int hashCode() {
            return Objects.hash(new Object[]{Boolean.valueOf(this.visible), Boolean.valueOf(this.activityOut), this.slot});
        }

        public SignalIconState(int i) {
        }
    }

    public static class WifiIconState extends SignalIconState {
        public boolean airplaneSpacerVisible;
        public boolean noDefaultNetwork;
        public boolean noNetworksAvailable;
        public boolean noValidatedNetwork;
        public int resId;
        public boolean signalSpacerVisible;

        public WifiIconState() {
            super(0);
        }

        public final boolean equals(Object obj) {
            if (obj == null || WifiIconState.class != obj.getClass() || !super.equals(obj)) {
                return false;
            }
            WifiIconState wifiIconState = (WifiIconState) obj;
            return this.resId == wifiIconState.resId && this.airplaneSpacerVisible == wifiIconState.airplaneSpacerVisible && this.signalSpacerVisible == wifiIconState.signalSpacerVisible && this.noDefaultNetwork == wifiIconState.noDefaultNetwork && this.noValidatedNetwork == wifiIconState.noValidatedNetwork && this.noNetworksAvailable == wifiIconState.noNetworksAvailable;
        }

        public final int hashCode() {
            return Objects.hash(new Object[]{Integer.valueOf(super.hashCode()), Integer.valueOf(this.resId), Boolean.valueOf(this.airplaneSpacerVisible), Boolean.valueOf(this.signalSpacerVisible), Boolean.valueOf(this.noDefaultNetwork), Boolean.valueOf(this.noValidatedNetwork), Boolean.valueOf(this.noNetworksAvailable)});
        }

        public final WifiIconState copy() {
            WifiIconState wifiIconState = new WifiIconState();
            wifiIconState.visible = this.visible;
            wifiIconState.activityIn = this.activityIn;
            wifiIconState.activityOut = this.activityOut;
            wifiIconState.slot = this.slot;
            wifiIconState.contentDescription = this.contentDescription;
            wifiIconState.resId = this.resId;
            wifiIconState.airplaneSpacerVisible = this.airplaneSpacerVisible;
            wifiIconState.signalSpacerVisible = this.signalSpacerVisible;
            wifiIconState.noDefaultNetwork = this.noDefaultNetwork;
            wifiIconState.noValidatedNetwork = this.noValidatedNetwork;
            wifiIconState.noNetworksAvailable = this.noNetworksAvailable;
            return wifiIconState;
        }

        public final String toString() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("WifiIconState(resId=");
            m.append(this.resId);
            m.append(", visible=");
            m.append(this.visible);
            m.append(")");
            return m.toString();
        }
    }

    public final void setMobileDataEnabled(boolean z) {
    }

    public final void setNoSims(boolean z, boolean z2) {
    }

    public final void onStateChanged() {
        this.mHandler.post(new ScreenDecorations$$ExternalSyntheticLambda3(this, 3));
    }

    public final void onTuningChanged(String str, String str2) {
        if ("icon_blacklist".equals(str)) {
            ArraySet<String> iconHideList = StatusBarIconController.getIconHideList(this.mContext, str2);
            boolean contains = iconHideList.contains(this.mSlotAirplane);
            boolean contains2 = iconHideList.contains(this.mSlotMobile);
            boolean contains3 = iconHideList.contains(this.mSlotWifi);
            boolean contains4 = iconHideList.contains(this.mSlotEthernet);
            if (contains != this.mHideAirplane || contains2 != this.mHideMobile || contains4 != this.mHideEthernet || contains3 != this.mHideWifi) {
                this.mHideAirplane = contains;
                this.mHideMobile = contains2;
                this.mHideEthernet = contains4;
                this.mHideWifi = contains3;
                this.mNetworkController.removeCallback(this);
                this.mNetworkController.addCallback(this);
            }
        }
    }

    public final void setCallIndicator(IconState iconState, int i) {
        CallIndicatorIconState callIndicatorIconState;
        if (DEBUG) {
            Log.d("StatusBarSignalPolicy", "setCallIndicator: statusIcon = " + iconState + ",subId = " + i);
        }
        Iterator<CallIndicatorIconState> it = this.mCallIndicatorStates.iterator();
        while (true) {
            if (!it.hasNext()) {
                KeyguardUpdateMonitor$$ExternalSyntheticOutline1.m27m("Unexpected subscription ", i, "StatusBarSignalPolicy");
                callIndicatorIconState = null;
                break;
            }
            callIndicatorIconState = it.next();
            if (callIndicatorIconState.subId == i) {
                break;
            }
        }
        if (callIndicatorIconState != null) {
            int i2 = iconState.icon;
            if (i2 == C1777R.C1778drawable.ic_qs_no_calling_sms) {
                callIndicatorIconState.isNoCalling = iconState.visible;
                callIndicatorIconState.noCallingDescription = iconState.contentDescription;
            } else {
                callIndicatorIconState.callStrengthResId = i2;
                callIndicatorIconState.callStrengthDescription = iconState.contentDescription;
            }
            if (this.mCarrierConfigTracker.getCallStrengthConfig(i)) {
                this.mIconController.setCallStrengthIcons(this.mSlotCallStrength, CallIndicatorIconState.m248$$Nest$smcopyStates(this.mCallIndicatorStates));
            } else {
                this.mIconController.removeIcon(this.mSlotCallStrength, i);
            }
            this.mIconController.setNoCallingIcons(this.mSlotNoCalling, CallIndicatorIconState.m248$$Nest$smcopyStates(this.mCallIndicatorStates));
        }
    }

    public final void setConnectivityStatus(boolean z, boolean z2, boolean z3) {
        if (this.mFeatureFlags.isEnabled(Flags.COMBINED_STATUS_BAR_SIGNAL_ICONS)) {
            if (DEBUG) {
                StringBuilder sb = new StringBuilder();
                sb.append("setConnectivityStatus: noDefaultNetwork = ");
                sb.append(z);
                sb.append(",noValidatedNetwork = ");
                sb.append(z2);
                sb.append(",noNetworksAvailable = ");
                KeyguardUpdateMonitor$18$$ExternalSyntheticOutline0.m29m(sb, z3, "StatusBarSignalPolicy");
            }
            WifiIconState copy = this.mWifiIconState.copy();
            copy.noDefaultNetwork = z;
            copy.noValidatedNetwork = z2;
            copy.noNetworksAvailable = z3;
            copy.slot = this.mSlotWifi;
            boolean z4 = this.mIsAirplaneMode;
            copy.airplaneSpacerVisible = z4;
            if (z && z3 && !z4) {
                copy.visible = true;
                copy.resId = C1777R.C1778drawable.ic_qs_no_internet_unavailable;
            } else if (!z || z3 || (z4 && (!z4 || !this.mIsWifiEnabled))) {
                copy.visible = false;
                copy.resId = 0;
            } else {
                copy.visible = true;
                copy.resId = C1777R.C1778drawable.ic_qs_no_internet_available;
            }
            updateWifiIconWithState(copy);
            this.mWifiIconState = copy;
        }
    }

    public final void setEthernetIndicators(IconState iconState) {
        boolean z = iconState.visible;
        int i = iconState.icon;
        String str = iconState.contentDescription;
        if (i > 0) {
            this.mIconController.setIcon(this.mSlotEthernet, i, str);
            this.mIconController.setIconVisibility(this.mSlotEthernet, true);
            return;
        }
        this.mIconController.setIconVisibility(this.mSlotEthernet, false);
    }

    public final void setIsAirplaneMode(IconState iconState) {
        boolean z;
        String str;
        if (DEBUG) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("setIsAirplaneMode: icon = ");
            if (iconState == null) {
                str = "";
            } else {
                str = iconState.toString();
            }
            ExifInterface$$ExternalSyntheticOutline2.m15m(m, str, "StatusBarSignalPolicy");
        }
        if (!iconState.visible || this.mHideAirplane) {
            z = false;
        } else {
            z = true;
        }
        this.mIsAirplaneMode = z;
        int i = iconState.icon;
        String str2 = iconState.contentDescription;
        if (!z || i <= 0) {
            this.mIconController.setIconVisibility(this.mSlotAirplane, false);
            return;
        }
        this.mIconController.setIcon(this.mSlotAirplane, i, str2);
        this.mIconController.setIconVisibility(this.mSlotAirplane, true);
    }

    public final void setMobileDataIndicators(MobileDataIndicators mobileDataIndicators) {
        MobileIconState mobileIconState;
        MobileIconState mobileIconState2;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        String str;
        if (DEBUG) {
            Log.d("StatusBarSignalPolicy", "setMobileDataIndicators: " + mobileDataIndicators);
        }
        int i = mobileDataIndicators.subId;
        Iterator<MobileIconState> it = this.mMobileStates.iterator();
        while (true) {
            mobileIconState = null;
            if (!it.hasNext()) {
                KeyguardUpdateMonitor$$ExternalSyntheticOutline1.m27m("Unexpected subscription ", i, "StatusBarSignalPolicy");
                mobileIconState2 = null;
                break;
            }
            mobileIconState2 = it.next();
            if (mobileIconState2.subId == i) {
                break;
            }
        }
        if (mobileIconState2 != null) {
            int i2 = mobileDataIndicators.statusType;
            int i3 = mobileIconState2.typeId;
            boolean z5 = true;
            if (i2 == i3 || !(i2 == 0 || i3 == 0)) {
                z = false;
            } else {
                z = true;
            }
            IconState iconState = mobileDataIndicators.statusIcon;
            if (!iconState.visible || this.mHideMobile) {
                z2 = false;
            } else {
                z2 = true;
            }
            mobileIconState2.visible = z2;
            mobileIconState2.strengthId = iconState.icon;
            mobileIconState2.typeId = i2;
            mobileIconState2.contentDescription = iconState.contentDescription;
            mobileIconState2.typeContentDescription = mobileDataIndicators.typeContentDescription;
            mobileIconState2.showTriangle = mobileDataIndicators.showTriangle;
            mobileIconState2.roaming = mobileDataIndicators.roaming;
            if (!mobileDataIndicators.activityIn || !this.mActivityEnabled) {
                z3 = false;
            } else {
                z3 = true;
            }
            mobileIconState2.activityIn = z3;
            if (!mobileDataIndicators.activityOut || !this.mActivityEnabled) {
                z4 = false;
            } else {
                z4 = true;
            }
            mobileIconState2.activityOut = z4;
            if (DEBUG) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("MobileIconStates: ");
                ArrayList<MobileIconState> arrayList = this.mMobileStates;
                if (arrayList == null) {
                    str = "";
                } else {
                    str = arrayList.toString();
                }
                ExifInterface$$ExternalSyntheticOutline2.m15m(m, str, "StatusBarSignalPolicy");
            }
            StatusBarIconController statusBarIconController = this.mIconController;
            String str2 = this.mSlotMobile;
            ArrayList<MobileIconState> arrayList2 = this.mMobileStates;
            ArrayList arrayList3 = new ArrayList();
            Iterator<MobileIconState> it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                MobileIconState next = it2.next();
                MobileIconState mobileIconState3 = new MobileIconState(next.subId);
                next.copyTo(mobileIconState3);
                arrayList3.add(mobileIconState3);
            }
            statusBarIconController.setMobileIcons(str2, arrayList3);
            if (z) {
                WifiIconState copy = this.mWifiIconState.copy();
                if (this.mMobileStates.size() > 0) {
                    mobileIconState = this.mMobileStates.get(0);
                }
                if (mobileIconState == null || mobileIconState.typeId == 0) {
                    z5 = false;
                }
                copy.signalSpacerVisible = z5;
                if (!copy.equals(this.mWifiIconState)) {
                    updateWifiIconWithState(copy);
                    this.mWifiIconState = copy;
                }
            }
        }
    }

    public final void setSubs(List<SubscriptionInfo> list) {
        boolean z;
        boolean z2;
        String str;
        if (DEBUG) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("setSubs: ");
            if (list == null) {
                str = "";
            } else {
                str = list.toString();
            }
            ExifInterface$$ExternalSyntheticOutline2.m15m(m, str, "StatusBarSignalPolicy");
        }
        int size = list.size();
        if (size == this.mMobileStates.size()) {
            int i = 0;
            while (true) {
                if (i >= size) {
                    z = true;
                    break;
                } else if (this.mMobileStates.get(i).subId != list.get(i).getSubscriptionId()) {
                    break;
                } else {
                    i++;
                }
            }
        }
        z = false;
        if (!z) {
            this.mIconController.removeAllIconsForSlot(this.mSlotMobile);
            this.mIconController.removeAllIconsForSlot(this.mSlotNoCalling);
            this.mIconController.removeAllIconsForSlot(this.mSlotCallStrength);
            this.mMobileStates.clear();
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(this.mCallIndicatorStates);
            this.mCallIndicatorStates.clear();
            int size2 = list.size();
            for (int i2 = 0; i2 < size2; i2++) {
                this.mMobileStates.add(new MobileIconState(list.get(i2).getSubscriptionId()));
                Iterator it = arrayList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z2 = true;
                        break;
                    }
                    CallIndicatorIconState callIndicatorIconState = (CallIndicatorIconState) it.next();
                    if (callIndicatorIconState.subId == list.get(i2).getSubscriptionId()) {
                        this.mCallIndicatorStates.add(callIndicatorIconState);
                        z2 = false;
                        break;
                    }
                }
                if (z2) {
                    this.mCallIndicatorStates.add(new CallIndicatorIconState(list.get(i2).getSubscriptionId()));
                }
            }
        }
    }

    public final void setWifiIndicators(WifiIndicators wifiIndicators) {
        boolean z;
        boolean z2;
        boolean z3;
        MobileIconState mobileIconState;
        boolean z4;
        if (DEBUG) {
            Log.d("StatusBarSignalPolicy", "setWifiIndicators: " + wifiIndicators);
        }
        boolean z5 = true;
        if (!wifiIndicators.statusIcon.visible || this.mHideWifi) {
            z = false;
        } else {
            z = true;
        }
        if (!wifiIndicators.activityIn || !this.mActivityEnabled || !z) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (!wifiIndicators.activityOut || !this.mActivityEnabled || !z) {
            z3 = false;
        } else {
            z3 = true;
        }
        this.mIsWifiEnabled = wifiIndicators.enabled;
        WifiIconState copy = this.mWifiIconState.copy();
        WifiIconState wifiIconState = this.mWifiIconState;
        boolean z6 = wifiIconState.noDefaultNetwork;
        if (z6 && wifiIconState.noNetworksAvailable && !this.mIsAirplaneMode) {
            copy.visible = true;
            copy.resId = C1777R.C1778drawable.ic_qs_no_internet_unavailable;
        } else if (!z6 || wifiIconState.noNetworksAvailable || (z4 && (!(z4 = this.mIsAirplaneMode) || !this.mIsWifiEnabled))) {
            copy.visible = z;
            IconState iconState = wifiIndicators.statusIcon;
            copy.resId = iconState.icon;
            copy.activityIn = z2;
            copy.activityOut = z3;
            copy.contentDescription = iconState.contentDescription;
            if (this.mMobileStates.size() > 0) {
                mobileIconState = this.mMobileStates.get(0);
            } else {
                mobileIconState = null;
            }
            if (mobileIconState == null || mobileIconState.typeId == 0) {
                z5 = false;
            }
            copy.signalSpacerVisible = z5;
        } else {
            copy.visible = true;
            copy.resId = C1777R.C1778drawable.ic_qs_no_internet_available;
        }
        copy.slot = this.mSlotWifi;
        copy.airplaneSpacerVisible = this.mIsAirplaneMode;
        updateWifiIconWithState(copy);
        this.mWifiIconState = copy;
    }

    public final void updateWifiIconWithState(WifiIconState wifiIconState) {
        String str;
        if (DEBUG) {
            if (("WifiIconState: " + wifiIconState) == null) {
                str = "";
            } else {
                str = wifiIconState.toString();
            }
            Log.d("StatusBarSignalPolicy", str);
        }
        if (!wifiIconState.visible || wifiIconState.resId <= 0) {
            this.mIconController.setIconVisibility(this.mSlotWifi, false);
            return;
        }
        this.mIconController.setSignalIcon(this.mSlotWifi, wifiIconState);
        this.mIconController.setIconVisibility(this.mSlotWifi, true);
    }

    public StatusBarSignalPolicy(Context context, StatusBarIconController statusBarIconController, CarrierConfigTracker carrierConfigTracker, NetworkController networkController, SecurityController securityController, TunerService tunerService, FeatureFlags featureFlags) {
        this.mContext = context;
        this.mIconController = statusBarIconController;
        this.mCarrierConfigTracker = carrierConfigTracker;
        this.mNetworkController = networkController;
        this.mSecurityController = securityController;
        this.mTunerService = tunerService;
        this.mFeatureFlags = featureFlags;
        this.mSlotAirplane = context.getString(17041533);
        this.mSlotMobile = context.getString(17041551);
        this.mSlotWifi = context.getString(17041567);
        this.mSlotEthernet = context.getString(17041544);
        this.mSlotVpn = context.getString(17041566);
        this.mSlotNoCalling = context.getString(17041554);
        this.mSlotCallStrength = context.getString(17041537);
        this.mActivityEnabled = context.getResources().getBoolean(C1777R.bool.config_showActivity);
    }
}
