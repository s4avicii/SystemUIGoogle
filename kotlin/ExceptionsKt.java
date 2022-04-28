package kotlin;

import kotlin.internal.PlatformImplementationsKt;

/* compiled from: Exceptions.kt */
public final class ExceptionsKt {
    public static final void addSuppressed(Throwable th, Throwable th2) {
        if (th != th2) {
            PlatformImplementationsKt.IMPLEMENTATIONS.addSuppressed(th, th2);
        }
    }
}
