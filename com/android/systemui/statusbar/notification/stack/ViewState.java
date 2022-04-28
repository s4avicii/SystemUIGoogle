package com.android.systemui.statusbar.notification.stack;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Paint;
import android.util.ArrayMap;
import android.util.Property;
import android.view.View;
import android.view.animation.Interpolator;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dumpable;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.statusbar.notification.AnimatableProperty;
import com.android.systemui.statusbar.notification.NotificationFadeAware;
import com.android.systemui.statusbar.notification.PropertyAnimator;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;

public class ViewState implements Dumpable {
    public static final C13921 NO_NEW_ANIMATIONS = new AnimationProperties() {
        public AnimationFilter mAnimationFilter = new AnimationFilter();

        public final AnimationFilter getAnimationFilter() {
            return this.mAnimationFilter;
        }
    };
    public static final C13932 SCALE_X_PROPERTY = new AnimatableProperty() {
        public final int getAnimationEndTag() {
            return C1777R.C1779id.scale_x_animator_end_value_tag;
        }

        public final int getAnimationStartTag() {
            return C1777R.C1779id.scale_x_animator_start_value_tag;
        }

        public final int getAnimatorTag() {
            return C1777R.C1779id.scale_x_animator_tag;
        }

        public final Property getProperty() {
            return View.SCALE_X;
        }
    };
    public static final C13943 SCALE_Y_PROPERTY = new AnimatableProperty() {
        public final int getAnimationEndTag() {
            return C1777R.C1779id.scale_y_animator_end_value_tag;
        }

        public final int getAnimationStartTag() {
            return C1777R.C1779id.scale_y_animator_start_value_tag;
        }

        public final int getAnimatorTag() {
            return C1777R.C1779id.scale_y_animator_tag;
        }

        public final Property getProperty() {
            return View.SCALE_Y;
        }
    };
    public static final int TAG_ANIMATOR_ALPHA = C1777R.C1779id.alpha_animator_tag;
    public static final int TAG_ANIMATOR_TRANSLATION_X = C1777R.C1779id.translation_x_animator_tag;
    public static final int TAG_ANIMATOR_TRANSLATION_Y = C1777R.C1779id.translation_y_animator_tag;
    public static final int TAG_ANIMATOR_TRANSLATION_Z = C1777R.C1779id.translation_z_animator_tag;
    public static final int TAG_END_ALPHA = C1777R.C1779id.alpha_animator_end_value_tag;
    public static final int TAG_END_TRANSLATION_X = C1777R.C1779id.translation_x_animator_end_value_tag;
    public static final int TAG_END_TRANSLATION_Y = C1777R.C1779id.translation_y_animator_end_value_tag;
    public static final int TAG_END_TRANSLATION_Z = C1777R.C1779id.translation_z_animator_end_value_tag;
    public static final int TAG_START_ALPHA = C1777R.C1779id.alpha_animator_start_value_tag;
    public static final int TAG_START_TRANSLATION_X = C1777R.C1779id.translation_x_animator_start_value_tag;
    public static final int TAG_START_TRANSLATION_Y = C1777R.C1779id.translation_y_animator_start_value_tag;
    public static final int TAG_START_TRANSLATION_Z = C1777R.C1779id.translation_z_animator_start_value_tag;
    public float alpha;
    public boolean gone;
    public boolean hidden;
    public float scaleX = 1.0f;
    public float scaleY = 1.0f;
    public float xTranslation;
    public float yTranslation;
    public float zTranslation;

    public final boolean isAnimating(View view) {
        if (view.getTag(TAG_ANIMATOR_TRANSLATION_X) != null) {
            return true;
        }
        if (view.getTag(TAG_ANIMATOR_TRANSLATION_Y) != null) {
            return true;
        }
        if (view.getTag(TAG_ANIMATOR_TRANSLATION_Z) != null) {
            return true;
        }
        return (view.getTag(TAG_ANIMATOR_ALPHA) != null) || isAnimating(view, SCALE_X_PROPERTY) || isAnimating(view, SCALE_Y_PROPERTY);
    }

    public static long cancelAnimatorAndGetNewDuration(long j, ValueAnimator valueAnimator) {
        if (valueAnimator == null) {
            return j;
        }
        long max = Math.max(valueAnimator.getDuration() - valueAnimator.getCurrentPlayTime(), j);
        valueAnimator.cancel();
        return max;
    }

