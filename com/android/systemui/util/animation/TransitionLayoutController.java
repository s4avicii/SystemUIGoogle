package com.android.systemui.util.animation;

import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.util.MathUtils;
import com.android.systemui.animation.Interpolators;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: TransitionLayoutController.kt */
public final class TransitionLayoutController {
    public TransitionViewState animationStartState;
    public ValueAnimator animator;
    public int currentHeight;
    public TransitionViewState currentState = new TransitionViewState();
    public int currentWidth;
    public Function2<? super Integer, ? super Integer, Unit> sizeChangedListener;
    public TransitionViewState state = new TransitionViewState();
    public TransitionLayout transitionLayout;

    public static TransitionViewState getGoneState(TransitionViewState transitionViewState, DisappearParameters disappearParameters, float f, TransitionViewState transitionViewState2) {
        float constrain = MathUtils.constrain(MathUtils.map(disappearParameters.disappearStart, disappearParameters.disappearEnd, 0.0f, 1.0f, f), 0.0f, 1.0f);
        TransitionViewState copy = transitionViewState.copy(transitionViewState2);
        float f2 = (float) transitionViewState.width;
        copy.width = (int) MathUtils.lerp(f2, disappearParameters.disappearSize.x * f2, constrain);
        float f3 = (float) transitionViewState.height;
        int lerp = (int) MathUtils.lerp(f3, disappearParameters.disappearSize.y * f3, constrain);
        copy.height = lerp;
        PointF pointF = copy.translation;
        PointF pointF2 = disappearParameters.gonePivot;
        float f4 = ((float) (transitionViewState.width - copy.width)) * pointF2.x;
        pointF.x = f4;
        float f5 = ((float) (transitionViewState.height - lerp)) * pointF2.y;
        pointF.y = f5;
        PointF pointF3 = copy.contentTranslation;
        PointF pointF4 = disappearParameters.contentTranslationFraction;
        pointF3.x = (pointF4.x - 1.0f) * f4;
        pointF3.y = (pointF4.y - 1.0f) * f5;
        copy.alpha = MathUtils.constrain(MathUtils.map(disappearParameters.fadeStartPosition, 1.0f, 1.0f, 0.0f, constrain), 0.0f, 1.0f);
        return copy;
    }

    public final void applyStateToLayout(TransitionViewState transitionViewState) {
        TransitionLayout transitionLayout2 = this.transitionLayout;
        if (transitionLayout2 != null) {
            transitionLayout2.currentState = transitionViewState;
            transitionLayout2.applyCurrentState();
        }
        int i = this.currentHeight;
        Objects.requireNonNull(transitionViewState);
        int i2 = transitionViewState.height;
        if (i != i2 || this.currentWidth != transitionViewState.width) {
            this.currentHeight = i2;
            int i3 = transitionViewState.width;
            this.currentWidth = i3;
            Function2<? super Integer, ? super Integer, Unit> function2 = this.sizeChangedListener;
            if (function2 != null) {
                function2.invoke(Integer.valueOf(i3), Integer.valueOf(this.currentHeight));
            }
        }
    }

