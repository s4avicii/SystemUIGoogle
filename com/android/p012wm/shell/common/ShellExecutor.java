package com.android.p012wm.shell.common;

import com.android.p012wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda11;
import com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui.SuggestController$$ExternalSyntheticLambda1;
import java.lang.reflect.Array;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/* renamed from: com.android.wm.shell.common.ShellExecutor */
public interface ShellExecutor extends Executor {
    void execute(Runnable runnable);

    void executeDelayed(Runnable runnable, long j);

    boolean hasCallback(SuggestController$$ExternalSyntheticLambda1 suggestController$$ExternalSyntheticLambda1);

    void removeCallbacks(Runnable runnable);

    void executeBlocking(Runnable runnable) throws InterruptedException {
        TimeUnit timeUnit = TimeUnit.SECONDS;
        CountDownLatch countDownLatch = new CountDownLatch(1);
        execute(new ShellExecutor$$ExternalSyntheticLambda0(runnable, countDownLatch, 0));
        countDownLatch.await((long) 2, timeUnit);
    }

    void executeBlocking$1(Runnable runnable) throws InterruptedException {
        TimeUnit timeUnit = TimeUnit.SECONDS;
        executeBlocking(runnable);
    }

    Object executeBlockingForResult(BubbleController$BubblesImpl$$ExternalSyntheticLambda11 bubbleController$BubblesImpl$$ExternalSyntheticLambda11) {
        Object[] objArr = (Object[]) Array.newInstance(Boolean.class, 1);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        execute(new ShellExecutor$$ExternalSyntheticLambda1(objArr, bubbleController$BubblesImpl$$ExternalSyntheticLambda11, countDownLatch, 0));
        try {
            countDownLatch.await();
            return objArr[0];
        } catch (InterruptedException unused) {
            return null;
        }
    }
}
