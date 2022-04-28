package kotlinx.coroutines.android;

import android.os.Handler;
import android.os.Looper;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.ExecutorCoroutineDispatcher;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.MainCoroutineDispatcher;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.LimitingDispatcher;

/* compiled from: HandlerDispatcher.kt */
public final class HandlerContext extends HandlerDispatcher {
    public volatile HandlerContext _immediate;
    public final Handler handler;
    public final HandlerContext immediate;
    public final boolean invokeImmediately;
    public final String name;

    public HandlerContext(Handler handler2, String str, boolean z) {
        super(0);
        this.handler = handler2;
        this.name = str;
        this.invokeImmediately = z;
        this._immediate = z ? this : null;
        HandlerContext handlerContext = this._immediate;
        if (handlerContext == null) {
            handlerContext = new HandlerContext(handler2, str, true);
            this._immediate = handlerContext;
        }
        this.immediate = handlerContext;
    }

    public final void dispatch(CoroutineContext coroutineContext, Runnable runnable) {
        if (!this.handler.post(runnable)) {
            CancellationException cancellationException = new CancellationException("The task was rejected, the handler underlying the dispatcher '" + this + "' was closed");
            Job job = (Job) coroutineContext.get(Job.Key.$$INSTANCE);
            if (job != null) {
                job.cancel(cancellationException);
            }
            LimitingDispatcher limitingDispatcher = Dispatchers.f157IO;
            Objects.requireNonNull(limitingDispatcher);
            limitingDispatcher.dispatch(runnable, false);
        }
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof HandlerContext) || ((HandlerContext) obj).handler != this.handler) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        return System.identityHashCode(this.handler);
    }

    public final boolean isDispatchNeeded() {
        if (!this.invokeImmediately || !Intrinsics.areEqual(Looper.myLooper(), this.handler.getLooper())) {
            return true;
        }
        return false;
    }

    public final String toString() {
        String str;
        MainCoroutineDispatcher mainCoroutineDispatcher;
        ExecutorCoroutineDispatcher executorCoroutineDispatcher = Dispatchers.Default;
        MainCoroutineDispatcher mainCoroutineDispatcher2 = MainDispatcherLoader.dispatcher;
        if (this == mainCoroutineDispatcher2) {
            str = "Dispatchers.Main";
        } else {
            try {
                mainCoroutineDispatcher = mainCoroutineDispatcher2.getImmediate();
            } catch (UnsupportedOperationException unused) {
                mainCoroutineDispatcher = null;
            }
            if (this == mainCoroutineDispatcher) {
                str = "Dispatchers.Main.immediate";
            } else {
                str = null;
            }
        }
        if (str != null) {
            return str;
        }
        String str2 = this.name;
        if (str2 == null) {
            str2 = this.handler.toString();
        }
        if (this.invokeImmediately) {
            return Intrinsics.stringPlus(str2, ".immediate");
        }
        return str2;
    }

    public HandlerContext(Handler handler2) {
        this(handler2, (String) null, false);
    }

    public final MainCoroutineDispatcher getImmediate() {
        return this.immediate;
    }
}
