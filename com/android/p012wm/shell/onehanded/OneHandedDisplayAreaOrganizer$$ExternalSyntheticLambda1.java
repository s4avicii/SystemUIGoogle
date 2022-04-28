package com.android.p012wm.shell.onehanded;

import android.graphics.Rect;
import android.view.SurfaceControl;
import android.window.WindowContainerToken;
import com.android.p012wm.shell.onehanded.OneHandedAnimationController;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.BiConsumer;

/* renamed from: com.android.wm.shell.onehanded.OneHandedDisplayAreaOrganizer$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class OneHandedDisplayAreaOrganizer$$ExternalSyntheticLambda1 implements BiConsumer {
    public final /* synthetic */ OneHandedDisplayAreaOrganizer f$0;
    public final /* synthetic */ float f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ OneHandedDisplayAreaOrganizer$$ExternalSyntheticLambda1(OneHandedDisplayAreaOrganizer oneHandedDisplayAreaOrganizer, float f, int i, int i2) {
        this.f$0 = oneHandedDisplayAreaOrganizer;
        this.f$1 = f;
        this.f$2 = i;
        this.f$3 = i2;
    }

    public final void accept(Object obj, Object obj2) {
        OneHandedDisplayAreaOrganizer oneHandedDisplayAreaOrganizer = this.f$0;
        float f = this.f$1;
        int i = this.f$2;
        int i2 = this.f$3;
        WindowContainerToken windowContainerToken = (WindowContainerToken) obj;
        SurfaceControl surfaceControl = (SurfaceControl) obj2;
        Objects.requireNonNull(oneHandedDisplayAreaOrganizer);
        float f2 = (float) i;
        int i3 = oneHandedDisplayAreaOrganizer.mEnterExitAnimationDurationMs;
        OneHandedAnimationController oneHandedAnimationController = oneHandedDisplayAreaOrganizer.mAnimationController;
        Rect rect = oneHandedDisplayAreaOrganizer.mLastVisualDisplayBounds;
        Objects.requireNonNull(oneHandedAnimationController);
        OneHandedAnimationController.OneHandedTransitionAnimator oneHandedTransitionAnimator = oneHandedAnimationController.mAnimatorMap.get(windowContainerToken);
        if (oneHandedTransitionAnimator == null) {
            HashMap<WindowContainerToken, OneHandedAnimationController.OneHandedTransitionAnimator> hashMap = oneHandedAnimationController.mAnimatorMap;
            OneHandedAnimationController.OneHandedTransitionAnimator ofYOffset = OneHandedAnimationController.OneHandedTransitionAnimator.ofYOffset(windowContainerToken, surfaceControl, f, f2, rect);
            oneHandedAnimationController.setupOneHandedTransitionAnimator(ofYOffset);
            hashMap.put(windowContainerToken, ofYOffset);
        } else if (oneHandedTransitionAnimator.isRunning()) {
            oneHandedTransitionAnimator.mEndValue = f2;
        } else {
            oneHandedTransitionAnimator.cancel();
            HashMap<WindowContainerToken, OneHandedAnimationController.OneHandedTransitionAnimator> hashMap2 = oneHandedAnimationController.mAnimatorMap;
            OneHandedAnimationController.OneHandedTransitionAnimator ofYOffset2 = OneHandedAnimationController.OneHandedTransitionAnimator.ofYOffset(windowContainerToken, surfaceControl, f, f2, rect);
            oneHandedAnimationController.setupOneHandedTransitionAnimator(ofYOffset2);
            hashMap2.put(windowContainerToken, ofYOffset2);
        }
        OneHandedAnimationController.OneHandedTransitionAnimator oneHandedTransitionAnimator2 = oneHandedAnimationController.mAnimatorMap.get(windowContainerToken);
        if (oneHandedTransitionAnimator2 != null) {
            oneHandedTransitionAnimator2.mTransitionDirection = i2;
            OneHandedAnimationCallback oneHandedAnimationCallback = oneHandedDisplayAreaOrganizer.mOneHandedAnimationCallback;
            if (oneHandedAnimationCallback != null) {
                oneHandedTransitionAnimator2.mOneHandedAnimationCallbacks.add(oneHandedAnimationCallback);
            }
            OneHandedTutorialHandler oneHandedTutorialHandler = oneHandedDisplayAreaOrganizer.mTutorialHandler;
            if (oneHandedTutorialHandler != null) {
                oneHandedTransitionAnimator2.mOneHandedAnimationCallbacks.add(oneHandedTutorialHandler);
            }
            oneHandedTransitionAnimator2.setDuration((long) i3).start();
        }
    }
}
