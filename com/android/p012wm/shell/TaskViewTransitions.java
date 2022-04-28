package com.android.p012wm.shell;

import android.app.ActivityManager;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.IBinder;
import android.util.Slog;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.p012wm.shell.TaskView;
import com.android.p012wm.shell.transition.Transitions;
import com.android.p012wm.shell.transition.Transitions$$ExternalSyntheticLambda1;
import java.util.ArrayList;
import java.util.Objects;

/* renamed from: com.android.wm.shell.TaskViewTransitions */
public class TaskViewTransitions implements Transitions.TransitionHandler {
    public final ArrayList<PendingTransition> mPending = new ArrayList<>();
    public final boolean[] mRegistered = {false};
    public final ArrayList<TaskView> mTaskViews = new ArrayList<>();
    public final Transitions mTransitions;

    public final TaskView findTaskView(ActivityManager.RunningTaskInfo runningTaskInfo) {
        for (int i = 0; i < this.mTaskViews.size(); i++) {
            TaskView taskView = this.mTaskViews.get(i);
            Objects.requireNonNull(taskView);
            if (taskView.mTaskInfo != null) {
                WindowContainerToken windowContainerToken = runningTaskInfo.token;
                TaskView taskView2 = this.mTaskViews.get(i);
                Objects.requireNonNull(taskView2);
                if (windowContainerToken.equals(taskView2.mTaskInfo.token)) {
                    return this.mTaskViews.get(i);
                }
            }
        }
        return null;
    }

    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        PendingTransition pendingTransition;
        boolean z;
        TaskView taskView;
        int i = 0;
        while (true) {
            if (i < this.mPending.size()) {
                if (this.mPending.get(i).mClaimed == iBinder) {
                    pendingTransition = this.mPending.get(i);
                    break;
                }
                i++;
            } else {
                pendingTransition = null;
                break;
            }
        }
        if (pendingTransition == null) {
            return false;
        }
        this.mPending.remove(pendingTransition);
        TaskView taskView2 = pendingTransition.mTaskView;
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < transitionInfo.getChanges().size(); i2++) {
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(i2);
            if (change.getTaskInfo() != null) {
                arrayList.add(change);
            }
        }
        if (arrayList.isEmpty()) {
            Slog.e("TaskViewTransitions", "Got a TaskView transition with no task.");
            return false;
        }
        int i3 = 0;
        WindowContainerTransaction windowContainerTransaction = null;
        while (true) {
            boolean z2 = true;
            if (i3 < arrayList.size()) {
                TransitionInfo.Change change2 = (TransitionInfo.Change) arrayList.get(i3);
                if (Transitions.isClosingType(change2.getMode())) {
                    if (change2.getMode() != 4) {
                        z2 = false;
                    }
                    TaskView findTaskView = findTaskView(change2.getTaskInfo());
                    if (findTaskView == null) {
                        throw new IllegalStateException("TaskView transition is closing a non-taskview task ");
                    } else if (!z2) {
                        if (findTaskView.mTaskToken != null) {
                            if (findTaskView.mListener != null) {
                                findTaskView.mListenerExecutor.execute(new TaskView$$ExternalSyntheticLambda10(findTaskView, findTaskView.mTaskInfo.taskId));
                            }
                            findTaskView.mTaskOrganizer.setInterceptBackPressedOnTaskRoot(findTaskView.mTaskToken, false);
                        }
                        findTaskView.mTaskInfo = null;
                        findTaskView.mTaskToken = null;
                        findTaskView.mTaskLeash = null;
                    } else if (findTaskView.mTaskToken != null) {
                        transaction2.reparent(findTaskView.mTaskLeash, (SurfaceControl) null).apply();
                        TaskView.Listener listener = findTaskView.mListener;
                        if (listener != null) {
                            int i4 = findTaskView.mTaskInfo.taskId;
                            listener.onTaskVisibilityChanged(findTaskView.mSurfaceCreated);
                        }
                    }
                } else if (Transitions.isOpeningType(change2.getMode())) {
                    if (change2.getMode() == 1) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (windowContainerTransaction == null) {
                        windowContainerTransaction = new WindowContainerTransaction();
                    }
                    if (!z) {
                        taskView = findTaskView(change2.getTaskInfo());
                        if (taskView == null) {
                            throw new IllegalStateException("TaskView transition is showing a non-taskview task ");
                        }
                    } else {
                        taskView = taskView2;
                    }
                    ActivityManager.RunningTaskInfo taskInfo = change2.getTaskInfo();
                    SurfaceControl leash = change2.getLeash();
                    Objects.requireNonNull(taskView);
                    taskView.mTaskInfo = taskInfo;
                    WindowContainerToken windowContainerToken = taskInfo.token;
                    taskView.mTaskToken = windowContainerToken;
                    taskView.mTaskLeash = leash;
                    if (taskView.mSurfaceCreated) {
                        transaction.reparent(leash, taskView.getSurfaceControl()).show(taskView.mTaskLeash).apply();
                        transaction2.reparent(taskView.mTaskLeash, taskView.getSurfaceControl()).setPosition(taskView.mTaskLeash, 0.0f, 0.0f).apply();
                        taskView.onLocationChanged(windowContainerTransaction);
                    } else {
                        windowContainerTransaction.setHidden(windowContainerToken, true);
                    }
                    if (z) {
                        taskView.mTaskOrganizer.setInterceptBackPressedOnTaskRoot(taskView.mTaskToken, true);
                    }
                    ActivityManager.TaskDescription taskDescription = taskView.mTaskInfo.taskDescription;
                    if (taskDescription != null) {
                        taskView.setResizeBackgroundColor(transaction, taskDescription.getBackgroundColor());
                    }
                    if (taskView.mListener != null) {
                        ActivityManager.RunningTaskInfo runningTaskInfo = taskView.mTaskInfo;
                        taskView.mListenerExecutor.execute(new TaskView$$ExternalSyntheticLambda15(taskView, z, runningTaskInfo.taskId, runningTaskInfo.baseActivity));
                    }
                } else {
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Claimed transition isn't an opening or closing type: ");
                    m.append(change2.getMode());
                    throw new IllegalStateException(m.toString());
                }
                i3++;
            } else {
                transaction.apply();
                transaction2.apply();
                ((Transitions$$ExternalSyntheticLambda1) transitionFinishCallback).onTransitionFinished(windowContainerTransaction);
                startNextTransition();
                return true;
            }
        }
    }

    /* renamed from: com.android.wm.shell.TaskViewTransitions$PendingTransition */
    public static class PendingTransition {
        public IBinder mClaimed;
        public final TaskView mTaskView;
        public final int mType;
        public final WindowContainerTransaction mWct;

        public PendingTransition(int i, WindowContainerTransaction windowContainerTransaction, TaskView taskView) {
            this.mType = i;
            this.mWct = windowContainerTransaction;
            this.mTaskView = taskView;
        }
    }

    public final PendingTransition findPending(TaskView taskView, boolean z, boolean z2) {
        int size = this.mPending.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            } else if (this.mPending.get(size).mTaskView == taskView) {
                if (Transitions.isClosingType(this.mPending.get(size).mType) == z) {
                    return this.mPending.get(size);
                }
                if (z2) {
                    break;
                }
            }
        }
        return null;
    }

    public final void setTaskViewVisible(TaskView taskView, boolean z) {
        int i;
        boolean z2 = !z;
        if (findPending(taskView, z2, true) == null && taskView.mTaskInfo != null) {
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            windowContainerTransaction.setHidden(taskView.mTaskInfo.token, z2);
            if (z) {
                i = 3;
            } else {
                i = 4;
            }
            this.mPending.add(new PendingTransition(i, windowContainerTransaction, taskView));
            startNextTransition();
        }
    }

    public final void startNextTransition() {
        if (!this.mPending.isEmpty()) {
            PendingTransition pendingTransition = this.mPending.get(0);
            if (pendingTransition.mClaimed == null) {
                pendingTransition.mClaimed = this.mTransitions.startTransition(pendingTransition.mType, pendingTransition.mWct, this);
            }
        }
    }

    public TaskViewTransitions(Transitions transitions) {
        this.mTransitions = transitions;
    }

    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        TaskView findTaskView;
        ActivityManager.RunningTaskInfo triggerTask = transitionRequestInfo.getTriggerTask();
        if (triggerTask == null || (findTaskView = findTaskView(triggerTask)) == null || !Transitions.isClosingType(transitionRequestInfo.getType())) {
            return null;
        }
        PendingTransition findPending = findPending(findTaskView, true, false);
        if (findPending == null) {
            findPending = new PendingTransition(transitionRequestInfo.getType(), (WindowContainerTransaction) null, findTaskView);
        }
        if (findPending.mClaimed == null) {
            findPending.mClaimed = iBinder;
            return new WindowContainerTransaction();
        }
        throw new IllegalStateException("Task is closing in 2 collecting transitions? This state doesn't make sense");
    }
}
