package kotlinx.coroutines.internal;

import kotlinx.coroutines.android.HandlerContext;

/* compiled from: MainDispatcherFactory.kt */
public interface MainDispatcherFactory {
    HandlerContext createDispatcher$1();

    void getLoadPriority();

    void hintOnError();
}
