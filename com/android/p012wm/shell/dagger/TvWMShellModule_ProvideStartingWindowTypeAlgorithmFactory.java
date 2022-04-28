package com.android.p012wm.shell.dagger;

import com.android.p012wm.shell.startingsurface.StartingWindowTypeAlgorithm;
import com.android.p012wm.shell.startingsurface.p014tv.TvStartingWindowTypeAlgorithm;
import dagger.internal.Factory;

/* renamed from: com.android.wm.shell.dagger.TvWMShellModule_ProvideStartingWindowTypeAlgorithmFactory */
public final class TvWMShellModule_ProvideStartingWindowTypeAlgorithmFactory implements Factory<StartingWindowTypeAlgorithm> {

    /* renamed from: com.android.wm.shell.dagger.TvWMShellModule_ProvideStartingWindowTypeAlgorithmFactory$InstanceHolder */
    public static final class InstanceHolder {
        public static final TvWMShellModule_ProvideStartingWindowTypeAlgorithmFactory INSTANCE = new TvWMShellModule_ProvideStartingWindowTypeAlgorithmFactory();
    }

    public final Object get() {
        return new TvStartingWindowTypeAlgorithm();
    }
}
