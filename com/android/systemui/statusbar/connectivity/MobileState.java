package com.android.systemui.statusbar.connectivity;

import android.telephony.NetworkRegistrationInfo;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyDisplayInfo;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MobileState.kt */
public final class MobileState extends ConnectivityState {
    public boolean airplaneMode = false;
    public boolean carrierNetworkChangeMode = false;
    public boolean dataConnected = false;
    public boolean dataSim = false;
    public int dataState = 0;
    public boolean defaultDataOff = false;
    public boolean isDefault = false;
    public boolean isEmergency = false;
    public String networkName = null;
    public String networkNameData = null;
    public boolean roaming = false;
    public ServiceState serviceState;
    public SignalStrength signalStrength;
    public TelephonyDisplayInfo telephonyDisplayInfo = new TelephonyDisplayInfo(0, 0);
    public boolean userSetup = false;

    public final boolean equals(Object obj) {
        Class<?> cls;
        if (this == obj) {
            return true;
        }
        Class<MobileState> cls2 = MobileState.class;
        if (obj == null) {
            cls = null;
        } else {
            cls = obj.getClass();
        }
        if (!Intrinsics.areEqual(cls2, cls) || !super.equals(obj)) {
            return false;
        }
        Objects.requireNonNull(obj, "null cannot be cast to non-null type com.android.systemui.statusbar.connectivity.MobileState");
        MobileState mobileState = (MobileState) obj;
        return Intrinsics.areEqual(this.networkName, mobileState.networkName) && Intrinsics.areEqual(this.networkNameData, mobileState.networkNameData) && this.dataSim == mobileState.dataSim && this.dataConnected == mobileState.dataConnected && this.isEmergency == mobileState.isEmergency && this.airplaneMode == mobileState.airplaneMode && this.carrierNetworkChangeMode == mobileState.carrierNetworkChangeMode && this.isDefault == mobileState.isDefault && this.userSetup == mobileState.userSetup && this.roaming == mobileState.roaming && this.dataState == mobileState.dataState && this.defaultDataOff == mobileState.defaultDataOff && Intrinsics.areEqual(this.telephonyDisplayInfo, mobileState.telephonyDisplayInfo) && Intrinsics.areEqual(this.serviceState, mobileState.serviceState) && Intrinsics.areEqual(this.signalStrength, mobileState.signalStrength);
    }

    public final void copyFrom(ConnectivityState connectivityState) {
        MobileState mobileState;
        if (connectivityState instanceof MobileState) {
            mobileState = (MobileState) connectivityState;
        } else {
            mobileState = null;
        }
        if (mobileState != null) {
            super.copyFrom(mobileState);
            this.networkName = mobileState.networkName;
            this.networkNameData = mobileState.networkNameData;
            this.dataSim = mobileState.dataSim;
            this.dataConnected = mobileState.dataConnected;
            this.isEmergency = mobileState.isEmergency;
            this.airplaneMode = mobileState.airplaneMode;
            this.carrierNetworkChangeMode = mobileState.carrierNetworkChangeMode;
            this.isDefault = mobileState.isDefault;
            this.userSetup = mobileState.userSetup;
            this.roaming = mobileState.roaming;
            this.dataState = mobileState.dataState;
            this.defaultDataOff = mobileState.defaultDataOff;
            this.telephonyDisplayInfo = mobileState.telephonyDisplayInfo;
            this.serviceState = mobileState.serviceState;
            this.signalStrength = mobileState.signalStrength;
            return;
        }
        throw new IllegalArgumentException("MobileState can only update from another MobileState");
    }

    public final String getOperatorAlphaShort() {
        String operatorAlphaShort;
        ServiceState serviceState2 = this.serviceState;
        if (serviceState2 == null || (operatorAlphaShort = serviceState2.getOperatorAlphaShort()) == null) {
            return "";
        }
        return operatorAlphaShort;
    }

