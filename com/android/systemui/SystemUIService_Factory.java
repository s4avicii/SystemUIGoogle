package com.android.systemui;

import android.os.Handler;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.management.ControlsFavoritingActivity;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.p004ui.ControlsUiController;
import com.android.systemui.dump.DumpHandler;
import com.android.systemui.dump.LogBufferFreezer;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.BatteryStateNotifier;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.condition.Monitor;
import com.google.android.systemui.communal.dock.callbacks.TimeoutToUserZeroCallback;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class SystemUIService_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider batteryStateNotifierProvider;
    public final Provider broadcastDispatcherProvider;
    public final Provider dumpHandlerProvider;
    public final Provider logBufferFreezerProvider;
    public final Provider mainHandlerProvider;

    public /* synthetic */ SystemUIService_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, int i) {
        this.$r8$classId = i;
        this.mainHandlerProvider = provider;
        this.dumpHandlerProvider = provider2;
        this.broadcastDispatcherProvider = provider3;
        this.logBufferFreezerProvider = provider4;
        this.batteryStateNotifierProvider = provider5;
    }

    public static SystemUIService_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new SystemUIService_Factory(provider, provider2, provider3, provider4, provider5, 0);
    }

    public static SystemUIService_Factory create$1(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new SystemUIService_Factory(provider, provider2, provider3, provider4, provider5, 1);
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new SystemUIService((Handler) this.mainHandlerProvider.get(), (DumpHandler) this.dumpHandlerProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (LogBufferFreezer) this.logBufferFreezerProvider.get(), (BatteryStateNotifier) this.batteryStateNotifierProvider.get());
            case 1:
                return new ControlsFavoritingActivity((Executor) this.mainHandlerProvider.get(), (ControlsControllerImpl) this.dumpHandlerProvider.get(), (ControlsListingController) this.broadcastDispatcherProvider.get(), (BroadcastDispatcher) this.logBufferFreezerProvider.get(), (ControlsUiController) this.batteryStateNotifierProvider.get());
            default:
                return new TimeoutToUserZeroCallback((DelayableExecutor) this.mainHandlerProvider.get(), (Monitor) this.dumpHandlerProvider.get(), this.broadcastDispatcherProvider, (UserSwitcherController) this.logBufferFreezerProvider.get(), (UserTracker) this.batteryStateNotifierProvider.get());
        }
    }
}
