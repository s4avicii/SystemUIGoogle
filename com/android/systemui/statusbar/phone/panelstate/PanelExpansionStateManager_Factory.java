package com.android.systemui.statusbar.phone.panelstate;

import dagger.internal.Factory;

public final class PanelExpansionStateManager_Factory implements Factory<PanelExpansionStateManager> {

    public static final class InstanceHolder {
        public static final PanelExpansionStateManager_Factory INSTANCE = new PanelExpansionStateManager_Factory();
    }

    public final Object get() {
        return new PanelExpansionStateManager();
    }
}
