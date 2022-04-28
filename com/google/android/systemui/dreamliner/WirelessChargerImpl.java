package com.google.android.systemui.dreamliner;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Bundle;
import android.os.Handler;
import android.os.HwBinder;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.util.Log;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.internal.annotations.VisibleForTesting;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline3;
import com.android.systemui.ImageWallpaper$GLEngine$$ExternalSyntheticLambda3;
import com.android.systemui.volume.CaptionsToggleImageButton$$ExternalSyntheticLambda0;
import com.google.android.systemui.dreamliner.DockObserver;
import com.google.android.systemui.dreamliner.WirelessCharger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import vendor.google.wireless_charger.V1_0.DockInfo;
import vendor.google.wireless_charger.V1_0.KeyExchangeResponse;
import vendor.google.wireless_charger.V1_1.IWirelessChargerInfoCallback$Stub;
import vendor.google.wireless_charger.V1_3.FanDetailedInfo;
import vendor.google.wireless_charger.V1_3.FanInfo;
import vendor.google.wireless_charger.V1_3.IWirelessCharger;

public class WirelessChargerImpl extends WirelessCharger implements IHwBinder.DeathRecipient {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long MAX_POLLING_TIMEOUT_NS = TimeUnit.SECONDS.toNanos(5);
    public IsDockPresentCallbackWrapper mCallback;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public long mPollingStartedTimeNs;
    public final C22331 mRunnable = new Runnable() {
        public final void run() {
            WirelessChargerImpl wirelessChargerImpl = WirelessChargerImpl.this;
            int i = WirelessChargerImpl.$r8$clinit;
            Objects.requireNonNull(wirelessChargerImpl);
            wirelessChargerImpl.initHALInterface();
            IWirelessCharger iWirelessCharger = wirelessChargerImpl.mWirelessCharger;
            if (iWirelessCharger != null) {
                try {
                    iWirelessCharger.isDockPresent(wirelessChargerImpl);
                } catch (Exception e) {
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("isDockPresent fail: ");
                    m.append(e.getMessage());
                    Log.i("Dreamliner-WLC_HAL", m.toString());
                }
            }
        }
    };
    public IWirelessCharger mWirelessCharger;

    public final class ChallengeCallbackWrapper {
        public final WirelessCharger.ChallengeCallback mCallback;

        public final void onValues(byte b, ArrayList<Byte> arrayList) {
            WirelessCharger.ChallengeCallback challengeCallback = this.mCallback;
            int intValue = Byte.valueOf(b).intValue();
            DockObserver.ChallengeCallback challengeCallback2 = (DockObserver.ChallengeCallback) challengeCallback;
            Objects.requireNonNull(challengeCallback2);
            Log.d("DLObserver", "C() Result: " + intValue);
            Bundle bundle = null;
            if (intValue == 0) {
                Log.d("DLObserver", "C() response: " + arrayList);
                ResultReceiver resultReceiver = challengeCallback2.mResultReceiver;
                DockObserver dockObserver = DockObserver.this;
                String str = DockObserver.ACTION_START_DREAMLINER_CONTROL_SERVICE;
                Objects.requireNonNull(dockObserver);
                if (arrayList != null && !arrayList.isEmpty()) {
                    byte[] convertArrayListToPrimitiveArray = DockObserver.convertArrayListToPrimitiveArray(arrayList);
                    bundle = new Bundle();
                    bundle.putByteArray("challenge_response", convertArrayListToPrimitiveArray);
                }
                resultReceiver.send(0, bundle);
                return;
            }
            challengeCallback2.mResultReceiver.send(1, (Bundle) null);
        }

        public ChallengeCallbackWrapper(WirelessCharger.ChallengeCallback challengeCallback) {
            this.mCallback = challengeCallback;
        }
    }

    public static final class GetFanInformationCallbackWrapper {
        public final WirelessCharger.GetFanInformationCallback mCallback;
        public final byte mFanId;

