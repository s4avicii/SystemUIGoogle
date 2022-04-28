package com.android.systemui.unfold.progress;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.util.FloatProperty;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.unfold.updates.FoldStateProvider;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: FixedTimingTransitionProgressProvider.kt */
public final class FixedTimingTransitionProgressProvider implements UnfoldTransitionProgressProvider, FoldStateProvider.FoldUpdatesListener {
    public final ObjectAnimator animator;
    public final FoldStateProvider foldStateProvider;
    public final ArrayList listeners = new ArrayList();
    public float transitionProgress;

    /* compiled from: FixedTimingTransitionProgressProvider.kt */
    public static final class AnimationProgressProperty extends FloatProperty<FixedTimingTransitionProgressProvider> {
        public static final AnimationProgressProperty INSTANCE = new AnimationProgressProperty();

        public AnimationProgressProperty() {
            super("animation_progress");
        }

        public final Object get(Object obj) {
            return Float.valueOf(((FixedTimingTransitionProgressProvider) obj).transitionProgress);
        }

        public final void setValue(Object obj, float f) {
            FixedTimingTransitionProgressProvider fixedTimingTransitionProgressProvider = (FixedTimingTransitionProgressProvider) obj;
            Iterator it = fixedTimingTransitionProgressProvider.listeners.iterator();
            while (it.hasNext()) {
                ((UnfoldTransitionProgressProvider.TransitionProgressListener) it.next()).onTransitionProgress(f);
            }
            fixedTimingTransitionProgressProvider.transitionProgress = f;
        }
    }

    /* compiled from: FixedTimingTransitionProgressProvider.kt */
    public final class AnimatorListener implements Animator.AnimatorListener {
        public final void onAnimationCancel(Animator animator) {
        }

        public final void onAnimationRepeat(Animator animator) {
        }

        public AnimatorListener() {
        }

        public final void onAnimationEnd(Animator animator) {
            Iterator it = FixedTimingTransitionProgressProvider.this.listeners.iterator();
            while (it.hasNext()) {
                ((UnfoldTransitionProgressProvider.TransitionProgressListener) it.next()).onTransitionFinished();
            }
        }

        public final void onAnimationStart(Animator animator) {
            Iterator it = FixedTimingTransitionProgressProvider.this.listeners.iterator();
            while (it.hasNext()) {
                ((UnfoldTransitionProgressProvider.TransitionProgressListener) it.next()).onTransitionStarted();
            }
        }
    }

    public final void onFoldUpdate(int i) {
        if (i == 2) {
            this.animator.start();
        } else if (i == 5) {
            this.animator.cancel();
        }
    }

    public final void onHingeAngleUpdate(float f) {
    }

    public final void addCallback(Object obj) {
        this.listeners.add((UnfoldTransitionProgressProvider.TransitionProgressListener) obj);
    }

    public final void removeCallback(Object obj) {
        this.listeners.remove((UnfoldTransitionProgressProvider.TransitionProgressListener) obj);
    }

    public FixedTimingTransitionProgressProvider(FoldStateProvider foldStateProvider2) {
        this.foldStateProvider = foldStateProvider2;
        AnimatorListener animatorListener = new AnimatorListener();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, AnimationProgressProperty.INSTANCE, new float[]{0.0f, 1.0f});
        ofFloat.setDuration(400);
        ofFloat.addListener(animatorListener);
        this.animator = ofFloat;
        foldStateProvider2.addCallback(this);
        foldStateProvider2.start();
    }
}
