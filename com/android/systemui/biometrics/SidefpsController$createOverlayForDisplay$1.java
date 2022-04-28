package com.android.systemui.biometrics;

import android.view.Display;
import android.view.View;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieOnCompositionLoadedListener;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SidefpsController.kt */
public final class SidefpsController$createOverlayForDisplay$1 implements LottieOnCompositionLoadedListener {
    public final /* synthetic */ Display $display;
    public final /* synthetic */ View $view;
    public final /* synthetic */ SidefpsController this$0;

    public SidefpsController$createOverlayForDisplay$1(SidefpsController sidefpsController, View view, Display display) {
        this.this$0 = sidefpsController;
        this.$view = view;
        this.$display = display;
    }

    public final void onCompositionLoaded(LottieComposition lottieComposition) {
        if (Intrinsics.areEqual(this.this$0.overlayView, this.$view)) {
            SidefpsController sidefpsController = this.this$0;
            Display display = this.$display;
            Objects.requireNonNull(lottieComposition);
            sidefpsController.updateOverlayParams(display, lottieComposition.bounds);
            SidefpsController sidefpsController2 = this.this$0;
            sidefpsController2.windowManager.updateViewLayout(sidefpsController2.overlayView, sidefpsController2.overlayViewParams);
        }
    }
}
