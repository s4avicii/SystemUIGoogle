package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Exceptions.kt */
public final class JobCancellationException extends CancellationException implements CopyableThrowable<JobCancellationException> {
    public final Job job;

    public final Throwable createCopy() {
        if (!DebugKt.DEBUG) {
            return null;
        }
        String message = getMessage();
        Intrinsics.checkNotNull(message);
        return new JobCancellationException(message, this, this.job);
    }

    public final boolean equals(Object obj) {
        if (obj != this) {
            if (obj instanceof JobCancellationException) {
                JobCancellationException jobCancellationException = (JobCancellationException) obj;
                if (!Intrinsics.areEqual(jobCancellationException.getMessage(), getMessage()) || !Intrinsics.areEqual(jobCancellationException.job, this.job) || !Intrinsics.areEqual(jobCancellationException.getCause(), getCause())) {
                    return false;
                }
            }
            return false;
        }
        return true;
    }

    public final Throwable fillInStackTrace() {
        if (DebugKt.DEBUG) {
            return super.fillInStackTrace();
        }
        setStackTrace(new StackTraceElement[0]);
        return this;
    }

    public final String toString() {
        return super.toString() + "; job=" + this.job;
    }

    public JobCancellationException(String str, Throwable th, Job job2) {
        super(str);
        this.job = job2;
        if (th != null) {
            initCause(th);
        }
    }

    public final int hashCode() {
        int i;
        String message = getMessage();
        Intrinsics.checkNotNull(message);
        int hashCode = (this.job.hashCode() + (message.hashCode() * 31)) * 31;
        Throwable cause = getCause();
        if (cause == null) {
            i = 0;
        } else {
            i = cause.hashCode();
        }
        return hashCode + i;
    }
}
