package com.google.android.systemui.columbus.actions;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.UserHandle;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.demomode.DemoModeController$tracker$1;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.recents.Recents;
import com.android.systemui.util.settings.GlobalSettings;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class LaunchOverview_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider contextProvider;
    public final Provider recentsProvider;
    public final Provider uiEventLoggerProvider;

    public /* synthetic */ LaunchOverview_Factory(Provider provider, Provider provider2, Provider provider3, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.recentsProvider = provider2;
        this.uiEventLoggerProvider = provider3;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new LaunchOverview((Context) this.contextProvider.get(), (Recents) this.recentsProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get());
            default:
                Context context = (Context) this.contextProvider.get();
                DumpManager dumpManager = (DumpManager) this.recentsProvider.get();
                DemoModeController demoModeController = new DemoModeController(context, dumpManager, (GlobalSettings) this.uiEventLoggerProvider.get());
                if (!demoModeController.initialized) {
                    demoModeController.initialized = true;
                    dumpManager.registerDumpable("DemoModeController", demoModeController);
                    demoModeController.tracker.startTracking();
                    DemoModeController$tracker$1 demoModeController$tracker$1 = demoModeController.tracker;
                    Objects.requireNonNull(demoModeController$tracker$1);
                    demoModeController.isInDemoMode = demoModeController$tracker$1.isInDemoMode;
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("com.android.systemui.demo");
                    context.registerReceiverAsUser(demoModeController.broadcastReceiver, UserHandle.ALL, intentFilter, "android.permission.DUMP", (Handler) null, 2);
                    return demoModeController;
                }
                throw new IllegalStateException("Already initialized");
        }
    }
}
