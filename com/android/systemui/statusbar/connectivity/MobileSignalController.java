package com.android.systemui.statusbar.connectivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.CarrierConfigManager;
import android.telephony.CellSignalStrength;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.ims.ImsMmTelManager;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline2;
import androidx.leanback.R$layout;
import com.android.internal.annotations.VisibleForTesting;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.SignalIcon$IconGroup;
import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.settingslib.graph.SignalDrawable;
import com.android.settingslib.mobile.MobileMappings;
import com.android.settingslib.mobile.MobileStatusTracker;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.systemui.util.CarrierConfigTracker;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public final class MobileSignalController extends SignalController<MobileState, SignalIcon$MobileIconGroup> {
    public static final SimpleDateFormat SSDF = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
    public final CarrierConfigTracker mCarrierConfigTracker;
    public MobileMappings.Config mConfig;
    public SignalIcon$MobileIconGroup mDefaultIcons;
    public final MobileStatusTracker.SubscriptionDefaults mDefaults;
    public final ImsMmTelManager mImsMmTelManager;
    public int mImsType = 1;
    @VisibleForTesting
    public boolean mInflateSignalStrengths = false;
    public int mLastLevel;
    public int mLastWlanCrossSimLevel;
    public int mLastWlanLevel;
    public int mLastWwanLevel;
    public final C11971 mMobileCallback;
    public final String[] mMobileStatusHistory = new String[64];
    public int mMobileStatusHistoryIndex;
    @VisibleForTesting
    public MobileStatusTracker mMobileStatusTracker;
    public final String mNetworkNameDefault;
    public final String mNetworkNameSeparator;
    public HashMap mNetworkToIconLookup;
    public final C11993 mObserver;
    public final TelephonyManager mPhone;
    public final boolean mProviderModelBehavior;
    public final Handler mReceiverHandler;
    public final C11982 mRegistrationCallback;
    public final SubscriptionInfo mSubscriptionInfo;
    public final C12004 mTryRegisterIms;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MobileSignalController(android.content.Context r11, com.android.settingslib.mobile.MobileMappings.Config r12, boolean r13, android.telephony.TelephonyManager r14, com.android.systemui.statusbar.connectivity.CallbackHandler r15, com.android.systemui.statusbar.connectivity.NetworkControllerImpl r16, android.telephony.SubscriptionInfo r17, com.android.settingslib.mobile.MobileStatusTracker.SubscriptionDefaults r18, android.os.Looper r19, com.android.systemui.util.CarrierConfigTracker r20, com.android.systemui.flags.FeatureFlags r21) {
        /*
            r10 = this;
            r6 = r10
            r7 = r13
            r8 = r19
            java.lang.String r0 = "MobileSignalController("
            java.lang.StringBuilder r0 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r0)
            int r1 = r17.getSubscriptionId()
            r0.append(r1)
            java.lang.String r1 = ")"
            r0.append(r1)
            java.lang.String r1 = r0.toString()
            r3 = 0
            r0 = r10
            r2 = r11
            r4 = r15
            r5 = r16
            r0.<init>(r1, r2, r3, r4, r5)
            r0 = 1
            r6.mImsType = r0
            r0 = 0
            r6.mInflateSignalStrengths = r0
            r0 = 64
            java.lang.String[] r0 = new java.lang.String[r0]
            r6.mMobileStatusHistory = r0
            com.android.systemui.statusbar.connectivity.MobileSignalController$1 r5 = new com.android.systemui.statusbar.connectivity.MobileSignalController$1
            r5.<init>()
            r6.mMobileCallback = r5
            com.android.systemui.statusbar.connectivity.MobileSignalController$2 r0 = new com.android.systemui.statusbar.connectivity.MobileSignalController$2
            r0.<init>()
            r6.mRegistrationCallback = r0
            com.android.systemui.statusbar.connectivity.MobileSignalController$4 r0 = new com.android.systemui.statusbar.connectivity.MobileSignalController$4
            r0.<init>()
            r6.mTryRegisterIms = r0
            r0 = r20
            r6.mCarrierConfigTracker = r0
            r0 = r12
            r6.mConfig = r0
            r1 = r14
            r6.mPhone = r1
            r4 = r18
            r6.mDefaults = r4
            r3 = r17
            r6.mSubscriptionInfo = r3
            r0 = 2131953318(0x7f1306a6, float:1.9543104E38)
            java.lang.CharSequence r0 = r10.getTextIfExists(r0)
            java.lang.String r0 = r0.toString()
            r6.mNetworkNameSeparator = r0
            r0 = 17040588(0x10404cc, float:2.4248013E-38)
            java.lang.CharSequence r0 = r10.getTextIfExists(r0)
            java.lang.String r0 = r0.toString()
            r6.mNetworkNameDefault = r0
            android.os.Handler r2 = new android.os.Handler
            r2.<init>(r8)
            r6.mReceiverHandler = r2
            com.android.settingslib.mobile.MobileMappings$Config r2 = r6.mConfig
            java.util.HashMap r2 = com.android.settingslib.mobile.MobileMappings.mapIconSets(r2)
            r6.mNetworkToIconLookup = r2
            com.android.settingslib.mobile.MobileMappings$Config r2 = r6.mConfig
            boolean r2 = r2.showAtLeast3G
            if (r2 != 0) goto L_0x0088
            com.android.settingslib.SignalIcon$MobileIconGroup r2 = com.android.settingslib.mobile.TelephonyIcons.f34G
            goto L_0x008a
        L_0x0088:
            com.android.settingslib.SignalIcon$MobileIconGroup r2 = com.android.settingslib.mobile.TelephonyIcons.THREE_G
        L_0x008a:
            r6.mDefaultIcons = r2
            java.lang.CharSequence r2 = r17.getCarrierName()
            if (r2 == 0) goto L_0x009a
            java.lang.CharSequence r0 = r17.getCarrierName()
            java.lang.String r0 = r0.toString()
        L_0x009a:
            T r2 = r6.mLastState
            com.android.systemui.statusbar.connectivity.MobileState r2 = (com.android.systemui.statusbar.connectivity.MobileState) r2
            T r9 = r6.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r9 = (com.android.systemui.statusbar.connectivity.MobileState) r9
            r9.networkName = r0
            r2.networkName = r0
            r9.networkNameData = r0
            r2.networkNameData = r0
            r9.enabled = r7
            r2.enabled = r7
            com.android.settingslib.SignalIcon$MobileIconGroup r0 = r6.mDefaultIcons
            r9.iconGroup = r0
            r2.iconGroup = r0
            com.android.systemui.statusbar.connectivity.MobileSignalController$3 r0 = new com.android.systemui.statusbar.connectivity.MobileSignalController$3
            android.os.Handler r2 = new android.os.Handler
            r2.<init>(r8)
            r0.<init>(r2)
            r6.mObserver = r0
            int r0 = r17.getSubscriptionId()
            android.telephony.ims.ImsMmTelManager r0 = android.telephony.ims.ImsMmTelManager.createForSubscriptionId(r0)
            r6.mImsMmTelManager = r0
            com.android.settingslib.mobile.MobileStatusTracker r7 = new com.android.settingslib.mobile.MobileStatusTracker
            r0 = r7
            r1 = r14
            r2 = r19
            r3 = r17
            r4 = r18
            r0.<init>(r1, r2, r3, r4, r5)
            r6.mMobileStatusTracker = r7
            com.android.systemui.flags.BooleanFlag r0 = com.android.systemui.flags.Flags.COMBINED_STATUS_BAR_SIGNAL_ICONS
            r1 = r21
            boolean r0 = r1.isEnabled((com.android.systemui.flags.BooleanFlag) r0)
            r6.mProviderModelBehavior = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.connectivity.MobileSignalController.<init>(android.content.Context, com.android.settingslib.mobile.MobileMappings$Config, boolean, android.telephony.TelephonyManager, com.android.systemui.statusbar.connectivity.CallbackHandler, com.android.systemui.statusbar.connectivity.NetworkControllerImpl, android.telephony.SubscriptionInfo, com.android.settingslib.mobile.MobileStatusTracker$SubscriptionDefaults, android.os.Looper, com.android.systemui.util.CarrierConfigTracker, com.android.systemui.flags.FeatureFlags):void");
    }

    public final int getSignalLevel(SignalStrength signalStrength) {
        if (signalStrength == null) {
            return 0;
        }
        if (signalStrength.isGsm() || !this.mConfig.alwaysShowCdmaRssi) {
            return signalStrength.getLevel();
        }
        List<CellSignalStrengthCdma> cellSignalStrengths = signalStrength.getCellSignalStrengths(CellSignalStrengthCdma.class);
        if (!cellSignalStrengths.isEmpty()) {
            return cellSignalStrengths.get(0).getLevel();
        }
        return 0;
    }

    public static final class QsInfo {
        public final CharSequence description;
        public final IconState icon;
        public final int ratTypeIcon;

        public QsInfo(int i, IconState iconState, String str) {
            this.ratTypeIcon = i;
            this.icon = iconState;
            this.description = str;
        }
    }

    public static int getCallStrengthIcon(int i, boolean z) {
        if (z) {
            return TelephonyIcons.WIFI_CALL_STRENGTH_ICONS[i];
        }
        return TelephonyIcons.MOBILE_CALL_STRENGTH_ICONS[i];
    }

    public final void checkDefaultData() {
        MobileState mobileState = (MobileState) this.mCurrentState;
        boolean z = false;
        if (mobileState.iconGroup != TelephonyIcons.NOT_DEFAULT_DATA) {
            mobileState.defaultDataOff = false;
            return;
        }
        NetworkControllerImpl networkControllerImpl = this.mNetworkController;
        Objects.requireNonNull(networkControllerImpl);
        Objects.requireNonNull(networkControllerImpl.mSubDefaults);
        MobileSignalController controllerWithSubId = networkControllerImpl.getControllerWithSubId(SubscriptionManager.getActiveDataSubscriptionId());
        if (controllerWithSubId != null) {
            z = !controllerWithSubId.mPhone.isDataConnectionAllowed();
        }
        mobileState.defaultDataOff = z;
    }

    public final ConnectivityState cleanState() {
        return new MobileState();
    }

    public final String getCallStrengthDescription(int i, boolean z) {
        if (z) {
            return getTextIfExists(R$layout.WIFI_CONNECTION_STRENGTH[i]).toString();
        }
        return getTextIfExists(R$layout.PHONE_SIGNAL_STRENGTH[i]).toString();
    }

    public final int getCurrentIconId() {
        int i;
        boolean z;
        boolean z2;
        boolean z3;
        int i2;
        int i3;
        MobileState mobileState = (MobileState) this.mCurrentState;
        SignalIcon$IconGroup signalIcon$IconGroup = mobileState.iconGroup;
        if (signalIcon$IconGroup == TelephonyIcons.CARRIER_NETWORK_CHANGE) {
            if (this.mInflateSignalStrengths) {
                i3 = CellSignalStrength.getNumSignalStrengthLevels() + 1;
            } else {
                i3 = CellSignalStrength.getNumSignalStrengthLevels();
            }
            int i4 = SignalDrawable.$r8$clinit;
            return (i3 << 8) | 196608;
        }
        int i5 = 0;
        if (mobileState.connected) {
            int i6 = mobileState.level;
            boolean z4 = this.mInflateSignalStrengths;
            if (z4) {
                i6++;
            }
            if (!mobileState.userSetup || (signalIcon$IconGroup != TelephonyIcons.DATA_DISABLED && (signalIcon$IconGroup != TelephonyIcons.NOT_DEFAULT_DATA || !mobileState.defaultDataOff))) {
                z = false;
            } else {
                z = true;
            }
            if (mobileState.inetCondition == 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z || z2) {
                z3 = true;
            } else {
                z3 = false;
            }
            if (z4) {
                i2 = CellSignalStrength.getNumSignalStrengthLevels() + 1;
            } else {
                i2 = CellSignalStrength.getNumSignalStrengthLevels();
            }
            int i7 = SignalDrawable.$r8$clinit;
            if (z3) {
                i5 = 2;
            }
            return (i2 << 8) | (i5 << 16) | i6;
        } else if (!mobileState.enabled) {
            return 0;
        } else {
            if (this.mInflateSignalStrengths) {
                i = CellSignalStrength.getNumSignalStrengthLevels() + 1;
            } else {
                i = CellSignalStrength.getNumSignalStrengthLevels();
            }
            int i8 = SignalDrawable.$r8$clinit;
            return (i << 8) | 131072 | 0;
        }
    }

    public final boolean hideNoCalling() {
        boolean z;
        NetworkControllerImpl networkControllerImpl = this.mNetworkController;
        Objects.requireNonNull(networkControllerImpl);
        if (!networkControllerImpl.mNoDefaultNetwork) {
            CarrierConfigTracker carrierConfigTracker = this.mCarrierConfigTracker;
            int subscriptionId = this.mSubscriptionInfo.getSubscriptionId();
            Objects.requireNonNull(carrierConfigTracker);
            synchronized (carrierConfigTracker.mNoCallingConfigs) {
                if (carrierConfigTracker.mNoCallingConfigs.indexOfKey(subscriptionId) >= 0) {
                    z = carrierConfigTracker.mNoCallingConfigs.get(subscriptionId);
                } else {
                    if (!carrierConfigTracker.mDefaultNoCallingConfigLoaded) {
                        carrierConfigTracker.mDefaultNoCallingConfig = CarrierConfigManager.getDefaultConfig().getBoolean("use_ip_for_calling_indicator_bool");
                        carrierConfigTracker.mDefaultNoCallingConfigLoaded = true;
                    }
                    z = carrierConfigTracker.mDefaultNoCallingConfig;
                }
            }
            if (z) {
                return true;
            }
        }
        return false;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v0, resolved type: com.android.systemui.statusbar.connectivity.IconState} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v7, resolved type: com.android.systemui.statusbar.connectivity.IconState} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v27, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v28, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v8, resolved type: com.android.systemui.statusbar.connectivity.IconState} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v33, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v9, resolved type: com.android.systemui.statusbar.connectivity.IconState} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v10, resolved type: com.android.systemui.statusbar.connectivity.IconState} */
    /* JADX WARNING: type inference failed for: r6v34, types: [java.lang.String] */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x015e, code lost:
        if (r2.airplaneMode == false) goto L_0x0160;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x0122, code lost:
        if (r0.mCurrentState.airplaneMode == false) goto L_0x0160;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x0181  */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x0183  */
    /* JADX WARNING: Removed duplicated region for block: B:117:0x0197  */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x0199  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00a6  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00a8  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00ba  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00df  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00e1  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00e6  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0125  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void notifyListeners(com.android.systemui.statusbar.connectivity.SignalCallback r20) {
        /*
            r19 = this;
            r0 = r19
            com.android.systemui.statusbar.connectivity.NetworkControllerImpl r1 = r0.mNetworkController
            android.telephony.SubscriptionInfo r2 = r0.mSubscriptionInfo
            int r2 = r2.getSubscriptionId()
            java.util.Objects.requireNonNull(r1)
            com.android.systemui.statusbar.connectivity.WifiSignalController r1 = r1.mWifiSignalController
            java.util.Objects.requireNonNull(r1)
            T r1 = r1.mCurrentState
            com.android.systemui.statusbar.connectivity.WifiState r1 = (com.android.systemui.statusbar.connectivity.WifiState) r1
            boolean r3 = r1.isDefault
            r4 = 0
            r5 = 1
            if (r3 == 0) goto L_0x0026
            boolean r3 = r1.isCarrierMerged
            if (r3 == 0) goto L_0x0026
            int r1 = r1.subId
            if (r1 != r2) goto L_0x0026
            r1 = r5
            goto L_0x0027
        L_0x0026:
            r1 = r4
        L_0x0027:
            if (r1 == 0) goto L_0x002a
            return
        L_0x002a:
            T r1 = r0.mCurrentState
            com.android.settingslib.SignalIcon$IconGroup r1 = r1.iconGroup
            com.android.settingslib.SignalIcon$MobileIconGroup r1 = (com.android.settingslib.SignalIcon$MobileIconGroup) r1
            int r2 = r19.getContentDescription()
            java.lang.CharSequence r2 = r0.getTextIfExists(r2)
            java.lang.String r2 = r2.toString()
            int r3 = r1.dataContentDescription
            java.lang.CharSequence r14 = r0.getTextIfExists(r3)
            java.lang.String r3 = r14.toString()
            android.text.Spanned r3 = android.text.Html.fromHtml(r3, r4)
            java.lang.String r3 = r3.toString()
            T r6 = r0.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r6 = (com.android.systemui.statusbar.connectivity.MobileState) r6
            int r6 = r6.inetCondition
            if (r6 != 0) goto L_0x005f
            android.content.Context r3 = r0.mContext
            r6 = 2131952235(0x7f13026b, float:1.9540907E38)
            java.lang.String r3 = r3.getString(r6)
        L_0x005f:
            r13 = r3
            int r3 = r1.dataType
            T r6 = r0.mCurrentState
            r7 = r6
            com.android.systemui.statusbar.connectivity.MobileState r7 = (com.android.systemui.statusbar.connectivity.MobileState) r7
            boolean r8 = r7.dataSim
            r9 = 0
            if (r8 == 0) goto L_0x00c0
            boolean r8 = r7.isDefault
            if (r8 != 0) goto L_0x0077
            com.android.systemui.statusbar.connectivity.MobileSignalController$QsInfo r3 = new com.android.systemui.statusbar.connectivity.MobileSignalController$QsInfo
            r3.<init>(r4, r9, r9)
            goto L_0x00c8
        L_0x0077:
            boolean r8 = r7.dataConnected
            if (r8 != 0) goto L_0x0091
            com.android.settingslib.SignalIcon$IconGroup r8 = r7.iconGroup
            com.android.settingslib.SignalIcon$MobileIconGroup r10 = com.android.settingslib.mobile.TelephonyIcons.DATA_DISABLED
            if (r8 == r10) goto L_0x0085
            com.android.settingslib.SignalIcon$MobileIconGroup r10 = com.android.settingslib.mobile.TelephonyIcons.NOT_DEFAULT_DATA
            if (r8 != r10) goto L_0x008b
        L_0x0085:
            boolean r7 = r7.userSetup
            if (r7 == 0) goto L_0x008b
            r7 = r5
            goto L_0x008c
        L_0x008b:
            r7 = r4
        L_0x008c:
            if (r7 == 0) goto L_0x008f
            goto L_0x0091
        L_0x008f:
            r7 = r4
            goto L_0x0092
        L_0x0091:
            r7 = r5
        L_0x0092:
            if (r7 != 0) goto L_0x009c
            com.android.settingslib.mobile.MobileMappings$Config r7 = r0.mConfig
            boolean r7 = r7.alwaysShowDataRatIcon
            if (r7 == 0) goto L_0x009b
            goto L_0x009c
        L_0x009b:
            r3 = r4
        L_0x009c:
            com.android.systemui.statusbar.connectivity.MobileState r6 = (com.android.systemui.statusbar.connectivity.MobileState) r6
            boolean r7 = r6.enabled
            if (r7 == 0) goto L_0x00a8
            boolean r6 = r6.isEmergency
            if (r6 != 0) goto L_0x00a8
            r6 = r5
            goto L_0x00a9
        L_0x00a8:
            r6 = r4
        L_0x00a9:
            com.android.systemui.statusbar.connectivity.IconState r7 = new com.android.systemui.statusbar.connectivity.IconState
            int r8 = r19.getCurrentIconId()
            r7.<init>(r6, r8, r2)
            T r6 = r0.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r6 = (com.android.systemui.statusbar.connectivity.MobileState) r6
            boolean r8 = r6.isEmergency
            if (r8 != 0) goto L_0x00bd
            java.lang.String r6 = r6.networkName
            r9 = r6
        L_0x00bd:
            r6 = r9
            r9 = r7
            goto L_0x00c2
        L_0x00c0:
            r3 = r4
            r6 = r9
        L_0x00c2:
            com.android.systemui.statusbar.connectivity.MobileSignalController$QsInfo r7 = new com.android.systemui.statusbar.connectivity.MobileSignalController$QsInfo
            r7.<init>(r3, r9, r6)
            r3 = r7
        L_0x00c8:
            int r1 = r1.dataType
            T r6 = r0.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r6 = (com.android.systemui.statusbar.connectivity.MobileState) r6
            java.util.Objects.requireNonNull(r6)
            com.android.settingslib.SignalIcon$IconGroup r7 = r6.iconGroup
            com.android.settingslib.SignalIcon$MobileIconGroup r8 = com.android.settingslib.mobile.TelephonyIcons.DATA_DISABLED
            if (r7 == r8) goto L_0x00db
            com.android.settingslib.SignalIcon$MobileIconGroup r8 = com.android.settingslib.mobile.TelephonyIcons.NOT_DEFAULT_DATA
            if (r7 != r8) goto L_0x00e1
        L_0x00db:
            boolean r6 = r6.userSetup
            if (r6 == 0) goto L_0x00e1
            r6 = r5
            goto L_0x00e2
        L_0x00e1:
            r6 = r4
        L_0x00e2:
            boolean r7 = r0.mProviderModelBehavior
            if (r7 == 0) goto L_0x0125
            T r7 = r0.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r7 = (com.android.systemui.statusbar.connectivity.MobileState) r7
            boolean r8 = r7.dataConnected
            if (r8 != 0) goto L_0x00f0
            if (r6 == 0) goto L_0x00fa
        L_0x00f0:
            boolean r6 = r7.dataSim
            if (r6 == 0) goto L_0x00fa
            boolean r6 = r7.isDefault
            if (r6 == 0) goto L_0x00fa
            r6 = r5
            goto L_0x00fb
        L_0x00fa:
            r6 = r4
        L_0x00fb:
            if (r6 != 0) goto L_0x0105
            com.android.settingslib.mobile.MobileMappings$Config r8 = r0.mConfig
            boolean r8 = r8.alwaysShowDataRatIcon
            if (r8 == 0) goto L_0x0104
            goto L_0x0105
        L_0x0104:
            r1 = r4
        L_0x0105:
            boolean r8 = r7.roaming
            r6 = r6 | r8
            com.android.systemui.statusbar.connectivity.IconState r8 = new com.android.systemui.statusbar.connectivity.IconState
            if (r6 == 0) goto L_0x0112
            boolean r7 = r7.airplaneMode
            if (r7 != 0) goto L_0x0112
            r7 = r5
            goto L_0x0113
        L_0x0112:
            r7 = r4
        L_0x0113:
            int r9 = r19.getCurrentIconId()
            r8.<init>(r7, r9, r2)
            if (r6 == 0) goto L_0x0164
            T r2 = r0.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r2 = (com.android.systemui.statusbar.connectivity.MobileState) r2
            boolean r2 = r2.airplaneMode
            if (r2 != 0) goto L_0x0164
            goto L_0x0160
        L_0x0125:
            com.android.systemui.statusbar.connectivity.IconState r8 = new com.android.systemui.statusbar.connectivity.IconState
            T r7 = r0.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r7 = (com.android.systemui.statusbar.connectivity.MobileState) r7
            boolean r9 = r7.enabled
            if (r9 == 0) goto L_0x0135
            boolean r7 = r7.airplaneMode
            if (r7 != 0) goto L_0x0135
            r7 = r5
            goto L_0x0136
        L_0x0135:
            r7 = r4
        L_0x0136:
            int r9 = r19.getCurrentIconId()
            r8.<init>(r7, r9, r2)
            T r2 = r0.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r2 = (com.android.systemui.statusbar.connectivity.MobileState) r2
            boolean r7 = r2.dataConnected
            if (r7 == 0) goto L_0x0149
            boolean r7 = r2.isDefault
            if (r7 != 0) goto L_0x014b
        L_0x0149:
            if (r6 == 0) goto L_0x014d
        L_0x014b:
            r6 = r5
            goto L_0x014e
        L_0x014d:
            r6 = r4
        L_0x014e:
            if (r6 != 0) goto L_0x0158
            com.android.settingslib.mobile.MobileMappings$Config r6 = r0.mConfig
            boolean r6 = r6.alwaysShowDataRatIcon
            if (r6 == 0) goto L_0x0157
            goto L_0x0158
        L_0x0157:
            r1 = r4
        L_0x0158:
            boolean r6 = r2.enabled
            if (r6 == 0) goto L_0x0164
            boolean r2 = r2.airplaneMode
            if (r2 != 0) goto L_0x0164
        L_0x0160:
            r9 = r1
            r18 = r5
            goto L_0x0167
        L_0x0164:
            r9 = r1
            r18 = r4
        L_0x0167:
            r7 = r8
            com.android.systemui.statusbar.connectivity.MobileDataIndicators r1 = new com.android.systemui.statusbar.connectivity.MobileDataIndicators
            com.android.systemui.statusbar.connectivity.IconState r8 = r3.icon
            int r10 = r3.ratTypeIcon
            T r2 = r0.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r2 = (com.android.systemui.statusbar.connectivity.MobileState) r2
            java.util.Objects.requireNonNull(r2)
            boolean r6 = r2.dataConnected
            if (r6 == 0) goto L_0x0183
            boolean r6 = r2.carrierNetworkChangeMode
            if (r6 != 0) goto L_0x0183
            boolean r2 = r2.activityIn
            if (r2 == 0) goto L_0x0183
            r11 = r5
            goto L_0x0184
        L_0x0183:
            r11 = r4
        L_0x0184:
            T r2 = r0.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r2 = (com.android.systemui.statusbar.connectivity.MobileState) r2
            java.util.Objects.requireNonNull(r2)
            boolean r6 = r2.dataConnected
            if (r6 == 0) goto L_0x0199
            boolean r6 = r2.carrierNetworkChangeMode
            if (r6 != 0) goto L_0x0199
            boolean r2 = r2.activityOut
            if (r2 == 0) goto L_0x0199
            r12 = r5
            goto L_0x019a
        L_0x0199:
            r12 = r4
        L_0x019a:
            java.lang.CharSequence r15 = r3.description
            android.telephony.SubscriptionInfo r2 = r0.mSubscriptionInfo
            int r16 = r2.getSubscriptionId()
            T r0 = r0.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r0 = (com.android.systemui.statusbar.connectivity.MobileState) r0
            boolean r0 = r0.roaming
            r6 = r1
            r17 = r0
            r6.<init>(r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18)
            r0 = r20
            r0.setMobileDataIndicators(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.connectivity.MobileSignalController.notifyListeners(com.android.systemui.statusbar.connectivity.SignalCallback):void");
    }

    public final void refreshCallIndicator(SignalCallback signalCallback) {
        boolean z;
        MobileState mobileState = (MobileState) this.mCurrentState;
        Objects.requireNonNull(mobileState);
        ServiceState serviceState = mobileState.serviceState;
        if (serviceState != null && serviceState.getState() == 0) {
            z = true;
        } else {
            z = false;
        }
        IconState iconState = new IconState((!z) & (!hideNoCalling()), C1777R.C1778drawable.ic_qs_no_calling_sms, getTextIfExists(C1777R.string.accessibility_no_calling).toString());
        signalCallback.setCallIndicator(iconState, this.mSubscriptionInfo.getSubscriptionId());
        int i = this.mImsType;
        if (i == 1) {
            iconState = new IconState(true, getCallStrengthIcon(this.mLastWwanLevel, false), getCallStrengthDescription(this.mLastWwanLevel, false));
        } else if (i == 2) {
            iconState = new IconState(true, getCallStrengthIcon(this.mLastWlanLevel, true), getCallStrengthDescription(this.mLastWlanLevel, true));
        } else if (i == 3) {
            iconState = new IconState(true, getCallStrengthIcon(this.mLastWlanCrossSimLevel, false), getCallStrengthDescription(this.mLastWlanCrossSimLevel, false));
        }
        signalCallback.setCallIndicator(iconState, this.mSubscriptionInfo.getSubscriptionId());
    }

    public final void registerListener() {
        this.mMobileStatusTracker.setListening(true);
        this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("mobile_data"), true, this.mObserver);
        ContentResolver contentResolver = this.mContext.getContentResolver();
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("mobile_data");
        m.append(this.mSubscriptionInfo.getSubscriptionId());
        contentResolver.registerContentObserver(Settings.Global.getUriFor(m.toString()), true, this.mObserver);
        if (this.mProviderModelBehavior) {
            this.mReceiverHandler.post(this.mTryRegisterIms);
        }
    }

    @VisibleForTesting
    public void setActivity(int i) {
        boolean z;
        T t = this.mCurrentState;
        MobileState mobileState = (MobileState) t;
        boolean z2 = false;
        if (i == 3 || i == 1) {
            z = true;
        } else {
            z = false;
        }
        mobileState.activityIn = z;
        MobileState mobileState2 = (MobileState) t;
        if (i == 3 || i == 2) {
            z2 = true;
        }
        mobileState2.activityOut = z2;
        notifyListenersIfNecessary();
    }

    public final void updateConnectivity(BitSet bitSet, BitSet bitSet2) {
        int i;
        boolean z = bitSet2.get(this.mTransportType);
        ((MobileState) this.mCurrentState).isDefault = bitSet.get(this.mTransportType);
        MobileState mobileState = (MobileState) this.mCurrentState;
        if (z || !mobileState.isDefault) {
            i = 1;
        } else {
            i = 0;
        }
        mobileState.inetCondition = i;
        notifyListenersIfNecessary();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00d7, code lost:
        if (r5.mPhone.getCdmaEnhancedRoamingIndicatorDisplayNumber() != 1) goto L_0x00eb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00e9, code lost:
        if (r0.getRoaming() != false) goto L_0x00eb;
     */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00f9  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0100  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0142  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0144  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x014d  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0196  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateTelephony() {
        /*
            r5 = this;
            java.lang.String r0 = r5.mTag
            r1 = 3
            boolean r0 = android.util.Log.isLoggable(r0, r1)
            if (r0 == 0) goto L_0x0040
            java.lang.String r0 = r5.mTag
            java.lang.String r1 = "updateTelephonySignalStrength: hasService="
            java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
            T r2 = r5.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r2 = (com.android.systemui.statusbar.connectivity.MobileState) r2
            boolean r2 = r2.isInService()
            r1.append(r2)
            java.lang.String r2 = " ss="
            r1.append(r2)
            T r2 = r5.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r2 = (com.android.systemui.statusbar.connectivity.MobileState) r2
            android.telephony.SignalStrength r2 = r2.signalStrength
            r1.append(r2)
            java.lang.String r2 = " displayInfo="
            r1.append(r2)
            T r2 = r5.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r2 = (com.android.systemui.statusbar.connectivity.MobileState) r2
            android.telephony.TelephonyDisplayInfo r2 = r2.telephonyDisplayInfo
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Log.d(r0, r1)
        L_0x0040:
            r5.checkDefaultData()
            T r0 = r5.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r0 = (com.android.systemui.statusbar.connectivity.MobileState) r0
            boolean r1 = r0.isInService()
            r0.connected = r1
            T r0 = r5.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r0 = (com.android.systemui.statusbar.connectivity.MobileState) r0
            boolean r1 = r0.connected
            if (r1 == 0) goto L_0x005d
            android.telephony.SignalStrength r1 = r0.signalStrength
            int r1 = r5.getSignalLevel(r1)
            r0.level = r1
        L_0x005d:
            T r0 = r5.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r0 = (com.android.systemui.statusbar.connectivity.MobileState) r0
            android.telephony.TelephonyDisplayInfo r0 = r0.telephonyDisplayInfo
            int r1 = r0.getOverrideNetworkType()
            if (r1 != 0) goto L_0x0072
            int r0 = r0.getNetworkType()
            java.lang.String r0 = java.lang.Integer.toString(r0)
            goto L_0x007a
        L_0x0072:
            int r0 = r0.getOverrideNetworkType()
            java.lang.String r0 = com.android.settingslib.mobile.MobileMappings.toDisplayIconKey(r0)
        L_0x007a:
            java.util.HashMap r1 = r5.mNetworkToIconLookup
            java.lang.Object r1 = r1.get(r0)
            if (r1 == 0) goto L_0x0091
            T r1 = r5.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r1 = (com.android.systemui.statusbar.connectivity.MobileState) r1
            java.util.HashMap r2 = r5.mNetworkToIconLookup
            java.lang.Object r0 = r2.get(r0)
            com.android.settingslib.SignalIcon$IconGroup r0 = (com.android.settingslib.SignalIcon$IconGroup) r0
            r1.iconGroup = r0
            goto L_0x0099
        L_0x0091:
            T r0 = r5.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r0 = (com.android.systemui.statusbar.connectivity.MobileState) r0
            com.android.settingslib.SignalIcon$MobileIconGroup r1 = r5.mDefaultIcons
            r0.iconGroup = r1
        L_0x0099:
            T r0 = r5.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r0 = (com.android.systemui.statusbar.connectivity.MobileState) r0
            java.util.Objects.requireNonNull(r0)
            boolean r1 = r0.connected
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L_0x00ad
            int r1 = r0.dataState
            r4 = 2
            if (r1 != r4) goto L_0x00ad
            r1 = r3
            goto L_0x00ae
        L_0x00ad:
            r1 = r2
        L_0x00ae:
            r0.dataConnected = r1
            T r0 = r5.mCurrentState
            r1 = r0
            com.android.systemui.statusbar.connectivity.MobileState r1 = (com.android.systemui.statusbar.connectivity.MobileState) r1
            r4 = r0
            com.android.systemui.statusbar.connectivity.MobileState r4 = (com.android.systemui.statusbar.connectivity.MobileState) r4
            boolean r4 = r4.carrierNetworkChangeMode
            if (r4 == 0) goto L_0x00bd
            goto L_0x00ed
        L_0x00bd:
            com.android.systemui.statusbar.connectivity.MobileState r0 = (com.android.systemui.statusbar.connectivity.MobileState) r0
            java.util.Objects.requireNonNull(r0)
            android.telephony.SignalStrength r0 = r0.signalStrength
            if (r0 == 0) goto L_0x00ce
            boolean r0 = r0.isGsm()
            if (r0 != 0) goto L_0x00ce
            r0 = r3
            goto L_0x00cf
        L_0x00ce:
            r0 = r2
        L_0x00cf:
            if (r0 == 0) goto L_0x00da
            android.telephony.TelephonyManager r0 = r5.mPhone
            int r0 = r0.getCdmaEnhancedRoamingIndicatorDisplayNumber()
            if (r0 == r3) goto L_0x00ed
            goto L_0x00eb
        L_0x00da:
            T r0 = r5.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r0 = (com.android.systemui.statusbar.connectivity.MobileState) r0
            java.util.Objects.requireNonNull(r0)
            android.telephony.ServiceState r0 = r0.serviceState
            if (r0 == 0) goto L_0x00ed
            boolean r0 = r0.getRoaming()
            if (r0 == 0) goto L_0x00ed
        L_0x00eb:
            r0 = r3
            goto L_0x00ee
        L_0x00ed:
            r0 = r2
        L_0x00ee:
            r1.roaming = r0
            T r0 = r5.mCurrentState
            r1 = r0
            com.android.systemui.statusbar.connectivity.MobileState r1 = (com.android.systemui.statusbar.connectivity.MobileState) r1
            boolean r1 = r1.carrierNetworkChangeMode
            if (r1 == 0) goto L_0x0100
            com.android.systemui.statusbar.connectivity.MobileState r0 = (com.android.systemui.statusbar.connectivity.MobileState) r0
            com.android.settingslib.SignalIcon$MobileIconGroup r1 = com.android.settingslib.mobile.TelephonyIcons.CARRIER_NETWORK_CHANGE
            r0.iconGroup = r1
            goto L_0x0131
        L_0x0100:
            android.telephony.TelephonyManager r0 = r5.mPhone
            boolean r0 = r0.isDataConnectionAllowed()
            r0 = r0 ^ r3
            if (r0 == 0) goto L_0x0131
            com.android.settingslib.mobile.MobileMappings$Config r0 = r5.mConfig
            boolean r0 = r0.alwaysShowDataRatIcon
            if (r0 != 0) goto L_0x0131
            android.telephony.SubscriptionInfo r0 = r5.mSubscriptionInfo
            int r0 = r0.getSubscriptionId()
            com.android.settingslib.mobile.MobileStatusTracker$SubscriptionDefaults r1 = r5.mDefaults
            java.util.Objects.requireNonNull(r1)
            int r1 = android.telephony.SubscriptionManager.getDefaultDataSubscriptionId()
            if (r0 == r1) goto L_0x0129
            T r0 = r5.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r0 = (com.android.systemui.statusbar.connectivity.MobileState) r0
            com.android.settingslib.SignalIcon$MobileIconGroup r1 = com.android.settingslib.mobile.TelephonyIcons.NOT_DEFAULT_DATA
            r0.iconGroup = r1
            goto L_0x0131
        L_0x0129:
            T r0 = r5.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r0 = (com.android.systemui.statusbar.connectivity.MobileState) r0
            com.android.settingslib.SignalIcon$MobileIconGroup r1 = com.android.settingslib.mobile.TelephonyIcons.DATA_DISABLED
            r0.iconGroup = r1
        L_0x0131:
            T r0 = r5.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r0 = (com.android.systemui.statusbar.connectivity.MobileState) r0
            java.util.Objects.requireNonNull(r0)
            android.telephony.ServiceState r0 = r0.serviceState
            if (r0 == 0) goto L_0x0144
            boolean r0 = r0.isEmergencyOnly()
            if (r0 == 0) goto L_0x0144
            r0 = r3
            goto L_0x0145
        L_0x0144:
            r0 = r2
        L_0x0145:
            T r1 = r5.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r1 = (com.android.systemui.statusbar.connectivity.MobileState) r1
            boolean r4 = r1.isEmergency
            if (r0 == r4) goto L_0x0162
            java.util.Objects.requireNonNull(r1)
            android.telephony.ServiceState r0 = r1.serviceState
            if (r0 == 0) goto L_0x015b
            boolean r0 = r0.isEmergencyOnly()
            if (r0 == 0) goto L_0x015b
            r2 = r3
        L_0x015b:
            r1.isEmergency = r2
            com.android.systemui.statusbar.connectivity.NetworkControllerImpl r0 = r5.mNetworkController
            r0.recalculateEmergency()
        L_0x0162:
            T r0 = r5.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r0 = (com.android.systemui.statusbar.connectivity.MobileState) r0
            java.lang.String r0 = r0.networkName
            java.lang.String r1 = r5.mNetworkNameDefault
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0188
            T r0 = r5.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r0 = (com.android.systemui.statusbar.connectivity.MobileState) r0
            java.lang.String r0 = r0.getOperatorAlphaShort()
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x0188
            T r0 = r5.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r0 = (com.android.systemui.statusbar.connectivity.MobileState) r0
            java.lang.String r1 = r0.getOperatorAlphaShort()
            r0.networkName = r1
        L_0x0188:
            T r0 = r5.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r0 = (com.android.systemui.statusbar.connectivity.MobileState) r0
            java.lang.String r0 = r0.networkNameData
            java.lang.String r1 = r5.mNetworkNameDefault
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x01b2
            T r0 = r5.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r0 = (com.android.systemui.statusbar.connectivity.MobileState) r0
            boolean r1 = r0.dataSim
            if (r1 == 0) goto L_0x01b2
            java.lang.String r0 = r0.getOperatorAlphaShort()
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x01b2
            T r0 = r5.mCurrentState
            com.android.systemui.statusbar.connectivity.MobileState r0 = (com.android.systemui.statusbar.connectivity.MobileState) r0
            java.lang.String r1 = r0.getOperatorAlphaShort()
            r0.networkNameData = r1
        L_0x01b2:
            r5.notifyListenersIfNecessary()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.connectivity.MobileSignalController.updateTelephony():void");
    }

    public final void dump(PrintWriter printWriter) {
        super.dump(printWriter);
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("  mSubscription=");
        m.append(this.mSubscriptionInfo);
        m.append(",");
        printWriter.println(m.toString());
        printWriter.println("  mProviderModelBehavior=" + this.mProviderModelBehavior + ",");
        printWriter.println("  mInflateSignalStrengths=" + this.mInflateSignalStrengths + ",");
        printWriter.println("  isDataDisabled=" + (this.mPhone.isDataConnectionAllowed() ^ true) + ",");
        printWriter.println("  mNetworkToIconLookup=" + this.mNetworkToIconLookup + ",");
        printWriter.println("  MobileStatusHistory");
        int i = 0;
        for (int i2 = 0; i2 < 64; i2++) {
            if (this.mMobileStatusHistory[i2] != null) {
                i++;
            }
        }
        int i3 = this.mMobileStatusHistoryIndex + 64;
        while (true) {
            i3--;
            if (i3 >= (this.mMobileStatusHistoryIndex + 64) - i) {
                StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("  Previous MobileStatus(");
                m2.append((this.mMobileStatusHistoryIndex + 64) - i3);
                m2.append("): ");
                m2.append(this.mMobileStatusHistory[i3 & 63]);
                printWriter.println(m2.toString());
            } else {
                return;
            }
        }
    }

    public final void handleBroadcast(Intent intent) {
        String action = intent.getAction();
        boolean z = false;
        if (action.equals("android.telephony.action.SERVICE_PROVIDERS_UPDATED")) {
            boolean booleanExtra = intent.getBooleanExtra("android.telephony.extra.SHOW_SPN", false);
            String stringExtra = intent.getStringExtra("android.telephony.extra.SPN");
            String stringExtra2 = intent.getStringExtra("android.telephony.extra.DATA_SPN");
            boolean booleanExtra2 = intent.getBooleanExtra("android.telephony.extra.SHOW_PLMN", false);
            String stringExtra3 = intent.getStringExtra("android.telephony.extra.PLMN");
            if (SignalController.CHATTY) {
                StringBuilder sb = new StringBuilder();
                sb.append("updateNetworkName showSpn=");
                sb.append(booleanExtra);
                sb.append(" spn=");
                sb.append(stringExtra);
                sb.append(" dataSpn=");
                sb.append(stringExtra2);
                sb.append(" showPlmn=");
                sb.append(booleanExtra2);
                sb.append(" plmn=");
                ExifInterface$$ExternalSyntheticOutline2.m15m(sb, stringExtra3, "CarrierLabel");
            }
            StringBuilder sb2 = new StringBuilder();
            StringBuilder sb3 = new StringBuilder();
            if (booleanExtra2 && stringExtra3 != null) {
                sb2.append(stringExtra3);
                sb3.append(stringExtra3);
            }
            if (booleanExtra && stringExtra != null) {
                if (sb2.length() != 0) {
                    sb2.append(this.mNetworkNameSeparator);
                }
                sb2.append(stringExtra);
            }
            if (sb2.length() != 0) {
                ((MobileState) this.mCurrentState).networkName = sb2.toString();
            } else {
                ((MobileState) this.mCurrentState).networkName = this.mNetworkNameDefault;
            }
            if (booleanExtra && stringExtra2 != null) {
                if (sb3.length() != 0) {
                    sb3.append(this.mNetworkNameSeparator);
                }
                sb3.append(stringExtra2);
            }
            if (sb3.length() != 0) {
                ((MobileState) this.mCurrentState).networkNameData = sb3.toString();
            } else {
                ((MobileState) this.mCurrentState).networkNameData = this.mNetworkNameDefault;
            }
            notifyListenersIfNecessary();
        } else if (action.equals("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED")) {
            Objects.requireNonNull(this.mDefaults);
            int activeDataSubscriptionId = SubscriptionManager.getActiveDataSubscriptionId();
            if (SubscriptionManager.isValidSubscriptionId(activeDataSubscriptionId)) {
                MobileState mobileState = (MobileState) this.mCurrentState;
                if (activeDataSubscriptionId == this.mSubscriptionInfo.getSubscriptionId()) {
                    z = true;
                }
                mobileState.dataSim = z;
            } else {
                ((MobileState) this.mCurrentState).dataSim = true;
            }
            notifyListenersIfNecessary();
        }
    }

    @VisibleForTesting
    public void setImsType(int i) {
        this.mImsType = i;
    }
}
