package com.android.systemui.statusbar;

import android.telephony.ServiceState;
import android.telephony.SubscriptionInfo;
import android.telephony.TelephonyManager;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.connectivity.IconState;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.SignalCallback;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.ViewController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public final class OperatorNameViewController extends ViewController<OperatorNameView> {
    public final DarkIconDispatcher mDarkIconDispatcher;
    public final OperatorNameViewController$$ExternalSyntheticLambda0 mDarkReceiver = new OperatorNameViewController$$ExternalSyntheticLambda0(this);
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final C11812 mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() {
        public final void onRefreshCarrierInfo() {
            OperatorNameViewController operatorNameViewController = OperatorNameViewController.this;
            ((OperatorNameView) operatorNameViewController.mView).updateText(operatorNameViewController.getSubInfos());
        }
    };
    public final NetworkController mNetworkController;
    public final C11801 mSignalCallback = new SignalCallback() {
        public final void setIsAirplaneMode(IconState iconState) {
            OperatorNameViewController.this.update();
        }
    };
    public final TelephonyManager mTelephonyManager;
    public final OperatorNameViewController$$ExternalSyntheticLambda1 mTunable = new OperatorNameViewController$$ExternalSyntheticLambda1(this);
    public final TunerService mTunerService;

    public static class Factory {
        public final DarkIconDispatcher mDarkIconDispatcher;
        public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
        public final NetworkController mNetworkController;
        public final TelephonyManager mTelephonyManager;
        public final TunerService mTunerService;

        public Factory(DarkIconDispatcher darkIconDispatcher, NetworkController networkController, TunerService tunerService, TelephonyManager telephonyManager, KeyguardUpdateMonitor keyguardUpdateMonitor) {
            this.mDarkIconDispatcher = darkIconDispatcher;
            this.mNetworkController = networkController;
            this.mTunerService = tunerService;
            this.mTelephonyManager = telephonyManager;
            this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        }
    }

    public static class SubInfo {
        public final CharSequence mCarrierName;
        public final ServiceState mServiceState;
        public final int mSimState;

        public SubInfo(CharSequence charSequence, int i, ServiceState serviceState) {
            this.mCarrierName = charSequence;
            this.mSimState = i;
            this.mServiceState = serviceState;
        }
    }

    public final ArrayList getSubInfos() {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.mKeyguardUpdateMonitor.getFilteredSubscriptionInfo().iterator();
        while (it.hasNext()) {
            SubscriptionInfo subscriptionInfo = (SubscriptionInfo) it.next();
            int subscriptionId = subscriptionInfo.getSubscriptionId();
            CharSequence carrierName = subscriptionInfo.getCarrierName();
            int simState = this.mKeyguardUpdateMonitor.getSimState(subscriptionId);
            KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
            Objects.requireNonNull(keyguardUpdateMonitor);
            arrayList.add(new SubInfo(carrierName, simState, keyguardUpdateMonitor.mServiceStates.get(Integer.valueOf(subscriptionId))));
        }
        return arrayList;
    }

    public final void onViewAttached() {
        this.mDarkIconDispatcher.addDarkReceiver((DarkIconDispatcher.DarkReceiver) this.mDarkReceiver);
        this.mNetworkController.addCallback(this.mSignalCallback);
        this.mTunerService.addTunable(this.mTunable, "show_operator_name");
        this.mKeyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
    }

    public final void onViewDetached() {
        this.mDarkIconDispatcher.removeDarkReceiver((DarkIconDispatcher.DarkReceiver) this.mDarkReceiver);
        this.mNetworkController.removeCallback(this.mSignalCallback);
        this.mTunerService.removeTunable(this.mTunable);
        this.mKeyguardUpdateMonitor.removeCallback(this.mKeyguardUpdateMonitorCallback);
    }

    public final void update() {
        OperatorNameView operatorNameView = (OperatorNameView) this.mView;
        boolean z = true;
        if (this.mTunerService.getValue("show_operator_name", 1) == 0) {
            z = false;
        }
        operatorNameView.update(z, this.mTelephonyManager.isDataCapable(), getSubInfos());
    }

    public OperatorNameViewController(OperatorNameView operatorNameView, DarkIconDispatcher darkIconDispatcher, NetworkController networkController, TunerService tunerService, TelephonyManager telephonyManager, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        super(operatorNameView);
        this.mDarkIconDispatcher = darkIconDispatcher;
        this.mNetworkController = networkController;
        this.mTunerService = tunerService;
        this.mTelephonyManager = telephonyManager;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
    }
}
