package com.android.p012wm.shell.dagger;

import android.view.IWindowManager;
import com.android.p012wm.shell.common.DisplayController;
import com.android.p012wm.shell.common.SystemWindows;
import com.android.systemui.dagger.DependencyProvider;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier;
import com.android.systemui.statusbar.policy.DataSaverControllerImpl;
import dagger.internal.Factory;
import java.util.Objects;
import javax.inject.Provider;

/* renamed from: com.android.wm.shell.dagger.WMShellBaseModule_ProvideSystemWindowsFactory */
public final class WMShellBaseModule_ProvideSystemWindowsFactory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider displayControllerProvider;
    public final Object wmServiceProvider;

    public /* synthetic */ WMShellBaseModule_ProvideSystemWindowsFactory(Provider provider, Provider provider2, int i) {
        this.$r8$classId = i;
        this.displayControllerProvider = provider;
        this.wmServiceProvider = provider2;
    }

    public WMShellBaseModule_ProvideSystemWindowsFactory(DependencyProvider dependencyProvider, Provider provider) {
        this.$r8$classId = 2;
        this.wmServiceProvider = dependencyProvider;
        this.displayControllerProvider = provider;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new SystemWindows((DisplayController) this.displayControllerProvider.get(), (IWindowManager) ((Provider) this.wmServiceProvider).get());
            case 1:
                return new ConversationCoordinator((PeopleNotificationIdentifier) this.displayControllerProvider.get(), (NodeController) ((Provider) this.wmServiceProvider).get());
            default:
                Objects.requireNonNull((DependencyProvider) this.wmServiceProvider);
                DataSaverControllerImpl dataSaverController = ((NetworkController) this.displayControllerProvider.get()).getDataSaverController();
                Objects.requireNonNull(dataSaverController, "Cannot return null from a non-@Nullable @Provides method");
                return dataSaverController;
        }
    }
}
