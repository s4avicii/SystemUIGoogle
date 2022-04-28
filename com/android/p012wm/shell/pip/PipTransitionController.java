package com.android.p012wm.shell.pip;

import android.app.TaskInfo;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.view.SurfaceControl;
import android.window.WindowContainerTransaction;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda15;
import com.android.p012wm.shell.pip.PipAnimationController;
import com.android.p012wm.shell.transition.Transitions;
import com.android.systemui.ImageWallpaper$GLEngine$$ExternalSyntheticLambda0;
import java.util.ArrayList;
import java.util.Objects;

/* renamed from: com.android.wm.shell.pip.PipTransitionController */
public abstract class PipTransitionController implements Transitions.TransitionHandler {
    public final C18921 mPipAnimationCallback = new PipAnimationController.PipAnimationCallback() {
        public final void onPipAnimationCancel(PipAnimationController.PipTransitionAnimator pipTransitionAnimator) {
            SurfaceControl surfaceControl;
            if (PipAnimationController.isInPipDirection(pipTransitionAnimator.getTransitionDirection()) && (surfaceControl = pipTransitionAnimator.mContentOverlay) != null) {
                PipTransitionController.this.mPipOrganizer.fadeOutAndRemoveOverlay(surfaceControl, new ImageWallpaper$GLEngine$$ExternalSyntheticLambda0(pipTransitionAnimator, 8), true);
            }
            PipTransitionController.this.sendOnPipTransitionCancelled(pipTransitionAnimator.getTransitionDirection());
        }

        public final void onPipAnimationEnd(TaskInfo taskInfo, SurfaceControl.Transaction transaction, PipAnimationController.PipTransitionAnimator pipTransitionAnimator) {
            SurfaceControl surfaceControl;
            int transitionDirection = pipTransitionAnimator.getTransitionDirection();
            PipTransitionController.this.mPipBoundsState.setBounds(pipTransitionAnimator.mDestinationBounds);
            if (transitionDirection != 5) {
                if (PipAnimationController.isInPipDirection(transitionDirection) && (surfaceControl = pipTransitionAnimator.mContentOverlay) != null) {
                    PipTransitionController.this.mPipOrganizer.fadeOutAndRemoveOverlay(surfaceControl, new BubbleStackView$$ExternalSyntheticLambda15(pipTransitionAnimator, 8), true);
                }
                PipTransitionController.this.onFinishResize(taskInfo, pipTransitionAnimator.mDestinationBounds, transitionDirection, transaction);
                PipTransitionController.this.sendOnPipTransitionFinished(transitionDirection);
            }
        }

        public final void onPipAnimationStart(PipAnimationController.PipTransitionAnimator pipTransitionAnimator) {
            PipTransitionController.this.sendOnPipTransitionStarted(pipTransitionAnimator.getTransitionDirection());
        }
    };
    public final PipAnimationController mPipAnimationController;
    public final PipBoundsAlgorithm mPipBoundsAlgorithm;
    public final PipBoundsState mPipBoundsState;
    public final PipMenuController mPipMenuController;
    public PipTaskOrganizer mPipOrganizer;
    public final ArrayList mPipTransitionCallbacks = new ArrayList();
    public final Transitions mTransitions;

    /* renamed from: com.android.wm.shell.pip.PipTransitionController$PipTransitionCallback */
    public interface PipTransitionCallback {
        void onPipTransitionCanceled(int i);

        void onPipTransitionFinished(int i);

        void onPipTransitionStarted(int i, Rect rect);
    }

    public void forceFinishTransition() {
    }

    public void onFinishResize(TaskInfo taskInfo, Rect rect, int i, SurfaceControl.Transaction transaction) {
    }

    public void onFixedRotationStarted() {
    }

    public void setIsFullAnimation(boolean z) {
    }

    public void startExitTransition(int i, WindowContainerTransaction windowContainerTransaction, Rect rect) {
    }

    public final void sendOnPipTransitionCancelled(int i) {
        int size = this.mPipTransitionCallbacks.size();
        while (true) {
            size--;
            if (size >= 0) {
                ((PipTransitionCallback) this.mPipTransitionCallbacks.get(size)).onPipTransitionCanceled(i);
            } else {
                return;
            }
        }
    }

    public final void sendOnPipTransitionFinished(int i) {
        int size = this.mPipTransitionCallbacks.size();
        while (true) {
            size--;
            if (size >= 0) {
                ((PipTransitionCallback) this.mPipTransitionCallbacks.get(size)).onPipTransitionFinished(i);
            } else {
                return;
            }
        }
    }

    public final void sendOnPipTransitionStarted(int i) {
        Rect bounds = this.mPipBoundsState.getBounds();
        int size = this.mPipTransitionCallbacks.size();
        while (true) {
            size--;
            if (size >= 0) {
                ((PipTransitionCallback) this.mPipTransitionCallbacks.get(size)).onPipTransitionStarted(i, bounds);
            } else {
                return;
            }
        }
    }

    public PipTransitionController(PipBoundsState pipBoundsState, PipMenuController pipMenuController, PipBoundsAlgorithm pipBoundsAlgorithm, PipAnimationController pipAnimationController, Transitions transitions) {
        this.mPipBoundsState = pipBoundsState;
        this.mPipMenuController = pipMenuController;
        this.mPipBoundsAlgorithm = pipBoundsAlgorithm;
        this.mPipAnimationController = pipAnimationController;
        this.mTransitions = transitions;
        new Handler(Looper.getMainLooper());
        if (Transitions.ENABLE_SHELL_TRANSITIONS) {
            Objects.requireNonNull(transitions);
            transitions.mHandlers.add(this);
        }
    }
}
