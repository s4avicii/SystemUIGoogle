package com.android.systemui.animation;

import android.animation.ValueAnimator;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.GradientDrawable;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.ViewOverlay;
import androidx.leanback.R$drawable;
import com.android.systemui.animation.LaunchAnimator;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlin.jvm.internal.Ref$IntRef;

/* compiled from: LaunchAnimator.kt */
public final class LaunchAnimator$startAnimation$2 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ Ref$BooleanRef $cancelled;
    public final /* synthetic */ LaunchAnimator.Controller $controller;
    public final /* synthetic */ boolean $drawHole;
    public final /* synthetic */ Ref$IntRef $endBottom;
    public final /* synthetic */ float $endBottomCornerRadius;
    public final /* synthetic */ Ref$FloatRef $endCenterX;
    public final /* synthetic */ Ref$IntRef $endLeft;
    public final /* synthetic */ Ref$IntRef $endRight;
    public final /* synthetic */ LaunchAnimator.State $endState;
    public final /* synthetic */ Ref$IntRef $endTop;
    public final /* synthetic */ float $endTopCornerRadius;
    public final /* synthetic */ Ref$IntRef $endWidth;
    public final /* synthetic */ ViewGroup $launchContainer;
    public final /* synthetic */ ViewGroupOverlay $launchContainerOverlay;
    public final /* synthetic */ boolean $moveBackgroundLayerWhenAppIsVisible;
    public final /* synthetic */ Ref$BooleanRef $movedBackgroundLayer;
    public final /* synthetic */ View $openingWindowSyncView;
    public final /* synthetic */ ViewOverlay $openingWindowSyncViewOverlay;
    public final /* synthetic */ int $startBottom;
    public final /* synthetic */ float $startBottomCornerRadius;
    public final /* synthetic */ float $startCenterX;
    public final /* synthetic */ int $startTop;
    public final /* synthetic */ float $startTopCornerRadius;
    public final /* synthetic */ int $startWidth;
    public final /* synthetic */ LaunchAnimator.State $state;
    public final /* synthetic */ GradientDrawable $windowBackgroundLayer;
    public final /* synthetic */ LaunchAnimator this$0;

    public LaunchAnimator$startAnimation$2(Ref$BooleanRef ref$BooleanRef, LaunchAnimator launchAnimator, float f, Ref$FloatRef ref$FloatRef, int i, Ref$IntRef ref$IntRef, LaunchAnimator.State state, int i2, Ref$IntRef ref$IntRef2, int i3, Ref$IntRef ref$IntRef3, float f2, float f3, float f4, float f5, boolean z, Ref$BooleanRef ref$BooleanRef2, ViewGroupOverlay viewGroupOverlay, GradientDrawable gradientDrawable, ViewOverlay viewOverlay, ViewGroup viewGroup, View view, LaunchAnimator.Controller controller, boolean z2, LaunchAnimator.State state2, Ref$IntRef ref$IntRef4, Ref$IntRef ref$IntRef5) {
        this.$cancelled = ref$BooleanRef;
        this.this$0 = launchAnimator;
        this.$startCenterX = f;
        this.$endCenterX = ref$FloatRef;
        this.$startWidth = i;
        this.$endWidth = ref$IntRef;
        this.$state = state;
        this.$startTop = i2;
        this.$endTop = ref$IntRef2;
        this.$startBottom = i3;
        this.$endBottom = ref$IntRef3;
        this.$startTopCornerRadius = f2;
        this.$endTopCornerRadius = f3;
        this.$startBottomCornerRadius = f4;
        this.$endBottomCornerRadius = f5;
        this.$moveBackgroundLayerWhenAppIsVisible = z;
        this.$movedBackgroundLayer = ref$BooleanRef2;
        this.$launchContainerOverlay = viewGroupOverlay;
        this.$windowBackgroundLayer = gradientDrawable;
        this.$openingWindowSyncViewOverlay = viewOverlay;
        this.$launchContainer = viewGroup;
        this.$openingWindowSyncView = view;
        this.$controller = controller;
        this.$drawHole = z2;
        this.$endState = state2;
        this.$endLeft = ref$IntRef4;
        this.$endRight = ref$IntRef5;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        boolean z;
        View view;
        if (!this.$cancelled.element) {
            Ref$IntRef ref$IntRef = this.$endTop;
            LaunchAnimator.State state = this.$endState;
            Ref$IntRef ref$IntRef2 = this.$endBottom;
            Ref$IntRef ref$IntRef3 = this.$endLeft;
            Ref$IntRef ref$IntRef4 = this.$endRight;
            Ref$FloatRef ref$FloatRef = this.$endCenterX;
            Ref$IntRef ref$IntRef5 = this.$endWidth;
            int i = ref$IntRef.element;
            Objects.requireNonNull(state);
            int i2 = state.top;
            if (!(i == i2 && ref$IntRef2.element == state.bottom && ref$IntRef3.element == state.left && ref$IntRef4.element == state.right)) {
                ref$IntRef.element = i2;
                ref$IntRef2.element = state.bottom;
                ref$IntRef3.element = state.left;
                int i3 = state.right;
                ref$IntRef4.element = i3;
                int i4 = ref$IntRef3.element;
                ref$FloatRef.element = ((float) (i4 + i3)) / 2.0f;
                ref$IntRef5.element = i3 - i4;
            }
            float animatedFraction = valueAnimator.getAnimatedFraction();
            LaunchAnimator.Interpolators interpolators = this.this$0.interpolators;
            Objects.requireNonNull(interpolators);
            float interpolation = interpolators.positionInterpolator.getInterpolation(animatedFraction);
            LaunchAnimator.Interpolators interpolators2 = this.this$0.interpolators;
            Objects.requireNonNull(interpolators2);
            float lerp = MathUtils.lerp(this.$startCenterX, this.$endCenterX.element, interpolators2.positionXInterpolator.getInterpolation(animatedFraction));
            float lerp2 = MathUtils.lerp(this.$startWidth, this.$endWidth.element, interpolation) / 2.0f;
            LaunchAnimator.State state2 = this.$state;
            int roundToInt = R$drawable.roundToInt(MathUtils.lerp(this.$startTop, this.$endTop.element, interpolation));
            Objects.requireNonNull(state2);
            state2.top = roundToInt;
            LaunchAnimator.State state3 = this.$state;
            int roundToInt2 = R$drawable.roundToInt(MathUtils.lerp(this.$startBottom, this.$endBottom.element, interpolation));
            Objects.requireNonNull(state3);
            state3.bottom = roundToInt2;
            LaunchAnimator.State state4 = this.$state;
            int roundToInt3 = R$drawable.roundToInt(lerp - lerp2);
            Objects.requireNonNull(state4);
            state4.left = roundToInt3;
            LaunchAnimator.State state5 = this.$state;
            int roundToInt4 = R$drawable.roundToInt(lerp + lerp2);
            Objects.requireNonNull(state5);
            state5.right = roundToInt4;
            LaunchAnimator.State state6 = this.$state;
            float lerp3 = MathUtils.lerp(this.$startTopCornerRadius, this.$endTopCornerRadius, interpolation);
            Objects.requireNonNull(state6);
            state6.topCornerRadius = lerp3;
            LaunchAnimator.State state7 = this.$state;
            float lerp4 = MathUtils.lerp(this.$startBottomCornerRadius, this.$endBottomCornerRadius, interpolation);
            Objects.requireNonNull(state7);
            state7.bottomCornerRadius = lerp4;
            LaunchAnimator.State state8 = this.$state;
            PorterDuffXfermode porterDuffXfermode = LaunchAnimator.SRC_MODE;
            LaunchAnimator.Timings timings = this.this$0.timings;
            Objects.requireNonNull(timings);
            long j = timings.contentBeforeFadeOutDelay;
            LaunchAnimator.Timings timings2 = this.this$0.timings;
            Objects.requireNonNull(timings2);
            if (LaunchAnimator.Companion.getProgress(timings, animatedFraction, j, timings2.contentBeforeFadeOutDuration) < 1.0f) {
                z = true;
            } else {
                z = false;
            }
            Objects.requireNonNull(state8);
            state8.visible = z;
            if (this.$moveBackgroundLayerWhenAppIsVisible) {
                LaunchAnimator.State state9 = this.$state;
                Objects.requireNonNull(state9);
                if (!state9.visible) {
                    Ref$BooleanRef ref$BooleanRef = this.$movedBackgroundLayer;
                    if (!ref$BooleanRef.element) {
                        ref$BooleanRef.element = true;
                        this.$launchContainerOverlay.remove(this.$windowBackgroundLayer);
                        ViewOverlay viewOverlay = this.$openingWindowSyncViewOverlay;
                        Intrinsics.checkNotNull(viewOverlay);
                        viewOverlay.add(this.$windowBackgroundLayer);
                        boolean z2 = ViewRootSync.forceDisableSynchronization;
                        ViewRootSync.synchronizeNextDraw(this.$launchContainer, this.$openingWindowSyncView, C06681.INSTANCE);
                    }
                }
            }
            if (this.$movedBackgroundLayer.element) {
                view = this.$openingWindowSyncView;
                Intrinsics.checkNotNull(view);
            } else {
                view = this.$controller.getLaunchContainer();
            }
            LaunchAnimator launchAnimator = this.this$0;
            GradientDrawable gradientDrawable = this.$windowBackgroundLayer;
            LaunchAnimator.State state10 = this.$state;
            boolean z3 = this.$drawHole;
            Objects.requireNonNull(launchAnimator);
            view.getLocationOnScreen(launchAnimator.launchContainerLocation);
            Objects.requireNonNull(state10);
            int i5 = state10.left;
            int[] iArr = launchAnimator.launchContainerLocation;
            gradientDrawable.setBounds(i5 - iArr[0], state10.top - iArr[1], state10.right - iArr[0], state10.bottom - iArr[1]);
            float[] fArr = launchAnimator.cornerRadii;
            float f = state10.topCornerRadius;
            fArr[0] = f;
            fArr[1] = f;
            fArr[2] = f;
            fArr[3] = f;
            float f2 = state10.bottomCornerRadius;
            fArr[4] = f2;
            fArr[5] = f2;
            fArr[6] = f2;
            fArr[7] = f2;
            gradientDrawable.setCornerRadii(fArr);
            LaunchAnimator.Timings timings3 = launchAnimator.timings;
            Objects.requireNonNull(timings3);
            long j2 = timings3.contentBeforeFadeOutDelay;
            LaunchAnimator.Timings timings4 = launchAnimator.timings;
            Objects.requireNonNull(timings4);
            GradientDrawable gradientDrawable2 = gradientDrawable;
            float progress = LaunchAnimator.Companion.getProgress(timings3, animatedFraction, j2, timings4.contentBeforeFadeOutDuration);
            if (progress < 1.0f) {
                LaunchAnimator.Interpolators interpolators3 = launchAnimator.interpolators;
                Objects.requireNonNull(interpolators3);
                gradientDrawable2.setAlpha(R$drawable.roundToInt(interpolators3.contentBeforeFadeOutInterpolator.getInterpolation(progress) * ((float) 255)));
            } else {
                LaunchAnimator.Timings timings5 = launchAnimator.timings;
                Objects.requireNonNull(timings5);
                long j3 = timings5.contentAfterFadeInDelay;
                LaunchAnimator.Timings timings6 = launchAnimator.timings;
                Objects.requireNonNull(timings6);
                float progress2 = LaunchAnimator.Companion.getProgress(timings5, animatedFraction, j3, timings6.contentAfterFadeInDuration);
                LaunchAnimator.Interpolators interpolators4 = launchAnimator.interpolators;
                Objects.requireNonNull(interpolators4);
                gradientDrawable2.setAlpha(R$drawable.roundToInt((((float) 1) - interpolators4.contentAfterFadeInInterpolator.getInterpolation(progress2)) * ((float) 255)));
                if (z3) {
                    gradientDrawable2.setXfermode(LaunchAnimator.SRC_MODE);
                }
            }
            this.$controller.onLaunchAnimationProgress(this.$state, interpolation, animatedFraction);
        }
    }
}
