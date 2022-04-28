package com.google.android.systemui.assist.uihints;

import com.google.android.systemui.assist.uihints.LightnessProvider;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class LightnessProvider$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ LightnessProvider.C21821 f$0;
    public final /* synthetic */ float f$1;

    public /* synthetic */ LightnessProvider$1$$ExternalSyntheticLambda0(LightnessProvider.C21821 r1, float f) {
        this.f$0 = r1;
        this.f$1 = f;
    }

    public final void run() {
        LightnessProvider.C21821 r0 = this.f$0;
        float f = this.f$1;
        Objects.requireNonNull(r0);
        LightnessProvider lightnessProvider = r0.this$0;
        NgaUiController$$ExternalSyntheticLambda1 ngaUiController$$ExternalSyntheticLambda1 = lightnessProvider.mListener;
        if (ngaUiController$$ExternalSyntheticLambda1 != null && !lightnessProvider.mMuted) {
            if (!lightnessProvider.mCardVisible || lightnessProvider.mColorMode == 0) {
                ngaUiController$$ExternalSyntheticLambda1.onLightnessUpdate(f);
            }
        }
    }
}
