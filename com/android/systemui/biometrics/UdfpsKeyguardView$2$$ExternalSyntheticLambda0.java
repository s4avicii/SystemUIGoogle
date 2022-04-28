package com.android.systemui.biometrics;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.android.systemui.biometrics.UdfpsKeyguardView;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class UdfpsKeyguardView$2$$ExternalSyntheticLambda0 implements SimpleLottieValueCallback {
    public final /* synthetic */ UdfpsKeyguardView.C07072 f$0;

    public /* synthetic */ UdfpsKeyguardView$2$$ExternalSyntheticLambda0(UdfpsKeyguardView.C07072 r1) {
        this.f$0 = r1;
    }

    public final PorterDuffColorFilter getValue() {
        UdfpsKeyguardView.C07072 r2 = this.f$0;
        Objects.requireNonNull(r2);
        return new PorterDuffColorFilter(UdfpsKeyguardView.this.mTextColorPrimary, PorterDuff.Mode.SRC_ATOP);
    }
}
