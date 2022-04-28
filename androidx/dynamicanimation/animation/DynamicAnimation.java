package androidx.dynamicanimation.animation;

import android.util.AndroidRuntimeException;
import android.view.View;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.dynamicanimation.animation.AnimationHandler;
import androidx.dynamicanimation.animation.DynamicAnimation;
import java.util.ArrayList;
import java.util.Objects;
import java.util.WeakHashMap;

public abstract class DynamicAnimation<T extends DynamicAnimation<T>> implements AnimationHandler.AnimationFrameCallback {
    public static final C013912 ALPHA = new ViewProperty() {
        public final float getValue(Object obj) {
            return ((View) obj).getAlpha();
        }

        public final void setValue(Object obj, float f) {
            ((View) obj).setAlpha(f);
        }
    };
    public static final C01466 ROTATION = new ViewProperty() {
        public final float getValue(Object obj) {
            return ((View) obj).getRotation();
        }

        public final void setValue(Object obj, float f) {
            ((View) obj).setRotation(f);
        }
    };
    public static final C01477 ROTATION_X = new ViewProperty() {
        public final float getValue(Object obj) {
            return ((View) obj).getRotationX();
        }

        public final void setValue(Object obj, float f) {
            ((View) obj).setRotationX(f);
        }
    };
    public static final C01488 ROTATION_Y = new ViewProperty() {
        public final float getValue(Object obj) {
            return ((View) obj).getRotationY();
        }

        public final void setValue(Object obj, float f) {
            ((View) obj).setRotationY(f);
        }
    };
    public static final C01444 SCALE_X = new ViewProperty() {
        public final float getValue(Object obj) {
            return ((View) obj).getScaleX();
        }

        public final void setValue(Object obj, float f) {
            ((View) obj).setScaleX(f);
        }
    };
    public static final C01455 SCALE_Y = new ViewProperty() {
        public final float getValue(Object obj) {
            return ((View) obj).getScaleY();
        }

        public final void setValue(Object obj, float f) {
            ((View) obj).setScaleY(f);
        }
    };
    public static final C014013 SCROLL_X = new ViewProperty() {
        public final float getValue(Object obj) {
            return (float) ((View) obj).getScrollX();
        }

        public final void setValue(Object obj, float f) {
            ((View) obj).setScrollX((int) f);
        }
    };
    public static final C014114 SCROLL_Y = new ViewProperty() {
        public final float getValue(Object obj) {
            return (float) ((View) obj).getScrollY();
        }

        public final void setValue(Object obj, float f) {
            ((View) obj).setScrollY((int) f);
        }
    };
    public static final C01371 TRANSLATION_X = new ViewProperty() {
        public final float getValue(Object obj) {
            return ((View) obj).getTranslationX();
        }

        public final void setValue(Object obj, float f) {
            ((View) obj).setTranslationX(f);
        }
    };
    public static final C01422 TRANSLATION_Y = new ViewProperty() {
        public final float getValue(Object obj) {
            return ((View) obj).getTranslationY();
        }

        public final void setValue(Object obj, float f) {
            ((View) obj).setTranslationY(f);
        }
    };
    public static final C01433 TRANSLATION_Z = new ViewProperty() {
        public final float getValue(Object obj) {
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            return ViewCompat.Api21Impl.getTranslationZ((View) obj);
        }

        public final void setValue(Object obj, float f) {
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api21Impl.setTranslationZ((View) obj, f);
        }
    };

    /* renamed from: Y */
    public static final C013810 f22Y = new ViewProperty() {
        public final float getValue(Object obj) {
            return ((View) obj).getY();
        }

        public final void setValue(Object obj, float f) {
            ((View) obj).setY(f);
        }
    };
    public AnimationHandler mAnimationHandler;
    public final ArrayList<OnAnimationEndListener> mEndListeners = new ArrayList<>();
    public long mLastFrameTime = 0;
    public float mMaxValue = Float.MAX_VALUE;
    public float mMinValue = -3.4028235E38f;
    public float mMinVisibleChange;
    public final FloatPropertyCompat mProperty;
    public boolean mRunning = false;
    public boolean mStartValueIsSet = false;
    public final Object mTarget;
    public final ArrayList<OnAnimationUpdateListener> mUpdateListeners = new ArrayList<>();
    public float mValue = Float.MAX_VALUE;
    public float mVelocity = 0.0f;

    public static class MassState {
        public float mValue;
        public float mVelocity;
    }

