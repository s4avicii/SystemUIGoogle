package com.android.systemui.statusbar.notification.stack;

import android.content.Context;
import com.android.systemui.p006qs.QuickQSPanelController_Factory;
import com.android.systemui.statusbar.notification.stack.StackScrollAlgorithm;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class AmbientState_Factory implements Factory<AmbientState> {
    public final Provider<StackScrollAlgorithm.BypassController> bypassControllerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<StackScrollAlgorithm.SectionProvider> sectionProvider;

    public final Object get() {
        return new AmbientState(this.contextProvider.get(), this.sectionProvider.get(), this.bypassControllerProvider.get());
    }

    public AmbientState_Factory(Provider provider, QuickQSPanelController_Factory quickQSPanelController_Factory, Provider provider2) {
        this.contextProvider = provider;
        this.sectionProvider = quickQSPanelController_Factory;
        this.bypassControllerProvider = provider2;
    }
}
