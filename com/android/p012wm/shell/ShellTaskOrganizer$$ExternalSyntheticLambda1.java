package com.android.p012wm.shell;

import android.app.ActivityManager;
import android.graphics.Rect;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.p012wm.shell.pip.phone.PipMotionHelper;
import com.android.p012wm.shell.recents.RecentTasksController;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.ShellTaskOrganizer$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ShellTaskOrganizer$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ShellTaskOrganizer$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                RecentTasksController recentTasksController = (RecentTasksController) obj;
                int i = ShellTaskOrganizer.$r8$clinit;
                Objects.requireNonNull(recentTasksController);
                recentTasksController.removeSplitPair(((ActivityManager.RunningTaskInfo) this.f$0).taskId);
                recentTasksController.notifyRecentTasksChanged();
                return;
            case 1:
                BiometricUnlockController biometricUnlockController = (BiometricUnlockController) this.f$0;
                UiEventLoggerImpl uiEventLoggerImpl = BiometricUnlockController.UI_EVENT_LOGGER;
                Objects.requireNonNull(biometricUnlockController);
                UiEventLoggerImpl uiEventLoggerImpl2 = BiometricUnlockController.UI_EVENT_LOGGER;
                SessionTracker sessionTracker = biometricUnlockController.mSessionTracker;
                Objects.requireNonNull(sessionTracker);
                uiEventLoggerImpl2.log((BiometricUnlockController.BiometricUiEvent) obj, (InstanceId) sessionTracker.mSessionToInstanceId.getOrDefault(1, (Object) null));
                return;
            default:
                PipMotionHelper pipMotionHelper = (PipMotionHelper) this.f$0;
                Rect rect = (Rect) obj;
                Objects.requireNonNull(pipMotionHelper);
                pipMotionHelper.mMenuController.updateMenuLayout();
                return;
        }
    }
}
