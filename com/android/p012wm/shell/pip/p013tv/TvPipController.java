package com.android.p012wm.shell.pip.p013tv;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.RemoteAction;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ParceledListSlice;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import android.view.IWindow;
import android.view.SurfaceControl;
import android.view.ViewRootImpl;
import android.view.WindowManagerGlobal;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.WindowManagerShellWrapper;
import com.android.p012wm.shell.common.DisplayLayout;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.TaskStackListenerCallback;
import com.android.p012wm.shell.common.TaskStackListenerImpl;
import com.android.p012wm.shell.pip.PinnedStackListenerForwarder;
import com.android.p012wm.shell.pip.Pip;
import com.android.p012wm.shell.pip.PipMediaController;
import com.android.p012wm.shell.pip.PipMenuController;
import com.android.p012wm.shell.pip.PipTaskOrganizer;
import com.android.p012wm.shell.pip.PipTransitionController;
import com.android.p012wm.shell.pip.p013tv.TvPipMenuController;
import com.android.p012wm.shell.pip.p013tv.TvPipNotificationController;
import com.android.systemui.p006qs.QSTileHost$$ExternalSyntheticLambda1;
import com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda10;
import com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda2;
import java.util.Objects;

/* renamed from: com.android.wm.shell.pip.tv.TvPipController */
public final class TvPipController implements PipTransitionController.PipTransitionCallback, TvPipMenuController.Delegate, TvPipNotificationController.Delegate {
    public final Context mContext;
    public final TvPipImpl mImpl = new TvPipImpl();
    public final ShellExecutor mMainExecutor;
    public int mPinnedTaskId = -1;
    public final PipMediaController mPipMediaController;
    public final TvPipNotificationController mPipNotificationController;
    public final PipTaskOrganizer mPipTaskOrganizer;
    public int mPreviousGravity = 85;
    public int mResizeAnimationDuration;
    public int mState = 0;
    public final TvPipBoundsAlgorithm mTvPipBoundsAlgorithm;
    public final TvPipBoundsState mTvPipBoundsState;
    public final TvPipMenuController mTvPipMenuController;

    /* renamed from: com.android.wm.shell.pip.tv.TvPipController$TvPipImpl */
    public class TvPipImpl implements Pip {
        public TvPipImpl() {
        }

        public final void onConfigurationChanged(Configuration configuration) {
            TvPipController.this.mMainExecutor.execute(new BubblesManager$5$$ExternalSyntheticLambda2(this, configuration, 4));
        }

        public final void registerSessionListenerForCurrentUser() {
            TvPipController.this.mMainExecutor.execute(new VolumeDialogImpl$$ExternalSyntheticLambda10(this, 9));
        }
    }

    public final void onPipTransitionCanceled(int i) {
    }

    public final void onPipTransitionFinished(int i) {
    }

    public final void onPipTransitionStarted(int i, Rect rect) {
    }

    public final void closePip() {
        try {
            ActivityTaskManager.getService().removeTask(this.mPinnedTaskId);
        } catch (Exception e) {
            Log.e("TvPipController", "Atm.removeTask() failed", e);
        }
        onPipDisappeared();
    }

    public final void movePinnedStack() {
        if (this.mState != 0) {
            TvPipBoundsAlgorithm tvPipBoundsAlgorithm = this.mTvPipBoundsAlgorithm;
            TvPipBoundsState tvPipBoundsState = this.mTvPipBoundsState;
            Objects.requireNonNull(tvPipBoundsState);
            Rect tvPipBounds = tvPipBoundsAlgorithm.getTvPipBounds(tvPipBoundsState.mIsTvPipExpanded);
            PipTaskOrganizer pipTaskOrganizer = this.mPipTaskOrganizer;
            int i = this.mResizeAnimationDuration;
            QSTileHost$$ExternalSyntheticLambda1 qSTileHost$$ExternalSyntheticLambda1 = new QSTileHost$$ExternalSyntheticLambda1(this, 4);
            Objects.requireNonNull(pipTaskOrganizer);
            pipTaskOrganizer.scheduleAnimateResizePip(tvPipBounds, i, 0, qSTileHost$$ExternalSyntheticLambda1);
        }
    }

