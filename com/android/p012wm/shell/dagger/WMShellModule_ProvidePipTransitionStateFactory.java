package com.android.p012wm.shell.dagger;

import com.android.p012wm.shell.pip.PipTransitionState;
import dagger.internal.Factory;

/* renamed from: com.android.wm.shell.dagger.WMShellModule_ProvidePipTransitionStateFactory */
public final class WMShellModule_ProvidePipTransitionStateFactory implements Factory<PipTransitionState> {

    /* renamed from: com.android.wm.shell.dagger.WMShellModule_ProvidePipTransitionStateFactory$InstanceHolder */
    public static final class InstanceHolder {
        public static final WMShellModule_ProvidePipTransitionStateFactory INSTANCE = new WMShellModule_ProvidePipTransitionStateFactory();
    }

    public final Object get() {
        return new PipTransitionState();
    }
}
