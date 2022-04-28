package com.google.android.systemui.assist.uihints.edgelights.mode;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.view.animation.PathInterpolator;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda2;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.assist.p003ui.EdgeLight;
import com.android.systemui.assist.p003ui.PerimeterPathGuide;
import com.google.android.systemui.assist.uihints.edgelights.EdgeLightsView;

public final class FulfillPerimeter implements EdgeLightsView.Mode {
    public static final PathInterpolator FULFILL_PERIMETER_INTERPOLATOR = new PathInterpolator(0.2f, 0.0f, 0.2f, 1.0f);
    public final EdgeLight mBlueLight;
    public boolean mDisappearing = false;
    public final EdgeLight mGreenLight;
    public final EdgeLight[] mLights;
    public EdgeLightsView.Mode mNextMode;
    public final EdgeLight mRedLight;
    public final EdgeLight mYellowLight;

    public final int getSubType() {
        return 4;
    }

    public final void start(EdgeLightsView edgeLightsView, PerimeterPathGuide perimeterPathGuide, EdgeLightsView.Mode mode) {
        boolean z;
        boolean z2;
        float f;
        AnimatorSet animatorSet;
        long j;
        AnimatorSet animatorSet2;
        final EdgeLightsView edgeLightsView2 = edgeLightsView;
        PerimeterPathGuide perimeterPathGuide2 = perimeterPathGuide;
        PerimeterPathGuide.Region region = PerimeterPathGuide.Region.TOP;
        boolean z3 = false;
        edgeLightsView2.setVisibility(0);
        AnimatorSet animatorSet3 = new AnimatorSet();
        EdgeLight[] edgeLightArr = this.mLights;
        int length = edgeLightArr.length;
        int i = 0;
        while (i < length) {
            EdgeLight edgeLight = edgeLightArr[i];
            if (edgeLight == this.mBlueLight || edgeLight == this.mRedLight) {
                z = true;
            } else {
                z = z3;
            }
            if (edgeLight == this.mRedLight || edgeLight == this.mYellowLight) {
                z2 = true;
            } else {
                z2 = z3;
            }
            PerimeterPathGuide.Region region2 = PerimeterPathGuide.Region.BOTTOM;
            float regionCenter = perimeterPathGuide2.getRegionCenter(region2);
            if (z) {
                f = perimeterPathGuide2.getRegionCenter(region) - 1.0f;
            } else {
                f = regionCenter;
            }
            float f2 = f - regionCenter;
            float regionCenter2 = perimeterPathGuide2.getRegionCenter(region) - perimeterPathGuide2.getRegionCenter(region2);
            float f3 = regionCenter2 - 0.0f;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            if (z2) {
                animatorSet = animatorSet3;
                j = 100;
            } else {
                animatorSet = animatorSet3;
                j = 0;
            }
            ofFloat.setStartDelay(j);
            ofFloat.setDuration(433);
            PathInterpolator pathInterpolator = FULFILL_PERIMETER_INTERPOLATOR;
            ofFloat.setInterpolator(pathInterpolator);
            ValueAnimator valueAnimator = ofFloat;
            PerimeterPathGuide.Region region3 = region;
            EdgeLight edgeLight2 = edgeLight;
            valueAnimator.addUpdateListener(new FulfillPerimeter$$ExternalSyntheticLambda0(this, edgeLight, f2, regionCenter, f3, edgeLightsView));
            if (!z2) {
                animatorSet2 = animatorSet;
                animatorSet2.play(valueAnimator);
            } else {
                animatorSet2 = animatorSet;
                float interpolation = valueAnimator.getInterpolator().getInterpolation(100.0f / ((float) valueAnimator.getDuration())) * regionCenter2;
                ValueAnimator ofFloat2 = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
                ofFloat2.setStartDelay(valueAnimator.getStartDelay() + 100);
                ofFloat2.setDuration(733);
                ofFloat2.setInterpolator(pathInterpolator);
                ofFloat2.addUpdateListener(new FulfillPerimeter$$ExternalSyntheticLambda1(this, edgeLight2, interpolation, perimeterPathGuide, edgeLightsView));
                animatorSet2.play(valueAnimator);
                animatorSet2.play(ofFloat2);
            }
            i++;
            perimeterPathGuide2 = perimeterPathGuide;
            animatorSet3 = animatorSet2;
            region = region3;
            z3 = false;
        }
        final AnimatorSet animatorSet4 = animatorSet3;
        animatorSet4.addListener(new AnimatorListenerAdapter() {
            public static final /* synthetic */ int $r8$clinit = 0;

            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                FulfillPerimeter fulfillPerimeter = FulfillPerimeter.this;
                EdgeLightsView.Mode mode = fulfillPerimeter.mNextMode;
                if (mode == null) {
                    fulfillPerimeter.mDisappearing = false;
                    animatorSet4.start();
                } else if (mode != null) {
                    new Handler().postDelayed(new CarrierTextManager$$ExternalSyntheticLambda2(this, edgeLightsView2, 4), 500);
                }
            }
        });
        animatorSet4.start();
    }

    public FulfillPerimeter(Context context) {
        EdgeLight edgeLight = new EdgeLight(context.getResources().getColor(C1777R.color.edge_light_blue, (Resources.Theme) null));
        this.mBlueLight = edgeLight;
        EdgeLight edgeLight2 = new EdgeLight(context.getResources().getColor(C1777R.color.edge_light_red, (Resources.Theme) null));
        this.mRedLight = edgeLight2;
        EdgeLight edgeLight3 = new EdgeLight(context.getResources().getColor(C1777R.color.edge_light_yellow, (Resources.Theme) null));
        this.mYellowLight = edgeLight3;
        EdgeLight edgeLight4 = new EdgeLight(context.getResources().getColor(C1777R.color.edge_light_green, (Resources.Theme) null));
        this.mGreenLight = edgeLight4;
        this.mLights = new EdgeLight[]{edgeLight, edgeLight2, edgeLight4, edgeLight3};
    }

    public final void onNewModeRequest(EdgeLightsView edgeLightsView, EdgeLightsView.Mode mode) {
        this.mNextMode = mode;
    }
}
