package com.android.systemui.util.leak;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;

public final class WeakIdentityHashMap<K, V> {
    public final HashMap<WeakReference<K>, V> mMap = new HashMap<>();
    public final ReferenceQueue<Object> mRefQueue = new ReferenceQueue<>();

    public static class CmpWeakReference<K> extends WeakReference<K> {
        public final int mHashCode;

        public CmpWeakReference(K k) {
            super(k);
            this.mHashCode = System.identityHashCode(k);
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            Object obj2 = get();
            if (obj2 == null || !(obj instanceof CmpWeakReference)) {
                return false;
            }
            return ((CmpWeakReference) obj).get() == obj2;
        }

        public CmpWeakReference(K k, ReferenceQueue<Object> referenceQueue) {
            super(k, referenceQueue);
            this.mHashCode = System.identityHashCode(k);
        }

        public final int hashCode() {
            return this.mHashCode;
        }
    }

    public final void cleanUp() {
        while (true) {
            Reference<? extends Object> poll = this.mRefQueue.poll();
            if (poll != null) {
                this.mMap.remove(poll);
            } else {
                return;
            }
        }
    }
}
