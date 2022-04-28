package com.android.systemui.p006qs.user;

import com.android.internal.logging.UiEventLogger;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.p006qs.tiles.UserDetailView;
import com.android.systemui.p006qs.tiles.UserDetailView_Adapter_Factory;
import com.android.systemui.p006qs.user.UserSwitchDialogController;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.user.UserSwitchDialogController_Factory */
public final class UserSwitchDialogController_Factory implements Factory<UserSwitchDialogController> {
    public final Provider<ActivityStarter> activityStarterProvider;
    public final Provider<DialogLaunchAnimator> dialogLaunchAnimatorProvider;
    public final Provider<FalsingManager> falsingManagerProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;
    public final Provider<UserDetailView.Adapter> userDetailViewAdapterProvider;

    public final Object get() {
        return new UserSwitchDialogController(this.userDetailViewAdapterProvider, this.activityStarterProvider.get(), this.falsingManagerProvider.get(), this.dialogLaunchAnimatorProvider.get(), this.uiEventLoggerProvider.get(), UserSwitchDialogController.C10561.INSTANCE);
    }

    public UserSwitchDialogController_Factory(UserDetailView_Adapter_Factory userDetailView_Adapter_Factory, Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.userDetailViewAdapterProvider = userDetailView_Adapter_Factory;
        this.activityStarterProvider = provider;
        this.falsingManagerProvider = provider2;
        this.dialogLaunchAnimatorProvider = provider3;
        this.uiEventLoggerProvider = provider4;
    }
}
