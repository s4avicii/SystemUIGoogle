package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.p012wm.shell.C1777R;
import com.android.systemui.statusbar.phone.PhoneStatusBarTransitions;
import com.android.systemui.statusbar.phone.PhoneStatusBarView;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class ShadeEventCoordinator_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider mLoggerProvider;
    public final Provider mMainExecutorProvider;

    public /* synthetic */ ShadeEventCoordinator_Factory(Provider provider, Provider provider2, int i) {
        this.$r8$classId = i;
        this.mMainExecutorProvider = provider;
        this.mLoggerProvider = provider2;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new ShadeEventCoordinator((Executor) this.mMainExecutorProvider.get(), (ShadeEventCoordinatorLogger) this.mLoggerProvider.get());
            default:
                StatusBarWindowController statusBarWindowController = (StatusBarWindowController) this.mLoggerProvider.get();
                Objects.requireNonNull(statusBarWindowController);
                return new PhoneStatusBarTransitions((PhoneStatusBarView) this.mMainExecutorProvider.get(), statusBarWindowController.mStatusBarWindowView.findViewById(C1777R.C1779id.status_bar_container));
        }
    }
}
