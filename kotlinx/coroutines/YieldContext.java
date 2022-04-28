package kotlinx.coroutines;

import kotlin.coroutines.AbstractCoroutineContextElement;
import kotlin.coroutines.CoroutineContext;

/* compiled from: Unconfined.kt */
public final class YieldContext extends AbstractCoroutineContextElement {
    public static final Key Key = new Key();
    public boolean dispatcherWasUnconfined;

    /* compiled from: Unconfined.kt */
    public static final class Key implements CoroutineContext.Key<YieldContext> {
    }

    public YieldContext() {
        super(Key);
    }
}
