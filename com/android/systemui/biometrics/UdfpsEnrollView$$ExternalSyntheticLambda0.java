package com.android.systemui.biometrics;

import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class UdfpsEnrollView$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ UdfpsEnrollView f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ UdfpsEnrollView$$ExternalSyntheticLambda0(UdfpsEnrollView udfpsEnrollView, int i, int i2) {
        this.f$0 = udfpsEnrollView;
        this.f$1 = i;
        this.f$2 = i2;
    }

    public final void run() {
        UdfpsEnrollView udfpsEnrollView = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        int i3 = UdfpsEnrollView.$r8$clinit;
        Objects.requireNonNull(udfpsEnrollView);
        UdfpsEnrollProgressBarDrawable udfpsEnrollProgressBarDrawable = udfpsEnrollView.mFingerprintProgressDrawable;
        Objects.requireNonNull(udfpsEnrollProgressBarDrawable);
        udfpsEnrollProgressBarDrawable.updateState(i, i2, true);
    }
}
