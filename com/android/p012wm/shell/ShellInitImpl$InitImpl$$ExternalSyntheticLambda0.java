package com.android.p012wm.shell;

import android.os.RemoteException;
import android.window.TransitionMetrics;
import com.android.p012wm.shell.ShellInitImpl;
import com.android.p012wm.shell.common.DisplayController;
import com.android.p012wm.shell.common.DisplayImeController;
import com.android.p012wm.shell.common.DisplayInsetsController;
import com.android.p012wm.shell.draganddrop.DragAndDropController;
import com.android.p012wm.shell.splitscreen.SplitScreenController;
import com.android.p012wm.shell.startingsurface.StartingWindowController;
import com.android.p012wm.shell.transition.Transitions;
import java.util.Objects;
import java.util.Optional;

/* renamed from: com.android.wm.shell.ShellInitImpl$InitImpl$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ShellInitImpl$InitImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ ShellInitImpl.InitImpl f$0;

    public /* synthetic */ ShellInitImpl$InitImpl$$ExternalSyntheticLambda0(ShellInitImpl.InitImpl initImpl) {
        this.f$0 = initImpl;
    }

    public final void run() {
        ShellInitImpl.InitImpl initImpl = this.f$0;
        Objects.requireNonNull(initImpl);
        ShellInitImpl shellInitImpl = ShellInitImpl.this;
        Objects.requireNonNull(shellInitImpl);
        DisplayController displayController = shellInitImpl.mDisplayController;
        Objects.requireNonNull(displayController);
        try {
            int[] registerDisplayWindowListener = displayController.mWmService.registerDisplayWindowListener(displayController.mDisplayContainerListener);
            for (int onDisplayAdded : registerDisplayWindowListener) {
                displayController.onDisplayAdded(onDisplayAdded);
            }
            DisplayInsetsController displayInsetsController = shellInitImpl.mDisplayInsetsController;
            Objects.requireNonNull(displayInsetsController);
            displayInsetsController.mDisplayController.addDisplayWindowListener(displayInsetsController);
            DisplayImeController displayImeController = shellInitImpl.mDisplayImeController;
            Objects.requireNonNull(displayImeController);
            displayImeController.mDisplayController.addDisplayWindowListener(displayImeController);
            shellInitImpl.mShellTaskOrganizer.addListenerForType(shellInitImpl.mFullscreenTaskListener, -2);
            ShellTaskOrganizer shellTaskOrganizer = shellInitImpl.mShellTaskOrganizer;
            StartingWindowController startingWindowController = shellInitImpl.mStartingWindow;
            Objects.requireNonNull(shellTaskOrganizer);
            shellTaskOrganizer.mStartingWindow = startingWindowController;
            shellInitImpl.mShellTaskOrganizer.registerOrganizer();
            shellInitImpl.mAppPairsOptional.ifPresent(ShellInitImpl$$ExternalSyntheticLambda1.INSTANCE);
            shellInitImpl.mSplitScreenOptional.ifPresent(ShellInitImpl$$ExternalSyntheticLambda6.INSTANCE);
            shellInitImpl.mBubblesOptional.ifPresent(ShellInitImpl$$ExternalSyntheticLambda5.INSTANCE);
            DragAndDropController dragAndDropController = shellInitImpl.mDragAndDropController;
            Optional<SplitScreenController> optional = shellInitImpl.mSplitScreenOptional;
            Objects.requireNonNull(dragAndDropController);
            dragAndDropController.mSplitScreen = optional.orElse((Object) null);
            dragAndDropController.mDisplayController.addDisplayWindowListener(dragAndDropController);
            if (Transitions.ENABLE_SHELL_TRANSITIONS) {
                Transitions transitions = shellInitImpl.mTransitions;
                ShellTaskOrganizer shellTaskOrganizer2 = shellInitImpl.mShellTaskOrganizer;
                Objects.requireNonNull(transitions);
                Transitions.TransitionPlayerImpl transitionPlayerImpl = transitions.mPlayerImpl;
                if (transitionPlayerImpl != null) {
                    shellTaskOrganizer2.registerTransitionPlayer(transitionPlayerImpl);
                    TransitionMetrics.getInstance();
                }
            }
            shellInitImpl.mPipTouchHandlerOptional.ifPresent(ShellInitImpl$$ExternalSyntheticLambda3.INSTANCE);
            shellInitImpl.mFreeformTaskListenerOptional.ifPresent(new ShellInitImpl$$ExternalSyntheticLambda0(shellInitImpl, 0));
            shellInitImpl.mFullscreenUnfoldController.ifPresent(ShellInitImpl$$ExternalSyntheticLambda2.INSTANCE);
            shellInitImpl.mRecentTasks.ifPresent(ShellInitImpl$$ExternalSyntheticLambda4.INSTANCE);
        } catch (RemoteException unused) {
            throw new RuntimeException("Unable to register display controller");
        }
    }
}
