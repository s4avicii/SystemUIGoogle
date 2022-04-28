package com.google.android.systemui.columbus;

import dagger.internal.Factory;
import java.util.Objects;
import java.util.Set;
import kotlin.collections.SetsKt__SetsKt;

public final class ColumbusModule_ProvideBlockingSystemKeysFactory implements Factory<Set<Integer>> {

    public static final class InstanceHolder {
        public static final ColumbusModule_ProvideBlockingSystemKeysFactory INSTANCE = new ColumbusModule_ProvideBlockingSystemKeysFactory();
    }

    public final Object get() {
        Set of = SetsKt__SetsKt.setOf(24, 25, 26);
        Objects.requireNonNull(of, "Cannot return null from a non-@Nullable @Provides method");
        return of;
    }
}
