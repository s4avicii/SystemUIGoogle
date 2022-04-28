package com.google.android.systemui.assist.uihints;

import com.android.systemui.statusbar.phone.StatusBar;
import com.google.android.systemui.assist.uihints.NgaMessageHandler;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class AssistantUIHintsModule_ProvideActivityStarterFactory implements Factory<NgaMessageHandler.StartActivityInfoListener> {
    public final Provider<StatusBar> statusBarLazyProvider;

    public final Object get() {
        return new AssistantUIHintsModule$1(DoubleCheck.lazy(this.statusBarLazyProvider));
    }

    public AssistantUIHintsModule_ProvideActivityStarterFactory(Provider<StatusBar> provider) {
        this.statusBarLazyProvider = provider;
    }
}
