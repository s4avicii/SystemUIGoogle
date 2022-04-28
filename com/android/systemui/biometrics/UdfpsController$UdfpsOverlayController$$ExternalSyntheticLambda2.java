package com.android.systemui.biometrics;

import android.util.Log;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.biometrics.UdfpsEnrollHelper;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ UdfpsController.UdfpsOverlayController f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda2(UdfpsController.UdfpsOverlayController udfpsOverlayController, int i) {
        this.f$0 = udfpsOverlayController;
        this.f$1 = i;
    }

    public final void run() {
        UdfpsController.UdfpsOverlayController udfpsOverlayController = this.f$0;
        int i = this.f$1;
        Objects.requireNonNull(udfpsOverlayController);
        UdfpsControllerOverlay udfpsControllerOverlay = UdfpsController.this.mOverlay;
        if (udfpsControllerOverlay == null) {
            Log.e("UdfpsController", "onEnrollProgress received but serverRequest is null");
            return;
        }
        UdfpsEnrollHelper udfpsEnrollHelper = udfpsControllerOverlay.enrollHelper;
        if (udfpsEnrollHelper != null) {
            if (udfpsEnrollHelper.mTotalSteps == -1) {
                udfpsEnrollHelper.mTotalSteps = i;
            }
            if (i != udfpsEnrollHelper.mRemainingSteps) {
                udfpsEnrollHelper.mLocationsEnrolled++;
                if (udfpsEnrollHelper.isCenterEnrollmentStage()) {
                    udfpsEnrollHelper.mCenterTouchCount++;
                }
            }
            udfpsEnrollHelper.mRemainingSteps = i;
            UdfpsEnrollHelper.Listener listener = udfpsEnrollHelper.mListener;
            if (listener != null) {
                int i2 = udfpsEnrollHelper.mTotalSteps;
                UdfpsEnrollView udfpsEnrollView = (UdfpsEnrollView) UdfpsEnrollViewController.this.mView;
                Objects.requireNonNull(udfpsEnrollView);
                udfpsEnrollView.mHandler.post(new UdfpsEnrollView$$ExternalSyntheticLambda1(udfpsEnrollView, i, i2));
            }
        }
    }
}