    public final boolean isInService() {
        boolean z;
        boolean z2;
        ServiceState serviceState2 = this.serviceState;
        if (serviceState2 == null) {
            return false;
        }
        int state = serviceState2.getState();
        int dataRegistrationState = serviceState2.getDataRegistrationState();
        if ((state == 1 || state == 2) && dataRegistrationState == 0) {
            NetworkRegistrationInfo networkRegistrationInfo = serviceState2.getNetworkRegistrationInfo(2, 2);
            if (networkRegistrationInfo == null) {
                z = true;
            } else {
                if (networkRegistrationInfo.getRegistrationState() == 1 || networkRegistrationInfo.getRegistrationState() == 5) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                z = !z2;
            }
            if (z) {
                state = 0;
            }
        }
        if (state == 3 || state == 1 || state == 2) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int i;
        int i2;
        int i3;
        int hashCode = super.hashCode() * 31;
        String str = this.networkName;
        int i4 = 0;
        if (str == null) {
            i = 0;
        } else {
            i = str.hashCode();
        }
        int i5 = (hashCode + i) * 31;
        String str2 = this.networkNameData;
        if (str2 == null) {
            i2 = 0;
        } else {
            i2 = str2.hashCode();
        }
        int hashCode2 = Boolean.hashCode(this.dataSim);
        int hashCode3 = Boolean.hashCode(this.dataConnected);
        int hashCode4 = Boolean.hashCode(this.isEmergency);
        int hashCode5 = Boolean.hashCode(this.airplaneMode);
        int hashCode6 = Boolean.hashCode(this.carrierNetworkChangeMode);
        int hashCode7 = Boolean.hashCode(this.isDefault);
        int hashCode8 = Boolean.hashCode(this.userSetup);
        int hashCode9 = Boolean.hashCode(this.roaming);
        int hashCode10 = (this.telephonyDisplayInfo.hashCode() + ((Boolean.hashCode(this.defaultDataOff) + ((((hashCode9 + ((hashCode8 + ((hashCode7 + ((hashCode6 + ((hashCode5 + ((hashCode4 + ((hashCode3 + ((hashCode2 + ((i5 + i2) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31) + this.dataState) * 31)) * 31)) * 31;
        ServiceState serviceState2 = this.serviceState;
        if (serviceState2 == null) {
            i3 = 0;
        } else {
            i3 = serviceState2.hashCode();
        }
        int i6 = (hashCode10 + i3) * 31;
        SignalStrength signalStrength2 = this.signalStrength;
        if (signalStrength2 != null) {
            i4 = signalStrength2.hashCode();
        }
        return i6 + i4;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0158, code lost:
        if (r2 != false) goto L_0x015a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x01f2, code lost:
        if (r2 == null) goto L_0x01f4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void toString(java.lang.StringBuilder r8) {
        /*
            r7 = this;
            super.toString(r8)
            r0 = 44
            r8.append(r0)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "dataSim="
            r1.append(r2)
            boolean r2 = r7.dataSim
            r1.append(r2)
            r1.append(r0)
            java.lang.String r1 = r1.toString()
            r8.append(r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "networkName="
            r1.append(r2)
            java.lang.String r2 = r7.networkName
            r1.append(r2)
            r1.append(r0)
            java.lang.String r1 = r1.toString()
            r8.append(r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "networkNameData="
            r1.append(r2)
            java.lang.String r2 = r7.networkNameData
            r1.append(r2)
            r1.append(r0)
            java.lang.String r1 = r1.toString()
            r8.append(r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "dataConnected="
            r1.append(r2)
            boolean r2 = r7.dataConnected
            r1.append(r2)
            r1.append(r0)
            java.lang.String r1 = r1.toString()
            r8.append(r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "roaming="
            r1.append(r2)
            boolean r2 = r7.roaming
            r1.append(r2)
            r1.append(r0)
            java.lang.String r1 = r1.toString()
            r8.append(r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "isDefault="
            r1.append(r2)
            boolean r2 = r7.isDefault
            r1.append(r2)
            r1.append(r0)
            java.lang.String r1 = r1.toString()
            r8.append(r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "isEmergency="
            r1.append(r2)
            boolean r2 = r7.isEmergency
            r1.append(r2)
            r1.append(r0)
            java.lang.String r1 = r1.toString()
            r8.append(r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "airplaneMode="
            r1.append(r2)
            boolean r2 = r7.airplaneMode
            r1.append(r2)
            r1.append(r0)
            java.lang.String r1 = r1.toString()
            r8.append(r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "carrierNetworkChangeMode="
            r1.append(r2)
            boolean r2 = r7.carrierNetworkChangeMode
            r1.append(r2)
            r1.append(r0)
            java.lang.String r1 = r1.toString()
            r8.append(r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "userSetup="
            r1.append(r2)
            boolean r2 = r7.userSetup
            r1.append(r2)
            r1.append(r0)
            java.lang.String r1 = r1.toString()
            r8.append(r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "dataState="
            r1.append(r2)
            int r2 = r7.dataState
            r1.append(r2)
            r1.append(r0)
            java.lang.String r1 = r1.toString()
            r8.append(r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "defaultDataOff="
            r1.append(r2)
            boolean r2 = r7.defaultDataOff
            r1.append(r2)
            r1.append(r0)
            java.lang.String r1 = r1.toString()
            r8.append(r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "showQuickSettingsRatIcon="
            r1.append(r2)
            boolean r2 = r7.dataConnected
            r3 = 0
            r4 = 1
            if (r2 != 0) goto L_0x015a
            com.android.settingslib.SignalIcon$IconGroup r2 = r7.iconGroup
            com.android.settingslib.SignalIcon$MobileIconGroup r5 = com.android.settingslib.mobile.TelephonyIcons.DATA_DISABLED
            if (r2 == r5) goto L_0x0151
            com.android.settingslib.SignalIcon$MobileIconGroup r5 = com.android.settingslib.mobile.TelephonyIcons.NOT_DEFAULT_DATA
            if (r2 != r5) goto L_0x0157
        L_0x0151:
            boolean r2 = r7.userSetup
            if (r2 == 0) goto L_0x0157
            r2 = r4
            goto L_0x0158
        L_0x0157:
            r2 = r3
        L_0x0158:
            if (r2 == 0) goto L_0x015b
        L_0x015a:
            r3 = r4
        L_0x015b:
            r1.append(r3)
            r1.append(r0)
            java.lang.String r1 = r1.toString()
            r8.append(r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "voiceServiceState="
            r1.append(r2)
            android.telephony.ServiceState r2 = r7.serviceState
            if (r2 != 0) goto L_0x0179
            r2 = -1
            goto L_0x017d
        L_0x0179:
            int r2 = r2.getState()
        L_0x017d:
            r1.append(r2)
            r1.append(r0)
            java.lang.String r1 = r1.toString()
            r8.append(r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "isInService="
            r1.append(r2)
            boolean r2 = r7.isInService()
            r1.append(r2)
            r1.append(r0)
            java.lang.String r1 = r1.toString()
            r8.append(r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "serviceState="
            r1.append(r2)
            android.telephony.ServiceState r2 = r7.serviceState
            r3 = 125(0x7d, float:1.75E-43)
            java.lang.String r4 = "(null)"
            if (r2 != 0) goto L_0x01b9
            goto L_0x01f4
        L_0x01b9:
            java.lang.String r5 = "serviceState={state="
            java.lang.StringBuilder r5 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r5)
            int r6 = r2.getState()
            r5.append(r6)
            java.lang.String r6 = ",isEmergencyOnly="
            r5.append(r6)
            boolean r6 = r2.isEmergencyOnly()
            r5.append(r6)
            java.lang.String r6 = ",roaming="
            r5.append(r6)
            boolean r6 = r2.getRoaming()
            r5.append(r6)
            java.lang.String r6 = ",operatorNameAlphaShort="
            r5.append(r6)
            java.lang.String r2 = r2.getOperatorAlphaShort()
            r5.append(r2)
            r5.append(r3)
            java.lang.String r2 = r5.toString()
            if (r2 != 0) goto L_0x01f5
        L_0x01f4:
            r2 = r4
        L_0x01f5:
            r1.append(r2)
            r1.append(r0)
            java.lang.String r1 = r1.toString()
            r8.append(r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "signalStrength="
            r1.append(r2)
            android.telephony.SignalStrength r2 = r7.signalStrength
            if (r2 != 0) goto L_0x0212
            goto L_0x0237
        L_0x0212:
            java.lang.String r5 = "signalStrength={isGsm="
            java.lang.StringBuilder r5 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r5)
            boolean r6 = r2.isGsm()
            r5.append(r6)
            java.lang.String r6 = ",level="
            r5.append(r6)
            int r2 = r2.getLevel()
            r5.append(r2)
            r5.append(r3)
            java.lang.String r2 = r5.toString()
            if (r2 != 0) goto L_0x0236
            goto L_0x0237
        L_0x0236:
            r4 = r2
        L_0x0237:
            r1.append(r4)
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            r8.append(r0)
            android.telephony.TelephonyDisplayInfo r7 = r7.telephonyDisplayInfo
            java.lang.String r0 = "displayInfo="
            java.lang.String r7 = kotlin.jvm.internal.Intrinsics.stringPlus(r0, r7)
            r8.append(r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.connectivity.MobileState.toString(java.lang.StringBuilder):void");
    }
}
