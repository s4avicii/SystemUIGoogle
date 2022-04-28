package kotlinx.coroutines.android;

import android.os.Looper;
import kotlinx.coroutines.internal.MainDispatcherFactory;

/* compiled from: HandlerDispatcher.kt */
public final class AndroidDispatcherFactory implements MainDispatcherFactory {
    public final void getLoadPriority() {
    }

    public final void hintOnError() {
    }

    public final HandlerContext createDispatcher$1() {
        return new HandlerContext(HandlerDispatcherKt.asHandler(Looper.getMainLooper(), true));
    }
}
