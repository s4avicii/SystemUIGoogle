package com.android.keyguard.clock;

import android.content.ContentResolver;
import android.content.Context;
import android.view.LayoutInflater;
import com.android.keyguard.clock.SettingsWrapper;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.dock.DockManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.p006qs.user.UserSwitchDialogController;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.CurrentUserObservable;
import com.android.systemui.shared.plugins.PluginManager;
import com.android.systemui.statusbar.phone.userswitcher.StatusBarUserInfoTracker;
import com.android.systemui.statusbar.phone.userswitcher.StatusBarUserSwitcherContainer;
import com.android.systemui.statusbar.phone.userswitcher.StatusBarUserSwitcherControllerImpl;
import com.android.systemui.statusbar.phone.userswitcher.StatusBarUserSwitcherFeatureController;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class ClockManager_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider broadcastDispatcherProvider;
    public final Provider colorExtractorProvider;
    public final Provider contextProvider;
    public final Provider dockManagerProvider;
    public final Provider layoutInflaterProvider;
    public final Provider pluginManagerProvider;

    public /* synthetic */ ClockManager_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.layoutInflaterProvider = provider2;
        this.pluginManagerProvider = provider3;
        this.colorExtractorProvider = provider4;
        this.dockManagerProvider = provider5;
        this.broadcastDispatcherProvider = provider6;
    }

    public static ClockManager_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        return new ClockManager_Factory(provider, provider2, provider3, provider4, provider5, provider6, 0);
    }

    public static ClockManager_Factory create$1(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        return new ClockManager_Factory(provider, provider2, provider3, provider4, provider5, provider6, 1);
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                Context context = (Context) this.contextProvider.get();
                ContentResolver contentResolver = context.getContentResolver();
                CurrentUserObservable currentUserObservable = new CurrentUserObservable((BroadcastDispatcher) this.broadcastDispatcherProvider.get());
                ContentResolver contentResolver2 = context.getContentResolver();
                return new ClockManager(context, (LayoutInflater) this.layoutInflaterProvider.get(), (PluginManager) this.pluginManagerProvider.get(), (SysuiColorExtractor) this.colorExtractorProvider.get(), contentResolver, currentUserObservable, new SettingsWrapper(contentResolver2, new SettingsWrapper.Migrator(contentResolver2)), (DockManager) this.dockManagerProvider.get());
            default:
                return new StatusBarUserSwitcherControllerImpl((StatusBarUserSwitcherContainer) this.contextProvider.get(), (StatusBarUserInfoTracker) this.layoutInflaterProvider.get(), (StatusBarUserSwitcherFeatureController) this.pluginManagerProvider.get(), (UserSwitchDialogController) this.colorExtractorProvider.get(), (FeatureFlags) this.dockManagerProvider.get(), (ActivityStarter) this.broadcastDispatcherProvider.get());
        }
    }
}
