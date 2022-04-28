package com.android.systemui.animation;

import android.animation.ValueAnimator;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.GradientDrawable;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.ViewOverlay;
import android.view.animation.Interpolator;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlin.jvm.internal.Ref$IntRef;

/* compiled from: LaunchAnimator.kt */
public final class LaunchAnimator {
    public static final PorterDuffXfermode SRC_MODE = new PorterDuffXfermode(PorterDuff.Mode.SRC);
    public final float[] cornerRadii = new float[8];
    public final Interpolators interpolators;
    public final int[] launchContainerLocation = new int[2];
    public final Timings timings;

    /* compiled from: LaunchAnimator.kt */
    public static final class Companion {
        public static float getProgress(Timings timings, float f, long j, long j2) {
            return MathUtils.constrain(((f * ((float) timings.totalDuration)) - ((float) j)) / ((float) j2), 0.0f, 1.0f);
        }
    }

    /* compiled from: LaunchAnimator.kt */
    public interface Controller {
        State createAnimatorState();

        ViewGroup getLaunchContainer();

        View getOpeningWindowSyncView() {
            return null;
        }

        void onLaunchAnimationEnd(boolean z) {
        }

        void onLaunchAnimationProgress(State state, float f, float f2) {
        }

        void onLaunchAnimationStart(boolean z) {
        }

        void setLaunchContainer(ViewGroup viewGroup);
    }

