package com.android.systemui.shortcut;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.statusbar.notification.NotificationSectionsFeatureManager;
import com.android.systemui.unfold.UnfoldTransitionModule;
import com.android.systemui.unfold.config.ResourceUnfoldTransitionConfig;
import com.android.systemui.util.DeviceConfigProxy;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class ShortcutKeyDispatcher_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider contextProvider;
    public final Object splitScreenOptionalProvider;

    public /* synthetic */ ShortcutKeyDispatcher_Factory(Provider provider, Provider provider2, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.splitScreenOptionalProvider = provider2;
    }

    public ShortcutKeyDispatcher_Factory(UnfoldTransitionModule unfoldTransitionModule, Provider provider) {
        this.$r8$classId = 4;
        this.splitScreenOptionalProvider = unfoldTransitionModule;
        this.contextProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new ShortcutKeyDispatcher((Context) this.contextProvider.get(), (Optional) ((Provider) this.splitScreenOptionalProvider).get());
            case 1:
                return new CommandRegistry((Context) this.contextProvider.get(), (Executor) ((Provider) this.splitScreenOptionalProvider).get());
            case 2:
                return new NotificationSectionsFeatureManager((DeviceConfigProxy) this.contextProvider.get(), (Context) ((Provider) this.splitScreenOptionalProvider).get());
            case 3:
                PendingIntent activity = PendingIntent.getActivity((Context) this.contextProvider.get(), 0, (Intent) ((Provider) this.splitScreenOptionalProvider).get(), 67108864);
                Objects.requireNonNull(activity, "Cannot return null from a non-@Nullable @Provides method");
                return activity;
            default:
                Objects.requireNonNull((UnfoldTransitionModule) this.splitScreenOptionalProvider);
                return new ResourceUnfoldTransitionConfig((Context) this.contextProvider.get());
        }
    }
}
