package kotlinx.coroutines;

import kotlin.coroutines.CoroutineContext;

/* compiled from: Unconfined.kt */
public final class Unconfined extends CoroutineDispatcher {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        new Unconfined();
    }

    public final boolean isDispatchNeeded() {
        return false;
    }

    public final String toString() {
        return "Dispatchers.Unconfined";
    }

    public final void dispatch(CoroutineContext coroutineContext, Runnable runnable) {
        YieldContext yieldContext = (YieldContext) coroutineContext.get(YieldContext.Key);
        if (yieldContext != null) {
            yieldContext.dispatcherWasUnconfined = true;
            return;
        }
        throw new UnsupportedOperationException("Dispatchers.Unconfined.dispatch function can only be used by the yield function. If you wrap Unconfined dispatcher in your code, make sure you properly delegate isDispatchNeeded and dispatch calls.");
    }
}
