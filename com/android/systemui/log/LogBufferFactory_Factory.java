package com.android.systemui.log;

import android.content.Context;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.NotificationClickNotifier;
import com.android.systemui.statusbar.policy.FlashlightControllerImpl;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class LogBufferFactory_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider dumpManagerProvider;
    public final Provider logcatEchoTrackerProvider;

    public /* synthetic */ LogBufferFactory_Factory(Provider provider, Provider provider2, int i) {
        this.$r8$classId = i;
        this.dumpManagerProvider = provider;
        this.logcatEchoTrackerProvider = provider2;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new LogBufferFactory((DumpManager) this.dumpManagerProvider.get(), (LogcatEchoTracker) this.logcatEchoTrackerProvider.get());
            case 1:
                return new NotificationClickNotifier((IStatusBarService) this.dumpManagerProvider.get(), (Executor) this.logcatEchoTrackerProvider.get());
            default:
                return new FlashlightControllerImpl((Context) this.dumpManagerProvider.get(), (DumpManager) this.logcatEchoTrackerProvider.get());
        }
    }
}
