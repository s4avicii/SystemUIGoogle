package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.pm.LauncherApps;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.icon.IconBuilder;
import com.android.systemui.statusbar.notification.icon.IconManager;
import com.android.systemui.unfold.FoldStateLoggingProviderImpl;
import com.android.systemui.unfold.UnfoldTransitionModule;
import com.android.systemui.unfold.config.UnfoldTransitionConfig;
import com.android.systemui.unfold.updates.FoldStateProvider;
import com.android.systemui.util.time.SystemClockImpl;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Provider;

public final class DarkIconDispatcherImpl_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider commandQueueProvider;
    public final Provider contextProvider;
    public final Object dumpManagerProvider;

    public /* synthetic */ DarkIconDispatcherImpl_Factory(Provider provider, Provider provider2, Provider provider3, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.commandQueueProvider = provider2;
        this.dumpManagerProvider = provider3;
    }

    public DarkIconDispatcherImpl_Factory(UnfoldTransitionModule unfoldTransitionModule, Provider provider, Provider provider2) {
        this.$r8$classId = 2;
        this.dumpManagerProvider = unfoldTransitionModule;
        this.contextProvider = provider;
        this.commandQueueProvider = provider2;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new DarkIconDispatcherImpl((Context) this.contextProvider.get(), (CommandQueue) this.commandQueueProvider.get(), (DumpManager) ((Provider) this.dumpManagerProvider).get());
            case 1:
                return new IconManager((CommonNotifCollection) this.contextProvider.get(), (LauncherApps) this.commandQueueProvider.get(), (IconBuilder) ((Provider) this.dumpManagerProvider).get());
            default:
                Lazy lazy = DoubleCheck.lazy(this.commandQueueProvider);
                Objects.requireNonNull((UnfoldTransitionModule) this.dumpManagerProvider);
                if (((UnfoldTransitionConfig) this.contextProvider.get()).isHingeAngleEnabled()) {
                    return Optional.of(new FoldStateLoggingProviderImpl((FoldStateProvider) lazy.get(), new SystemClockImpl()));
                }
                return Optional.empty();
        }
    }
}