    public final void onPipDisappeared() {
        TvPipNotificationController tvPipNotificationController = this.mPipNotificationController;
        Objects.requireNonNull(tvPipNotificationController);
        tvPipNotificationController.mNotificationManager.cancel("TvPip", 1100);
        tvPipNotificationController.mNotified = false;
        tvPipNotificationController.mPackageName = null;
        TvPipNotificationController.ActionBroadcastReceiver actionBroadcastReceiver = tvPipNotificationController.mActionBroadcastReceiver;
        Objects.requireNonNull(actionBroadcastReceiver);
        if (actionBroadcastReceiver.mRegistered) {
            TvPipNotificationController.this.mContext.unregisterReceiver(actionBroadcastReceiver);
            actionBroadcastReceiver.mRegistered = false;
        }
        this.mTvPipMenuController.hideMenu();
        TvPipBoundsState tvPipBoundsState = this.mTvPipBoundsState;
        Objects.requireNonNull(tvPipBoundsState);
        tvPipBoundsState.mTvFixedPipOrientation = 0;
        tvPipBoundsState.mTvPipGravity = 85;
        setState(0);
        this.mPinnedTaskId = -1;
    }

    public final void setState(int i) {
        SurfaceControl surfaceControl;
        this.mState = i;
        if (i == 2) {
            TvPipMenuController tvPipMenuController = this.mTvPipMenuController;
            Objects.requireNonNull(tvPipMenuController);
            if (tvPipMenuController.mPipMenuView != null) {
                Rect menuBounds = tvPipMenuController.getMenuBounds(tvPipMenuController.mTvPipBoundsState.getBounds());
                tvPipMenuController.mSystemWindows.updateViewLayout(tvPipMenuController.mPipMenuView, PipMenuController.getPipMenuLayoutParams(menuBounds.width(), menuBounds.height()));
                tvPipMenuController.maybeUpdateMenuViewActions();
                tvPipMenuController.updateExpansionState();
                SurfaceControl viewSurface = tvPipMenuController.mSystemWindows.getViewSurface(tvPipMenuController.mPipMenuView);
                if (viewSurface != null) {
                    SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                    TvPipMenuView tvPipMenuView = tvPipMenuController.mPipMenuView;
                    Objects.requireNonNull(tvPipMenuView);
                    ViewRootImpl viewRootImpl = tvPipMenuView.getViewRootImpl();
                    if (viewRootImpl == null || (surfaceControl = viewRootImpl.getSurfaceControl()) == null || !surfaceControl.isValid()) {
                        surfaceControl = null;
                    }
                    transaction.setRelativeLayer(surfaceControl, tvPipMenuController.mLeash, 1);
                    transaction.setPosition(viewSurface, (float) menuBounds.left, (float) menuBounds.top);
                    transaction.apply();
                }
                TvPipMenuView tvPipMenuView2 = tvPipMenuController.mPipMenuView;
                boolean z = tvPipMenuController.mInMoveMode;
                TvPipController tvPipController = (TvPipController) tvPipMenuController.mDelegate;
                Objects.requireNonNull(tvPipController);
                TvPipBoundsState tvPipBoundsState = tvPipController.mTvPipBoundsState;
                Objects.requireNonNull(tvPipBoundsState);
                int i2 = tvPipBoundsState.mTvPipGravity;
                Objects.requireNonNull(tvPipMenuView2);
                try {
                    WindowManagerGlobal.getWindowSession().grantEmbeddedWindowFocus((IWindow) null, tvPipMenuView2.mFocusGrantToken, true);
                } catch (Exception e) {
                    Log.e("TvPipMenuView", "Unable to update focus", e);
                }
                if (z) {
                    tvPipMenuView2.showMovementHints(i2);
                } else {
                    TvPipMenuView.animateAlphaTo(1.0f, tvPipMenuView2.mActionButtonsContainer);
                }
                TvPipMenuView.animateAlphaTo(1.0f, tvPipMenuView2.mMenuFrameView);
            }
        }
    }

