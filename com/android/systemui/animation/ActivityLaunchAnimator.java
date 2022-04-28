package com.android.systemui.animation;

import android.app.ActivityTaskManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationAdapter;
import android.view.RemoteAnimationTarget;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.SystemUIApplication$$ExternalSyntheticLambda1;
import com.android.systemui.animation.LaunchAnimator;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.phone.StatusBarLaunchAnimatorController;
import java.util.LinkedHashSet;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ActivityLaunchAnimator.kt */
public final class ActivityLaunchAnimator {
    public static final long ANIMATION_DELAY_NAV_FADE_IN = 234;
    public static final LaunchAnimator.Timings DIALOG_TIMINGS = new LaunchAnimator.Timings(500, 0, 200, 200, 183);
    public static final LaunchAnimator.Interpolators INTERPOLATORS;
    public static final PathInterpolator NAV_FADE_IN_INTERPOLATOR = Interpolators.STANDARD_DECELERATE;
    public static final PathInterpolator NAV_FADE_OUT_INTERPOLATOR = new PathInterpolator(0.2f, 0.0f, 1.0f, 1.0f);
    public static final LaunchAnimator.Timings TIMINGS = new LaunchAnimator.Timings(500, 0, 150, 150, 183);
    public Callback callback;
    public final LaunchAnimator dialogToAppAnimator;
    public final LaunchAnimator launchAnimator;
    public final LinkedHashSet<Listener> listeners = new LinkedHashSet<>();

    /* compiled from: ActivityLaunchAnimator.kt */
    public interface Callback {
    }

    /* compiled from: ActivityLaunchAnimator.kt */
    public interface Listener {
        void onLaunchAnimationEnd() {
        }

        void onLaunchAnimationProgress(float f) {
        }

        void onLaunchAnimationStart() {
        }
    }

    /* compiled from: ActivityLaunchAnimator.kt */
    public interface PendingIntentStarter {
        int startPendingIntent(RemoteAnimationAdapter remoteAnimationAdapter) throws PendingIntent.CanceledException;
    }

    @VisibleForTesting
    /* compiled from: ActivityLaunchAnimator.kt */
    public final class Runner extends IRemoteAnimationRunner.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;
        public LaunchAnimator$startAnimation$3 animation;
        public boolean cancelled;
        public final Context context;
        public final Controller controller;
        public final Matrix invertMatrix;
        public final ViewGroup launchContainer;
        public final Matrix matrix;
        public ActivityLaunchAnimator$Runner$onTimeout$1 onTimeout;
        public boolean timedOut;
        public final SyncRtSurfaceTransactionApplier transactionApplier;
        public final View transactionApplierView;
        public Rect windowCrop;
        public RectF windowCropF;

        public Runner(Controller controller2) {
            this.controller = controller2;
            ViewGroup launchContainer2 = controller2.getLaunchContainer();
            this.launchContainer = launchContainer2;
            this.context = launchContainer2.getContext();
            View openingWindowSyncView = controller2.getOpeningWindowSyncView();
            openingWindowSyncView = openingWindowSyncView == null ? controller2.getLaunchContainer() : openingWindowSyncView;
            this.transactionApplierView = openingWindowSyncView;
            this.transactionApplier = new SyncRtSurfaceTransactionApplier(openingWindowSyncView);
            this.matrix = new Matrix();
            this.invertMatrix = new Matrix();
            this.windowCrop = new Rect();
            this.windowCropF = new RectF();
            this.onTimeout = new ActivityLaunchAnimator$Runner$onTimeout$1(this);
        }

        public final void onAnimationCancelled() {
            if (!this.timedOut) {
                Log.i("ActivityLaunchAnimator", "Remote animation was cancelled");
                this.cancelled = true;
                this.launchContainer.removeCallbacks(this.onTimeout);
                this.context.getMainExecutor().execute(new ActivityLaunchAnimator$Runner$onAnimationCancelled$1(this));
            }
        }

        public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            this.launchContainer.removeCallbacks(this.onTimeout);
            if (this.timedOut) {
                if (iRemoteAnimationFinishedCallback != null) {
                    try {
                        iRemoteAnimationFinishedCallback.onAnimationFinished();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            } else if (!this.cancelled) {
                this.context.getMainExecutor().execute(new ActivityLaunchAnimator$Runner$onAnimationStart$1(this, remoteAnimationTargetArr, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback));
            }
        }
    }

    /* compiled from: ActivityLaunchAnimator.kt */
    public interface Controller extends LaunchAnimator.Controller {
        boolean isDialogLaunch() {
            return false;
        }

        void onIntentStarted(boolean z) {
        }

        void onLaunchAnimationCancelled() {
        }

