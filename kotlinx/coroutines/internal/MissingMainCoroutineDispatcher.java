package kotlinx.coroutines.internal;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.MainCoroutineDispatcher;

/* compiled from: MainDispatchers.kt */
public final class MissingMainCoroutineDispatcher extends MainCoroutineDispatcher {
    public final Throwable cause;
    public final String errorHint;

    public final MainCoroutineDispatcher getImmediate() {
        return this;
    }

    public final Void missing() {
        String str;
        if (this.cause != null) {
            String str2 = this.errorHint;
            if (str2 == null || (str = Intrinsics.stringPlus(". ", str2)) == null) {
                str = "";
            }
            throw new IllegalStateException(Intrinsics.stringPlus("Module with the Main dispatcher had failed to initialize", str), this.cause);
        }
        throw new IllegalStateException("Module with the Main dispatcher is missing. Add dependency providing the Main dispatcher, e.g. 'kotlinx-coroutines-android' and ensure it has the same version as 'kotlinx-coroutines-core'");
    }

    public final String toString() {
        String str;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Dispatchers.Main[missing");
        Throwable th = this.cause;
        if (th != null) {
            str = Intrinsics.stringPlus(", cause=", th);
        } else {
            str = "";
        }
        m.append(str);
        m.append(']');
        return m.toString();
    }

    public MissingMainCoroutineDispatcher(Throwable th, String str) {
        this.cause = th;
        this.errorHint = str;
    }

    public final void dispatch(CoroutineContext coroutineContext, Runnable runnable) {
        missing();
        throw null;
    }

    public final boolean isDispatchNeeded() {
        missing();
        throw null;
    }
}
