package com.android.systemui.statusbar.notification;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.util.ArrayMap;
import android.util.Property;
import android.view.View;
import android.view.animation.Interpolator;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.statusbar.notification.stack.AnimationFilter;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.notification.stack.ViewState;

public final class PropertyAnimator {
    public static <T extends View> void setProperty(T t, AnimatableProperty animatableProperty, float f, AnimationProperties animationProperties, boolean z) {
        int animatorTag = animatableProperty.getAnimatorTag();
        ViewState.C13921 r1 = ViewState.NO_NEW_ANIMATIONS;
        if (((ValueAnimator) t.getTag(animatorTag)) != null || z) {
            if (!z) {
                animationProperties = null;
            }
            startAnimation(t, animatableProperty, f, animationProperties);
            return;
        }
        animatableProperty.getProperty().set(t, Float.valueOf(f));
    }

    public static <T extends View> void startAnimation(final T t, AnimatableProperty animatableProperty, float f, AnimationProperties animationProperties) {
        AnimationFilter animationFilter;
        Property property = animatableProperty.getProperty();
        final int animationStartTag = animatableProperty.getAnimationStartTag();
        final int animationEndTag = animatableProperty.getAnimationEndTag();
        ViewState.C13921 r3 = ViewState.NO_NEW_ANIMATIONS;
        Float f2 = (Float) t.getTag(animationStartTag);
        Float f3 = (Float) t.getTag(animationEndTag);
        if (f3 == null || f3.floatValue() != f) {
            final int animatorTag = animatableProperty.getAnimatorTag();
            ValueAnimator valueAnimator = (ValueAnimator) t.getTag(animatorTag);
            Interpolator interpolator = null;
            if (animationProperties != null) {
                animationFilter = animationProperties.getAnimationFilter();
            } else {
                animationFilter = null;
            }
            if (animationFilter != null && animationFilter.shouldAnimateProperty(property)) {
                Float f4 = (Float) property.get(t);
                AnimatorListenerAdapter animationFinishListener = animationProperties.getAnimationFinishListener(property);
                if (f4.equals(Float.valueOf(f))) {
                    if (valueAnimator != null) {
                        valueAnimator.cancel();
                    }
                    if (animationFinishListener != null) {
                        animationFinishListener.onAnimationEnd((Animator) null);
                        return;
                    }
                    return;
                }
                ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{f4.floatValue(), f});
                ofFloat.addUpdateListener(new PropertyAnimator$$ExternalSyntheticLambda0(property, t));
                ArrayMap<Property, Interpolator> arrayMap = animationProperties.mInterpolatorMap;
                if (arrayMap != null) {
                    interpolator = arrayMap.get(property);
                }
                if (interpolator == null) {
                    interpolator = Interpolators.FAST_OUT_SLOW_IN;
                }
                ofFloat.setInterpolator(interpolator);
                ofFloat.setDuration(ViewState.cancelAnimatorAndGetNewDuration(animationProperties.duration, valueAnimator));
                if (animationProperties.delay > 0 && (valueAnimator == null || valueAnimator.getAnimatedFraction() == 0.0f)) {
                    ofFloat.setStartDelay(animationProperties.delay);
                }
                if (animationFinishListener != null) {
                    ofFloat.addListener(animationFinishListener);
                }
                ofFloat.addListener(new AnimatorListenerAdapter() {
                    public final void onAnimationEnd(Animator animator) {
                        t.setTag(animatorTag, (Object) null);
                        t.setTag(animationStartTag, (Object) null);
                        t.setTag(animationEndTag, (Object) null);
                    }
                });
                ViewState.startAnimator(ofFloat, animationFinishListener);
                t.setTag(animatorTag, ofFloat);
                t.setTag(animationStartTag, f4);
                t.setTag(animationEndTag, Float.valueOf(f));
            } else if (valueAnimator != null) {
                PropertyValuesHolder[] values = valueAnimator.getValues();
                float floatValue = f2.floatValue() + (f - f3.floatValue());
                values[0].setFloatValues(new float[]{floatValue, f});
                t.setTag(animationStartTag, Float.valueOf(floatValue));
                t.setTag(animationEndTag, Float.valueOf(f));
                valueAnimator.setCurrentPlayTime(valueAnimator.getCurrentPlayTime());
            } else {
                property.set(t, Float.valueOf(f));
            }
        }
    }
}
