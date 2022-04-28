package com.google.android.systemui.dagger;

import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.google.android.systemui.smartspace.BcSmartspaceDataProvider;
import dagger.internal.Factory;

public final class SystemUIGoogleModule_ProvideBcSmartspaceDataPluginFactory implements Factory<BcSmartspaceDataPlugin> {

    public static final class InstanceHolder {
        public static final SystemUIGoogleModule_ProvideBcSmartspaceDataPluginFactory INSTANCE = new SystemUIGoogleModule_ProvideBcSmartspaceDataPluginFactory();
    }

    public final Object get() {
        return new BcSmartspaceDataProvider();
    }
}