        static GhostedViewLaunchAnimatorController fromView(View view, Integer num) {
            if (view.getParent() instanceof ViewGroup) {
                return new GhostedViewLaunchAnimatorController(view, num, 4);
            }
            Log.wtf("ActivityLaunchAnimator", "Skipping animation as view " + view + " is not attached to a ViewGroup", new Exception());
            return null;
        }
    }

    static {
        PathInterpolator pathInterpolator = Interpolators.EMPHASIZED;
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        Path path2 = path;
        path2.cubicTo(0.1217f, 0.0462f, 0.15f, 0.4686f, 0.1667f, 0.66f);
        path2.cubicTo(0.1834f, 0.8878f, 0.1667f, 1.0f, 1.0f, 1.0f);
        INTERPOLATORS = new LaunchAnimator.Interpolators(pathInterpolator, new PathInterpolator(path), Interpolators.LINEAR_OUT_SLOW_IN, new PathInterpolator(0.0f, 0.0f, 0.6f, 1.0f));
    }

    public ActivityLaunchAnimator() {
        LaunchAnimator.Timings timings = TIMINGS;
        LaunchAnimator.Interpolators interpolators = INTERPOLATORS;
        LaunchAnimator launchAnimator2 = new LaunchAnimator(timings, interpolators);
        LaunchAnimator launchAnimator3 = new LaunchAnimator(DIALOG_TIMINGS, interpolators);
        this.launchAnimator = launchAnimator2;
        this.dialogToAppAnimator = launchAnimator3;
    }

    @VisibleForTesting
    public final Runner createRunner(Controller controller) {
        return new Runner(controller);
    }

    public final void startIntentWithAnimation(Controller controller, boolean z, String str, boolean z2, Function1<? super RemoteAnimationAdapter, Integer> function1) {
        boolean z3;
        boolean z4;
        Controller controller2 = controller;
        String str2 = str;
        Function1<? super RemoteAnimationAdapter, Integer> function12 = function1;
        boolean z5 = false;
        RemoteAnimationAdapter remoteAnimationAdapter = null;
        if (controller2 == null || !z) {
            Log.i("ActivityLaunchAnimator", "Starting intent with no animation");
            function12.invoke(null);
            if (controller2 != null) {
                callOnIntentStartedOnMainThread(controller2, false);
                return;
            }
            return;
        }
        Callback callback2 = this.callback;
        if (callback2 != null) {
            IRemoteAnimationRunner runner = new Runner(controller2);
            StatusBar.C154723 r7 = (StatusBar.C154723) callback2;
            if (!StatusBar.this.mKeyguardStateController.isShowing() || z2) {
                z3 = false;
            } else {
                z3 = true;
            }
            if (!z3) {
                z4 = z3;
                remoteAnimationAdapter = new RemoteAnimationAdapter(runner, 500, 500 - ((long) 150));
            } else {
                z4 = z3;
            }
            if (!(str2 == null || remoteAnimationAdapter == null)) {
                try {
                    ActivityTaskManager.getService().registerRemoteAnimationForNextActivityStart(str2, remoteAnimationAdapter);
                } catch (RemoteException e) {
                    Log.w("ActivityLaunchAnimator", "Unable to register the remote animation", e);
                }
            }
            int intValue = function12.invoke(remoteAnimationAdapter).intValue();
            if (intValue == 2 || intValue == 0 || (intValue == 3 && z4)) {
                z5 = true;
            }
            Log.i("ActivityLaunchAnimator", "launchResult=" + intValue + " willAnimate=" + z5 + " hideKeyguardWithAnimation=" + z4);
            callOnIntentStartedOnMainThread(controller2, z5);
            if (z5) {
                runner.launchContainer.postDelayed(runner.onTimeout, 1000);
                if (z4) {
                    StatusBar.this.mMainExecutor.execute(new SystemUIApplication$$ExternalSyntheticLambda1(r7, runner, 2));
                    return;
                }
                return;
            }
            return;
        }
        throw new IllegalStateException("ActivityLaunchAnimator.callback must be set before using this animator");
    }

    public final void startPendingIntentWithAnimation(StatusBarLaunchAnimatorController statusBarLaunchAnimatorController, boolean z, String str, PendingIntentStarter pendingIntentStarter) throws PendingIntent.CanceledException {
        startIntentWithAnimation(statusBarLaunchAnimatorController, z, str, false, new ActivityLaunchAnimator$startPendingIntentWithAnimation$1(pendingIntentStarter));
    }

    public static void callOnIntentStartedOnMainThread(Controller controller, boolean z) {
        if (!Intrinsics.areEqual(Looper.myLooper(), Looper.getMainLooper())) {
            controller.getLaunchContainer().getContext().getMainExecutor().execute(new ActivityLaunchAnimator$callOnIntentStartedOnMainThread$1(controller, z));
        } else {
            controller.onIntentStarted(z);
        }
    }
}
