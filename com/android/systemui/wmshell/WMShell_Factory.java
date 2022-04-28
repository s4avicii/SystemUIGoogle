package com.android.systemui.wmshell;

import android.content.Context;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.p012wm.shell.ShellCommandHandler;
import com.android.p012wm.shell.compatui.CompatUI;
import com.android.p012wm.shell.draganddrop.DragAndDrop;
import com.android.p012wm.shell.hidedisplaycutout.HideDisplayCutout;
import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreen;
import com.android.p012wm.shell.onehanded.OneHanded;
import com.android.p012wm.shell.pip.Pip;
import com.android.p012wm.shell.splitscreen.SplitScreen;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.UserInfoController;
import com.android.systemui.tracing.ProtoTracer;
import dagger.internal.Factory;
import java.util.Optional;
import java.util.concurrent.Executor;
import javax.inject.Provider;

public final class WMShell_Factory implements Factory<WMShell> {
    public final Provider<CommandQueue> commandQueueProvider;
    public final Provider<ConfigurationController> configurationControllerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<Optional<DragAndDrop>> dragAndDropOptionalProvider;
    public final Provider<Optional<HideDisplayCutout>> hideDisplayCutoutOptionalProvider;
    public final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;
    public final Provider<Optional<LegacySplitScreen>> legacySplitScreenOptionalProvider;
    public final Provider<NavigationModeController> navigationModeControllerProvider;
    public final Provider<Optional<OneHanded>> oneHandedOptionalProvider;
    public final Provider<Optional<Pip>> pipOptionalProvider;
    public final Provider<ProtoTracer> protoTracerProvider;
    public final Provider<ScreenLifecycle> screenLifecycleProvider;
    public final Provider<Optional<ShellCommandHandler>> shellCommandHandlerProvider;
    public final Provider<Optional<CompatUI>> sizeCompatUIOptionalProvider;
    public final Provider<Optional<SplitScreen>> splitScreenOptionalProvider;
    public final Provider<Executor> sysUiMainExecutorProvider;
    public final Provider<SysUiState> sysUiStateProvider;
    public final Provider<UserInfoController> userInfoControllerProvider;
    public final Provider<WakefulnessLifecycle> wakefulnessLifecycleProvider;

    public WMShell_Factory(Provider<Context> provider, Provider<Optional<Pip>> provider2, Provider<Optional<LegacySplitScreen>> provider3, Provider<Optional<SplitScreen>> provider4, Provider<Optional<OneHanded>> provider5, Provider<Optional<HideDisplayCutout>> provider6, Provider<Optional<ShellCommandHandler>> provider7, Provider<Optional<CompatUI>> provider8, Provider<Optional<DragAndDrop>> provider9, Provider<CommandQueue> provider10, Provider<ConfigurationController> provider11, Provider<KeyguardUpdateMonitor> provider12, Provider<NavigationModeController> provider13, Provider<ScreenLifecycle> provider14, Provider<SysUiState> provider15, Provider<ProtoTracer> provider16, Provider<WakefulnessLifecycle> provider17, Provider<UserInfoController> provider18, Provider<Executor> provider19) {
        this.contextProvider = provider;
        this.pipOptionalProvider = provider2;
        this.legacySplitScreenOptionalProvider = provider3;
        this.splitScreenOptionalProvider = provider4;
        this.oneHandedOptionalProvider = provider5;
        this.hideDisplayCutoutOptionalProvider = provider6;
        this.shellCommandHandlerProvider = provider7;
        this.sizeCompatUIOptionalProvider = provider8;
        this.dragAndDropOptionalProvider = provider9;
        this.commandQueueProvider = provider10;
        this.configurationControllerProvider = provider11;
        this.keyguardUpdateMonitorProvider = provider12;
        this.navigationModeControllerProvider = provider13;
        this.screenLifecycleProvider = provider14;
        this.sysUiStateProvider = provider15;
        this.protoTracerProvider = provider16;
        this.wakefulnessLifecycleProvider = provider17;
        this.userInfoControllerProvider = provider18;
        this.sysUiMainExecutorProvider = provider19;
    }

    public static WMShell_Factory create(Provider<Context> provider, Provider<Optional<Pip>> provider2, Provider<Optional<LegacySplitScreen>> provider3, Provider<Optional<SplitScreen>> provider4, Provider<Optional<OneHanded>> provider5, Provider<Optional<HideDisplayCutout>> provider6, Provider<Optional<ShellCommandHandler>> provider7, Provider<Optional<CompatUI>> provider8, Provider<Optional<DragAndDrop>> provider9, Provider<CommandQueue> provider10, Provider<ConfigurationController> provider11, Provider<KeyguardUpdateMonitor> provider12, Provider<NavigationModeController> provider13, Provider<ScreenLifecycle> provider14, Provider<SysUiState> provider15, Provider<ProtoTracer> provider16, Provider<WakefulnessLifecycle> provider17, Provider<UserInfoController> provider18, Provider<Executor> provider19) {
        return new WMShell_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17, provider18, provider19);
    }

    public final Object get() {
        NavigationModeController navigationModeController = this.navigationModeControllerProvider.get();
        return new WMShell(this.contextProvider.get(), this.pipOptionalProvider.get(), this.legacySplitScreenOptionalProvider.get(), this.splitScreenOptionalProvider.get(), this.oneHandedOptionalProvider.get(), this.hideDisplayCutoutOptionalProvider.get(), this.shellCommandHandlerProvider.get(), this.sizeCompatUIOptionalProvider.get(), this.dragAndDropOptionalProvider.get(), this.commandQueueProvider.get(), this.configurationControllerProvider.get(), this.keyguardUpdateMonitorProvider.get(), this.screenLifecycleProvider.get(), this.sysUiStateProvider.get(), this.protoTracerProvider.get(), this.wakefulnessLifecycleProvider.get(), this.userInfoControllerProvider.get(), this.sysUiMainExecutorProvider.get());
    }
}
