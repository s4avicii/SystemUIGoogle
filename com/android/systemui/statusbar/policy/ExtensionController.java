package com.android.systemui.statusbar.policy;

import com.android.systemui.plugins.IntentButtonProvider;
import com.android.systemui.statusbar.policy.ExtensionControllerImpl;

public interface ExtensionController {

    public interface Extension<T> {
    }

    public interface PluginConverter<T, P> {
        IntentButtonProvider.IntentButton getInterfaceFromPlugin(Object obj);
    }

    public interface TunerFactory<T> {
    }

    ExtensionControllerImpl.ExtensionBuilder newExtension();
}
