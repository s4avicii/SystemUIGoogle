package com.android.p012wm.shell;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LauncherApps;
import android.content.pm.ShortcutInfo;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline0;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Binder;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.CloseGuard;
import android.view.SurfaceControl;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.p012wm.shell.ShellTaskOrganizer;
import com.android.p012wm.shell.TaskViewTransitions;
import com.android.p012wm.shell.common.SyncTransactionQueue;
import com.android.p012wm.shell.transition.Transitions;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.concurrent.Executor;

/* renamed from: com.android.wm.shell.TaskView */
public class TaskView extends SurfaceView implements SurfaceHolder.Callback, ShellTaskOrganizer.TaskListener, ViewTreeObserver.OnComputeInternalInsetsListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CloseGuard mGuard;
    public boolean mIsInitialized;
    public Listener mListener;
    public Executor mListenerExecutor;
    public Rect mObscuredTouchRect;
    public final Executor mShellExecutor;
    public boolean mSurfaceCreated;
    public final SyncTransactionQueue mSyncQueue;
    public ActivityManager.RunningTaskInfo mTaskInfo;
    public SurfaceControl mTaskLeash;
    public final ShellTaskOrganizer mTaskOrganizer;
    public WindowContainerToken mTaskToken;
    public final TaskViewTransitions mTaskViewTransitions;
    public final int[] mTmpLocation = new int[2];
    public final Rect mTmpRect = new Rect();
    public final Rect mTmpRootRect = new Rect();
    public final SurfaceControl.Transaction mTransaction = new SurfaceControl.Transaction();

    /* renamed from: com.android.wm.shell.TaskView$Listener */
    public interface Listener {
        void onBackPressedOnTaskRoot(int i) {
        }

        void onInitialized() {
        }

        void onReleased() {
        }

        void onTaskCreated(int i) {
        }

        void onTaskRemovalStarted() {
        }

        void onTaskVisibilityChanged(boolean z) {
        }
    }

    public TaskView(Context context, ShellTaskOrganizer shellTaskOrganizer, TaskViewTransitions taskViewTransitions, SyncTransactionQueue syncTransactionQueue) {
        super(context, (AttributeSet) null, 0, 0, true);
        CloseGuard closeGuard = new CloseGuard();
        this.mGuard = closeGuard;
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mShellExecutor = shellTaskOrganizer.getExecutor();
        this.mSyncQueue = syncTransactionQueue;
        this.mTaskViewTransitions = taskViewTransitions;
        if (taskViewTransitions != null) {
            synchronized (taskViewTransitions.mRegistered) {
                try {
                    boolean[] zArr = taskViewTransitions.mRegistered;
                    if (!zArr[0]) {
                        zArr[0] = true;
                        Transitions transitions = taskViewTransitions.mTransitions;
                        Objects.requireNonNull(transitions);
                        transitions.mHandlers.add(taskViewTransitions);
                    }
                } catch (Throwable th) {
                    while (true) {
                        throw th;
                    }
                }
            }
            taskViewTransitions.mTaskViews.add(this);
        }
        setUseAlpha();
        getHolder().addCallback(this);
        closeGuard.open("release");
    }

    public final void onLocationChanged(WindowContainerTransaction windowContainerTransaction) {
        getBoundsOnScreen(this.mTmpRect);
        getRootView().getBoundsOnScreen(this.mTmpRootRect);
        if (!this.mTmpRootRect.contains(this.mTmpRect)) {
            this.mTmpRect.offsetTo(0, 0);
        }
        windowContainerTransaction.setBounds(this.mTaskToken, this.mTmpRect);
    }

    public final void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.mSurfaceCreated = true;
        if (this.mListener != null && !this.mIsInitialized) {
            this.mIsInitialized = true;
            this.mListenerExecutor.execute(new TaskView$$ExternalSyntheticLambda2(this, 0));
        }
        this.mShellExecutor.execute(new TaskView$$ExternalSyntheticLambda6(this, 0));
    }

    public final void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this.mSurfaceCreated = false;
        this.mShellExecutor.execute(new TaskView$$ExternalSyntheticLambda5(this, 0));
    }

    public final void attachChildSurfaceToTask(int i, SurfaceControl.Builder builder) {
        if (this.mTaskInfo.taskId == i) {
            builder.setParent(this.mTaskLeash);
            return;
        }
        throw new IllegalArgumentException(VendorAtomValue$$ExternalSyntheticOutline0.m0m("There is no surface for taskId=", i));
    }

    public final void dump(PrintWriter printWriter, String str) {
        printWriter.println(str + this);
    }

    public final void finalize() throws Throwable {
        try {
            CloseGuard closeGuard = this.mGuard;
            if (closeGuard != null) {
                closeGuard.warnIfOpen();
                performRelease();
            }
        } finally {
            super.finalize();
        }
    }

    public final boolean isUsingShellTransitions() {
        if (this.mTaskViewTransitions == null || !Transitions.ENABLE_SHELL_TRANSITIONS) {
            return false;
        }
        return true;
    }

    public final void onBackPressedOnTaskRoot(ActivityManager.RunningTaskInfo runningTaskInfo) {
        WindowContainerToken windowContainerToken = this.mTaskToken;
        if (windowContainerToken != null && windowContainerToken.equals(runningTaskInfo.token) && this.mListener != null) {
            this.mListenerExecutor.execute(new TaskView$$ExternalSyntheticLambda12(this, runningTaskInfo.taskId));
        }
    }

    public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
        if (internalInsetsInfo.touchableRegion.isEmpty()) {
            internalInsetsInfo.setTouchableInsets(3);
            View rootView = getRootView();
            rootView.getLocationInWindow(this.mTmpLocation);
            Rect rect = this.mTmpRootRect;
            int[] iArr = this.mTmpLocation;
            rect.set(iArr[0], iArr[1], rootView.getWidth(), rootView.getHeight());
            internalInsetsInfo.touchableRegion.set(this.mTmpRootRect);
        }
        getLocationInWindow(this.mTmpLocation);
        Rect rect2 = this.mTmpRect;
        int[] iArr2 = this.mTmpLocation;
        rect2.set(iArr2[0], iArr2[1], getWidth() + iArr2[0], getHeight() + this.mTmpLocation[1]);
        internalInsetsInfo.touchableRegion.op(this.mTmpRect, Region.Op.DIFFERENCE);
        Rect rect3 = this.mObscuredTouchRect;
        if (rect3 != null) {
            internalInsetsInfo.touchableRegion.union(rect3);
        }
    }

    public final void onTaskInfoChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
        ActivityManager.TaskDescription taskDescription = runningTaskInfo.taskDescription;
        if (taskDescription != null) {
            setResizeBackgroundColor(taskDescription.getBackgroundColor());
        }
    }

    public final void onTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo) {
        WindowContainerToken windowContainerToken = this.mTaskToken;
        if (windowContainerToken != null && windowContainerToken.equals(runningTaskInfo.token)) {
            if (this.mListener != null) {
                this.mListenerExecutor.execute(new TaskView$$ExternalSyntheticLambda9(this, runningTaskInfo.taskId));
            }
            this.mTaskOrganizer.setInterceptBackPressedOnTaskRoot(this.mTaskToken, false);
            this.mTransaction.reparent(this.mTaskLeash, (SurfaceControl) null).apply();
            this.mTaskInfo = null;
            this.mTaskToken = null;
            this.mTaskLeash = null;
        }
    }

    public final void prepareActivityOptions(ActivityOptions activityOptions, Rect rect) {
        Binder binder = new Binder();
        this.mShellExecutor.execute(new TaskView$$ExternalSyntheticLambda7(this, binder, 0));
        activityOptions.setLaunchBounds(rect);
        activityOptions.setLaunchCookie(binder);
        activityOptions.setLaunchWindowingMode(6);
        activityOptions.setRemoveWithTaskOrganizer(true);
    }

    public final void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        if (this.mTaskToken != null) {
            onLocationChanged();
        }
    }

    public final String toString() {
        Object obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("TaskView:");
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskInfo;
        if (runningTaskInfo != null) {
            obj = Integer.valueOf(runningTaskInfo.taskId);
        } else {
            obj = "null";
        }
        m.append(obj);
        return m.toString();
    }

    public final void updateTaskVisibility() {
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.setHidden(this.mTaskToken, !this.mSurfaceCreated);
        this.mSyncQueue.queue(windowContainerTransaction);
        if (this.mListener != null) {
            this.mSyncQueue.runInSync(new TaskView$$ExternalSyntheticLambda0(this, this.mTaskInfo.taskId));
        }
    }

    public static void $r8$lambda$GN2DCVjyacAWE0TrNN18t7khmiQ(TaskView taskView, ShortcutInfo shortcutInfo, ActivityOptions activityOptions) {
        Objects.requireNonNull(taskView);
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.startShortcut(taskView.mContext.getPackageName(), shortcutInfo, activityOptions.toBundle());
        TaskViewTransitions taskViewTransitions = taskView.mTaskViewTransitions;
        Objects.requireNonNull(taskViewTransitions);
        taskViewTransitions.mPending.add(new TaskViewTransitions.PendingTransition(1, windowContainerTransaction, taskView));
        taskViewTransitions.startNextTransition();
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnComputeInternalInsetsListener(this);
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnComputeInternalInsetsListener(this);
    }

    public final void onTaskAppeared(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl) {
        if (!isUsingShellTransitions()) {
            this.mTaskInfo = runningTaskInfo;
            this.mTaskToken = runningTaskInfo.token;
            this.mTaskLeash = surfaceControl;
            if (this.mSurfaceCreated) {
                this.mTransaction.reparent(surfaceControl, getSurfaceControl()).show(this.mTaskLeash).apply();
            } else {
                updateTaskVisibility();
            }
            this.mTaskOrganizer.setInterceptBackPressedOnTaskRoot(this.mTaskToken, true);
            onLocationChanged();
            ActivityManager.TaskDescription taskDescription = runningTaskInfo.taskDescription;
            if (taskDescription != null) {
                this.mSyncQueue.runInSync(new TaskView$$ExternalSyntheticLambda1(this, taskDescription.getBackgroundColor()));
            }
            if (this.mListener != null) {
                this.mListenerExecutor.execute(new TaskView$$ExternalSyntheticLambda13(this, runningTaskInfo.taskId, runningTaskInfo.baseActivity));
            }
        }
    }

    public final void performRelease() {
        getHolder().removeCallback(this);
        TaskViewTransitions taskViewTransitions = this.mTaskViewTransitions;
        if (taskViewTransitions != null) {
            Objects.requireNonNull(taskViewTransitions);
            taskViewTransitions.mTaskViews.remove(this);
        }
        this.mShellExecutor.execute(new TaskView$$ExternalSyntheticLambda3(this, 0));
        this.mGuard.close();
        if (this.mListener != null && this.mIsInitialized) {
            this.mListenerExecutor.execute(new TaskView$$ExternalSyntheticLambda4(this, 0));
            this.mIsInitialized = false;
        }
    }

    public final void startActivity(PendingIntent pendingIntent, Intent intent, ActivityOptions activityOptions, Rect rect) {
        prepareActivityOptions(activityOptions, rect);
        if (isUsingShellTransitions()) {
            this.mShellExecutor.execute(new TaskView$$ExternalSyntheticLambda14(this, pendingIntent, intent, activityOptions));
            return;
        }
        try {
            pendingIntent.send(this.mContext, 0, intent, (PendingIntent.OnFinished) null, (Handler) null, (String) null, activityOptions.toBundle());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public final void startShortcutActivity(ShortcutInfo shortcutInfo, ActivityOptions activityOptions, Rect rect) {
        prepareActivityOptions(activityOptions, rect);
        LauncherApps launcherApps = (LauncherApps) this.mContext.getSystemService(LauncherApps.class);
        if (isUsingShellTransitions()) {
            this.mShellExecutor.execute(new TaskView$$ExternalSyntheticLambda8(this, shortcutInfo, activityOptions, 0));
            return;
        }
        try {
            launcherApps.startShortcut(shortcutInfo, (Rect) null, activityOptions.toBundle());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public final void onLocationChanged() {
        if (this.mTaskToken != null) {
            if (isUsingShellTransitions()) {
                TaskViewTransitions taskViewTransitions = this.mTaskViewTransitions;
                Objects.requireNonNull(taskViewTransitions);
                if (!taskViewTransitions.mPending.isEmpty()) {
                    return;
                }
            }
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            onLocationChanged(windowContainerTransaction);
            this.mSyncQueue.queue(windowContainerTransaction);
        }
    }
}
