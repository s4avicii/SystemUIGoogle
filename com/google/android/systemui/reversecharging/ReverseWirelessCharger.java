package com.google.android.systemui.reversecharging;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Bundle;
import android.os.IHwBinder;
import android.util.Log;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import vendor.google.wireless_charger.V1_2.IWirelessCharger;
import vendor.google.wireless_charger.V1_2.IWirelessChargerRtxStatusCallback;
import vendor.google.wireless_charger.V1_2.RtxStatusInfo;

public final class ReverseWirelessCharger extends IWirelessChargerRtxStatusCallback.Stub implements IHwBinder.DeathRecipient {
    public static final boolean DEBUG = Log.isLoggable("ReverseWirelessCharger", 3);
    public final ArrayList<Object> mIsDockPresentCallbacks = new ArrayList<>();
    public final LocalRtxInformationCallback mLocalRtxInformationCallback = new LocalRtxInformationCallback();
    public final Object mLock = new Object();
    public final ArrayList<RtxInformationCallback> mRtxInformationCallbacks = new ArrayList<>();
    public final ArrayList<RtxStatusCallback> mRtxStatusCallbacks = new ArrayList<>();
    public IWirelessCharger mWirelessCharger;

    public class LocalRtxInformationCallback {
        public LocalRtxInformationCallback() {
        }

        public final void onValues(RtxStatusInfo rtxStatusInfo) {
            ArrayList arrayList;
            ReverseWirelessCharger reverseWirelessCharger = ReverseWirelessCharger.this;
            Objects.requireNonNull(reverseWirelessCharger);
            synchronized (reverseWirelessCharger.mLock) {
                arrayList = new ArrayList(reverseWirelessCharger.mRtxInformationCallbacks);
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((RtxInformationCallback) it.next()).onRtxInformationChanged(rtxStatusInfo);
            }
        }
    }

    public interface ReverseChargingChangeListener extends RtxStatusCallback {
        void onRtxStatusChanged(RtxStatusInfo rtxStatusInfo) {
            if (ReverseWirelessCharger.DEBUG) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("onRtxStatusChanged() RtxStatusInfo : ");
                m.append(rtxStatusInfo.toString());
                Log.d("ReverseWirelessCharger", m.toString());
            }
            ((ReverseChargingController$$ExternalSyntheticLambda6) this).f$0.onReverseStateChanged(ReverseWirelessCharger.m311$$Nest$smbuildReverseStatusBundle(rtxStatusInfo));
        }
    }

    public interface ReverseChargingInformationChangeListener extends RtxInformationCallback {
        void onRtxInformationChanged(RtxStatusInfo rtxStatusInfo) {
            if (ReverseWirelessCharger.DEBUG) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("onRtxInformationChanged() RtxStatusInfo : ");
                m.append(rtxStatusInfo.toString());
                Log.d("ReverseWirelessCharger", m.toString());
            }
            Bundle r4 = ReverseWirelessCharger.m311$$Nest$smbuildReverseStatusBundle(rtxStatusInfo);
            ReverseChargingController reverseChargingController = ((ReverseChargingController$$ExternalSyntheticLambda7) this).f$0;
            Objects.requireNonNull(reverseChargingController);
            if (ReverseChargingController.DEBUG) {
                StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("onReverseInformationChanged(): rtx=");
                int i = 1;
                if (r4.getInt("key_rtx_mode") != 1) {
                    i = 0;
                }
                m2.append(i);
                m2.append(" wlc=");
                m2.append(reverseChargingController.mWirelessCharging ? 1 : 0);
                m2.append(" mName=");
                m2.append(reverseChargingController.mName);
                m2.append(" bundle=");
                m2.append(r4.toString());
                m2.append(" this=");
                m2.append(reverseChargingController);
                Log.d("ReverseChargingControl", m2.toString());
            }
            if (r4.getInt("key_rtx_level") > 0) {
                reverseChargingController.mMainExecutor.execute(new CarrierTextManager$$ExternalSyntheticLambda2(reverseChargingController, r4, 5));
            }
        }
    }

    public interface RtxInformationCallback {
        void onRtxInformationChanged(RtxStatusInfo rtxStatusInfo);
    }

    public interface RtxStatusCallback {
        void onRtxStatusChanged(RtxStatusInfo rtxStatusInfo);
    }

    /* renamed from: -$$Nest$smbuildReverseStatusBundle  reason: not valid java name */
    public static Bundle m311$$Nest$smbuildReverseStatusBundle(RtxStatusInfo rtxStatusInfo) {
        Bundle bundle = new Bundle();
        bundle.putInt("key_rtx_mode", rtxStatusInfo.mode);
        bundle.putInt("key_accessory_type", rtxStatusInfo.acctype);
        bundle.putBoolean("key_rtx_connection", rtxStatusInfo.chg_s);
        bundle.putInt("key_rtx_iout", rtxStatusInfo.iout);
        bundle.putInt("key_rtx_vout", rtxStatusInfo.vout);
        bundle.putInt("key_rtx_level", rtxStatusInfo.level);
        bundle.putInt("key_reason_type", rtxStatusInfo.reason);
        return bundle;
    }

    public final void initHALInterface() {
        if (this.mWirelessCharger == null) {
            try {
                IWirelessCharger service = IWirelessCharger.getService();
                this.mWirelessCharger = service;
                service.linkToDeath(this);
                this.mWirelessCharger.registerRtxCallback(this);
            } catch (Exception e) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("no wireless charger hal found: ");
                m.append(e.getMessage());
                Log.i("ReverseWirelessCharger", m.toString(), e);
                this.mWirelessCharger = null;
            }
        }
    }

    public final void serviceDied(long j) {
        Log.i("ReverseWirelessCharger", "serviceDied");
        this.mWirelessCharger = null;
    }
}