        public final void onValues(byte b, FanDetailedInfo fanDetailedInfo) {
            FanDetailedInfo fanDetailedInfo2 = fanDetailedInfo;
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("command=0, result=");
            m.append(Byte.valueOf(b).intValue());
            m.append(", i=");
            m.append(this.mFanId);
            m.append(", m=");
            m.append(fanDetailedInfo2.fanMode);
            m.append(", cr=");
            m.append(fanDetailedInfo2.currentRpm);
            m.append(", mir=");
            m.append(fanDetailedInfo2.minimumRpm);
            m.append(", mxr=");
            m.append(fanDetailedInfo2.maximumRpm);
            m.append(", t=");
            m.append(fanDetailedInfo2.type);
            m.append(", c=");
            KeyguardUpdateMonitor$$ExternalSyntheticOutline3.m28m(m, fanDetailedInfo2.count, "Dreamliner-WLC_HAL");
            WirelessCharger.GetFanInformationCallback getFanInformationCallback = this.mCallback;
            int intValue = Byte.valueOf(b).intValue();
            byte b2 = this.mFanId;
            int i = WirelessChargerImpl.$r8$clinit;
            Bundle bundle = new Bundle();
            bundle.putByte("fan_id", b2);
            bundle.putByte("fan_mode", fanDetailedInfo2.fanMode);
            bundle.putInt("fan_current_rpm", fanDetailedInfo2.currentRpm);
            bundle.putInt("fan_min_rpm", fanDetailedInfo2.minimumRpm);
            String str = ", c=";
            bundle.putInt("fan_max_rpm", fanDetailedInfo2.maximumRpm);
            String str2 = ", t=";
            bundle.putByte("fan_type", fanDetailedInfo2.type);
            bundle.putByte("fan_count", fanDetailedInfo2.count);
            DockObserver.GetFanInformationCallback getFanInformationCallback2 = (DockObserver.GetFanInformationCallback) getFanInformationCallback;
            Objects.requireNonNull(getFanInformationCallback2);
            StringBuilder sb = new StringBuilder();
            String str3 = "fan_count";
            sb.append("Callback of command=0, result=");
            sb.append(intValue);
            sb.append(", i=");
            KeyguardUpdateMonitor$$ExternalSyntheticOutline3.m28m(sb, getFanInformationCallback2.mFanId, "DLObserver");
            if (intValue == 0) {
                StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Callback of command=0, i=");
                m2.append(bundle.getByte("fan_id", (byte) -1));
                m2.append(", m=");
                m2.append(bundle.getByte("fan_mode", (byte) -1));
                m2.append(", cr=");
                m2.append(bundle.getInt("fan_current_rpm", -1));
                m2.append(", mir=");
                m2.append(bundle.getInt("fan_min_rpm", -1));
                m2.append(", mxr=");
                m2.append(bundle.getInt("fan_max_rpm", -1));
                m2.append(str2);
                m2.append(bundle.getByte("fan_type", (byte) -1));
                m2.append(str);
                m2.append(bundle.getByte(str3, (byte) -1));
                Log.d("DLObserver", m2.toString());
                getFanInformationCallback2.mResultReceiver.send(0, bundle);
                return;
            }
            getFanInformationCallback2.mResultReceiver.send(1, (Bundle) null);
        }

        public GetFanInformationCallbackWrapper(byte b, WirelessCharger.GetFanInformationCallback getFanInformationCallback) {
            this.mFanId = b;
            this.mCallback = getFanInformationCallback;
        }
    }

    public static final class GetFanSimpleInformationCallbackWrapper {
        public final WirelessCharger.GetFanSimpleInformationCallback mCallback;
        public final byte mFanId;

        public final void onValues(byte b, FanInfo fanInfo) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("command=3, result=");
            m.append(Byte.valueOf(b).intValue());
            m.append(", i=");
            m.append(this.mFanId);
            m.append(", m=");
            m.append(fanInfo.fanMode);
            m.append(", cr=");
            KeyguardUpdateMonitor$$ExternalSyntheticOutline3.m28m(m, fanInfo.currentRpm, "Dreamliner-WLC_HAL");
            WirelessCharger.GetFanSimpleInformationCallback getFanSimpleInformationCallback = this.mCallback;
            int intValue = Byte.valueOf(b).intValue();
            byte b2 = this.mFanId;
            int i = WirelessChargerImpl.$r8$clinit;
            Bundle bundle = new Bundle();
            bundle.putByte("fan_id", b2);
            bundle.putByte("fan_mode", fanInfo.fanMode);
            bundle.putInt("fan_current_rpm", fanInfo.currentRpm);
            DockObserver.GetFanSimpleInformationCallback getFanSimpleInformationCallback2 = (DockObserver.GetFanSimpleInformationCallback) getFanSimpleInformationCallback;
            Objects.requireNonNull(getFanSimpleInformationCallback2);
            StringBuilder sb = new StringBuilder();
            sb.append("Callback of command=3, result=");
            sb.append(intValue);
            sb.append(", i=");
            KeyguardUpdateMonitor$$ExternalSyntheticOutline3.m28m(sb, getFanSimpleInformationCallback2.mFanId, "DLObserver");
            if (intValue == 0) {
                StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Callback of command=3, i=");
                m2.append(bundle.getByte("fan_id", (byte) -1));
                m2.append(", m=");
                m2.append(bundle.getByte("fan_mode", (byte) -1));
                m2.append(", cr=");
                m2.append(bundle.getInt("fan_current_rpm", -1));
                Log.d("DLObserver", m2.toString());
                getFanSimpleInformationCallback2.mResultReceiver.send(0, bundle);
                return;
            }
            getFanSimpleInformationCallback2.mResultReceiver.send(1, (Bundle) null);
        }

