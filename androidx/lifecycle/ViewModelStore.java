package androidx.lifecycle;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public final class ViewModelStore {
    public final HashMap<String, ViewModel> mMap = new HashMap<>();

    public final void clear() {
        for (ViewModel next : this.mMap.values()) {
            Objects.requireNonNull(next);
            HashMap hashMap = next.mBagOfTags;
            if (hashMap != null) {
                synchronized (hashMap) {
                    for (Object next2 : next.mBagOfTags.values()) {
                        if (next2 instanceof Closeable) {
                            try {
                                ((Closeable) next2).close();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            }
            next.onCleared();
        }
        this.mMap.clear();
    }
}
