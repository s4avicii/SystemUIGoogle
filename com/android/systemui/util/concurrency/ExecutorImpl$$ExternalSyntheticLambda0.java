package com.android.systemui.util.concurrency;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Handler;
import android.os.Message;
import com.android.systemui.util.concurrency.ExecutorImpl;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ExecutorImpl$$ExternalSyntheticLambda0 implements Handler.Callback {
    public final /* synthetic */ ExecutorImpl f$0;

    public /* synthetic */ ExecutorImpl$$ExternalSyntheticLambda0(ExecutorImpl executorImpl) {
        this.f$0 = executorImpl;
    }

    public final boolean handleMessage(Message message) {
        Objects.requireNonNull(this.f$0);
        if (message.what == 0) {
            ((ExecutorImpl.ExecutionToken) message.obj).runnable.run();
            return true;
        }
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Unrecognized message: ");
        m.append(message.what);
        throw new IllegalStateException(m.toString());
    }
}