        public GetFanSimpleInformationCallbackWrapper(byte b, WirelessCharger.GetFanSimpleInformationCallback getFanSimpleInformationCallback) {
            this.mFanId = b;
            this.mCallback = getFanSimpleInformationCallback;
        }
    }

    public static final class GetFeaturesCallbackWrapper {
        public final WirelessCharger.GetFeaturesCallback mCallback;

        public final void onValues(byte b, long j) {
            WirelessCharger.GetFeaturesCallback getFeaturesCallback = this.mCallback;
            int intValue = Byte.valueOf(b).intValue();
            DockObserver.GetFeaturesCallback getFeaturesCallback2 = (DockObserver.GetFeaturesCallback) getFeaturesCallback;
            Objects.requireNonNull(getFeaturesCallback2);
            Log.d("DLObserver", "GF() result: " + intValue);
            if (intValue == 0) {
                Log.d("DLObserver", "GF() response: f=" + j);
                ResultReceiver resultReceiver = getFeaturesCallback2.mResultReceiver;
                DockObserver dockObserver = DockObserver.this;
                String str = DockObserver.ACTION_START_DREAMLINER_CONTROL_SERVICE;
                Objects.requireNonNull(dockObserver);
                Bundle bundle = new Bundle();
                bundle.putLong("charger_feature", j);
                resultReceiver.send(0, bundle);
                return;
            }
            getFeaturesCallback2.mResultReceiver.send(1, (Bundle) null);
        }

        public GetFeaturesCallbackWrapper(WirelessCharger.GetFeaturesCallback getFeaturesCallback) {
            this.mCallback = getFeaturesCallback;
        }
    }

    public final class GetInformationCallbackWrapper {
        public final WirelessCharger.GetInformationCallback mCallback;

        public final void onValues(byte b, DockInfo dockInfo) {
            WirelessCharger.GetInformationCallback getInformationCallback = this.mCallback;
            int intValue = Byte.valueOf(b).intValue();
            String str = dockInfo.manufacturer;
            String str2 = dockInfo.model;
            String str3 = dockInfo.serial;
            int intValue2 = Byte.valueOf(dockInfo.type).intValue();
            DockObserver.GetInformationCallback getInformationCallback2 = (DockObserver.GetInformationCallback) getInformationCallback;
            Objects.requireNonNull(getInformationCallback2);
            Log.d("DLObserver", "GI() Result: " + intValue);
            if (intValue == 0) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("GI() response: di=");
                m.append(str + ", " + str2 + ", " + str3 + ", " + intValue2);
                Log.d("DLObserver", m.toString());
                ResultReceiver resultReceiver = getInformationCallback2.mResultReceiver;
                Bundle bundle = new Bundle();
                bundle.putString("manufacturer", str);
                bundle.putString("model", str2);
                bundle.putString("serialNumber", str3);
                bundle.putInt("accessoryType", intValue2);
                resultReceiver.send(0, bundle);
            } else if (intValue != 1) {
                getInformationCallback2.mResultReceiver.send(1, (Bundle) null);
            }
        }

        public GetInformationCallbackWrapper(WirelessCharger.GetInformationCallback getInformationCallback) {
            this.mCallback = getInformationCallback;
        }
    }

    public static final class GetWpcAuthCertificateCallbackWrapper {
        public final WirelessCharger.GetWpcAuthCertificateCallback mCallback;

        public final void onValues(byte b, ArrayList<Byte> arrayList) {
            WirelessCharger.GetWpcAuthCertificateCallback getWpcAuthCertificateCallback = this.mCallback;
            int intValue = Byte.valueOf(b).intValue();
            DockObserver.GetWpcAuthCertificateCallback getWpcAuthCertificateCallback2 = (DockObserver.GetWpcAuthCertificateCallback) getWpcAuthCertificateCallback;
            Objects.requireNonNull(getWpcAuthCertificateCallback2);
            Log.d("DLObserver", "GWAC() result: " + intValue);
            Bundle bundle = null;
            if (intValue == 0) {
                Log.d("DLObserver", "GWAC() response: c=" + arrayList);
                ResultReceiver resultReceiver = getWpcAuthCertificateCallback2.mResultReceiver;
                DockObserver dockObserver = DockObserver.this;
                String str = DockObserver.ACTION_START_DREAMLINER_CONTROL_SERVICE;
                Objects.requireNonNull(dockObserver);
                if (arrayList != null && !arrayList.isEmpty()) {
                    byte[] convertArrayListToPrimitiveArray = DockObserver.convertArrayListToPrimitiveArray(arrayList);
                    bundle = new Bundle();
                    bundle.putByteArray("wpc_cert", convertArrayListToPrimitiveArray);
                }
                resultReceiver.send(0, bundle);
                return;
            }
            getWpcAuthCertificateCallback2.mResultReceiver.send(1, (Bundle) null);
        }

        public GetWpcAuthCertificateCallbackWrapper(WirelessCharger.GetWpcAuthCertificateCallback getWpcAuthCertificateCallback) {
            this.mCallback = getWpcAuthCertificateCallback;
        }
    }

    public static final class GetWpcAuthChallengeResponseCallbackWrapper {
        public final WirelessCharger.GetWpcAuthChallengeResponseCallback mCallback;

        public final void onValues(byte b, byte b2, byte b3, byte b4, ArrayList<Byte> arrayList, ArrayList<Byte> arrayList2) {
            WirelessCharger.GetWpcAuthChallengeResponseCallback getWpcAuthChallengeResponseCallback = this.mCallback;
            int intValue = Byte.valueOf(b).intValue();
            DockObserver.GetWpcAuthChallengeResponseCallback getWpcAuthChallengeResponseCallback2 = (DockObserver.GetWpcAuthChallengeResponseCallback) getWpcAuthChallengeResponseCallback;
            Objects.requireNonNull(getWpcAuthChallengeResponseCallback2);
            Log.d("DLObserver", "GWACR() result: " + intValue);
            if (intValue == 0) {
                StringBuilder m = GridLayoutManager$$ExternalSyntheticOutline0.m19m("GWACR() response: mpv=", b2, ", pm=", b3, ", chl=");
                m.append(b4);
                m.append(", rv=");
                m.append(arrayList);
                m.append(", sv=");
                m.append(arrayList2);
                Log.d("DLObserver", m.toString());
                ResultReceiver resultReceiver = getWpcAuthChallengeResponseCallback2.mResultReceiver;
                DockObserver dockObserver = DockObserver.this;
                String str = DockObserver.ACTION_START_DREAMLINER_CONTROL_SERVICE;
                Objects.requireNonNull(dockObserver);
                Bundle bundle = new Bundle();
                bundle.putByte("max_protocol_ver", b2);
                bundle.putByte("slot_populated_mask", b3);
                bundle.putByte("cert_lsb", b4);
                bundle.putByteArray("signature_r", DockObserver.convertArrayListToPrimitiveArray(arrayList));
                bundle.putByteArray("signature_s", DockObserver.convertArrayListToPrimitiveArray(arrayList2));
                resultReceiver.send(0, bundle);
                return;
            }
            getWpcAuthChallengeResponseCallback2.mResultReceiver.send(1, (Bundle) null);
        }

        public GetWpcAuthChallengeResponseCallbackWrapper(WirelessCharger.GetWpcAuthChallengeResponseCallback getWpcAuthChallengeResponseCallback) {
            this.mCallback = getWpcAuthChallengeResponseCallback;
        }
    }

    public static final class GetWpcAuthDigestsCallbackWrapper {
        public final WirelessCharger.GetWpcAuthDigestsCallback mCallback;

        public final void onValues(byte b, byte b2, byte b3, ArrayList<byte[]> arrayList) {
            WirelessCharger.GetWpcAuthDigestsCallback getWpcAuthDigestsCallback = this.mCallback;
            int intValue = Byte.valueOf(b).intValue();
            DockObserver.GetWpcAuthDigestsCallback getWpcAuthDigestsCallback2 = (DockObserver.GetWpcAuthDigestsCallback) getWpcAuthDigestsCallback;
            Objects.requireNonNull(getWpcAuthDigestsCallback2);
            Log.d("DLObserver", "GWAD() result: " + intValue);
            if (intValue == 0) {
                StringBuilder m = GridLayoutManager$$ExternalSyntheticOutline0.m19m("GWAD() response: pm=", b2, ", rm=", b3, ", d=");
                m.append(arrayList);
                Log.d("DLObserver", m.toString());
                ResultReceiver resultReceiver = getWpcAuthDigestsCallback2.mResultReceiver;
                DockObserver dockObserver = DockObserver.this;
                String str = DockObserver.ACTION_START_DREAMLINER_CONTROL_SERVICE;
                Objects.requireNonNull(dockObserver);
                Bundle bundle = new Bundle();
                bundle.putByte("slot_populated_mask", b2);
                bundle.putByte("slot_returned_mask", b3);
                ArrayList arrayList2 = new ArrayList();
                arrayList.forEach(new ImageWallpaper$GLEngine$$ExternalSyntheticLambda3(arrayList2, 2));
                bundle.putParcelableArrayList("wpc_digests", arrayList2);
                resultReceiver.send(0, bundle);
                return;
            }
            getWpcAuthDigestsCallback2.mResultReceiver.send(1, (Bundle) null);
        }

        public GetWpcAuthDigestsCallbackWrapper(WirelessCharger.GetWpcAuthDigestsCallback getWpcAuthDigestsCallback) {
            this.mCallback = getWpcAuthDigestsCallback;
        }
    }

    public final class IsDockPresentCallbackWrapper {
        public final WirelessCharger.IsDockPresentCallback mCallback;

        /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onValues(boolean r10, byte r11, byte r12, boolean r13, int r14) {
            /*
                r9 = this;
                com.google.android.systemui.dreamliner.WirelessCharger$IsDockPresentCallback r9 = r9.mCallback
                com.google.android.systemui.dreamliner.DockObserver$IsDockPresentCallback r9 = (com.google.android.systemui.dreamliner.DockObserver.IsDockPresentCallback) r9
                java.util.Objects.requireNonNull(r9)
                java.lang.String r0 = "DLObserver"
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "IDP() response: d="
                r1.append(r2)
                r1.append(r10)
                java.lang.String r2 = ", i="
                r1.append(r2)
                r1.append(r14)
                java.lang.String r2 = ", t="
                r1.append(r2)
                r1.append(r11)
                java.lang.String r2 = ", o="
                r1.append(r2)
                r1.append(r12)
                java.lang.String r2 = ", sgi="
                r1.append(r2)
                r1.append(r13)
                java.lang.String r13 = r1.toString()
                android.util.Log.i(r0, r13)
                if (r10 == 0) goto L_0x00f3
                com.google.android.systemui.dreamliner.DockObserver r10 = com.google.android.systemui.dreamliner.DockObserver.this
                android.content.Context r9 = r9.mContext
                java.lang.String r13 = com.google.android.systemui.dreamliner.DockObserver.ACTION_START_DREAMLINER_CONTROL_SERVICE
                java.util.Objects.requireNonNull(r10)
                monitor-enter(r10)
                r13 = 1
                com.google.android.systemui.dreamliner.DockObserver.notifyForceEnabledAmbientDisplay(r13)     // Catch:{ all -> 0x00f0 }
                com.google.android.systemui.dreamliner.DockObserver$DreamlinerServiceConn r0 = r10.mDreamlinerServiceConn     // Catch:{ all -> 0x00f0 }
                if (r0 != 0) goto L_0x00ee
                com.google.android.systemui.dreamliner.DockObserver$DreamlinerBroadcastReceiver r0 = r10.mDreamlinerReceiver     // Catch:{ all -> 0x00f0 }
                r0.registerReceiver(r9)     // Catch:{ all -> 0x00f0 }
                com.google.android.systemui.dreamliner.DockGestureController r8 = new com.google.android.systemui.dreamliner.DockGestureController     // Catch:{ all -> 0x00f0 }
                android.widget.ImageView r2 = r10.mDreamlinerGear     // Catch:{ all -> 0x00f0 }
                android.widget.FrameLayout r3 = r10.mPhotoPreview     // Catch:{ all -> 0x00f0 }
                android.view.ViewParent r0 = r2.getParent()     // Catch:{ all -> 0x00f0 }
                r4 = r0
                android.view.View r4 = (android.view.View) r4     // Catch:{ all -> 0x00f0 }
                com.google.android.systemui.dreamliner.DockIndicationController r5 = r10.mIndicationController     // Catch:{ all -> 0x00f0 }
                com.android.systemui.plugins.statusbar.StatusBarStateController r6 = r10.mStatusBarStateController     // Catch:{ all -> 0x00f0 }
                java.lang.Class<com.android.systemui.statusbar.policy.KeyguardStateController> r0 = com.android.systemui.statusbar.policy.KeyguardStateController.class
                java.lang.Object r0 = com.android.systemui.Dependency.get(r0)     // Catch:{ all -> 0x00f0 }
                r7 = r0
                com.android.systemui.statusbar.policy.KeyguardStateController r7 = (com.android.systemui.statusbar.policy.KeyguardStateController) r7     // Catch:{ all -> 0x00f0 }
                r0 = r8
                r1 = r9
                r0.<init>(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ all -> 0x00f0 }
                r10.mDockGestureController = r8     // Catch:{ all -> 0x00f0 }
                com.android.systemui.statusbar.policy.ConfigurationController r0 = r10.mConfigurationController     // Catch:{ all -> 0x00f0 }
                r0.addCallback(r8)     // Catch:{ all -> 0x00f0 }
                android.content.Intent r0 = new android.content.Intent     // Catch:{ all -> 0x00f0 }
                java.lang.String r1 = "com.google.android.apps.dreamliner.START"
                r0.<init>(r1)     // Catch:{ all -> 0x00f0 }
                java.lang.String r1 = "com.google.android.apps.dreamliner/.DreamlinerControlService"
                android.content.ComponentName r1 = android.content.ComponentName.unflattenFromString(r1)     // Catch:{ all -> 0x00f0 }
                r0.setComponent(r1)     // Catch:{ all -> 0x00f0 }
                java.lang.String r1 = "type"
                r0.putExtra(r1, r11)     // Catch:{ all -> 0x00f0 }
                java.lang.String r11 = "orientation"
                r0.putExtra(r11, r12)     // Catch:{ all -> 0x00f0 }
                java.lang.String r11 = "id"
                r0.putExtra(r11, r14)     // Catch:{ all -> 0x00f0 }
                java.lang.String r11 = "occluded"
                com.google.android.systemui.elmyra.gates.KeyguardVisibility r12 = new com.google.android.systemui.elmyra.gates.KeyguardVisibility     // Catch:{ all -> 0x00f0 }
                r12.<init>(r9)     // Catch:{ all -> 0x00f0 }
                com.android.systemui.statusbar.policy.KeyguardStateController r12 = r12.mKeyguardStateController     // Catch:{ all -> 0x00f0 }
                boolean r12 = r12.isOccluded()     // Catch:{ all -> 0x00f0 }
                r0.putExtra(r11, r12)     // Catch:{ all -> 0x00f0 }
                com.google.android.systemui.dreamliner.DockObserver$DreamlinerServiceConn r11 = new com.google.android.systemui.dreamliner.DockObserver$DreamlinerServiceConn     // Catch:{ SecurityException -> 0x00cb }
                r11.<init>(r9)     // Catch:{ SecurityException -> 0x00cb }
                r10.mDreamlinerServiceConn = r11     // Catch:{ SecurityException -> 0x00cb }
                android.os.UserHandle r12 = new android.os.UserHandle     // Catch:{ SecurityException -> 0x00cb }
                com.google.android.systemui.dreamliner.DockObserver$1 r14 = r10.mUserTracker     // Catch:{ SecurityException -> 0x00cb }
                int r14 = r14.getCurrentUserId()     // Catch:{ SecurityException -> 0x00cb }
                r12.<init>(r14)     // Catch:{ SecurityException -> 0x00cb }
                boolean r9 = r9.bindServiceAsUser(r0, r11, r13, r12)     // Catch:{ SecurityException -> 0x00cb }
                if (r9 == 0) goto L_0x00d5
                com.google.android.systemui.dreamliner.DockObserver$1 r9 = r10.mUserTracker     // Catch:{ SecurityException -> 0x00cb }
                r9.startTracking()     // Catch:{ SecurityException -> 0x00cb }
                monitor-exit(r10)
                goto L_0x00f3
            L_0x00cb:
                r9 = move-exception
                java.lang.String r11 = "DLObserver"
                java.lang.String r12 = r9.getMessage()     // Catch:{ all -> 0x00f0 }
                android.util.Log.e(r11, r12, r9)     // Catch:{ all -> 0x00f0 }
            L_0x00d5:
                r9 = 0
                r10.mDreamlinerServiceConn = r9     // Catch:{ all -> 0x00f0 }
                java.lang.String r9 = "DLObserver"
                java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x00f0 }
                r11.<init>()     // Catch:{ all -> 0x00f0 }
                java.lang.String r12 = "Unable to bind Dreamliner service: "
                r11.append(r12)     // Catch:{ all -> 0x00f0 }
                r11.append(r0)     // Catch:{ all -> 0x00f0 }
                java.lang.String r11 = r11.toString()     // Catch:{ all -> 0x00f0 }
                android.util.Log.w(r9, r11)     // Catch:{ all -> 0x00f0 }
            L_0x00ee:
                monitor-exit(r10)
                goto L_0x00f3
            L_0x00f0:
                r9 = move-exception
                monitor-exit(r10)
                throw r9
            L_0x00f3:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.dreamliner.WirelessChargerImpl.IsDockPresentCallbackWrapper.onValues(boolean, byte, byte, boolean, int):void");
        }

        public IsDockPresentCallbackWrapper(WirelessCharger.IsDockPresentCallback isDockPresentCallback) {
            this.mCallback = isDockPresentCallback;
        }
    }

    public final class KeyExchangeCallbackWrapper {
        public final WirelessCharger.KeyExchangeCallback mCallback;

        public final void onValues(byte b, KeyExchangeResponse keyExchangeResponse) {
            ((DockObserver.KeyExchangeCallback) this.mCallback).onCallback(Byte.valueOf(b).intValue(), keyExchangeResponse.dockId, keyExchangeResponse.dockPublicKey);
        }

        public KeyExchangeCallbackWrapper(WirelessCharger.KeyExchangeCallback keyExchangeCallback) {
            this.mCallback = keyExchangeCallback;
        }
    }

    public static final class SetFanCallbackWrapper {
        public final WirelessCharger.SetFanCallback mCallback;
        public final byte mFanId;

        public final void onValues(byte b, FanInfo fanInfo) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("command=1, result=");
            m.append(Byte.valueOf(b).intValue());
            m.append(", i=");
            m.append(this.mFanId);
            m.append(", m=");
            m.append(fanInfo.fanMode);
            m.append(", cr=");
            KeyguardUpdateMonitor$$ExternalSyntheticOutline3.m28m(m, fanInfo.currentRpm, "Dreamliner-WLC_HAL");
            WirelessCharger.SetFanCallback setFanCallback = this.mCallback;
            Byte.valueOf(b).intValue();
            byte b2 = this.mFanId;
            int i = WirelessChargerImpl.$r8$clinit;
            Bundle bundle = new Bundle();
            bundle.putByte("fan_id", b2);
            bundle.putByte("fan_mode", fanInfo.fanMode);
            bundle.putInt("fan_current_rpm", fanInfo.currentRpm);
            Objects.requireNonNull((DockObserver.SetFanCallback) setFanCallback);
            Log.d("DLObserver", "Callback of command=1, i=" + bundle.getByte("fan_id", (byte) -1) + ", m=" + bundle.getByte("fan_mode", (byte) -1) + ", cr=" + bundle.getInt("fan_current_rpm", -1));
        }

        public SetFanCallbackWrapper(byte b, WirelessCharger.SetFanCallback setFanCallback) {
            this.mFanId = b;
            this.mCallback = setFanCallback;
        }
    }

    public final class WirelessChargerInfoCallback extends IWirelessChargerInfoCallback$Stub {
        public final WirelessCharger.AlignInfoListener mListener;

        public WirelessChargerInfoCallback(WirelessCharger.AlignInfoListener alignInfoListener) {
            this.mListener = alignInfoListener;
        }
    }

    public static ArrayList convertPrimitiveArrayToArrayList(byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (byte valueOf : bArr) {
            arrayList.add(Byte.valueOf(valueOf));
        }
        return arrayList;
    }

    public final void initHALInterface() {
        IWirelessCharger.Proxy proxy;
        HwParcel hwParcel;
        if (this.mWirelessCharger == null) {
            try {
                IHwBinder service = HwBinder.getService("vendor.google.wireless_charger@1.3::IWirelessCharger", "default");
                if (service != null) {
                    IHwInterface queryLocalInterface = service.queryLocalInterface("vendor.google.wireless_charger@1.3::IWirelessCharger");
                    if (queryLocalInterface == null || !(queryLocalInterface instanceof IWirelessCharger)) {
                        IWirelessCharger.Proxy proxy2 = new IWirelessCharger.Proxy(service);
                        try {
                            HwParcel hwParcel2 = new HwParcel();
                            hwParcel2.writeInterfaceToken("android.hidl.base@1.0::IBase");
                            hwParcel = new HwParcel();
                            proxy2.mRemote.transact(256067662, hwParcel2, hwParcel, 0);
                            hwParcel.verifySuccess();
                            hwParcel2.releaseTemporaryStorage();
                            ArrayList readStringVector = hwParcel.readStringVector();
                            hwParcel.release();
                            Iterator it = readStringVector.iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    if (((String) it.next()).equals("vendor.google.wireless_charger@1.3::IWirelessCharger")) {
                                        proxy = proxy2;
                                        break;
                                    }
                                } else {
                                    break;
                                }
                            }
                        } catch (RemoteException unused) {
                        } catch (Throwable th) {
                            hwParcel.release();
                            throw th;
                        }
                        this.mWirelessCharger = proxy;
                        proxy.linkToDeath(this);
                    }
                    proxy = (IWirelessCharger) queryLocalInterface;
                    this.mWirelessCharger = proxy;
                    proxy.linkToDeath(this);
                }
                proxy = null;
                this.mWirelessCharger = proxy;
                proxy.linkToDeath(this);
            } catch (Exception e) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("no wireless charger hal found: ");
                m.append(e.getMessage());
                Log.i("Dreamliner-WLC_HAL", m.toString());
                this.mWirelessCharger = null;
            }
        }
    }

    public final void serviceDied(long j) {
        Log.i("Dreamliner-WLC_HAL", "serviceDied");
        this.mWirelessCharger = null;
    }

    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    public void asyncIsDockPresent(WirelessCharger.IsDockPresentCallback isDockPresentCallback) {
        initHALInterface();
        if (this.mWirelessCharger != null) {
            this.mPollingStartedTimeNs = System.nanoTime();
            this.mCallback = new IsDockPresentCallbackWrapper(isDockPresentCallback);
            this.mHandler.removeCallbacks(this.mRunnable);
            this.mHandler.postDelayed(this.mRunnable, 100);
        }
    }

    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    public void challenge(byte b, byte[] bArr, WirelessCharger.ChallengeCallback challengeCallback) {
        initHALInterface();
        if (this.mWirelessCharger != null) {
            try {
                this.mWirelessCharger.challenge(b, convertPrimitiveArrayToArrayList(bArr), new ChallengeCallbackWrapper(challengeCallback));
            } catch (Exception e) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("challenge fail: ");
                m.append(e.getMessage());
                Log.i("Dreamliner-WLC_HAL", m.toString());
            }
        }
    }

    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    public void getFanInformation(byte b, WirelessCharger.GetFanInformationCallback getFanInformationCallback) {
        initHALInterface();
        Log.d("Dreamliner-WLC_HAL", "command=0");
        IWirelessCharger iWirelessCharger = this.mWirelessCharger;
        if (iWirelessCharger != null) {
            try {
                iWirelessCharger.getFanInformation(b, new GetFanInformationCallbackWrapper(b, getFanInformationCallback));
            } catch (Exception e) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("command=0 fail: ");
                m.append(e.getMessage());
                Log.i("Dreamliner-WLC_HAL", m.toString());
            }
        }
    }

    public final int getFanLevel() {
        initHALInterface();
        Log.d("Dreamliner-WLC_HAL", "command=2");
        IWirelessCharger iWirelessCharger = this.mWirelessCharger;
        if (iWirelessCharger == null) {
            return -1;
        }
        try {
            return iWirelessCharger.getFanLevel();
        } catch (Exception e) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("command=2 fail: ");
            m.append(e.getMessage());
            Log.i("Dreamliner-WLC_HAL", m.toString());
            return -1;
        }
    }

    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    public void getFanSimpleInformation(byte b, WirelessCharger.GetFanSimpleInformationCallback getFanSimpleInformationCallback) {
        initHALInterface();
        Log.d("Dreamliner-WLC_HAL", "command=3");
        IWirelessCharger iWirelessCharger = this.mWirelessCharger;
        if (iWirelessCharger != null) {
            try {
                iWirelessCharger.getFan(b, new GetFanSimpleInformationCallbackWrapper(b, getFanSimpleInformationCallback));
            } catch (Exception e) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("command=3 fail: ");
                m.append(e.getMessage());
                Log.i("Dreamliner-WLC_HAL", m.toString());
            }
        }
    }

    public final void getFeatures(long j, WirelessCharger.GetFeaturesCallback getFeaturesCallback) {
        initHALInterface();
        IWirelessCharger iWirelessCharger = this.mWirelessCharger;
        if (iWirelessCharger != null) {
            try {
                iWirelessCharger.getFeatures(j, new GetFeaturesCallbackWrapper(getFeaturesCallback));
            } catch (Exception e) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("get features fail: ");
                m.append(e.getMessage());
                Log.i("Dreamliner-WLC_HAL", m.toString());
            }
        }
    }

    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    public void getInformation(WirelessCharger.GetInformationCallback getInformationCallback) {
        initHALInterface();
        IWirelessCharger iWirelessCharger = this.mWirelessCharger;
        if (iWirelessCharger != null) {
            try {
                iWirelessCharger.getInformation(new GetInformationCallbackWrapper(getInformationCallback));
            } catch (Exception e) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("getInformation fail: ");
                m.append(e.getMessage());
                Log.i("Dreamliner-WLC_HAL", m.toString());
            }
        }
    }

    public final void getWpcAuthCertificate(byte b, short s, short s2, WirelessCharger.GetWpcAuthCertificateCallback getWpcAuthCertificateCallback) {
        initHALInterface();
        IWirelessCharger iWirelessCharger = this.mWirelessCharger;
        if (iWirelessCharger != null) {
            try {
                iWirelessCharger.getWpcAuthCertificate(b, s, s2, new GetWpcAuthCertificateCallbackWrapper(getWpcAuthCertificateCallback));
            } catch (Exception e) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("get wpc cert fail: ");
                m.append(e.getMessage());
                Log.i("Dreamliner-WLC_HAL", m.toString());
            }
        }
    }

    public final void getWpcAuthChallengeResponse(byte b, byte[] bArr, WirelessCharger.GetWpcAuthChallengeResponseCallback getWpcAuthChallengeResponseCallback) {
        initHALInterface();
        IWirelessCharger iWirelessCharger = this.mWirelessCharger;
        if (iWirelessCharger != null) {
            try {
                iWirelessCharger.getWpcAuthChallengeResponse(b, convertPrimitiveArrayToArrayList(bArr), new GetWpcAuthChallengeResponseCallbackWrapper(getWpcAuthChallengeResponseCallback));
            } catch (Exception e) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("get wpc challenge response fail: ");
                m.append(e.getMessage());
                Log.i("Dreamliner-WLC_HAL", m.toString());
            }
        }
    }

    public final void getWpcAuthDigests(byte b, WirelessCharger.GetWpcAuthDigestsCallback getWpcAuthDigestsCallback) {
        initHALInterface();
        IWirelessCharger iWirelessCharger = this.mWirelessCharger;
        if (iWirelessCharger != null) {
            try {
                iWirelessCharger.getWpcAuthDigests(b, new GetWpcAuthDigestsCallbackWrapper(getWpcAuthDigestsCallback));
            } catch (Exception e) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("get wpc digests fail: ");
                m.append(e.getMessage());
                Log.i("Dreamliner-WLC_HAL", m.toString());
            }
        }
    }

    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    public void keyExchange(byte[] bArr, WirelessCharger.KeyExchangeCallback keyExchangeCallback) {
        initHALInterface();
        if (this.mWirelessCharger != null) {
            try {
                this.mWirelessCharger.keyExchange(convertPrimitiveArrayToArrayList(bArr), new KeyExchangeCallbackWrapper(keyExchangeCallback));
            } catch (Exception e) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("keyExchange fail: ");
                m.append(e.getMessage());
                Log.i("Dreamliner-WLC_HAL", m.toString());
            }
        }
    }

    public final void onValues(boolean z, byte b, byte b2, boolean z2, int i) {
        if (System.nanoTime() >= this.mPollingStartedTimeNs + MAX_POLLING_TIMEOUT_NS || i != 0) {
            IsDockPresentCallbackWrapper isDockPresentCallbackWrapper = this.mCallback;
            if (isDockPresentCallbackWrapper != null) {
                isDockPresentCallbackWrapper.onValues(z, b, b2, z2, i);
                this.mCallback = null;
                return;
            }
            return;
        }
        this.mHandler.postDelayed(this.mRunnable, 100);
    }

    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    public void registerAlignInfo(WirelessCharger.AlignInfoListener alignInfoListener) {
        initHALInterface();
        IWirelessCharger iWirelessCharger = this.mWirelessCharger;
        if (iWirelessCharger != null) {
            try {
                iWirelessCharger.registerCallback(new WirelessChargerInfoCallback(alignInfoListener));
            } catch (Exception e) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("register alignInfo callback fail: ");
                m.append(e.getMessage());
                Log.i("Dreamliner-WLC_HAL", m.toString());
            }
        }
    }

    @VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
    public void setFan(byte b, byte b2, int i, WirelessCharger.SetFanCallback setFanCallback) {
        initHALInterface();
        Log.d("Dreamliner-WLC_HAL", "command=1, i=" + b + ", m=" + b2 + ", r=" + i);
        if (this.mWirelessCharger != null) {
            try {
                long currentTimeMillis = System.currentTimeMillis();
                this.mWirelessCharger.setFan(b, b2, (short) i, new SetFanCallbackWrapper(b, setFanCallback));
                Log.d("Dreamliner-WLC_HAL", "command=1 spending time: " + (System.currentTimeMillis() - currentTimeMillis));
            } catch (Exception e) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("command=1 fail: ");
                m.append(e.getMessage());
                Log.i("Dreamliner-WLC_HAL", m.toString());
            }
        }
    }

    public final void setFeatures(long j, long j2, CaptionsToggleImageButton$$ExternalSyntheticLambda0 captionsToggleImageButton$$ExternalSyntheticLambda0) {
        initHALInterface();
        IWirelessCharger iWirelessCharger = this.mWirelessCharger;
        if (iWirelessCharger != null) {
            try {
                byte features = iWirelessCharger.setFeatures(j, j2);
                DockObserver.SetFeatures setFeatures = (DockObserver.SetFeatures) captionsToggleImageButton$$ExternalSyntheticLambda0.f$0;
                Objects.requireNonNull(setFeatures);
                setFeatures.mResultReceiver.send(features, (Bundle) null);
            } catch (Exception e) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("set features fail: ");
                m.append(e.getMessage());
                Log.i("Dreamliner-WLC_HAL", m.toString());
            }
        }
    }
}
