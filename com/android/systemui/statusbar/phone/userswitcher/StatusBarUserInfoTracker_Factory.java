package com.android.systemui.statusbar.phone.userswitcher;

import android.os.UserManager;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.policy.UserInfoController;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class StatusBarUserInfoTracker_Factory implements Factory<StatusBarUserInfoTracker> {
    public final Provider<Executor> backgroundExecutorProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<Executor> mainExecutorProvider;
    public final Provider<UserInfoController> userInfoControllerProvider;
    public final Provider<UserManager> userManagerProvider;

    public final Object get() {
        return new StatusBarUserInfoTracker(this.userInfoControllerProvider.get(), this.userManagerProvider.get(), this.dumpManagerProvider.get(), this.mainExecutorProvider.get(), this.backgroundExecutorProvider.get());
    }

    public StatusBarUserInfoTracker_Factory(Provider<UserInfoController> provider, Provider<UserManager> provider2, Provider<DumpManager> provider3, Provider<Executor> provider4, Provider<Executor> provider5) {
        this.userInfoControllerProvider = provider;
        this.userManagerProvider = provider2;
        this.dumpManagerProvider = provider3;
        this.mainExecutorProvider = provider4;
        this.backgroundExecutorProvider = provider5;
    }
}
