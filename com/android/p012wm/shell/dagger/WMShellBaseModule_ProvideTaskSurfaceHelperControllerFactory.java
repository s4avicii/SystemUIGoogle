package com.android.p012wm.shell.dagger;

import com.android.p012wm.shell.ShellTaskOrganizer;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.tasksurfacehelper.TaskSurfaceHelperController;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Provider;

/* renamed from: com.android.wm.shell.dagger.WMShellBaseModule_ProvideTaskSurfaceHelperControllerFactory */
public final class WMShellBaseModule_ProvideTaskSurfaceHelperControllerFactory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider mainExecutorProvider;
    public final Provider taskOrganizerProvider;

    public /* synthetic */ WMShellBaseModule_ProvideTaskSurfaceHelperControllerFactory(Provider provider, Provider provider2, int i) {
        this.$r8$classId = i;
        this.taskOrganizerProvider = provider;
        this.mainExecutorProvider = provider2;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                Optional ofNullable = Optional.ofNullable(new TaskSurfaceHelperController((ShellTaskOrganizer) this.taskOrganizerProvider.get(), (ShellExecutor) this.mainExecutorProvider.get()));
                Objects.requireNonNull(ofNullable, "Cannot return null from a non-@Nullable @Provides method");
                return ofNullable;
            default:
                return new NotificationInterruptLogger((LogBuffer) this.taskOrganizerProvider.get(), (LogBuffer) this.mainExecutorProvider.get());
        }
    }
}
