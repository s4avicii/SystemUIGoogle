package com.android.systemui.util.leak;

import android.content.Context;
import android.os.UserManager;
import android.view.LayoutInflater;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.phone.ShadeController;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.user.UserSwitcherActivity;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.MessageRouter;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class GarbageMonitor_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider contextProvider;
    public final Provider delayableExecutorProvider;
    public final Provider dumpManagerProvider;
    public final Provider leakDetectorProvider;
    public final Provider leakReporterProvider;
    public final Provider messageRouterProvider;

    public /* synthetic */ GarbageMonitor_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.delayableExecutorProvider = provider2;
        this.messageRouterProvider = provider3;
        this.leakDetectorProvider = provider4;
        this.leakReporterProvider = provider5;
        this.dumpManagerProvider = provider6;
    }

    public static GarbageMonitor_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        return new GarbageMonitor_Factory(provider, provider2, provider3, provider4, provider5, provider6, 1);
    }

    public static GarbageMonitor_Factory create$1(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        return new GarbageMonitor_Factory(provider, provider2, provider3, provider4, provider5, provider6, 0);
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new GarbageMonitor((Context) this.contextProvider.get(), (DelayableExecutor) this.delayableExecutorProvider.get(), (MessageRouter) this.messageRouterProvider.get(), (LeakDetector) this.leakDetectorProvider.get(), (LeakReporter) this.leakReporterProvider.get(), (DumpManager) this.dumpManagerProvider.get());
            default:
                return new UserSwitcherActivity((UserSwitcherController) this.contextProvider.get(), (BroadcastDispatcher) this.delayableExecutorProvider.get(), (LayoutInflater) this.messageRouterProvider.get(), (FalsingManager) this.leakDetectorProvider.get(), (UserManager) this.leakReporterProvider.get(), (ShadeController) this.dumpManagerProvider.get());
        }
    }
}
