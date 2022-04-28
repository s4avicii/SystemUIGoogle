package com.android.systemui.animation;

import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.RemoteException;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.RemoteAnimationTarget;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.View;
import android.view.ViewGroup;
import androidx.leanback.R$drawable;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.LaunchAnimator;
import java.util.Objects;

/* compiled from: ActivityLaunchAnimator.kt */
public final class ActivityLaunchAnimator$Runner$startAnimation$controller$1 implements LaunchAnimator.Controller {
    public final /* synthetic */ ActivityLaunchAnimator.Controller $$delegate_0;
    public final /* synthetic */ ActivityLaunchAnimator.Controller $delegate;
    public final /* synthetic */ IRemoteAnimationFinishedCallback $iCallback;
    public final /* synthetic */ RemoteAnimationTarget $navigationBar;
    public final /* synthetic */ RemoteAnimationTarget $window;
    public final /* synthetic */ ActivityLaunchAnimator this$0;
    public final /* synthetic */ ActivityLaunchAnimator.Runner this$1;

    public final LaunchAnimator.State createAnimatorState() {
        return this.$$delegate_0.createAnimatorState();
    }

    public final ViewGroup getLaunchContainer() {
        return this.$$delegate_0.getLaunchContainer();
    }

    public final View getOpeningWindowSyncView() {
        return this.$$delegate_0.getOpeningWindowSyncView();
    }

    public ActivityLaunchAnimator$Runner$startAnimation$controller$1(ActivityLaunchAnimator.Controller controller, ActivityLaunchAnimator activityLaunchAnimator, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback, ActivityLaunchAnimator.Runner runner, RemoteAnimationTarget remoteAnimationTarget, RemoteAnimationTarget remoteAnimationTarget2) {
        this.$delegate = controller;
        this.this$0 = activityLaunchAnimator;
        this.$iCallback = iRemoteAnimationFinishedCallback;
        this.this$1 = runner;
        this.$window = remoteAnimationTarget;
        this.$navigationBar = remoteAnimationTarget2;
        this.$$delegate_0 = controller;
    }

    public final void onLaunchAnimationEnd(boolean z) {
        for (ActivityLaunchAnimator.Listener onLaunchAnimationEnd : this.this$0.listeners) {
            onLaunchAnimationEnd.onLaunchAnimationEnd();
        }
        IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback = this.$iCallback;
        if (iRemoteAnimationFinishedCallback != null) {
            ActivityLaunchAnimator.Runner runner = this.this$1;
            int i = ActivityLaunchAnimator.Runner.$r8$clinit;
            Objects.requireNonNull(runner);
            try {
                iRemoteAnimationFinishedCallback.onAnimationFinished();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        this.$delegate.onLaunchAnimationEnd(z);
    }

    public final void onLaunchAnimationProgress(LaunchAnimator.State state, float f, float f2) {
        LaunchAnimator.State state2 = state;
        float f3 = f2;
        if (!state2.visible) {
            ActivityLaunchAnimator.Runner runner = this.this$1;
            RemoteAnimationTarget remoteAnimationTarget = this.$window;
            int i = ActivityLaunchAnimator.Runner.$r8$clinit;
            Objects.requireNonNull(runner);
            if (runner.transactionApplierView.getViewRootImpl() != null) {
                Rect rect = remoteAnimationTarget.screenSpaceBounds;
                int i2 = rect.left;
                int i3 = rect.right;
                float f4 = ((float) (i2 + i3)) / 2.0f;
                int i4 = rect.top;
                int i5 = rect.bottom;
                float f5 = (float) (i5 - i4);
                float max = Math.max(((float) (state2.right - state2.left)) / ((float) (i3 - i2)), ((float) (state2.bottom - state2.top)) / f5);
                runner.matrix.reset();
                runner.matrix.setScale(max, max, f4, ((float) (i4 + i5)) / 2.0f);
                float f6 = (f5 * max) - f5;
                int i6 = state2.left;
                runner.matrix.postTranslate(((((float) (state2.right - i6)) / 2.0f) + ((float) i6)) - f4, (f6 / 2.0f) + ((float) (state2.top - rect.top)));
                int i7 = state2.left;
                float f7 = ((float) i7) - ((float) rect.left);
                int i8 = state2.top;
                float f8 = ((float) i8) - ((float) rect.top);
                runner.windowCropF.set(f7, f8, ((float) (state2.right - i7)) + f7, ((float) (state2.bottom - i8)) + f8);
                runner.matrix.invert(runner.invertMatrix);
                runner.invertMatrix.mapRect(runner.windowCropF);
                runner.windowCrop.set(R$drawable.roundToInt(runner.windowCropF.left), R$drawable.roundToInt(runner.windowCropF.top), R$drawable.roundToInt(runner.windowCropF.right), R$drawable.roundToInt(runner.windowCropF.bottom));
                SyncRtSurfaceTransactionApplier.SurfaceParams build = new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(remoteAnimationTarget.leash).withAlpha(1.0f).withMatrix(runner.matrix).withWindowCrop(runner.windowCrop).withCornerRadius(Math.max(state2.topCornerRadius, state2.bottomCornerRadius) / max).withVisibility(true).build();
                runner.transactionApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{build});
            }
        }
        RemoteAnimationTarget remoteAnimationTarget2 = this.$navigationBar;
        if (remoteAnimationTarget2 != null) {
            ActivityLaunchAnimator.Runner runner2 = this.this$1;
            int i9 = ActivityLaunchAnimator.Runner.$r8$clinit;
            Objects.requireNonNull(runner2);
            if (runner2.transactionApplierView.getViewRootImpl() != null) {
                PorterDuffXfermode porterDuffXfermode = LaunchAnimator.SRC_MODE;
                LaunchAnimator.Timings timings = ActivityLaunchAnimator.TIMINGS;
                float progress = LaunchAnimator.Companion.getProgress(timings, f2, ActivityLaunchAnimator.ANIMATION_DELAY_NAV_FADE_IN, 133);
                SyncRtSurfaceTransactionApplier.SurfaceParams.Builder builder = new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(remoteAnimationTarget2.leash);
                if (progress > 0.0f) {
                    runner2.matrix.reset();
                    runner2.matrix.setTranslate(0.0f, (float) (state2.top - remoteAnimationTarget2.sourceContainerBounds.top));
                    runner2.windowCrop.set(state2.left, 0, state2.right, state2.bottom - state2.top);
                    builder.withAlpha(ActivityLaunchAnimator.NAV_FADE_IN_INTERPOLATOR.getInterpolation(progress)).withMatrix(runner2.matrix).withWindowCrop(runner2.windowCrop).withVisibility(true);
                } else {
                    builder.withAlpha(1.0f - ActivityLaunchAnimator.NAV_FADE_OUT_INTERPOLATOR.getInterpolation(LaunchAnimator.Companion.getProgress(timings, f2, 0, 133)));
                }
                runner2.transactionApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{builder.build()});
            }
        }
        for (ActivityLaunchAnimator.Listener onLaunchAnimationProgress : this.this$0.listeners) {
            onLaunchAnimationProgress.onLaunchAnimationProgress(f3);
        }
        this.$delegate.onLaunchAnimationProgress(state2, f, f3);
    }

    public final void onLaunchAnimationStart(boolean z) {
        for (ActivityLaunchAnimator.Listener onLaunchAnimationStart : this.this$0.listeners) {
            onLaunchAnimationStart.onLaunchAnimationStart();
        }
        this.$delegate.onLaunchAnimationStart(z);
    }
}
