package com.android.keyguard;

import com.android.keyguard.CarrierTextManager;
import com.android.systemui.util.ViewController;
import java.util.Objects;

public final class CarrierTextController extends ViewController<CarrierText> {
    public final C04791 mCarrierTextCallback = new CarrierTextManager.CarrierTextCallback() {
        public final void finishedWakingUp() {
            ((CarrierText) CarrierTextController.this.mView).setSelected(true);
        }

        public final void startedGoingToSleep() {
            ((CarrierText) CarrierTextController.this.mView).setSelected(false);
        }

        public final void updateCarrierInfo(CarrierTextManager.CarrierTextCallbackInfo carrierTextCallbackInfo) {
            ((CarrierText) CarrierTextController.this.mView).setText(carrierTextCallbackInfo.carrierText);
        }
    };
    public final CarrierTextManager mCarrierTextManager;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;

    public final void onInit() {
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        Objects.requireNonNull(keyguardUpdateMonitor);
        ((CarrierText) this.mView).setSelected(keyguardUpdateMonitor.mDeviceInteractive);
    }

    public final void onViewAttached() {
        CarrierTextManager carrierTextManager = this.mCarrierTextManager;
        C04791 r4 = this.mCarrierTextCallback;
        Objects.requireNonNull(carrierTextManager);
        carrierTextManager.mBgExecutor.execute(new CarrierTextManager$$ExternalSyntheticLambda2(carrierTextManager, r4, 0));
    }

    public final void onViewDetached() {
        CarrierTextManager carrierTextManager = this.mCarrierTextManager;
        Objects.requireNonNull(carrierTextManager);
        carrierTextManager.mBgExecutor.execute(new CarrierTextManager$$ExternalSyntheticLambda2(carrierTextManager, (Object) null, 0));
    }

    public CarrierTextController(CarrierText carrierText, CarrierTextManager.Builder builder, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        super(carrierText);
        Objects.requireNonNull(carrierText);
        boolean z = carrierText.mShowAirplaneMode;
        Objects.requireNonNull(builder);
        builder.mShowAirplaneMode = z;
        builder.mShowMissingSim = carrierText.mShowMissingSim;
        this.mCarrierTextManager = builder.build();
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
    }
}
