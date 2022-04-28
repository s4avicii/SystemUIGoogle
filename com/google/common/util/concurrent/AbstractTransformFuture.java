package com.google.common.util.concurrent;

import androidx.appcompat.view.SupportMenuInflater$$ExternalSyntheticOutline0;
import com.google.android.systemui.assist.uihints.TranscriptionController$$ExternalSyntheticLambda0;
import com.google.common.base.Function;
import com.google.common.util.concurrent.AbstractFuture;
import com.google.common.util.concurrent.FluentFuture;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

public abstract class AbstractTransformFuture<I, O, F, T> extends FluentFuture.TrustedFuture<O> implements Runnable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public F function;
    public ListenableFuture<? extends I> inputFuture;

    public final void afterDone() {
        boolean z;
        ListenableFuture<? extends I> listenableFuture = this.inputFuture;
        boolean z2 = true;
        if (listenableFuture != null) {
            z = true;
        } else {
            z = false;
        }
        if (z && isCancelled()) {
            Object obj = this.value;
            if (!(obj instanceof AbstractFuture.Cancellation) || !((AbstractFuture.Cancellation) obj).wasInterrupted) {
                z2 = false;
            }
            listenableFuture.cancel(z2);
        }
        this.inputFuture = null;
        this.function = null;
    }

    public final String pendingToString() {
        String str;
        ListenableFuture<? extends I> listenableFuture = this.inputFuture;
        F f = this.function;
        String pendingToString = super.pendingToString();
        if (listenableFuture != null) {
            str = "inputFuture=[" + listenableFuture + "], ";
        } else {
            str = "";
        }
        if (f != null) {
            return str + "function=[" + f + "]";
        } else if (pendingToString != null) {
            return SupportMenuInflater$$ExternalSyntheticOutline0.m4m(str, pendingToString);
        } else {
            return null;
        }
    }

    public final void run() {
        boolean z;
        AbstractFuture.SetFuture setFuture;
        AbstractFuture.Failure failure;
        ListenableFuture<? extends I> listenableFuture = this.inputFuture;
        F f = this.function;
        boolean z2 = this.value instanceof AbstractFuture.Cancellation;
        boolean z3 = true;
        if (listenableFuture == null) {
            z = true;
        } else {
            z = false;
        }
        boolean z4 = z2 | z;
        if (f != null) {
            z3 = false;
        }
        if (!z4 && !z3) {
            this.inputFuture = null;
            if (listenableFuture.isCancelled()) {
                Object obj = this.value;
                if (obj == null) {
                    if (listenableFuture.isDone()) {
                        if (AbstractFuture.ATOMIC_HELPER.casValue(this, (Object) null, AbstractFuture.getFutureValue(listenableFuture))) {
                            AbstractFuture.complete(this);
                            return;
                        }
                        return;
                    }
                    setFuture = new AbstractFuture.SetFuture(this, listenableFuture);
                    if (AbstractFuture.ATOMIC_HELPER.casValue(this, (Object) null, setFuture)) {
                        try {
                            listenableFuture.addListener(setFuture, DirectExecutor.INSTANCE);
                            return;
                        } catch (Throwable unused) {
                            failure = AbstractFuture.Failure.FALLBACK_INSTANCE;
                        }
                    } else {
                        obj = this.value;
                    }
                }
                if (obj instanceof AbstractFuture.Cancellation) {
                    listenableFuture.cancel(((AbstractFuture.Cancellation) obj).wasInterrupted);
                    return;
                }
                return;
            }
            try {
                try {
                    Object apply = ((Function) f).apply(Futures.getDone(listenableFuture));
                    this.function = null;
                    TransformFuture transformFuture = (TransformFuture) this;
                    if (apply == null) {
                        apply = AbstractFuture.NULL;
                    }
                    if (AbstractFuture.ATOMIC_HELPER.casValue(transformFuture, (Object) null, apply)) {
                        AbstractFuture.complete(transformFuture);
                        return;
                    }
                    return;
                } catch (Throwable th) {
                    this.function = null;
                    throw th;
                }
            } catch (CancellationException unused2) {
                cancel(false);
                return;
            } catch (ExecutionException e) {
                setException(e.getCause());
                return;
            } catch (RuntimeException e2) {
                setException(e2);
                return;
            } catch (Error e3) {
                setException(e3);
                return;
            }
        } else {
            return;
        }
        AbstractFuture.ATOMIC_HELPER.casValue(this, setFuture, failure);
    }

    public AbstractTransformFuture(ListenableFuture listenableFuture, TranscriptionController$$ExternalSyntheticLambda0 transcriptionController$$ExternalSyntheticLambda0) {
        Objects.requireNonNull(listenableFuture);
        this.inputFuture = listenableFuture;
        this.function = transcriptionController$$ExternalSyntheticLambda0;
    }

    public static final class TransformFuture<I, O> extends AbstractTransformFuture<I, O, Function<? super I, ? extends O>, O> {
        public TransformFuture(ListenableFuture listenableFuture, TranscriptionController$$ExternalSyntheticLambda0 transcriptionController$$ExternalSyntheticLambda0) {
            super(listenableFuture, transcriptionController$$ExternalSyntheticLambda0);
        }
    }
}