    public interface OnAnimationEndListener {
        void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2);
    }

    public interface OnAnimationUpdateListener {
        void onAnimationUpdate(float f, float f2);
    }

    public static abstract class ViewProperty extends FloatPropertyCompat<View> {
    }

    public final void endAnimationInternal(boolean z) {
        this.mRunning = false;
        AnimationHandler animationHandler = getAnimationHandler();
        Objects.requireNonNull(animationHandler);
        animationHandler.mDelayedCallbackStartTime.remove(this);
        int indexOf = animationHandler.mAnimationCallbacks.indexOf(this);
        if (indexOf >= 0) {
            animationHandler.mAnimationCallbacks.set(indexOf, (Object) null);
            animationHandler.mListDirty = true;
        }
        this.mLastFrameTime = 0;
        this.mStartValueIsSet = false;
        for (int i = 0; i < this.mEndListeners.size(); i++) {
            if (this.mEndListeners.get(i) != null) {
                this.mEndListeners.get(i).onAnimationEnd(this, z, this.mValue, this.mVelocity);
            }
        }
        ArrayList<OnAnimationEndListener> arrayList = this.mEndListeners;
        int size = arrayList.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            }
            if (arrayList.get(size) == null) {
                arrayList.remove(size);
            }
        }
    }

    public abstract boolean updateValueAndVelocity(long j);

    public final T addEndListener(OnAnimationEndListener onAnimationEndListener) {
        if (!this.mEndListeners.contains(onAnimationEndListener)) {
            this.mEndListeners.add(onAnimationEndListener);
        }
        return this;
    }

    public final boolean doAnimationFrame(long j) {
        long j2 = this.mLastFrameTime;
        if (j2 == 0) {
            this.mLastFrameTime = j;
            setPropertyValue(this.mValue);
            return false;
        }
        this.mLastFrameTime = j;
        boolean updateValueAndVelocity = updateValueAndVelocity(j - j2);
        float min = Math.min(this.mValue, this.mMaxValue);
        this.mValue = min;
        float max = Math.max(min, this.mMinValue);
        this.mValue = max;
        setPropertyValue(max);
        if (updateValueAndVelocity) {
            endAnimationInternal(false);
        }
        return updateValueAndVelocity;
    }

    public final AnimationHandler getAnimationHandler() {
        if (this.mAnimationHandler == null) {
            ThreadLocal<AnimationHandler> threadLocal = AnimationHandler.sAnimatorHandler;
            if (threadLocal.get() == null) {
                threadLocal.set(new AnimationHandler(new AnimationHandler.FrameCallbackScheduler16()));
            }
            this.mAnimationHandler = threadLocal.get();
        }
        return this.mAnimationHandler;
    }

    public final void setPropertyValue(float f) {
        this.mProperty.setValue(this.mTarget, f);
        for (int i = 0; i < this.mUpdateListeners.size(); i++) {
            if (this.mUpdateListeners.get(i) != null) {
                this.mUpdateListeners.get(i).onAnimationUpdate(this.mValue, this.mVelocity);
            }
        }
        ArrayList<OnAnimationUpdateListener> arrayList = this.mUpdateListeners;
        int size = arrayList.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            }
            if (arrayList.get(size) == null) {
                arrayList.remove(size);
            }
        }
    }

    public <K> DynamicAnimation(K k, FloatPropertyCompat<K> floatPropertyCompat) {
        this.mTarget = k;
        this.mProperty = floatPropertyCompat;
        if (floatPropertyCompat == ROTATION || floatPropertyCompat == ROTATION_X || floatPropertyCompat == ROTATION_Y) {
            this.mMinVisibleChange = 0.1f;
        } else if (floatPropertyCompat == ALPHA) {
            this.mMinVisibleChange = 0.00390625f;
        } else if (floatPropertyCompat == SCALE_X || floatPropertyCompat == SCALE_Y) {
            this.mMinVisibleChange = 0.002f;
        } else {
            this.mMinVisibleChange = 1.0f;
        }
    }

    public void cancel() {
        AnimationHandler animationHandler = getAnimationHandler();
        Objects.requireNonNull(animationHandler);
        if (!animationHandler.mScheduler.isCurrentThread()) {
            throw new AndroidRuntimeException("Animations may only be canceled from the same thread as the animation handler");
        } else if (this.mRunning) {
            endAnimationInternal(true);
        }
    }

    public void start() {
        AnimationHandler animationHandler = getAnimationHandler();
        Objects.requireNonNull(animationHandler);
        if (animationHandler.mScheduler.isCurrentThread()) {
            boolean z = this.mRunning;
            if (!z && !z) {
                this.mRunning = true;
                if (!this.mStartValueIsSet) {
                    this.mValue = this.mProperty.getValue(this.mTarget);
                }
                float f = this.mValue;
                if (f > this.mMaxValue || f < this.mMinValue) {
                    throw new IllegalArgumentException("Starting value need to be in between min value and max value");
                }
                AnimationHandler animationHandler2 = getAnimationHandler();
                Objects.requireNonNull(animationHandler2);
                if (animationHandler2.mAnimationCallbacks.size() == 0) {
                    animationHandler2.mScheduler.postFrameCallback(animationHandler2.mRunnable);
                }
                if (!animationHandler2.mAnimationCallbacks.contains(this)) {
                    animationHandler2.mAnimationCallbacks.add(this);
                    return;
                }
                return;
            }
            return;
        }
        throw new AndroidRuntimeException("Animations may only be started on the same thread as the animation handler");
    }
}
