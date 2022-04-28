package androidx.core.provider;

import android.os.Handler;
import androidx.core.provider.FontRequestWorker;
import androidx.core.util.Consumer;
import java.util.concurrent.Callable;

public final class RequestExecutor$ReplyRunnable<T> implements Runnable {
    public Callable<T> mCallable;
    public Consumer<T> mConsumer;
    public Handler mHandler;

    public final void run() {
        final T t;
        try {
            t = this.mCallable.call();
        } catch (Exception unused) {
            t = null;
        }
        final Consumer<T> consumer = this.mConsumer;
        this.mHandler.post(new Runnable() {
            public final void run() {
                Consumer.this.accept(t);
            }
        });
    }

    public RequestExecutor$ReplyRunnable(Handler handler, FontRequestWorker.C01153 r2, FontRequestWorker.C01164 r3) {
        this.mCallable = r2;
        this.mConsumer = r3;
        this.mHandler = handler;
    }
}
