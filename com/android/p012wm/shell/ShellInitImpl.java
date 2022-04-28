package com.android.p012wm.shell;

import com.android.p012wm.shell.apppairs.AppPairsController;
import com.android.p012wm.shell.bubbles.BubbleController;
import com.android.p012wm.shell.common.DisplayController;
import com.android.p012wm.shell.common.DisplayImeController;
import com.android.p012wm.shell.common.DisplayInsetsController;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.draganddrop.DragAndDropController;
import com.android.p012wm.shell.freeform.FreeformTaskListener;
import com.android.p012wm.shell.fullscreen.FullscreenTaskListener;
import com.android.p012wm.shell.fullscreen.FullscreenUnfoldController;
import com.android.p012wm.shell.pip.phone.PipTouchHandler;
import com.android.p012wm.shell.recents.RecentTasksController;
import com.android.p012wm.shell.splitscreen.SplitScreenController;
import com.android.p012wm.shell.startingsurface.StartingWindowController;
import com.android.p012wm.shell.transition.Transitions;
import java.util.Optional;

/* renamed from: com.android.wm.shell.ShellInitImpl */
public class ShellInitImpl {
    public final Optional<AppPairsController> mAppPairsOptional;
    public final Optional<BubbleController> mBubblesOptional;
    public final DisplayController mDisplayController;
    public final DisplayImeController mDisplayImeController;
    public final DisplayInsetsController mDisplayInsetsController;
    public final DragAndDropController mDragAndDropController;
    public final Optional<FreeformTaskListener> mFreeformTaskListenerOptional;
    public final FullscreenTaskListener mFullscreenTaskListener;
    public final Optional<FullscreenUnfoldController> mFullscreenUnfoldController;
    public final InitImpl mImpl = new InitImpl();
    public final ShellExecutor mMainExecutor;
    public final Optional<PipTouchHandler> mPipTouchHandlerOptional;
    public final Optional<RecentTasksController> mRecentTasks;
    public final ShellTaskOrganizer mShellTaskOrganizer;
    public final Optional<SplitScreenController> mSplitScreenOptional;
    public final StartingWindowController mStartingWindow;
    public final Transitions mTransitions;

    /* renamed from: com.android.wm.shell.ShellInitImpl$InitImpl */
    public class InitImpl implements ShellInit {
        public InitImpl() {
        }

        public final void init() {
            try {
                ShellInitImpl.this.mMainExecutor.executeBlocking$1(new ShellInitImpl$InitImpl$$ExternalSyntheticLambda0(this));
            } catch (InterruptedException e) {
                throw new RuntimeException("Failed to initialize the Shell in 2s", e);
            }
        }
    }

    public ShellInitImpl(DisplayController displayController, DisplayImeController displayImeController, DisplayInsetsController displayInsetsController, DragAndDropController dragAndDropController, ShellTaskOrganizer shellTaskOrganizer, Optional<BubbleController> optional, Optional<SplitScreenController> optional2, Optional<AppPairsController> optional3, Optional<PipTouchHandler> optional4, FullscreenTaskListener fullscreenTaskListener, Optional<FullscreenUnfoldController> optional5, Optional<FreeformTaskListener> optional6, Optional<RecentTasksController> optional7, Transitions transitions, StartingWindowController startingWindowController, ShellExecutor shellExecutor) {
        this.mDisplayController = displayController;
        this.mDisplayImeController = displayImeController;
        this.mDisplayInsetsController = displayInsetsController;
        this.mDragAndDropController = dragAndDropController;
        this.mShellTaskOrganizer = shellTaskOrganizer;
        this.mBubblesOptional = optional;
        this.mSplitScreenOptional = optional2;
        this.mAppPairsOptional = optional3;
        this.mFullscreenTaskListener = fullscreenTaskListener;
        this.mPipTouchHandlerOptional = optional4;
        this.mFullscreenUnfoldController = optional5;
        this.mFreeformTaskListenerOptional = optional6;
        this.mRecentTasks = optional7;
        this.mTransitions = transitions;
        this.mMainExecutor = shellExecutor;
        this.mStartingWindow = startingWindowController;
    }
}
