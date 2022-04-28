package androidx.leanback.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.BaseInterpolator;
import android.view.animation.DecelerateInterpolator;
import androidx.leanback.R$styleable;
import com.android.p012wm.shell.C1777R;

class SlideKitkat extends Visibility {
    public static final AccelerateInterpolator sAccelerate = new AccelerateInterpolator();
    public static final C02144 sCalculateBottom = new CalculateSlideVertical() {
        public final float getGone(View view) {
            return view.getTranslationY() + ((float) view.getHeight());
        }
    };
    public static final C02166 sCalculateEnd = new CalculateSlideHorizontal() {
        public final float getGone(View view) {
            if (view.getLayoutDirection() == 1) {
                return view.getTranslationX() - ((float) view.getWidth());
            }
            return view.getTranslationX() + ((float) view.getWidth());
        }
    };
    public static final C02111 sCalculateLeft = new CalculateSlideHorizontal() {
        public final float getGone(View view) {
            return view.getTranslationX() - ((float) view.getWidth());
        }
    };
    public static final C02133 sCalculateRight = new CalculateSlideHorizontal() {
        public final float getGone(View view) {
            return view.getTranslationX() + ((float) view.getWidth());
        }
    };
    public static final C02155 sCalculateStart = new CalculateSlideHorizontal() {
        public final float getGone(View view) {
            if (view.getLayoutDirection() == 1) {
                return view.getTranslationX() + ((float) view.getWidth());
            }
            return view.getTranslationX() - ((float) view.getWidth());
        }
    };
    public static final C02122 sCalculateTop = new CalculateSlideVertical() {
        public final float getGone(View view) {
            return view.getTranslationY() - ((float) view.getHeight());
        }
    };
    public static final DecelerateInterpolator sDecelerate = new DecelerateInterpolator();
    public CalculateSlide mSlideCalculator;

    public interface CalculateSlide {
        float getGone(View view);

        float getHere(View view);

        Property<View, Float> getProperty();
    }

    public static class SlideAnimatorListener extends AnimatorListenerAdapter {
        public boolean mCanceled = false;
        public final float mEndValue;
        public final int mFinalVisibility;
        public float mPausedValue;
        public final Property<View, Float> mProp;
        public final float mTerminalValue;
        public final View mView;

        public final void onAnimationCancel(Animator animator) {
            this.mView.setTag(C1777R.C1779id.lb_slide_transition_value, new float[]{this.mView.getTranslationX(), this.mView.getTranslationY()});
            this.mProp.set(this.mView, Float.valueOf(this.mTerminalValue));
            this.mCanceled = true;
        }

        public final void onAnimationEnd(Animator animator) {
            if (!this.mCanceled) {
                this.mProp.set(this.mView, Float.valueOf(this.mTerminalValue));
            }
            this.mView.setVisibility(this.mFinalVisibility);
        }

        public final void onAnimationPause(Animator animator) {
            this.mPausedValue = this.mProp.get(this.mView).floatValue();
            this.mProp.set(this.mView, Float.valueOf(this.mEndValue));
            this.mView.setVisibility(this.mFinalVisibility);
        }

        public final void onAnimationResume(Animator animator) {
            this.mProp.set(this.mView, Float.valueOf(this.mPausedValue));
            this.mView.setVisibility(0);
        }

        public SlideAnimatorListener(View view, Property<View, Float> property, float f, float f2, int i) {
            this.mProp = property;
            this.mView = view;
            this.mTerminalValue = f;
            this.mEndValue = f2;
            this.mFinalVisibility = i;
            view.setVisibility(0);
        }
    }

    public SlideKitkat() {
        setSlideEdge(80);
    }

    public final Animator onAppear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        View view;
        if (transitionValues2 != null) {
            view = transitionValues2.view;
        } else {
            view = null;
        }
        if (view == null) {
            return null;
        }
        float here = this.mSlideCalculator.getHere(view);
        return createAnimation(view, this.mSlideCalculator.getProperty(), this.mSlideCalculator.getGone(view), here, here, sDecelerate, 0);
    }

    public final Animator onDisappear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        View view;
        if (transitionValues != null) {
            view = transitionValues.view;
        } else {
            view = null;
        }
        if (view == null) {
            return null;
        }
        float here = this.mSlideCalculator.getHere(view);
        return createAnimation(view, this.mSlideCalculator.getProperty(), here, this.mSlideCalculator.getGone(view), here, sAccelerate, 4);
    }

    public final void setSlideEdge(int i) {
        if (i == 3) {
            this.mSlideCalculator = sCalculateLeft;
        } else if (i == 5) {
            this.mSlideCalculator = sCalculateRight;
        } else if (i == 48) {
            this.mSlideCalculator = sCalculateTop;
        } else if (i == 80) {
            this.mSlideCalculator = sCalculateBottom;
        } else if (i == 8388611) {
            this.mSlideCalculator = sCalculateStart;
        } else if (i == 8388613) {
            this.mSlideCalculator = sCalculateEnd;
        } else {
            throw new IllegalArgumentException("Invalid slide direction");
        }
    }

    public static abstract class CalculateSlideHorizontal implements CalculateSlide {
        public final float getHere(View view) {
            return view.getTranslationX();
        }

        public final Property<View, Float> getProperty() {
            return View.TRANSLATION_X;
        }
    }

    public static abstract class CalculateSlideVertical implements CalculateSlide {
        public final float getHere(View view) {
            return view.getTranslationY();
        }

        public final Property<View, Float> getProperty() {
            return View.TRANSLATION_Y;
        }
    }

    public SlideKitkat(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.lbSlide);
        setSlideEdge(obtainStyledAttributes.getInt(3, 80));
        long j = (long) obtainStyledAttributes.getInt(1, -1);
        if (j >= 0) {
            setDuration(j);
        }
        long j2 = (long) obtainStyledAttributes.getInt(2, -1);
        if (j2 > 0) {
            setStartDelay(j2);
        }
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        if (resourceId > 0) {
            setInterpolator(AnimationUtils.loadInterpolator(context, resourceId));
        }
        obtainStyledAttributes.recycle();
    }

    public static ObjectAnimator createAnimation(View view, Property property, float f, float f2, float f3, BaseInterpolator baseInterpolator, int i) {
        float[] fArr = (float[]) view.getTag(C1777R.C1779id.lb_slide_transition_value);
        if (fArr != null) {
            if (View.TRANSLATION_Y == property) {
                f = fArr[1];
            } else {
                f = fArr[0];
            }
            view.setTag(C1777R.C1779id.lb_slide_transition_value, (Object) null);
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, property, new float[]{f, f2});
        SlideAnimatorListener slideAnimatorListener = new SlideAnimatorListener(view, property, f3, f2, i);
        ofFloat.addListener(slideAnimatorListener);
        ofFloat.addPauseListener(slideAnimatorListener);
        ofFloat.setInterpolator(baseInterpolator);
        return ofFloat;
    }
}