    /* compiled from: LaunchAnimator.kt */
    public static final class Interpolators {
        public final Interpolator contentAfterFadeInInterpolator;
        public final Interpolator contentBeforeFadeOutInterpolator;
        public final Interpolator positionInterpolator;
        public final Interpolator positionXInterpolator;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Interpolators)) {
                return false;
            }
            Interpolators interpolators = (Interpolators) obj;
            return Intrinsics.areEqual(this.positionInterpolator, interpolators.positionInterpolator) && Intrinsics.areEqual(this.positionXInterpolator, interpolators.positionXInterpolator) && Intrinsics.areEqual(this.contentBeforeFadeOutInterpolator, interpolators.contentBeforeFadeOutInterpolator) && Intrinsics.areEqual(this.contentAfterFadeInInterpolator, interpolators.contentAfterFadeInInterpolator);
        }

        public final int hashCode() {
            int hashCode = this.positionXInterpolator.hashCode();
            int hashCode2 = this.contentBeforeFadeOutInterpolator.hashCode();
            return this.contentAfterFadeInInterpolator.hashCode() + ((hashCode2 + ((hashCode + (this.positionInterpolator.hashCode() * 31)) * 31)) * 31);
        }

        public final String toString() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Interpolators(positionInterpolator=");
            m.append(this.positionInterpolator);
            m.append(", positionXInterpolator=");
            m.append(this.positionXInterpolator);
            m.append(", contentBeforeFadeOutInterpolator=");
            m.append(this.contentBeforeFadeOutInterpolator);
            m.append(", contentAfterFadeInInterpolator=");
            m.append(this.contentAfterFadeInInterpolator);
            m.append(')');
            return m.toString();
        }

        public Interpolators(Interpolator interpolator, Interpolator interpolator2, Interpolator interpolator3, Interpolator interpolator4) {
            this.positionInterpolator = interpolator;
            this.positionXInterpolator = interpolator2;
            this.contentBeforeFadeOutInterpolator = interpolator3;
            this.contentAfterFadeInInterpolator = interpolator4;
        }
    }

    /* compiled from: LaunchAnimator.kt */
    public static final class Timings {
        public final long contentAfterFadeInDelay;
        public final long contentAfterFadeInDuration;
        public final long contentBeforeFadeOutDelay;
        public final long contentBeforeFadeOutDuration;
        public final long totalDuration;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Timings)) {
                return false;
            }
            Timings timings = (Timings) obj;
            return this.totalDuration == timings.totalDuration && this.contentBeforeFadeOutDelay == timings.contentBeforeFadeOutDelay && this.contentBeforeFadeOutDuration == timings.contentBeforeFadeOutDuration && this.contentAfterFadeInDelay == timings.contentAfterFadeInDelay && this.contentAfterFadeInDuration == timings.contentAfterFadeInDuration;
        }

        public final int hashCode() {
            int hashCode = Long.hashCode(this.contentBeforeFadeOutDelay);
            int hashCode2 = Long.hashCode(this.contentBeforeFadeOutDuration);
            int hashCode3 = Long.hashCode(this.contentAfterFadeInDelay);
            return Long.hashCode(this.contentAfterFadeInDuration) + ((hashCode3 + ((hashCode2 + ((hashCode + (Long.hashCode(this.totalDuration) * 31)) * 31)) * 31)) * 31);
        }

        public final String toString() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Timings(totalDuration=");
            m.append(this.totalDuration);
            m.append(", contentBeforeFadeOutDelay=");
            m.append(this.contentBeforeFadeOutDelay);
            m.append(", contentBeforeFadeOutDuration=");
            m.append(this.contentBeforeFadeOutDuration);
            m.append(", contentAfterFadeInDelay=");
            m.append(this.contentAfterFadeInDelay);
            m.append(", contentAfterFadeInDuration=");
            m.append(this.contentAfterFadeInDuration);
            m.append(')');
            return m.toString();
        }

        public Timings(long j, long j2, long j3, long j4, long j5) {
            this.totalDuration = j;
            this.contentBeforeFadeOutDelay = j2;
            this.contentBeforeFadeOutDuration = j3;
            this.contentAfterFadeInDelay = j4;
            this.contentAfterFadeInDuration = j5;
        }
    }

    /* renamed from: isExpandingFullyAbove$frameworks__base__packages__SystemUI__animation__android_common__SystemUIAnimationLib */
    public final boolean mo6968xb114753a(ViewGroup viewGroup, State state) {
        viewGroup.getLocationOnScreen(this.launchContainerLocation);
        int i = state.top;
        int[] iArr = this.launchContainerLocation;
        if (i <= iArr[1]) {
            if (state.bottom >= viewGroup.getHeight() + iArr[1]) {
                int i2 = state.left;
                int[] iArr2 = this.launchContainerLocation;
                if (i2 <= iArr2[0]) {
                    if (state.right >= viewGroup.getWidth() + iArr2[0]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final LaunchAnimator$startAnimation$3 startAnimation(Controller controller, State state, int i, boolean z) {
        ViewOverlay viewOverlay;
        boolean z2;
        State state2 = state;
        State createAnimatorState = controller.createAnimatorState();
        Objects.requireNonNull(createAnimatorState);
        int i2 = createAnimatorState.top;
        int i3 = createAnimatorState.bottom;
        int i4 = createAnimatorState.left;
        int i5 = createAnimatorState.right;
        float f = ((float) (i4 + i5)) / 2.0f;
        int i6 = i5 - i4;
        float f2 = createAnimatorState.topCornerRadius;
        float f3 = createAnimatorState.bottomCornerRadius;
        Ref$IntRef ref$IntRef = new Ref$IntRef();
        ref$IntRef.element = state2.top;
        Ref$IntRef ref$IntRef2 = new Ref$IntRef();
        ref$IntRef2.element = state2.bottom;
        Ref$IntRef ref$IntRef3 = new Ref$IntRef();
        ref$IntRef3.element = state2.left;
        Ref$IntRef ref$IntRef4 = new Ref$IntRef();
        ref$IntRef4.element = state2.right;
        Ref$FloatRef ref$FloatRef = new Ref$FloatRef();
        ref$FloatRef.element = ((float) (ref$IntRef3.element + ref$IntRef4.element)) / 2.0f;
        Ref$IntRef ref$IntRef5 = new Ref$IntRef();
        Ref$FloatRef ref$FloatRef2 = ref$FloatRef;
        ref$IntRef5.element = ref$IntRef4.element - ref$IntRef3.element;
        float f4 = state2.topCornerRadius;
        float f5 = state2.bottomCornerRadius;
        float f6 = f3;
        ViewGroup launchContainer = controller.getLaunchContainer();
        boolean isExpandingFullyAbove$frameworks__base__packages__SystemUI__animation__android_common__SystemUIAnimationLib = mo6968xb114753a(launchContainer, state2);
        GradientDrawable gradientDrawable = new GradientDrawable();
        float f7 = f4;
        gradientDrawable.setColor(i);
        gradientDrawable.setAlpha(0);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        float f8 = f5;
        Timings timings2 = this.timings;
        Objects.requireNonNull(timings2);
        ofFloat.setDuration(timings2.totalDuration);
        ofFloat.setInterpolator(Interpolators.LINEAR);
        View openingWindowSyncView = controller.getOpeningWindowSyncView();
        if (openingWindowSyncView == null) {
            viewOverlay = null;
        } else {
            viewOverlay = openingWindowSyncView.getOverlay();
        }
        ViewOverlay viewOverlay2 = viewOverlay;
        if (openingWindowSyncView == null || Intrinsics.areEqual(openingWindowSyncView.getViewRootImpl(), controller.getLaunchContainer().getViewRootImpl())) {
            z2 = false;
        } else {
            z2 = true;
        }
        ViewGroupOverlay overlay = launchContainer.getOverlay();
        float f9 = f8;
        Ref$BooleanRef ref$BooleanRef = r2;
        Ref$BooleanRef ref$BooleanRef2 = new Ref$BooleanRef();
        Ref$BooleanRef ref$BooleanRef3 = r20;
        Ref$BooleanRef ref$BooleanRef4 = new Ref$BooleanRef();
        ofFloat.addListener(new LaunchAnimator$startAnimation$1(controller, isExpandingFullyAbove$frameworks__base__packages__SystemUI__animation__android_common__SystemUIAnimationLib, overlay, gradientDrawable, z2, viewOverlay2));
        ValueAnimator valueAnimator = ofFloat;
        LaunchAnimator$startAnimation$2 launchAnimator$startAnimation$2 = r0;
        Ref$IntRef ref$IntRef6 = ref$IntRef5;
        Ref$IntRef ref$IntRef7 = ref$IntRef3;
        ViewGroup viewGroup = launchContainer;
        float f10 = f6;
        GradientDrawable gradientDrawable2 = gradientDrawable;
        LaunchAnimator$startAnimation$2 launchAnimator$startAnimation$22 = new LaunchAnimator$startAnimation$2(ref$BooleanRef, this, f, ref$FloatRef2, i6, ref$IntRef6, createAnimatorState, i2, ref$IntRef, i3, ref$IntRef2, f2, f7, f10, f9, z2, ref$BooleanRef3, overlay, gradientDrawable2, viewOverlay2, viewGroup, openingWindowSyncView, controller, z, state, ref$IntRef7, ref$IntRef4);
        ValueAnimator valueAnimator2 = valueAnimator;
        valueAnimator2.addUpdateListener(launchAnimator$startAnimation$2);
        valueAnimator2.start();
        return new LaunchAnimator$startAnimation$3(ref$BooleanRef2, valueAnimator2);
    }

    public LaunchAnimator(Timings timings2, Interpolators interpolators2) {
        this.timings = timings2;
        this.interpolators = interpolators2;
    }

    /* compiled from: LaunchAnimator.kt */
    public static class State {
        public int bottom;
        public float bottomCornerRadius;
        public int left;
        public int right;
        public int top;
        public float topCornerRadius;
        public boolean visible;

        public State(int i, int i2, int i3, int i4, float f, float f2) {
            this.top = i;
            this.bottom = i2;
            this.left = i3;
            this.right = i4;
            this.topCornerRadius = f;
            this.bottomCornerRadius = f2;
            this.visible = true;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public /* synthetic */ State(int i, int i2, int i3, int i4, float f, float f2, int i5) {
            this((i5 & 1) != 0 ? 0 : i, (i5 & 2) != 0 ? 0 : i2, (i5 & 4) != 0 ? 0 : i3, (i5 & 8) != 0 ? 0 : i4, (i5 & 16) != 0 ? 0.0f : f, (i5 & 32) != 0 ? 0.0f : f2);
        }
    }
}
