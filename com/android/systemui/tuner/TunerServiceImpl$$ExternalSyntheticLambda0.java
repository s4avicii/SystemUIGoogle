package com.android.systemui.tuner;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda0;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class TunerServiceImpl$$ExternalSyntheticLambda0 implements DialogInterface.OnClickListener {
    public final /* synthetic */ TunerServiceImpl f$0;
    public final /* synthetic */ Runnable f$1;

    public /* synthetic */ TunerServiceImpl$$ExternalSyntheticLambda0(TunerServiceImpl tunerServiceImpl, CarrierTextManager$$ExternalSyntheticLambda0 carrierTextManager$$ExternalSyntheticLambda0) {
        this.f$0 = tunerServiceImpl;
        this.f$1 = carrierTextManager$$ExternalSyntheticLambda0;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        TunerServiceImpl tunerServiceImpl = this.f$0;
        Runnable runnable = this.f$1;
        Objects.requireNonNull(tunerServiceImpl);
        tunerServiceImpl.mContext.sendBroadcast(new Intent("com.android.systemui.action.CLEAR_TUNER"));
        tunerServiceImpl.mUserTracker.getUserContext().getPackageManager().setComponentEnabledSetting(tunerServiceImpl.mTunerComponent, 2, 1);
        Settings.Secure.putInt(tunerServiceImpl.mContext.getContentResolver(), "seen_tuner_warning", 0);
        if (runnable != null) {
            runnable.run();
        }
    }
}
