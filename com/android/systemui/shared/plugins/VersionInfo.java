package com.android.systemui.shared.plugins;

import android.util.ArrayMap;
import com.android.systemui.plugins.annotations.Dependencies;
import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import com.android.systemui.plugins.annotations.Requirements;
import com.android.systemui.plugins.annotations.Requires;

public final class VersionInfo {
    public Class<?> mDefault;
    public final ArrayMap<Class<?>, Version> mVersions = new ArrayMap<>();

    public static class InvalidVersionException extends RuntimeException {
        private int mActual;
        private int mExpected;
        private final boolean mTooNew;

        public InvalidVersionException(String str) {
            super(str);
            this.mTooNew = false;
        }

        public InvalidVersionException(Class<?> cls, boolean z, int i, int i2) {
            super(cls.getSimpleName() + " expected version " + i + " but had " + i2);
            this.mTooNew = z;
            this.mExpected = i;
            this.mActual = i2;
        }

        public final int getActualVersion() {
            return this.mActual;
        }

        public final int getExpectedVersion() {
            return this.mExpected;
        }

        public final boolean isTooNew() {
            return this.mTooNew;
        }
    }

    public static class Version {
        public final boolean mRequired;
        public final int mVersion;

        public Version(int i, boolean z) {
            this.mVersion = i;
            this.mRequired = z;
        }
    }

    public final void addClass(Class<?> cls, boolean z) {
        if (!this.mVersions.containsKey(cls)) {
            ProvidesInterface providesInterface = (ProvidesInterface) cls.getDeclaredAnnotation(ProvidesInterface.class);
            if (providesInterface != null) {
                this.mVersions.put(cls, new Version(providesInterface.version(), true));
            }
            Requires requires = (Requires) cls.getDeclaredAnnotation(Requires.class);
            if (requires != null) {
                this.mVersions.put(requires.target(), new Version(requires.version(), z));
            }
            Requirements requirements = (Requirements) cls.getDeclaredAnnotation(Requirements.class);
            if (requirements != null) {
                for (Requires requires2 : requirements.value()) {
                    this.mVersions.put(requires2.target(), new Version(requires2.version(), z));
                }
            }
            DependsOn dependsOn = (DependsOn) cls.getDeclaredAnnotation(DependsOn.class);
            if (dependsOn != null) {
                addClass(dependsOn.target(), true);
            }
            Dependencies dependencies = (Dependencies) cls.getDeclaredAnnotation(Dependencies.class);
            if (dependencies != null) {
                for (DependsOn target : dependencies.value()) {
                    addClass(target.target(), true);
                }
            }
        }
    }
}
