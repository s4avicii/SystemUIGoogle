package com.airbnb.lottie;

import android.os.Handler;
import android.os.Looper;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public final class LottieTask<T> {
    public static ExecutorService EXECUTOR = Executors.newCachedThreadPool();
    public final LinkedHashSet failureListeners;
    public final Handler handler;
    public volatile LottieResult<T> result;
    public final LinkedHashSet successListeners;

    public class LottieFutureTask extends FutureTask<LottieResult<T>> {
        public LottieFutureTask(Callable<LottieResult<T>> callable) {
            super(callable);
        }

        public final void done() {
            if (!isCancelled()) {
                try {
                    LottieTask.this.setResult((LottieResult) get());
                } catch (InterruptedException | ExecutionException e) {
                    LottieTask.this.setResult(new LottieResult(e));
                }
            }
        }
    }

    public LottieTask() {
        throw null;
    }

    public LottieTask(Callable<LottieResult<T>> callable) {
        this.successListeners = new LinkedHashSet(1);
        this.failureListeners = new LinkedHashSet(1);
        this.handler = new Handler(Looper.getMainLooper());
        this.result = null;
        EXECUTOR.execute(new LottieFutureTask(callable));
    }

    public final synchronized LottieTask<T> addFailureListener(LottieListener<Throwable> lottieListener) {
        if (this.result != null) {
            LottieResult<T> lottieResult = this.result;
            Objects.requireNonNull(lottieResult);
            if (lottieResult.exception != null) {
                LottieResult<T> lottieResult2 = this.result;
                Objects.requireNonNull(lottieResult2);
                lottieListener.onResult(lottieResult2.exception);
            }
        }
        this.failureListeners.add(lottieListener);
        return this;
    }

    public final synchronized LottieTask<T> addListener(LottieListener<T> lottieListener) {
        if (this.result != null) {
            LottieResult<T> lottieResult = this.result;
            Objects.requireNonNull(lottieResult);
            if (lottieResult.value != null) {
                LottieResult<T> lottieResult2 = this.result;
                Objects.requireNonNull(lottieResult2);
                lottieListener.onResult(lottieResult2.value);
            }
        }
        this.successListeners.add(lottieListener);
        return this;
    }

    public final void setResult(LottieResult<T> lottieResult) {
        if (this.result == null) {
            this.result = lottieResult;
            this.handler.post(new Runnable() {
                /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
                    return;
                 */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public final void run() {
                    /*
                        r4 = this;
                        com.airbnb.lottie.LottieTask r0 = com.airbnb.lottie.LottieTask.this
                        com.airbnb.lottie.LottieResult<T> r0 = r0.result
                        if (r0 != 0) goto L_0x0007
                        return
                    L_0x0007:
                        com.airbnb.lottie.LottieTask r0 = com.airbnb.lottie.LottieTask.this
                        com.airbnb.lottie.LottieResult<T> r0 = r0.result
                        java.util.Objects.requireNonNull(r0)
                        V r1 = r0.value
                        if (r1 == 0) goto L_0x0038
                        com.airbnb.lottie.LottieTask r4 = com.airbnb.lottie.LottieTask.this
                        java.util.Objects.requireNonNull(r4)
                        monitor-enter(r4)
                        java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x0035 }
                        java.util.LinkedHashSet r2 = r4.successListeners     // Catch:{ all -> 0x0035 }
                        r0.<init>(r2)     // Catch:{ all -> 0x0035 }
                        java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x0035 }
                    L_0x0023:
                        boolean r2 = r0.hasNext()     // Catch:{ all -> 0x0035 }
                        if (r2 == 0) goto L_0x0033
                        java.lang.Object r2 = r0.next()     // Catch:{ all -> 0x0035 }
                        com.airbnb.lottie.LottieListener r2 = (com.airbnb.lottie.LottieListener) r2     // Catch:{ all -> 0x0035 }
                        r2.onResult(r1)     // Catch:{ all -> 0x0035 }
                        goto L_0x0023
                    L_0x0033:
                        monitor-exit(r4)
                        goto L_0x007c
                    L_0x0035:
                        r0 = move-exception
                        monitor-exit(r4)
                        throw r0
                    L_0x0038:
                        com.airbnb.lottie.LottieTask r4 = com.airbnb.lottie.LottieTask.this
                        java.lang.Throwable r0 = r0.exception
                        java.util.Objects.requireNonNull(r4)
                        monitor-enter(r4)
                        java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ all -> 0x007d }
                        java.util.LinkedHashSet r2 = r4.failureListeners     // Catch:{ all -> 0x007d }
                        r1.<init>(r2)     // Catch:{ all -> 0x007d }
                        boolean r2 = r1.isEmpty()     // Catch:{ all -> 0x007d }
                        if (r2 == 0) goto L_0x0067
                        java.lang.String r1 = "Lottie encountered an error but no failure listener was added:"
                        com.airbnb.lottie.utils.LogcatLogger r2 = com.airbnb.lottie.utils.Logger.INSTANCE     // Catch:{ all -> 0x007d }
                        java.util.Objects.requireNonNull(r2)     // Catch:{ all -> 0x007d }
                        java.util.HashSet r2 = com.airbnb.lottie.utils.LogcatLogger.loggedMessages     // Catch:{ all -> 0x007d }
                        boolean r3 = r2.contains(r1)     // Catch:{ all -> 0x007d }
                        if (r3 == 0) goto L_0x005d
                        goto L_0x0065
                    L_0x005d:
                        java.lang.String r3 = "LOTTIE"
                        android.util.Log.w(r3, r1, r0)     // Catch:{ all -> 0x007d }
                        r2.add(r1)     // Catch:{ all -> 0x007d }
                    L_0x0065:
                        monitor-exit(r4)
                        goto L_0x007c
                    L_0x0067:
                        java.util.Iterator r1 = r1.iterator()     // Catch:{ all -> 0x007d }
                    L_0x006b:
                        boolean r2 = r1.hasNext()     // Catch:{ all -> 0x007d }
                        if (r2 == 0) goto L_0x007b
                        java.lang.Object r2 = r1.next()     // Catch:{ all -> 0x007d }
                        com.airbnb.lottie.LottieListener r2 = (com.airbnb.lottie.LottieListener) r2     // Catch:{ all -> 0x007d }
                        r2.onResult(r0)     // Catch:{ all -> 0x007d }
                        goto L_0x006b
                    L_0x007b:
                        monitor-exit(r4)
                    L_0x007c:
                        return
                    L_0x007d:
                        r0 = move-exception
                        monitor-exit(r4)
                        throw r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.LottieTask.C04671.run():void");
                }
            });
            return;
        }
        throw new IllegalStateException("A task may only be set once.");
    }
}
