package com.android.systemui.dagger;

import android.os.HandlerThread;
import com.android.p012wm.shell.ShellCommandHandler;
import com.android.p012wm.shell.ShellInit;
import com.android.p012wm.shell.TaskViewFactory;
import com.android.p012wm.shell.back.BackAnimation;
import com.android.p012wm.shell.bubbles.Bubbles;
import com.android.p012wm.shell.compatui.CompatUI;
import com.android.p012wm.shell.displayareahelper.DisplayAreaHelper;
import com.android.p012wm.shell.draganddrop.DragAndDrop;
import com.android.p012wm.shell.hidedisplaycutout.HideDisplayCutout;
import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreen;
import com.android.p012wm.shell.onehanded.OneHanded;
import com.android.p012wm.shell.pip.Pip;
import com.android.p012wm.shell.recents.RecentTasks;
import com.android.p012wm.shell.splitscreen.SplitScreen;
import com.android.p012wm.shell.startingsurface.StartingSurface;
import com.android.p012wm.shell.tasksurfacehelper.TaskSurfaceHelper;
import com.android.p012wm.shell.transition.ShellTransitions;
import java.util.Optional;

public interface WMComponent {

    public interface Builder {
        WMComponent build();

        Builder setShellMainThread(HandlerThread handlerThread);
    }

    Optional<Object> getAppPairs();

    Optional<BackAnimation> getBackAnimation();

    Optional<Bubbles> getBubbles();

    Optional<CompatUI> getCompatUI();

    Optional<DisplayAreaHelper> getDisplayAreaHelper();

    Optional<DragAndDrop> getDragAndDrop();

    Optional<HideDisplayCutout> getHideDisplayCutout();

    Optional<LegacySplitScreen> getLegacySplitScreen();

    Optional<OneHanded> getOneHanded();

    Optional<Pip> getPip();

    Optional<RecentTasks> getRecentTasks();

    Optional<ShellCommandHandler> getShellCommandHandler();

    ShellInit getShellInit();

    Optional<SplitScreen> getSplitScreen();

    Optional<StartingSurface> getStartingSurface();

    Optional<TaskSurfaceHelper> getTaskSurfaceHelper();

    Optional<TaskViewFactory> getTaskViewFactory();

    ShellTransitions getTransitions();

    void init() {
        getShellInit().init();
    }
}
