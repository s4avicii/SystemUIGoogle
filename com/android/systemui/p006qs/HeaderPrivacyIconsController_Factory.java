package com.android.systemui.p006qs;

import android.permission.PermissionManager;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.appops.AppOpsController;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.privacy.OngoingPrivacyChip;
import com.android.systemui.privacy.PrivacyDialogController;
import com.android.systemui.privacy.PrivacyItemController;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.statusbar.phone.StatusIconContainer;
import com.android.systemui.util.DeviceConfigProxy;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.HeaderPrivacyIconsController_Factory */
public final class HeaderPrivacyIconsController_Factory implements Factory<HeaderPrivacyIconsController> {
    public final Provider<ActivityStarter> activityStarterProvider;
    public final Provider<AppOpsController> appOpsControllerProvider;
    public final Provider<Executor> backgroundExecutorProvider;
    public final Provider<DeviceConfigProxy> deviceConfigProxyProvider;
    public final Provider<StatusIconContainer> iconContainerProvider;
    public final Provider<PermissionManager> permissionManagerProvider;
    public final Provider<OngoingPrivacyChip> privacyChipProvider;
    public final Provider<PrivacyDialogController> privacyDialogControllerProvider;
    public final Provider<PrivacyItemController> privacyItemControllerProvider;
    public final Provider<PrivacyLogger> privacyLoggerProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;
    public final Provider<Executor> uiExecutorProvider;

    public static HeaderPrivacyIconsController_Factory create(Provider<PrivacyItemController> provider, Provider<UiEventLogger> provider2, Provider<OngoingPrivacyChip> provider3, Provider<PrivacyDialogController> provider4, Provider<PrivacyLogger> provider5, Provider<StatusIconContainer> provider6, Provider<PermissionManager> provider7, Provider<Executor> provider8, Provider<Executor> provider9, Provider<ActivityStarter> provider10, Provider<AppOpsController> provider11, Provider<DeviceConfigProxy> provider12) {
        return new HeaderPrivacyIconsController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12);
    }

    public final Object get() {
        return new HeaderPrivacyIconsController(this.privacyItemControllerProvider.get(), this.uiEventLoggerProvider.get(), this.privacyChipProvider.get(), this.privacyDialogControllerProvider.get(), this.privacyLoggerProvider.get(), this.iconContainerProvider.get(), this.permissionManagerProvider.get(), this.backgroundExecutorProvider.get(), this.uiExecutorProvider.get(), this.activityStarterProvider.get(), this.appOpsControllerProvider.get(), this.deviceConfigProxyProvider.get());
    }

    public HeaderPrivacyIconsController_Factory(Provider<PrivacyItemController> provider, Provider<UiEventLogger> provider2, Provider<OngoingPrivacyChip> provider3, Provider<PrivacyDialogController> provider4, Provider<PrivacyLogger> provider5, Provider<StatusIconContainer> provider6, Provider<PermissionManager> provider7, Provider<Executor> provider8, Provider<Executor> provider9, Provider<ActivityStarter> provider10, Provider<AppOpsController> provider11, Provider<DeviceConfigProxy> provider12) {
        this.privacyItemControllerProvider = provider;
        this.uiEventLoggerProvider = provider2;
        this.privacyChipProvider = provider3;
        this.privacyDialogControllerProvider = provider4;
        this.privacyLoggerProvider = provider5;
        this.iconContainerProvider = provider6;
        this.permissionManagerProvider = provider7;
        this.backgroundExecutorProvider = provider8;
        this.uiExecutorProvider = provider9;
        this.activityStarterProvider = provider10;
        this.appOpsControllerProvider = provider11;
        this.deviceConfigProxyProvider = provider12;
    }
}
