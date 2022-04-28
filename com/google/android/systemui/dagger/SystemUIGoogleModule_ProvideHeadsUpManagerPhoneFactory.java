package com.google.android.systemui.dagger;

import android.content.Context;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.HeadsUpManagerLogger;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class SystemUIGoogleModule_ProvideHeadsUpManagerPhoneFactory implements Factory<HeadsUpManagerPhone> {
    public final Provider<KeyguardBypassController> bypassControllerProvider;
    public final Provider<ConfigurationController> configurationControllerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<GroupMembershipManager> groupManagerProvider;
    public final Provider<HeadsUpManagerLogger> headsUpManagerLoggerProvider;
    public final Provider<StatusBarStateController> statusBarStateControllerProvider;
    public final Provider<VisualStabilityProvider> visualStabilityProvider;

    public final Object get() {
        return new HeadsUpManagerPhone(this.contextProvider.get(), this.headsUpManagerLoggerProvider.get(), this.statusBarStateControllerProvider.get(), this.bypassControllerProvider.get(), this.groupManagerProvider.get(), this.visualStabilityProvider.get(), this.configurationControllerProvider.get());
    }

    public SystemUIGoogleModule_ProvideHeadsUpManagerPhoneFactory(Provider<Context> provider, Provider<HeadsUpManagerLogger> provider2, Provider<StatusBarStateController> provider3, Provider<KeyguardBypassController> provider4, Provider<GroupMembershipManager> provider5, Provider<VisualStabilityProvider> provider6, Provider<ConfigurationController> provider7) {
        this.contextProvider = provider;
        this.headsUpManagerLoggerProvider = provider2;
        this.statusBarStateControllerProvider = provider3;
        this.bypassControllerProvider = provider4;
        this.groupManagerProvider = provider5;
        this.visualStabilityProvider = provider6;
        this.configurationControllerProvider = provider7;
    }
}
