package com.android.p012wm.shell.dagger;

import com.android.p012wm.shell.pip.PipTransitionState;
import dagger.internal.Factory;

/* renamed from: com.android.wm.shell.dagger.TvPipModule_ProvidePipTransitionStateFactory */
public final class TvPipModule_ProvidePipTransitionStateFactory implements Factory<PipTransitionState> {

    /* renamed from: com.android.wm.shell.dagger.TvPipModule_ProvidePipTransitionStateFactory$InstanceHolder */
    public static final class InstanceHolder {
        public static final TvPipModule_ProvidePipTransitionStateFactory INSTANCE = new TvPipModule_ProvidePipTransitionStateFactory();
    }

    public final Object get() {
        return new PipTransitionState();
    }
}
