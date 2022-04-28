package com.android.systemui.biometrics;

import android.content.Context;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.android.p012wm.shell.C1777R;
import kotlin.collections.SetsKt__SetsKt;

/* compiled from: SidefpsController.kt */
public final class SidefpsControllerKt {
    public static final void addOverlayDynamicColor$update(Context context, LottieAnimationView lottieAnimationView) {
        int color = context.getColor(C1777R.color.biometric_dialog_accent);
        for (String str : SetsKt__SetsKt.listOf(".blue600", ".blue400")) {
            lottieAnimationView.addValueCallback(new KeyPath(str, "**"), LottieProperty.COLOR_FILTER, new SidefpsControllerKt$addOverlayDynamicColor$update$1(color));
        }
    }
}
