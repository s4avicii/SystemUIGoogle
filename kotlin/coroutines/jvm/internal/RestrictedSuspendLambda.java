package kotlin.coroutines.jvm.internal;

import java.util.Objects;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.FunctionBase;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.ReflectionFactory;

/* compiled from: ContinuationImpl.kt */
public abstract class RestrictedSuspendLambda extends RestrictedContinuationImpl implements FunctionBase<Object> {
    private final int arity = 2;

    public RestrictedSuspendLambda(Continuation continuation) {
        super(continuation);
    }

    public final String toString() {
        if (getCompletion() != null) {
            return super.toString();
        }
        Objects.requireNonNull(Reflection.factory);
        return ReflectionFactory.renderLambdaToString(this);
    }

    public final int getArity() {
        return this.arity;
    }
}
