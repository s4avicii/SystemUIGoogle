package com.android.p012wm.shell.dagger;

import com.android.p012wm.shell.pip.PipSnapAlgorithm;
import dagger.internal.Factory;

/* renamed from: com.android.wm.shell.dagger.WMShellModule_ProvidePipSnapAlgorithmFactory */
public final class WMShellModule_ProvidePipSnapAlgorithmFactory implements Factory<PipSnapAlgorithm> {

    /* renamed from: com.android.wm.shell.dagger.WMShellModule_ProvidePipSnapAlgorithmFactory$InstanceHolder */
    public static final class InstanceHolder {
        public static final WMShellModule_ProvidePipSnapAlgorithmFactory INSTANCE = new WMShellModule_ProvidePipSnapAlgorithmFactory();
    }

    public final Object get() {
        return new PipSnapAlgorithm();
    }
}
