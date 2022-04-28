package com.android.systemui.animation;

import android.app.ActivityManager;
import android.graphics.Rect;
import android.os.RemoteException;
import android.util.Log;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.RemoteAnimationTarget;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.p012wm.shell.startingsurface.SplashscreenContentDrawer;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.LaunchAnimator;
import com.android.systemui.statusbar.phone.StatusBar;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ActivityLaunchAnimator.kt */
public final class ActivityLaunchAnimator$Runner$onAnimationStart$1 implements Runnable {
    public final /* synthetic */ RemoteAnimationTarget[] $apps;
    public final /* synthetic */ IRemoteAnimationFinishedCallback $iCallback;
    public final /* synthetic */ RemoteAnimationTarget[] $nonApps;
    public final /* synthetic */ ActivityLaunchAnimator.Runner this$0;

    public ActivityLaunchAnimator$Runner$onAnimationStart$1(ActivityLaunchAnimator.Runner runner, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
        this.this$0 = runner;
        this.$apps = remoteAnimationTargetArr;
        this.$nonApps = remoteAnimationTargetArr2;
        this.$iCallback = iRemoteAnimationFinishedCallback;
    }

    public final void run() {
        RemoteAnimationTarget remoteAnimationTarget;
        RemoteAnimationTarget remoteAnimationTarget2;
        int i;
        LaunchAnimator launchAnimator;
        float f;
        boolean z;
        boolean z2;
        ActivityLaunchAnimator.Runner runner = this.this$0;
        RemoteAnimationTarget[] remoteAnimationTargetArr = this.$apps;
        RemoteAnimationTarget[] remoteAnimationTargetArr2 = this.$nonApps;
        IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback = this.$iCallback;
        int i2 = ActivityLaunchAnimator.Runner.$r8$clinit;
        Objects.requireNonNull(runner);
        if (remoteAnimationTargetArr != null) {
            int length = remoteAnimationTargetArr.length;
            int i3 = 0;
            while (true) {
                if (i3 >= length) {
                    break;
                }
                RemoteAnimationTarget remoteAnimationTarget3 = remoteAnimationTargetArr[i3];
                i3++;
                if (remoteAnimationTarget3.mode == 0) {
                    z2 = true;
                    continue;
                } else {
                    z2 = false;
                    continue;
                }
                if (z2) {
                    remoteAnimationTarget = remoteAnimationTarget3;
                    break;
                }
            }
        }
        remoteAnimationTarget = null;
        if (remoteAnimationTarget == null) {
            Log.i("ActivityLaunchAnimator", "Aborting the animation as no window is opening");
            runner.launchContainer.removeCallbacks(runner.onTimeout);
            if (iRemoteAnimationFinishedCallback != null) {
                try {
                    iRemoteAnimationFinishedCallback.onAnimationFinished();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            runner.controller.onLaunchAnimationCancelled();
            return;
        }
        if (remoteAnimationTargetArr2 != null) {
            int length2 = remoteAnimationTargetArr2.length;
            int i4 = 0;
            while (true) {
                if (i4 >= length2) {
                    break;
                }
                RemoteAnimationTarget remoteAnimationTarget4 = remoteAnimationTargetArr2[i4];
                i4++;
                if (remoteAnimationTarget4.windowType == 2019) {
                    z = true;
                    continue;
                } else {
                    z = false;
                    continue;
                }
                if (z) {
                    remoteAnimationTarget2 = remoteAnimationTarget4;
                    break;
                }
            }
        }
        remoteAnimationTarget2 = null;
        Rect rect = remoteAnimationTarget.screenSpaceBounds;
        LaunchAnimator.State state = new LaunchAnimator.State(rect.top, rect.bottom, rect.left, rect.right, 0.0f, 0.0f, 48);
        ActivityLaunchAnimator activityLaunchAnimator = ActivityLaunchAnimator.this;
        Objects.requireNonNull(activityLaunchAnimator);
        ActivityLaunchAnimator.Callback callback = activityLaunchAnimator.callback;
        Intrinsics.checkNotNull(callback);
        ActivityManager.RunningTaskInfo runningTaskInfo = remoteAnimationTarget.taskInfo;
        StatusBar.C154723 r0 = (StatusBar.C154723) callback;
        if (!StatusBar.this.mStartingSurfaceOptional.isPresent()) {
            Log.w("StatusBar", "No starting surface, defaulting to SystemBGColor");
            i = SplashscreenContentDrawer.getSystemBGColor();
        } else {
            i = StatusBar.this.mStartingSurfaceOptional.get().getBackgroundColor(runningTaskInfo);
        }
        int i5 = i;
        if (runner.controller.isDialogLaunch()) {
            launchAnimator = ActivityLaunchAnimator.this.dialogToAppAnimator;
        } else {
            launchAnimator = ActivityLaunchAnimator.this.launchAnimator;
        }
        LaunchAnimator launchAnimator2 = launchAnimator;
        if (launchAnimator2.mo6968xb114753a(runner.controller.getLaunchContainer(), state)) {
            f = ScreenDecorationsUtils.getWindowCornerRadius(runner.context);
        } else {
            f = 0.0f;
        }
        state.topCornerRadius = f;
        state.bottomCornerRadius = f;
        runner.animation = launchAnimator2.startAnimation(new ActivityLaunchAnimator$Runner$startAnimation$controller$1(runner.controller, ActivityLaunchAnimator.this, iRemoteAnimationFinishedCallback, runner, remoteAnimationTarget, remoteAnimationTarget2), state, i5, true);
    }
}
