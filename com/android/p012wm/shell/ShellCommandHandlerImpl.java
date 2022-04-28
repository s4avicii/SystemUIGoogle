package com.android.p012wm.shell;

import com.android.p012wm.shell.apppairs.AppPairsController;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.hidedisplaycutout.HideDisplayCutoutController;
import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreenController;
import com.android.p012wm.shell.onehanded.OneHandedController;
import com.android.p012wm.shell.pip.Pip;
import com.android.p012wm.shell.recents.RecentTasksController;
import com.android.p012wm.shell.splitscreen.SplitScreenController;
import java.io.PrintWriter;
import java.util.Optional;

/* renamed from: com.android.wm.shell.ShellCommandHandlerImpl */
public final class ShellCommandHandlerImpl {
    public final Optional<AppPairsController> mAppPairsOptional;
    public final Optional<HideDisplayCutoutController> mHideDisplayCutout;
    public final HandlerImpl mImpl = new HandlerImpl();
    public final Optional<LegacySplitScreenController> mLegacySplitScreenOptional;
    public final ShellExecutor mMainExecutor;
    public final Optional<OneHandedController> mOneHandedOptional;
    public final Optional<Pip> mPipOptional;
    public final Optional<RecentTasksController> mRecentTasks;
    public final ShellTaskOrganizer mShellTaskOrganizer;
    public final Optional<SplitScreenController> mSplitScreenOptional;

    /* renamed from: com.android.wm.shell.ShellCommandHandlerImpl$HandlerImpl */
    public class HandlerImpl implements ShellCommandHandler {
        public final boolean handleCommand(String[] strArr, PrintWriter printWriter) {
            try {
                boolean[] zArr = new boolean[1];
                ShellCommandHandlerImpl.this.mMainExecutor.executeBlocking$1(new ShellCommandHandlerImpl$HandlerImpl$$ExternalSyntheticLambda1(this, zArr, strArr, printWriter));
                return zArr[0];
            } catch (InterruptedException e) {
                throw new RuntimeException("Failed to handle Shell command in 2s", e);
            }
        }

        public HandlerImpl() {
        }

        public final void dump(PrintWriter printWriter) {
            try {
                ShellCommandHandlerImpl.this.mMainExecutor.executeBlocking$1(new ShellCommandHandlerImpl$HandlerImpl$$ExternalSyntheticLambda0(this, printWriter));
            } catch (InterruptedException e) {
                throw new RuntimeException("Failed to dump the Shell in 2s", e);
            }
        }
    }

    public ShellCommandHandlerImpl(ShellTaskOrganizer shellTaskOrganizer, Optional<LegacySplitScreenController> optional, Optional<SplitScreenController> optional2, Optional<Pip> optional3, Optional<OneHandedController> optional4, Optional<HideDisplayCutoutController> optional5, Optional<AppPairsController> optional6, Optional<RecentTasksController> optional7, ShellExecutor shellExecutor) {
        this.mShellTaskOrganizer = shellTaskOrganizer;
        this.mRecentTasks = optional7;
        this.mLegacySplitScreenOptional = optional;
        this.mSplitScreenOptional = optional2;
        this.mPipOptional = optional3;
        this.mOneHandedOptional = optional4;
        this.mHideDisplayCutout = optional5;
        this.mAppPairsOptional = optional6;
        this.mMainExecutor = shellExecutor;
    }
}
