package com.android.systemui.biometrics;

import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.util.Log;
import com.android.systemui.biometrics.AuthController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AuthController$2$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ AuthController.C06912 f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ boolean f$3;

    public /* synthetic */ AuthController$2$$ExternalSyntheticLambda0(AuthController.C06912 r1, int i, int i2, boolean z) {
        this.f$0 = r1;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = z;
    }

    public final void run() {
        AuthController.C06912 r0 = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        boolean z = this.f$3;
        Objects.requireNonNull(r0);
        AuthController authController = AuthController.this;
        Objects.requireNonNull(authController);
        authController.mExecution.assertIsMainThread();
        Log.d("AuthController", "handleEnrollmentsChanged, userId: " + i + ", sensorId: " + i2 + ", hasEnrollments: " + z);
        ArrayList<FingerprintSensorPropertiesInternal> arrayList = authController.mUdfpsProps;
        if (arrayList == null) {
            Log.d("AuthController", "handleEnrollmentsChanged, mUdfpsProps is null");
        } else {
            for (FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal : arrayList) {
                if (fingerprintSensorPropertiesInternal.sensorId == i2) {
                    authController.mUdfpsEnrolledForUser.put(i, z);
                }
            }
        }
        Iterator it = authController.mCallbacks.iterator();
        while (it.hasNext()) {
            ((AuthController.Callback) it.next()).onEnrollmentsChanged();
        }
    }
}
