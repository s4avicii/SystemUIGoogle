package com.android.systemui.biometrics;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import com.airbnb.lottie.value.SimpleLottieValueCallback;

/* compiled from: SidefpsController.kt */
public final class SidefpsControllerKt$addOverlayDynamicColor$update$1<T> implements SimpleLottieValueCallback {

    /* renamed from: $c */
    public final /* synthetic */ int f39$c;

    public SidefpsControllerKt$addOverlayDynamicColor$update$1(int i) {
        this.f39$c = i;
    }

    public final PorterDuffColorFilter getValue() {
        return new PorterDuffColorFilter(this.f39$c, PorterDuff.Mode.SRC_ATOP);
    }
}
