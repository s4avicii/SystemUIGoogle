package com.android.systemui.biometrics;

import android.os.Handler;
import android.util.Log;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline1;
import com.android.settingslib.wifi.AccessPoint$$ExternalSyntheticLambda1;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.biometrics.UdfpsEnrollHelper;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ UdfpsController.UdfpsOverlayController f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda1(UdfpsController.UdfpsOverlayController udfpsOverlayController, int i) {
        this.f$0 = udfpsOverlayController;
        this.f$1 = i;
    }

    public final void run() {
        UdfpsController.UdfpsOverlayController udfpsOverlayController = this.f$0;
        int i = this.f$1;
        Objects.requireNonNull(udfpsOverlayController);
        UdfpsController udfpsController = UdfpsController.this;
        UdfpsControllerOverlay udfpsControllerOverlay = udfpsController.mOverlay;
        if (udfpsControllerOverlay == null) {
            KeyguardUpdateMonitor$$ExternalSyntheticOutline1.m27m("Null request when onAcquiredGood for sensorId: ", i, "UdfpsController");
            return;
        }
        udfpsController.mGoodCaptureReceived = true;
        UdfpsView udfpsView = udfpsControllerOverlay.overlayView;
        if (udfpsView != null) {
            udfpsView.stopIllumination();
        }
        UdfpsControllerOverlay udfpsControllerOverlay2 = UdfpsController.this.mOverlay;
        Objects.requireNonNull(udfpsControllerOverlay2);
        UdfpsEnrollHelper udfpsEnrollHelper = udfpsControllerOverlay2.enrollHelper;
        if (udfpsEnrollHelper != null) {
            UdfpsEnrollHelper.Listener listener = udfpsEnrollHelper.mListener;
            if (listener == null) {
                Log.e("UdfpsEnrollHelper", "animateIfLastStep, null listener");
                return;
            }
            int i2 = udfpsEnrollHelper.mRemainingSteps;
            if (i2 <= 2 && i2 >= 0) {
                UdfpsEnrollView udfpsEnrollView = (UdfpsEnrollView) UdfpsEnrollViewController.this.mView;
                Objects.requireNonNull(udfpsEnrollView);
                Handler handler = udfpsEnrollView.mHandler;
                UdfpsEnrollProgressBarDrawable udfpsEnrollProgressBarDrawable = udfpsEnrollView.mFingerprintProgressDrawable;
                Objects.requireNonNull(udfpsEnrollProgressBarDrawable);
                handler.post(new AccessPoint$$ExternalSyntheticLambda1(udfpsEnrollProgressBarDrawable, 1));
            }
        }
    }
}
