package com.google.android.systemui.assist.uihints;

import com.google.android.systemui.assist.uihints.NgaMessageHandler;
import dagger.internal.Factory;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Provider;

public final class AssistantUIHintsModule_ProvideConfigInfoListenersFactory implements Factory<Set<NgaMessageHandler.ConfigInfoListener>> {
    public final Provider<AssistantPresenceHandler> assistantPresenceHandlerProvider;
    public final Provider<ColorChangeHandler> colorChangeHandlerProvider;
    public final Provider<ConfigurationHandler> configurationHandlerProvider;
    public final Provider<KeyboardMonitor> keyboardMonitorProvider;
    public final Provider<TaskStackNotifier> taskStackNotifierProvider;
    public final Provider<TouchInsideHandler> touchInsideHandlerProvider;
    public final Provider<TouchOutsideHandler> touchOutsideHandlerProvider;

    public final Object get() {
        return new HashSet(Arrays.asList(new NgaMessageHandler.ConfigInfoListener[]{this.assistantPresenceHandlerProvider.get(), this.touchInsideHandlerProvider.get(), this.touchOutsideHandlerProvider.get(), this.taskStackNotifierProvider.get(), this.keyboardMonitorProvider.get(), this.colorChangeHandlerProvider.get(), this.configurationHandlerProvider.get()}));
    }

    public AssistantUIHintsModule_ProvideConfigInfoListenersFactory(Provider<AssistantPresenceHandler> provider, Provider<TouchInsideHandler> provider2, Provider<TouchOutsideHandler> provider3, Provider<TaskStackNotifier> provider4, Provider<KeyboardMonitor> provider5, Provider<ColorChangeHandler> provider6, Provider<ConfigurationHandler> provider7) {
        this.assistantPresenceHandlerProvider = provider;
        this.touchInsideHandlerProvider = provider2;
        this.touchOutsideHandlerProvider = provider3;
        this.taskStackNotifierProvider = provider4;
        this.keyboardMonitorProvider = provider5;
        this.colorChangeHandlerProvider = provider6;
        this.configurationHandlerProvider = provider7;
    }
}