    public TvPipController(Context context, TvPipBoundsState tvPipBoundsState, TvPipBoundsAlgorithm tvPipBoundsAlgorithm, PipTaskOrganizer pipTaskOrganizer, PipTransitionController pipTransitionController, TvPipMenuController tvPipMenuController, PipMediaController pipMediaController, TvPipNotificationController tvPipNotificationController, TaskStackListenerImpl taskStackListenerImpl, WindowManagerShellWrapper windowManagerShellWrapper, ShellExecutor shellExecutor) {
        this.mContext = context;
        this.mMainExecutor = shellExecutor;
        this.mTvPipBoundsState = tvPipBoundsState;
        int displayId = context.getDisplayId();
        Objects.requireNonNull(tvPipBoundsState);
        tvPipBoundsState.mDisplayId = displayId;
        tvPipBoundsState.mDisplayLayout.set(new DisplayLayout(context, context.getDisplay()));
        this.mTvPipBoundsAlgorithm = tvPipBoundsAlgorithm;
        this.mPipMediaController = pipMediaController;
        this.mPipNotificationController = tvPipNotificationController;
        Objects.requireNonNull(tvPipNotificationController);
        if (tvPipNotificationController.mDelegate == null) {
            tvPipNotificationController.mDelegate = this;
            this.mTvPipMenuController = tvPipMenuController;
            Objects.requireNonNull(tvPipMenuController);
            if (tvPipMenuController.mDelegate == null) {
                tvPipMenuController.mDelegate = this;
                this.mPipTaskOrganizer = pipTaskOrganizer;
                Objects.requireNonNull(pipTransitionController);
                pipTransitionController.mPipTransitionCallbacks.add(this);
                this.mResizeAnimationDuration = context.getResources().getInteger(C1777R.integer.config_pipResizeAnimationDuration);
                taskStackListenerImpl.addListener(new TaskStackListenerCallback() {
                    public final void onActivityPinned(String str) {
                        ActivityTaskManager.RootTaskInfo rootTaskInfo;
                        TvPipController tvPipController = TvPipController.this;
                        Objects.requireNonNull(tvPipController);
                        try {
                            rootTaskInfo = ActivityTaskManager.getService().getRootTaskInfo(2, 0);
                        } catch (RemoteException e) {
                            Log.e("TvPipController", "getRootTaskInfo() failed", e);
                            rootTaskInfo = null;
                        }
                        if (rootTaskInfo != null && rootTaskInfo.topActivity != null) {
                            tvPipController.mPinnedTaskId = rootTaskInfo.taskId;
                            tvPipController.setState(1);
                            PipMediaController pipMediaController = tvPipController.mPipMediaController;
                            Objects.requireNonNull(pipMediaController);
                            pipMediaController.resolveActiveMediaController(pipMediaController.mMediaSessionManager.getActiveSessionsForUser((ComponentName) null, UserHandle.CURRENT));
                            TvPipNotificationController tvPipNotificationController = tvPipController.mPipNotificationController;
                            String packageName = rootTaskInfo.topActivity.getPackageName();
                            Objects.requireNonNull(tvPipNotificationController);
                            if (tvPipNotificationController.mDelegate != null) {
                                tvPipNotificationController.mPackageName = packageName;
                                tvPipNotificationController.update();
                                TvPipNotificationController.ActionBroadcastReceiver actionBroadcastReceiver = tvPipNotificationController.mActionBroadcastReceiver;
                                Objects.requireNonNull(actionBroadcastReceiver);
                                if (!actionBroadcastReceiver.mRegistered) {
                                    TvPipNotificationController tvPipNotificationController2 = TvPipNotificationController.this;
                                    tvPipNotificationController2.mContext.registerReceiverForAllUsers(actionBroadcastReceiver, actionBroadcastReceiver.mIntentFilter, "com.android.systemui.permission.SELF", tvPipNotificationController2.mMainHandler);
                                    actionBroadcastReceiver.mRegistered = true;
                                    return;
                                }
                                return;
                            }
                            throw new IllegalStateException("Delegate is not set.");
                        }
                    }

                    public final void onTaskStackChanged() {
                        boolean z;
                        ActivityTaskManager.RootTaskInfo rootTaskInfo;
                        TvPipController tvPipController = TvPipController.this;
                        Objects.requireNonNull(tvPipController);
                        if (tvPipController.mState != 0) {
                            z = true;
                        } else {
                            z = false;
                        }
                        if (z) {
                            try {
                                rootTaskInfo = ActivityTaskManager.getService().getRootTaskInfo(2, 0);
                            } catch (RemoteException e) {
                                Log.e("TvPipController", "getRootTaskInfo() failed", e);
                                rootTaskInfo = null;
                            }
                            if (rootTaskInfo == null) {
                                Log.w("TvPipController", "Pinned task is gone.");
                                tvPipController.onPipDisappeared();
                            }
                        }
                    }

                    public final void onActivityRestartAttempt(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, boolean z2) {
                        if (runningTaskInfo.getWindowingMode() == 2) {
                            TvPipController tvPipController = TvPipController.this;
                            Objects.requireNonNull(tvPipController);
                            tvPipController.mPipTaskOrganizer.exitPip(tvPipController.mResizeAnimationDuration, false);
                            tvPipController.onPipDisappeared();
                        }
                    }
                });
                try {
                    windowManagerShellWrapper.addPinnedStackListener(new PinnedStackListenerForwarder.PinnedTaskListener() {
                        public final void onMovementBoundsChanged(boolean z) {
                        }

                        public final void onActionsChanged(ParceledListSlice<RemoteAction> parceledListSlice) {
                            TvPipMenuController tvPipMenuController = TvPipController.this.mTvPipMenuController;
                            Objects.requireNonNull(tvPipMenuController);
                            tvPipMenuController.updateAdditionalActionsList(tvPipMenuController.mAppActions, parceledListSlice.getList());
                        }

                        public final void onAspectRatioChanged(float f) {
                            boolean z;
                            TvPipBoundsState tvPipBoundsState = TvPipController.this.mTvPipBoundsState;
                            Objects.requireNonNull(tvPipBoundsState);
                            if (tvPipBoundsState.mAspectRatio != f) {
                                z = true;
                            } else {
                                z = false;
                            }
                            TvPipBoundsState tvPipBoundsState2 = TvPipController.this.mTvPipBoundsState;
                            Objects.requireNonNull(tvPipBoundsState2);
                            tvPipBoundsState2.mAspectRatio = f;
                            TvPipBoundsState tvPipBoundsState3 = TvPipController.this.mTvPipBoundsState;
                            Objects.requireNonNull(tvPipBoundsState3);
                            if (!tvPipBoundsState3.mIsTvPipExpanded && z) {
                                TvPipController.this.movePinnedStack();
                            }
                        }

                        public final void onExpandedAspectRatioChanged(float f) {
                            TvPipBoundsState tvPipBoundsState = TvPipController.this.mTvPipBoundsState;
                            Objects.requireNonNull(tvPipBoundsState);
                            if (tvPipBoundsState.mTvExpandedAspectRatio != f) {
                                TvPipController.this.mTvPipBoundsState.setTvExpandedAspectRatio(f, false);
                                TvPipBoundsState tvPipBoundsState2 = TvPipController.this.mTvPipBoundsState;
                                Objects.requireNonNull(tvPipBoundsState2);
                                if (tvPipBoundsState2.mIsTvPipExpanded && f != 0.0f) {
                                    TvPipController.this.movePinnedStack();
                                }
                                TvPipBoundsState tvPipBoundsState3 = TvPipController.this.mTvPipBoundsState;
                                Objects.requireNonNull(tvPipBoundsState3);
                                if (tvPipBoundsState3.mIsTvPipExpanded && f == 0.0f) {
                                    TvPipController tvPipController = TvPipController.this;
                                    int updatePositionOnExpandToggled = tvPipController.mTvPipBoundsAlgorithm.updatePositionOnExpandToggled(tvPipController.mPreviousGravity, false);
                                    if (updatePositionOnExpandToggled != 0) {
                                        TvPipController.this.mPreviousGravity = updatePositionOnExpandToggled;
                                    }
                                    TvPipBoundsState tvPipBoundsState4 = TvPipController.this.mTvPipBoundsState;
                                    Objects.requireNonNull(tvPipBoundsState4);
                                    tvPipBoundsState4.mIsTvPipExpanded = false;
                                    TvPipController.this.movePinnedStack();
                                }
                                TvPipBoundsState tvPipBoundsState5 = TvPipController.this.mTvPipBoundsState;
                                Objects.requireNonNull(tvPipBoundsState5);
                                if (!tvPipBoundsState5.mIsTvPipExpanded && f != 0.0f) {
                                    TvPipBoundsState tvPipBoundsState6 = TvPipController.this.mTvPipBoundsState;
                                    Objects.requireNonNull(tvPipBoundsState6);
                                    if (!tvPipBoundsState6.mTvPipManuallyCollapsed) {
                                        TvPipController tvPipController2 = TvPipController.this;
                                        int updatePositionOnExpandToggled2 = tvPipController2.mTvPipBoundsAlgorithm.updatePositionOnExpandToggled(tvPipController2.mPreviousGravity, true);
                                        if (updatePositionOnExpandToggled2 != 0) {
                                            TvPipController.this.mPreviousGravity = updatePositionOnExpandToggled2;
                                        }
                                        TvPipBoundsState tvPipBoundsState7 = TvPipController.this.mTvPipBoundsState;
                                        Objects.requireNonNull(tvPipBoundsState7);
                                        tvPipBoundsState7.mIsTvPipExpanded = true;
                                        TvPipController.this.movePinnedStack();
                                    }
                                }
                            }
                        }

                        public final void onImeVisibilityChanged(boolean z, int i) {
                            TvPipBoundsState tvPipBoundsState = TvPipController.this.mTvPipBoundsState;
                            Objects.requireNonNull(tvPipBoundsState);
                            if (z == tvPipBoundsState.mIsImeShowing) {
                                if (z) {
                                    TvPipBoundsState tvPipBoundsState2 = TvPipController.this.mTvPipBoundsState;
                                    Objects.requireNonNull(tvPipBoundsState2);
                                    if (i == tvPipBoundsState2.mImeHeight) {
                                        return;
                                    }
                                } else {
                                    return;
                                }
                            }
                            TvPipBoundsState tvPipBoundsState3 = TvPipController.this.mTvPipBoundsState;
                            Objects.requireNonNull(tvPipBoundsState3);
                            tvPipBoundsState3.mIsImeShowing = z;
                            tvPipBoundsState3.mImeHeight = i;
                            TvPipController tvPipController = TvPipController.this;
                            if (tvPipController.mState != 0) {
                                tvPipController.movePinnedStack();
                            }
                        }
                    });
                } catch (RemoteException e) {
                    Log.e("TvPipController", "Failed to register pinned stack listener", e);
                }
            } else {
                throw new IllegalStateException("The delegate has already been set and should not change.");
            }
        } else {
            throw new IllegalStateException("The delegate has already been set and should not change.");
        }
    }
}
