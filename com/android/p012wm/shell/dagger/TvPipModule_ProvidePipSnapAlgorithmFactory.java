package com.android.p012wm.shell.dagger;

import com.android.p012wm.shell.pip.PipSnapAlgorithm;
import dagger.internal.Factory;

/* renamed from: com.android.wm.shell.dagger.TvPipModule_ProvidePipSnapAlgorithmFactory */
public final class TvPipModule_ProvidePipSnapAlgorithmFactory implements Factory<PipSnapAlgorithm> {

    /* renamed from: com.android.wm.shell.dagger.TvPipModule_ProvidePipSnapAlgorithmFactory$InstanceHolder */
    public static final class InstanceHolder {
        public static final TvPipModule_ProvidePipSnapAlgorithmFactory INSTANCE = new TvPipModule_ProvidePipSnapAlgorithmFactory();
    }

    public final Object get() {
        return new PipSnapAlgorithm();
    }
}
