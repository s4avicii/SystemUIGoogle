package com.android.p012wm.shell;

import com.android.p012wm.shell.splitscreen.SplitScreenController;
import com.android.p012wm.shell.splitscreen.StageCoordinator;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.ShellInitImpl$$ExternalSyntheticLambda6 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ShellInitImpl$$ExternalSyntheticLambda6 implements Consumer {
    public static final /* synthetic */ ShellInitImpl$$ExternalSyntheticLambda6 INSTANCE = new ShellInitImpl$$ExternalSyntheticLambda6(0);
    public static final /* synthetic */ ShellInitImpl$$ExternalSyntheticLambda6 INSTANCE$1 = new ShellInitImpl$$ExternalSyntheticLambda6(1);
    public static final /* synthetic */ ShellInitImpl$$ExternalSyntheticLambda6 INSTANCE$2 = new ShellInitImpl$$ExternalSyntheticLambda6(2);
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ ShellInitImpl$$ExternalSyntheticLambda6(int i) {
        this.$r8$classId = i;
    }

    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                SplitScreenController splitScreenController = (SplitScreenController) obj;
                Objects.requireNonNull(splitScreenController);
                if (splitScreenController.mStageCoordinator == null) {
                    splitScreenController.mStageCoordinator = new StageCoordinator(splitScreenController.mContext, splitScreenController.mSyncQueue, splitScreenController.mRootTDAOrganizer, splitScreenController.mTaskOrganizer, splitScreenController.mDisplayController, splitScreenController.mDisplayImeController, splitScreenController.mDisplayInsetsController, splitScreenController.mTransitions, splitScreenController.mTransactionPool, splitScreenController.mLogger, splitScreenController.mIconProvider, splitScreenController.mRecentTasksOptional, splitScreenController.mUnfoldControllerProvider);
                    return;
                }
                return;
            case 1:
                ((StatusBar) obj).collapseShade();
                return;
            default:
                int i = ZenModeControllerImpl.$r8$clinit;
                Objects.requireNonNull((ZenModeController.Callback) obj);
                return;
        }
    }
}
