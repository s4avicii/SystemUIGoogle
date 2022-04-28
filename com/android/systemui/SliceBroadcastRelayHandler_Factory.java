package com.android.systemui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.android.internal.util.NotificationMessagingUtil;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dagger.DependencyProvider;
import com.android.systemui.p006qs.QSPanel;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

public final class SliceBroadcastRelayHandler_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Object broadcastDispatcherProvider;
    public final Provider contextProvider;

    public /* synthetic */ SliceBroadcastRelayHandler_Factory(Provider provider, Provider provider2, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.broadcastDispatcherProvider = provider2;
    }

    public SliceBroadcastRelayHandler_Factory(DependencyProvider dependencyProvider, Provider provider) {
        this.$r8$classId = 2;
        this.broadcastDispatcherProvider = dependencyProvider;
        this.contextProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new SliceBroadcastRelayHandler((Context) this.contextProvider.get(), (BroadcastDispatcher) ((Provider) this.broadcastDispatcherProvider).get());
            case 1:
                View inflate = ((LayoutInflater) this.contextProvider.get()).inflate(C1777R.layout.quick_settings_security_footer, (QSPanel) ((Provider) this.broadcastDispatcherProvider).get(), false);
                Objects.requireNonNull(inflate, "Cannot return null from a non-@Nullable @Provides method");
                return inflate;
            default:
                Objects.requireNonNull((DependencyProvider) this.broadcastDispatcherProvider);
                return new NotificationMessagingUtil((Context) this.contextProvider.get());
        }
    }
}
