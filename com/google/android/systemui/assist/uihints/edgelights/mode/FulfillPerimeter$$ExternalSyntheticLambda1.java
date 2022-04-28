package com.google.android.systemui.assist.uihints.edgelights.mode;

import android.animation.ValueAnimator;
import com.android.systemui.assist.p003ui.EdgeLight;
import com.android.systemui.assist.p003ui.PerimeterPathGuide;
import com.google.android.systemui.assist.uihints.edgelights.EdgeLightsView;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class FulfillPerimeter$$ExternalSyntheticLambda1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ FulfillPerimeter f$0;
    public final /* synthetic */ EdgeLight f$1;
    public final /* synthetic */ float f$2;
    public final /* synthetic */ PerimeterPathGuide f$3;
    public final /* synthetic */ EdgeLightsView f$4;

    public /* synthetic */ FulfillPerimeter$$ExternalSyntheticLambda1(FulfillPerimeter fulfillPerimeter, EdgeLight edgeLight, float f, PerimeterPathGuide perimeterPathGuide, EdgeLightsView edgeLightsView) {
        this.f$0 = fulfillPerimeter;
        this.f$1 = edgeLight;
        this.f$2 = f;
        this.f$3 = perimeterPathGuide;
        this.f$4 = edgeLightsView;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        FulfillPerimeter fulfillPerimeter = this.f$0;
        EdgeLight edgeLight = this.f$1;
        float f = this.f$2;
        PerimeterPathGuide perimeterPathGuide = this.f$3;
        EdgeLightsView edgeLightsView = this.f$4;
        Objects.requireNonNull(fulfillPerimeter);
        float animatedFraction = valueAnimator.getAnimatedFraction();
        if (animatedFraction != 0.0f) {
            fulfillPerimeter.mDisappearing = true;
            EdgeLight edgeLight2 = fulfillPerimeter.mRedLight;
            if (edgeLight == edgeLight2) {
                float max = Math.max(((0.0f - f) * animatedFraction) + f, 0.0f);
                Objects.requireNonNull(edgeLight2);
                edgeLight2.mLength = max;
                EdgeLight edgeLight3 = fulfillPerimeter.mBlueLight;
                Objects.requireNonNull(edgeLight3);
                float abs = Math.abs(edgeLight3.mStart);
                EdgeLight edgeLight4 = fulfillPerimeter.mRedLight;
                Objects.requireNonNull(edgeLight4);
                edgeLight3.mLength = abs - Math.abs(edgeLight4.mStart);
            } else {
                EdgeLight edgeLight5 = fulfillPerimeter.mYellowLight;
                if (edgeLight == edgeLight5) {
                    PerimeterPathGuide.Region region = PerimeterPathGuide.Region.BOTTOM;
                    EdgeLight edgeLight6 = fulfillPerimeter.mRedLight;
                    Objects.requireNonNull(edgeLight6);
                    float f2 = edgeLight6.mStart;
                    EdgeLight edgeLight7 = fulfillPerimeter.mRedLight;
                    Objects.requireNonNull(edgeLight7);
                    Objects.requireNonNull(edgeLight5);
                    edgeLight5.mStart = (perimeterPathGuide.getRegionCenter(region) * 2.0f) - (f2 + edgeLight7.mLength);
                    EdgeLight edgeLight8 = fulfillPerimeter.mYellowLight;
                    EdgeLight edgeLight9 = fulfillPerimeter.mRedLight;
                    Objects.requireNonNull(edgeLight9);
                    float f3 = edgeLight9.mLength;
                    Objects.requireNonNull(edgeLight8);
                    edgeLight8.mLength = f3;
                    EdgeLight edgeLight10 = fulfillPerimeter.mGreenLight;
                    EdgeLight edgeLight11 = fulfillPerimeter.mBlueLight;
                    Objects.requireNonNull(edgeLight11);
                    float f4 = edgeLight11.mStart;
                    EdgeLight edgeLight12 = fulfillPerimeter.mBlueLight;
                    Objects.requireNonNull(edgeLight12);
                    Objects.requireNonNull(edgeLight10);
                    edgeLight10.mStart = (perimeterPathGuide.getRegionCenter(region) * 2.0f) - (f4 + edgeLight12.mLength);
                    EdgeLight edgeLight13 = fulfillPerimeter.mGreenLight;
                    EdgeLight edgeLight14 = fulfillPerimeter.mBlueLight;
                    Objects.requireNonNull(edgeLight14);
                    float f5 = edgeLight14.mLength;
                    Objects.requireNonNull(edgeLight13);
                    edgeLight13.mLength = f5;
                }
            }
            edgeLightsView.setAssistLights(fulfillPerimeter.mLights);
        }
    }
}
