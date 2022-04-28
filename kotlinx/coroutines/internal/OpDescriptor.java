package kotlinx.coroutines.internal;

import kotlinx.coroutines.DebugStringsKt;

/* compiled from: Atomic.kt */
public abstract class OpDescriptor {
    public abstract Object perform(Object obj);

    public final String toString() {
        return DebugStringsKt.getClassSimpleName(this) + '@' + DebugStringsKt.getHexAddress(this);
    }
}
