package com.google.android.systemui.fingerprint;

import android.os.RemoteException;
import android.os.Trace;
import android.util.Log;
import android.view.Surface;
import com.google.hardware.pixel.display.IDisplay;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UdfpsHbmController.kt */
public final class HbmRequest$enable$1 implements Runnable {
    public final /* synthetic */ HbmRequest this$0;

    public HbmRequest$enable$1(HbmRequest hbmRequest) {
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
            udfpsGhbmProvider.enableGhbm(surface);
        } else if (i != 1) {
            HbmRequest hbmRequest3 = this.this$0;
            Objects.requireNonNull(hbmRequest3);
            Log.e("UdfpsHbmController", Intrinsics.stringPlus("doEnableHbm | unsupported HBM type: ", Integer.valueOf(hbmRequest3.hbmType)));
        } else {
            UdfpsLhbmProvider udfpsLhbmProvider = this.this$0.lhbmProvider;
            Objects.requireNonNull(udfpsLhbmProvider);
            Log.v("UdfpsLhbmProvider", "enableLhbm");
            IDisplay displayHal = udfpsLhbmProvider.getDisplayHal();
            if (displayHal == null) {
                Log.e("UdfpsLhbmProvider", "enableLhbm | displayHal is null");
            } else {
                try {
                    displayHal.setLhbmState(true);
                } catch (RemoteException e) {
                    Log.e("UdfpsLhbmProvider", "enableLhbm | RemoteException", e);
                }
            }
        }
        Trace.endAsyncSection("UdfpsHbmController.e2e.enableHbm", 0);
        try {
            HbmRequest hbmRequest4 = this.this$0;
            Runnable runnable = hbmRequest4.onHbmEnabled;
            if (runnable != null) {
                hbmRequest4.mainHandler.post(runnable);
            } else {
                Log.w("UdfpsHbmController", "doEnableHbm | onHbmEnabled is null");
            }
        } finally {
            this.this$0.finishedStarting = true;
        }
    }
}
