package kotlin;

import kotlin.Result;

/* compiled from: Result.kt */
public final class ResultKt {
    public static final void throwOnFailure(Object obj) {
        if (obj instanceof Result.Failure) {
            throw ((Result.Failure) obj).exception;
        }
    }
}
