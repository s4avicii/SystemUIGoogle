package kotlin.internal.jdk7;

import kotlin.internal.PlatformImplementations;

/* compiled from: JDK7PlatformImplementations.kt */
public class JDK7PlatformImplementations extends PlatformImplementations {
    public final void addSuppressed(Throwable th, Throwable th2) {
        th.addSuppressed(th2);
    }
}