    public final TransitionViewState getInterpolatedState(TransitionViewState transitionViewState, TransitionViewState transitionViewState2, float f, TransitionViewState transitionViewState3) {
        TransitionLayoutController transitionLayoutController;
        TransitionViewState transitionViewState4;
        int i;
        TransitionLayout transitionLayout2;
        WidgetState widgetState;
        float f2;
        int i2;
        float f3;
        float f4;
        int i3;
        float f5;
        float f6;
        float f7;
        float f8;
        TransitionViewState transitionViewState5 = transitionViewState;
        TransitionViewState transitionViewState6 = transitionViewState2;
        float f9 = f;
        if (transitionViewState3 == null) {
            transitionViewState4 = new TransitionViewState();
            transitionLayoutController = this;
        } else {
            transitionLayoutController = this;
            transitionViewState4 = transitionViewState3;
        }
        TransitionLayout transitionLayout3 = transitionLayoutController.transitionLayout;
        if (transitionLayout3 == null) {
            return transitionViewState4;
        }
        int childCount = transitionLayout3.getChildCount();
        int i4 = 0;
        while (i4 < childCount) {
            int i5 = i4 + 1;
            int id = transitionLayout3.getChildAt(i4).getId();
            WidgetState widgetState2 = (WidgetState) transitionViewState4.widgetStates.get(Integer.valueOf(id));
            if (widgetState2 == null) {
                widgetState2 = new WidgetState(511);
            }
            WidgetState widgetState3 = (WidgetState) transitionViewState5.widgetStates.get(Integer.valueOf(id));
            if (widgetState3 == null || (widgetState = (WidgetState) transitionViewState6.widgetStates.get(Integer.valueOf(id))) == null) {
                transitionLayout2 = transitionLayout3;
                i = childCount;
            } else {
                boolean z = widgetState3.gone;
                if (z != widgetState.gone) {
                    boolean z2 = true;
                    if (z) {
                        f4 = MathUtils.map(0.8f, 1.0f, 0.0f, 1.0f, f9);
                        if (f9 >= 0.8f) {
                            z2 = false;
                        }
                        float f10 = widgetState.scale;
                        f3 = MathUtils.lerp(0.8f * f10, f10, f9);
                        i2 = widgetState.measureWidth;
                        i3 = widgetState.measureHeight;
                        transitionLayout2 = transitionLayout3;
                        float lerp = MathUtils.lerp(widgetState3.f84x - (((float) i2) / 2.0f), widgetState.f84x, f9);
                        f8 = MathUtils.lerp(widgetState3.f85y - (((float) i3) / 2.0f), widgetState.f85y, f9);
                        f7 = lerp;
                        f2 = 1.0f;
                        i = childCount;
                    } else {
                        transitionLayout2 = transitionLayout3;
                        f4 = MathUtils.map(0.0f, 0.19999999f, 0.0f, 1.0f, f9);
                        if (f9 <= 0.19999999f) {
                            z2 = false;
                        }
                        float f11 = widgetState3.scale;
                        f3 = MathUtils.lerp(f11, 0.8f * f11, f9);
                        int i6 = widgetState3.measureWidth;
                        i3 = widgetState3.measureHeight;
                        i = childCount;
                        float lerp2 = MathUtils.lerp(widgetState3.f84x, widgetState.f84x - (((float) i6) / 2.0f), f9);
                        int i7 = i6;
                        f8 = MathUtils.lerp(widgetState3.f85y, widgetState.f85y - (((float) i3) / 2.0f), f9);
                        f7 = lerp2;
                        i2 = i7;
                        f2 = 0.0f;
                    }
                    f5 = f8;
                    widgetState2.gone = z2;
                    f6 = f7;
                } else {
                    transitionLayout2 = transitionLayout3;
                    i = childCount;
                    widgetState2.gone = z;
                    i2 = widgetState.measureWidth;
                    i3 = widgetState.measureHeight;
                    f3 = MathUtils.lerp(widgetState3.scale, widgetState.scale, f9);
                    f6 = MathUtils.lerp(widgetState3.f84x, widgetState.f84x, f9);
                    f5 = MathUtils.lerp(widgetState3.f85y, widgetState.f85y, f9);
                    f4 = f9;
                    f2 = f4;
                }
                widgetState2.f84x = f6;
                widgetState2.f85y = f5;
                widgetState2.alpha = MathUtils.lerp(widgetState3.alpha, widgetState.alpha, f4);
                widgetState2.width = (int) MathUtils.lerp((float) widgetState3.width, (float) widgetState.width, f2);
                widgetState2.height = (int) MathUtils.lerp((float) widgetState3.height, (float) widgetState.height, f2);
                widgetState2.scale = f3;
                widgetState2.measureWidth = i2;
                widgetState2.measureHeight = i3;
                transitionViewState4.widgetStates.put(Integer.valueOf(id), widgetState2);
            }
            i4 = i5;
            transitionLayout3 = transitionLayout2;
            childCount = i;
        }
        transitionViewState4.width = (int) MathUtils.lerp((float) transitionViewState5.width, (float) transitionViewState6.width, f9);
        transitionViewState4.height = (int) MathUtils.lerp((float) transitionViewState5.height, (float) transitionViewState6.height, f9);
        transitionViewState4.translation.x = MathUtils.lerp(transitionViewState5.translation.x, transitionViewState6.translation.x, f9);
        transitionViewState4.translation.y = MathUtils.lerp(transitionViewState5.translation.y, transitionViewState6.translation.y, f9);
        transitionViewState4.alpha = MathUtils.lerp(transitionViewState5.alpha, transitionViewState6.alpha, f9);
        transitionViewState4.contentTranslation.x = MathUtils.lerp(transitionViewState5.contentTranslation.x, transitionViewState6.contentTranslation.x, f9);
        transitionViewState4.contentTranslation.y = MathUtils.lerp(transitionViewState5.contentTranslation.y, transitionViewState6.contentTranslation.y, f9);
        return transitionViewState4;
    }

    public final void setMeasureState(TransitionViewState transitionViewState) {
        TransitionLayout transitionLayout2 = this.transitionLayout;
        if (transitionLayout2 != null) {
            int i = transitionViewState.width;
            int i2 = transitionViewState.height;
            if (i != transitionLayout2.desiredMeasureWidth || i2 != transitionLayout2.desiredMeasureHeight) {
                transitionLayout2.desiredMeasureWidth = i;
                transitionLayout2.desiredMeasureHeight = i2;
                if (transitionLayout2.isInLayout()) {
                    transitionLayout2.forceLayout();
                } else {
                    transitionLayout2.requestLayout();
                }
            }
        }
    }

    public TransitionLayoutController() {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        this.animator = ofFloat;
        ofFloat.addUpdateListener(new TransitionLayoutController$1$1(this));
        ofFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
    }
}
