package com.google.android.systemui.assist;

import android.content.Context;
import android.os.UserManager;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.people.widget.LaunchConversationActivity;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import dagger.internal.Factory;
import java.util.Optional;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class OpaEnabledReceiver_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider bgExecutorProvider;
    public final Provider broadcastDispatcherProvider;
    public final Provider contextProvider;
    public final Provider fgExecutorProvider;
    public final Provider opaEnabledSettingsProvider;

    public /* synthetic */ OpaEnabledReceiver_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.broadcastDispatcherProvider = provider2;
        this.fgExecutorProvider = provider3;
        this.bgExecutorProvider = provider4;
        this.opaEnabledSettingsProvider = provider5;
    }

    public static OpaEnabledReceiver_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new OpaEnabledReceiver_Factory(provider, provider2, provider3, provider4, provider5, 1);
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new OpaEnabledReceiver((Context) this.contextProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (Executor) this.fgExecutorProvider.get(), (Executor) this.bgExecutorProvider.get(), (OpaEnabledSettings) this.opaEnabledSettingsProvider.get());
            default:
                return new LaunchConversationActivity((NotificationVisibilityProvider) this.contextProvider.get(), (CommonNotifCollection) this.broadcastDispatcherProvider.get(), (Optional) this.fgExecutorProvider.get(), (UserManager) this.bgExecutorProvider.get(), (CommandQueue) this.opaEnabledSettingsProvider.get());
        }
    }
}