    public static float getFinalTranslationY(ExpandableView expandableView) {
        if (expandableView == null) {
            return 0.0f;
        }
        if (((ValueAnimator) expandableView.getTag(TAG_ANIMATOR_TRANSLATION_Y)) == null) {
            return expandableView.getTranslationY();
        }
        return ((Float) expandableView.getTag(TAG_END_TRANSLATION_Y)).floatValue();
    }

    public static void startAnimator(ValueAnimator valueAnimator, AnimatorListenerAdapter animatorListenerAdapter) {
        if (animatorListenerAdapter != null) {
            animatorListenerAdapter.onAnimationStart(valueAnimator);
        }
        valueAnimator.start();
    }

    public void applyToView(View view) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        int i;
        if (!this.gone) {
            boolean z8 = true;
            int i2 = 0;
            if (view.getTag(TAG_ANIMATOR_TRANSLATION_X) != null) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                startXTranslationAnimation(view, NO_NEW_ANIMATIONS);
            } else {
                float translationX = view.getTranslationX();
                float f = this.xTranslation;
                if (translationX != f) {
                    view.setTranslationX(f);
                }
            }
            if (view.getTag(TAG_ANIMATOR_TRANSLATION_Y) != null) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2) {
                startYTranslationAnimation(view, NO_NEW_ANIMATIONS);
            } else {
                float translationY = view.getTranslationY();
                float f2 = this.yTranslation;
                if (translationY != f2) {
                    view.setTranslationY(f2);
                }
            }
            if (view.getTag(TAG_ANIMATOR_TRANSLATION_Z) != null) {
                z3 = true;
            } else {
                z3 = false;
            }
            if (z3) {
                startZTranslationAnimation(view, NO_NEW_ANIMATIONS);
            } else {
                float translationZ = view.getTranslationZ();
                float f3 = this.zTranslation;
                if (translationZ != f3) {
                    view.setTranslationZ(f3);
                }
            }
            C13932 r0 = SCALE_X_PROPERTY;
            if (isAnimating(view, r0)) {
                PropertyAnimator.startAnimation(view, r0, this.scaleX, NO_NEW_ANIMATIONS);
            } else {
                float scaleX2 = view.getScaleX();
                float f4 = this.scaleX;
                if (scaleX2 != f4) {
                    view.setScaleX(f4);
                }
            }
            C13943 r02 = SCALE_Y_PROPERTY;
            if (isAnimating(view, r02)) {
                PropertyAnimator.startAnimation(view, r02, this.scaleY, NO_NEW_ANIMATIONS);
            } else {
                float scaleY2 = view.getScaleY();
                float f5 = this.scaleY;
                if (scaleY2 != f5) {
                    view.setScaleY(f5);
                }
            }
            int visibility = view.getVisibility();
            if (this.alpha == 0.0f || (this.hidden && (!isAnimating(view) || visibility != 0))) {
                z4 = true;
            } else {
                z4 = false;
            }
            if (view.getTag(TAG_ANIMATOR_ALPHA) != null) {
                z5 = true;
            } else {
                z5 = false;
            }
            if (z5) {
                startAlphaAnimation(view, NO_NEW_ANIMATIONS);
            } else {
                float alpha2 = view.getAlpha();
                float f6 = this.alpha;
                if (alpha2 != f6) {
                    if (f6 == 1.0f) {
                        z6 = true;
                    } else {
                        z6 = false;
                    }
                    if (z4 || z6) {
                        z7 = false;
                    } else {
                        z7 = true;
                    }
                    if (view instanceof NotificationFadeAware.FadeOptimizedNotification) {
                        NotificationFadeAware.FadeOptimizedNotification fadeOptimizedNotification = (NotificationFadeAware.FadeOptimizedNotification) view;
                        if (fadeOptimizedNotification.isNotificationFaded() != z7) {
                            fadeOptimizedNotification.setNotificationFaded(z7);
                        }
                    } else {
                        if (!z7 || !view.hasOverlappingRendering()) {
                            z8 = false;
                        }
                        int layerType = view.getLayerType();
                        if (z8) {
                            i = 2;
                        } else {
                            i = 0;
                        }
                        if (layerType != i) {
                            view.setLayerType(i, (Paint) null);
                        }
                    }
                    view.setAlpha(this.alpha);
                }
            }
            if (z4) {
                i2 = 4;
            }
            if (i2 == visibility) {
                return;
            }
            if (!(view instanceof ExpandableView) || !((ExpandableView) view).mWillBeGone) {
                view.setVisibility(i2);
            }
        }
    }

    public void cancelAnimations(View view) {
        Animator animator = (Animator) view.getTag(TAG_ANIMATOR_TRANSLATION_X);
        if (animator != null) {
            animator.cancel();
        }
        Animator animator2 = (Animator) view.getTag(TAG_ANIMATOR_TRANSLATION_Y);
        if (animator2 != null) {
            animator2.cancel();
        }
        Animator animator3 = (Animator) view.getTag(TAG_ANIMATOR_TRANSLATION_Z);
        if (animator3 != null) {
            animator3.cancel();
        }
        Animator animator4 = (Animator) view.getTag(TAG_ANIMATOR_ALPHA);
        if (animator4 != null) {
            animator4.cancel();
        }
    }

    public void copyFrom(ExpandableViewState expandableViewState) {
        this.alpha = expandableViewState.alpha;
        this.xTranslation = expandableViewState.xTranslation;
        this.yTranslation = expandableViewState.yTranslation;
        this.zTranslation = expandableViewState.zTranslation;
        this.gone = expandableViewState.gone;
        this.hidden = expandableViewState.hidden;
        this.scaleX = expandableViewState.scaleX;
        this.scaleY = expandableViewState.scaleY;
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("ViewState { ");
        boolean z = true;
        for (Class cls = getClass(); cls != null; cls = cls.getSuperclass()) {
            for (Field field : cls.getDeclaredFields()) {
                int modifiers = field.getModifiers();
                if (!Modifier.isStatic(modifiers) && !field.isSynthetic() && !Modifier.isTransient(modifiers)) {
                    if (!z) {
                        m.append(", ");
                    }
                    try {
                        m.append(field.getName());
                        m.append(": ");
                        field.setAccessible(true);
                        m.append(field.get(this));
                    } catch (IllegalAccessException unused) {
                    }
                    z = false;
                }
            }
        }
        m.append(" }");
        printWriter.print(m);
    }

    public void onYTranslationAnimationFinished(View view) {
        if (this.hidden && !this.gone) {
            view.setVisibility(4);
        }
    }

    public final void startAlphaAnimation(final View view, AnimationProperties animationProperties) {
        int i = TAG_START_ALPHA;
        Float f = (Float) view.getTag(i);
        int i2 = TAG_END_ALPHA;
        Float f2 = (Float) view.getTag(i2);
        final float f3 = this.alpha;
        if (f2 == null || f2.floatValue() != f3) {
            int i3 = TAG_ANIMATOR_ALPHA;
            ObjectAnimator objectAnimator = (ObjectAnimator) view.getTag(i3);
            if (!animationProperties.getAnimationFilter().animateAlpha) {
                if (objectAnimator != null) {
                    PropertyValuesHolder[] values = objectAnimator.getValues();
                    float floatValue = f.floatValue() + (f3 - f2.floatValue());
                    values[0].setFloatValues(new float[]{floatValue, f3});
                    view.setTag(i, Float.valueOf(floatValue));
                    view.setTag(i2, Float.valueOf(f3));
                    objectAnimator.setCurrentPlayTime(objectAnimator.getCurrentPlayTime());
                    return;
                }
                view.setAlpha(f3);
                if (f3 == 0.0f) {
                    view.setVisibility(4);
                }
            }
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, View.ALPHA, new float[]{view.getAlpha(), f3});
            ofFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
            view.setLayerType(2, (Paint) null);
            ofFloat.addListener(new AnimatorListenerAdapter() {
                public boolean mWasCancelled;

                public final void onAnimationCancel(Animator animator) {
                    this.mWasCancelled = true;
                }

                public final void onAnimationStart(Animator animator) {
                    this.mWasCancelled = false;
                }

                public final void onAnimationEnd(Animator animator) {
                    view.setLayerType(0, (Paint) null);
                    if (f3 == 0.0f && !this.mWasCancelled) {
                        view.setVisibility(4);
                    }
                    view.setTag(ViewState.TAG_ANIMATOR_ALPHA, (Object) null);
                    view.setTag(ViewState.TAG_START_ALPHA, (Object) null);
                    view.setTag(ViewState.TAG_END_ALPHA, (Object) null);
                }
            });
            ofFloat.setDuration(cancelAnimatorAndGetNewDuration(animationProperties.duration, objectAnimator));
            if (animationProperties.delay > 0 && (objectAnimator == null || objectAnimator.getAnimatedFraction() == 0.0f)) {
                ofFloat.setStartDelay(animationProperties.delay);
            }
            AnimatorListenerAdapter animationFinishListener = animationProperties.getAnimationFinishListener(View.ALPHA);
            if (animationFinishListener != null) {
                ofFloat.addListener(animationFinishListener);
            }
            startAnimator(ofFloat, animationFinishListener);
            view.setTag(i3, ofFloat);
            view.setTag(i, Float.valueOf(view.getAlpha()));
            view.setTag(i2, Float.valueOf(f3));
        }
    }

    public final void startXTranslationAnimation(final View view, AnimationProperties animationProperties) {
        TimeInterpolator timeInterpolator;
        int i = TAG_START_TRANSLATION_X;
        Float f = (Float) view.getTag(i);
        int i2 = TAG_END_TRANSLATION_X;
        Float f2 = (Float) view.getTag(i2);
        float f3 = this.xTranslation;
        if (f2 == null || f2.floatValue() != f3) {
            int i3 = TAG_ANIMATOR_TRANSLATION_X;
            ObjectAnimator objectAnimator = (ObjectAnimator) view.getTag(i3);
            if (animationProperties.getAnimationFilter().animateX) {
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, new float[]{view.getTranslationX(), f3});
                Property property = View.TRANSLATION_X;
                ArrayMap<Property, Interpolator> arrayMap = animationProperties.mInterpolatorMap;
                if (arrayMap != null) {
                    timeInterpolator = arrayMap.get(property);
                } else {
                    timeInterpolator = null;
                }
                if (timeInterpolator == null) {
                    timeInterpolator = Interpolators.FAST_OUT_SLOW_IN;
                }
                ofFloat.setInterpolator(timeInterpolator);
                ofFloat.setDuration(cancelAnimatorAndGetNewDuration(animationProperties.duration, objectAnimator));
                if (animationProperties.delay > 0 && (objectAnimator == null || objectAnimator.getAnimatedFraction() == 0.0f)) {
                    ofFloat.setStartDelay(animationProperties.delay);
                }
                AnimatorListenerAdapter animationFinishListener = animationProperties.getAnimationFinishListener(View.TRANSLATION_X);
                if (animationFinishListener != null) {
                    ofFloat.addListener(animationFinishListener);
                }
                ofFloat.addListener(new AnimatorListenerAdapter() {
                    public final void onAnimationEnd(Animator animator) {
                        view.setTag(ViewState.TAG_ANIMATOR_TRANSLATION_X, (Object) null);
                        view.setTag(ViewState.TAG_START_TRANSLATION_X, (Object) null);
                        view.setTag(ViewState.TAG_END_TRANSLATION_X, (Object) null);
                    }
                });
                startAnimator(ofFloat, animationFinishListener);
                view.setTag(i3, ofFloat);
                view.setTag(i, Float.valueOf(view.getTranslationX()));
                view.setTag(i2, Float.valueOf(f3));
            } else if (objectAnimator != null) {
                PropertyValuesHolder[] values = objectAnimator.getValues();
                float floatValue = f.floatValue() + (f3 - f2.floatValue());
                values[0].setFloatValues(new float[]{floatValue, f3});
                view.setTag(i, Float.valueOf(floatValue));
                view.setTag(i2, Float.valueOf(f3));
                objectAnimator.setCurrentPlayTime(objectAnimator.getCurrentPlayTime());
            } else {
                view.setTranslationX(f3);
            }
        }
    }

    public final void startYTranslationAnimation(final View view, AnimationProperties animationProperties) {
        boolean z;
        TimeInterpolator timeInterpolator;
        int i = TAG_START_TRANSLATION_Y;
        Float f = (Float) view.getTag(i);
        int i2 = TAG_END_TRANSLATION_Y;
        Float f2 = (Float) view.getTag(i2);
        float f3 = this.yTranslation;
        if (f2 == null || f2.floatValue() != f3) {
            int i3 = TAG_ANIMATOR_TRANSLATION_Y;
            ObjectAnimator objectAnimator = (ObjectAnimator) view.getTag(i3);
            AnimationFilter animationFilter = animationProperties.getAnimationFilter();
            Objects.requireNonNull(animationFilter);
            if (animationFilter.animateY || animationFilter.animateYViews.contains(view)) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, new float[]{view.getTranslationY(), f3});
                Property property = View.TRANSLATION_Y;
                ArrayMap<Property, Interpolator> arrayMap = animationProperties.mInterpolatorMap;
                if (arrayMap != null) {
                    timeInterpolator = arrayMap.get(property);
                } else {
                    timeInterpolator = null;
                }
                if (timeInterpolator == null) {
                    timeInterpolator = Interpolators.FAST_OUT_SLOW_IN;
                }
                ofFloat.setInterpolator(timeInterpolator);
                ofFloat.setDuration(cancelAnimatorAndGetNewDuration(animationProperties.duration, objectAnimator));
                if (animationProperties.delay > 0 && (objectAnimator == null || objectAnimator.getAnimatedFraction() == 0.0f)) {
                    ofFloat.setStartDelay(animationProperties.delay);
                }
                AnimatorListenerAdapter animationFinishListener = animationProperties.getAnimationFinishListener(View.TRANSLATION_Y);
                if (animationFinishListener != null) {
                    ofFloat.addListener(animationFinishListener);
                }
                ofFloat.addListener(new AnimatorListenerAdapter() {
                    public final void onAnimationEnd(Animator animator) {
                        view.setTag(C1777R.C1779id.is_clicked_heads_up_tag, (Object) null);
                        view.setTag(ViewState.TAG_ANIMATOR_TRANSLATION_Y, (Object) null);
                        view.setTag(ViewState.TAG_START_TRANSLATION_Y, (Object) null);
                        view.setTag(ViewState.TAG_END_TRANSLATION_Y, (Object) null);
                        ViewState.this.onYTranslationAnimationFinished(view);
                    }
                });
                startAnimator(ofFloat, animationFinishListener);
                view.setTag(i3, ofFloat);
                view.setTag(i, Float.valueOf(view.getTranslationY()));
                view.setTag(i2, Float.valueOf(f3));
            } else if (objectAnimator != null) {
                PropertyValuesHolder[] values = objectAnimator.getValues();
                float floatValue = f.floatValue() + (f3 - f2.floatValue());
                values[0].setFloatValues(new float[]{floatValue, f3});
                view.setTag(i, Float.valueOf(floatValue));
                view.setTag(i2, Float.valueOf(f3));
                objectAnimator.setCurrentPlayTime(objectAnimator.getCurrentPlayTime());
            } else {
                view.setTranslationY(f3);
            }
        }
    }

    public final void startZTranslationAnimation(final View view, AnimationProperties animationProperties) {
        int i = TAG_START_TRANSLATION_Z;
        Float f = (Float) view.getTag(i);
        int i2 = TAG_END_TRANSLATION_Z;
        Float f2 = (Float) view.getTag(i2);
        float f3 = this.zTranslation;
        if (f2 == null || f2.floatValue() != f3) {
            int i3 = TAG_ANIMATOR_TRANSLATION_Z;
            ObjectAnimator objectAnimator = (ObjectAnimator) view.getTag(i3);
            if (!animationProperties.getAnimationFilter().animateZ) {
                if (objectAnimator != null) {
                    PropertyValuesHolder[] values = objectAnimator.getValues();
                    float floatValue = f.floatValue() + (f3 - f2.floatValue());
                    values[0].setFloatValues(new float[]{floatValue, f3});
                    view.setTag(i, Float.valueOf(floatValue));
                    view.setTag(i2, Float.valueOf(f3));
                    objectAnimator.setCurrentPlayTime(objectAnimator.getCurrentPlayTime());
                    return;
                }
                view.setTranslationZ(f3);
            }
            Property property = View.TRANSLATION_Z;
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, property, new float[]{view.getTranslationZ(), f3});
            ofFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
            ofFloat.setDuration(cancelAnimatorAndGetNewDuration(animationProperties.duration, objectAnimator));
            if (animationProperties.delay > 0 && (objectAnimator == null || objectAnimator.getAnimatedFraction() == 0.0f)) {
                ofFloat.setStartDelay(animationProperties.delay);
            }
            AnimatorListenerAdapter animationFinishListener = animationProperties.getAnimationFinishListener(property);
            if (animationFinishListener != null) {
                ofFloat.addListener(animationFinishListener);
            }
            ofFloat.addListener(new AnimatorListenerAdapter() {
                public final void onAnimationEnd(Animator animator) {
                    view.setTag(ViewState.TAG_ANIMATOR_TRANSLATION_Z, (Object) null);
                    view.setTag(ViewState.TAG_START_TRANSLATION_Z, (Object) null);
                    view.setTag(ViewState.TAG_END_TRANSLATION_Z, (Object) null);
                }
            });
            startAnimator(ofFloat, animationFinishListener);
            view.setTag(i3, ofFloat);
            view.setTag(i, Float.valueOf(view.getTranslationZ()));
            view.setTag(i2, Float.valueOf(f3));
        }
    }

    public static void abortAnimation(View view, int i) {
        Animator animator = (Animator) view.getTag(i);
        if (animator != null) {
            animator.cancel();
        }
    }

    public void animateTo(View view, AnimationProperties animationProperties) {
        boolean z;
        boolean z2 = false;
        if (view.getVisibility() == 0) {
            z = true;
        } else {
            z = false;
        }
        float f = this.alpha;
        if (!z && (!(f == 0.0f && view.getAlpha() == 0.0f) && !this.gone && !this.hidden)) {
            view.setVisibility(0);
        }
        if (this.alpha != view.getAlpha()) {
            z2 = true;
        }
        if (view instanceof ExpandableView) {
            z2 &= !((ExpandableView) view).mWillBeGone;
        }
        if (view.getTranslationX() != this.xTranslation) {
            startXTranslationAnimation(view, animationProperties);
        } else {
            abortAnimation(view, TAG_ANIMATOR_TRANSLATION_X);
        }
        if (view.getTranslationY() != this.yTranslation) {
            startYTranslationAnimation(view, animationProperties);
        } else {
            abortAnimation(view, TAG_ANIMATOR_TRANSLATION_Y);
        }
        if (view.getTranslationZ() != this.zTranslation) {
            startZTranslationAnimation(view, animationProperties);
        } else {
            abortAnimation(view, TAG_ANIMATOR_TRANSLATION_Z);
        }
        float scaleX2 = view.getScaleX();
        float f2 = this.scaleX;
        if (scaleX2 != f2) {
            PropertyAnimator.startAnimation(view, SCALE_X_PROPERTY, f2, animationProperties);
        } else {
            Objects.requireNonNull(SCALE_X_PROPERTY);
            abortAnimation(view, C1777R.C1779id.scale_x_animator_tag);
        }
        float scaleY2 = view.getScaleY();
        float f3 = this.scaleY;
        if (scaleY2 != f3) {
            PropertyAnimator.startAnimation(view, SCALE_Y_PROPERTY, f3, animationProperties);
        } else {
            Objects.requireNonNull(SCALE_Y_PROPERTY);
            abortAnimation(view, C1777R.C1779id.scale_y_animator_tag);
        }
        if (z2) {
            startAlphaAnimation(view, animationProperties);
        } else {
            abortAnimation(view, TAG_ANIMATOR_ALPHA);
        }
    }

    public void initFrom(View view) {
        boolean z;
        this.alpha = view.getAlpha();
        this.xTranslation = view.getTranslationX();
        this.yTranslation = view.getTranslationY();
        this.zTranslation = view.getTranslationZ();
        boolean z2 = true;
        if (view.getVisibility() == 8) {
            z = true;
        } else {
            z = false;
        }
        this.gone = z;
        if (view.getVisibility() != 4) {
            z2 = false;
        }
        this.hidden = z2;
        this.scaleX = view.getScaleX();
        this.scaleY = view.getScaleY();
    }

    public static boolean isAnimating(View view, AnimatableProperty animatableProperty) {
        return view.getTag(animatableProperty.getAnimatorTag()) != null;
    }
}
