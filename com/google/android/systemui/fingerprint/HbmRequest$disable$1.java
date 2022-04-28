package com.google.android.systemui.fingerprint;

import android.hardware.fingerprint.IUdfpsHbmListener;
import android.os.RemoteException;
import android.os.Trace;
import android.util.Log;
import android.view.Surface;
import com.android.systemui.biometrics.AuthController;
import com.google.hardware.pixel.display.IDisplay;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UdfpsHbmController.kt */
public final class HbmRequest$disable$1 implements Runnable {
    public final /* synthetic */ Runnable $onHbmDisabled = null;
    public final /* synthetic */ HbmRequest this$0;

    public HbmRequest$disable$1(HbmRequest hbmRequest) {
        this.this$0 = hbmRequest;
    }

    public final void run() {
        HbmRequest hbmRequest = this.this$0;
        Objects.requireNonNull(hbmRequest);
        int i = hbmRequest.hbmType;
        if (i == 0) {
            HbmRequest hbmRequest2 = this.this$0;
            UdfpsGhbmProvider udfpsGhbmProvider = hbmRequest2.ghbmProvider;
            Objects.requireNonNull(hbmRequest2);
            Surface surface = hbmRequest2.surface;
            Intrinsics.checkNotNull(surface);
            udfpsGhbmProvider.disableGhbm(surface);
        } else if (i != 1) {
            HbmRequest hbmRequest3 = this.this$0;
            Objects.requireNonNull(hbmRequest3);
            Log.e("UdfpsHbmController", Intrinsics.stringPlus("doDisableHbm | unsupported HBM type: ", Integer.valueOf(hbmRequest3.hbmType)));
        } else {
            UdfpsLhbmProvider udfpsLhbmProvider = this.this$0.lhbmProvider;
            Objects.requireNonNull(udfpsLhbmProvider);
            Log.v("UdfpsLhbmProvider", "disableLhbm");
            IDisplay displayHal = udfpsLhbmProvider.getDisplayHal();
            if (displayHal == null) {
                Log.e("UdfpsLhbmProvider", "disableLhbm | displayHal is null");
            } else {
                try {
                    displayHal.setLhbmState(false);
                } catch (RemoteException e) {
                    Log.e("UdfpsLhbmProvider", "disableLhbm | RemoteException", e);
                }
            }
        }
        Trace.endAsyncSection("UdfpsHbmController.e2e.disableHbm", 0);
        final HbmRequest hbmRequest4 = this.this$0;
        hbmRequest4.mainHandler.post(new Runnable() {
            public final void run() {
                try {
                    AuthController authController = hbmRequest4.authController;
                    Objects.requireNonNull(authController);
                    IUdfpsHbmListener iUdfpsHbmListener = authController.mUdfpsHbmListener;
                    Intrinsics.checkNotNull(iUdfpsHbmListener);
                    HbmRequest hbmRequest = hbmRequest4;
                    Objects.requireNonNull(hbmRequest);
                    int i = hbmRequest.hbmType;
                    HbmRequest hbmRequest2 = hbmRequest4;
                    Objects.requireNonNull(hbmRequest2);
                    iUdfpsHbmListener.onHbmDisabled(i, hbmRequest2.displayId);
                    Log.v("UdfpsHbmController", "disableHbm | requested to unfreeze the refresh rate");
                } catch (RemoteException e) {
                    Log.e("UdfpsHbmController", "disableHbm", e);
                }
            }
        });
        Runnable runnable = this.$onHbmDisabled;
        if (runnable != null) {
            this.this$0.mainHandler.post(runnable);
        } else {
            Log.w("UdfpsHbmController", "doDisableHbm | onHbmDisabled is null");
        }
    }
}
