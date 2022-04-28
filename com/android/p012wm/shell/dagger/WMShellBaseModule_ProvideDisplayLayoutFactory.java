package com.android.p012wm.shell.dagger;

import com.android.p012wm.shell.common.DisplayLayout;
import dagger.internal.Factory;

/* renamed from: com.android.wm.shell.dagger.WMShellBaseModule_ProvideDisplayLayoutFactory */
public final class WMShellBaseModule_ProvideDisplayLayoutFactory implements Factory<DisplayLayout> {

    /* renamed from: com.android.wm.shell.dagger.WMShellBaseModule_ProvideDisplayLayoutFactory$InstanceHolder */
    public static final class InstanceHolder {
        public static final WMShellBaseModule_ProvideDisplayLayoutFactory INSTANCE = new WMShellBaseModule_ProvideDisplayLayoutFactory();
    }

    public final Object get() {
        return new DisplayLayout();
    }
}
