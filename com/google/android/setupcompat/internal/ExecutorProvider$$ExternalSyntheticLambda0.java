package com.google.android.setupcompat.internal;

import java.util.concurrent.ThreadFactory;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ExecutorProvider$$ExternalSyntheticLambda0 implements ThreadFactory {
    public final /* synthetic */ String f$0;

    public /* synthetic */ ExecutorProvider$$ExternalSyntheticLambda0(String str) {
        this.f$0 = str;
    }

    public final Thread newThread(Runnable runnable) {
        return new Thread(runnable, this.f$0);
    }
}
