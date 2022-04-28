package com.android.systemui.util.p010io;

import dagger.internal.Factory;

/* renamed from: com.android.systemui.util.io.Files_Factory */
public final class Files_Factory implements Factory<Files> {

    /* renamed from: com.android.systemui.util.io.Files_Factory$InstanceHolder */
    public static final class InstanceHolder {
        public static final Files_Factory INSTANCE = new Files_Factory();
    }

    public final Object get() {
        return new Files();
    }
}
