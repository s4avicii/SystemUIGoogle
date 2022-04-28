package com.google.android.setupcompat.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import com.google.android.setupcompat.util.Logger;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

public final class SetupCompatServiceInvoker {
    public static final Logger LOG = new Logger("SetupCompatServiceInvoker");
    public static final long MAX_WAIT_TIME_FOR_CONNECTION_MS = TimeUnit.SECONDS.toMillis(10);
    @SuppressLint({"StaticFieldLeak"})
    public static SetupCompatServiceInvoker instance;
    public final Context context;
    public final ExecutorService loggingExecutor;
    public final long waitTimeInMillisForServiceConnection;

    public static synchronized SetupCompatServiceInvoker get(Context context2) {
        SetupCompatServiceInvoker setupCompatServiceInvoker;
        synchronized (SetupCompatServiceInvoker.class) {
            if (instance == null) {
                instance = new SetupCompatServiceInvoker(context2.getApplicationContext());
            }
            setupCompatServiceInvoker = instance;
        }
        return setupCompatServiceInvoker;
    }

    @SuppressLint({"DefaultLocale"})
    public final void logMetricEvent(int i, Bundle bundle) {
        try {
            this.loggingExecutor.execute(new SetupCompatServiceInvoker$$ExternalSyntheticLambda1(this, i, bundle));
        } catch (RejectedExecutionException e) {
            LOG.mo18772e(String.format("Metric of type %d dropped since queue is full.", new Object[]{Integer.valueOf(i)}), e);
        }
    }

    public SetupCompatServiceInvoker(Context context2) {
        this.context = context2;
        ExecutorProvider<ExecutorService> executorProvider = ExecutorProvider.setupCompatServiceInvoker;
        Objects.requireNonNull(executorProvider);
        T t = executorProvider.injectedExecutor;
        this.loggingExecutor = (ExecutorService) (t == null ? executorProvider.executor : t);
        this.waitTimeInMillisForServiceConnection = MAX_WAIT_TIME_FOR_CONNECTION_MS;
    }

    public static void setInstanceForTesting(SetupCompatServiceInvoker setupCompatServiceInvoker) {
        instance = setupCompatServiceInvoker;
    }
}
