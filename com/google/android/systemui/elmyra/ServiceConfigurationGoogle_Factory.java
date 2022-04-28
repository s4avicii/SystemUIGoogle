package com.google.android.systemui.elmyra;

import android.content.Context;
import com.google.android.systemui.elmyra.actions.CameraAction;
import com.google.android.systemui.elmyra.actions.LaunchOpa;
import com.google.android.systemui.elmyra.actions.SettingsAction;
import com.google.android.systemui.elmyra.actions.SetupWizardAction;
import com.google.android.systemui.elmyra.actions.SilenceCall;
import com.google.android.systemui.elmyra.actions.UnpinNotifications;
import com.google.android.systemui.elmyra.feedback.AssistInvocationEffect;
import com.google.android.systemui.elmyra.feedback.SquishyNavigationButtons;
import com.google.android.systemui.elmyra.gates.TelephonyActivity;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class ServiceConfigurationGoogle_Factory implements Factory<ServiceConfigurationGoogle> {
    public final Provider<AssistInvocationEffect> assistInvocationEffectProvider;
    public final Provider<CameraAction.Builder> cameraActionBuilderProvider;
    public final Provider<Context> contextProvider;
    public final Provider<LaunchOpa.Builder> launchOpaBuilderProvider;
    public final Provider<SettingsAction.Builder> settingsActionBuilderProvider;
    public final Provider<SetupWizardAction.Builder> setupWizardActionBuilderProvider;
    public final Provider<SilenceCall> silenceCallProvider;
    public final Provider<SquishyNavigationButtons> squishyNavigationButtonsProvider;
    public final Provider<TelephonyActivity> telephonyActivityProvider;
    public final Provider<UnpinNotifications> unpinNotificationsProvider;

    public final Object get() {
        return new ServiceConfigurationGoogle(this.contextProvider.get(), this.assistInvocationEffectProvider.get(), this.launchOpaBuilderProvider.get(), this.settingsActionBuilderProvider.get(), this.cameraActionBuilderProvider.get(), this.setupWizardActionBuilderProvider.get(), this.squishyNavigationButtonsProvider.get(), this.unpinNotificationsProvider.get(), this.silenceCallProvider.get(), this.telephonyActivityProvider.get());
    }

    public ServiceConfigurationGoogle_Factory(Provider<Context> provider, Provider<AssistInvocationEffect> provider2, Provider<LaunchOpa.Builder> provider3, Provider<SettingsAction.Builder> provider4, Provider<CameraAction.Builder> provider5, Provider<SetupWizardAction.Builder> provider6, Provider<SquishyNavigationButtons> provider7, Provider<UnpinNotifications> provider8, Provider<SilenceCall> provider9, Provider<TelephonyActivity> provider10) {
        this.contextProvider = provider;
        this.assistInvocationEffectProvider = provider2;
        this.launchOpaBuilderProvider = provider3;
        this.settingsActionBuilderProvider = provider4;
        this.cameraActionBuilderProvider = provider5;
        this.setupWizardActionBuilderProvider = provider6;
        this.squishyNavigationButtonsProvider = provider7;
        this.unpinNotificationsProvider = provider8;
        this.silenceCallProvider = provider9;
        this.telephonyActivityProvider = provider10;
    }
}
