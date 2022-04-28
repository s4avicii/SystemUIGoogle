package com.android.systemui.util.leak;

import java.util.Objects;
import java.util.WeakHashMap;

public final class TrackedObjects {
    public final WeakHashMap<Class<?>, TrackedClass<?>> mTrackedClasses = new WeakHashMap<>();
    public final TrackedCollections mTrackedCollections;

    public static class TrackedClass<T> extends AbstractCollection<T> {
        public final WeakIdentityHashMap<T, Void> instances = new WeakIdentityHashMap<>();

        public final boolean isEmpty() {
            WeakIdentityHashMap<T, Void> weakIdentityHashMap = this.instances;
            Objects.requireNonNull(weakIdentityHashMap);
            weakIdentityHashMap.cleanUp();
            return weakIdentityHashMap.mMap.isEmpty();
        }

        public final int size() {
            WeakIdentityHashMap<T, Void> weakIdentityHashMap = this.instances;
            Objects.requireNonNull(weakIdentityHashMap);
            weakIdentityHashMap.cleanUp();
            return weakIdentityHashMap.mMap.size();
        }
    }

    public TrackedObjects(TrackedCollections trackedCollections) {
        this.mTrackedCollections = trackedCollections;
    }
}
