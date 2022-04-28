package com.android.systemui.unfold.progress;

import android.util.Log;
import android.util.MathUtils;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.unfold.updates.FoldStateProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PhysicsBasedUnfoldTransitionProgressProvider.kt */
public final class PhysicsBasedUnfoldTransitionProgressProvider implements UnfoldTransitionProgressProvider, FoldStateProvider.FoldUpdatesListener, DynamicAnimation.OnAnimationEndListener {
    public final FoldStateProvider foldStateProvider;
    public boolean isAnimatedCancelRunning;
    public boolean isTransitionRunning;
    public final ArrayList listeners = new ArrayList();
    public final SpringAnimation springAnimation;
    public float transitionProgress;

    /* compiled from: PhysicsBasedUnfoldTransitionProgressProvider.kt */
    public static final class AnimationProgressProperty extends FloatPropertyCompat<PhysicsBasedUnfoldTransitionProgressProvider> {
        public static final AnimationProgressProperty INSTANCE = new AnimationProgressProperty();

        public final float getValue(Object obj) {
            return ((PhysicsBasedUnfoldTransitionProgressProvider) obj).transitionProgress;
        }

        public final void setValue(Object obj, float f) {
            ((PhysicsBasedUnfoldTransitionProgressProvider) obj).setTransitionProgress(f);
        }
    }

    public final void addCallback(Object obj) {
        this.listeners.add((UnfoldTransitionProgressProvider.TransitionProgressListener) obj);
    }

    public final void cancelTransition(float f, boolean z) {
        if (!this.isTransitionRunning || !z) {
            setTransitionProgress(f);
            this.isAnimatedCancelRunning = false;
            this.isTransitionRunning = false;
            this.springAnimation.cancel();
            Iterator it = this.listeners.iterator();
            while (it.hasNext()) {
                ((UnfoldTransitionProgressProvider.TransitionProgressListener) it.next()).onTransitionFinished();
            }
            Log.d("PhysicsBasedUnfoldTransitionProgressProvider", "onTransitionFinished");
            return;
        }
        this.isAnimatedCancelRunning = true;
        this.springAnimation.animateToFinalPosition(f);
    }

    public final void onAnimationEnd(DynamicAnimation<? extends DynamicAnimation<?>> dynamicAnimation, boolean z, float f, float f2) {
        if (this.isAnimatedCancelRunning) {
            cancelTransition(f, false);
        }
    }

    public final void onFoldUpdate(int i) {
        if (i != 1) {
            if (i == 2) {
                startTransition(0.0f);
                if (this.foldStateProvider.isFullyOpened()) {
                    cancelTransition(1.0f, true);
                }
            } else if (i == 3 || i == 4) {
                if (this.isTransitionRunning) {
                    cancelTransition(1.0f, true);
                }
            } else if (i == 5) {
                cancelTransition(0.0f, false);
            }
        } else if (!this.isTransitionRunning) {
            startTransition(1.0f);
        }
        Log.d("PhysicsBasedUnfoldTransitionProgressProvider", Intrinsics.stringPlus("onFoldUpdate = ", Integer.valueOf(i)));
    }

    public final void onHingeAngleUpdate(float f) {
        if (this.isTransitionRunning && !this.isAnimatedCancelRunning) {
            this.springAnimation.animateToFinalPosition(MathUtils.saturate(f / 165.0f));
        }
    }

    public final void removeCallback(Object obj) {
        this.listeners.remove((UnfoldTransitionProgressProvider.TransitionProgressListener) obj);
    }

    public final void setTransitionProgress(float f) {
        if (this.isTransitionRunning) {
            Iterator it = this.listeners.iterator();
            while (it.hasNext()) {
                ((UnfoldTransitionProgressProvider.TransitionProgressListener) it.next()).onTransitionProgress(f);
            }
        }
        this.transitionProgress = f;
    }

    public final void startTransition(float f) {
        if (!this.isTransitionRunning) {
            Iterator it = this.listeners.iterator();
            while (it.hasNext()) {
                ((UnfoldTransitionProgressProvider.TransitionProgressListener) it.next()).onTransitionStarted();
            }
            this.isTransitionRunning = true;
            Log.d("PhysicsBasedUnfoldTransitionProgressProvider", "onTransitionStarted");
        }
        SpringAnimation springAnimation2 = this.springAnimation;
        SpringForce springForce = new SpringForce();
        springForce.mFinalPosition = (double) f;
        springForce.setDampingRatio(1.0f);
        springForce.setStiffness(200.0f);
        Objects.requireNonNull(springAnimation2);
        springAnimation2.mSpring = springForce;
        springAnimation2.mMinVisibleChange = 0.001f;
        springAnimation2.mValue = f;
        springAnimation2.mStartValueIsSet = true;
        springAnimation2.mMinValue = 0.0f;
        springAnimation2.mMaxValue = 1.0f;
        this.springAnimation.start();
    }

    public PhysicsBasedUnfoldTransitionProgressProvider(FoldStateProvider foldStateProvider2) {
        this.foldStateProvider = foldStateProvider2;
        SpringAnimation springAnimation2 = new SpringAnimation(this, AnimationProgressProperty.INSTANCE);
        springAnimation2.addEndListener(this);
        this.springAnimation = springAnimation2;
        foldStateProvider2.addCallback(this);
        foldStateProvider2.start();
    }
}
